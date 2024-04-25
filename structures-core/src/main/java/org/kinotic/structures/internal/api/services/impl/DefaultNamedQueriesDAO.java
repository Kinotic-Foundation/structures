package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.NamedQueryServiceDefinition;
import org.kinotic.structures.internal.api.services.NamedQueriesDAO;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/23/24.
 */
@Component
public class DefaultNamedQueriesDAO extends AbstractCrudService<NamedQueryServiceDefinition> implements NamedQueriesDAO {

    public DefaultNamedQueriesDAO(ElasticsearchAsyncClient esAsyncClient,
                                  ReactiveElasticsearchOperations esOperations,
                                  CrudServiceTemplate crudServiceTemplate) {
        super("namedQueryServiceDefinition",
              NamedQueryServiceDefinition.class,
              esAsyncClient,
              esOperations,
              crudServiceTemplate);
    }

    @Override
    public CompletableFuture<Page<NamedQueryServiceDefinition>> search(String searchText, Pageable pageable) {
        return crudServiceTemplate.search(indexName,
                                          pageable,
                                          NamedQueryServiceDefinition.class,
                                          builder -> builder.q(searchText));
    }
}
