package org.kinotic.structures.internal.idl.converters.openapi;

import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.Cacheable;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.EnumC3Type;

import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 6/5/23.
 */
public class EnumC3TypeToOpenApi implements SpecificC3TypeConverter<Schema<?>, EnumC3Type, OpenApiConversionState>, Cacheable {

    private static final Set<Class<? extends C3Type>> supports = Set.of(EnumC3Type.class);

    @Override
    public Schema<?> convert(EnumC3Type c3Type,
                             C3ConversionContext<Schema<?>, OpenApiConversionState> conversionContext) {

        StringSchema stringSchema = new StringSchema();
        stringSchema.setEnum(c3Type.getValues());

        conversionContext.state().addReferenceSchema(c3Type.getName(), stringSchema);

        return new Schema<>().$ref("#/components/schemas/"+c3Type.getName());
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }
}
