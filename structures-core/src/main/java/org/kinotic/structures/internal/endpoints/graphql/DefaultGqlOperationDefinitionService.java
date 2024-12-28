package org.kinotic.structures.internal.endpoints.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.language.OperationDefinition;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLFieldDefinition;
import org.apache.commons.text.WordUtils;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.continuum.idl.api.schema.FunctionDefinition;
import org.kinotic.structures.api.domain.NamedQueriesDefinition;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.idl.PageC3Type;
import org.kinotic.structures.api.domain.idl.decorators.EntityServiceDecorator;
import org.kinotic.structures.api.domain.idl.decorators.PolicyDecorator;
import org.kinotic.structures.api.domain.idl.decorators.QueryDecorator;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.api.services.NamedQueriesService;
import org.kinotic.structures.internal.api.services.impl.EntityOperation;
import org.kinotic.structures.internal.endpoints.graphql.datafetchers.*;
import org.kinotic.structures.internal.utils.GqlUtils;
import org.kinotic.structures.internal.utils.QueryUtils;
import org.kinotic.structures.internal.utils.SqlQueryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

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

    private static final Logger log = LoggerFactory.getLogger(DefaultGqlOperationDefinitionService.class);
    private static final Pageable CURSOR_PAGEABLE = Pageable.create(null, 25, null);
    private static final Pageable OFFSET_PAGEABLE = Pageable.create(0, 25, null);

    private final List<GqlOperationDefinition> builtInOperationDefinitions;
    private final EntitiesService entitiesService;
    private final NamedQueriesService namedQueriesService;

    /**
     * This is a map of structure ids to their named query operation definitions
     */
    private final ConcurrentHashMap<String, List<GqlOperationDefinition>> namedQueryOperationDefinitionMap = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    public DefaultGqlOperationDefinitionService(EntitiesService entitiesService,
                                                NamedQueriesService namedQueriesService,
                                                ObjectMapper objectMapper) {

        this.entitiesService = entitiesService;
        this.namedQueriesService = namedQueriesService;
        this.objectMapper = objectMapper;

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
                                                  .type(GraphQLID)
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
                                      .build()
        );
    }

    @Override
    public void evictCachesFor(Structure structure) {
        namedQueryOperationDefinitionMap.remove(structure.getId());
    }

    @Override
    public List<GqlOperationDefinition> getBuiltInOperationDefinitions() {
        return builtInOperationDefinitions;
    }

    @Override
    public List<GqlOperationDefinition> getNamedQueryOperationDefinitions(final Structure structure) {
        Function<String, List<GqlOperationDefinition>> computeFunction
                = s -> {
            NamedQueriesDefinition namedQueriesDefinition = namedQueriesService.findByNamespaceAndStructure(structure.getNamespace(),
                                                                                                            structure.getName())
                                                                               .join();
            List<GqlOperationDefinition> ret;
            if(namedQueriesDefinition != null) {

                ret = new ArrayList<>(namedQueriesDefinition.getNamedQueries().size());
                for (FunctionDefinition queryDefinition : namedQueriesDefinition.getNamedQueries()) {

                    QueryDecorator queryDecorator = queryDefinition.findDecorator(QueryDecorator.class);
                    if(queryDecorator != null) {

                        List<GqlOperationDefinition> definitions = buildGqlOperationDefinitions(structure,
                                                                                                queryDefinition,
                                                                                                queryDecorator);

                        ret.addAll(definitions);
                    }else{
                        log.warn("No QueryDecorator found for Named query {} in Structure {}. No GraphQL operation will be created.",
                                 queryDefinition.getName(),
                                 structure.getName());
                    }
                }
            }else{
                ret = List.of();
            }
            return ret;
        };
        return namedQueryOperationDefinitionMap.computeIfAbsent(structure.getId(), computeFunction);
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

    private List<GqlOperationDefinition> buildGqlOperationDefinitions(Structure structure,
                                                                      FunctionDefinition queryDefinition,
                                                                      QueryDecorator queryDecorator) {
        List<GqlOperationDefinition> ret = new ArrayList<>();
        String queryName = queryDefinition.getName();
        SqlQueryType queryType = QueryUtils.determineQueryType(queryDecorator.getStatements());

        // If we return a page then we can potentially have multiple operations
        if(queryDefinition.getReturnType() instanceof PageC3Type) {
            switch (queryType) {
                case AGGREGATE:
                    ret.add(createForCursorPageQuery(structure, queryDefinition, queryName, ""));
                    break;
                case SELECT:
                    ret.add(createForCursorPageQuery(structure, queryDefinition, queryName, "WithCursor"));
                    ret.add(createForOffsetPageQuery(structure, queryDefinition, queryName));
                    break;
                default:
                    log.warn("The Page Return type is not valid for a {} query. Query {} will be skipped.", queryType, queryName);
                    break;
            }
        }else{
            GqlOperationDefinition.GqlOperationDefinitionBuilder builder = GqlOperationDefinition.builder();
            builder.operationName(queryName + WordUtils.capitalize(structure.getName()))
                   .operationType(getGqlOperationType(queryType))
                   .fieldDefinitionFunction(new QueryGqlFieldDefinitionFunction(queryDefinition, false))
                   .dataFetcherDefinitionFunction(struct -> new QueryDataFetcher(entitiesService, queryName, struct.getId()));
            ret.add(builder.build());
        }
        return ret;
    }

    private GqlOperationDefinition createForCursorPageQuery(Structure structure,
                                                            FunctionDefinition queryDefinition,
                                                            String queryName,
                                                            String suffix) {
        GqlOperationDefinition.GqlOperationDefinitionBuilder builder = GqlOperationDefinition.builder();
        builder.operationName(queryName + suffix + WordUtils.capitalize(structure.getName()))
               .operationType(OperationDefinition.Operation.QUERY)
               .fieldDefinitionFunction(new QueryGqlFieldDefinitionFunction(queryDefinition, true))
               .dataFetcherDefinitionFunction(struct -> new PagedQueryDataFetcher(entitiesService,
                                                                                  objectMapper,
                                                                                  queryDefinition,
                                                                                  CURSOR_PAGEABLE,
                                                                                  struct.getId()));
        return builder.build();
    }

    private GqlOperationDefinition createForOffsetPageQuery(Structure structure,
                                                            FunctionDefinition queryDefinition,
                                                            String queryName) {
        GqlOperationDefinition.GqlOperationDefinitionBuilder builder = GqlOperationDefinition.builder();
        builder.operationName(queryName + WordUtils.capitalize(structure.getName()))
               .operationType(OperationDefinition.Operation.QUERY)
               .fieldDefinitionFunction(new QueryGqlFieldDefinitionFunction(queryDefinition, false))
               .dataFetcherDefinitionFunction(struct -> new PagedQueryDataFetcher(entitiesService,
                                                                                  objectMapper,
                                                                                  queryDefinition,
                                                                                  OFFSET_PAGEABLE,
                                                                                  struct.getId()));
        return builder.build();
    }

    private static OperationDefinition.Operation getGqlOperationType(SqlQueryType queryType) {
        OperationDefinition.Operation operation = null;
        switch (queryType) {
            case AGGREGATE:
            case SELECT:
                operation = OperationDefinition.Operation.QUERY;
                break;
            case DELETE:
            case INSERT:
            case UPDATE:
                operation = OperationDefinition.Operation.MUTATION;
                break;
        }
        return operation;
    }

}
