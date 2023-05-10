package org.kinotic.structures.internal.api.services;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import org.kinotic.structures.api.domain.Namespace;
import org.kinotic.structures.api.services.NamespaceService;
import org.kinotic.structures.api.services.StructureService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class DefaultNamespaceService extends AbstractCrudService<Namespace> implements NamespaceService {

    private final StructureService structureService;

    public DefaultNamespaceService(ElasticsearchAsyncClient esAsyncClient,
                                   ReactiveElasticsearchOperations esOperations,
                                   StructureService structureService) {
        super("namespace", Namespace.class, esAsyncClient, esOperations);
        this.structureService = structureService;
    }

    @Override
    public CompletableFuture<Namespace> save(Namespace entity) {
        entity.setUpdated(System.currentTimeMillis());
        return super.save(entity);
    }

    @Override
    public CompletableFuture<Void> deleteById(String id) {
        return structureService.countForNamespace(id).thenAccept(count -> {
            if(count > 0){
                throw new IllegalStateException("Cannot delete namespace with structures in it.");
            }
        }).thenCompose(v -> super.deleteById(id));
    }

    @Override
    public CompletableFuture<Page<Namespace>> search(String searchText, Pageable pageable) {
        return null;
    }
}
