package org.kinotic.structures.internal.idl.converters.graphql;

import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLOutputType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Holds the Graph input and output types created for a given C3 type
 * Created by Navíd Mitchell 🤪 on 5/2/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
public class GqlTypeHolder {

    /**
     * The GraphQL input type for the C3 type
     */
    private GraphQLInputType inputType;

    /**
     * The GraphQL output type for the C3 type
     */
    private GraphQLOutputType outputType;

}
