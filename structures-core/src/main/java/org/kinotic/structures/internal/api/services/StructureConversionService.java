package org.kinotic.structures.internal.api.services;

import io.swagger.v3.oas.models.media.Schema;
import org.kinotic.continuum.idl.api.converter.IdlConverter;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.idl.converters.openapi.OpenApiConversionState;

/**
 * Handles converting {@link Structure}s to various mappings. Such as ElasticSearch, OpenAPI, GraphQL.
 * Created by Navíd Mitchell 🤪on 5/11/23.
 */
public interface StructureConversionService {

    /**
     * Converts the given {@link Structure#getEntityDefinition()} to an ElasticSearch ObjectProperty
     * @param structure to convert
     * @return the {@link ElasticConversionResult} created for the {@link Structure}
     */
    ElasticConversionResult convertToElasticMapping(Structure structure);

    /**
     * Converts the given {@link Structure#getEntityDefinition()} to an OpenAPI Schema
     * @param structure to convert
     * @return the {@link GqlConversionResult} created for the {@link Structure}
     */
    GqlConversionResult convertToGqlMapping(Structure structure);

    /**
     * Creates a new {@link IdlConverter} for converting {@link org.kinotic.continuum.idl.api.schema.C3Type}s to an OpenAPI Schema
     * @return the new {@link IdlConverter}
     */
    IdlConverter<Schema<?>, OpenApiConversionState> createOpenApiConverter();

}
