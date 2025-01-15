package org.kinotic.structures.internal.idl.converters.graphql;

import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLOutputType;
import lombok.Builder;

/**
 * Holds the Graph input and output types created for a given C3 type
 * Created by Navíd Mitchell 🤪 on 5/2/23.
 *
 * @param inputType  The GraphQL input type for the C3 type
 * @param outputType The GraphQL output type for the C3 type
 */
@Builder(toBuilder = true)
public record GqlTypeHolder(GraphQLInputType inputType, GraphQLOutputType outputType) {

}
