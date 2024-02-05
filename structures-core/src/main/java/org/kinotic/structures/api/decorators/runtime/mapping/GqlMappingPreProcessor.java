package org.kinotic.structures.api.decorators.runtime.mapping;

import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.idl.converters.graphql.GqlTypeHolder;
import org.kinotic.structures.internal.idl.converters.graphql.GqlConversionState;

/**
 * The {@link GqlMappingPreProcessor} is used to create an GraphQL mapping based on data available in the {@link C3Decorator}
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
public interface GqlMappingPreProcessor<D extends C3Decorator> extends MappingPreProcessor<D, GqlTypeHolder, GqlConversionState> {

    /**
     * Process the given {@link Structure} and {@link C3Decorator} to create or modify an GraphQL mapping
     * @param structure the {@link Structure} that is being processed
     * @param fieldName the name of the field that is being processed or null if this is a root level mapping
     * @param decorator the {@link C3Decorator} that is being processed
     * @param type the {@link C3Type} that is being processed
     * @param context the {@link MappingContext} for use by this {@link GqlMappingPreProcessor}
     * @return the {@link GqlTypeHolder} that was created or modified
     */
    @Override
    GqlTypeHolder process(Structure structure,
                          String fieldName,
                          D decorator,
                          C3Type type,
                          MappingContext<GqlTypeHolder, GqlConversionState> context);
}
