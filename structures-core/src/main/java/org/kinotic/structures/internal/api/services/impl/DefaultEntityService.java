package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.OpType;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.mget.MultiGetOperation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Created by Navíd Mitchell 🤪 on 5/2/23.
 */
@RequiredArgsConstructor
public class DefaultEntityService implements EntityService {

    private final AuthorizationService<EntityOperation> authService;
    private final CrudServiceTemplate crudServiceTemplate;
    private final DelegatingUpsertPreProcessor delegatingUpsertPreProcessor;
    private final ElasticsearchAsyncClient esAsyncClient;
    private final NamedQueriesService namedQueriesService;
    private final ObjectMapper objectMapper;
    private final ReadPreProcessor readPreProcessor;
    private final Structure structure;

    @WithSpan
    @Override
    public <T> CompletableFuture<Void> bulkSave(T entities, EntityContext context) {
        return doPersistBulk(entities,
                             EntityOperation.BULK_SAVE,
                             context,
                             entityHolder -> BulkOperation.of(b -> {

                                 // When optimistic locking is enabled and no version is present we use create
                                 // We do this since there is no way to set an initial primary_term / seq_no combination
                                 ElasticVersion elasticVersion = entityHolder.getElasticVersionIfPresent();
                                 if(structure.isOptimisticLockingEnabled()
                                         && elasticVersion == null){

                                     return b.create(c ->
                                                             c.index(structure.getItemIndex())
                                                              .id(entityHolder.getDocumentId())
                                                              .document(entityHolder.entity()));
                                 }else{
                                     return b.index(i -> {
                                         i.index(structure.getItemIndex())
                                          .id(entityHolder.getDocumentId())
                                          .document(entityHolder.entity());

                                         if(structure.isOptimisticLockingEnabled()
                                                 && elasticVersion != null){
                                             i.ifPrimaryTerm(elasticVersion.primaryTerm());
                                             i.ifSeqNo(elasticVersion.seqNo());
                                         }
                                         return i;
                                     });
                                 }
                             }));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<Void> bulkUpdate(T entities, EntityContext context) {
        return doPersistBulk(entities,
                             EntityOperation.BULK_UPDATE,
                             context,
                             entityHolder -> BulkOperation.of(b -> b
                                     .update(u -> {

                                                 ElasticVersion elasticVersion = entityHolder.getElasticVersionIfPresent();
                                                 u.index(structure.getItemIndex())
                                                  .id(entityHolder.getDocumentId())
                                                  .action(upB -> {
                                                      upB.doc(entityHolder.entity())
                                                         .detectNoop(true);

                                                      if(elasticVersion == null){
                                                          upB.docAsUpsert(true);
                                                      }
                                                      return upB;
                                                  });

                                                 if(structure.isOptimisticLockingEnabled()
                                                         && elasticVersion != null) {

                                                     u.ifPrimaryTerm(elasticVersion.primaryTerm())
                                                      .ifSeqNo(elasticVersion.seqNo());
                                                 } else if (structure.isOptimisticLockingEnabled()) {
                                                     throw new IllegalArgumentException("A Version must be provided when calling update");
                                                 }
                                                 return u;
                                             }
                                     )));
    }

    @WithSpan
    @Override
    public CompletableFuture<Long> count(EntityContext context) {
        return validateContext(context)
                .thenCompose(un -> authService.authorize(EntityOperation.COUNT, context))
                .thenCompose(un -> crudServiceTemplate
                        .count(structure.getItemIndex(),
                               builder -> readPreProcessor.beforeCount(structure, null, builder, context)));
    }

    @WithSpan
    @Override
    public CompletableFuture<Long> countByQuery(String query, EntityContext context) {
        return validateContext(context)
                .thenCompose(un -> authService.authorize(EntityOperation.COUNT_BY_QUERY, context))
                .thenCompose(un -> crudServiceTemplate
                        .count(structure.getItemIndex(),
                               builder -> readPreProcessor.beforeCount(structure, query, builder, context)));
    }

    @WithSpan
    @Override
    public CompletableFuture<Void> deleteById(String id, EntityContext context) {
        return validateContext(context)
                .thenCompose(un -> authService.authorize(EntityOperation.DELETE_BY_ID, context))
                .thenApply(un -> composeId(id, context))
                .thenCompose(composedId -> crudServiceTemplate
                        .deleteById(structure.getItemIndex(),
                                    composedId,
                                    builder -> readPreProcessor.beforeDelete(structure, builder, context))
                        .thenApply(deleteResponse -> null));
    }

    @WithSpan
    @Override
    public CompletableFuture<Void> deleteById(TenantSpecificId id, EntityContext context) {
        // We set the tenant selection so validation below will know that a tenant specific operation is being used
        context.setTenantSelection(List.of(id.getTenantId()));

        return validateContext(context)
                .thenCompose(un -> authService.authorize(EntityOperation.DELETE_BY_ID, context))
                .thenApply(un -> composeId(id))
                .thenCompose(composedId -> crudServiceTemplate
                        .deleteById(structure.getItemIndex(),
                                    composedId,
                                    builder -> readPreProcessor.beforeDelete(structure, builder, context))
                        .thenApply(deleteResponse -> null));
    }

    @WithSpan
    @Override
    public CompletableFuture<Void> deleteByQuery(String query, EntityContext context) {
        return validateContext(context)
                .thenCompose(un -> authService.authorize(EntityOperation.DELETE_BY_QUERY, context))
                .thenCompose(un -> crudServiceTemplate
                        .deleteByQuery(structure.getItemIndex(),
                                       builder -> readPreProcessor.beforeDeleteByQuery(structure, query, builder, context))
                        .thenApply(deleteResponse -> null));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<Page<T>> findAll(Pageable pageable, Class<T> type, EntityContext context) {
        return validateContext(context)
                .thenCompose(un -> authService.authorize(EntityOperation.FIND_ALL, context))
                .thenCompose(un -> {

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
                });
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<T> findById(String id, Class<T> type, EntityContext context) {
        return validateContext(context)
                .thenCompose(un -> authService.authorize(EntityOperation.FIND_BY_ID, context))
                .thenApply(un -> composeId(id, context))
                .thenCompose(composedId -> doFindById(composedId, type, context));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<T> findById(TenantSpecificId id, Class<T> type, EntityContext context) {
        // We set the tenant selection so validation below will know that a tenant specific operation is being used
        context.setTenantSelection(List.of(id.getTenantId()));

        return validateContext(context)
                .thenCompose(un -> authService.authorize(EntityOperation.FIND_BY_ID, context))
                .thenApply(un -> composeId(id))
                .thenCompose(composedId -> doFindById(composedId, type, context));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<List<T>> findByIds(List<String> ids, Class<T> type, EntityContext context) {
        return validateContext(context)
                .thenCompose(un -> authService.authorize(EntityOperation.FIND_BY_IDS, context))
                .thenApply(un -> composeIds(ids, context))
                .thenCompose(composedIds -> doFindByIds(composedIds, type, context));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<List<T>> findByIdsWithTenant(List<TenantSpecificId> ids, Class<T> type, EntityContext context) {
        return  validate_ComposeIds_AddTenantsToContext(ids, context)
                .thenCompose(composedIds
                                     -> authService.authorize(EntityOperation.FIND_BY_IDS, context)
                                                   .thenCompose(v -> doFindByIds(composedIds, type, context)));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<List<T>> namedQuery(String queryName,
                                                     ParameterHolder parameterHolder,
                                                     Class<T> type,
                                                     EntityContext context) {
        // Authorization happens in the QueryExecutor so we don't need an additional cache to hold the NamedQueryAuthorizationService
        return validateContext(context)
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
        // Authorization happens in the QueryExecutor so we don't need an additional cache to hold the NamedQueryAuthorizationService
        return validateContext(context)
                .thenCompose(unused -> namedQueriesService.executeNamedQueryPage(structure,
                                                                                 queryName,
                                                                                 parameterHolder,
                                                                                 pageable,
                                                                                 type,
                                                                                 context));
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<T> save(T entity, EntityContext context) {
        return doPersist(entity,
                         EntityOperation.SAVE,
                         context,
                         entityHolder -> {

                             String routing = (structure.getMultiTenancyType() == MultiTenancyType.SHARED)
                                     ? context.getParticipant().getTenantId()
                                     : null;

                             return esAsyncClient.index(i -> {
                                 i.routing(routing)
                                  .index(structure.getItemIndex())
                                  .id(entityHolder.getDocumentId())
                                  .document(entityHolder.entity())
                                  .refresh(Refresh.True);

                                 // When optimistic locking is enabled and no version is present we use create
                                 // We do this since there is no way to set an initial primary_term / seq_no combination
                                 ElasticVersion elasticVersion = entityHolder.getElasticVersionIfPresent();
                                 if(structure.isOptimisticLockingEnabled()
                                         && elasticVersion == null){

                                     i.opType(OpType.Create);

                                 }else if(structure.isOptimisticLockingEnabled()
                                         && elasticVersion != null){

                                     i.ifPrimaryTerm(elasticVersion.primaryTerm());
                                     i.ifSeqNo(elasticVersion.seqNo());
                                 }

                                 return i;
                             }).thenApply(indexResponse -> postProcessSaveOrUpdate(entity,
                                                                                   entityHolder,
                                                                                   indexResponse.primaryTerm(),
                                                                                   indexResponse.seqNo()));
                         });
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<Page<T>> search(String searchText, Pageable pageable, Class<T> type, EntityContext context) {
        return validateContext(context)
                .thenCompose(un -> authService.authorize(EntityOperation.SEARCH, context))
                .thenCompose(un -> {

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
                                                                                              hit.primaryTerm(),
                                                                                              hit.seqNo()
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
                });
    }

    @WithSpan
    @Override
    public CompletableFuture<Void> syncIndex(EntityContext context) {
        return authService.authorize(EntityOperation.SYNC_INDEX, context)
                          .thenCompose(un -> esAsyncClient.indices().refresh(
                                  b -> b.index(structure.getItemIndex())))
                          .thenApply(unused -> null);
    }

    @WithSpan
    @Override
    public <T> CompletableFuture<T> update(T entity, EntityContext context) {
        return doPersist(entity,
                         EntityOperation.UPDATE,
                         context,
                         entityHolder -> {

                             String routing = (structure.getMultiTenancyType() == MultiTenancyType.SHARED)
                                     ? context.getParticipant().getTenantId()
                                     : null;

                             UpdateRequest<?,?> request = UpdateRequest.of(u -> {
                                 u.routing(routing)
                                  .index(structure.getItemIndex())
                                  .id(entityHolder.getDocumentId())
                                  .doc(entityHolder.entity())
                                  .refresh(Refresh.True);

                                 ElasticVersion elasticVersion = entityHolder.getElasticVersionIfPresent();
                                 if(structure.isOptimisticLockingEnabled()
                                         && elasticVersion != null) {

                                     u.ifPrimaryTerm(elasticVersion.primaryTerm())
                                      .ifSeqNo(elasticVersion.seqNo());

                                 } else if (structure.isOptimisticLockingEnabled()) {
                                     throw new IllegalArgumentException("A Version must be provided when calling update");
                                 }else{
                                     u.docAsUpsert(true);
                                 }
                                 return u;
                             });

                             return esAsyncClient.update(request, entityHolder.entity().getClass())
                                                 .thenApply(updateResponse -> postProcessSaveOrUpdate(entity,
                                                                                                      entityHolder,
                                                                                                      updateResponse.primaryTerm(),
                                                                                                      updateResponse.seqNo()));
                         });
    }

    private String composeId(final String id, final EntityContext context){
        String ret;
        if(structure.getMultiTenancyType() == MultiTenancyType.SHARED){
            String tenantId = context.getParticipant().getTenantId();
            ret = tenantId + "-" + id;
        }else{
            ret = id;
        }
        return ret;
    }

    private String composeId(final TenantSpecificId id){
        return id.getTenantId() + "-" + id.getEntityId();
    }

    private List<MultiGetOperation> composeIds(final List<String> ids, final EntityContext context){
        List<MultiGetOperation> ret = new ArrayList<>(ids.size());
        boolean multiTenancyShared = structure.getMultiTenancyType() == MultiTenancyType.SHARED;

        String tenantId = context.getParticipant().getTenantId();
        for (String id : ids){
            MultiGetOperation.Builder builder =  new MultiGetOperation.Builder();
            builder.index(structure.getItemIndex());
            if(multiTenancyShared){
                builder.id(tenantId + "-" + id)
                       .routing(tenantId);
            }else{
                builder.id(id);
            }
            ret.add(builder.build());
        }
        return ret;
    }

    private <T> CompletableFuture<T> doFindById(String id, Class<T> type, EntityContext context) {
        if(FastestType.class.isAssignableFrom(type)){

            if(structure.isOptimisticLockingEnabled()){
                //noinspection unchecked
                return crudServiceTemplate
                        .findById(structure.getItemIndex(),
                                  id,
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
                                  id,
                                  RawJson.class,
                                  builder -> readPreProcessor.beforeFindById(structure, builder, context),
                                  result -> (T) new FastestType(result.source()));
            }
        }else{

            if(structure.isOptimisticLockingEnabled()){
                return crudServiceTemplate
                        .findById(structure.getItemIndex(),
                                  id,
                                  type,
                                  builder -> readPreProcessor.beforeFindById(structure, builder, context),
                                  result -> updateVersionForEntity(result.source(),
                                                                   result.primaryTerm(),
                                                                   result.seqNo()
                                  ));
            }else{
                return crudServiceTemplate
                        .findById(structure.getItemIndex(),
                                  id,
                                  type,
                                  builder -> readPreProcessor.beforeFindById(structure, builder, context));
            }
        }
    }

    private <T> CompletableFuture<List<T>> doFindByIds(List<MultiGetOperation> composedIds,
                                                       Class<T> type,
                                                       EntityContext context) {
        if(FastestType.class.isAssignableFrom(type)){
            if(structure.isOptimisticLockingEnabled()){
                //noinspection unchecked
                return crudServiceTemplate
                        .multiGet(composedIds,
                                  Map.class,
                                  builder -> readPreProcessor.beforeFindByIds(builder, context),
                                  result -> (T) new FastestType(updateVersionForEntity(result.source(),
                                                                                       result.primaryTerm(),
                                                                                       result.seqNo()
                                  )));
            }else{
                //noinspection unchecked
                return crudServiceTemplate
                        .multiGet(composedIds,
                                  RawJson.class,
                                  builder -> readPreProcessor.beforeFindByIds(builder, context),
                                  result -> (T) new FastestType(result.source()));
            }
        }else{

            if(structure.isOptimisticLockingEnabled()){
                return crudServiceTemplate
                        .multiGet(composedIds,
                                  type,
                                  builder -> readPreProcessor.beforeFindByIds(builder, context),
                                  result -> updateVersionForEntity(result.source(),
                                                                   result.primaryTerm(),
                                                                   result.seqNo()
                                  ));
            }else{
                return crudServiceTemplate
                        .multiGet(composedIds,
                                  type,
                                  builder -> readPreProcessor.beforeFindByIds(builder, context),
                                  null);
            }
        }
    }

    private <T> CompletableFuture<T> doPersist(T entity,
                                               EntityOperation operation,
                                               EntityContext context,
                                               Function<EntityHolder<?>, CompletableFuture<T>> persistLogic){
        // We do this since ideally processing data before auth is not ideal
        // However, in the case of Multi-tenant access we must extract tenant ids prior to calling auth
        if(structure.isMultiTenantSelectionEnabled()){

            return validateContext(context)
                    .thenCompose(un -> delegatingUpsertPreProcessor.process(entity, context))
                    .thenCompose(entityHolder ->
                                         authService.authorize(operation, context)
                                                    .thenCompose(un -> persistLogic.apply(entityHolder)));
        }else{
            return validateContext(context)
                    .thenCompose(un -> authService.authorize(operation, context))
                    .thenCompose(un -> delegatingUpsertPreProcessor.process(entity, context))
                    .thenCompose(persistLogic);
        }
    }

    private <T> CompletableFuture<Void> doPersistBulk(T entities,
                                                      EntityOperation operation,
                                                      EntityContext context,
                                                      Function<EntityHolder<?>, BulkOperation> persistLogic){
        // We do this since ideally processing data before auth is not ideal
        // However, in the case of Multi-tenant access we must extract tenant ids prior to calling auth
        if(structure.isMultiTenantSelectionEnabled()){

            return validateContext(context)
                    .thenCompose(un -> delegatingUpsertPreProcessor.processArray(entities, context))
                    .thenCompose(entityList ->
                                         authService.authorize(operation, context)
                                                    .thenCompose(un -> doPersistBulkLogic(entityList, context, persistLogic)))
                    .thenApply(unused -> null);
        }else {
            return validateContext(context)
                    .thenCompose(un -> authService.authorize(operation, context))
                    .thenCompose(un -> delegatingUpsertPreProcessor.processArray(entities, context))
                    .thenCompose(list -> doPersistBulkLogic(list, context, persistLogic))
                    .thenApply(un -> null);
        }
    }

    private CompletableFuture<BulkResponse> doPersistBulkLogic(List<EntityHolder<Object>> list, EntityContext context,
                                                               Function<EntityHolder<?>, BulkOperation> persistLogic) {
        String routing = (structure.getMultiTenancyType() == MultiTenancyType.SHARED)
                ? context.getParticipant().getTenantId()
                : null;

        BulkRequest.Builder br = new BulkRequest.Builder();
        br.routing(routing);

        List<BulkOperation> bulkOperations = new ArrayList<>(list.size());
        for(EntityHolder<?> entityHolder : list){
            bulkOperations.add(persistLogic.apply(entityHolder));
        }

        if(bulkOperations.isEmpty()){
            return CompletableFuture.failedFuture(new IllegalArgumentException("No items found to create bulk request for"));
        }

        br.operations(bulkOperations);

        return esAsyncClient.bulk(br.build()).thenCompose(bulkResponse -> {
            if (bulkResponse.errors()) {
                StringBuilder builder = new StringBuilder();
                for (BulkResponseItem item : bulkResponse.items()) {
                    if (item.error() != null) {
                        if (builder.indexOf(item.error().reason()) == -1) {
                            builder.append(item.error().reason()).append("\n");
                        }
                    }
                }
                return CompletableFuture.failedFuture(new IllegalArgumentException("Bulk save failed with errors:\n" + builder));
            } else {
                return CompletableFuture.completedFuture(bulkResponse);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private <T> T postProcessSaveOrUpdate(T entity, EntityHolder<?> entityHolder, Long primaryTerm, Long seqNo) {
        // All token buffers received will be converted to RawJson in the upsert preprocessor
        // This is done since it uses less memory for bulk operations
        // So we convert those cases back to a TokenBuffer before returning
        if(structure.isOptimisticLockingEnabled()){
            return (T) updateVersionForEntity(entityHolder.entity(),
                                              primaryTerm,
                                              seqNo,
                                              entity instanceof TokenBuffer
                                                      && entityHolder.entity() instanceof RawJson
            );
        }else{
            if(entity instanceof TokenBuffer
                    && entityHolder.entity() instanceof RawJson json){
                try {
                    ObjectNode node = (ObjectNode) objectMapper.readTree(json.data());
                    TokenBuffer buffer = new TokenBuffer(objectMapper, false);
                    objectMapper.writeValue(buffer, node);
                    return (T) buffer;
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }else {
                return (T) entityHolder.entity();
            }
        }
    }

    private <T> T updateVersionForEntity(T entity, Long primaryTerm, Long seqNo){
        return updateVersionForEntity(entity, primaryTerm, seqNo, false);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private <T> T updateVersionForEntity(T entity, Long primaryTerm, Long seqNo, boolean convertRawJsonToTokenBuffer){
        String versionValue =  primaryTerm + ":" + seqNo;

        switch (entity) {
            case TokenBuffer buffer -> {
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
            }
            case Map map -> map.put(structure.getVersionFieldName(), versionValue);
            case RawJson rawJson -> {

                try {
                    ObjectNode node = (ObjectNode) objectMapper.readTree(rawJson.data());
                    node.put(structure.getVersionFieldName(), versionValue);

                    // All token buffers passed to save or update will receive a RawJson object do to how the upsert pre processor works
                    // So we convert if need be
                    if (convertRawJsonToTokenBuffer) {

                        TokenBuffer updatedBuffer = new TokenBuffer(objectMapper, false);
                        objectMapper.writeValue(updatedBuffer, node);
                        return (T) updatedBuffer;
                    } else {

                        byte[] updatedData = objectMapper.writeValueAsBytes(node);
                        return (T) new RawJson(updatedData);
                    }
                } catch (Exception e) {
                    throw new IllegalStateException("Failed to update version in RawJson", e);
                }
            }
            case null, default -> throw new IllegalArgumentException("Pojo Not Supported for Version");
        }
        return entity;
    }

    // FIXME: This logic doesn't seem as clean as it used to be, for find/search operations it makes sense, for others such as save/update borderline redundant?
    private CompletableFuture<Void> validateContext(final EntityContext context){
        if(structure.getMultiTenancyType() == MultiTenancyType.SHARED){
            if(context.getParticipant() != null && context.getParticipant().getTenantId() != null) {

                // Check if tenant selection is trying to be used but not enabled
                if (ObjectUtils.isNotEmpty(context.getTenantSelection())
                        && !structure.isMultiTenantSelectionEnabled()) {

                    return CompletableFuture.failedFuture(
                            new IllegalArgumentException("Multi-tenant access for this Structure %s is not enabled".formatted(structure.getName()))
                    );
                } else {
                    return CompletableFuture.completedFuture(null);
                }
            }else{
                return CompletableFuture.failedFuture(new IllegalArgumentException("Participant with a TenantId is required when MultiTenancyType is SHARED"));
            }
        }else if(ObjectUtils.isNotEmpty(context.getTenantSelection())){
            // This check is here since continuum will allow any published service to be called.
            // So someone could call the admin service even though it is not enabled for this Structure
            // Multitenant access can only be enabled if MultiTenancyType.SHARED
            return CompletableFuture.failedFuture(
                    new IllegalArgumentException("Multi-tenant access for this Structure %s is not enabled".formatted(structure.getName()))
            );
        }else{
            return CompletableFuture.completedFuture(null);
        }
    }

    private CompletableFuture<List<MultiGetOperation>> validate_ComposeIds_AddTenantsToContext(final List<TenantSpecificId> ids, EntityContext entityContext){
        if(structure.getMultiTenancyType() == MultiTenancyType.SHARED
                && structure.isMultiTenantSelectionEnabled()){

            List<MultiGetOperation> ret = new ArrayList<>(ids.size());
            List<String> tenants = new ArrayList<>(ids.size());
            for(TenantSpecificId id : ids){
                MultiGetOperation.Builder builder = new MultiGetOperation.Builder();
                builder.index(structure.getItemIndex())
                       .id(id.getTenantId() + "-" + id.getEntityId())
                       .routing(id.getTenantId());

                ret.add(builder.build());
                tenants.add(id.getTenantId());
            }

            entityContext.setTenantSelection(tenants);
            return CompletableFuture.completedFuture(ret);
        }else{
            return CompletableFuture.failedFuture(
                    new IllegalArgumentException("Multi-tenant access for this Structure %s is not enabled".formatted(structure.getName()))
            );
        }
    }

}
