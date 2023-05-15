package org.kinotic.structures.internal.api.services.impl;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import graphql.schema.*;
import org.kinotic.structures.api.decorators.runtime.GraphQLTypeHolder;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.internal.api.services.ExecutionGraphQlServiceProvider;
import org.kinotic.structures.internal.api.services.StructureConversionService;
import org.kinotic.structures.internal.graphql.*;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.ExecutionGraphQlService;
import org.springframework.graphql.execution.DefaultBatchLoaderRegistry;
import org.springframework.graphql.execution.DefaultExecutionGraphQlService;
import org.springframework.graphql.execution.GraphQlSource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import static graphql.Scalars.*;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * Created by Navíd Mitchell 🤪 on 4/16/23.
 */
@Component
public class DefaultExecutionGraphQlServiceProvider implements ExecutionGraphQlServiceProvider {

    private final AsyncLoadingCache<String, ExecutionGraphQlService> cache;

    public DefaultExecutionGraphQlServiceProvider(StructureService structureService,
                                                  EntitiesService entitiesService,
                                                  StructureConversionService structureConversionService) {
        this.cache = Caffeine.newBuilder()
                             .expireAfterAccess(1, TimeUnit.HOURS)
                             .maximumSize(10_000)
                             .buildAsync(new ExecutionGraphQlServiceCacheLoader(structureService,
                                                                                entitiesService,
                                                                                structureConversionService));
    }

    @Override
    public Mono<ExecutionGraphQlService> getService(String namespace) {
        return Mono.fromCompletionStage(cache.get(namespace));
    }

    @Override
    public void evictCacheFor(String namespace) {
        cache.asMap().remove(namespace);
    }


    private static class ExecutionGraphQlServiceCacheLoader implements AsyncCacheLoader<String, ExecutionGraphQlService> {

        private final StructureService structureService;
        private final EntitiesService entitiesService;
        private final StructureConversionService structureConversionService;

        public ExecutionGraphQlServiceCacheLoader(StructureService structureService,
                                                  EntitiesService entitiesService,
                                                  StructureConversionService structureConversionService) {
            this.structureService = structureService;
            this.entitiesService = entitiesService;
            this.structureConversionService = structureConversionService;
        }

        @Override
        public CompletableFuture<ExecutionGraphQlService> asyncLoad(String key,
                                                                    Executor executor) throws Exception {
            return createGraphQlSchema(key, executor)
                    .thenCompose(schema -> {
                        GraphQlSource graphQlSource = GraphQlSource.builder(schema)
                                                                   .configureGraphQl(builder -> builder.preparsedDocumentProvider(
                                                                           new CachingPreparsedDocumentProvider()))
                                                                   .build();

                        DefaultExecutionGraphQlService service = new DefaultExecutionGraphQlService(graphQlSource);
                        service.addDataLoaderRegistrar(new DefaultBatchLoaderRegistry());

                        return CompletableFuture.completedFuture(service);
                    });
        }

