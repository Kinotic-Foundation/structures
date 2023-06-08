package org.kinotic.structures.api.decorators.runtime;

import io.swagger.v3.oas.models.media.Schema;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.idl.converters.openapi.OpenApiConversionState;

/**
 * The {@link OpenApiMappingPreProcessor} is used to create an OpenApi mapping based on data available in the {@link C3Decorator}
 * Created by Navíd Mitchell 🤪 on 5/15/23.
 */
public interface OpenApiMappingPreProcessor<D extends C3Decorator> extends MappingPreProcessor<D, Schema<?>, OpenApiConversionState> {

    /**
     * Process the given {@link Structure} and {@link C3Decorator} to create or modify an OpenApi mapping
     * @param structure the {@link Structure} that is being processed
     * @param fieldName the name of the field that is being processed or null if this is a root level mapping
     * @param decorator the {@link C3Decorator} that is being processed
     * @param type the {@link C3Type} that is being processed
     * @param context the {@link MappingContext} for use by this {@link OpenApiMappingPreProcessor}
     * @return the {@link Schema} that was created or modified
     */
    @Override
    Schema<?> process(Structure structure,
                      String fieldName,
                      D decorator,
                      C3Type type,
                      MappingContext<Schema<?>, OpenApiConversionState> context);
}
