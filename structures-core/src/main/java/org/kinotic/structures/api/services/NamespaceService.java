package org.kinotic.structures.api.services;

import org.elasticsearch.search.SearchHits;
import org.kinotic.structures.api.domain.AlreadyExistsException;
import org.kinotic.structures.api.domain.Namespace;

import java.io.IOException;
import java.util.Optional;

public interface NamespaceService {
    Namespace save(Namespace namespace) throws AlreadyExistsException, IOException;

    Optional<Namespace> getNamespace(String namespace) throws IOException;

    SearchHits getAll(int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException;

    SearchHits getAllNamespaceLike(String namespaceLike, int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException;

    void delete(String namespace) throws IOException;

    void createNamespaceIndex();
}
