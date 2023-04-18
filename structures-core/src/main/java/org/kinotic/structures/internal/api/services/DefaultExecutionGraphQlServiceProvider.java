package org.kinotic.structures.internal.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import graphql.GraphQL;
import graphql.scalars.ExtendedScalars;
import graphql.schema.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.kinotic.continuum.api.jsonSchema.*;
import org.kinotic.continuum.api.jsonSchema.datestyles.MillsDateStyle;
import org.kinotic.continuum.api.jsonSchema.datestyles.StringDateStyle;
import org.kinotic.continuum.api.jsonSchema.datestyles.UnixDateStyle;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.StructureHolder;
import org.kinotic.structures.api.domain.Structures;
import org.kinotic.structures.api.domain.Trait;
import org.kinotic.structures.internal.graphql.CachingPreparsedDocumentProvider;
import org.kinotic.structures.internal.graphql.GetAllItemsDataFetcher;
import org.kinotic.structures.internal.graphql.GetItemDataFetcher;
import org.kinotic.structures.internal.graphql.SearchItemDataFetcher;
import org.springframework.graphql.ExecutionGraphQlService;
import org.springframework.graphql.execution.DefaultBatchLoaderRegistry;
import org.springframework.graphql.execution.DefaultExecutionGraphQlService;
import org.springframework.graphql.execution.GraphQlSource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static graphql.Scalars.*;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * Created by Navíd Mitchell 🤪 on 4/16/23.
 */
@Component
public class DefaultExecutionGraphQlServiceProvider implements ExecutionGraphQlServiceProvider{

    private final AsyncLoadingCache<String, ExecutionGraphQlService> cache;

    public DefaultExecutionGraphQlServiceProvider(ObjectMapper objectMapper,
                                                  ItemServiceInternal itemService,
                                                  StructureServiceInternal structureService) {
        this.cache = Caffeine.newBuilder()
                             .expireAfterAccess(1, TimeUnit.HOURS)
                             .maximumSize(10_000)
                             .buildAsync(new ExecutionGraphQlServiceCacheLoader(objectMapper,
                                                                                structureService,
                                                                                itemService));
    }

    @Override
    public Mono<ExecutionGraphQlService> getService(String namespace) {
        return Mono.fromCompletionStage(cache.get(namespace));
    }

    @Override
    public void evictCacheFor(String namespace) {
        cache.asMap().remove(namespace);
    }


    private static class ExecutionGraphQlServiceCacheLoader implements CacheLoader<String, ExecutionGraphQlService>{

        private final ObjectMapper objectMapper;
        private final StructureServiceInternal structureService;
        private final ItemServiceInternal itemService;

        public ExecutionGraphQlServiceCacheLoader(ObjectMapper objectMapper,
                                                  StructureServiceInternal structureService,
                                                  ItemServiceInternal itemService) {
            this.objectMapper = objectMapper;
            this.structureService = structureService;
            this.itemService = itemService;
        }

        @Override
        public @Nullable ExecutionGraphQlService load(String namespace) throws Exception {
            GraphQLSchema schema = createGraphQlSchema(namespace);

            GraphQL.Builder builder = GraphQL.newGraphQL(schema)
                                             .preparsedDocumentProvider(new CachingPreparsedDocumentProvider());

            DefaultExecutionGraphQlService service = new DefaultExecutionGraphQlService(new FixedGraphQlSource(builder.build(), schema));
            service.addDataLoaderRegistrar(new DefaultBatchLoaderRegistry());
            return service;
        }

