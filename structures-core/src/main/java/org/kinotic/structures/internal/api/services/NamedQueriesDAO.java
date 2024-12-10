package org.kinotic.structures.internal.api.services;

import org.kinotic.continuum.api.annotations.Publish;
import org.kinotic.continuum.core.api.crud.IdentifiableCrudService;
import org.kinotic.structures.api.domain.NamedQueriesDefinition;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/23/24.
 */
@Publish
public interface NamedQueriesDAO extends IdentifiableCrudService<NamedQueriesDefinition, String> {

    /**
     * Finds all {@link NamedQueriesDefinition} for a given namespace and structure.
     * @param namespace the name of the namespace that the structure belongs to
     * @param structure the name of the structure that this {@link NamedQueriesDefinition} is defined for
     * @return {@link CompletableFuture} with the {@link NamedQueriesDefinition} or null if not found
     */
    CompletableFuture<NamedQueriesDefinition> findByNamespaceAndStructure(String namespace, String structure);

}
