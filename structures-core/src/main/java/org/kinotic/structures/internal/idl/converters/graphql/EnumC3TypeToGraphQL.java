package org.kinotic.structures.internal.idl.converters.graphql;

import graphql.schema.GraphQLEnumType;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.EnumC3Type;
import org.kinotic.structures.api.decorators.runtime.mapping.GraphQLTypeHolder;

import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 6/5/23.
 */
public class EnumC3TypeToGraphQL implements SpecificC3TypeConverter<GraphQLTypeHolder, EnumC3Type, GraphQLConversionState> {

    private static final Set<Class<? extends C3Type>> supports = Set.of(EnumC3Type.class);

    @Override
    public GraphQLTypeHolder convert(EnumC3Type c3Type,
                                     C3ConversionContext<GraphQLTypeHolder, GraphQLConversionState> conversionContext) {

        GraphQLEnumType.Builder builder = GraphQLEnumType.newEnum();
        for (String value : c3Type.getValues()) {
            builder.value(value);
        }

        return new GraphQLTypeHolder(builder.build(), builder.build());
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }
}