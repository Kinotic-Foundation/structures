package org.kinotic.structures.internal.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionResult;
import graphql.ExecutionResultImpl;
import graphql.GraphQLError;
import graphql.schema.*;
import org.kinotic.continuum.core.api.crud.CursorPage;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.structures.api.domain.DefaultEntityContext;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.internal.endpoints.graphql.GqlOperationArguments;
import org.kinotic.structures.internal.endpoints.graphql.ParsedFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;
import static graphql.introspection.Introspection.DirectiveLocation.*;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLDirective.newDirective;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLNonNull.nonNull;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * Created by Navíd Mitchell 🤪 on 6/13/24.
 */
public class GqlUtils {
    private static final Logger log = LoggerFactory.getLogger(GqlUtils.class);

    public static final GraphQLScalarType FederationPolicyScalar = GraphQLScalarType.newScalar()
                                                                             .name("federation__Policy")
                                                                             .description("Apollo custom Scalar for the federation__Policy")
                                                                             .coercing(new GqlPolicyCoercing())
                                                                             .build();

    private static final GraphQLList nestedPolicyList = GraphQLList.list( // Define the [[federation__Policy!]!] input type
                                                                   new GraphQLNonNull(
                                                                           GraphQLList.list(
                                                                                   new GraphQLNonNull(FederationPolicyScalar)
                                                                           )
                                                                   )
    );

    private static final GraphQLArgument policiesArgument = newArgument()
            .name("policies")
            .description("Nested list of federation__Policy")
            .type(new GraphQLNonNull(nestedPolicyList))
            .build();

    private static final GraphQLDirective policyDirective = newDirective()
            .name("policy")
            .description("Apollo federation policy directive")
            .argument(policiesArgument)
            .validLocations(
                    OBJECT,
                    FIELD_DEFINITION,
                    INTERFACE,
                    SCALAR,
                    ENUM
            )
            .build();

    public static ExecutionResult convertToExecutionResult(Throwable throwable) {
        GraphQLError error = GraphQLError.newError()
                                         .message(throwable.getMessage())
                                         .build();
        return ExecutionResultImpl.newExecutionResult()
                                  .addError(error)
                                  .build();
    }

    public static ExecutionResult convertToResult(String fieldName, Object data) {
        return ExecutionResultImpl.newExecutionResult()
                                  .data(Map.of(fieldName, data))
                                  .build();
    }

    public static ExecutionResult convertToResult(String fieldName, Page<?> page, ParsedFields parsedFields) {
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
                log.warn("{}.cursor requested cursor but page is not a CursorPage", fieldName);
                data.put("cursor", null);
            }
        }
        return ExecutionResultImpl.newExecutionResult()
                                  .data(Map.of(fieldName, data))
                                  .build();
    }

    public static EntityContext createContext(GqlOperationArguments args, List<String> includeFieldsFilter) {
        return new DefaultEntityContext(args.getParticipant(), includeFieldsFilter);
    }

    /**
     * Will return the structure id for the given operation.
     * This works for the built-in operations.
     * @param namespace the namespace of the structures
     * @param operationName the name of the operation
     * @param operationPrefix the prefix of the built-in operation
     * @return the structure id
     */
    public static String getStructureId(String namespace, String operationName, String operationPrefix) {
        String structureName = operationName.substring(operationPrefix.length());
        return StructuresUtil.structureNameToId(namespace, structureName);
    }

    /**
     * Creates a {@link GraphQLDirective} for the Apollo @policy directive
     * @param policies a list of policies to apply
     * @return the directive
     */
    public static GraphQLDirective policy(List<List<String>> policies){
        return newDirective(policyDirective)
                .argument(policiesArgument(policies))
                .build();
    }

    private static GraphQLArgument policiesArgument(List<List<String>> policies){
        return newArgument(policiesArgument)
                .value(policies)
                .build();
    }

    public static <T> T parseVariable(Map<String, Object> variables,
                                      String variableName,
                                      Class<T> clazz,
                                      ObjectMapper objectMapper) {
        Object value = variables.get(variableName);
        if (value != null) {
            return objectMapper.convertValue(value, clazz);
        } else {
            throw new IllegalArgumentException("Variable: " + variableName + " not supplied");
        }
    }

    public static GraphQLNamedOutputType wrapTypeWithPage(GraphQLNamedOutputType namedOutputType) {
        return newObject()
                .name(namedOutputType.getName() + "Page")
                .field(newFieldDefinition()
                               .name("totalElements")
                               .type(GraphQLInt))
                .field(newFieldDefinition()
                               .name("content")
                               .type(nonNull(GraphQLList.list(nonNull(namedOutputType)))))
                .build();
    }

    public static GraphQLNamedOutputType wrapTypeWithCursorPage(GraphQLNamedOutputType namedOutputType) {
        return newObject()
                .name(namedOutputType.getName() + "CursorPage")
                .field(newFieldDefinition()
                               .name("totalElements")
                               .type(GraphQLInt))
                .field(newFieldDefinition()
                               .name("cursor")
                               .type(GraphQLString))
                .field(newFieldDefinition()
                               .name("content")
                               .type(nonNull(GraphQLList.list(nonNull(namedOutputType)))))
                .build();
    }
}
