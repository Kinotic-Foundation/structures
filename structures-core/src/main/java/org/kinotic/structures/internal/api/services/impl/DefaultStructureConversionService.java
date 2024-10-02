package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch._types.mapping.ObjectProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.swagger.v3.oas.models.media.Schema;
import lombok.RequiredArgsConstructor;
import org.kinotic.continuum.idl.api.converter.IdlConverter;
import org.kinotic.continuum.idl.api.converter.IdlConverterFactory;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.services.ElasticConversionResult;
import org.kinotic.structures.internal.api.services.StructureConversionService;
import org.kinotic.structures.internal.idl.converters.elastic.ElasticConversionState;
import org.kinotic.structures.internal.idl.converters.elastic.ElasticConverterStrategy;
import org.kinotic.structures.internal.idl.converters.graphql.GqlConversionState;
import org.kinotic.structures.internal.idl.converters.graphql.GqlConverterStrategy;
import org.kinotic.structures.internal.idl.converters.graphql.GqlTypeHolder;
import org.kinotic.structures.internal.idl.converters.openapi.OpenApiConversionState;
import org.kinotic.structures.internal.idl.converters.openapi.OpenApiConverterStrategy;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪on 5/11/23.
 */
@Component
@RequiredArgsConstructor
public class DefaultStructureConversionService implements StructureConversionService {

    private final IdlConverterFactory idlConverterFactory;
    private final StructuresProperties structuresProperties;

    @WithSpan
    @Override
    public ElasticConversionResult convertToElasticMapping(Structure structure) {
        ObjectProperty ret;

        IdlConverter<Property, ElasticConversionState> converter = idlConverterFactory
                .createConverter(new ElasticConverterStrategy(structuresProperties));

        ElasticConversionState state = converter.getConversionContext().state();

        Property esProperty = converter.convert(structure.getEntityDefinition());

        if(esProperty.isObject()){
            ret = esProperty.object();
        }else{
            throw new IllegalStateException("EntityDefinition must be an object");
        }

        return new ElasticConversionResult(state.getDecoratedProperties(), state.getMultiTenancyType(), ret);
    }

    @Override
    public IdlConverter<GqlTypeHolder, GqlConversionState> createGqlConverter() {
        return idlConverterFactory.createConverter(new GqlConverterStrategy(structuresProperties));
    }

    @Override
    public IdlConverter<Schema<?>, OpenApiConversionState> createOpenApiConverter(){
        return idlConverterFactory.createConverter(new OpenApiConverterStrategy(structuresProperties));
    }

}
