package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.util.BinaryData;
import co.elastic.clients.util.ContentType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.NotImplementedException;
import org.kinotic.continuum.core.api.crud.CursorPage;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.decorators.MultiTenancyType;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.decorators.DelegatingReadPreProcessor;
import org.kinotic.structures.internal.api.decorators.DelegatingUpsertPreProcessor;
import org.kinotic.structures.internal.api.decorators.EntityHolder;
import org.kinotic.structures.internal.api.services.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Navíd Mitchell 🤪 on 5/2/23.
 */
public class DefaultEntityService implements EntityService {

    private static final Logger log = LoggerFactory.getLogger(DefaultEntityService.class);

    private final Structure structure;
    private final StructuresProperties structuresProperties;
    private final ObjectMapper objectMapper;
    private final ElasticsearchAsyncClient esAsyncClient;
    private final CrudServiceTemplate crudServiceTemplate;
    private final DelegatingUpsertPreProcessor delegatingUpsertPreProcessor;
    private final DelegatingReadPreProcessor delegatingReadPreProcessor;


    public DefaultEntityService(Structure structure,
                                StructuresProperties structuresProperties,
                                ObjectMapper objectMapper,
                                ElasticsearchAsyncClient esAsyncClient,
                                CrudServiceTemplate crudServiceTemplate,
                                DelegatingUpsertPreProcessor delegatingUpsertPreProcessor,
                                DelegatingReadPreProcessor delegatingReadPreProcessor) {
        this.structure = structure;
        this.structuresProperties = structuresProperties;
        this.objectMapper = objectMapper;
        this.esAsyncClient = esAsyncClient;
        this.crudServiceTemplate = crudServiceTemplate;
        this.delegatingUpsertPreProcessor = delegatingUpsertPreProcessor;
        this.delegatingReadPreProcessor = delegatingReadPreProcessor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> CompletableFuture<T> save(T entity, EntityContext context) {
        return doPersist(entity, context, entityHolder -> {

            String routing = (structure.getMultiTenancyType() == MultiTenancyType.SHARED)
                    ? context.getParticipant().getTenantId()
                    : null;

            // This is a bit of a hack since the BinaryData type does not work properly to retrieve complex objects, but does work to store them.
            // https://github.com/elastic/elasticsearch-java/issues/574
            if(entityHolder.getEntity() instanceof RawJson){
                RawJson rawEntity = (RawJson) entityHolder.getEntity();
                BinaryData binaryData = BinaryData.of(rawEntity.data(), ContentType.APPLICATION_JSON);
                return esAsyncClient.index(i -> i
                                            .routing(routing)
                                            .index(structure.getItemIndex())
                                            .id(entityHolder.getId())
                                            .document(binaryData)
                                            .refresh(Refresh.True))
                                    .thenApply(indexResponse -> (T) rawEntity);
            }else{
                return esAsyncClient.index(i -> i
                                            .routing(routing)
                                            .index(structure.getItemIndex())
                                            .id(entityHolder.getId())
                                            .document(entityHolder.getEntity())
                                            .refresh(Refresh.True))
                                    .thenApply(indexResponse -> (T) entityHolder.getEntity());
            }
        });
    }

    @Override
    public <T> CompletableFuture<Void> bulkSave(T entities, EntityContext context) {
        return doBulkPersist(entities,
                             context,
                             entityHolder -> BulkOperation.of(b -> b
                                     .index(idx -> idx.index(structure.getItemIndex())
                                                      .id(entityHolder.getId())
                                                      .document(entityHolder.getEntity())
                                     )));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> CompletableFuture<T> update(T entity, EntityContext context) {
        return doPersist(entity, context, entityHolder -> {

            String routing = (structure.getMultiTenancyType() == MultiTenancyType.SHARED)
                    ? context.getParticipant().getTenantId()
                    : null;

            return esAsyncClient.update(UpdateRequest.of(u -> u
                                        .routing(routing)
                                        .index(structure.getItemIndex())
                                        .id(entityHolder.getId())
                                        .doc(entityHolder.getEntity())
                                        .docAsUpsert(true)
                                        .refresh(Refresh.True)), entityHolder.getEntity().getClass())
                                .thenApply(updateResponse -> (T) entityHolder.getEntity());
        });
    }

    @Override
    public <T> CompletableFuture<Void> bulkUpdate(T entities, EntityContext context) {
        return doBulkPersist(entities,
                             context,
                             entityHolder -> BulkOperation.of(b -> b
                                     .update(u -> u
                                             .index(structure.getItemIndex())
                                             .id(entityHolder.getId())
                                             .action(upB -> upB.doc(entityHolder.getEntity())
                                                               .docAsUpsert(true)
                                                               .detectNoop(true))
                                     )));
    }

    @SuppressWarnings("unchecked")
    private <T> CompletableFuture<T> doPersist(T entity,
                                               EntityContext context,
                                               Function<EntityHolder, CompletableFuture<T>> persistLogic){
        return validateTenant(context)
                .thenCompose(unused -> (CompletableFuture<T>) delegatingUpsertPreProcessor
                        .process(entity, context)
                        .thenCompose(entityHolder -> {

                            if(entityHolder.getId() == null || entityHolder.getId().isEmpty()){
                                return CompletableFuture.failedFuture(new IllegalArgumentException("Entity must have an id"));
                            }

                            return persistLogic.apply(entityHolder)
                                               .thenApply(response -> entityHolder.getEntity());
                        }));
    }

    private <T> CompletableFuture<Void> doBulkPersist(T entities,
                                                      EntityContext context,
                                                      Function<EntityHolder, BulkOperation> persistLogic){
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
                            for(EntityHolder entityHolder : list){

                                if(entityHolder.getId() == null || entityHolder.getId().isEmpty()){
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


    @Override
    public <T> CompletableFuture<T> findById(String id, Class<T> type, EntityContext context) {
        return validateTenantAndComposeId(id, context)
                .thenCompose(composedId -> crudServiceTemplate
                        .findById(structure.getItemIndex(), composedId, type,
                                  builder -> delegatingReadPreProcessor.beforeFindById(structure, builder, context)));
    }

    @Override
    public <T> CompletableFuture<List<T>> findByIds(List<String> ids, Class<T> type, EntityContext context) {
        return validateTenantAndComposeIds(ids, context)
                .thenCompose(composedIds -> crudServiceTemplate
                        .findByIds(structure.getItemIndex(), composedIds, type,
                                builder -> delegatingReadPreProcessor.beforeFindByIds(structure, builder, context)));
    }

    @Override
    public CompletableFuture<Long> count(EntityContext context) {
        return validateTenant(context)
                .thenCompose(unused -> crudServiceTemplate
                        .count(structure.getItemIndex(),
                               builder -> delegatingReadPreProcessor.beforeCount(structure, null, builder, context)));
    }

    @Override
    public CompletableFuture<Long> countByQuery(String query, EntityContext context) {
        return validateTenant(context)
                .thenCompose(unused -> crudServiceTemplate
                        .count(structure.getItemIndex(),
                                builder -> delegatingReadPreProcessor.beforeCount(structure, query, builder, context)));
    }

    @Override
    public CompletableFuture<Void> deleteById(String id, EntityContext context) {
        return validateTenantAndComposeId(id, context)
                .thenCompose(composedId -> crudServiceTemplate
                        .deleteById(structure.getItemIndex(), composedId,
                                    builder -> delegatingReadPreProcessor.beforeDelete(structure, builder, context))
                        .thenApply(deleteResponse -> null));
    }

    @Override
    public CompletableFuture<Void> deleteByQuery(String query, EntityContext context) {
        return validateTenant(context)
                .thenCompose(unused -> crudServiceTemplate
                        .deleteByQuery(structure.getItemIndex(),
                                builder -> delegatingReadPreProcessor.beforeDeleteByQuery(structure, query, builder, context))
                        .thenApply(deleteResponse -> null));
    }

    @Override
    public <T> CompletableFuture<Page<T>> findAll(Pageable pageable, Class<T> type, EntityContext context) {
        return validateTenant(context)
                .thenCompose(unused -> crudServiceTemplate
                        .search(structure.getItemIndex(), pageable, type,
                                builder -> delegatingReadPreProcessor.beforeFindAll(structure, builder, context))
                        .thenApply(createParanoidCheck(type, context, "Find All")));
    }

    @Override
    public <T> CompletableFuture<Page<T>> search(String searchText, Pageable pageable, Class<T> type, EntityContext context) {
        return validateTenant(context)
                .thenCompose(unused -> crudServiceTemplate
                        .search(structure.getItemIndex(),
                                pageable,
                                type,
                                builder -> {

                                    Query.Builder queryBuilder = new Query.Builder();

                                    delegatingReadPreProcessor.beforeSearch(structure, searchText, builder, queryBuilder, context);

                                    builder.query(queryBuilder.build());
                                })
                        .thenApply(createParanoidCheck(type, context, "Search")));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T> Function<Page<T>, Page<T>> createParanoidCheck(Class<T> type, EntityContext context, String what){
        return page -> {
            // This is a temporary bit of code to make sure multi tenancy is working properly
            if(structure.getMultiTenancyType() == MultiTenancyType.SHARED){
                List<Object> result = new ArrayList<>(page.getContent().size());
                if(RawJson.class.isAssignableFrom(type)){
                    for(RawJson rawJson : (List<RawJson>)page.getContent()){
                        try {
                            Map converted = objectMapper.readValue(rawJson.data(), Map.class);
                            String tenant = (String) converted.get(structuresProperties.getTenantIdFieldName());
                            if(tenant != null && tenant.equals(context.getParticipant().getTenantId())){
                                result.add(rawJson);
                            }else{
                                log.error("{} Multi tenancy is not working properly for structure: {} and tenant: {}\nData:\n{}",
                                          what,
                                          structure,
                                          context.getParticipant().getTenantId(),
                                          converted);
                            }
                        } catch (IOException e) {
                            throw new IllegalStateException("RawJson could not be deserialized for sanity check",e);
                        }
                    }
                }else if(Map.class.isAssignableFrom(type)){
                    List<Map> content = (List<Map>)page.getContent();
                    for(Map map : content){
                        String tenant = (String) map.get(structuresProperties.getTenantIdFieldName());
                        if(tenant != null && tenant.equals(context.getParticipant().getTenantId())){
                            result.add(map);
                        }else{
                            log.error("Multi tenancy is not working properly for structure: {} and tenant: {}\nData:\n{}",
                                      structure,
                                      context.getParticipant().getTenantId(),
                                      map);
                        }
                    }
                }else{
                    throw new NotImplementedException("Pojo Multi tenancy check is not implemented yet");
                }

                if(page instanceof CursorPage){
                    return (Page<T>) new CursorPage<>(result, ((CursorPage) page).getCursor(), page.getTotalElements());
                }else{
                    return (Page<T>) new Page<>(result, page.getTotalElements());
                }

            }else{
                return page;
            }
        };
    }

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

}
