package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.services.StructureDAO;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪on 6/25/23.
 */
@Component
public class DefaultStructureDAO extends AbstractCrudService<Structure> implements StructureDAO {

    public DefaultStructureDAO(ElasticsearchAsyncClient esAsyncClient,
                               CrudServiceTemplate crudServiceTemplate) {
        super("struct_structure",
              Structure.class,
              esAsyncClient,
              crudServiceTemplate);
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
    public CompletableFuture<Long> countForProject(String projectId) {
        return crudServiceTemplate.count(indexName, builder -> builder
                .query(q -> q
                        .bool(b -> b
                                .filter(TermQuery.of(tq -> tq.field("projectId").value(projectId))._toQuery())
                        )));
    }

    @Override
    public CompletableFuture<Page<Structure>> findAllPublishedForApplication(String applicationId, Pageable pageable) {
        return crudServiceTemplate.search(indexName, pageable, type, builder -> builder
                .query(q -> q
                        .bool(b -> b
                                .filter(TermQuery.of(tq -> tq.field("applicationId").value(applicationId))._toQuery(),
                                        TermQuery.of(tq -> tq.field("published").value(true))._toQuery())
                        )
                ));
    }

    @Override
    public CompletableFuture<Page<Structure>> findAllForApplication(String applicationId, Pageable pageable) {
        return crudServiceTemplate.search(indexName, pageable, type, builder -> builder
                .query(q -> q
                        .bool(b -> b.filter(TermQuery.of(tq -> tq.field("applicationId").value(applicationId))._toQuery())
                        )
                ));
    }

    @Override
    public CompletableFuture<Page<Structure>> findAllForProject(String projectId, Pageable pageable) {
        return crudServiceTemplate.search(indexName, pageable, type, builder -> builder
                .query(q -> q
                        .bool(b -> b.filter(TermQuery.of(tq -> tq.field("projectId").value(projectId))._toQuery())
                        )
                ));
    }

    @Override
    public CompletableFuture<Page<Structure>> search(String searchText, Pageable pageable) {
        return crudServiceTemplate.search(indexName,
                                          pageable,
                                          Structure.class,
                                          builder -> builder.q(searchText));
    }

    @Override
    public CompletableFuture<Void> syncIndex() {
        return esAsyncClient.indices()
                            .refresh(b -> b.index(indexName))
                            .thenApply(unused -> null);
    }
}
