package org.kinotic.structures.internal.endpoints.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionResult;
import graphql.ExecutionResultImpl;
import graphql.language.OperationDefinition;
import graphql.schema.GraphQLNonNull;
import org.kinotic.continuum.core.api.crud.CursorPage;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.DefaultEntityContext;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.api.services.EntityContextConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static graphql.Scalars.*;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLList.list;
import static graphql.schema.GraphQLNonNull.nonNull;

/**
 * Created by Navíd Mitchell 🤪 on 12/14/23.
 */
@Component
public class DefaultGqlOperationProviderService implements GqlOperationProviderService {

    private static final Logger log = LoggerFactory.getLogger(DefaultGqlOperationProviderService.class);

    private final Trie<GqlOperationDefinition> operationTrie = new Trie<>();
    private final List<GqlOperationDefinition> operationDefinitions;
    private final ObjectMapper objectMapper;


    public DefaultGqlOperationProviderService(EntitiesService entitiesService,
                                              ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.operationDefinitions = List.of(
                GqlOperationDefinition.builder()
                                      .operationNamePrefix("findById")
                                      .operationType(OperationDefinition.Operation.QUERY)
                                      .fieldDefinitionFunction(args -> newFieldDefinition()
                                                  .name("findById" + args.getStructuresName())
                                                  .type(args.getOutputType())
                                                  .argument(newArgument().name("id")
                                                                         .type(GraphQLNonNull.nonNull(GraphQLID)))
                                                  .build())
                                      .operationExecutionFunction(args -> {
                                              ParsedFields fields = args.getParsedFields();
                                              return entitiesService.findById(args.getStructureId(),
                                                                              (String) args.getVariables()
                                                                                           .get("id"),
                                                                              RawJson.class,
                                                                              createContext(args,
                                                                                            fields.getNonContentFields()))
                                                                    .thenApply(this::convertToResult);
                                          })
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationNamePrefix("findAll")
                                      .operationType(OperationDefinition.Operation.QUERY)
                                      .fieldDefinitionFunction(args -> newFieldDefinition()
                                                  .name("findAll" + args.getStructuresName())
                                                  .type(args.getPageResponseType())
                                                  .argument(newArgument().name("pageable")
                                                                         .type(nonNull(args.getPageableReference())))
                                                  .build())
                                      .operationExecutionFunction(args -> {
                                              ParsedFields fields = args.getParsedFields();
                                              Pageable pageable = parseVariable(args.getVariables(),
                                                                                "pageable",
                                                                                Pageable.class);
                                              return entitiesService.findAll(args.getStructureId(),
                                                                             pageable,
                                                                             RawJson.class,
                                                                             createContext(args, fields.getContentFields()))
                                                                    .thenApply(page -> convertToResult(page,
                                                                                                       args.getParsedFields()));
                                          })
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationNamePrefix("search")
                                      .operationType(OperationDefinition.Operation.QUERY)
                                      .fieldDefinitionFunction(args -> newFieldDefinition()
                                                  .name("search" + args.getStructuresName())
                                                  .type(args.getPageResponseType())
                                                  .argument(newArgument().name("searchText")
                                                                         .type(nonNull(GraphQLString)))
                                                  .argument(newArgument().name("pageable")
                                                                         .type(nonNull(args.getPageableReference())))
                                                  .build())
                                      .operationExecutionFunction(args -> {
                                              ParsedFields fields = args.getParsedFields();
                                              Pageable pageable = parseVariable(args.getVariables(),
                                                                                "pageable",
                                                                                Pageable.class);
                                              String searchText = (String) args.getVariables()
                                                                               .get("searchText");
                                              return entitiesService.search(args.getStructureId(),
                                                                            searchText,
                                                                            pageable,
                                                                            RawJson.class,
                                                                            createContext(args, fields.getContentFields()))
                                                                    .thenApply(page -> convertToResult(page,
                                                                                                       args.getParsedFields()));
                                          })
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationNamePrefix("save")
                                      .operationType(OperationDefinition.Operation.MUTATION)
                                      .fieldDefinitionFunction(args -> newFieldDefinition()
                                                  .name("save" + args.getStructuresName())
                                                  .type(GraphQLID)
                                                  .argument(newArgument().name("input")
                                                                         .type(nonNull(args.getInputType())))
                                                  .build())
                                      .operationExecutionFunction(args -> {
                                              Map<?,?> map = (Map<?,?>) args.getVariables().get("input");
                                              EntityContext context = createContext(args, null);
                                              return entitiesService.save(args.getStructureId(),
                                                                          map,
                                                                          context)
                                                                    .thenApply(savedEntity -> convertToResult(context.get(
                                                                            EntityContextConstants.ENTITY_ID_KEY)));
                                          })
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationNamePrefix("bulkSave")
                                      .operationType(OperationDefinition.Operation.MUTATION)
                                      .fieldDefinitionFunction(args -> newFieldDefinition()
                                                  .name("bulkSave" + args.getStructuresName())
                                                  .type(GraphQLBoolean)
                                                  .argument(newArgument().name("input")
                                                                         .type(nonNull(list(nonNull(args.getInputType())))))
                                                  .build())
                                      .operationExecutionFunction(args -> {
                                              @SuppressWarnings("unchecked")
                                              List<Map<?,?>> input = (List<Map<?,?>>) args.getVariables().get("input");
                                              EntityContext context = createContext(args, null);
                                              return entitiesService.bulkSave(args.getStructureId(),
                                                                              input,
                                                                              context)
                                                                    .thenApply(res -> convertToResult(Boolean.TRUE));
                                          })
                                      .build(),

                GqlOperationDefinition.builder()
                                      .operationNamePrefix("delete")
                                      .operationType(OperationDefinition.Operation.MUTATION)
                                      .fieldDefinitionFunction(args -> newFieldDefinition()
                                                  .name("delete" + args.getStructuresName())
                                                  .type(GraphQLID)
                                                  .argument(newArgument().name("id")
                                                                         .type(nonNull(GraphQLID)))
                                                  .build())
                                      .operationExecutionFunction(args -> {
                                              RawJson rawJson = (RawJson) args.getVariables().get("input");
                                              EntityContext context = createContext(args, null);
                                              return entitiesService.save(args.getStructureId(),
                                                                          rawJson,
                                                                          context)
                                                                    .thenApply(savedEntity -> convertToResult(context.get(
                                                                            EntityContextConstants.ENTITY_ID_KEY)));
                                          })
                                      .build()
        );

        for(GqlOperationDefinition definition : operationDefinitions){
            operationTrie.insert(definition.getOperationNamePrefix(), definition);
        }
    }

