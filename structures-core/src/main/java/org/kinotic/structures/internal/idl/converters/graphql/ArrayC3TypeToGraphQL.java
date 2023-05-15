package org.kinotic.structures.internal.idl.converters.graphql;

import graphql.schema.GraphQLList;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.ArrayC3Type;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.structures.api.decorators.runtime.GraphQLTypeHolder;

import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
public class ArrayC3TypeToGraphQL implements SpecificC3TypeConverter<GraphQLTypeHolder, ArrayC3Type, GraphQLConversionState> {

    private static final Set<Class<? extends C3Type>> supports = Set.of(ArrayC3Type.class);

    @Override
    public GraphQLTypeHolder convert(ArrayC3Type c3Type,
                                     C3ConversionContext<GraphQLTypeHolder, GraphQLConversionState> conversionContext) {

        GraphQLTypeHolder typeHolder = conversionContext.convert(c3Type.getContains());

        return new GraphQLTypeHolder(GraphQLList.list(typeHolder.getInputType()),
                                     GraphQLList.list(typeHolder.getOutputType()));
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }

}
