package org.kinotic.structures.internal.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionResult;
import graphql.ExecutionResultImpl;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNamedOutputType;
import graphql.schema.GraphQLNonNull;
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
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * Created by Navíd Mitchell 🤪 on 6/13/24.
 */
public class GqlUtils {
    private static final Logger log = LoggerFactory.getLogger(GqlUtils.class);

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
                log.warn("Page should have cursor but does not: {}", page.getClass());
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
                               .type(GraphQLNonNull.nonNull(GraphQLList.list(GraphQLNonNull.nonNull(
                                       namedOutputType)))))
                .build();
    }
}
