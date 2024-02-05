package org.kinotic.structures.internal.idl.converters.openapi;

import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.ArrayC3Type;
import org.kinotic.continuum.idl.api.schema.ByteC3Type;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;

import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 5/15/23.
 */
public class ArrayC3TypeToOpenApi implements SpecificC3TypeConverter<Schema<?>, ArrayC3Type, OpenApiConversionState> {

    private static final Set<Class<? extends C3Type>> supports = Set.of(ArrayC3Type.class);

    @Override
    public Schema<?> convert(ArrayC3Type c3Type,
                             C3ConversionContext<Schema<?>, OpenApiConversionState> conversionContext) {

        // byte arrays are a special case per the open api spec
        // https://swagger.io/docs/specification/data-models/data-types/#string
        if(c3Type.getContains() instanceof ByteC3Type){
            return new StringSchema().format("binary");
        } else {

            Schema<?> schema = conversionContext.convert(c3Type.getContains());

            if(c3Type.getContains() instanceof ObjectC3Type){
                ObjectC3Type objectC3Type = (ObjectC3Type) c3Type.getContains();
                conversionContext.state().addReferencedSchema(objectC3Type.getName(), schema);
                schema = new Schema<>().$ref("#/components/schemas/"+objectC3Type.getName());
            }

            return new ArraySchema().items(schema);
        }
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }
}
