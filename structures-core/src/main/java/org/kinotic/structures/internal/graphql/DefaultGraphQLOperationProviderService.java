package org.kinotic.structures.internal.graphql;

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
public class DefaultGraphQLOperationProviderService implements GraphQLOperationProviderService {

    private static final Logger log = LoggerFactory.getLogger(DefaultGraphQLOperationProviderService.class);

    private final Trie<GraphQLOperationDefinition> operationTrie = new Trie<>();
    private final List<GraphQLOperationDefinition> operationDefinitions;
    private final ObjectMapper objectMapper;


    public DefaultGraphQLOperationProviderService(EntitiesService entitiesService,
                                                  ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.operationDefinitions = List.of(
                GraphQLOperationDefinition.builder()
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

                GraphQLOperationDefinition.builder()
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

                GraphQLOperationDefinition.builder()
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

                GraphQLOperationDefinition.builder()
                                          .operationNamePrefix("save")
                                          .operationType(OperationDefinition.Operation.MUTATION)
                                          .fieldDefinitionFunction(args -> newFieldDefinition()
                                                  .name("save" + args.getStructuresName())
                                                  .type(GraphQLID)
                                                  .argument(newArgument().name("input")
                                                                         .type(nonNull(args.getInputType())))
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
                                          .build(),

                GraphQLOperationDefinition.builder()
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
                                              List<RawJson> input = (List<RawJson>) args.getVariables().get("input");
                                              EntityContext context = createContext(args, null);
                                              return entitiesService.bulkSave(args.getStructureId(),
                                                                              input,
                                                                              context)
                                                                    .thenApply(res -> convertToResult(Boolean.TRUE));
                                          })
                                          .build(),

                GraphQLOperationDefinition.builder()
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

        for(GraphQLOperationDefinition definition : operationDefinitions){
            operationTrie.insert(definition.getOperationNamePrefix(), definition);
        }
    }

    @Override
    public List<GraphQLOperationDefinition> getOperationDefinitions() {
        return operationDefinitions;
    }

    @Override
    public GraphQLOperationDefinition findOperationName(String completeOperationName) {
        long now = System.nanoTime();
        GraphQLOperationDefinition ret = operationTrie.findValue(completeOperationName);
        log.debug("Finished Searching Trie for: {} in {}ns", completeOperationName, System.nanoTime() - now);
        return ret;
    }

    private EntityContext createContext(GraphQLOperationArguments args, List<String> includeFieldsFilter) {
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
