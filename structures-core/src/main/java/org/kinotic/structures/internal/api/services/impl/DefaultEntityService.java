package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.RequiredArgsConstructor;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.*;
import org.kinotic.structures.api.domain.idl.decorators.MultiTenancyType;
import org.kinotic.structures.api.services.NamedQueriesService;
import org.kinotic.structures.api.services.security.AuthorizationService;
import org.kinotic.structures.internal.api.hooks.DelegatingUpsertPreProcessor;
import org.kinotic.structures.internal.api.hooks.ReadPreProcessor;
import org.kinotic.structures.internal.api.services.ElasticVersion;
import org.kinotic.structures.internal.api.services.EntityHolder;
import org.kinotic.structures.internal.api.services.EntityService;
import org.kinotic.structures.internal.api.services.sql.ParameterHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Navíd Mitchell 🤪 on 5/2/23.
 */
@RequiredArgsConstructor
public class DefaultEntityService implements EntityService {

    private static final Logger log = LoggerFactory.getLogger(DefaultEntityService.class);
    private final AuthorizationService<EntityOperation> authService;
    private final CrudServiceTemplate crudServiceTemplate;
    private final DelegatingUpsertPreProcessor delegatingUpsertPreProcessor;
    private final ElasticsearchAsyncClient esAsyncClient;
    private final NamedQueriesService namedQueriesService;
    private final ObjectMapper objectMapper;
    private final ReadPreProcessor readPreProcessor;
    private final Structure structure;
    private final StructuresProperties structuresProperties;

