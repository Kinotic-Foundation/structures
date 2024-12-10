package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.NamedQueriesDefinition;
import org.kinotic.structures.internal.api.services.NamedQueriesDAO;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/23/24.
 */
@Component
public class DefaultNamedQueriesDAO extends AbstractCrudService<NamedQueriesDefinition> implements NamedQueriesDAO {

    public DefaultNamedQueriesDAO(ElasticsearchAsyncClient esAsyncClient,
                                  ReactiveElasticsearchOperations esOperations,
                                  CrudServiceTemplate crudServiceTemplate) {
            super("named_query_service_definition",
                  NamedQueriesDefinition.class,
                  esAsyncClient,
                  esOperations,
                  crudServiceTemplate);
    }

    @Override
    public CompletableFuture<NamedQueriesDefinition> findByNamespaceAndStructure(String namespace, String structure) {
        return crudServiceTemplate.search(indexName, Pageable.ofSize(1), type, builder -> builder
                .query(q -> q
                        .bool(b -> b
                                .filter(TermQuery.of(tq -> tq.field("namespace").value(namespace))._toQuery(),
                                        TermQuery.of(tq -> tq.field("structure").value(structure))._toQuery())
                        )
                )).thenApply(page -> page.getContent() != null && !page.getContent().isEmpty()
                        ? page.getContent().get(0)
                        : null);
    }

    @Override
    public CompletableFuture<Page<NamedQueriesDefinition>> search(String searchText, Pageable pageable) {
        return crudServiceTemplate.search(indexName,
                                          pageable,
                                          NamedQueriesDefinition.class,
                                          builder -> builder.q(searchText));
    }

}
