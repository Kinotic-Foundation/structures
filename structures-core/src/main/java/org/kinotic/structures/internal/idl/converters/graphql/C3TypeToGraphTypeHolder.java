package org.kinotic.structures.internal.idl.converters.graphql;

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
public class C3TypeToGraphTypeHolder<I extends GraphQLInputType, O extends GraphQLOutputType> {

    private final I inputType;

    private final O outputType;

}
