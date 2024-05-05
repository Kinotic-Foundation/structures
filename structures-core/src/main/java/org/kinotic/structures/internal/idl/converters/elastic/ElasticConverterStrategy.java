package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.*;
import org.kinotic.continuum.idl.api.converter.C3TypeConverter;
import org.kinotic.continuum.idl.api.converter.C3TypeConverterContainer;
import org.kinotic.continuum.idl.api.converter.IdlConverterStrategy;
import org.kinotic.continuum.idl.api.schema.*;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Strategy for converting C3 types to ES properties
 * Created by Navíd Mitchell 🤪 on 4/28/23.
 */
@Component
public class ElasticConverterStrategy implements IdlConverterStrategy<Property, ElasticConversionState> {

    private static final Property BOOL = BooleanProperty.of(f -> f)._toProperty();
    private static final Property BYTE = ByteNumberProperty.of(f -> f)._toProperty();
    private static final Property CHAR = KeywordProperty.of(f -> f)._toProperty();
    private static final Property DATE = DateProperty.of(f -> f)._toProperty();
    private static final Property DOUBLE = DoubleNumberProperty.of(f -> f)._toProperty();
    private static final Property ENUM = KeywordProperty.of(f -> f)._toProperty();
    private static final Property FLOAT = FloatNumberProperty.of(f -> f)._toProperty();
    private static final Property INT = IntegerNumberProperty.of(f -> f)._toProperty();
    private static final Property LONG = LongNumberProperty.of(f -> f)._toProperty();
    private static final Property SHORT = ShortNumberProperty.of(f -> f)._toProperty();
    private static final Property STRING = KeywordProperty.of(f -> f)._toProperty();
    private final Set<C3TypeConverter<Property, ? extends C3Type, ElasticConversionState>> converters;

    public ElasticConverterStrategy() {
        // Basic types
        C3TypeConverterContainer<Property, ElasticConversionState> container = new C3TypeConverterContainer<>();
        container.addConverter(BooleanC3Type.class, (c3Type, context) -> BOOL)
                 .addConverter(ByteC3Type.class, (c3Type, context) -> BYTE)
                 .addConverter(CharC3Type.class, (c3Type, context) -> CHAR)
                 .addConverter(DoubleC3Type.class, (c3Type, context) -> DOUBLE)
                 .addConverter(FloatC3Type.class, (c3Type, context) -> FLOAT)
                 .addConverter(IntC3Type.class, (c3Type, context) -> INT)
                 .addConverter(LongC3Type.class, (c3Type, context) -> LONG)
                 .addConverter(ShortC3Type.class, (c3Type, context) -> SHORT)
                 .addConverter(StringC3Type.class, (c3Type, context) -> STRING)
                 .addConverter(DateC3Type.class, (c3Type, context) -> DATE)
                 .addConverter(EnumC3Type.class, (c3Type, context) -> ENUM)
                 .addConverter(ArrayC3Type.class, (arrayC3Type, context) -> {
                     // There is no specific type for arrays in elastic search, so we just convert the inner type
                     return context.convert(arrayC3Type.getContains());
                 });

        converters = Set.of(container, new ObjectC3TypeToElastic(), new UnionC3TypeToElastic());
    }

    @Override
    public Set<C3TypeConverter<Property, ? extends C3Type, ElasticConversionState>> converters() {
        return converters;
    }

    @Override
    public ElasticConversionState initialState() {
        return new ElasticConversionState();
    }

    @Override
    public boolean shouldCache() {
        return true;
    }

}
