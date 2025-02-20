package org.kinotic.structures.api.domain;

import java.util.List;
import java.util.Map;

/**
 * Holds information for all "Entity" related operations.
 * Created by Navíd Mitchell 🤪 on 6/7/23.
 */
public interface EntityContext extends SecurityContext{

    /**
     * If defined, this will restrict the response to only include the fields listed here.
     * @return a list of included fields, if {@link List} is empty no fields will be included, if null all fields will be included.
     */
    List<String> getIncludedFieldsFilter();

    /**
     * Returns whether there is an included fields filter defined.
     * @return true if an included fields filter is defined, false otherwise
     */
    boolean hasIncludedFieldsFilter();

    /**
     * Put some arbitrary data in the context. This will be available in any services that receive the context.
     *
     * @param key  the key for the data
     * @param obj  the data
     * @return a reference to this, so the API can be used fluently
     */
    EntityContext put(String key, Object obj);

    /**
     * Put all the data in the map into the context. This will be available in any services that receive the context.
     *
     * @param value  the data
     * @return a reference to this, so the API can be used fluently
     */
    EntityContext putAll(Map<String, Object> value);

    /**
     * Get some data from the context. The data is available in any services that receive the context.
     *
     * @param key  the key for the data
     * @param <T>  the type of the data
     * @return  the data
     * @throws ClassCastException if the data is not of the expected type
     */
    <T> T get(String key);

    /**
     * Remove some data from the context. The data is available in any services that receive the context.
     *
     * @param key  the key for the data
     * @param <T>  the type of the data
     * @return  the previous data associated with the key
     * @throws ClassCastException if the data is not of the expected type
     */
    <T> T remove(String key);

    /**
     * @return all the context data as a map
     */
    Map<String, Object> data();

}
