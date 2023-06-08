package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.util.BinaryData;
import co.elastic.clients.util.ContentType;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.decorators.DelegatingUpsertPreProcessor;
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

    public DefaultEntityService(Structure structure,
                                ElasticsearchAsyncClient esAsyncClient,
                                CrudServiceTemplate crudServiceTemplate,
                                DelegatingUpsertPreProcessor delegatingUpsertPreProcessor) {
        this.structure = structure;
        this.esAsyncClient = esAsyncClient;
        this.crudServiceTemplate = crudServiceTemplate;
        this.delegatingUpsertPreProcessor = delegatingUpsertPreProcessor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> CompletableFuture<T> save(T entity, EntityContext context) {
        return (CompletableFuture<T>) delegatingUpsertPreProcessor
                .process(entity, context)
                .thenCompose(entityHolder -> {

                    // This is a bit of a hack since the BinaryData type does not work properly with complex objects
                    // https://github.com/elastic/elasticsearch-java/issues/574

                    if(entityHolder.getEntity() instanceof RawJson){
                        RawJson rawEntity = (RawJson) entityHolder.getEntity();
                        BinaryData binaryData = BinaryData.of(rawEntity.data(), ContentType.APPLICATION_JSON);
                        return esAsyncClient.index(i -> i
                                                    .index(structure.getItemIndex())
                                                    .id(entityHolder.getId())
                                                    .document(binaryData)
                                                    .refresh(Refresh.True))
                                            .thenApply(indexResponse -> new RawJson(rawEntity.data()));
                    }else{
                        return esAsyncClient.index(i -> i
                                                    .index(structure.getItemIndex())
                                                    .id(entityHolder.getId())
                                                    .document(entityHolder.getEntity())
                                                    .refresh(Refresh.True))
                                            .thenApply(indexResponse -> entityHolder.getEntity());
                    }
                });
    }

    @Override
    public <T> CompletableFuture<T> findById(String id, Class<T> type, EntityContext context) {
        return crudServiceTemplate.findById(structure.getItemIndex(), id, type, null);
    }

    @Override
    public CompletableFuture<Long> count(EntityContext context) {
        return crudServiceTemplate.count(structure.getItemIndex(), null);
    }

    @Override
    public CompletableFuture<Void> deleteById(String id, EntityContext context) {
        return crudServiceTemplate.deleteById(structure.getItemIndex(), id, null)
                                  .thenApply(deleteResponse -> null);
    }

    @Override
    public <T> CompletableFuture<Page<T>> findAll(Pageable pageable, Class<T> type, EntityContext context) {
        return crudServiceTemplate.search(structure.getItemIndex(), pageable, type, null);
    }

    @Override
    public <T> CompletableFuture<Page<T>> search(String searchText, Pageable pageable, Class<T> type, EntityContext context) {
        return crudServiceTemplate.search(structure.getItemIndex(),
                                          pageable,
                                          type,
                                          b -> b.query(q -> {
                                              q.queryString(qs -> {
                                                  qs.query(searchText);
                                                  return qs;
                                              });
                                              return q;
                                          }));
    }

}
