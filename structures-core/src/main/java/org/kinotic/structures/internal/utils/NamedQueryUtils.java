package org.kinotic.structures.internal.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.continuum.idl.api.schema.ParameterDefinition;
import org.kinotic.continuum.idl.api.schema.FunctionDefinition;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.structures.api.domain.QueryParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Navíd Mitchell 🤪 on 5/5/24.
 */
public class NamedQueryUtils {

    public static Pageable convertToPageable(QueryParameter argument, ObjectMapper objectMapper){
        Validate.notNull(argument, "QueryParameter cannot be null");
        Validate.notNull(objectMapper, "ObjectMapper cannot be null");
        Pageable ret;
        if(argument.getValue() instanceof Pageable){
            ret = (Pageable) argument.getValue();
        }else if(argument.getValue() instanceof Map){
            try {
                ret = objectMapper.convertValue(argument.getValue(), Pageable.class);
            } catch (Exception e) {
                throw new IllegalArgumentException("Failed to convert QueryParameter to Pageable", e);
            }
        } else {
            throw new IllegalArgumentException("QueryParameter value must be a Pageable or a Map");
        }
        return ret;
    }

    public static boolean isPageable(ObjectC3Type objectC3Type){
        return objectC3Type.getName().equals("Pageable")
                && objectC3Type.getNamespace().equals("org.kinotic");
    }

    public static boolean isPage(ObjectC3Type objectC3Type){
        return objectC3Type.getName().equals("Page")
                && objectC3Type.getNamespace().equals("org.kinotic");
    }

    public static Integer getPageableIndex(FunctionDefinition functionDefinition){
        Integer ret = null;
        List<ParameterDefinition> parameters = functionDefinition.getParameters();
        for(int i = 0; i < parameters.size(); i++){
            ParameterDefinition parameterDefinition = parameters.get(i);
            if(parameterDefinition.getType() instanceof ObjectC3Type){
                ObjectC3Type objectC3Type = (ObjectC3Type) parameterDefinition.getType();
                if(isPageable(objectC3Type)){
                    ret = i;
                    break;
                }
            }
        }
        return ret;
    }

    public static Pair<Pageable, List<Object>> extractArgumentsList(List<QueryParameter> queryParameters,
                                                                    Integer pageableIndex,
                                                                    ObjectMapper objectMapper){
        Pageable pageable = null;
        List<Object> arguments = new ArrayList<>(queryParameters.size()-1);
        for(int i = 0; i < queryParameters.size(); i++){
            QueryParameter queryParameter = queryParameters.get(i);
            if(i == pageableIndex){
                pageable = convertToPageable(queryParameter, objectMapper);
            }else {
                arguments.add(queryParameter.getValue());
            }
        }
        return Pair.of(pageable, arguments);
    }

    public static List<Object> extractArgumentsList(List<QueryParameter> queryParameters){
        List<Object> ret = new ArrayList<>(queryParameters.size());
        for(QueryParameter queryParameter : queryParameters){
            ret.add(queryParameter.getValue());
        }
        return ret;
    }

    public static Pair<Pageable, Map<String, Object>> extractArgumentsMap(List<QueryParameter> queryParameters,
                                                                          Integer pageableIndex,
                                                                          ObjectMapper objectMapper){
        Pageable pageable = null;
        Map<String, Object> arguments = new HashMap<>(queryParameters.size()-1, 1.5F);
        for(int i = 0; i < queryParameters.size(); i++){
            QueryParameter queryParameter = queryParameters.get(i);
            if(i == pageableIndex){
                pageable = convertToPageable(queryParameter, objectMapper);
            }else {
                arguments.put(queryParameter.getKey(), queryParameter.getValue());
            }
        }
        return Pair.of(pageable, arguments);
    }

    public static Map<String, Object> extractArgumentsMap(List<QueryParameter> queryParameters){
        Map<String, Object> ret = new HashMap<>(queryParameters.size(), 1.5F);
        for(QueryParameter queryParameter : queryParameters){
            ret.put(queryParameter.getKey(), queryParameter.getValue());
        }
        return ret;
    }

}
