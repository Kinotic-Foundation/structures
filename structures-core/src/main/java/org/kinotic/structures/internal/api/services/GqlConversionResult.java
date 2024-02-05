package org.kinotic.structures.internal.api.services;

import graphql.schema.GraphQLUnionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.idl.converters.graphql.GqlTypeHolder;

import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 2/4/24.
 */
@Getter
@RequiredArgsConstructor
public class GqlConversionResult {

    /**
     * This class holds the input and output types created for a given {@link Structure}
     */
    private final GqlTypeHolder structureType;

    /**
     * A list of all {@link GraphQLUnionType} that were created during the conversion process
     */
    private final List<GraphQLUnionType> unionTypes;

}
