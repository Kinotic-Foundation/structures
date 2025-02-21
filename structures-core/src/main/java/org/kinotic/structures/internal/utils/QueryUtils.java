package org.kinotic.structures.internal.utils;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.kinotic.structures.api.domain.QueryParameter;
import org.kinotic.structures.internal.api.services.sql.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Navíd Mitchell 🤪 on 5/5/24.
 */
public class QueryUtils {

    private static final Pattern aggregatePattern = Pattern.compile("\\b(AVG|COUNT|FIRST|LAST|MAX|MIN|SUM|KURTOSIS|MAD|PERCENTILE|PERCENTILE_RANK|SKEWNESS|STDDEV_POP|STDDEV_SAMP|SUM_OF_SQUARES|VAR_POP|VAR_SAMP)\\s*\\([a-zA-Z0-9_.,='() ]+\\)");
    private static final String timeZoneOptionName = "queryTimeZone".toLowerCase();
    private static final String requestTimeoutOptionName = "queryRequestTimeout".toLowerCase();
    private static final String pageTimeoutOptionName = "queryPageTimeout".toLowerCase();


    /**
     * Extracts the parameters from the given ParameterHolder in the order specified by the parameterOrder list
     * @param parameterHolder the ParameterHolder to extract parameters from
     * @param parameterNames in the order to extract the parameters in
     * @return a list of parameters in the order specified by the parameterOrder list and the QueryOptions
     */
    public static Pair<List<Object>, QueryOptions> extractOrderedParameterList(ParameterHolder parameterHolder,
                                                                               List<String> parameterNames){

        Validate.notNull(parameterHolder, "parameters must not be null");

        List<Object> parameters = new ArrayList<>(parameterNames.size());
        QueryOptions.QueryOptionsBuilder queryOptionsBuilder = QueryOptions.builder();

        if(parameterHolder instanceof ListParameterHolder listParameterHolder) {

            List<QueryParameter> queryParameters = listParameterHolder.getParameters();

            // for now, we will just return the parameters in the order they are in the list.
            // We may need to use the parameterNames list to reorder them.
            // So far the javascript client provides the order of the parameters in the list is the same as the order of the parameterNames list
            for (QueryParameter queryParameter : queryParameters) {
                if(!setQueryOptionIfApplicable(queryParameter.getKey(), queryParameter.getValue(), queryOptionsBuilder)){
                    parameters.add(queryParameter.getValue());
                }
            }
        }else if(parameterHolder instanceof MapParameterHolder mapParameterHolder){

            Map<String, Object> queryParameters = mapParameterHolder.getParameters();

            for(String key : parameterNames){
                Object value = queryParameters.get(key);
                if(value != null){
                    if(!setQueryOptionIfApplicable(key, value, queryOptionsBuilder)) {
                        parameters.add(value);
                    }
                }else{
                    throw new IllegalArgumentException("Parameter " + key + " is missing from expected parameters");
                }
            }
        }else {
            throw new IllegalArgumentException("Unsupported ParameterHolder type: " + parameterHolder.getClass().getName());
        }
        return Pair.of(parameters, queryOptionsBuilder.build());
    }

    /**
     * Extracts a map of parameters from the given ParameterHolder
     * @param parameterHolder the ParameterHolder to extract parameters from
     * @return a map of parameters and the QueryOptions
     */
    public static Pair<Map<String, Object>, QueryOptions> extractParameterMap(ParameterHolder parameterHolder){

        Validate.notNull(parameterHolder, "parameters must not be null");

        Map<String, Object> parameters;
        QueryOptions.QueryOptionsBuilder queryOptionsBuilder = QueryOptions.builder();
        if(parameterHolder instanceof MapParameterHolder){

            parameters = new HashMap<>();
            for(Map.Entry<String, Object> entry : ((MapParameterHolder) parameterHolder).getParameters().entrySet()){
                if(!setQueryOptionIfApplicable(entry.getKey(), entry.getValue(), queryOptionsBuilder)){
                    parameters.put(entry.getKey(), entry.getValue());
                }
            }

        }else if(parameterHolder instanceof ListParameterHolder){
            List<QueryParameter> queryParameters
                    = ((ListParameterHolder) parameterHolder).getParameters();

            parameters = new HashMap<>(queryParameters.size(), 1.2F);
            for(QueryParameter queryParameter : queryParameters){
                if(!setQueryOptionIfApplicable(queryParameter.getKey(), queryParameter.getValue(), queryOptionsBuilder)) {
                    parameters.put(queryParameter.getKey(), queryParameter.getValue());
                }
            }
        }else {
            throw new IllegalArgumentException("Unsupported ParameterHolder type: " + parameterHolder.getClass().getName());
        }
        return Pair.of(parameters, queryOptionsBuilder.build());
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean setQueryOptionIfApplicable(String key, Object value, QueryOptions.QueryOptionsBuilder queryOptionsBuilder){
        if(key.toLowerCase().equals(timeZoneOptionName)){
            queryOptionsBuilder.timeZone((String) value);
            return true;
        }else if(key.toLowerCase().equals(requestTimeoutOptionName)){
            queryOptionsBuilder.requestTimeout((Integer) value);
            return true;
        }else if(key.toLowerCase().equals(pageTimeoutOptionName)){
            queryOptionsBuilder.pageTimeout((String) value);
            return true;
        }
        return false;
    }

    public static SqlQueryType determineQueryType(String query){
        if(query.toLowerCase().startsWith("select")) {
            if(aggregatePattern.matcher(query.toUpperCase()).find()){
                return SqlQueryType.AGGREGATE;
            }else {
                return SqlQueryType.SELECT;
            }
        }else if(query.toLowerCase().startsWith("update")) {
            return SqlQueryType.UPDATE;
        }else if(query.toLowerCase().startsWith("delete")) {
            return SqlQueryType.DELETE;
        }else if(query.toLowerCase().startsWith("insert")) {
            return SqlQueryType.INSERT;
        }else {
            throw new IllegalArgumentException("Unsupported statement " + query);
        }
    }

}
