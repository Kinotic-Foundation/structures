package org.kinotic.structures.internal.api.decorators.instances;

import graphql.schema.GraphQLNonNull;
import org.kinotic.continuum.idl.api.schema.decorators.NotNullC3Decorator;
import org.kinotic.structures.api.decorators.runtime.GraphQLMappingPreProcessor;
import org.kinotic.structures.api.decorators.runtime.GraphQLTypeHolder;
import org.kinotic.structures.api.decorators.runtime.MappingContext;
import org.kinotic.structures.api.domain.Structure;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
@Component
public class NotNullDecoratorInstance implements GraphQLMappingPreProcessor<NotNullC3Decorator> {
    @Override
    public Class<NotNullC3Decorator> implementsDecorator() {
        return NotNullC3Decorator.class;
    }

    @Override
    public String decoratorTypeName() {
        return null; // This is already registered as a default decorator
    }

    @Override
    public GraphQLTypeHolder process(Structure structure,
                                     String fieldName,
                                     NotNullC3Decorator decorator,
                                     MappingContext<GraphQLTypeHolder> context) {

        GraphQLTypeHolder typeHolder = context.convertInternal(context.value());

        return new GraphQLTypeHolder(GraphQLNonNull.nonNull(typeHolder.getInputType()), GraphQLNonNull.nonNull(typeHolder.getOutputType()));
    }
}
