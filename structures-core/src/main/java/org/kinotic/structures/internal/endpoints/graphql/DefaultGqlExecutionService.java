package org.kinotic.structures.internal.endpoints.graphql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import graphql.*;
import graphql.execution.preparsed.PreparsedDocumentEntry;
import graphql.language.*;
import graphql.schema.GraphQLSchema;
import graphql.validation.ValidationError;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.graphql.impl.GraphQLQuery;
import me.escoffier.vertx.completablefuture.VertxCompletableFuture;
import org.apache.commons.lang3.Validate;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.endpoints.openapi.RoutingContextToEntityContextAdapter;
import org.kinotic.structures.internal.utils.GqlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by Navíd Mitchell 🤪on 6/25/23.
 */
@Component
public class DefaultGqlExecutionService implements GqlExecutionService {
    private static final Logger log = LoggerFactory.getLogger(DefaultGqlExecutionService.class);

    private final GqlOperationDefinitionService gqlOperationDefinitionService;
    private final AsyncLoadingCache<String, GraphQL> graphQLCache;
    private final ObjectMapper objectMapper;
    private final Vertx vertx;

    public DefaultGqlExecutionService(GqlOperationDefinitionService gqlOperationDefinitionService,
                                      GqlSchemaCacheLoader gqlSchemaCacheLoader,
                                      ObjectMapper objectMapper,
                                      Vertx vertx) {
        this.gqlOperationDefinitionService = gqlOperationDefinitionService;
        this.objectMapper = objectMapper;
        this.vertx = vertx;

        graphQLCache = Caffeine.newBuilder()
                               .expireAfterAccess(20, TimeUnit.HOURS)
                               .maximumSize(2000)
                               .buildAsync(gqlSchemaCacheLoader);
    }

    @Override
    public void evictCachesFor(Structure structure) {
        Validate.notNull(structure, "structure must not be null");
        graphQLCache.asMap().remove(structure.getNamespace());
        gqlOperationDefinitionService.evictCachesFor(structure);
    }

    /**
     * Executes the GQL request.
     * All logic tries to conform to the <a href="https://graphql.org/learn/serving-over-http/">GQL spec</a>
     * @param routingContext the {@link RoutingContext} for the request
     * @param query the {@link GraphQLQuery} to execute
     * @return the result as a {@link Buffer} ready to send back over the wire
     */
    @WithSpan
    @Override
    public CompletableFuture<Buffer> execute(RoutingContext routingContext, GraphQLQuery query) {

        String namespace = routingContext.pathParam(GqlVerticle.NAMESPACE_PATH_PARAMETER);

        // TODO: push this up to the GraphQLHandler, and create directly from JSON vs the GraphQLQuery intermediate object
        ExecutionInput.Builder executionInputBuilder = ExecutionInput.newExecutionInput();

        executionInputBuilder.query(query.getQuery());
        String operationName = query.getOperationName();
        if (operationName != null) {
            executionInputBuilder.operationName(operationName);
        }
        Map<String, Object> variables = query.getVariables();
        if (variables != null) {
            executionInputBuilder.variables(variables);
        }

        executionInputBuilder.localContext(new RoutingContextToEntityContextAdapter(routingContext));

        // TODO: This should maybe look at the selection set for __schema and __service since the operationName here is not required
//        if (operationName != null && (operationName.equals("IntrospectionQuery") || operationName.equals("SubgraphIntrospectQuery"))) {
            // We execute Introspection Queries using the java graphql library
            return VertxCompletableFuture.from(vertx, getOrCreateGraphQL(namespace)
                    .thenCompose(graphQL -> graphQL.executeAsync(executionInputBuilder.build()))
                    .thenApply(this::convertToBuffer));
//        } else {
//            // We execute all others using our own logic path optimized for elasticsearch
//            Participant participant = routingContext.get(EventConstants.SENDER_HEADER);
//            return VertxCompletableFuture.from(vertx, getOrCreateGraphQL(namespace))
//                                         .thenCompose(graphQL -> executeOperation(namespace,
//                                                                                  participant,
//                                                                                  graphQL,
//                                                                                  executionInputBuilder.build()))
//                                         .thenApply(executionResult -> {
//                                             Buffer buffer = convertToBuffer(executionResult);
//                                             log.trace("GraphQL {} Execution result: {}", operationName, buffer.toString());
//                                             return buffer;
//                                         });
//        }
    }

