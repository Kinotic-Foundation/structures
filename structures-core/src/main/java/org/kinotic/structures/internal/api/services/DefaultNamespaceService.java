package org.kinotic.structures.internal.api.services;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import org.kinotic.structures.api.domain.Namespace;
import org.kinotic.structures.api.services.NamespaceService;
import org.kinotic.structures.api.services.StructureService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/25/23.
 */
@Component
public class DefaultNamespaceService extends AbstractCrudService<Namespace> implements NamespaceService {

    private final StructureService structureService;

    public DefaultNamespaceService(ElasticsearchAsyncClient esAsyncClient,
                                   StructureService structureService) {
        super("namespace", Namespace.class, esAsyncClient);
        this.structureService = structureService;
    }

    @PostConstruct
    public void init(){
        createIndex("namespace", false, builder -> builder
            .mappings(m -> {
                // FIXME: add mapping
                return m;
            }));
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
}
