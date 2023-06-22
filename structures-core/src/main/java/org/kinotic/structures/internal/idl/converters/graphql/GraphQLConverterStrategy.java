package org.kinotic.structures.internal.idl.converters.graphql;

import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.internal.api.converter.AbstractIdlConverterStrategy;
import org.kinotic.structures.api.decorators.runtime.mapping.GraphQLMappingPreProcessor;
import org.kinotic.structures.api.decorators.runtime.mapping.GraphQLTypeHolder;
import org.kinotic.structures.internal.idl.converters.common.MappingPreProcessorConverter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
@Component
public class GraphQLConverterStrategy extends AbstractIdlConverterStrategy<GraphQLTypeHolder, GraphQLConversionState> {

    private final static List<SpecificC3TypeConverter<GraphQLTypeHolder, ?, GraphQLConversionState>> specificTypeConverters = List.of(
            new PrimitiveC3TypeToGraphQL(),
            new ArrayC3TypeToGraphQL(),
            new DateC3TypeToGraphQL(),
            new UnionC3TypeToGraphQL(),
            new EnumC3TypeToGraphQL(),
            new ObjectC3TypeToGraphQL());

    public GraphQLConverterStrategy(List<GraphQLMappingPreProcessor<?>> graphQLMappingPreProcessors) {
        //noinspection unchecked,rawtypes
        super(specificTypeConverters, List.of(new MappingPreProcessorConverter(graphQLMappingPreProcessors)));
    }

    @Override
    public GraphQLConversionState initialState() {
        return new GraphQLConversionState();
    }

    @Override
    public boolean shouldCache() {
        return true;
    }

    @Override
    protected boolean shouldCheckGenericConvertersFirst() {
        return true;
    }
}
