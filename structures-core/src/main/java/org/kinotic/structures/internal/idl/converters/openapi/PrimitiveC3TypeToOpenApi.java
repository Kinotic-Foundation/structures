package org.kinotic.structures.internal.idl.converters.openapi;

import io.swagger.v3.oas.models.media.*;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.*;
import org.kinotic.continuum.idl.internal.api.converter.MultipleSpecificC3TypeConverter;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
public class PrimitiveC3TypeToOpenApi implements SpecificC3TypeConverter<Schema<?>, C3Type, OpenApiConversionState> {

    private static final Schema<?> BOOL = new BooleanSchema();
    private static final Schema<?> BYTE = new NumberSchema().minimum(BigDecimal.valueOf(Byte.MIN_VALUE))
                                                            .maximum(BigDecimal.valueOf(Byte.MAX_VALUE));
    private static final Schema<?> CHAR = new StringSchema().minLength(1).maxLength(1);
    private static final Schema<?> DOUBLE = new NumberSchema().format("double");
    private static final Schema<?> FLOAT = new NumberSchema().format("float");
    private static final Schema<?> INT = new IntegerSchema().format("int32");
    private static final Schema<?> LONG = new IntegerSchema().format("int64");
    private static final Schema<?> SHORT = new NumberSchema().minimum(BigDecimal.valueOf(Short.MIN_VALUE))
                                                             .maximum(BigDecimal.valueOf(Short.MAX_VALUE));
    private static final Schema<?> STRING = new StringSchema();

    private final MultipleSpecificC3TypeConverter<Schema<?>, OpenApiConversionState> converter = new MultipleSpecificC3TypeConverter<>();

    public PrimitiveC3TypeToOpenApi() {
        converter.addConverter(BooleanC3Type.class, (c3Type, context) -> BOOL)
                 .addConverter(ByteC3Type.class, (c3Type, context) -> BYTE)
                 .addConverter(CharC3Type.class, (c3Type, context) -> CHAR)
                 .addConverter(DoubleC3Type.class, (c3Type, context) -> DOUBLE) // GraphQLFloat a double precision floating‐point value
                 .addConverter(FloatC3Type.class, (c3Type, context) -> FLOAT)
                 .addConverter(IntC3Type.class, (c3Type, context) -> INT)
                 .addConverter(LongC3Type.class, (c3Type, context) -> LONG)
                 .addConverter(ShortC3Type.class, (c3Type, context) -> SHORT)
                 .addConverter(StringC3Type.class, (c3Type, context) -> STRING);
    }


    @Override
    public Schema<?> convert(C3Type c3Type, C3ConversionContext<Schema<?>, OpenApiConversionState> conversionContext) {
        return converter.convert(c3Type, conversionContext);
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return converter.supports();
    }
}
