package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.util.BinaryData;
import co.elastic.clients.util.ContentType;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.decorators.UpsertEntityPreProcessor;
import org.kinotic.structures.internal.api.services.EntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.nio.ByteBuffer;
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
    public CompletableFuture<ByteBuffer> save(ByteBuffer entity) {
        return upsertEntityPreProcessor.process(entity)
                .thenCompose(rawEntity -> {
                    BinaryData binaryData = BinaryData.of(rawEntity.getData(), ContentType.APPLICATION_JSON);
                    return esAsyncClient.index(i -> i
                            .index(structure.getItemIndex())
                            .id(rawEntity.getId())
                            .document(binaryData)
                            .refresh(Refresh.True))
                            .thenApply(indexResponse -> binaryData.asByteBuffer());
                });
    }

    @Override
    public CompletableFuture<ByteBuffer> findById(String id) {
        return crudServiceTemplate.findById(structure.getItemIndex(), BinaryData.class, id, null)
                                  .thenApply(binaryData -> {
                                      return binaryData.asByteBuffer();
                                  });
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
    public CompletableFuture<Page<ByteBuffer>> findAll(Pageable pageable) {
        return crudServiceTemplate.findAll(structure.getItemIndex(), BinaryData.class, pageable, null)
                                  .thenApply(page -> page.map(BinaryData::asByteBuffer));
    }

    @Override
    public CompletableFuture<Page<ByteBuffer>> search(String searchText, Pageable pageable) {
        return null;
    }

}
