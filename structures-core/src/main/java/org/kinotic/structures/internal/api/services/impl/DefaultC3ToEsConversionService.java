package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch._types.mapping.ObjectProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.converter.IdlConverter;
import org.kinotic.continuum.idl.api.converter.IdlConverterFactory;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.structures.internal.api.services.C3ToEsConversionService;
import org.kinotic.structures.internal.api.services.EsConversionResult;
import org.kinotic.structures.internal.idl.converters.elastic.EsConversionState;
import org.kinotic.structures.internal.idl.converters.elastic.EsConverterStrategy;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪 on 5/11/23.
 */
@Component
public class DefaultC3ToEsConversionService implements C3ToEsConversionService {

    private final IdlConverterFactory idlConverterFactory;
    private final EsConverterStrategy esConverterStrategy;

    public DefaultC3ToEsConversionService(IdlConverterFactory idlConverterFactory,
                                          EsConverterStrategy esConverterStrategy) {
        this.idlConverterFactory = idlConverterFactory;
        this.esConverterStrategy = esConverterStrategy;
    }

    public EsConversionResult convert(ObjectC3Type objectC3Type) {
        ObjectProperty objectProperty;
        IdlConverter<Property, EsConversionState> converter = idlConverterFactory.createConverter(esConverterStrategy);
        Property esProperty = converter.convert(objectC3Type);

        if(esProperty.isObject()){
            objectProperty = esProperty.object();
        }else{
            throw new IllegalStateException("EntityDefinition must be an object");
        }
        return new EsConversionResult(objectProperty, converter.getConversionContext().state().getDecoratedProperties());
    }
}
