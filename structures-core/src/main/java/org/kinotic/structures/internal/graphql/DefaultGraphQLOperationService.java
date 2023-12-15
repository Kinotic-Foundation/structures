package org.kinotic.structures.internal.graphql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.*;
import graphql.execution.preparsed.PreparsedDocumentEntry;
import graphql.language.*;
import graphql.schema.GraphQLSchema;
import graphql.validation.ValidationError;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.graphql.impl.GraphQLQuery;
import lombok.RequiredArgsConstructor;
import me.escoffier.vertx.completablefuture.VertxCompletableFuture;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.continuum.core.api.event.EventConstants;
import org.kinotic.structures.internal.endpoints.GraphQLVerticle;
import org.kinotic.structures.internal.utils.StructuresUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

/**
 * Created by Navíd Mitchell 🤪 on 12/13/23.
 */
@Component
@RequiredArgsConstructor
public class DefaultGraphQLOperationService implements GraphQLOperationService {

    private static final Logger log = LoggerFactory.getLogger(DefaultGraphQLOperationService.class);

    private final GraphQLProviderService graphQLProviderService;
    private final GraphQLOperationProviderService graphQLOperationProviderService;
    private final Vertx vertx;
    private final ObjectMapper objectMapper;

    @Override
    public CompletableFuture<Buffer> execute(RoutingContext routingContext, GraphQLQuery query) {

        String namespace = routingContext.pathParam(GraphQLVerticle.NAMESPACE_PATH_PARAMETER);

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

        if (query.getOperationName() != null && query.getOperationName().equals("IntrospectionQuery")) {

            return VertxCompletableFuture.from(vertx, graphQLProviderService.getOrCreateGraphQL(namespace)
                                                                            .thenCompose(graphQL -> graphQL.executeAsync(
                                                                                    executionInputBuilder.build()))
                                                                            .thenApply(this::convertToBuffer));

        } else {

            Participant participant = routingContext.get(EventConstants.SENDER_HEADER);
            return VertxCompletableFuture.from(vertx, graphQLProviderService.getOrCreateGraphQL(namespace))
                                         .thenCompose(graphQL -> executeOperation(namespace,
                                                                                  participant,
                                                                                  graphQL,
                                                                                  executionInputBuilder.build()))
                                         .thenApply(this::convertToBuffer);

        }
    }

    private CompletableFuture<ExecutionResult> executeOperation(String namespace,
                                                                Participant participant,
                                                                GraphQL graphQL,
                                                                ExecutionInput executionInput) {

        AtomicReference<ExecutionInput> executionInputRef = new AtomicReference<>(executionInput);
        Function<ExecutionInput, PreparsedDocumentEntry> computeFunction = transformedInput -> {
            // if they change the original query in the pre-parser, then we want to see it downstream from then on
            executionInputRef.set(transformedInput);
            return parseAndValidate(executionInputRef, graphQL.getGraphQLSchema());
        };

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
                    ret = CompletableFuture.completedFuture(convertToResult(e));
                }
            } else {
                ret = CompletableFuture.completedFuture(new ExecutionResultImpl(documentEntry.getErrors()));
            }
            return ret;
        });
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

            Field selection = validateAndGetSelection(opDefinition, executionInput.getOperationName());

            ParsedFields parsedFields = getFieldsFromSelectionSet(selection.getSelectionSet(), null);

            String operationName = selection.getName();

            GraphQLOperationDefinition definition = graphQLOperationProviderService.findOperationName(operationName);
            if (definition != null) {
                String structureId = getStructureId(namespace, operationName, definition.getOperationNamePrefix());
                Function<GraphQLOperationArguments, CompletableFuture<ExecutionResult>> function = definition.getOperationExecutionFunction();
                return function.apply(new GraphQLOperationArguments(structureId,
                                                                    participant,
                                                                    parseArguments(selection.getArguments(),
                                                                                   executionInput.getVariables()),
                                                                    parsedFields));
            } else {
                return CompletableFuture
                        .completedFuture(convertToResult(new IllegalArgumentException("Unsupported operation name: " + operationName)));
            }

        } else {
            throw new IllegalArgumentException("Unsupported definition type: " + document.getDefinitions()
                                                                                         .get(0)
                                                                                         .getClass());
        }
    }

    private Map<String, Object> parseArguments(List<Argument> arguments,
                                               Map<String, Object> rawVariables) {
        Map<String, Object> ret = new HashMap<>(rawVariables.size(), 1);
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
        for (Selection<?> selection : selectionSet.getSelections()) {
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
        return ret;
    }

    private String getStructureId(String namespace, String operationName, String operationPrefix) {
        String structureName = operationName.substring(operationPrefix.length());
        return StructuresUtil.structureNameToId(namespace, structureName);
    }

    private Field validateAndGetSelection(OperationDefinition operationDefinition, String operationName) {
        if (operationDefinition.getOperation() != OperationDefinition.Operation.QUERY
                && operationDefinition.getOperation() != OperationDefinition.Operation.MUTATION) {
            throw new IllegalArgumentException("Unsupported operation type: " + operationDefinition.getOperation());
        }

        SelectionSet selectionSet = operationDefinition.getSelectionSet();
        if (selectionSet == null || selectionSet.getSelections().isEmpty()) {
            throw new IllegalArgumentException("Operation must have a selection set");
        }

        if (selectionSet.getSelections().size() != 1 && operationName == null) {

            throw new IllegalArgumentException("Operation must have only one selection");

        } else if (operationName != null) {
            for (Selection<?> selection : selectionSet.getSelections()) {
                if (selection instanceof Field) {
                    Field field = (Field) selection;
                    if (field.getName().equals(operationName)) {
                        return field;
                    }
                }
            }
            throw new IllegalArgumentException("Operation name: " + operationName + " not found in selection set");
        } else {
            Selection<?> selection = selectionSet.getSelections().get(0);
            if (selection instanceof Field) {
                return (Field) selection;
            } else {
                throw new IllegalArgumentException("Operation must have a selection set of type Field");
            }
        }
    }

    private PreparsedDocumentEntry parseAndValidate(AtomicReference<ExecutionInput> executionInputRef,
                                                    GraphQLSchema graphQLSchema) {
        ExecutionInput executionInput = executionInputRef.get();
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

    private Buffer convertToBuffer(ExecutionResult executionResult) {
        try {
            return Buffer.buffer(objectMapper.writeValueAsBytes(executionResult.toSpecification()));
        } catch (JsonProcessingException e) {
            log.error("Error converting ExecutionResult to JSON", e);
            throw new IllegalStateException(e);
        }
    }

    private ExecutionResult convertToResult(Throwable throwable) {
        GraphQLError error = GraphQLError.newError()
                                         .message(throwable.getMessage())
                                         .build();
        return ExecutionResultImpl.newExecutionResult()
                                  .addError(error)
                                  .build();
    }

}
