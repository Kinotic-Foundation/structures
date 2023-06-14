package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch._types.mapping.ObjectProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import org.kinotic.continuum.idl.api.converter.IdlConverter;
import org.kinotic.continuum.idl.api.converter.IdlConverterFactory;
import org.kinotic.structures.api.decorators.runtime.mapping.GraphQLTypeHolder;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.services.OpenApiConversionResult;
import org.kinotic.structures.internal.api.services.StructureConversionService;
import org.kinotic.structures.internal.api.services.ElasticConversionResult;
import org.kinotic.structures.internal.idl.converters.elastic.ElasticConversionState;
import org.kinotic.structures.internal.idl.converters.elastic.ElasticConverterStrategy;
import org.kinotic.structures.internal.idl.converters.graphql.GraphQLConversionState;
import org.kinotic.structures.internal.idl.converters.graphql.GraphQLConverterStrategy;
import org.kinotic.structures.internal.idl.converters.openapi.OpenApiConversionState;
import org.kinotic.structures.internal.idl.converters.openapi.OpenApiConverterStrategy;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪on 5/11/23.
 */
@Component
public class DefaultStructureConversionService implements StructureConversionService {

    private final IdlConverterFactory idlConverterFactory;
    private final ElasticConverterStrategy elasticConverterStrategy;
    private final GraphQLConverterStrategy graphQLConverterStrategy;
    private final OpenApiConverterStrategy openApiConverterStrategy;

    public DefaultStructureConversionService(IdlConverterFactory idlConverterFactory,
                                             ElasticConverterStrategy elasticConverterStrategy,
                                             GraphQLConverterStrategy graphQLConverterStrategy,
                                             OpenApiConverterStrategy openApiConverterStrategy) {
        this.idlConverterFactory = idlConverterFactory;
        this.elasticConverterStrategy = elasticConverterStrategy;
        this.graphQLConverterStrategy = graphQLConverterStrategy;
        this.openApiConverterStrategy = openApiConverterStrategy;
    }

    public ElasticConversionResult convertToElasticMapping(Structure structure) {
        ObjectProperty ret;

        IdlConverter<Property, ElasticConversionState> converter = idlConverterFactory.createConverter(elasticConverterStrategy);

        converter.getConversionContext().state().setStructureBeingConverted(structure);

        Property esProperty = converter.convert(structure.getEntityDefinition());

        if(esProperty.isObject()){
            ret = esProperty.object();
        }else{
            throw new IllegalStateException("EntityDefinition must be an object");
        }

        return new ElasticConversionResult(ret, converter.getConversionContext().state().getDecoratedProperties());
    }

    @Override
    public GraphQLTypeHolder convertToGraphQLMapping(Structure structure) {
        IdlConverter<GraphQLTypeHolder, GraphQLConversionState> converter = idlConverterFactory.createConverter(graphQLConverterStrategy);

        converter.getConversionContext().state().setStructureBeingConverted(structure);

        return converter.convert(structure.getEntityDefinition());
    }

    @Override
    public OpenApiConversionResult convertToOpenApiMapping(Structure structure) {
        ObjectSchema ret;
        IdlConverter<Schema<?>, OpenApiConversionState> converter = idlConverterFactory.createConverter(openApiConverterStrategy);

        converter.getConversionContext().state().setStructureBeingConverted(structure);

        Schema<?> schema = converter.convert(structure.getEntityDefinition());
        if(schema instanceof ObjectSchema){
            ret = (ObjectSchema) schema;
        }else{
            throw new IllegalStateException("EntityDefinition did not convert to an OpenAPI ObjectSchema");
        }

        return new OpenApiConversionResult(ret, converter.getConversionContext().state().getReferenceSchemas());
    }
}
