package org.kinotic.structures.api.services.security;

import org.kinotic.continuum.idl.api.schema.FunctionDefinition;
import org.kinotic.structures.api.domain.NamedQueryOperation;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.EntityOperation;

import java.util.concurrent.CompletableFuture;


/**
 * Factory for creating AuthorizationService instances.
 * Created by Navíd Mitchell 🤪on 1/30/25.
 */
public interface AuthorizationServiceFactory {

    /**
     * Creates an AuthorizationService for the given structure
     * @param structure to create the AuthorizationService for
     * @return a CompletableFuture that will complete with the AuthorizationService
     */
    CompletableFuture<AuthorizationService<EntityOperation>> createStructureAuthorizationService(Structure structure);

    /**
     * Creates an AuthorizationService for the given named query
     * @param namedQuery to create the AuthorizationService for
     * @return a CompletableFuture that will complete with the AuthorizationService
     */
    CompletableFuture<AuthorizationService<NamedQueryOperation>> createNamedQueryAuthorizationService(FunctionDefinition namedQuery);

}
