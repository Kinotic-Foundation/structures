package org.kinotic.structures.internal.endpoints.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import graphql.language.OperationDefinition;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLFieldDefinition;
import org.kinotic.structures.api.domain.EntityOperation;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.idl.decorators.EntityServiceDecorator;
import org.kinotic.structures.api.domain.idl.decorators.PolicyDecorator;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.endpoints.graphql.datafetchers.*;
import org.kinotic.structures.internal.utils.GqlUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static graphql.Scalars.*;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLList.list;
import static graphql.schema.GraphQLNonNull.nonNull;

/**
 * Created by Navíd Mitchell 🤪 on 12/14/23.
 */
@Component
public class DefaultGqlOperationDefinitionService implements GqlOperationDefinitionService {

    private final List<GqlOperationDefinition> builtInOperationDefinitions;
    private final AsyncLoadingCache<String, List<GqlOperationDefinition>> namedQueryOperationDefinitionCache;

    public DefaultGqlOperationDefinitionService(EntitiesService entitiesService,
                                                NamedQueryGqlOperationDefinitionCacheLoader namedQueryGqlOperationDefinitionCacheLoader,
                                                ObjectMapper objectMapper) {

        namedQueryOperationDefinitionCache
                = Caffeine.newBuilder()
                          .expireAfterAccess(1, TimeUnit.HOURS)
                          .maximumSize(2000)
                          .buildAsync(namedQueryGqlOperationDefinitionCacheLoader);

        this.builtInOperationDefinitions = List.of(

                GqlOperationDefinition.builder()
                                      .operationName(EntityOperation.BULK_SAVE.methodName())
                                      .operationType(OperationDefinition.Operation.MUTATION)
                                      .fieldDefinitionFunction(args -> {

                                          GraphQLFieldDefinition.Builder builder = newFieldDefinition()
                                                  .name(EntityOperation.BULK_SAVE.methodName() + args.getStructureName())
                                                  .type(GraphQLBoolean)
                                                  .argument(newArgument().name("input")
                                                                         .type(nonNull(list(nonNull(args.getInputType())))));

                                          builder = addPolicyIfPresent(builder, args.getEntityOperationsMap().get(EntityOperation.BULK_SAVE));
                                          return builder.build();
                                      })
                                      .dataFetcherDefinitionFunction(structure -> new BulkSaveDataFetcher(structure.getId(), entitiesService))
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationName(EntityOperation.BULK_UPDATE.methodName())
                                      .operationType(OperationDefinition.Operation.MUTATION)
                                      .fieldDefinitionFunction(args -> {

                                          GraphQLFieldDefinition.Builder builder = newFieldDefinition()
                                                  .name(EntityOperation.BULK_UPDATE.methodName() + args.getStructureName())
                                                  .type(GraphQLBoolean)
                                                  .argument(newArgument().name("input")
                                                                         .type(nonNull(list(nonNull(args.getInputType())))));

                                          builder = addPolicyIfPresent(builder, args.getEntityOperationsMap().get(EntityOperation.BULK_UPDATE));
                                          return builder.build();
                                      })
                                      .dataFetcherDefinitionFunction(structure -> new BulkUpdateDataFetcher(structure.getId(), entitiesService))
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationName(EntityOperation.COUNT.methodName())
                                      .operationType(OperationDefinition.Operation.QUERY)
                                      .fieldDefinitionFunction(args -> {

                                          GraphQLFieldDefinition.Builder builder = newFieldDefinition()
                                                  .name(EntityOperation.COUNT.methodName() + args.getStructureName())
                                                  .type(ExtendedScalars.GraphQLLong);

                                          builder = addPolicyIfPresent(builder, args.getEntityOperationsMap().get(EntityOperation.COUNT));
                                          return builder.build();
                                      })
                                      .dataFetcherDefinitionFunction(structure -> new CountDataFetcher(structure.getId(), entitiesService))
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationName("delete")
                                      .operationType(OperationDefinition.Operation.MUTATION)
                                      .fieldDefinitionFunction(args -> {

                                          GraphQLFieldDefinition.Builder builder = newFieldDefinition()
                                                  .name("delete" + args.getStructureName())
                                                  .type(GraphQLID)
                                                  .argument(newArgument().name("id")
                                                                         .type(nonNull(GraphQLID)));

                                          builder = addPolicyIfPresent(builder, args.getEntityOperationsMap().get(EntityOperation.DELETE_BY_ID));
                                          return builder.build();
                                      })
                                      .dataFetcherDefinitionFunction(structure -> new DeleteDataFetcher(structure.getId(), entitiesService))
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationName(EntityOperation.FIND_BY_ID.methodName())
                                      .operationType(OperationDefinition.Operation.QUERY)
                                      .fieldDefinitionFunction(args -> {

                                          GraphQLFieldDefinition.Builder builder = newFieldDefinition()
                                                  .name(EntityOperation.FIND_BY_ID.methodName() + args.getStructureName())
                                                  .type(args.getOutputType())
                                                  .argument(newArgument().name("id")
                                                                         .type(nonNull(GraphQLID)));

                                          builder = addPolicyIfPresent(builder, args.getEntityOperationsMap().get(EntityOperation.FIND_BY_ID));
                                          return builder.build();
                                      })
                                      .dataFetcherDefinitionFunction(structure -> new FindByIdDataFetcher(structure.getId(), entitiesService))
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationName(EntityOperation.FIND_ALL.methodName())
                                      .operationType(OperationDefinition.Operation.QUERY)
                                      .fieldDefinitionFunction(args -> {

                                          GraphQLFieldDefinition.Builder builder = newFieldDefinition()
                                                  .name(EntityOperation.FIND_ALL.methodName() + args.getStructureName())
                                                  .type(nonNull(args.getPageResponseType()))
                                                  .argument(newArgument().name("pageable")
                                                                         .type(nonNull(args.getOffsetPageableReference())));

                                            builder = addPolicyIfPresent(builder, args.getEntityOperationsMap().get(EntityOperation.FIND_ALL));
                                            return builder.build();
                                      })
                                      .dataFetcherDefinitionFunction(structure -> new FindAllDataFetcher(structure.getId(), entitiesService, objectMapper))
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationName("findAllWithCursor")
                                      .operationType(OperationDefinition.Operation.QUERY)
                                      .fieldDefinitionFunction(args -> {

                                          GraphQLFieldDefinition.Builder builder = newFieldDefinition()
                                                  .name("findAllWithCursor" + args.getStructureName())
                                                  .type(nonNull(args.getCursorPageResponseType()))
                                                  .argument(newArgument().name("pageable")
                                                                         .type(nonNull(args.getCursorPageableReference())));

                                            builder = addPolicyIfPresent(builder, args.getEntityOperationsMap().get(EntityOperation.FIND_ALL));
                                            return builder.build();
                                      })
                                      .dataFetcherDefinitionFunction(structure -> new FindAllDataFetcher(structure.getId(), entitiesService, objectMapper))
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationName(EntityOperation.SAVE.methodName())
                                      .operationType(OperationDefinition.Operation.MUTATION)
                                      .fieldDefinitionFunction(args -> {

                                          GraphQLFieldDefinition.Builder builder = newFieldDefinition()
                                                  .name(EntityOperation.SAVE.methodName() + args.getStructureName())
                                                  .type(args.getOutputType())
                                                  .argument(newArgument().name("input")
                                                                         .type(nonNull(args.getInputType())));

                                          builder = addPolicyIfPresent(builder, args.getEntityOperationsMap().get(EntityOperation.SAVE));
                                          return builder.build();
                                      })
                                      .dataFetcherDefinitionFunction(structure -> new SaveDataFetcher(structure.getId(), entitiesService))
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationName(EntityOperation.SEARCH.methodName())
                                      .operationType(OperationDefinition.Operation.QUERY)
                                      .fieldDefinitionFunction(args -> {

                                          GraphQLFieldDefinition.Builder builder = newFieldDefinition()
                                                  .name(EntityOperation.SEARCH.methodName() + args.getStructureName())
                                                  .type(nonNull(args.getPageResponseType()))
                                                  .argument(newArgument().name("searchText")
                                                                         .type(nonNull(GraphQLString)))
                                                  .argument(newArgument().name("pageable")
                                                                         .type(nonNull(args.getOffsetPageableReference())));

                                            builder = addPolicyIfPresent(builder, args.getEntityOperationsMap().get(EntityOperation.SEARCH));
                                            return builder.build();
                                      })
                                      .dataFetcherDefinitionFunction(structure -> new SearchDataFetcher(structure.getId(), entitiesService, objectMapper))
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationName("searchWithCursor")
                                      .operationType(OperationDefinition.Operation.QUERY)
                                      .fieldDefinitionFunction(args -> {

                                          GraphQLFieldDefinition.Builder builder = newFieldDefinition()
                                                  .name("searchWithCursor" + args.getStructureName())
                                                  .type(nonNull(args.getCursorPageResponseType()))
                                                  .argument(newArgument().name("searchText")
                                                                         .type(nonNull(GraphQLString)))
                                                  .argument(newArgument().name("pageable")
                                                                         .type(nonNull(args.getCursorPageableReference())));

                                            builder = addPolicyIfPresent(builder, args.getEntityOperationsMap().get(EntityOperation.SEARCH));
                                            return builder.build();
                                      })
                                      .dataFetcherDefinitionFunction(structure -> new SearchDataFetcher(structure.getId(), entitiesService, objectMapper))
                                      .build(),


                GqlOperationDefinition.builder()
                                      .operationName("sync")
                                      .operationType(OperationDefinition.Operation.MUTATION)
                                      .fieldDefinitionFunction(args -> {

                                          GraphQLFieldDefinition.Builder builder = newFieldDefinition()
                                                  .name("sync" + args.getStructureName())
                                                  .type(GraphQLID);

                                          builder = addPolicyIfPresent(builder, args.getEntityOperationsMap().get(EntityOperation.SYNC_INDEX));
                                          return builder.build();
                                      })
                                      .dataFetcherDefinitionFunction(structure -> new SyncIndexDataFetcher(structure.getId(), entitiesService))
                                      .build(),


                GqlOperationDefinition.builder()
                                      .operationName(EntityOperation.UPDATE.methodName())
                                      .operationType(OperationDefinition.Operation.MUTATION)
                                      .fieldDefinitionFunction(args -> {

                                          GraphQLFieldDefinition.Builder builder = newFieldDefinition()
                                                  .name(EntityOperation.UPDATE.methodName() + args.getStructureName())
                                                  .type(args.getOutputType())
                                                  .argument(newArgument().name("input")
                                                                         .type(nonNull(args.getInputType())));

                                          builder = addPolicyIfPresent(builder, args.getEntityOperationsMap().get(EntityOperation.UPDATE));
                                          return builder.build();
                                      })
                                      .dataFetcherDefinitionFunction(structure -> new UpdateDataFetcher(structure.getId(), entitiesService))
                                      .build()
        );
    }


    @Override
    public void evictCachesFor(Structure structure) {
        namedQueryOperationDefinitionCache.asMap().remove(structure.getId());
    }

    @Override
    public List<GqlOperationDefinition> getBuiltInOperationDefinitions() {
        return builtInOperationDefinitions;
    }

    @Override
    public List<GqlOperationDefinition> getNamedQueryOperationDefinitions(final Structure structure) {
        return namedQueryOperationDefinitionCache.get(structure.getId()).join();
    }

    private GraphQLFieldDefinition.Builder addPolicyIfPresent(GraphQLFieldDefinition.Builder builder,
                                                              List<EntityServiceDecorator> decorators){
        if(decorators != null){
            for(EntityServiceDecorator decorator : decorators){
                if(decorator instanceof PolicyDecorator policyDecorator){
                    builder = builder.withDirective(GqlUtils.policy(policyDecorator.getPolicies()));
                }
            }
        }
        return builder;
    }

}
