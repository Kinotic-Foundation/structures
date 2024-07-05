package org.kinotic.structures.internal.endpoints.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.language.OperationDefinition;
import org.apache.commons.text.WordUtils;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.continuum.idl.api.schema.FunctionDefinition;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.NamedQueriesDefinition;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.idl.PageC3Type;
import org.kinotic.structures.api.domain.idl.decorators.QueryDecorator;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.api.services.NamedQueriesService;
import org.kinotic.structures.internal.api.services.EntityContextConstants;
import org.kinotic.structures.internal.api.services.sql.MapParameterHolder;
import org.kinotic.structures.internal.endpoints.graphql.datafetchers.*;
import org.kinotic.structures.internal.utils.GqlUtils;
import org.kinotic.structures.internal.utils.QueryUtils;
import org.kinotic.structures.internal.utils.SqlQueryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private final Trie<GqlOperationExecutionFunction> builtInOperationTrie = new Trie<>();
    private final EntitiesService entitiesService;
    private final NamedQueriesService namedQueriesService;
    /**
     * This is a map of operation names to their execution functions for named queries
     */
    private final ConcurrentHashMap<String, GqlOperationExecutionFunction> namedQueryExecutionFunctionMap = new ConcurrentHashMap<>();
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
                                      .operationName("findById")
                                      .operationType(OperationDefinition.Operation.QUERY)
                                      .fieldDefinitionFunction(args -> newFieldDefinition()
                                              .name("findById" + args.getStructureName())
                                              .type(args.getOutputType())
                                              .argument(newArgument().name("id")
                                                                     .type(nonNull(GraphQLID)))
                                              .build())
                                      .dataFetcherDefinitionFunction(structure -> new FindByIdDataFetcher(structure.getId(), entitiesService))
                                      .operationExecutionFunction(args -> {

                                          ParsedFields fields = args.getParsedFields();
                                          String structureId = GqlUtils.getStructureId(args.getNamespace(), args.getOperationName(), "findById");

                                          return entitiesService.findById(structureId,
                                                                          (String) args.getVariables()
                                                                                       .get("id"),
                                                                          RawJson.class,
                                                                          GqlUtils.createContext(args,
                                                                                                 fields.getNonContentFields()))
                                                                .thenApply(entity -> GqlUtils.convertToResult(args.getOperationName(),
                                                                                                              entity));
                                      })
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationName("findAll")
                                      .operationType(OperationDefinition.Operation.QUERY)
                                      .fieldDefinitionFunction(args -> newFieldDefinition()
                                              .name("findAll" + args.getStructureName())
                                              .type(nonNull(args.getPageResponseType()))
                                              .argument(newArgument().name("pageable")
                                                                     .type(nonNull(args.getOffsetPageableReference())))
                                              .build())
                                      .dataFetcherDefinitionFunction(structure -> new FindAllDataFetcher(structure.getId(), entitiesService, objectMapper))
                                      .operationExecutionFunction(args -> {

                                          ParsedFields fields = args.getParsedFields();
                                          Pageable pageable = GqlUtils.parseVariable(args.getVariables(),
                                                                                     "pageable",
                                                                                     Pageable.class,
                                                                                     objectMapper);
                                          String structureId = GqlUtils.getStructureId(args.getNamespace(), args.getOperationName(), "findAll");

                                          return entitiesService.findAll(structureId,
                                                                         pageable,
                                                                         RawJson.class,
                                                                         GqlUtils.createContext(args, fields.getContentFields()))
                                                                .thenApply(page -> GqlUtils.convertToResult(args.getOperationName(),
                                                                                                            page,
                                                                                                            args.getParsedFields()));
                                      })
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationName("findAllWithCursor")
                                      .operationType(OperationDefinition.Operation.QUERY)
                                      .fieldDefinitionFunction(args -> newFieldDefinition()
                                              .name("findAllWithCursor" + args.getStructureName())
                                              .type(nonNull(args.getCursorPageResponseType()))
                                              .argument(newArgument().name("pageable")
                                                                     .type(nonNull(args.getCursorPageableReference())))
                                              .build())
                                      .dataFetcherDefinitionFunction(structure -> new FindAllDataFetcher(structure.getId(), entitiesService, objectMapper))
                                      .operationExecutionFunction(args -> {

                                          ParsedFields fields = args.getParsedFields();
                                          Pageable pageable = GqlUtils.parseVariable(args.getVariables(),
                                                                                     "pageable",
                                                                                     Pageable.class,
                                                                                     objectMapper);
                                          String structureId = GqlUtils.getStructureId(args.getNamespace(), args.getOperationName(), "findAllWithCursor");

                                          return entitiesService.findAll(structureId,
                                                                         pageable,
                                                                         RawJson.class,
                                                                         GqlUtils.createContext(args, fields.getContentFields()))
                                                                .thenApply(page -> GqlUtils.convertToResult(args.getOperationName(),
                                                                                                            page,
                                                                                                            args.getParsedFields()));
                                      })
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationName("search")
                                      .operationType(OperationDefinition.Operation.QUERY)
                                      .fieldDefinitionFunction(args -> newFieldDefinition()
                                              .name("search" + args.getStructureName())
                                              .type(nonNull(args.getPageResponseType()))
                                              .argument(newArgument().name("searchText")
                                                                     .type(nonNull(GraphQLString)))
                                              .argument(newArgument().name("pageable")
                                                                     .type(nonNull(args.getOffsetPageableReference())))
                                              .build())
                                      .dataFetcherDefinitionFunction(structure -> new SearchDataFetcher(structure.getId(), entitiesService, objectMapper))
                                      .operationExecutionFunction(args -> {

                                          ParsedFields fields = args.getParsedFields();
                                          Pageable pageable = GqlUtils.parseVariable(args.getVariables(),
                                                                                     "pageable",
                                                                                     Pageable.class,
                                                                                     objectMapper);
                                          String searchText = (String) args.getVariables()
                                                                           .get("searchText");
                                          String structureId = GqlUtils.getStructureId(args.getNamespace(), args.getOperationName(), "search");

                                          return entitiesService.search(structureId,
                                                                        searchText,
                                                                        pageable,
                                                                        RawJson.class,
                                                                        GqlUtils.createContext(args, fields.getContentFields()))
                                                                .thenApply(page -> GqlUtils.convertToResult(args.getOperationName(),
                                                                                                            page,
                                                                                                            args.getParsedFields()));
                                      })
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationName("searchWithCursor")
                                      .operationType(OperationDefinition.Operation.QUERY)
                                      .fieldDefinitionFunction(args -> newFieldDefinition()
                                              .name("searchWithCursor" + args.getStructureName())
                                              .type(nonNull(args.getCursorPageResponseType()))
                                              .argument(newArgument().name("searchText")
                                                                     .type(nonNull(GraphQLString)))
                                              .argument(newArgument().name("pageable")
                                                                     .type(nonNull(args.getCursorPageableReference())))
                                              .build())
                                      .dataFetcherDefinitionFunction(structure -> new SearchDataFetcher(structure.getId(), entitiesService, objectMapper))
                                      .operationExecutionFunction(args -> {

                                          ParsedFields fields = args.getParsedFields();
                                          Pageable pageable = GqlUtils.parseVariable(args.getVariables(),
                                                                                     "pageable",
                                                                                     Pageable.class,
                                                                                     objectMapper);
                                          String searchText = (String) args.getVariables()
                                                                           .get("searchText");
                                          String structureId = GqlUtils.getStructureId(args.getNamespace(), args.getOperationName(), "searchWithCursor");

                                          return entitiesService.search(structureId,
                                                                        searchText,
                                                                        pageable,
                                                                        RawJson.class,
                                                                        GqlUtils.createContext(args, fields.getContentFields()))
                                                                .thenApply(page -> GqlUtils.convertToResult(args.getOperationName(),
                                                                                                            page,
                                                                                                            args.getParsedFields()));
                                      })
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationName("save")
                                      .operationType(OperationDefinition.Operation.MUTATION)
                                      .fieldDefinitionFunction(args -> newFieldDefinition()
                                              .name("save" + args.getStructureName())
                                              .type(GraphQLID)
                                              .argument(newArgument().name("input")
                                                                     .type(nonNull(args.getInputType())))
                                              .build())
                                      .dataFetcherDefinitionFunction(structure -> new SaveDataFetcher(structure.getId(), entitiesService))
                                      .operationExecutionFunction(args -> {

                                          Map<?,?> map = (Map<?,?>) args.getVariables().get("input");
                                          EntityContext context = GqlUtils.createContext(args, null);
                                          String structureId = GqlUtils.getStructureId(args.getNamespace(), args.getOperationName(), "save");

                                          return entitiesService.save(structureId,
                                                                      map,
                                                                      context)
                                                                .thenApply(savedEntity -> GqlUtils.convertToResult(args.getOperationName(),
                                                                                                                   context.get(EntityContextConstants.ENTITY_ID_KEY)));
                                      })
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationName("bulkSave")
                                      .operationType(OperationDefinition.Operation.MUTATION)
                                      .fieldDefinitionFunction(args -> newFieldDefinition()
                                              .name("bulkSave" + args.getStructureName())
                                              .type(GraphQLBoolean)
                                              .argument(newArgument().name("input")
                                                                     .type(nonNull(list(nonNull(args.getInputType())))))
                                              .build())
                                      .dataFetcherDefinitionFunction(structure -> new BulkSaveDataFetcher(structure.getId(), entitiesService))
                                      .operationExecutionFunction(args -> {

                                          @SuppressWarnings("unchecked")
                                          List<Map<?,?>> input = (List<Map<?,?>>) args.getVariables().get("input");
                                          EntityContext context = GqlUtils.createContext(args, null);

                                          String structureId = GqlUtils.getStructureId(args.getNamespace(), args.getOperationName(), "bulkSave");
                                          return entitiesService.bulkSave(structureId,
                                                                          input,
                                                                          context)
                                                                .thenApply(res -> GqlUtils.convertToResult(args.getOperationName(),
                                                                                                           Boolean.TRUE));
                                      })
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationName("delete")
                                      .operationType(OperationDefinition.Operation.MUTATION)
                                      .fieldDefinitionFunction(args -> newFieldDefinition()
                                              .name("delete" + args.getStructureName())
                                              .type(GraphQLID)
                                              .argument(newArgument().name("id")
                                                                     .type(nonNull(GraphQLID)))
                                              .build())
                                      .dataFetcherDefinitionFunction(structure -> new DeleteDataFetcher(structure.getId(), entitiesService))
                                      .operationExecutionFunction(args -> {

                                          RawJson rawJson = (RawJson) args.getVariables().get("input");
                                          EntityContext context = GqlUtils.createContext(args, null);
                                          String structureId = GqlUtils.getStructureId(args.getNamespace(), args.getOperationName(), "delete");

                                          return entitiesService.save(structureId,
                                                                      rawJson,
                                                                      context)
                                                                .thenApply(savedEntity -> GqlUtils.convertToResult(args.getOperationName(),
                                                                                                                   context.get(EntityContextConstants.ENTITY_ID_KEY)));
                                      })
                                      .build()
        );

        for(GqlOperationDefinition definition : builtInOperationDefinitions){
            builtInOperationTrie.insert(definition.getOperationName(), definition.getOperationExecutionFunction());
        }
    }

    @Override
    public void evictCachesFor(Structure structure) {
        namedQueryOperationDefinitionMap.computeIfPresent(structure.getId(), (k, v) -> {
            for (GqlOperationDefinition definition : v) {
                namedQueryExecutionFunctionMap.remove(definition.getOperationName());
            }
            return null;
        });
    }

    @Override
    public GqlOperationExecutionFunction findOperationExecutionFunction(String operationName) {
        long now = System.nanoTime();

        // First search named query map, since it is an exact match lookup
        GqlOperationExecutionFunction ret = namedQueryExecutionFunctionMap.get(operationName);
        log.debug("Finished Searching Named Query Map for: {} in {}ns", operationName, System.nanoTime() - now);

        // If not found in named query map then search the trie for built-in operations
        if(ret == null){
            now = System.nanoTime();
            ret = builtInOperationTrie.findValue(operationName);
            log.debug("Finished Searching Trie for: {} in {}ns", operationName, System.nanoTime() - now);
        }
        return ret;
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

                        for(GqlOperationDefinition definition : definitions) {
                            namedQueryExecutionFunctionMap.put(definition.getOperationName(),
                                                               definition.getOperationExecutionFunction());
                            ret.add(definition);
                        }
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
                   .dataFetcherDefinitionFunction(struct -> new QueryDataFetcher(entitiesService, queryName, struct.getId()))
                   .operationExecutionFunction(args -> {

                       ParsedFields fields = args.getParsedFields();

                       return entitiesService.namedQuery(structure.getId(),
                                                         queryName,
                                                         new MapParameterHolder(args.getVariables()),
                                                         RawJson.class,
                                                         GqlUtils.createContext(args, fields.getNonContentFields()))
                                             .thenApply(entity -> GqlUtils.convertToResult(args.getOperationName(),
                                                                                           entity));
                   });
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
                                                                                  struct.getId()))
               .operationExecutionFunction(new PagedQueryGqlOperationExecutionFunction(entitiesService,
                                                                                       objectMapper,
                                                                                       queryDefinition,
                                                                                       CURSOR_PAGEABLE,
                                                                                       structure.getId()));
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
                                                                                  struct.getId()))
               .operationExecutionFunction(new PagedQueryGqlOperationExecutionFunction(entitiesService,
                                                                                       objectMapper,
                                                                                       queryDefinition,
                                                                                       OFFSET_PAGEABLE,
                                                                                       structure.getId()));
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
