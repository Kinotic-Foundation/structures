package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch._types.mapping.ObjectProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import org.kinotic.continuum.idl.api.converter.IdlConverter;
import org.kinotic.continuum.idl.api.converter.IdlConverterFactory;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.internal.idl.converters.graphql.GqlTypeHolder;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.services.ElasticConversionResult;
import org.kinotic.structures.internal.api.services.GqlConversionResult;
import org.kinotic.structures.internal.api.services.OpenApiConversionResult;
import org.kinotic.structures.internal.api.services.StructureConversionService;
import org.kinotic.structures.internal.idl.converters.elastic.ElasticConversionState;
import org.kinotic.structures.internal.idl.converters.elastic.ElasticConverterStrategy;
import org.kinotic.structures.internal.idl.converters.graphql.GqlConversionState;
import org.kinotic.structures.internal.idl.converters.graphql.GqlConverterStrategy;
import org.kinotic.structures.internal.idl.converters.openapi.OpenApiConversionState;
import org.kinotic.structures.internal.idl.converters.openapi.OpenApiConverterStrategy;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪on 5/11/23.
 */
@Component
public class DefaultStructureConversionService implements StructureConversionService {

    private final StructuresProperties structuresProperties;
    private final IdlConverterFactory idlConverterFactory;
    private final ElasticConverterStrategy elasticConverterStrategy;
    private final GqlConverterStrategy gqlConverterStrategy;
    private final OpenApiConverterStrategy openApiConverterStrategy;

    public DefaultStructureConversionService(StructuresProperties structuresProperties,
                                             IdlConverterFactory idlConverterFactory,
                                             ElasticConverterStrategy elasticConverterStrategy,
                                             GqlConverterStrategy gqlConverterStrategy,
                                             OpenApiConverterStrategy openApiConverterStrategy) {
        this.structuresProperties = structuresProperties;
        this.idlConverterFactory = idlConverterFactory;
        this.elasticConverterStrategy = elasticConverterStrategy;
        this.gqlConverterStrategy = gqlConverterStrategy;
        this.openApiConverterStrategy = openApiConverterStrategy;
    }

    public ElasticConversionResult convertToElasticMapping(Structure structure) {
        ObjectProperty ret;

        IdlConverter<Property, ElasticConversionState> converter = idlConverterFactory.createConverter(elasticConverterStrategy);

        ElasticConversionState state = converter.getConversionContext().state();
        state.setStructureBeingConverted(structure);
        state.setStructuresProperties(structuresProperties);

        Property esProperty = converter.convert(structure.getEntityDefinition());

        if(esProperty.isObject()){
            ret = esProperty.object();
        }else{
            throw new IllegalStateException("EntityDefinition must be an object");
        }

        return new ElasticConversionResult(ret, state.getDecoratedProperties(), state.getMultiTenancyType());
    }

    @Override
    public GqlConversionResult convertToGqlMapping(Structure structure) {
        IdlConverter<GqlTypeHolder, GqlConversionState> converter = idlConverterFactory.createConverter(
                gqlConverterStrategy);

        GqlConversionState state = converter.getConversionContext().state();
        state.setStructureBeingConverted(structure);
        state.setStructuresProperties(structuresProperties);

        GqlTypeHolder typeHolder = converter.convert(structure.getEntityDefinition());

        return new GqlConversionResult(typeHolder, state.getUnionTypes(), state.getReferencedTypes());
    }

    @Override
    public OpenApiConversionResult convertToOpenApiMapping(Structure structure) {
        ObjectSchema ret;
        IdlConverter<Schema<?>, OpenApiConversionState> converter = idlConverterFactory.createConverter(openApiConverterStrategy);

        OpenApiConversionState state = converter.getConversionContext().state();
        state.setStructureBeingConverted(structure);
        state.setStructuresProperties(structuresProperties);

        Schema<?> schema = converter.convert(structure.getEntityDefinition());
        if(schema instanceof ObjectSchema){
            ret = (ObjectSchema) schema;
        }else{
            throw new IllegalStateException("EntityDefinition did not convert to an OpenAPI ObjectSchema");
        }

        return new OpenApiConversionResult(ret, state.getReferencedSchemas());
    }
}
