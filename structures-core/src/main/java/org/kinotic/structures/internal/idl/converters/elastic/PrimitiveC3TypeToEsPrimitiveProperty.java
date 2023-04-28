package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.*;
import org.kinotic.continuum.idl.api.*;
import org.kinotic.continuum.idl.converter.C3ConversionContext;
import org.kinotic.continuum.idl.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.internal.converter.MultipleSpecificC3TypeConverter;

import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 4/28/23.
 */
public class PrimitiveC3TypeToEsPrimitiveProperty implements SpecificC3TypeConverter<Property> {

    private final MultipleSpecificC3TypeConverter<Property> converter = new MultipleSpecificC3TypeConverter<>();

    public PrimitiveC3TypeToEsPrimitiveProperty() {
        converter.addConverter(BooleanC3Type.class, (c3Type, context) -> BooleanProperty.of(f -> f)._toProperty())
                 .addConverter(ByteC3Type.class, (c3Type, context) -> ByteNumberProperty.of(f -> f)._toProperty())
                 .addConverter(CharC3Type.class, (c3Type, context) -> KeywordProperty.of(f -> f)._toProperty())
                 .addConverter(DoubleC3Type.class, (c3Type, context) -> DoubleNumberProperty.of(f -> f)._toProperty())
                 .addConverter(FloatC3Type.class, (c3Type, context) -> FloatNumberProperty.of(f -> f)._toProperty())
                 .addConverter(IntC3Type.class, (c3Type, context) -> IntegerNumberProperty.of(f -> f)._toProperty())
                 .addConverter(LongC3Type.class, (c3Type, context) -> LongNumberProperty.of(f -> f)._toProperty())
                 .addConverter(ShortC3Type.class, (c3Type, context) -> ShortNumberProperty.of(f -> f)._toProperty());
    }

    @Override
    public Property convert(C3Type c3Type, C3ConversionContext<Property> conversionContext) {
        return converter.convert(c3Type, conversionContext);
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return converter.supports();
    }
}