        private CompletableFuture<GraphQLSchema> createGraphQlSchema(String namespace, Executor executor) throws Exception {
            return structureService.findAllPublishedForNamespace(namespace, Pageable.ofSize(100))
                                   .thenComposeAsync(structures -> {

                                       Map<String, GraphQLTypeHolder> structureTypeMap = new HashMap<>();
                                       for(Structure structure : structures.getContent()){
                                           structureTypeMap.put(structure.getId(),
                                                                structureConversionService.convertToGraphQLMapping(structure));
                                       }

                                       GraphQLObjectType.Builder queryBuilder = newObject().name("Query");
                                       GraphQLObjectType.Builder mutationBuilder = newObject().name("Mutation");

                                       for (Map.Entry<String, GraphQLTypeHolder> entry : structureTypeMap.entrySet()) {

                                           GraphQLObjectType outputType;
                                           if (entry.getValue().getOutputType() instanceof GraphQLObjectType) {
                                               outputType = (GraphQLObjectType) entry.getValue().getOutputType();
                                           } else {
                                               throw new IllegalStateException("Output type must be a GraphQLObjectType");
                                           }

                                           GraphQLInputObjectType inputType;
                                           if (entry.getValue().getInputType() instanceof GraphQLInputObjectType) {
                                               inputType = (GraphQLInputObjectType) entry.getValue().getInputType();
                                           } else {
                                               throw new IllegalStateException("Input type must be a GraphQLInputObjectType");
                                           }

                                           queryBuilder.field(newFieldDefinition()
                                                                      .name(outputType.getName())
                                                                      .type(outputType)
                                                                      .argument(newArgument().name("id")
                                                                                             .type(GraphQLNonNull.nonNull(GraphQLID)))
                                                                      .dataFetcher(new GetItemDataFetcher(entry.getKey(),
                                                                                                          entitiesService)));

                                           GraphQLTypeReference graphQLTypeReference = new GraphQLTypeReference(
                                                   outputType.getName());
                                           GraphQLNamedOutputType listResponse = wrapForItemListResponse(
                                                   graphQLTypeReference);

                                           queryBuilder.field(newFieldDefinition()
                                                                      .name(outputType.getName() + "s")
                                                                      .type(listResponse)
                                                                      .argument(newArgument().name("offset")
                                                                                             .type(GraphQLNonNull.nonNull(GraphQLInt)))
                                                                      .argument(newArgument().name("limit")
                                                                                             .type(GraphQLNonNull.nonNull(GraphQLInt)))
                                                                      .dataFetcher(new GetAllItemsDataFetcher(entry.getKey(),
                                                                                                              entitiesService)));

                                           queryBuilder.field(newFieldDefinition()
                                                                      .name("search" + outputType.getName())
                                                                      .type(listResponse)
                                                                      .argument(newArgument().name("search")
                                                                                             .type(GraphQLNonNull.nonNull(GraphQLString)))
                                                                      .argument(newArgument().name("offset")
                                                                                             .type(GraphQLNonNull.nonNull(GraphQLInt)))
                                                                      .argument(newArgument().name("limit")
                                                                                             .type(GraphQLNonNull.nonNull(GraphQLInt)))
                                                                      .dataFetcher(new SearchItemDataFetcher(entry.getKey(),
                                                                                                             entitiesService)));

                                           mutationBuilder.field(newFieldDefinition()
                                                                         .name("upsert" + inputType.getName())
                                                                         .type(outputType)
                                                                         .argument(newArgument().name("input")
                                                                                                .type(GraphQLNonNull.nonNull(inputType)))
                                                                         .dataFetcher(new UpsertDataFetcher(entry.getKey(),
                                                                                                            entitiesService)));

                                           mutationBuilder.field(newFieldDefinition()
                                                                         .name("delete" + inputType.getName())
                                                                         .type(GraphQLID)
                                                                         .argument(newArgument().name("id")
                                                                                                .type(GraphQLNonNull.nonNull(GraphQLString)))
                                                                         .dataFetcher(new DeleteDataFetcher(entry.getKey(),
                                                                                                            entitiesService)));

                                       }

                                       return CompletableFuture.completedFuture(GraphQLSchema.newSchema()
                                                                                             .query(queryBuilder.build())
                                                                                             .mutation(mutationBuilder.build())
                                                                                             .build());
                                   }, executor);
        }

        private GraphQLNamedOutputType wrapForItemListResponse(GraphQLNamedOutputType graphQlOutputStructureItem) {
            return newObject()
                    .name(graphQlOutputStructureItem.getName() + "Page")
                    .field(newFieldDefinition()
                                   .name("totalElements")
                                   .type(GraphQLInt))
                    .field(newFieldDefinition()
                                   .name("content")
                                   .type(GraphQLList.list(graphQlOutputStructureItem)))
                    .build();
        }
    }
}
