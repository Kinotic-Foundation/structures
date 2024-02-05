package org.kinotic.structures.internal.idl.converters.graphql;

import graphql.scalars.ExtendedScalars;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.*;
import org.kinotic.continuum.idl.internal.api.converter.MultipleSpecificC3TypeConverter;

import java.util.Set;

import static graphql.Scalars.*;

/**
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
public class PrimitiveC3TypeToGql implements SpecificC3TypeConverter<GqlTypeHolder, C3Type, GqlConversionState>  {

    private static final GqlTypeHolder BOOL = new GqlTypeHolder(GraphQLBoolean, GraphQLBoolean);
    private static final GqlTypeHolder BYTE = new GqlTypeHolder(ExtendedScalars.GraphQLByte, ExtendedScalars.GraphQLByte);
    private static final GqlTypeHolder CHAR = new GqlTypeHolder(ExtendedScalars.GraphQLChar, ExtendedScalars.GraphQLChar);
    private static final GqlTypeHolder DOUBLE = new GqlTypeHolder(GraphQLFloat, GraphQLFloat);
    private static final GqlTypeHolder FLOAT = new GqlTypeHolder(GraphQLFloat, GraphQLFloat);
    private static final GqlTypeHolder INT = new GqlTypeHolder(GraphQLInt, GraphQLInt);
    private static final GqlTypeHolder LONG = new GqlTypeHolder(ExtendedScalars.GraphQLLong, ExtendedScalars.GraphQLLong);
    private static final GqlTypeHolder SHORT = new GqlTypeHolder(ExtendedScalars.GraphQLShort, ExtendedScalars.GraphQLShort);
    private static final GqlTypeHolder STRING = new GqlTypeHolder(GraphQLString, GraphQLString);

    private final MultipleSpecificC3TypeConverter<GqlTypeHolder, GqlConversionState> converter = new MultipleSpecificC3TypeConverter<>();

    public PrimitiveC3TypeToGql() {
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
    public GqlTypeHolder convert(C3Type c3Type,
                                 C3ConversionContext<GqlTypeHolder, GqlConversionState> conversionContext) {
        return converter.convert(c3Type, conversionContext);
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return converter.supports();
    }
}
