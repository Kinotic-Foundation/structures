package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.Namespace;
import org.kinotic.structures.api.services.NamespaceService;
import org.kinotic.structures.api.services.StructureService;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Component
public class DefaultNamespaceService extends AbstractCrudService<Namespace> implements NamespaceService {

    private final StructureService structureService;

    public DefaultNamespaceService(ElasticsearchAsyncClient esAsyncClient,
                                   ReactiveElasticsearchOperations esOperations,
                                   StructureService structureService,
                                   CrudServiceTemplate crudServiceTemplate) {
        super("namespace",
              Namespace.class,
              esAsyncClient,
              esOperations,
              crudServiceTemplate);
        this.structureService = structureService;
    }

    @Override
    public CompletableFuture<Namespace> createNamespaceIfNotExist(String id, String description) {
        return findById(id)
                .thenCompose(namespace -> {
                    if(namespace != null){
                        return CompletableFuture.completedFuture(namespace);
                    }else{
                        return save(new Namespace(id, description));
                    }
                });
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
    public CompletableFuture<Namespace> save(Namespace entity) {
        entity.setUpdated(new Date());
        return super.save(entity);
    }

    @Override
    public CompletableFuture<Page<Namespace>> search(String searchText, Pageable pageable) {
        return crudServiceTemplate.search(indexName,
                                          pageable,
                                          Namespace.class,
                                          builder -> builder.q(searchText));
    }

    @Override
    public CompletableFuture<Void> syncIndex() {
        return esAsyncClient.indices()
                            .refresh(b -> b.index(indexName))
                            .thenApply(unused -> null);
    }
}
