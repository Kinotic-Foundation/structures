package org.kinotic.structures.internal.utils;

import org.apache.commons.lang3.Validate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class StructuresUtil {

    /**
     * Function will convert a structure namespace and name to a valid
     * @param structureNamespace to convert
     * @param structureName to convert
     * @return a valid structure id
     */
    public static String structureNameToId(String structureNamespace, String structureName){
        return (structureNamespace + "." + structureName).toLowerCase();
    }

    /**
     * Function will validate the index name, ensures we can use it for creating an
     * elasticsearch index.
     *
     * @param indexName to validate
     * @throws IllegalArgumentException will be thrown if the index name is invalid
     */
    public static void indexNameValidation(String indexName) throws IllegalArgumentException {
        //    255 characters in length
        //    must be lowercase
        //    must not start with '_', '-', or '+'
        //    must not be '.' or '..' -
        //
        //    must not contain the following characters
        //    '\\', '/', '*', '?', '"', '<', '>', '|', ' ', ',', '#', ':', ';'
        if(indexName.startsWith("_")
                || indexName.startsWith("-")
                || indexName.startsWith("+")
                || indexName.startsWith(".")
                || indexName.startsWith("..")
                || indexName.contains("\\")
                || indexName.contains("/")
                || indexName.contains("*")
                || indexName.contains("?")
                || indexName.contains("\"")
                || indexName.contains("<")
                || indexName.contains(">")
                || indexName.contains("|")
                || indexName.contains(" ")
                || indexName.contains(",")
                || indexName.contains("#")
                || indexName.contains(":")
                || indexName.contains(";")
                || indexName.contains("..")
                || indexName.getBytes().length > 255){
            throw new IllegalArgumentException("Elastic index name is not in correct format, \ncannot start with _ - +\ncannot contain . .. \\ / * ? \" < > | , # : ; space, or be longer than 255 bytes");
        }
    }

    public static void fieldNameValidation(String fieldName){
        if(fieldName.contains("-")
                || fieldName.contains("+")
                || fieldName.contains(".")
                || fieldName.contains("..")
                || fieldName.contains("\\")
                || fieldName.contains("/")
                || fieldName.contains("*")
                || fieldName.contains("?")
                || fieldName.contains("\"")
                || fieldName.contains("<")
                || fieldName.contains(">")
                || fieldName.contains("|")
                || fieldName.contains(" ")
                || fieldName.contains(",")
                || fieldName.contains("#")
                || fieldName.contains(":")
                || fieldName.contains(";")
                || fieldName.getBytes().length > 255){
            throw new IllegalArgumentException("Field Name is not in correct format, \ncannot contain - + . .. \\ / * ? \" < > | , # : ; space, or be longer than 255 bytes");
        }
    }

    /**
     * Function will convert a List to a Map using the provided mapping function.
     * @param list to convert
     * @param mappingFunction to use derive the key from the value
     * @return a map of the list
     * @param <K> the type of the key
     * @param <T> the type of the list
     * @throws IllegalArgumentException if multiple values map to the same key
     */
    public static <K, T> Map<K, T> listToMap(List<T> list, Function<T, K> mappingFunction){
        Validate.notNull(list, "list cannot be null");
        Map<K, T> ret = new LinkedHashMap<>(list.size());
        for(T value : list){
            K key = mappingFunction.apply(value);
            if(ret.containsKey(key)){
                T existing = ret.get(key);
                throw new IllegalArgumentException("Multiple values that map to the same key: " + key
                                                           + "\n existing: " + existing.getClass().getName() + " new: " + value.getClass().getName());
            }
            ret.put(key, value);
        }
        return ret;
    }

}
