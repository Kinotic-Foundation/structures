package org.kinotic.structures.internal.api.services.impl;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.Validate;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.Project;
import org.kinotic.structures.api.services.ProjectService;
import org.kinotic.structures.api.services.StructureService;
import org.springframework.stereotype.Component;

import com.github.slugify.Slugify;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;

@Component
public class DefaultProjectService extends AbstractCrudService<Project> implements ProjectService {

    private final StructureService structureService;
    final Slugify slg = Slugify.builder().underscoreSeparator(true).build();

    public DefaultProjectService(CrudServiceTemplate crudServiceTemplate, 
                                 ElasticsearchAsyncClient esAsyncClient,
                                 StructureService structureService) {
        super("struct_project", 
              Project.class, 
              esAsyncClient, 
              crudServiceTemplate);
        this.structureService = structureService;
    }

    @Override
    public CompletableFuture<Long> countForApplication(String applicationId) {
        return crudServiceTemplate.count(indexName, builder -> builder
        .query(q -> q
                .bool(b -> b
                        .filter(TermQuery.of(tq -> tq.field("applicationId").value(applicationId))._toQuery()
                        )
                )));
    }

    @Override
    public CompletableFuture<Project> createProjectIfNotExist(Project project) {
        Validate.notNull(project, "Project cannot be null");
        Validate.notNull(project.getName(), "Project name cannot be null");
        Validate.notNull(project.getApplicationId(), "Project applicationId cannot be null");

        return findById(project.getId())
                .thenCompose(existing -> {
                    if(existing != null){
                        return CompletableFuture.completedFuture(existing);
                    }else{
                        return save(project);
                    }
                });
    }

    @Override
    public CompletableFuture<Void> deleteById(String id) {
        return structureService.countForProject(id).thenAccept(count -> {
            if(count > 0){
                throw new IllegalStateException("Cannot delete application with structures in it.");
            }
        }).thenCompose(v -> super.deleteById(id));
    }


    @Override
    public CompletableFuture<Page<Project>> findAllForApplication(String applicationId, Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAllForApplication'");
    }

    @Override
    public CompletableFuture<Project> save(Project entity) {
        Validate.notNull(entity, "Project cannot be null");
        Validate.notNull(entity.getApplicationId(), "Project applicationId cannot be null");
        Validate.notNull(entity.getName(), "Project name cannot be null");

        if(entity.getId() == null){
            entity.setId(entity.getApplicationId()+"_"+slg.slugify(entity.getName()));
        }

        entity.setUpdated(new Date());
        return super.save(entity);
    }

    @Override
    public CompletableFuture<Page<Project>> search(String searchText, Pageable pageable) {
        return crudServiceTemplate.search(indexName,
                                          pageable,
                                          Project.class,
                                          builder -> builder.q(searchText));
    }

    @Override
    public CompletableFuture<Void> syncIndex() {
        return esAsyncClient.indices()
        .refresh(b -> b.index(indexName))
        .thenApply(unused -> null);
    }
}
