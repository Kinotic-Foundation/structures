package org.kinotic.structures.api.decorators.runtime.mapping;

import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLOutputType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Holds the Graph input and output types created for a given C3 type
 * Created by Navíd Mitchell 🤪 on 5/2/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class GraphQLTypeHolder {

    /**
     * The GraphQL input type for the C3 type
     */
    private final GraphQLInputType inputType;

    /**
     * The GraphQL output type for the C3 type
     */
    private final GraphQLOutputType outputType;

}
