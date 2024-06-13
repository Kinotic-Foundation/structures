package org.kinotic.structures.internal.endpoints.graphql;

import graphql.schema.*;
import lombok.Builder;
import lombok.Getter;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.continuum.idl.api.converter.IdlConverter;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.idl.converters.graphql.GqlConversionState;
import org.kinotic.structures.internal.idl.converters.graphql.GqlTypeHolder;

/**
 * Created by Navíd Mitchell 🤪 on 12/14/23.
 */
@Builder
@Getter
public class GqlFieldDefinitionData {

    /**
     * The {@link IdlConverter} currently being used to convert the {@link Structure} types to {@link GraphQLType}s
     */
    private final IdlConverter<GqlTypeHolder, GqlConversionState> converter;

    /**s
     * The {@link GraphQLInputObjectType} that is created from the {@link ObjectC3Type} for the {@link Structure} or null if the {@link Structure}s does not have an input type
     */
    private final GraphQLInputObjectType inputType;

    /**
     * The {@link GraphQLObjectType} that is created from the {@link ObjectC3Type} for the {@link Structure}
     */
    private final GraphQLObjectType outputType;

    /**
     * The {@link GraphQLNamedOutputType} for the {@link Page} type containing the {@link ObjectC3Type} for the {@link Structure}
     */
    private final GraphQLNamedOutputType pageResponseType;

    /**
     * The {@link GraphQLTypeReference} for the {@link Pageable} type used for all requests needing paging
     */
    private final GraphQLTypeReference pageableReference;

    /**
     * The {@link Structure#getName()} (first letter capitalized) that the {@link GraphQLFieldDefinition} is for
     */
    private final String structureName;

}
