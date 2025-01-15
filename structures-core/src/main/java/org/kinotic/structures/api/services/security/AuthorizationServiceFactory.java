package org.kinotic.structures.api.services.security;

import org.kinotic.continuum.idl.api.schema.FunctionDefinition;
import org.kinotic.structures.api.domain.NamedQueryOperation;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.EntityOperation;

import java.util.concurrent.CompletableFuture;


public interface AuthorizationServiceFactory {

    CompletableFuture<AuthorizationService<EntityOperation>> createStructureAuthorizationService(Structure structure);

    CompletableFuture<AuthorizationService<NamedQueryOperation>> createNamedQueryAuthorizationService(FunctionDefinition namedQuery);

}
