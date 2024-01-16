package org.kinotic.structures.internal.graphql;

import graphql.schema.*;
import lombok.Builder;
import lombok.Getter;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.continuum.core.api.crud.Page;
/**
 * Created by Navíd Mitchell 🤪 on 12/14/23.
 */
@Builder
@Getter
public class GqlFieldDefinitionData {

    /**
     * The name of the structure the {@link GraphQLFieldDefinition} is for
     */
    private final String structuresName;

    /**
     * The {@link GraphQLObjectType} that is created from the {@link ObjectC3Type} for the Entity
     */
    private final GraphQLObjectType outputType;

    /**
     * The {@link GraphQLInputObjectType} that is created from the {@link ObjectC3Type} for the Entity
     */
    private final GraphQLInputObjectType inputType;

    /**
     * The {@link GraphQLTypeReference} for the {@link Pageable} type
     */
    private final GraphQLTypeReference pageableReference;

    /**
     * The {@link GraphQLNamedOutputType} for the {@link Page} type containing the {@link ObjectC3Type} for the Entity
     */
    private final GraphQLNamedOutputType pageResponseType;

}
