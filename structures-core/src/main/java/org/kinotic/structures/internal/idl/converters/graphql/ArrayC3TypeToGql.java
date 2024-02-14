package org.kinotic.structures.internal.idl.converters.graphql;

import graphql.schema.GraphQLList;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.ArrayC3Type;
import org.kinotic.continuum.idl.api.schema.C3Type;

import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
public class ArrayC3TypeToGql implements SpecificC3TypeConverter<GqlTypeHolder, ArrayC3Type, GqlConversionState> {

    private static final Set<Class<? extends C3Type>> supports = Set.of(ArrayC3Type.class);

    @Override
    public GqlTypeHolder convert(ArrayC3Type c3Type,
                                 C3ConversionContext<GqlTypeHolder, GqlConversionState> conversionContext) {

        GqlTypeHolder typeHolder = conversionContext.convert(c3Type.getContains());

        // input type can be null in some cases such as a Union type.
        // This can create a paradigm mismatch between OpenApi and GraphQL, but we cannot do anything about it.
        // For now, we will not create an input type for these cases.

        return new GqlTypeHolder(typeHolder.getInputType() != null ? GraphQLList.list(typeHolder.getInputType()) : null,
                                 GraphQLList.list(typeHolder.getOutputType()));
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }

}
