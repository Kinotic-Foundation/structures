package org.kinotic.structures.internal.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import graphql.scalars.ExtendedScalars;
import graphql.schema.*;
import org.kinotic.continuum.idl.api.schema.datestyles.MillsDateStyle;
import org.kinotic.continuum.idl.api.schema.datestyles.StringDateStyle;
import org.kinotic.continuum.idl.api.schema.datestyles.UnixDateStyle;
import org.kinotic.continuum.idl.internal.support.jsonSchema.BooleanJsonSchema;
import org.kinotic.continuum.idl.internal.support.jsonSchema.DateJsonSchema;
import org.kinotic.continuum.idl.internal.support.jsonSchema.NumberJsonSchema;
import org.kinotic.continuum.idl.internal.support.jsonSchema.StringJsonSchema;
import org.kinotic.structures.internal.graphql.*;
import org.springframework.graphql.ExecutionGraphQlService;
import org.springframework.graphql.execution.DefaultBatchLoaderRegistry;
import org.springframework.graphql.execution.DefaultExecutionGraphQlService;
import org.springframework.graphql.execution.GraphQlSource;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Navíd Mitchell 🤪 on 4/16/23.
 */
//@Component
public class DefaultExecutionGraphQlServiceProvider { //implements ExecutionGraphQlServiceProvider{

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


    private static class ExecutionGraphQlServiceCacheLoader implements CacheLoader<String, ExecutionGraphQlService> {

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

            GraphQlSource graphQlSource = GraphQlSource.builder(schema)
                                                       .configureGraphQl(builder -> builder.preparsedDocumentProvider(new CachingPreparsedDocumentProvider()))
                                                       .build();


            DefaultExecutionGraphQlService service = new DefaultExecutionGraphQlService(graphQlSource);
            service.addDataLoaderRegistrar(new DefaultBatchLoaderRegistry());
            return service;
        }

        private GraphQLSchema createGraphQlSchema(String namespace) throws Exception {
            Map<String, StructureGraphTypeHolder> structureTypeMap = new HashMap<>();
            Structures structures = structureService.getAllPublishedForNamespace(namespace, 100, 0, "name", false);

            for(StructureHolder structureHolder : structures.getContent()) {
                Structure structure = structureHolder.getStructure();
                structureTypeMap.put(structure.getId(), getGraphQlObjectsForStructure(structure));
            }

            GraphQLObjectType.Builder queryBuilder = newObject().name("Query");

            GraphQLObjectType.Builder mutationBuilder = newObject().name("Mutation");

            for(Map.Entry<String, StructureGraphTypeHolder> entry : structureTypeMap.entrySet()){

                GraphQLObjectType outputType = entry.getValue().getGraphOutputType();
                GraphQLInputObjectType inputType = entry.getValue().getGraphInputType();

                queryBuilder.field(newFieldDefinition()
                                           .name(outputType.getName())
                                           .type(outputType)
                                           .argument(newArgument().name("id").type(GraphQLNonNull.nonNull(GraphQLID)))
                                           .dataFetcher(new GetItemDataFetcher(entry.getKey(), itemService)));

                GraphQLTypeReference graphQLTypeReference = new GraphQLTypeReference(outputType.getName());
                GraphQLNamedOutputType listResponse = wrapForItemListResponse(graphQLTypeReference);

                queryBuilder.field(newFieldDefinition()
                                           .name(outputType.getName() + "s")
                                           .type(listResponse)
                                           .argument(newArgument().name("offset").type(GraphQLNonNull.nonNull(GraphQLInt)))
                                           .argument(newArgument().name("limit").type(GraphQLNonNull.nonNull(GraphQLInt)))
                                           .dataFetcher(new GetAllItemsDataFetcher(entry.getKey(), itemService)));

                queryBuilder.field(newFieldDefinition()
                                           .name("search" + outputType.getName())
                                           .type(listResponse)
                                           .argument(newArgument().name("search").type(GraphQLNonNull.nonNull(GraphQLString)))
                                           .argument(newArgument().name("offset").type(GraphQLNonNull.nonNull(GraphQLInt)))
                                           .argument(newArgument().name("limit").type(GraphQLNonNull.nonNull(GraphQLInt)))
                                           .dataFetcher(new SearchItemDataFetcher(entry.getKey(), itemService)));

                mutationBuilder.field(newFieldDefinition()
                                              .name("upsert" + inputType.getName())
                                              .type(outputType)
                                              .argument(newArgument().name("input").type(GraphQLNonNull.nonNull(inputType)))
                                              .dataFetcher(new UpsertDataFetcher(entry.getKey(), itemService)));

                mutationBuilder.field(newFieldDefinition()
                                              .name("delete" + inputType.getName())
                                              .type(GraphQLBoolean)
                                              .argument(newArgument().name("id").type(GraphQLNonNull.nonNull(GraphQLString)))
                                              .dataFetcher(new UpsertDataFetcher(entry.getKey(), itemService)));

            }
            return GraphQLSchema.newSchema()
                                .query(queryBuilder.build())
                                .mutation(mutationBuilder.build())
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


        private StructureGraphTypeHolder getGraphQlObjectsForStructure(Structure structure){
            GraphQLObjectType.Builder outputBuilder = newObject().name(structure.getName());
            GraphQLInputObjectType.Builder inputBuilder = newInputObject().name(structure.getName()+"Input");

            for (Map.Entry<String, Trait> traitEntry : structure.getTraits().entrySet()) {
                try {
                    Trait trait = traitEntry.getValue();

                    GraphQLScalarType scalarType = getGraphQlScalarTypeForTrait(trait);

                    GraphQLOutputType outputType = scalarType;
                    GraphQLInputType inputType = scalarType;

                    if(trait.isRequired() && !trait.getName().equals("DeletedTime")){
                        outputType = GraphQLNonNull.nonNull(outputType);
                        inputType = GraphQLNonNull.nonNull(inputType);
                    }

                    if(traitEntry.getValue().isCollection()){
                        outputType = GraphQLList.list(outputType);
                        inputType = GraphQLList.list(inputType);
                    }

                    outputBuilder.field(newFieldDefinition()
                                                .name(traitEntry.getKey())
                                                .type(outputType));

                    if(!trait.isSystemManaged()){
                        inputBuilder.field(newInputObjectField()
                                                   .name(traitEntry.getKey())
                                                   .type(inputType));
                    }

                } catch (Exception e) {
                    throw new RuntimeException("Failed to get schema for trait " + traitEntry.getKey(), e);
                }
            }

            return new StructureGraphTypeHolder(outputBuilder.build(), inputBuilder.build());
        }

        private GraphQLScalarType getGraphQlScalarTypeForTrait(Trait trait) throws Exception {
            GraphQLScalarType ret;
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

            return ret;
        }
    }

    private static class StructureGraphTypeHolder {
        private final GraphQLObjectType graphOutputType;
        private final GraphQLInputObjectType graphInputType;

        public StructureGraphTypeHolder(GraphQLObjectType graphOutputType, GraphQLInputObjectType graphInputType) {
            this.graphOutputType = graphOutputType;
            this.graphInputType = graphInputType;
        }

        public GraphQLObjectType getGraphOutputType() {
            return graphOutputType;
        }

        public GraphQLInputObjectType getGraphInputType() {
            return graphInputType;
        }
    }

}
