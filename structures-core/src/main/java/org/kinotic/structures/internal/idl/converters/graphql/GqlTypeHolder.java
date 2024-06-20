package org.kinotic.structures.internal.idl.converters.graphql;

import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLOutputType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Holds the Graph input and output types created for a given C3 type
 * Created by Navíd Mitchell 🤪 on 5/2/23.
 */
@Getter
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class GqlTypeHolder {

    /**
     * The GraphQL input type for the C3 type
     */
    private final GraphQLInputType inputType;

    /**
     * The GraphQL output type for the C3 type
     */
    private final GraphQLOutputType outputType;

}
