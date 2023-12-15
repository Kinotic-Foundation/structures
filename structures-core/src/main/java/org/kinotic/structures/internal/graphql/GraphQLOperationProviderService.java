package org.kinotic.structures.internal.graphql;

import java.util.List;

/**
 * This service provides all the {@link GraphQLOperationDefinition} available
 * Created by Navíd Mitchell 🤪 on 12/14/23.
 */
public interface GraphQLOperationProviderService {

    /**
     * @return all the {@link GraphQLOperationDefinition} available
     */
    List<GraphQLOperationDefinition> getOperationDefinitions();

    /**
     * Finds a {@link GraphQLOperationDefinition} by its operation name
     * This will return operations that partially match the operation name
     * For example:
     * - if the operation name is "createUser" then the "create" {@link GraphQLOperationDefinition} will be returned
     * @param completeOperationName to find the {@link GraphQLOperationDefinition} for
     * @return the {@link GraphQLOperationDefinition} or null if not found
     */
    GraphQLOperationDefinition findOperationName(String completeOperationName);

}