    @Override
    public List<GqlOperationDefinition> getOperationDefinitions() {
        return operationDefinitions;
    }

    @Override
    public GqlOperationDefinition findOperationDefinition(String completeOperationName) {
        long now = System.nanoTime();
        GqlOperationDefinition ret = operationTrie.findValue(completeOperationName);
        log.debug("Finished Searching Trie for: {} in {}ns", completeOperationName, System.nanoTime() - now);
        return ret;
    }

    private EntityContext createContext(GqlOperationArguments args, List<String> includeFieldsFilter) {
        return new DefaultEntityContext(args.getParticipant(), includeFieldsFilter);
    }

    private <T> T parseVariable(Map<String, Object> variables, String variableName, Class<T> clazz) {
        Object value = variables.get(variableName);
        if (value != null) {
            return objectMapper.convertValue(value, clazz);
        } else {
            throw new IllegalArgumentException("Variable: " + variableName + " not supplied");
        }
    }

    private ExecutionResult convertToResult(Object data) {
        return ExecutionResultImpl.newExecutionResult()
                                  .data(data)
                                  .build();
    }

    private ExecutionResult convertToResult(Page<?> page, ParsedFields parsedFields) {
        Map<String, Object> data = new HashMap<>();
        boolean shouldHaveTotalElements = false;
        boolean shouldHaveCursor = false;

        for (String field : parsedFields.getNonContentFields()) {
            if (shouldHaveTotalElements && shouldHaveCursor) {
                break;
            }
            if (field.equals("totalElements")) {
                shouldHaveTotalElements = true;
            } else if (field.equals("cursor")) {
                shouldHaveCursor = true;
            }
        }
        if (!parsedFields.getContentFields().isEmpty()) {
            data.put("content", page.getContent());
        }
        if (shouldHaveTotalElements) {
            data.put("totalElements", page.getTotalElements());
        }
        if (shouldHaveCursor) {
            if (page instanceof CursorPage) {
                data.put("cursor", ((CursorPage<?>) page).getCursor());
            } else {
                log.warn("Page should have cursor but does not: {}", page.getClass());
                data.put("cursor", null);
            }
        }
        return ExecutionResultImpl.newExecutionResult()
                                  .data(data)
                                  .build();
    }

}
