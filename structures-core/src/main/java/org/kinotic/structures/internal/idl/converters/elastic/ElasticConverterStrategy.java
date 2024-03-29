package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.internal.api.converter.AbstractIdlConverterStrategy;
import org.kinotic.structures.internal.idl.converters.common.DecoratorPreProcessorConverter;
import org.kinotic.structures.internal.idl.converters.elastic.decorators.ElasticDecoratorMappingProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Strategy for converting C3 types to ES properties
 * Created by Navíd Mitchell 🤪 on 4/28/23.
 */
@Component
public class ElasticConverterStrategy extends AbstractIdlConverterStrategy<Property, ElasticConversionState> {

    private final static List<SpecificC3TypeConverter<Property, ?, ElasticConversionState>> specificTypeConverters = List.of(
            new PrimitiveC3TypeToElastic(),
            new ArrayC3TypeToElastic(),
            new DateC3TypeToElastic(),
            new UnionC3TypeToElastic(),
            new ObjectC3TypeToElastic()
    );

    public ElasticConverterStrategy(List<ElasticDecoratorMappingProcessor<?>> elasticMappingPreProcessors) {
        // FIXME: get the generics correct here. Seems I shouldn't have to suppress the warnings.
        //noinspection unchecked,rawtypes
        super(specificTypeConverters, List.of(new DecoratorPreProcessorConverter(elasticMappingPreProcessors)));
    }

    @Override
    public ElasticConversionState initialState() {
        return new ElasticConversionState();
    }

    @Override
    public boolean shouldCache() {
        return true;
    }

    @Override
    protected boolean shouldCheckGenericConvertersFirst() {
        // We do this so mapping converters will take precedence over specific converters
        return true;
    }
}
