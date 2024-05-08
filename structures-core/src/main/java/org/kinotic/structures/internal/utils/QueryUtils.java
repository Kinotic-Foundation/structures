package org.kinotic.structures.internal.utils;

import org.apache.commons.lang3.Validate;
import org.kinotic.structures.api.domain.QueryParameter;
import org.kinotic.structures.internal.api.services.sql.ListParameterHolder;
import org.kinotic.structures.internal.api.services.sql.MapParameterHolder;
import org.kinotic.structures.internal.api.services.sql.ParameterHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Navíd Mitchell 🤪 on 5/5/24.
 */
public class QueryUtils {

    /**
     * Extracts the parameters from the given ParameterHolder in the order specified by the parameterOrder list
     * @param parameterHolder the ParameterHolder to extract parameters from
     * @param parameterNames in the order to extract the parameters in
     * @return a list of parameters in the order specified by the parameterOrder list
     */
    public static List<Object> extractOrderedParameterList(ParameterHolder parameterHolder,
                                                           List<String> parameterNames){

        Validate.notNull(parameterHolder, "parameters must not be null");

        List<Object> ret = new ArrayList<>(parameterNames.size());

        if(parameterHolder instanceof ListParameterHolder) {

            List<QueryParameter> queryParameters
                    = ((ListParameterHolder) parameterHolder).getParameterList();

            // for now, we will just return the parameters in the order they are in the list
            for (QueryParameter queryParameter : queryParameters) {
                ret.add(queryParameter.getValue());
            }
        }else if(parameterHolder instanceof MapParameterHolder){

            Map<String, Object> queryParameters
                    = ((MapParameterHolder) parameterHolder).getParameterMap();

            for(String key : parameterNames){
                Object value = queryParameters.get(key);
                if(value != null){
                    ret.add(value);
                }else{
                    throw new IllegalArgumentException("Parameter " + key + " is missing from expected parameters");
                }
            }
        }else {
            throw new IllegalArgumentException("Unsupported ParameterHolder type: " + parameterHolder.getClass().getName());
        }
        return ret;
    }

    /**
     * Extracts a map of parameters from the given ParameterHolder
     * @param parameterHolder the ParameterHolder to extract parameters from
     * @return a map of parameters
     */
    public static Map<String, Object> extractParameterMap(ParameterHolder parameterHolder){

        Validate.notNull(parameterHolder, "parameters must not be null");

        Map<String, Object> ret;
        if(parameterHolder instanceof MapParameterHolder){

            ret = ((MapParameterHolder) parameterHolder).getParameterMap();

        }else if(parameterHolder instanceof ListParameterHolder){
            List<QueryParameter> queryParameters
                    = ((ListParameterHolder) parameterHolder).getParameterList();

            ret = new HashMap<>(queryParameters.size(), 1.5F);
            for(QueryParameter queryParameter : queryParameters){
                ret.put(queryParameter.getKey(), queryParameter.getValue());
            }
        }else {
            throw new IllegalArgumentException("Unsupported ParameterHolder type: " + parameterHolder.getClass().getName());
        }
        return ret;
    }

}
