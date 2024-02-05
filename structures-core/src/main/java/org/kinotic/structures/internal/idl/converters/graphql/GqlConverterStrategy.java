package org.kinotic.structures.internal.idl.converters.graphql;

import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.internal.api.converter.AbstractIdlConverterStrategy;
import org.kinotic.structures.api.decorators.runtime.mapping.GqlMappingPreProcessor;
import org.kinotic.structures.internal.idl.converters.common.MappingPreProcessorConverter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
@Component
public class GqlConverterStrategy extends AbstractIdlConverterStrategy<GqlTypeHolder, GqlConversionState> {

    private final static List<SpecificC3TypeConverter<GqlTypeHolder, ?, GqlConversionState>> specificTypeConverters = List.of(
            new PrimitiveC3TypeToGql(),
            new ArrayC3TypeToGql(),
            new DateC3TypeToGql(),
            new UnionC3TypeToGql(),
            new EnumC3TypeToGql(),
            new ObjectC3TypeToGql());

    public GqlConverterStrategy(List<GqlMappingPreProcessor<?>> gqlMappingPreProcessors) {
        //noinspection unchecked,rawtypes
        super(specificTypeConverters, List.of(new MappingPreProcessorConverter(gqlMappingPreProcessors)));
    }

    @Override
    public GqlConversionState initialState() {
        return new GqlConversionState();
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
