package org.kinotic.structures.internal.endpoints.graphql;

import com.apollographql.federation.graphqljava.Federation;
import com.apollographql.federation.graphqljava._Entity;
import com.apollographql.federation.graphqljava.printer.ServiceSDLPrinter;
import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import graphql.GraphQL;
import graphql.language.OperationDefinition;
import graphql.schema.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.WordUtils;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.continuum.idl.api.converter.IdlConverter;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.services.StructureConversionService;
import org.kinotic.structures.internal.api.services.StructureDAO;
import org.kinotic.structures.internal.idl.converters.graphql.GqlConversionState;
import org.kinotic.structures.internal.idl.converters.graphql.GqlTypeHolder;
import org.kinotic.structures.internal.utils.GqlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
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
public class GqlSchemaCacheLoader implements AsyncCacheLoader<String, GraphQL> {

    private static final Logger log = LoggerFactory.getLogger(GqlSchemaCacheLoader.class);
    private static final NoOpTypeResolver NO_OP_TYPE_RESOLVER = new NoOpTypeResolver();

    private final StructureDAO structureDAO;
    private final StructureConversionService structureConversionService;
    private final GqlOperationDefinitionService gqlOperationDefinitionService;


    @Override
    public CompletableFuture<GraphQL> asyncLoad(String key, Executor executor) throws Exception {
        long now = System.nanoTime();
        return createGraphQlSchema(key, executor)
                .thenCompose(schema -> {

                    GraphQL.Builder builder = GraphQL.newGraphQL(schema)
                                                     .preparsedDocumentProvider(new CachingPreparsedDocumentProvider());
                    GraphQL graphQL = builder.build();

                    log.debug("Finished creating GraphQL Schema for namespace: {} in {}ms",
                              key,
                              TimeUnit.MILLISECONDS.convert(System.nanoTime() - now, TimeUnit.NANOSECONDS));

                    return CompletableFuture.completedFuture(graphQL);
                });
    }

    private CompletableFuture<GraphQLSchema> createGraphQlSchema(String namespace,
                                                                 Executor executor) throws Exception {
        return structureDAO
                .findAllPublishedForNamespace(namespace, Pageable.ofSize(100))
                .thenComposeAsync(structures -> {

                    log.debug("Creating GraphQL Schema for namespace: {}", namespace);

                    GraphQLInputObjectType pageableType = createPageableType();
                    GraphQLTypeReference pageableReference = new GraphQLTypeReference(pageableType.getName());

                    GraphQLObjectType.Builder queryBuilder = newObject().name("Query");
                    GraphQLObjectType.Builder mutationBuilder = newObject().name("Mutation");

                    IdlConverter<GqlTypeHolder, GqlConversionState> converter
                            = structureConversionService.createGqlConverter();

                    for (Structure structure : structures.getContent()) {

                        GqlTypeHolder structureType = converter.convert(structure.getEntityDefinition());
                        GraphQLObjectType outputType;
                        if (structureType.getOutputType() instanceof GraphQLObjectType) {
                            outputType = (GraphQLObjectType) structureType.getOutputType();
                        } else {
                            throw new IllegalStateException("Output type must be a GraphQLObjectType");
                        }

                        GraphQLInputObjectType inputType = getGraphQLInputObjectType(structureType);

                        GraphQLTypeReference graphQLTypeReference = new GraphQLTypeReference(outputType.getName());
                        GraphQLNamedOutputType pageResponseType = GqlUtils.wrapTypeWithPage(graphQLTypeReference);

                        String structureName = WordUtils.capitalize(structure.getName());
                        GqlFieldDefinitionData fieldDefinitionData = GqlFieldDefinitionData.builder()
                                                                                           .converter(converter)
                                                                                           .inputType(inputType)
                                                                                           .outputType(outputType)
                                                                                           .pageableReference(pageableReference)
                                                                                           .pageResponseType(pageResponseType)
                                                                                           .structureName(structureName)
                                                                                           .build();

                        // Add all graphQL operations to the schema
                        addOperations(fieldDefinitionData, mutationBuilder, queryBuilder, structure);
                    }


                    // Add all type resolvers to the schema
                    GraphQLCodeRegistry.Builder codeRegistryBuilder = GraphQLCodeRegistry.newCodeRegistry();
                    for (GraphQLUnionType unionType : converter.getConversionContext()
                                                               .state()
                                                               .getUnionTypes()) {
                        // NoOp is used since we do not actually use the GraphQL api to execute operations
                        codeRegistryBuilder.typeResolver(unionType, NO_OP_TYPE_RESOLVER);
                    }

                    // Add all additional types to the schema
                    Map<String, GraphQLType> additionalTypes = new HashMap<>(converter.getConversionContext()
                                                                                      .state()
                                                                                      .getReferencedTypes());


                    GraphQLSchema.Builder graphQLSchemaBuilder = GraphQLSchema.newSchema()
                                                                              .codeRegistry(codeRegistryBuilder.build())
                                                                              .additionalType(pageableType);

                    GraphQLObjectType query = queryBuilder.build();
                    if (!query.getFieldDefinitions().isEmpty()) {
                        graphQLSchemaBuilder.query(query);
                    }else{
                        // This namespace has no published structures, so we add a dummy query to prevent errors
                        queryBuilder.field(newFieldDefinition().name("empty").type(GraphQLString));
                        graphQLSchemaBuilder.query(queryBuilder.build());
                    }
                    GraphQLObjectType mutation = mutationBuilder.build();
                    if (!mutation.getFieldDefinitions().isEmpty()) {
                        graphQLSchemaBuilder.mutation(mutation);
                    }

                    graphQLSchemaBuilder.additionalTypes(Set.copyOf(additionalTypes.values()));
                    GraphQLSchema graphQLSchema = graphQLSchemaBuilder.build();

                    // Add GQL federation support (Fetcher and Resolver below not really needed unless we want to allow entities to contribute to one another)
                    DataFetcher<?> entityDataFetcher = env -> {
                        List<Map<String, Object>> representations = env.getArgument(_Entity.argumentName);
                        log.warn("Entity data fetcher called with representations: {}", representations);
                        return null;
                    };

                    graphQLSchema = Federation.transform(graphQLSchema)
                                              .setFederation2(true)
                                              .fetchEntities(entityDataFetcher)
                                              .resolveEntityType(NO_OP_TYPE_RESOLVER) // No type resolvers needed since we don't actually use the GraphQL API to execute operations
                                              .build();

                    if(log.isTraceEnabled()){
                        log.trace(ServiceSDLPrinter.generateServiceSDLV2(graphQLSchema));
                    }

                    return CompletableFuture.completedFuture(graphQLSchema);
                }, executor);
    }

