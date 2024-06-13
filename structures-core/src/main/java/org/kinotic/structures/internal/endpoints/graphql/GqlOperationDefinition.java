package org.kinotic.structures.internal.endpoints.graphql;

import graphql.language.OperationDefinition;
import graphql.schema.GraphQLFieldDefinition;
import lombok.Builder;
import lombok.Getter;

import java.util.function.Function;

/**
 * Defines all the needed data to define a GraphQL Query or Mutation
 * Created by Navíd Mitchell 🤪 on 12/14/23.
 */
@Builder
@Getter
public class GqlOperationDefinition {

    /**
     * The prefix of the operation name
     * Such as findById, findAll, update, delete, etc...
     * NOTE: This must be unique for the operation, and only contain alpha characters.
     */
    private final String operationNamePrefix;

    /**
     * The type of operation this definition is for
     */
    private final OperationDefinition.Operation operationType;

    /**
     * The function that will execute the operation
     */
    private final GqlOperationExecutionFunction operationExecutionFunction;

    /**
     * The function that will build the {@link GraphQLFieldDefinition} for this operation
     * This function will be passed a {@link GqlFieldDefinitionData} that contains all the needed data to build the {@link GraphQLFieldDefinition
     */
    private final Function<GqlFieldDefinitionData, GraphQLFieldDefinition> fieldDefinitionFunction;

}