    private Buffer convertToBuffer(ExecutionResult executionResult) {
        try {
            return Buffer.buffer(objectMapper.writeValueAsBytes(executionResult.toSpecification()));
        } catch (JsonProcessingException e) {
            log.error("Error converting ExecutionResult to JSON", e);
            throw new IllegalStateException(e);
        }
    }

    private CompletableFuture<ExecutionResult> executeDocument(String namespace,
                                                               Participant participant,
                                                               Document document,
                                                               ExecutionInput executionInput) {
        if (document.getDefinitions().size() != 1) {
            throw new IllegalArgumentException("Invalid number of definitions in GraphQL document");
        }

        if (document.getDefinitions().get(0) instanceof OperationDefinition) {
            OperationDefinition opDefinition = (OperationDefinition) document.getDefinitions().get(0);

            Field selection = validateAndGetSelection(opDefinition);

            ParsedFields parsedFields = getFieldsFromSelectionSet(selection.getSelectionSet(), null);

            String operationName = selection.getName();

            GqlOperationExecutionFunction function = gqlOperationDefinitionService.findOperationExecutionFunction(operationName);
            if (function != null) {
                CompletableFuture<ExecutionResult> result
                        = function.apply(new GqlOperationArguments(namespace,
                                                                   operationName,
                                                                   parsedFields,
                                                                   participant,
                                                                   parseArguments(selection.getArguments(),
                                                                                  executionInput.getVariables())));

                return result.handle((executionResult, throwable) -> {
                    if (throwable != null) {
                        log.error("Error calling GqlOperationExecutionFunction for operation: {}", operationName, throwable);
                        return GqlUtils.convertToExecutionResult(throwable);
                    } else {
                        return executionResult;
                    }
                });
            } else {
                throw new IllegalArgumentException("Unsupported operation name: " + operationName);
            }

        } else {
            throw new IllegalArgumentException("Unsupported definition type: " + document.getDefinitions()
                                                                                         .get(0)
                                                                                         .getClass());
        }
    }

    private CompletableFuture<ExecutionResult> executeOperation(String namespace,
                                                                Participant participant,
                                                                GraphQL graphQL,
                                                                ExecutionInput executionInput) {

        Function<ExecutionInput, PreparsedDocumentEntry> computeFunction
                = transformedInput -> parseAndValidate(transformedInput, graphQL.getGraphQLSchema());

        CompletableFuture<PreparsedDocumentEntry> preparsedDoc = graphQL.getPreparsedDocumentProvider()
                                                                        .getDocumentAsync(executionInput,
                                                                                          computeFunction);
        return preparsedDoc.thenCompose(documentEntry -> {
            CompletableFuture<ExecutionResult> ret;
            if (!documentEntry.hasErrors()) {
                try {

                    ret = executeDocument(namespace,
                                          participant,
                                          documentEntry.getDocument(),
                                          executionInput);

                } catch (Exception e) {
                    log.trace("Error executing operation", e);
                    ret = CompletableFuture.completedFuture(GqlUtils.convertToExecutionResult(e));
                }
            } else {
                ret = CompletableFuture.completedFuture(new ExecutionResultImpl(documentEntry.getErrors()));
            }
            return ret;
        });
    }

    /**
     * Recursively parses a selection set and returns a list of fields
     * All fields beginning with "content" are considered content fields and will be stored in the ParsedFields.contentFields list
     *
     * @param selectionSet the selection set to parse
     * @param rootField    the root field of the selection set, or null if this is the root
     * @return a ParsedFields object containing the parsed fields
     */
    private ParsedFields getFieldsFromSelectionSet(SelectionSet selectionSet, String rootField) {
        ParsedFields ret = new ParsedFields();
        if(selectionSet != null) {
            for (Selection<?> selection : selectionSet.getSelections()) {
                // Look at FieldCollector for parsing InlineFragments used by _entities queries
                if (selection instanceof Field) {
                    Field field = (Field) selection;
                    String jsonPath = (rootField != null) ? rootField + "." + field.getName() : field.getName();
                    if (field.getSelectionSet() != null) {
                        if (rootField != null && rootField.equals("content")) {
                            ParsedFields parsedFields = getFieldsFromSelectionSet(field.getSelectionSet(), field.getName());
                            ret.getContentFields().addAll(parsedFields.getContentFields());
                            ret.getContentFields().addAll(parsedFields.getNonContentFields());
                        } else {
                            ParsedFields parsedFields = getFieldsFromSelectionSet(field.getSelectionSet(), jsonPath);
                            ret.getContentFields().addAll(parsedFields.getContentFields());
                            ret.getNonContentFields().addAll(parsedFields.getNonContentFields());
                        }
                    } else {
                        if (rootField != null && rootField.equals("content")) {
                            ret.getContentFields().add(field.getName());
                        } else {
                            ret.getNonContentFields().add(jsonPath);
                        }
                    }
                } else {
                    throw new IllegalArgumentException("Unsupported selection type: " + selection.getClass());
                }
            }
        }
        return ret;
    }

