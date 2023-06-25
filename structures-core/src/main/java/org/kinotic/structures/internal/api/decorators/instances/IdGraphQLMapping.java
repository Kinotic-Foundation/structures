package org.kinotic.structures.internal.api.decorators.instances;

import graphql.Scalars;
import graphql.schema.GraphQLNonNull;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.StringC3Type;
import org.kinotic.structures.api.decorators.IdDecorator;
import org.kinotic.structures.api.decorators.runtime.mapping.GraphQLMappingPreProcessor;
import org.kinotic.structures.api.decorators.runtime.mapping.GraphQLTypeHolder;
import org.kinotic.structures.api.decorators.runtime.mapping.MappingContext;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.idl.converters.graphql.GraphQLConversionState;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪on 6/24/23.
 */
@Component
public class IdGraphQLMapping implements GraphQLMappingPreProcessor<IdDecorator> {

    @Override
    public Class<IdDecorator> implementsDecorator() {
        return IdDecorator.class;
    }

    @Override
    public boolean supportC3Type(C3Type c3Type) {
        return c3Type instanceof StringC3Type;
    }

    @Override
    public GraphQLTypeHolder process(Structure structure,
                                     String fieldName,
                                     IdDecorator decorator,
                                     C3Type type,
                                     MappingContext<GraphQLTypeHolder, GraphQLConversionState> context) {
        return new GraphQLTypeHolder(GraphQLNonNull.nonNull(Scalars.GraphQLID), GraphQLNonNull.nonNull(Scalars.GraphQLID));
    }


}
