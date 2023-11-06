package org.kinotic.structures.internal.api.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import graphql.GraphQL;
import graphql.schema.*;
import org.apache.commons.text.WordUtils;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.decorators.runtime.mapping.GraphQLTypeHolder;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.api.services.StructureConversionService;
import org.kinotic.structures.internal.api.services.StructureDAO;
import org.kinotic.structures.internal.graphql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static graphql.Scalars.*;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLEnumType.newEnum;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLInputObjectField.newInputObjectField;
import static graphql.schema.GraphQLInputObjectType.newInputObject;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * Created by Navíd Mitchell 🤪on 6/25/23.
 */
@Component
public class GraphQLCacheLoader implements AsyncCacheLoader<String, GraphQL> {

    private static final Logger log = LoggerFactory.getLogger(GraphQLCacheLoader.class);

    private final StructureDAO structureDAO;
    private final EntitiesService entitiesService;
    private final StructureConversionService structureConversionService;
    private final ObjectMapper objectMapper;

    public GraphQLCacheLoader(StructureDAO structureDAO,
                              EntitiesService entitiesService,
                              StructureConversionService structureConversionService,
                              ObjectMapper objectMapper) {
        this.structureDAO = structureDAO;
        this.entitiesService = entitiesService;
        this.structureConversionService = structureConversionService;
        this.objectMapper = objectMapper;
    }

    @Override
    public CompletableFuture<GraphQL> asyncLoad(String key, Executor executor) throws Exception {
        return createGraphQlSchema(key, executor)
                .thenCompose(schema -> {

                    GraphQL.Builder builder = GraphQL.newGraphQL(schema)
                                                     .preparsedDocumentProvider(new CachingPreparsedDocumentProvider());

                    return CompletableFuture.completedFuture(builder.build());
                });
    }

    private CompletableFuture<GraphQLSchema> createGraphQlSchema(String namespace,
                                                                 Executor executor) throws Exception {
        return structureDAO
                .findAllPublishedForNamespace(namespace, Pageable.ofSize(100))
                .thenComposeAsync(structures -> {

                    long start = System.currentTimeMillis();
                    log.debug("Creating GraphQL Schema for namespace: {}", namespace);

                    Map<String, GraphQLTypeHolder> structureTypeMap = new HashMap<>();
                    for (Structure structure : structures.getContent()) {
                        structureTypeMap.put(structure.getId(),
                                             structureConversionService.convertToGraphQLMapping(structure));
                    }

                    GraphQLInputObjectType pageableType = createPageableType();
                    GraphQLTypeReference pageableReference = new GraphQLTypeReference(pageableType.getName());

                    GraphQLObjectType.Builder queryBuilder = newObject().name("Query");
                    GraphQLObjectType.Builder mutationBuilder = newObject().name("Mutation");

                    for (Map.Entry<String, GraphQLTypeHolder> entry : structureTypeMap.entrySet()) {

                        GraphQLObjectType outputType;
                        if (entry.getValue().getOutputType() instanceof GraphQLObjectType) {
                            outputType = (GraphQLObjectType) entry.getValue().getOutputType();
                        } else {
                            throw new IllegalStateException("Output type must be a GraphQLObjectType");
                        }

                        GraphQLInputObjectType inputType = null;
                        // Will be null if a UnionC3Type is found anywhere in the object graph
                        if (entry.getValue().getInputType() != null) {
                            if (entry.getValue().getInputType() instanceof GraphQLInputObjectType) {
                                inputType = (GraphQLInputObjectType) entry.getValue().getInputType();
                            } else {
                                throw new IllegalStateException("Input type must be a GraphQLInputObjectType");
                            }
                        }

                        String structureName = WordUtils.capitalize(outputType.getName());

                        queryBuilder.field(newFieldDefinition()
                                                   .name("get" + structureName + "ById")
                                                   .type(outputType)
                                                   .argument(newArgument().name("id")
                                                                          .type(GraphQLNonNull.nonNull(GraphQLID)))
                                                   .dataFetcher(new GetItemDataFetcher(entry.getKey(),
                                                                                       entitiesService)));

                        GraphQLTypeReference graphQLTypeReference = new GraphQLTypeReference(outputType.getName());
                        GraphQLNamedOutputType listResponse = wrapForItemListResponse(graphQLTypeReference);

                        queryBuilder.field(newFieldDefinition()
                                                   .name("findAll" + structureName)
                                                   .type(listResponse)
                                                   .argument(newArgument().name("pageable")
                                                                          .type(GraphQLNonNull.nonNull(
                                                                                  pageableReference)))
                                                   .dataFetcher(new GetAllItemsDataFetcher(entry.getKey(),
                                                                                           entitiesService,
                                                                                           objectMapper)));

                        queryBuilder.field(newFieldDefinition()
                                                   .name("search" + outputType.getName())
                                                   .type(listResponse)
                                                   .argument(newArgument().name("searchText")
                                                                          .type(GraphQLNonNull.nonNull(GraphQLString)))
                                                   .argument(newArgument().name("pageable")
                                                                          .type(GraphQLNonNull.nonNull(
                                                                                  pageableReference)))
                                                   .dataFetcher(new SearchItemDataFetcher(entry.getKey(),
                                                                                          entitiesService,
                                                                                          objectMapper)));

                        // Add Mutations if an input type was provided
                        if (inputType != null) {
                            mutationBuilder.field(newFieldDefinition()
                                                          .name("save" + structureName)
                                                          .type(outputType)
                                                          .argument(newArgument().name("input")
                                                                                 .type(GraphQLNonNull.nonNull(
                                                                                         inputType)))
                                                          .dataFetcher(new SaveDataFetcher(entry.getKey(),
                                                                                           entitiesService)));

                            mutationBuilder.field(newFieldDefinition()
                                                          .name("bulkSave" + structureName)
                                                          .type(GraphQLBoolean)
                                                          .argument(newArgument().name("input")
                                                                                 .type(GraphQLNonNull.nonNull(
                                                                                         GraphQLList.list(
                                                                                                 GraphQLNonNull.nonNull(
                                                                                                         inputType)))))
                                                          .dataFetcher(new BulkSaveDataFetcher(entry.getKey(),
                                                                                               entitiesService)));

                            mutationBuilder.field(newFieldDefinition()
                                                          .name("delete" + structureName)
                                                          .type(GraphQLID)
                                                          .argument(newArgument().name("id")
                                                                                 .type(GraphQLNonNull.nonNull(
                                                                                         GraphQLID)))
                                                          .dataFetcher(new DeleteDataFetcher(entry.getKey(),
                                                                                             entitiesService)));
                        }

                    }

                    GraphQLSchema graphQLSchema = GraphQLSchema.newSchema()
                                                               .query(queryBuilder.build())
                                                               .mutation(mutationBuilder.build())
                                                               .additionalType(pageableType)
                                                               .build();

                    log.debug("Finished creating GraphQL Schema for namespace: {} in {}ms",
                              namespace,
                              System.currentTimeMillis() - start);

                    return CompletableFuture.completedFuture(graphQLSchema);
                }, executor);
    }

