package org.kinotic.structures.internal.graphql;

import java.util.List;

/**
 * This service provides all the {@link GqlOperationDefinition} available
 * Created by Navíd Mitchell 🤪 on 12/14/23.
 */
public interface GqlOperationProviderService {

    /**
     * @return all the {@link GqlOperationDefinition} available
     */
    List<GqlOperationDefinition> getOperationDefinitions();

    /**
     * Finds a {@link GqlOperationDefinition} by its operation name
     * This will return operations that partially match the operation name
     * For example:
     * - if the operation name is "createUser" then the "create" {@link GqlOperationDefinition} will be returned
     * @param completeOperationName to find the {@link GqlOperationDefinition} for
     * @return the {@link GqlOperationDefinition} or null if not found
     */
    GqlOperationDefinition findOperationDefinition(String completeOperationName);

}