        private GraphQLSchema createGraphQlSchema(String namespace) throws Exception {
            Map<String, StructureGraphTypeHolder> structureTypeMap = new HashMap<>();
            Structures structures = structureService.getAllPublishedForNamespace(namespace, 100, 0, "name", false);

            for(StructureHolder structureHolder : structures.getContent()) {
                Structure structure = structureHolder.getStructure();
                GraphQLObjectType graphQlOutputStructureItem = getGraphQlOutputStructureItem(structure);
                structureTypeMap.put(structure.getId(), new StructureGraphTypeHolder(graphQlOutputStructureItem));
            }

            GraphQLObjectType.Builder queryBuilder = newObject().name("Query");

            for(Map.Entry<String, StructureGraphTypeHolder> entry : structureTypeMap.entrySet()){

                queryBuilder.field(newFieldDefinition()
                                           .name(entry.getValue().getGraphOutputType().getName())
                                           .type(entry.getValue().getGraphOutputType())
                                           .argument(newArgument().name("id").type(GraphQLNonNull.nonNull(GraphQLID)))
                                           .dataFetcher(new GetItemDataFetcher(entry.getKey(), itemService)));

                GraphQLTypeReference graphQLTypeReference = new GraphQLTypeReference(entry.getValue().getGraphOutputType().getName());
                GraphQLNamedOutputType listResponse = wrapForItemListResponse(graphQLTypeReference);

                queryBuilder.field(newFieldDefinition()
                                           .name(entry.getValue().getGraphOutputType().getName() + "s")
                                           .type(listResponse)
                                           .argument(newArgument().name("offset").type(GraphQLNonNull.nonNull(GraphQLInt)))
                                           .argument(newArgument().name("limit").type(GraphQLNonNull.nonNull(GraphQLInt)))
                                           .dataFetcher(new GetAllItemsDataFetcher(entry.getKey(), itemService)));

                queryBuilder.field(newFieldDefinition()
                                           .name("search" + entry.getValue().getGraphOutputType().getName())
                                           .type(listResponse)
                                           .argument(newArgument().name("search").type(GraphQLNonNull.nonNull(GraphQLString)))
                                           .argument(newArgument().name("offset").type(GraphQLNonNull.nonNull(GraphQLInt)))
                                           .argument(newArgument().name("limit").type(GraphQLNonNull.nonNull(GraphQLInt)))
                                           .dataFetcher(new SearchItemDataFetcher(entry.getKey(), itemService)));
            }
            return GraphQLSchema.newSchema()
                                .query(queryBuilder.build())
                                .build();
        }

        private GraphQLNamedOutputType wrapForItemListResponse(GraphQLNamedOutputType graphQlOutputStructureItem){
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


        private GraphQLObjectType getGraphQlOutputStructureItem(Structure structure){
            GraphQLObjectType.Builder builder = newObject().name(structure.getName());

            for (Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()) {
                try {

                    GraphQLOutputType graphQlType = getGraphQlOutputTypeForTrait(traitEntry.getValue());
                    if(traitEntry.getValue().isCollection()){
                        graphQlType = GraphQLList.list(graphQlType);
                    }

                    builder.field(newFieldDefinition()
                                          .name(traitEntry.getKey())
                                          .type(graphQlType));

                } catch (Exception e) {
                    throw new RuntimeException("Failed to get schema for trait " + traitEntry.getKey(), e);
                }
            }

            return builder.build();
        }

        private GraphQLOutputType getGraphQlOutputTypeForTrait(Trait trait) throws Exception {
            GraphQLOutputType ret;
            if(trait.getSchema() != null){
                if(trait.getName().equals("Id")){
                    ret = GraphQLID;
                }else {
                    JsonSchema schema = objectMapper.readValue(trait.getSchema(), JsonSchema.class);
                    if (schema instanceof DateJsonSchema) {
                        DateJsonSchema dateJsonSchema = (DateJsonSchema) schema;
                        if (dateJsonSchema.getFormat() instanceof UnixDateStyle) {
                            ret = ExtendedScalars.GraphQLLong;
                        } else if (dateJsonSchema.getFormat() instanceof MillsDateStyle) {
                            ret = ExtendedScalars.GraphQLLong;
                        } else if (dateJsonSchema.getFormat() instanceof StringDateStyle) {
                            ret = GraphQLString;
                        } else {
                            throw new RuntimeException("Unknown date format " + dateJsonSchema.getFormat()
                                                                                              .getClass()
                                                                                              .getName());
                        }
                    } else if (schema instanceof StringJsonSchema) {
                        ret = GraphQLString;
                    } else if (schema instanceof NumberJsonSchema) {
                        ret = GraphQLInt;
                    } else if (schema instanceof BooleanJsonSchema) {
                        ret = GraphQLBoolean;
                    } else {
                        throw new RuntimeException("Unknown schema type " + schema.getClass().getName());
                    }
                }
            }else{
                throw new RuntimeException("Trait schema is null");
            }

            //FIXME: remove once deleted time is corrected in the DB
            if(trait.isRequired() && !trait.getName().equals("DeletedTime")){
                ret = GraphQLNonNull.nonNull(ret);
            }

            return ret;
        }
    }

    private static class StructureGraphTypeHolder {
        private final GraphQLObjectType graphOutputType;

        public StructureGraphTypeHolder(GraphQLObjectType graphOutputType) {
            this.graphOutputType = graphOutputType;
        }

        public GraphQLObjectType getGraphOutputType() {
            return graphOutputType;
        }
    }

    private static class FixedGraphQlSource implements GraphQlSource {

        private final GraphQL graphQl;

        private final GraphQLSchema schema;

        FixedGraphQlSource(GraphQL graphQl, GraphQLSchema schema) {
            this.graphQl = graphQl;
            this.schema = schema;
        }

        @Override
        public GraphQL graphQl() {
            return this.graphQl;
        }

        @Override
        public GraphQLSchema schema() {
            return this.schema;
        }

    }
}
