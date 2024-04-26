package org.kinotic.structures.internal.api.services.impl;

import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.NamedQueriesDefinition;
import org.kinotic.structures.api.services.NamedQueriesService;
import org.kinotic.structures.internal.api.services.NamedQueriesDAO;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/23/24.
 */
@Component
public class DefaultNamedQueriesService implements NamedQueriesService {

    private final NamedQueriesDAO namedQueriesDAO;

    public DefaultNamedQueriesService(NamedQueriesDAO namedQueriesDAO) {
        this.namedQueriesDAO = namedQueriesDAO;
    }

    @Override
    public CompletableFuture<NamedQueriesDefinition> save(NamedQueriesDefinition entity) {
        return null;
    }

    @Override
    public CompletableFuture<NamedQueriesDefinition> findById(String id) {
        return namedQueriesDAO.findById(id);
    }

    @Override
    public CompletableFuture<Long> count() {
        return namedQueriesDAO.count();
    }

    @Override
    public CompletableFuture<Void> deleteById(String id) {
        return namedQueriesDAO.deleteById(id);
    }

    @Override
    public CompletableFuture<Page<NamedQueriesDefinition>> findAll(Pageable pageable) {
        return namedQueriesDAO.findAll(pageable);
    }

    @Override
    public CompletableFuture<Page<NamedQueriesDefinition>> search(String searchText, Pageable pageable) {
        return namedQueriesDAO.search(searchText, pageable);
    }
}
