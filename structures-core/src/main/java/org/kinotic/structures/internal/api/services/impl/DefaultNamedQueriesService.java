package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.NamedQueriesDefinition;
import org.kinotic.structures.api.services.NamedQueriesService;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/23/24.
 */
@Component
public class DefaultNamedQueriesService extends AbstractCrudService<NamedQueriesDefinition> implements NamedQueriesService {

    public DefaultNamedQueriesService(ElasticsearchAsyncClient esAsyncClient,
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
        // TODO: This should not depend on the id format in the future
        return this.findById((namespace + "." + structure).toLowerCase());
    }

    @Override
    public CompletableFuture<Page<NamedQueriesDefinition>> search(String searchText, Pageable pageable) {
        return crudServiceTemplate.search(indexName,
                                          pageable,
                                          NamedQueriesDefinition.class,
                                          builder -> builder.q(searchText));
    }

}
