package org.kinotic.structures.internal.utils;

import org.apache.commons.lang3.Validate;
import org.kinotic.structures.api.domain.Structure;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public class StructuresUtil {

    private static final Pattern IdentifierNamePattern = Pattern.compile("^[A-Za-z_][A-Za-z0-9_]*$");
    private static final Pattern StructureNamespacePattern = Pattern.compile("^[A-Za-z][A-Za-z0-9._-]*$");

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
     * Function will validate a structure
     *
     * @param structure to validate
     * @throws IllegalArgumentException will be thrown if the structure is invalid
     */
    public static void validateStructure(Structure structure){

        validateStructureName(structure.getName());

        validateStructureNamespaceName(structure.getNamespace());

        if (structure.getEntityDefinition() == null) {
            throw new IllegalArgumentException("Structure entityDefinition must not be null");
        }
    }

    /**
     * Function will validate the structure name
     *
     * @param structureName to validate
     * @throws IllegalArgumentException will be thrown if the structure name is invalid
     */
    public static void validateStructureName(String structureName){
        if (structureName == null
                || !IdentifierNamePattern.matcher(structureName).matches()){
            throw new IllegalArgumentException("Structure Name Invalid, first character must be a " +
                                               "letter, number or underscore. And contain only letters, numbers or underscores");
        }
    }

    /**
     * Function will validate the structure namespace name
     *
     * @param structureNamespace to validate
     * @throws IllegalArgumentException will be thrown if the structure namespace is invalid
     */
    public static void validateStructureNamespaceName(String structureNamespace){
        if (structureNamespace == null
                || !StructureNamespacePattern.matcher(structureNamespace).matches()){
            throw new IllegalArgumentException("Structure Namespace Invalid, first character must be a " +
                                               "letter. And contain only letters, numbers, periods, underscores or dashes");
        }
    }

    /**
     * Function will validate the property name
     *
     * @param propertyName to validate
     * @throws IllegalArgumentException will be thrown if the property name is invalid
     */
    public static void validatePropertyName(String propertyName){
        if(propertyName == null
            || propertyName.length() > 255
            || !IdentifierNamePattern.matcher(propertyName).matches()){
            throw new IllegalArgumentException("Property Name Invalid, first character must be a " +
                                               "letter, number or underscore. And contain only letters, numbers or underscores");
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
