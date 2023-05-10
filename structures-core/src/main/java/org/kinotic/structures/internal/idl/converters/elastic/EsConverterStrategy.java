package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.converter.GenericC3TypeConverter;
import org.kinotic.continuum.idl.api.converter.IdlConverterStrategy;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;

import java.util.Collections;
import java.util.List;

/**
 * Strategy for converting C3 types to ES properties
 * Created by Navíd Mitchell 🤪 on 4/28/23.
 */
public class EsConverterStrategy implements IdlConverterStrategy<Property, EsConversionInfo> {

    private final static List<SpecificC3TypeConverter<Property, ?, EsConversionInfo>> specificTypeConverters = List.of(
            new PrimitiveC3TypeToEsPrimitiveProperty(),
            new StringC3TypeToEsStringLikeProperty(),
            new DateC3TypeToEsDateProperty(),
            new ObjectC3TypeToEsObjectProperty(),
            new ArrayC3TypeToEsProperty()
    );

    @Override
    public List<SpecificC3TypeConverter<Property, ?, EsConversionInfo>> specificTypeConverters() {
        return specificTypeConverters;
    }

    @Override
    public List<GenericC3TypeConverter<Property, ?, EsConversionInfo>> genericTypeConverters() {
        return Collections.emptyList();
    }

    @Override
    public EsConversionInfo initialState() {
        return new EsConversionInfo();
    }

    @Override
    public boolean shouldCache() {
        return true;
    }
}
