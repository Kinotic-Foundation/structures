package org.kinotic.structuresserver.namespace

import org.elasticsearch.search.SearchHits
import org.kinotic.structures.api.domain.AlreadyExistsException
import org.kinotic.structures.api.domain.Namespace
import org.kinotic.structures.api.services.NamespaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class NamespaceManager implements INamespaceManager {

    @Autowired
    NamespaceService namespaceService

    @Override
    Namespace save(Namespace namespace) throws AlreadyExistsException, IOException {
        namespaceService.save(namespace)
    }

    @Override
    Optional<Namespace> getNamespace(String namespace) throws IOException {
        namespaceService.getNamespace(namespace)
    }

    @Override
    SearchHits getAll(int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException {
        namespaceService.getAll(numberPerPage, page, columnToSortBy, descending)
    }

    @Override
    SearchHits getAllNamespaceLike(String namespaceLike, int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException {
        namespaceService.getAllNamespaceLike(namespaceLike, numberPerPage, page, columnToSortBy, descending)
    }

    @Override
    void delete(String namespace) throws IOException {
        namespaceService.delete(namespace)
    }
}
