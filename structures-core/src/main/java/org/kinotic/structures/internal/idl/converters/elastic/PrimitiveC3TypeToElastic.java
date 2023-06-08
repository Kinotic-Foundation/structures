package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.*;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.*;
import org.kinotic.continuum.idl.internal.api.converter.MultipleSpecificC3TypeConverter;

import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 4/28/23.
 */
public class PrimitiveC3TypeToElastic implements SpecificC3TypeConverter<Property, C3Type, ElasticConversionState> {

    private static final Property BOOL = BooleanProperty.of(f -> f)._toProperty();
    private static final Property BYTE = ByteNumberProperty.of(f -> f)._toProperty();
    private static final Property CHAR = KeywordProperty.of(f -> f)._toProperty();
    private static final Property DOUBLE = DoubleNumberProperty.of(f -> f)._toProperty();
    private static final Property FLOAT = FloatNumberProperty.of(f -> f)._toProperty();
    private static final Property INT = IntegerNumberProperty.of(f -> f)._toProperty();
    private static final Property LONG = LongNumberProperty.of(f -> f)._toProperty();
    private static final Property SHORT = ShortNumberProperty.of(f -> f)._toProperty();
    private static final Property STRING = KeywordProperty.of(f -> f)._toProperty();
    private static final Property ENUM = KeywordProperty.of(f -> f)._toProperty();

    private final MultipleSpecificC3TypeConverter<Property, ElasticConversionState> converter = new MultipleSpecificC3TypeConverter<>();

    public PrimitiveC3TypeToElastic() {
        converter.addConverter(BooleanC3Type.class, (c3Type, context) -> BOOL)
                 .addConverter(ByteC3Type.class, (c3Type, context) -> BYTE)
                 .addConverter(CharC3Type.class, (c3Type, context) -> CHAR)
                 .addConverter(DoubleC3Type.class, (c3Type, context) -> DOUBLE)
                 .addConverter(FloatC3Type.class, (c3Type, context) -> FLOAT)
                 .addConverter(IntC3Type.class, (c3Type, context) -> INT)
                 .addConverter(LongC3Type.class, (c3Type, context) -> LONG)
                 .addConverter(ShortC3Type.class, (c3Type, context) -> SHORT)
                 .addConverter(StringC3Type.class, (c3Type, context) -> STRING)
                 .addConverter(EnumC3Type.class, (c3Type, context) -> ENUM);
    }

    @Override
    public Property convert(C3Type c3Type, C3ConversionContext<Property, ElasticConversionState> conversionContext) {
        return converter.convert(c3Type, conversionContext);
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return converter.supports();
    }
}

