package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.util.BinaryData;
import co.elastic.clients.util.ContentType;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.decorators.UpsertEntityPreProcessor;
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
    private final UpsertEntityPreProcessor upsertEntityPreProcessor;

    public DefaultEntityService(Structure structure,
                                ElasticsearchAsyncClient esAsyncClient,
                                CrudServiceTemplate crudServiceTemplate,
                                UpsertEntityPreProcessor upsertEntityPreProcessor) {
        this.structure = structure;
        this.esAsyncClient = esAsyncClient;
        this.crudServiceTemplate = crudServiceTemplate;
        this.upsertEntityPreProcessor = upsertEntityPreProcessor;
    }

    @Override
    public CompletableFuture<RawJson> save(RawJson entity) {
        return upsertEntityPreProcessor.process(entity.data())
                .thenCompose(rawEntity -> {
                    BinaryData binaryData = BinaryData.of(rawEntity.getData(), ContentType.APPLICATION_JSON);
                    return esAsyncClient.index(i -> i
                            .index(structure.getItemIndex())
                            .id(rawEntity.getId())
                            .document(binaryData)
                            .refresh(Refresh.True))
                            .thenApply(indexResponse -> new RawJson(rawEntity.getData()));
                });
    }

    @Override
    public CompletableFuture<RawJson> findById(String id) {
        return crudServiceTemplate.findById(structure.getItemIndex(), id, RawJson.class, null);
    }

    @Override
    public CompletableFuture<Long> count() {
        return crudServiceTemplate.count(structure.getItemIndex(), null);
    }

    @Override
    public CompletableFuture<Void> deleteById(String id) {
        return crudServiceTemplate.deleteById(structure.getItemIndex(), id, null)
                                  .thenApply(deleteResponse -> null);
    }

    @Override
    public CompletableFuture<Page<RawJson>> findAll(Pageable pageable) {
        return crudServiceTemplate.search(structure.getItemIndex(), pageable, RawJson.class, null);
    }

    @Override
    public CompletableFuture<Page<RawJson>> search(String searchText, Pageable pageable) {
        return crudServiceTemplate.search(structure.getItemIndex(),
                                          pageable,
                                          RawJson.class,
                                          b -> b.query(q -> {
                                             q.queryString(qs -> {
                                                 qs.query(searchText);
                                                 return qs;
                                             });
                                              return q;
                                          }));
    }

}
