package org.kinotic.structures.api.services;

import org.kinotic.continuum.api.annotations.Publish;
import org.kinotic.continuum.core.api.crud.IdentifiableCrudService;
import org.kinotic.structures.api.domain.NamedQueriesDefinition;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 4/23/24.
 */
@Publish
public interface NamedQueriesService extends IdentifiableCrudService<NamedQueriesDefinition, String> {

    CompletableFuture<NamedQueriesDefinition> findByNamespaceAndStructure(String namespace, String structure);
}
