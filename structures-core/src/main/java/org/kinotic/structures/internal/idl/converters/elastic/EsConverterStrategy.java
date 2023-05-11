package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.internal.api.converter.AbstractIdlConverterStrategy;

import java.util.Collections;
import java.util.List;

/**
 * Strategy for converting C3 types to ES properties
 * Created by Navíd Mitchell 🤪 on 4/28/23.
 */
public class EsConverterStrategy extends AbstractIdlConverterStrategy<Property, EsConversionState> {

    private final static List<SpecificC3TypeConverter<Property, ?, EsConversionState>> specificTypeConverters = List.of(
            new PrimitiveC3TypeToEsPrimitiveProperty(),
            new StringC3TypeToEsStringLikeProperty(),
            new DateC3TypeToEsDateProperty(),
            new ObjectC3TypeToEsObjectProperty(),
            new ArrayC3TypeToEsProperty()
    );

    public EsConverterStrategy() {
        super(specificTypeConverters, Collections.emptyList());
    }

    @Override
    public EsConversionState initialState() {
        return new EsConversionState();
    }

    @Override
    public boolean shouldCache() {
        return true;
    }
}
