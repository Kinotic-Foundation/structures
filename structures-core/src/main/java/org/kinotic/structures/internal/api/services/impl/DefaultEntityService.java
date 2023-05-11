package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.util.BinaryData;
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
    public CompletableFuture<BinaryData> save(BinaryData entity) {
        return upsertEntityPreProcessor.process(entity.asByteBuffer().array())
                .thenCompose(rawEntity -> {
                    BinaryData binaryData = BinaryData.of(rawEntity.getData());
                    return esAsyncClient.index(i -> i
                            .index(structure.getItemIndex())
                            .id(rawEntity.getId())
                            .document(rawEntity.getData()))
                            .thenApply(indexResponse -> binaryData);
                });
    }

    @Override
    public CompletableFuture<BinaryData> findById(String s) {
        return null;
    }

    @Override
    public CompletableFuture<Long> count() {
        return null;
    }

    @Override
    public CompletableFuture<Void> deleteById(String id) {
        return crudServiceTemplate.deleteById(structure.getItemIndex(), id, null)
                                  .thenApply(deleteResponse -> null);
    }

    @Override
    public CompletableFuture<Page<BinaryData>> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public CompletableFuture<Page<BinaryData>> search(String searchText, Pageable pageable) {
        return null;
    }

}