    private GraphQLInputObjectType createOrderType() {

        GraphQLEnumType enumType = newEnum().name("Direction")
                                            .value("ASC")
                                            .value("DESC")
                                            .build();

        GraphQLInputObjectType.Builder inputBuilder = newInputObject().name("Order");

        inputBuilder.field(newInputObjectField()
                                   .name("direction")
                                   .type(GraphQLNonNull.nonNull(enumType)))
                    .field(newInputObjectField()
                                   .name("property")
                                   .type(GraphQLNonNull.nonNull(GraphQLString)));

        return inputBuilder.build();
    }

    private GraphQLInputObjectType createSortType() {
        GraphQLInputObjectType.Builder inputBuilder = newInputObject().name("Sort");
        inputBuilder.field(newInputObjectField()
                                   .name("orders")
                                   .type(GraphQLNonNull.nonNull(GraphQLList.list(GraphQLNonNull.nonNull(
                                           createOrderType())))));
        return inputBuilder.build();
    }

    private GraphQLInputObjectType createPageableType() {
        GraphQLInputObjectType.Builder inputBuilder = newInputObject().name("Pageable");
        inputBuilder.field(newInputObjectField()
                                   .name("pageNumber")
                                   .type(GraphQLNonNull.nonNull(GraphQLInt)))
                    .field(newInputObjectField()
                                   .name("pageSize")
                                   .type(GraphQLNonNull.nonNull(GraphQLInt)))
                    .field(newInputObjectField()
                                   .name("sort")
                                   .type(createSortType()));
        return inputBuilder.build();
    }

    private GraphQLNamedOutputType wrapForItemListResponse(GraphQLNamedOutputType graphQlOutputStructureItem) {
        return newObject()
                .name(graphQlOutputStructureItem.getName() + "Page")
                .field(newFieldDefinition()
                               .name("size")
                               .type(GraphQLInt))
                .field(newFieldDefinition()
                               .name("totalElements")
                               .type(GraphQLInt))
                .field(newFieldDefinition()
                               .name("content")
                               .type(GraphQLNonNull.nonNull(GraphQLList.list(GraphQLNonNull.nonNull(
                                       graphQlOutputStructureItem)))))
                .build();
    }
}
