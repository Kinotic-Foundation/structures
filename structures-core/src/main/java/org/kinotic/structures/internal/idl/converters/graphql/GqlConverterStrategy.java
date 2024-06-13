package org.kinotic.structures.internal.idl.converters.graphql;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLEnumType;
import graphql.schema.GraphQLList;
import lombok.RequiredArgsConstructor;
import org.kinotic.continuum.idl.api.converter.C3TypeConverter;
import org.kinotic.continuum.idl.api.converter.C3TypeConverterContainer;
import org.kinotic.continuum.idl.api.converter.IdlConverterStrategy;
import org.kinotic.continuum.idl.api.schema.*;
import org.kinotic.structures.api.config.StructuresProperties;

import java.util.Set;

import static graphql.Scalars.*;

/**
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
@RequiredArgsConstructor
public class GqlConverterStrategy implements IdlConverterStrategy<GqlTypeHolder, GqlConversionState> {

    private static final GqlTypeHolder BOOL = new GqlTypeHolder(GraphQLBoolean, GraphQLBoolean);
    private static final GqlTypeHolder BYTE = new GqlTypeHolder(ExtendedScalars.GraphQLByte, ExtendedScalars.GraphQLByte);
    private static final GqlTypeHolder CHAR = new GqlTypeHolder(ExtendedScalars.GraphQLChar, ExtendedScalars.GraphQLChar);
    private static final GqlTypeHolder DATE = new GqlTypeHolder(ExtendedScalars.DateTime, ExtendedScalars.DateTime);
    private static final GqlTypeHolder DOUBLE = new GqlTypeHolder(GraphQLFloat, GraphQLFloat);
    private static final GqlTypeHolder FLOAT = new GqlTypeHolder(GraphQLFloat, GraphQLFloat);
    private static final GqlTypeHolder INT = new GqlTypeHolder(GraphQLInt, GraphQLInt);
    private static final GqlTypeHolder LONG = new GqlTypeHolder(ExtendedScalars.GraphQLLong, ExtendedScalars.GraphQLLong);
    private static final GqlTypeHolder SHORT = new GqlTypeHolder(ExtendedScalars.GraphQLShort, ExtendedScalars.GraphQLShort);
    private static final GqlTypeHolder STRING = new GqlTypeHolder(GraphQLString, GraphQLString);
    private static final Set<C3TypeConverter<GqlTypeHolder, ? extends C3Type, GqlConversionState>> converters;
    private final StructuresProperties structuresProperties;

    static {
        C3TypeConverterContainer<GqlTypeHolder, GqlConversionState> container = new C3TypeConverterContainer<>();
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
                 // Enum type
                 .addConverter(EnumC3Type.class, (c3Type, context) -> {
                     GraphQLEnumType.Builder builder = GraphQLEnumType.newEnum();
                     builder.name(c3Type.getName());

                     for (String value : c3Type.getValues()) {
                         builder.value(value);
                     }

                     GraphQLEnumType type = builder.build();
                     return new GqlTypeHolder(type, type);
                 })
                 // Array type
                 .addConverter(ArrayC3Type.class, (c3Type, context) -> {
                     GqlTypeHolder typeHolder = context.convert(c3Type.getContains());
                     // input type can be null in some cases such as a Union type.
                     // This can create a paradigm mismatch between OpenApi and GraphQL, but we cannot do anything about it.
                     // For now, we will not create an input type for these cases.
                     return new GqlTypeHolder(typeHolder.getInputType() != null ? GraphQLList.list(typeHolder.getInputType()) : null,
                                              GraphQLList.list(typeHolder.getOutputType()));
                 });

        converters = Set.of(container, new ObjectC3TypeToGql(), new UnionC3TypeToGql(), new PageC3TypeToGql());
    }

    @Override
    public Set<C3TypeConverter<GqlTypeHolder, ? extends C3Type, GqlConversionState>> converters() {
        return converters;
    }

    @Override
    public GqlConversionState initialState() {
        return new GqlConversionState(this.structuresProperties);
    }

    @Override
    public boolean shouldCache() {
        return true;
    }

}
