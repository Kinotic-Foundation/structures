package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.internal.api.converter.AbstractIdlConverterStrategy;
import org.kinotic.structures.api.decorators.runtime.MappingPreProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Strategy for converting C3 types to ES properties
 * Created by Navíd Mitchell 🤪 on 4/28/23.
 */
@Component
public class ElasticConverterStrategy extends AbstractIdlConverterStrategy<Property, ElasticConversionState> {

    private final static List<SpecificC3TypeConverter<Property, ?, ElasticConversionState>> specificTypeConverters = List.of(
            new PrimitiveC3TypeToEsPrimitiveProperty(),
            new StringC3TypeToEsStringLikeProperty(),
            new DateC3TypeToEsDateProperty(),
            new ObjectC3TypeToEsObjectProperty(),
            new ArrayC3TypeToEsProperty()
    );

    public ElasticConverterStrategy(List<MappingPreProcessor<?>> mappingPreProcessors) {
        super(specificTypeConverters, List.of(new MappingPreProcessorConverter(mappingPreProcessors)));
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
