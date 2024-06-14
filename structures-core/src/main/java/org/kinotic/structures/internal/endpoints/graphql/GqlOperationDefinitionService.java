package org.kinotic.structures.internal.endpoints.graphql;

import org.kinotic.structures.api.domain.Structure;

import java.util.List;

/**
 * This service provides all the {@link GqlOperationDefinition} available
 * Created by Navíd Mitchell 🤪 on 12/14/23.
 */
public interface GqlOperationDefinitionService {

    /**
     * Evicts the cache for a given {@link Structure}
     * @param structure to evict the cache for
     */
    void evictCachesFor(Structure structure);

    /**
     * Finds a {@link GqlOperationExecutionFunction} by its operation name
     *
     * @param operationName to find the {@link GqlOperationExecutionFunction} for
     * @return the {@link GqlOperationExecutionFunction} or null if not found
     */
    GqlOperationExecutionFunction findOperationExecutionFunction(String operationName);

    /**
     * Returns the built-in operations that are always available, such as findById, findAll, etc...
     * @return all the {@link GqlOperationDefinition} available
     */
    List<GqlOperationDefinition> getBuiltInOperationDefinitions();

    /**
     * Returns all the named query operations that are available for the given {@link Structure}
     * @param structure to get the named query operations for
     * @return all the {@link GqlOperationDefinition} available for the given {@link Structure}
     */
    List<GqlOperationDefinition> getNamedQueryOperationDefinitions(Structure structure);

}
