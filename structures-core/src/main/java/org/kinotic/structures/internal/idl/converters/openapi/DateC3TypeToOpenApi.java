package org.kinotic.structures.internal.idl.converters.openapi;

import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.DateC3Type;

import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 5/15/23.
 */
public class DateC3TypeToOpenApi implements SpecificC3TypeConverter<Schema<?>, DateC3Type, OpenApiConversionState> {

    private static final Set<Class<? extends C3Type>> supports = Set.of(DateC3Type.class);

    private static final Schema<?> DATE = new StringSchema().format("date-time");

    @Override
    public Schema<?> convert(DateC3Type dateC3Type, C3ConversionContext<Schema<?>, OpenApiConversionState> conversionContext) {
        return DATE;
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }
}