    private CompletableFuture<GraphQL> getOrCreateGraphQL(String namespace) {
        return graphQLCache.get(namespace);
    }

    private PreparsedDocumentEntry parseAndValidate(ExecutionInput executionInput,
                                                    GraphQLSchema graphQLSchema) {
        String query = executionInput.getQuery();
        PreparsedDocumentEntry ret;

        ParseAndValidateResult parseResult = ParseAndValidate.parse(executionInput);
        if (parseResult.isFailure()) {

            log.trace("Query did not parse : '{}'", executionInput.getQuery());
            ret = new PreparsedDocumentEntry(parseResult.getSyntaxException().toInvalidSyntaxError());

        } else {

            final Document document = parseResult.getDocument();

            log.trace("Validating query: '{}'", query);
            List<ValidationError> validationErrors = ParseAndValidate.validate(graphQLSchema, document);

            if (validationErrors.isEmpty()) {
                ret = new PreparsedDocumentEntry(document);
            } else {
                log.warn("Query did not validate : '{}'", query);
                ret = new PreparsedDocumentEntry(document, validationErrors);
            }
        }
        return ret;
    }

    private Map<String, Object> parseArguments(List<Argument> arguments,
                                               Map<String, Object> rawVariables) {
        Map<String, Object> ret = new HashMap<>(rawVariables.size(), 1.2F);
        for (Argument argument : arguments) {
            Value<?> value = argument.getValue();
            Object parsedValue = parseLiteral(value, rawVariables);
            ret.put(argument.getName(), parsedValue);
        }
        return ret;
    }

    private Object parseLiteral(Value<?> value, Map<String, Object> variables) {
        Object ret;
        if (value instanceof VariableReference) {
            String variableName = ((VariableReference) value).getName();
            if (variables.containsKey(variableName)) {
                ret = variables.get(variableName);
            } else {
                throw new IllegalArgumentException("Variable: " + variableName + " not supplied. But a VariableReference was found.");
            }
        } else if (value instanceof IntValue) {
            ret = ((IntValue) value).getValue();
        } else if (value instanceof FloatValue) {
            ret = ((FloatValue) value).getValue();
        } else if (value instanceof StringValue) {
            ret = ((StringValue) value).getValue();
        } else if (value instanceof BooleanValue) {
            ret = ((BooleanValue) value).isValue();
        } else if (value instanceof EnumValue) {
            ret = ((EnumValue) value).getName();
        } else if (value instanceof ArrayValue) {
            ArrayValue arrayValue = (ArrayValue) value;
            List<Object> list = new ArrayList<>(arrayValue.getValues().size());
            for (Value<?> arrayElement : arrayValue.getValues()) {
                list.add(parseLiteral(arrayElement, variables));
            }
            ret = list;
        } else if (value instanceof ObjectValue) {
            Map<String, Object> obj = new LinkedHashMap<>();
            ObjectValue input = (ObjectValue) value;
            for (ObjectField field : input.getObjectFields()) {
                obj.put(field.getName(), parseLiteral(field.getValue(), variables));
            }
            ret = obj;
        } else if (value instanceof NullValue) {
            ret = null;
        } else {
            throw new IllegalArgumentException("Unknown argument value type: " + value.getClass());
        }
        return ret;
    }

    private Field validateAndGetSelection(OperationDefinition operationDefinition) {
        if (operationDefinition.getOperation() != OperationDefinition.Operation.QUERY
                && operationDefinition.getOperation() != OperationDefinition.Operation.MUTATION) {
            throw new IllegalArgumentException("Unsupported operation type: " + operationDefinition.getOperation());
        }

        SelectionSet selectionSet = operationDefinition.getSelectionSet();
        if (selectionSet == null || selectionSet.getSelections().isEmpty()) {
            throw new IllegalArgumentException("Operation must have a selection set");
        }

        if (selectionSet.getSelections().size() == 1) {
            Selection<?> selection = selectionSet.getSelections().get(0);
            if (selection instanceof Field) {
                return (Field) selection;
            } else {
                throw new IllegalArgumentException("Operation must have a selection set of type Field");
            }
        } else {
            throw new IllegalArgumentException("Multi-operation documents not supported");
        }
    }
}
