package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch._types.mapping.ObjectProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.converter.IdlConverter;
import org.kinotic.continuum.idl.api.converter.IdlConverterFactory;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.services.StructureConversionService;
import org.kinotic.structures.internal.api.services.ElasticConversionResult;
import org.kinotic.structures.internal.idl.converters.elastic.ElasticConversionState;
import org.kinotic.structures.internal.idl.converters.elastic.ElasticConverterStrategy;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪 on 5/11/23.
 */
@Component
public class DefaultStructureConversionService implements StructureConversionService {

    private final IdlConverterFactory idlConverterFactory;
    private final ElasticConverterStrategy elasticConverterStrategy;

    public DefaultStructureConversionService(IdlConverterFactory idlConverterFactory,
                                             ElasticConverterStrategy elasticConverterStrategy) {
        this.idlConverterFactory = idlConverterFactory;
        this.elasticConverterStrategy = elasticConverterStrategy;
    }

    public ElasticConversionResult convertToElasticMapping(Structure structure) {
        ObjectProperty objectProperty;

        IdlConverter<Property, ElasticConversionState> converter = idlConverterFactory.createConverter(elasticConverterStrategy);

        converter.getConversionContext().state().setStructureBeingConverted(structure);

        Property esProperty = converter.convert(structure.getEntityDefinition());

        if(esProperty.isObject()){
            objectProperty = esProperty.object();
        }else{
            throw new IllegalStateException("EntityDefinition must be an object");
        }

        return new ElasticConversionResult(objectProperty, converter.getConversionContext().state().getDecoratedProperties());
    }
}
