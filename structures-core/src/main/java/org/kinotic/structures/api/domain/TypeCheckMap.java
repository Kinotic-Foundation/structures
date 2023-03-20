/*
 *
 * Copyright 2008-2021 Kinotic and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kinotic.structures.api.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class TypeCheckMap extends LinkedHashMap<String, Object> {

    public TypeCheckMap() {
    }

    public TypeCheckMap(Map<? extends String, ?> m) {
        super(m);
    }

    /**
     * Returns the number of name/value mappings in this object.
     * @return the number of name/value mappings in this object
     */
    public int length() {
        return this.size();
    }

    /**
     * Maps {@code name} to {@code value}, clobbering any existing name/value mapping with
     * the same name.
     * @param name the name of the property
     * @param value the value of the property
     * @return this object.
     * @throws IllegalStateException if an error occurs
     */
    public TypeCheckMap amend(String name, boolean value) throws IllegalStateException {
        this.put(checkName(name), value);
        return this;
    }

    /**
     * Maps {@code name} to {@code value}, clobbering any existing name/value mapping with
     * the same name.
     * @param name the name of the property
     * @param value a finite value. May not be {@link Double#isNaN() NaNs} or
     * {@link Double#isInfinite() infinities}.
     * @return this object.
     * @throws IllegalStateException if an error occurs
     */
    public TypeCheckMap amend(String name, double value) throws IllegalStateException {
        checkDouble(value);
        this.put(checkName(name), value);
        return this;
    }

    /**
     * Maps {@code name} to {@code value}, clobbering any existing name/value mapping with
     * the same name.
     * @param name the name of the property
     * @param value the value of the property
     * @return this object.
     * @throws IllegalStateException if an error occurs
     */
    public TypeCheckMap amend(String name, int value) throws IllegalStateException {
        this.put(checkName(name), value);
        return this;
    }

    /**
     * Maps {@code name} to {@code value}, clobbering any existing name/value mapping with
     * the same name.
     * @param name the name of the property
     * @param value the value of the property
     * @return this object.
     * @throws IllegalStateException if an error occurs
     */
    public TypeCheckMap amend(String name, long value) throws IllegalStateException {
        this.put(checkName(name), value);
        return this;
    }

    /**
     * Maps {@code name} to {@code value}, clobbering any existing name/value mapping with
     * the same name. If the value is {@code null}, any existing mapping for {@code name}
     * is removed.
     * @param name the name of the property
     * @param value a {@link TypeCheckMap}, String, Boolean, Integer,
     * Long, Double, or {@code null}. May not be {@link Double#isNaN()
     * NaNs} or {@link Double#isInfinite() infinities}.
     * @return this object.
     * @throws IllegalStateException if an error occurs
     */
    public TypeCheckMap amend(String name, Object value) throws IllegalStateException {
        if (value == null) {
            this.remove(name);
            return this;
        }
        if (value instanceof Number) {
            // deviate from the original by checking all Numbers, not just floats &
            // doubles
            checkDouble(((Number) value).doubleValue());
        }
        this.put(checkName(name), value);
        return this;
    }

    /**
     * Removes the named mapping if it exists; does nothing otherwise.
     *
     * @param name the name of the property
     * @return the value previously mapped by {@code name}, or null if there was no such
     * mapping.
     */
    public Object remove(String name) {
        return this.remove(name);
    }

    /**
     * Returns true if this object has no mapping for {@code name} or if it has a mapping
     * whose value is null.
     * @param name the name of the property
     * @return true if this object has no mapping for {@code name}
     */
    public boolean isNull(String name) {
        Object value = this.get(name);
        return value == null;
    }

    /**
     * Returns true if this object has a mapping for {@code name}.
     * @param name the name of the property
     * @return true if this object has a mapping for {@code name}
     */
    public boolean has(String name) {
        return this.containsKey(name);
    }

    /**
     * Returns the value mapped by {@code name}.
     * @param name the name of the property
     * @return the value
     * @throws IllegalStateException if no such mapping exists.
     */
    public Object getProp(String name) throws IllegalStateException {
        Object result = this.get(name);
        if (result == null) {
            throw new IllegalStateException("No value for " + name);
        }
        return result;
    }


    /**
     * Returns the value mapped by {@code name} if it exists and is a boolean or can be
     * coerced to a boolean.
     * @param name the name of the property
     * @return the value
     * @throws IllegalArgumentException if the mapping doesn't exist or cannot be coerced to a
     * boolean.
     */
    public boolean getBoolean(String name) throws IllegalArgumentException {
        Object object = this.get(name);
        Boolean result = toBoolean(object);
        if (result == null) {
            throw typeMismatch(name, object, "boolean");
        }
        return result;
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is a double or can be
     * coerced to a double.
     *
     * @param name the name of the property
     * @return the value
     * @throws IllegalArgumentException if the mapping doesn't exist or cannot be coerced to a
     * double.
     */
    public double getDouble(String name) throws IllegalArgumentException {
        Object object = get(name);
        Double result = toDouble(object);
        if (result == null) {
            throw typeMismatch(name, object, "double");
        }
        return result;
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is an int or can be
     * coerced to an int.
     * @param name the name of the property
     * @return the value
     * @throws IllegalArgumentException if the mapping doesn't exist or cannot be coerced to an int.
     */
    public int getInt(String name) throws IllegalArgumentException {
        Object object = get(name);
        Integer result = toInteger(object);
        if (result == null) {
            throw typeMismatch(name, object, "int");
        }
        return result;
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is a long or can be
     * coerced to a long. Note that JSON represents numbers as doubles, so this is
     * <a href="#lossy">lossy</a>; use strings to transfer numbers via JSON.
     * @param name the name of the property
     * @return the value
     * @throws IllegalArgumentException if the mapping doesn't exist or cannot be coerced to a long.
     */
    public long getLong(String name) throws IllegalArgumentException {
        Object object = get(name);
        Long result = toLong(object);
        if (result == null) {
            throw typeMismatch(name, object, "long");
        }
        return result;
    }

    /**
     * Returns the value mapped by {@code name} if it exists, coercing it if necessary.
     * @param name the name of the property
     * @return the value
     * @throws IllegalArgumentException if no such mapping exists.
     */
    public String getString(String name) throws IllegalArgumentException {
        Object object = get(name);
        String result = toString(object);
        if (result == null) {
            throw typeMismatch(name, object, "String");
        }
        return result;
    }

    /**
     * Returns the value mapped by {@code name} if it exists and is a {@code
     * JSONObject}.
     * @param name the name of the property
     * @return the value
     * @throws IllegalArgumentException if the mapping doesn't exist or is not a {@code
     *     JSONObject}.
     */
    public TypeCheckMap getTypeCheckMap(String name) throws IllegalArgumentException {
        Object object = get(name);
        if (object instanceof TypeCheckMap) {
            return (TypeCheckMap) object;
        }else if(object instanceof Map){
            return new TypeCheckMap((Map<String, Object>) object);
        } else {
            throw typeMismatch(name, object, "TypeCheckMap");
        }
    }

    /**
     * Returns an iterator of the {@code String} names in this object. The returned
     * iterator supports {@link Iterator#remove() remove}, which will remove the
     * corresponding mapping from this object. If this object is modified after the
     * iterator is returned, the iterator's behavior is undefined. The order of the keys
     * is undefined.
     * @return the keys
     */
    /* Return a raw type for API compatibility */
    @SuppressWarnings("rawtypes")
    public Iterator keys() {
        return this.keySet().iterator();
    }

    /**
     * Returns an array containing the string names in this object. This method returns
     * null if this object contains no mappings.
     * @return the array
     */
    public ArrayList names() {
        return this.isEmpty() ? null
                : new ArrayList<>(this.keySet());
    }

    Boolean toBoolean(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof String) {
            String stringValue = (String) value;
            if ("true".equalsIgnoreCase(stringValue)) {
                return true;
            }
            if ("false".equalsIgnoreCase(stringValue)) {
                return false;
            }
        }
        return null;
    }

    Double toDouble(Object value) {
        if (value instanceof Double) {
            return (Double) value;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        if (value instanceof String) {
            try {
                return Double.valueOf((String) value);
            }
            catch (NumberFormatException ignored) {
            }
        }
        return null;
    }

    Integer toInteger(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof String) {
            try {
                return (int) Double.parseDouble((String) value);
            }
            catch (NumberFormatException ignored) {
            }
        }
        return null;
    }

    Long toLong(Object value) {
        if (value instanceof Long) {
            return (Long) value;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        if (value instanceof String) {
            try {
                return (long) Double.parseDouble((String) value);
            }
            catch (NumberFormatException ignored) {
            }
        }
        return null;
    }

    String toString(Object value) {
        if (value instanceof String) {
            return (String) value;
        }
        if (value != null) {
            return String.valueOf(value);
        }
        return null;
    }

    IllegalArgumentException typeMismatch(Object indexOrName, Object actual,
                                             String requiredType) throws IllegalArgumentException {
        if (actual == null) {
            throw new IllegalArgumentException("Value at " + indexOrName + " is null.");
        }
        throw new IllegalArgumentException("Value " + actual + " at " + indexOrName + " of type "
                + actual.getClass().getName() + " cannot be converted to "
                + requiredType);
    }

    IllegalArgumentException typeMismatch(Object actual, String requiredType)
            throws IllegalArgumentException {
        if (actual == null) {
            throw new IllegalArgumentException("Value is null.");
        }
        throw new IllegalArgumentException(
                "Value " + actual + " of type " + actual.getClass().getName()
                        + " cannot be converted to " + requiredType);
    }

    String checkName(String name) {
        if (name == null) {
            throw new IllegalStateException("Names must be non-null");
        }
        return name;
    }

    double checkDouble(double value){
        if (Double.isInfinite(value) || Double.isNaN(value)) {
            throw new IllegalStateException("Forbidden numeric value: " + value);
        }
        return value;
    }
}
