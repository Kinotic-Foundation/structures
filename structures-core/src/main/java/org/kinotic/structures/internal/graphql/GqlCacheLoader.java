package org.kinotic.structures.internal.graphql;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import graphql.GraphQL;
import graphql.language.OperationDefinition;
import graphql.schema.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.WordUtils;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.decorators.runtime.mapping.GraphQLTypeHolder;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.services.StructureConversionService;
import org.kinotic.structures.internal.api.services.StructureDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLEnumType.newEnum;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLInputObjectField.newInputObjectField;
import static graphql.schema.GraphQLInputObjectType.newInputObject;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * Created by Navíd Mitchell 🤪on 6/25/23.
 */
@Component
@RequiredArgsConstructor
public class GqlCacheLoader implements AsyncCacheLoader<String, GraphQL> {

    private static final Logger log = LoggerFactory.getLogger(GqlCacheLoader.class);

    private final StructureDAO structureDAO;
    private final StructureConversionService structureConversionService;
    private final GqlOperationProviderService gqlOperationProviderService;


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
                        GraphQLTypeReference graphQLTypeReference = new GraphQLTypeReference(outputType.getName());
                        GraphQLNamedOutputType pageResponseType = wrapForItemListResponse(graphQLTypeReference);

                        GqlFieldDefinitionData fieldDefinitionData = GqlFieldDefinitionData.builder()
                                                                                           .outputType(outputType)
                                                                                           .inputType(inputType)
                                                                                           .structuresName(structureName)
                                                                                           .pageableReference(pageableReference)
                                                                                           .pageResponseType(pageResponseType).build();

                        // Add all graphQL operations to the schema
                        for(GqlOperationDefinition definition : gqlOperationProviderService.getOperationDefinitions()){

                            Function<GqlFieldDefinitionData, GraphQLFieldDefinition> function = definition.getFieldDefinitionFunction();

                            if(definition.getOperationType() == OperationDefinition.Operation.QUERY) {

                                queryBuilder.field(function.apply(fieldDefinitionData));

                            }else if(definition.getOperationType() == OperationDefinition.Operation.MUTATION){

                                if(fieldDefinitionData.getInputType() != null){
                                    mutationBuilder.field(function.apply(fieldDefinitionData));
                                }

                            }else{
                                log.error("Unsupported operation {} type: {}", definition.getOperationNamePrefix(), definition.getOperationType());
                            }
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

//                    DataFetcher<?> entityDataFetcher = env -> {
//                        List<Map<String, Object>> representations = env.getArgument(_Entity.argumentName);
//                        log.warn("Entity data fetcher called with representations: {}", representations);
//                        return null;
//                    };
//                    TypeResolver entityTypeResolver = env -> {
//                        final Object obj = env.getObject();
//                        log.warn("Type resolver called with class: {}", obj.getClass());
//                        return null;
//                    };

//                    graphQLSchema = Federation.transform(graphQLSchema)
//                                              .setFederation2(true)
//                                              .fetchEntities(entityDataFetcher)
//                                              .resolveEntityType(entityTypeResolver)
//                                              .build();

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
                               .name("totalElements")
                               .type(GraphQLInt))
                .field(newFieldDefinition()
                               .name("content")
                               .type(GraphQLNonNull.nonNull(GraphQLList.list(GraphQLNonNull.nonNull(
                                       graphQlOutputStructureItem)))))
                .build();
    }
}
