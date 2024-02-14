package org.kinotic.structures.internal.api.services;

import org.kinotic.structures.api.domain.Structure;

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
     * Converts the given {@link Structure#getEntityDefinition()} to an OpenAPI Schema
     * @param structure to convert
     * @return the {@link OpenApiConversionResult} created for the {@link Structure}
     */
    OpenApiConversionResult convertToOpenApiMapping(Structure structure);

}
