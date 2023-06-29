package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.util.BinaryData;
import co.elastic.clients.util.ContentType;
import org.kinotic.structures.api.decorators.MultiTenancyType;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.decorators.DelegatingReadPreProcessor;
import org.kinotic.structures.internal.api.decorators.DelegatingUpsertPreProcessor;
import org.kinotic.structures.internal.api.decorators.EntityHolder;
import org.kinotic.structures.internal.api.services.EntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 5/2/23.
 */
public class DefaultEntityService implements EntityService {

    private final Structure structure;
    private final ElasticsearchAsyncClient esAsyncClient;
    private final CrudServiceTemplate crudServiceTemplate;
    private final DelegatingUpsertPreProcessor delegatingUpsertPreProcessor;
    private final DelegatingReadPreProcessor delegatingReadPreProcessor;

    public DefaultEntityService(Structure structure,
                                ElasticsearchAsyncClient esAsyncClient,
                                CrudServiceTemplate crudServiceTemplate,
                                DelegatingUpsertPreProcessor delegatingUpsertPreProcessor,
                                DelegatingReadPreProcessor delegatingReadPreProcessor) {
        this.structure = structure;
        this.esAsyncClient = esAsyncClient;
        this.crudServiceTemplate = crudServiceTemplate;
        this.delegatingUpsertPreProcessor = delegatingUpsertPreProcessor;
        this.delegatingReadPreProcessor = delegatingReadPreProcessor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> CompletableFuture<T> save(T entity, EntityContext context) {
        return validateTenant(context)
                .thenCompose(unused -> (CompletableFuture<T>) delegatingUpsertPreProcessor
                        .process(entity, context)
                        .thenCompose(entityHolder -> {

                            if(entityHolder.getId() == null || entityHolder.getId().isEmpty()){
                                return CompletableFuture.failedFuture(new IllegalArgumentException("Entity must have an id"));
                            }

                            String routing = (structure.getMultiTenancyType() == MultiTenancyType.SHARED)
                                    ? context.getParticipant().getTenantId()
                                    : null;

                            // This is a bit of a hack since the BinaryData type does not work properly to retrieve complex objects, but does work to store them.
                            // https://github.com/elastic/elasticsearch-java/issues/574
                            if(entityHolder.getEntity() instanceof RawJson){
                                RawJson rawEntity = (RawJson) entityHolder.getEntity();
                                BinaryData binaryData = BinaryData.of(rawEntity.data(), ContentType.APPLICATION_JSON);
                                return esAsyncClient.index(i -> i
                                                            .index(structure.getItemIndex())
                                                            .id(entityHolder.getId())
                                                            .routing(routing)
                                                            .document(binaryData)
                                                            .refresh(Refresh.True))
                                                    .thenApply(indexResponse -> new RawJson(rawEntity.data()));
                            }else{
                                return esAsyncClient.index(i -> i
                                                            .index(structure.getItemIndex())
                                                            .id(entityHolder.getId())
                                                            .routing(routing)
                                                            .document(entityHolder.getEntity())
                                                            .refresh(Refresh.True))
                                                    .thenApply(indexResponse -> entityHolder.getEntity());
                            }
                        }));
    }

    @Override
    public <T> CompletableFuture<Void> bulkSave(T entities, EntityContext context) {
        return validateTenant(context)
                .thenCompose(unused -> delegatingUpsertPreProcessor
                        .processArray(entities, context)
                        .thenCompose(list -> {
                                String routing = (structure.getMultiTenancyType() == MultiTenancyType.SHARED)
                                        ? context.getParticipant().getTenantId()
                                        : null;

                                BulkRequest.Builder br = new BulkRequest.Builder();
                                br.routing(routing);

                                for(EntityHolder<?> entityHolder : list){

                                    if(entityHolder.getId() == null || entityHolder.getId().isEmpty()){
                                        return CompletableFuture.failedFuture(new IllegalArgumentException("All Entities must have an id"));
                                    }

                                    Object data;
                                    if(entityHolder.getEntity() instanceof RawJson){
                                        RawJson rawEntity = (RawJson) entityHolder.getEntity();
                                        data = BinaryData.of(rawEntity.data(), ContentType.APPLICATION_JSON);
                                    }else{
                                        data = entityHolder.getEntity();
                                    }

                                    br.operations(op -> op
                                            .index(idx -> idx
                                                    .index(structure.getItemIndex())
                                                    .id(entityHolder.getId())
                                                    .document(data)
                                            ));
                                }

                                return esAsyncClient.bulk(br.build()).thenCompose(bulkResponse -> {
                                    if(bulkResponse.errors()){
                                        return CompletableFuture.failedFuture(new RuntimeException("Bulk save failed with errors"));
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
    public CompletableFuture<Long> count(EntityContext context) {
        return validateTenant(context)
                .thenCompose(unused -> crudServiceTemplate
                        .count(structure.getItemIndex(),
                               builder -> delegatingReadPreProcessor.beforeCount(structure, builder, context)));
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
    public <T> CompletableFuture<Page<T>> findAll(Pageable pageable, Class<T> type, EntityContext context) {
        return validateTenant(context)
                .thenCompose(unused -> crudServiceTemplate
                        .search(structure.getItemIndex(), pageable, type,
                                builder -> delegatingReadPreProcessor.beforeFindAll(structure, builder, context)));
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

                                    delegatingReadPreProcessor.beforeSearch(structure, builder, queryBuilder, context);

                                    builder.q(searchText);

                                    builder.query(queryBuilder.build());
                                }));
    }

    private CompletableFuture<String> validateTenantAndComposeId(final String id, final EntityContext context){
        return validateTenant(context)
                .thenApply(unused -> {
                    String ret;
                    if(structure.getMultiTenancyType() == MultiTenancyType.SHARED){
                        String tenantId = context.getParticipant().getTenantId();
                        if(!id.startsWith(tenantId)){
                            ret = tenantId + "-" + id;
                        }else{
                            ret = id;
                        }
                    }else{
                        ret = id;
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
