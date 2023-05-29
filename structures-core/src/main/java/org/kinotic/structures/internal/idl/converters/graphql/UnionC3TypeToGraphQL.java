package org.kinotic.structures.internal.idl.converters.graphql;

import graphql.schema.GraphQLUnionType;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.UnionC3Type;
import org.kinotic.structures.api.decorators.runtime.GraphQLTypeHolder;

import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 5/27/23.
 */
public class UnionC3TypeToGraphQL implements SpecificC3TypeConverter<GraphQLTypeHolder, UnionC3Type, GraphQLConversionState>  {

    private static final Set<Class<? extends C3Type>> supports = Set.of(UnionC3Type.class);

    @Override
    public GraphQLTypeHolder convert(UnionC3Type c3Type,
                                     C3ConversionContext<GraphQLTypeHolder, GraphQLConversionState> conversionContext) {

        GraphQLUnionType.Builder unionBuilder = GraphQLUnionType.newUnionType();


        return null;
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }
}
