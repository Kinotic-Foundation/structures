package org.kinotic.structures.api.decorators.runtime;

import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.idl.converters.graphql.GraphQLConversionState;

/**
 * The {@link GraphQLMappingPreProcessor} is used to create an GraphQL mapping based on data available in the {@link C3Decorator}
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
public interface GraphQLMappingPreProcessor<D extends C3Decorator> extends MappingPreProcessor<D, GraphQLTypeHolder, GraphQLConversionState> {

    /**
     * Process the given {@link Structure} and {@link C3Decorator} to create or modify an GraphQL mapping
     * @param structure the {@link Structure} that is being processed
     * @param fieldName the name of the field that is being processed or null if this is a root level mapping
     * @param decorator the {@link C3Decorator} that is being processed
     * @param type the {@link C3Type} that is being processed
     * @param context the {@link MappingContext} for use by this {@link GraphQLMappingPreProcessor}
     * @return the {@link GraphQLTypeHolder} that was created or modified
     */
    @Override
    GraphQLTypeHolder process(Structure structure,
                              String fieldName,
                              D decorator,
                              C3Type type,
                              MappingContext<GraphQLTypeHolder, GraphQLConversionState> context);
}
