package org.kinotic.structures.internal.endpoints.graphql;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.escoffier.vertx.completablefuture.VertxCompletableFuture;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.continuum.core.api.event.EventConstants;
import org.kinotic.structures.internal.api.services.GraphQLProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private final Vertx vertx;
    private final ObjectMapper objectMapper;

    @Override
    public CompletableFuture<Buffer> execute(RoutingContext routingContext, GraphQLQuery query) {

        String namespace = routingContext.pathParam(GraphQLVerticle.NAMESPACE_PATH_PARAMETER);

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
        return preparsedDoc.thenApply(documentEntry -> {
            ExecutionResult ret;
            if (!documentEntry.hasErrors()) {
                try {
                    ParsedOperation parsedOperation = parseDocument(documentEntry.getDocument(), executionInput);

                    ret = null;
                } catch (Exception e) {
                    ret = convertToResult(e);
                }
            }else{
                ret = new ExecutionResultImpl(documentEntry.getErrors());
            }
            return ret;
        });
    }

    private ParsedOperation parseDocument(Document document, ExecutionInput executionInput) {
        if (document.getDefinitions().size() != 1) {
            throw new IllegalArgumentException("Invalid number of definitions in GraphQL document");
        }

        ParsedOperation.ParsedOperationBuilder builder = ParsedOperation.builder();
        if (document.getDefinitions().get(0) instanceof OperationDefinition) {
            OperationDefinition opDefinition = (OperationDefinition) document.getDefinitions().get(0);
            Field selection = validateAndGetSelection(opDefinition, executionInput.getOperationName());

            builder.operationId(getOperationIdForName(selection.getName()));
            ParsedFields parsedFields = getFieldsFromSelectionSet(selection.getSelectionSet(), null);
            int i = 0;
        }
        return builder.build();
    }

    private ParsedFields getFieldsFromSelectionSet(SelectionSet selectionSet, String rootField){
        ParsedFields ret = new ParsedFields();
        for(Selection<?> selection : selectionSet.getSelections()){
            if(selection instanceof Field){
                Field field = (Field) selection;
                String jsonPath = (rootField != null) ? rootField + "." + field.getName() : field.getName();
                if(field.getSelectionSet() != null){
                    if(rootField != null && rootField.equals("content")){
                        ParsedFields parsedFields = getFieldsFromSelectionSet(field.getSelectionSet(), null);
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

    private String getOperationIdForName(String operationName){
        String ret;
        if(operationName.startsWith("get")){
            ret = "getById";
        } else if (operationName.startsWith("findAll")){
            ret = "findAll";
        } else if (operationName.startsWith("search")){
            ret = "search";
        } else if (operationName.startsWith("save")){
            ret = "save";
        } else if (operationName.startsWith("bulkSave")) {
            ret = "bulkSave";
        } else if (operationName.startsWith("delete")) {
            ret = "delete";
        } else {
            throw new IllegalArgumentException("Unsupported operation name: " + operationName);
        }
        return ret;
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

            log.debug("Query did not parse : '{}'", executionInput.getQuery());
            ret = new PreparsedDocumentEntry(parseResult.getSyntaxException().toInvalidSyntaxError());

        } else {

            final Document document = parseResult.getDocument();

            log.debug("Validating query: '{}'", query);
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

    @Builder
    @Getter
    private static class ParsedOperation {
        private final String operationId;
        private final String structureName;
        private final Map<String, Object> variables;
        private final List<String> fields;
    }

    @NoArgsConstructor
    @Getter
    private static class ParsedFields {
        private final List<String> contentFields = new ArrayList<>();
        private final List<String> nonContentFields = new ArrayList<>();
    }

}
