package org.kinotic.structures.internal.idl.converters.graphql;

import graphql.scalars.ExtendedScalars;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.*;
import org.kinotic.continuum.idl.internal.api.converter.MultipleSpecificC3TypeConverter;
import org.kinotic.structures.api.decorators.runtime.mapping.GraphQLTypeHolder;

import java.util.Set;

import static graphql.Scalars.*;

/**
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
public class PrimitiveC3TypeToGraphQL implements SpecificC3TypeConverter<GraphQLTypeHolder, C3Type, GraphQLConversionState>  {

    private static final GraphQLTypeHolder BOOL = new GraphQLTypeHolder(GraphQLBoolean, GraphQLBoolean);
    private static final GraphQLTypeHolder BYTE = new GraphQLTypeHolder(ExtendedScalars.GraphQLByte, ExtendedScalars.GraphQLByte);
    private static final GraphQLTypeHolder CHAR = new GraphQLTypeHolder(ExtendedScalars.GraphQLChar, ExtendedScalars.GraphQLChar);
    private static final GraphQLTypeHolder DOUBLE = new GraphQLTypeHolder(GraphQLFloat, GraphQLFloat);
    private static final GraphQLTypeHolder FLOAT = new GraphQLTypeHolder(GraphQLFloat, GraphQLFloat);
    private static final GraphQLTypeHolder INT = new GraphQLTypeHolder(GraphQLInt, GraphQLInt);
    private static final GraphQLTypeHolder LONG = new GraphQLTypeHolder(ExtendedScalars.GraphQLLong, ExtendedScalars.GraphQLLong);
    private static final GraphQLTypeHolder SHORT = new GraphQLTypeHolder(ExtendedScalars.GraphQLShort, ExtendedScalars.GraphQLShort);
    private static final GraphQLTypeHolder STRING = new GraphQLTypeHolder(GraphQLString, GraphQLString);

    private final MultipleSpecificC3TypeConverter<GraphQLTypeHolder, GraphQLConversionState> converter = new MultipleSpecificC3TypeConverter<>();

    public PrimitiveC3TypeToGraphQL() {
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
    public GraphQLTypeHolder convert(C3Type c3Type,
                                     C3ConversionContext<GraphQLTypeHolder, GraphQLConversionState> conversionContext) {
        return converter.convert(c3Type, conversionContext);
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return converter.supports();
    }
}