    /**
     * Adds all operations to the query and mutation builders
     * @param fieldDefinitionData the data needed to build the operations
     * @param mutationBuilder the mutation builder
     * @param queryBuilder the query builder
     */
    private void addOperations(GqlFieldDefinitionData fieldDefinitionData,
                               GraphQLObjectType.Builder mutationBuilder,
                               GraphQLObjectType.Builder queryBuilder,
                               Structure structure) {

        // Add built in operations
        for (GqlOperationDefinition definition : gqlOperationDefinitionService.getBuiltInOperationDefinitions()) {
            addOperation(definition, fieldDefinitionData, mutationBuilder, queryBuilder);
        }

        // Add named query operations
        for(GqlOperationDefinition definition : gqlOperationDefinitionService.getNamedQueryOperationDefinitions(structure)){
            addOperation(definition, fieldDefinitionData, mutationBuilder, queryBuilder);
        }
    }

    private static void addOperation(GqlOperationDefinition definition,
                                     GqlFieldDefinitionData fieldDefinitionData,
                                     GraphQLObjectType.Builder mutationBuilder,
                                     GraphQLObjectType.Builder queryBuilder) {
        Function<GqlFieldDefinitionData, GraphQLFieldDefinition> function = definition.getFieldDefinitionFunction();

        if (definition.getOperationType() == OperationDefinition.Operation.QUERY) {

            queryBuilder.field(function.apply(fieldDefinitionData));

        } else if (definition.getOperationType() == OperationDefinition.Operation.MUTATION) {

            if (fieldDefinitionData.getInputType() != null) {
                mutationBuilder.field(function.apply(fieldDefinitionData));
            }

        } else {
            log.error("Unsupported operation {} type: {}",
                      definition.getOperationName(),
                      definition.getOperationType());
        }
    }

    private static GraphQLInputObjectType getGraphQLInputObjectType(GqlTypeHolder gqlTypeHolder) {
        GraphQLInputObjectType inputType = null;
        // Will be null if a UnionC3Type is found anywhere in the object graph
        if (gqlTypeHolder.getInputType() != null) {
            if (gqlTypeHolder.getInputType() instanceof GraphQLInputObjectType) {
                inputType = (GraphQLInputObjectType) gqlTypeHolder.getInputType();
            } else {
                throw new IllegalStateException("Input type must be a GraphQLInputObjectType");
            }
        }
        return inputType;
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

}
