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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.escoffier.vertx.completablefuture.VertxCompletableFuture;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.continuum.core.api.event.EventConstants;
import org.kinotic.structures.api.domain.DefaultEntityContext;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.endpoints.GraphQLVerticle;
import org.kinotic.structures.internal.utils.StructuresUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Navíd Mitchell 🤪 on 12/13/23.
 */
@Component
@RequiredArgsConstructor
public class DefaultGraphQLOperationService implements GraphQLOperationService {

    private static final Logger log = LoggerFactory.getLogger(DefaultGraphQLOperationService.class);

    private final GraphQLProviderService graphQLProviderService;
    private final EntitiesService entitiesService;
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
                    Supplier<CompletableFuture<ExecutionResult>> resultSupplier
                            = executeDocument(namespace,
                                              participant,
                                              documentEntry.getDocument(),
                                              executionInput);

                    ret = resultSupplier.get();

                } catch (Exception e) {
                    ret = CompletableFuture.completedFuture(convertToResult(e));
                }
            }else{
                ret = CompletableFuture.completedFuture(new ExecutionResultImpl(documentEntry.getErrors()));
            }
            return ret;
        });
    }

    private Supplier<CompletableFuture<ExecutionResult>> executeDocument(String namespace,
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

            // now we normalize the variable names
            Map<String, Object> variables = new HashMap<>(executionInput.getVariables().size(), 1);
            for(Argument argument : selection.getArguments()){
                Value<?> value = argument.getValue();
                if(value instanceof VariableReference) {
                    String variableName = ((VariableReference) value).getName();
                    if (executionInput.getVariables().containsKey(variableName)) {
                        variables.put(argument.getName(), executionInput.getVariables().get(variableName));
                    } else {
                        throw new IllegalArgumentException("Variable: " + variableName + " not supplied");
                    }
                }else{
                    log.warn("Unknown argument value type: {}", value.getClass());
                    variables.put(argument.getName(), value);
                }
            }

            return createExecutionSupplier(namespace,
                                           participant,
                                           selection.getName(),
                                           variables,
                                           parsedFields);

        }else{
            throw new IllegalArgumentException("Unsupported definition type: " + document.getDefinitions().get(0).getClass());
        }
    }

    /**
     * Recursively parses a selection set and returns a list of fields
     * All fields beginning with "content" are considered content fields and will be stored in the ParsedFields.contentFields list
     * @param selectionSet the selection set to parse
     * @param rootField the root field of the selection set, or null if this is the root
     * @return a ParsedFields object containing the parsed fields
     */
    private ParsedFields getFieldsFromSelectionSet(SelectionSet selectionSet, String rootField){
        ParsedFields ret = new ParsedFields();
        for(Selection<?> selection : selectionSet.getSelections()){
            if(selection instanceof Field){
                Field field = (Field) selection;
                String jsonPath = (rootField != null) ? rootField + "." + field.getName() : field.getName();
                if(field.getSelectionSet() != null){
                    if(rootField != null && rootField.equals("content")){
                        ParsedFields parsedFields = getFieldsFromSelectionSet(field.getSelectionSet(), field.getName());
                        ret.getContentFields().addAll(parsedFields.getContentFields());
                        ret.getContentFields().addAll(parsedFields.getNonContentFields());
                    }else {
                        ParsedFields parsedFields = getFieldsFromSelectionSet(field.getSelectionSet(), jsonPath);
                        ret.getContentFields().addAll(parsedFields.getContentFields());
                        ret.getNonContentFields().addAll(parsedFields.getNonContentFields());
                    }
                }else{
                    if(rootField != null && rootField.equals("content")){
                        ret.getContentFields().add(field.getName());
                    }else{
                        ret.getNonContentFields().add(jsonPath);
                    }
                }
            } else {
                throw new IllegalArgumentException("Unsupported selection type: " + selection.getClass());
            }
        }
        return ret;
    }

    private Supplier<CompletableFuture<ExecutionResult>> createExecutionSupplier(String namespace,
                                                                         Participant participant,
                                                                         String operationName,
                                                                         Map<String, Object> variables,
                                                                         ParsedFields parsedFields){
        Supplier<CompletableFuture<ExecutionResult>> ret;
        if(operationName.startsWith("findById")){
            String structureId = getStructureId(namespace, operationName, "findById");
            ret = () -> entitiesService.findById(structureId,
                                             (String) variables.get("id"),
                                             RawJson.class,
                                             new DefaultEntityContext(participant, parsedFields.getNonContentFields()))
                                   .thenApply(rawJson -> ExecutionResultImpl.newExecutionResult()
                                                         .data(rawJson)
                                                         .build());
        } else if (operationName.startsWith("findAll")){
            ret = () -> {
                String structureId = getStructureId(namespace, operationName, "findAll");
                Pageable pageable = parseVariable(variables,"pageable", Pageable.class);
                return entitiesService.findAll(structureId,
                                               pageable,
                                               RawJson.class,
                                               new DefaultEntityContext(participant, parsedFields.getContentFields()))
                        .thenApply(page -> ExecutionResultImpl.newExecutionResult()
                                                             .data(page)
                                                             .build());
            };
        } else if (operationName.startsWith("search")) {
            ret = () -> {
                String structureId = getStructureId(namespace, operationName, "search");
                Pageable pageable = parseVariable(variables,"pageable", Pageable.class);
                String searchText = (String) variables.get("searchText");
                return entitiesService.search(structureId,
                                              searchText,
                                              pageable,
                                              RawJson.class,
                                              new DefaultEntityContext(participant, parsedFields.getContentFields()))
                                      .thenApply(page -> ExecutionResultImpl.newExecutionResult()
                                                                            .data(page)
                                                                            .build());
            };
        } else if (operationName.startsWith("save")) {
            ret = () -> {
                String structureId = getStructureId(namespace, operationName, "save");
                RawJson rawJson = (RawJson) variables.get("input");
                return entitiesService.save(structureId,
                                            rawJson,
                                            new DefaultEntityContext(participant, parsedFields.getContentFields()))
                                      .thenApply(savedEntity -> ExecutionResultImpl.newExecutionResult()
                                                                         .data(savedEntity)
                                                                         .build());
            };
        } else if (operationName.startsWith("deleteById")){
            ret = () -> {
                String structureId = getStructureId(namespace, operationName, "deleteById");
                String entityId = (String) variables.get("id");
                return entitiesService.deleteById(structureId,
                                                  entityId,
                                                  new DefaultEntityContext(participant, parsedFields.getContentFields()))
                        .thenApply(v -> ExecutionResultImpl.newExecutionResult()
                                                           .data(entityId)
                                                           .build());
            };
        } else {
            ret = () -> {
                log.warn("Unsupported operation name: {}", operationName);
                return CompletableFuture
                        .completedFuture(convertToResult(new IllegalArgumentException("Unsupported operation name: " + operationName)));
            };
        }

        return ret;
    }

    private <T> T parseVariable(Map<String, Object> variables, String variableName, Class<T> clazz){
        Object value = variables.get(variableName);
        if(value != null){
            return objectMapper.convertValue(value, clazz);
        }else {
            throw new IllegalArgumentException("Variable: " + variableName + " not supplied");
        }
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

    @NoArgsConstructor
    @Getter
    private static class ParsedFields {
        private final List<String> contentFields = new ArrayList<>();
        private final List<String> nonContentFields = new ArrayList<>();
    }

}
