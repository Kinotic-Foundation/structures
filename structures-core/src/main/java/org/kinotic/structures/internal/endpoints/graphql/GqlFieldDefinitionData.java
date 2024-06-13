package org.kinotic.structures.internal.endpoints.graphql;

import graphql.schema.*;
import lombok.Builder;
import lombok.Getter;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.structures.api.domain.Structure;

/**
 * Created by Navíd Mitchell 🤪 on 12/14/23.
 */
@Builder
@Getter
public class GqlFieldDefinitionData {

    /**s
     * The name of the structure the {@link GraphQLFieldDefinition} is for
     */
    private final String structuresName;

    /**
     * The {@link GraphQLObjectType} that is created from the {@link ObjectC3Type} for the {@link Structure}
     */
    private final GraphQLObjectType outputType;

    /**
     * The {@link GraphQLInputObjectType} that is created from the {@link ObjectC3Type} for the {@link Structure} or null if the {@link Structure}s does not have an input type
     */
    private final GraphQLInputObjectType inputType;

    /**
     * The {@link GraphQLTypeReference} for the {@link Pageable} type used for all requests needing paging
     */
    private final GraphQLTypeReference pageableReference;

    /**
     * The {@link GraphQLNamedOutputType} for the {@link Page} type containing the {@link ObjectC3Type} for the {@link Structure}
     */
    private final GraphQLNamedOutputType pageResponseType;

}
