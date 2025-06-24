package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.Application;
import org.kinotic.structures.api.services.ApplicationService;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.internal.utils.StructuresUtil;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Component
public class DefaultApplicationService extends AbstractCrudService<Application> implements ApplicationService {

    private final StructureService structureService;

    public DefaultApplicationService(ElasticsearchAsyncClient esAsyncClient,
                                     StructureService structureService,
                                     CrudServiceTemplate crudServiceTemplate) {
        super("struct_application",
              Application.class,
              esAsyncClient,
              crudServiceTemplate);
        this.structureService = structureService;
    }

    @Override
    public CompletableFuture<Application> createApplicationIfNotExist(String id, String description) {
        return findById(id)
                .thenCompose(application -> {
                    if(application != null){
                        return CompletableFuture.completedFuture(application);
                    }else{
                        return save(new Application(id, description));
                    }
                });
    }

    @Override
    public CompletableFuture<Void> deleteById(String id) {
        return structureService.countForApplication(id).thenAccept(count -> {
            if(count > 0){
                throw new IllegalStateException("Cannot delete an application with structures in it.");
            }
        }).thenCompose(v -> super.deleteById(id));
    }

    @Override
    public CompletableFuture<Application> save(Application entity) {
        StructuresUtil.validateApplicaitonId(entity.getId());
        entity.setUpdated(new Date());
        return super.save(entity);
    }

    @Override
    public CompletableFuture<Page<Application>> search(String searchText, Pageable pageable) {
        return crudServiceTemplate.search(indexName,
                                          pageable,
                                          Application.class,
                                          builder -> builder.q(searchText));
    }

    @Override
    public CompletableFuture<Void> syncIndex() {
        return esAsyncClient.indices()
                            .refresh(b -> b.index(indexName))
                            .thenApply(unused -> null);
    }
}