    @WithSpan
    @Override
    public <T> CompletableFuture<Void> bulkSave(T entities, EntityContext context) {
        return authService.authorize(EntityOperation.BULK_SAVE, context).thenCompose(
                un -> doBulkPersist(entities, context,
                                    entityHolder -> BulkOperation.of(b -> b
                                            .index(i -> {
                                                       i.index(structure.getItemIndex())
                                                        .id(entityHolder.getDocumentId())
                                                        .document(entityHolder.entity());

                                                       ElasticVersion elasticVersion = entityHolder.getElasticVersionIfPresent();
                                                       if(elasticVersion != null) {
                                                           i.ifPrimaryTerm(elasticVersion.primaryTerm())
                                                            .ifSeqNo(elasticVersion.seqNo());
                                                       }
                                                       return i;
                                                   }
                                            ))));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<Void> bulkUpdate(T entities, EntityContext context) {
        return authService.authorize(EntityOperation.BULK_UPDATE, context).thenCompose(
                un -> doBulkPersist(entities, context,
                                    entityHolder -> BulkOperation.of(b -> b
                                            .update(u -> {
                                                        u.index(structure.getItemIndex())
                                                         .id(entityHolder.getDocumentId())
                                                         .action(upB -> upB.doc(entityHolder.entity())
                                                                           .docAsUpsert(true)
                                                                           .detectNoop(true));

                                                        ElasticVersion elasticVersion = entityHolder.getElasticVersionIfPresent();
                                                        if(elasticVersion != null) {
                                                            u.ifPrimaryTerm(elasticVersion.primaryTerm())
                                                             .ifSeqNo(elasticVersion.seqNo());
                                                        }
                                                        return u;
                                                    }
                                            ))));
    }

    @WithSpan
    @Override
    public CompletableFuture<Long> count(EntityContext context) {
        return authService.authorize(EntityOperation.COUNT, context)
                          .thenCompose(un -> validateTenant(context)
                                  .thenCompose(unused -> crudServiceTemplate
                                          .count(structure.getItemIndex(),
                                                 builder -> readPreProcessor.beforeCount(structure, null, builder, context))));
    }

    @WithSpan
    @Override
    public CompletableFuture<Long> countByQuery(String query, EntityContext context) {
        return authService.authorize(EntityOperation.COUNT_BY_QUERY, context).thenCompose(
                un -> validateTenant(context)
                        .thenCompose(unused -> crudServiceTemplate
                                .count(structure.getItemIndex(),
                                       builder -> readPreProcessor.beforeCount(structure, query, builder, context))));
    }

    @WithSpan
    @Override
    public CompletableFuture<Void> deleteById(String id, EntityContext context) {
        return authService.authorize(EntityOperation.DELETE_BY_ID, context).thenCompose(
                un -> validateTenantAndComposeId(id, context)
                        .thenCompose(composedId -> crudServiceTemplate
                                .deleteById(structure.getItemIndex(),
                                            composedId,
                                            builder -> readPreProcessor.beforeDelete(structure, builder, context))
                                .thenApply(deleteResponse -> null)));
    }

    @WithSpan
    @Override
    public CompletableFuture<Void> deleteByQuery(String query, EntityContext context) {
        return authService.authorize(EntityOperation.DELETE_BY_QUERY, context).thenCompose(
                un -> validateTenant(context)
                        .thenCompose(unused -> crudServiceTemplate
                                .deleteByQuery(structure.getItemIndex(),
                                               builder -> readPreProcessor.beforeDeleteByQuery(structure, query, builder, context))
                                .thenApply(deleteResponse -> null)));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<Page<T>> findAll(Pageable pageable, Class<T> type, EntityContext context) {
        return authService.authorize(EntityOperation.FIND_ALL, context).thenCompose(
                un -> validateTenant(context)
                        .thenCompose(unused -> {

                            if(FastestType.class.isAssignableFrom(type)){

                                if(structure.isOptimisticLockingEnabled()){
                                    //noinspection unchecked
                                    return crudServiceTemplate
                                            .search(structure.getItemIndex(),
                                                    pageable,
                                                    Map.class,
                                                    builder -> readPreProcessor.beforeFindAll(structure, builder, context),
                                                    hit -> (T) new FastestType(updateVersionForEntity(hit.source(),
                                                                                                      hit.primaryTerm(),
                                                                                                      hit.seqNo()
                                                    )));
                                }else{
                                    //noinspection unchecked
                                    return crudServiceTemplate
                                            .search(structure.getItemIndex(),
                                                    pageable,
                                                    RawJson.class,
                                                    builder -> readPreProcessor.beforeFindAll(structure, builder, context),
                                                    hit -> (T) new FastestType(hit.source()));
                                }
                            }else{

                                if(structure.isOptimisticLockingEnabled()){
                                    return crudServiceTemplate
                                            .search(structure.getItemIndex(),
                                                    pageable,
                                                    type,
                                                    builder -> readPreProcessor.beforeFindAll(structure, builder, context),
                                                    hit -> updateVersionForEntity(hit.source(),
                                                                                  hit.primaryTerm(),
                                                                                  hit.seqNo()
                                                    ));
                                }else{
                                    return crudServiceTemplate
                                            .search(structure.getItemIndex(),
                                                    pageable,
                                                    type,
                                                    builder -> readPreProcessor.beforeFindAll(structure, builder, context));
                                }
                            }
                        }));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<T> findById(String id, Class<T> type, EntityContext context) {
        return authService.authorize(EntityOperation.FIND_BY_ID, context).thenCompose(
                un -> validateTenantAndComposeId(id, context).thenCompose(
                        composedId -> {

                            if(FastestType.class.isAssignableFrom(type)){

                                if(structure.isOptimisticLockingEnabled()){
                                    //noinspection unchecked
                                    return crudServiceTemplate
                                            .findById(structure.getItemIndex(),
                                                      composedId,
                                                      Map.class,
                                                      builder -> readPreProcessor.beforeFindById(structure, builder, context),
                                                      result -> (T) new FastestType(updateVersionForEntity(result.source(),
                                                                                                           result.primaryTerm(),
                                                                                                           result.seqNo()
                                                      )));
                                }else{
                                    //noinspection unchecked
                                    return crudServiceTemplate
                                            .findById(structure.getItemIndex(),
                                                      composedId,
                                                      RawJson.class,
                                                      builder -> readPreProcessor.beforeFindById(structure, builder, context),
                                                      result -> (T) new FastestType(result.source()));
                                }
                            }else{

                                if(structure.isOptimisticLockingEnabled()){
                                    return crudServiceTemplate
                                            .findById(structure.getItemIndex(),
                                                      composedId,
                                                      type,
                                                      builder -> readPreProcessor.beforeFindById(structure, builder, context),
                                                      result -> updateVersionForEntity(result.source(),
                                                                                       result.primaryTerm(),
                                                                                       result.seqNo()
                                                      ));
                                }else{
                                    return crudServiceTemplate
                                            .findById(structure.getItemIndex(),
                                                      composedId,
                                                      type,
                                                      builder -> readPreProcessor.beforeFindById(structure, builder, context));
                                }
                            }
                        }));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<List<T>> findByIds(List<String> ids, Class<T> type, EntityContext context) {
        return authService.authorize(EntityOperation.FIND_BY_IDS, context).thenCompose(
                un -> validateTenantAndComposeIds(ids, context)
                        .thenCompose(composedIds -> {

                            if(FastestType.class.isAssignableFrom(type)){

                                if(structure.isOptimisticLockingEnabled()){
                                    //noinspection unchecked
                                    return crudServiceTemplate
                                            .findByIds(structure.getItemIndex(),
                                                       composedIds,
                                                       Map.class,
                                                       builder -> readPreProcessor.beforeFindByIds(structure, builder, context),
                                                       result -> (T) new FastestType(updateVersionForEntity(result.source(),
                                                                                                            result.primaryTerm(),
                                                                                                            result.seqNo()
                                                       )));
                                }else{
                                    //noinspection unchecked
                                    return crudServiceTemplate
                                            .findByIds(structure.getItemIndex(),
                                                       composedIds,
                                                       RawJson.class,
                                                       builder -> readPreProcessor.beforeFindByIds(structure, builder, context),
                                                       result -> (T) new FastestType(result.source()));
                                }
                            }else{

                                if(structure.isOptimisticLockingEnabled()){
                                    return crudServiceTemplate
                                            .findByIds(structure.getItemIndex(),
                                                       composedIds,
                                                       type,
                                                       builder -> readPreProcessor.beforeFindByIds(structure, builder, context),
                                                       result -> updateVersionForEntity(result.source(),
                                                                                        result.primaryTerm(),
                                                                                        result.seqNo()
                                                       ));
                                }else{
                                    return crudServiceTemplate
                                            .findByIds(structure.getItemIndex(),
                                                       composedIds,
                                                       type,
                                                       builder -> readPreProcessor.beforeFindByIds(structure, builder, context));
                                }
                            }
                        }));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<List<T>> namedQuery(String queryName,
                                                     ParameterHolder parameterHolder,
                                                     Class<T> type,
                                                     EntityContext context) {
        return validateTenant(context)
                .thenCompose(unused -> namedQueriesService.executeNamedQuery(structure,
                                                                             queryName,
                                                                             parameterHolder,
                                                                             type,
                                                                             context));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<Page<T>> namedQueryPage(String queryName,
                                                         ParameterHolder parameterHolder,
                                                         Pageable pageable,
                                                         Class<T> type,
                                                         EntityContext context) {
        return validateTenant(context)
                .thenCompose(unused -> namedQueriesService.executeNamedQueryPage(structure,
                                                                                 queryName,
                                                                                 parameterHolder,
                                                                                 pageable,
                                                                                 type,
                                                                                 context));
    }

    @Override
    public CompletableFuture<Void> syncIndex(EntityContext context) {
        return authService.authorize(EntityOperation.SYNC_INDEX, context).thenCompose(
                un -> esAsyncClient.indices()
                                   .refresh(b -> b.index(structure.getItemIndex())
                                                  .allowNoIndices(false))
                                   .thenApply(unused -> null));
    }

    @WithSpan
    @SuppressWarnings("unchecked")
    @Override
    public <T> CompletableFuture<T> save(T entity, EntityContext context) {
        return authService.authorize(EntityOperation.SAVE, context).thenCompose(
                un -> doPrePersist(entity, context, entityHolder -> {

                    String routing = (structure.getMultiTenancyType() == MultiTenancyType.SHARED)
                            ? context.getParticipant().getTenantId()
                            : null;

                    return esAsyncClient.index(i -> {
                        i.routing(routing)
                         .index(structure.getItemIndex())
                         .id(entityHolder.getDocumentId())
                         .document(entityHolder.entity())
                         .refresh(Refresh.True);

                        ElasticVersion elasticVersion = entityHolder.getElasticVersionIfPresent();
                        if(elasticVersion != null) {
                            i.ifPrimaryTerm(elasticVersion.primaryTerm())
                             .ifSeqNo(elasticVersion.seqNo());
                        }

                        return i;
                    }).thenApply(indexResponse -> {

                        if(structure.isOptimisticLockingEnabled()){
                            return (T) updateVersionForEntity(entityHolder.entity(),
                                                              indexResponse.primaryTerm(), indexResponse.seqNo()
                            );
                        }else{
                            return (T) entityHolder.entity();
                        }
                    });
                }));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<Page<T>> search(String searchText, Pageable pageable, Class<T> type, EntityContext context) {
        return authService.authorize(EntityOperation.SEARCH, context).thenCompose(
                un -> validateTenant(context)
                        .thenCompose(unused -> {

                            if(FastestType.class.isAssignableFrom(type)){

                                if(structure.isOptimisticLockingEnabled()){
                                    //noinspection unchecked
                                    return crudServiceTemplate
                                            .search(structure.getItemIndex(),
                                                    pageable,
                                                    Map.class,
                                                    builder -> readPreProcessor.beforeSearch(structure,
                                                                                             searchText,
                                                                                             builder,
                                                                                             context),
                                                    hit -> (T) new FastestType(updateVersionForEntity(hit.source(),
                                                                                                      hit.primaryTerm(), hit.seqNo()
                                                    )));
                                }else{
                                    //noinspection unchecked
                                    return crudServiceTemplate
                                            .search(structure.getItemIndex(),
                                                    pageable,
                                                    RawJson.class,
                                                    builder -> readPreProcessor.beforeSearch(structure,
                                                                                             searchText,
                                                                                             builder,
                                                                                             context),
                                                    hit -> (T) new FastestType(hit.source()));
                                }
                            }else{

                                if(structure.isOptimisticLockingEnabled()){
                                    return crudServiceTemplate
                                            .search(structure.getItemIndex(),
                                                    pageable,
                                                    type,
                                                    builder -> readPreProcessor.beforeSearch(structure,
                                                                                             searchText,
                                                                                             builder,
                                                                                             context),
                                                    hit -> updateVersionForEntity(hit.source(),
                                                                                  hit.primaryTerm(),
                                                                                  hit.seqNo()
                                                    ));
                                }else{
                                    return crudServiceTemplate
                                            .search(structure.getItemIndex(),
                                                    pageable,
                                                    type,
                                                    builder -> readPreProcessor.beforeSearch(structure,
                                                                                             searchText,
                                                                                             builder,
                                                                                             context));
                                }
                            }
                        }));
    }

    @WithSpan
    @SuppressWarnings("unchecked")
    @Override
    public <T> CompletableFuture<T> update(T entity, EntityContext context) {
        return authService.authorize(EntityOperation.UPDATE, context).thenCompose(
                un -> doPrePersist(entity, context, entityHolder -> {

                    String routing = (structure.getMultiTenancyType() == MultiTenancyType.SHARED)
                            ? context.getParticipant().getTenantId()
                            : null;

                    UpdateRequest<?,?> request = UpdateRequest.of(u -> {
                        u.routing(routing)
                         .index(structure.getItemIndex())
                         .id(entityHolder.getDocumentId())
                         .doc(entityHolder.entity())
                         .docAsUpsert(true)
                         .refresh(Refresh.True);

                        ElasticVersion elasticVersion = entityHolder.getElasticVersionIfPresent();
                        if(elasticVersion != null) {
                            u.ifPrimaryTerm(elasticVersion.primaryTerm())
                             .ifSeqNo(elasticVersion.seqNo());
                        }
                        return u;
                    });

                    return esAsyncClient.update(request, entityHolder.entity().getClass())
                                        .thenApply(updateResponse -> {

                                            if(structure.isOptimisticLockingEnabled()){
                                                return (T) updateVersionForEntity(entityHolder.entity(),
                                                                                  updateResponse.primaryTerm(),
                                                                                  updateResponse.seqNo()
                                                );
                                            }else{
                                                return (T) entityHolder.entity();
                                            }
                                        });
                }));
    }

    @WithSpan
    private <T> CompletableFuture<Void> doBulkPersist(T entities,
                                                      EntityContext context,
                                                      Function<EntityHolder<?>, BulkOperation> persistLogic){
        return validateTenant(context)
                .thenCompose(unused -> delegatingUpsertPreProcessor
                        .processArray(entities, context)
                        .thenCompose(list -> {

                            String routing = (structure.getMultiTenancyType() == MultiTenancyType.SHARED)
                                    ? context.getParticipant().getTenantId()
                                    : null;

                            BulkRequest.Builder br = new BulkRequest.Builder();
                            br.routing(routing);

                            List<BulkOperation> bulkOperations = new ArrayList<>(list.size());
                            for(EntityHolder<?> entityHolder : list){

                                if(entityHolder.id() == null || entityHolder.id().isEmpty()){
                                    return CompletableFuture.failedFuture(new IllegalArgumentException("All Entities must have an id"));
                                }

                                bulkOperations.add(persistLogic.apply(entityHolder));
                            }

                            if(bulkOperations.isEmpty()){
                                return CompletableFuture.failedFuture(new IllegalArgumentException("No items found to create bulk request for"));
                            }

                            br.operations(bulkOperations);

                            return esAsyncClient.bulk(br.build()).thenCompose(bulkResponse -> {
                                if(bulkResponse.errors()){
                                    StringBuilder builder = new StringBuilder();
                                    for(BulkResponseItem item : bulkResponse.items()){
                                        if(item.error() != null){
                                            if(builder.indexOf(item.error().reason()) == -1){
                                                builder.append(item.error().reason()).append("\n");
                                            }
                                        }
                                    }
                                    return CompletableFuture.failedFuture(new IllegalArgumentException("Bulk save failed with errors:\n"+builder));
                                }else{
                                    return CompletableFuture.completedFuture(bulkResponse);
                                }
                            });

                        })).thenApply(unused -> null);
    }

    @WithSpan
    @SuppressWarnings("unchecked")
    private <T> CompletableFuture<T> doPrePersist(T entity,
                                                  EntityContext context,
                                                  Function<EntityHolder<?>, CompletableFuture<T>> persistLogic){
        return validateTenant(context)
                .thenCompose(unused -> (CompletableFuture<T>) delegatingUpsertPreProcessor
                        .process(entity, context)
                        .thenCompose(entityHolder -> {

                            if(entityHolder.id() == null || entityHolder.id().isEmpty()){
                                return CompletableFuture.failedFuture(new IllegalArgumentException("Entity must have an id"));
                            }

                            return persistLogic.apply(entityHolder)
                                               .thenApply(response -> entityHolder.entity());
                        }));
    }

    @WithSpan
    @SuppressWarnings({"rawtypes", "unchecked"})
    private <T> T updateVersionForEntity(T entity, Long primaryTerm, Long seqNo){
        String versionValue =  primaryTerm + ":" + seqNo;

        if (entity instanceof TokenBuffer buffer) {
            try {
                // Convert TokenBuffer to JSON tree
                ObjectNode node = objectMapper.readTree(buffer.asParser(objectMapper));

                node.put(structure.getVersionFieldName(), versionValue);

                // Serialize back to TokenBuffer
                TokenBuffer updatedBuffer = new TokenBuffer(objectMapper, false);
                objectMapper.writeValue(updatedBuffer, node);

                return (T) updatedBuffer;
            } catch (Exception e) {
                throw new IllegalStateException("Failed to update version in TokenBuffer", e);
            }
        } else if (entity instanceof Map map) {
            map.put(structure.getVersionFieldName(), versionValue);
        } else if (entity instanceof RawJson rawJson) {
            try {
                ObjectNode node = (ObjectNode) objectMapper.readTree(rawJson.data());
                node.put(structure.getVersionFieldName(), versionValue);

                byte[] updatedData = objectMapper.writeValueAsBytes(node);
                return (T) new RawJson(updatedData);
            } catch (Exception e) {
                throw new IllegalStateException("Failed to update version in RawJson", e);
            }
        } else {
            throw new IllegalArgumentException("Pojo Not Supported for Version");
        }
        return entity;
    }

    @WithSpan
    private CompletableFuture<Void> validateTenant(final EntityContext context){
        if(structure.getMultiTenancyType() == MultiTenancyType.SHARED){
            if(context.getParticipant() != null && context.getParticipant().getTenantId() != null) {
                return CompletableFuture.completedFuture(null);
            }else{
                return CompletableFuture.failedFuture(new IllegalArgumentException("TenantId is required when MultiTenancyType is SHARED"));
            }
        }else{
            return CompletableFuture.completedFuture(null);
        }
    }

    @WithSpan
    private CompletableFuture<String> validateTenantAndComposeId(final String id, final EntityContext context){
        return validateTenant(context)
                .thenApply(unused -> {
                    String ret;
                    if(structure.getMultiTenancyType() == MultiTenancyType.SHARED){
                        String tenantId = context.getParticipant().getTenantId();
                        ret = tenantId + "-" + id;
                    }else{
                        ret = id;
                    }
                    return ret;
                });
    }

    @WithSpan
    private CompletableFuture<List<String>> validateTenantAndComposeIds(final List<String> ids, final EntityContext context){
        return validateTenant(context)
                .thenApply(unused -> {
                    List<String> ret;
                    if(structure.getMultiTenancyType() == MultiTenancyType.SHARED){
                        String tenantId = context.getParticipant().getTenantId();
                        ret = ids.stream().map(id -> tenantId + "-" + id).collect(Collectors.toList());
                    }else{
                        ret = ids;
                    }
                    return ret;
                });
    }

}
