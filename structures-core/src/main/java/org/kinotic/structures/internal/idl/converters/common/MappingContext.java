package org.kinotic.structures.internal.idl.converters.common;

import org.kinotic.continuum.idl.api.schema.C3Type;

/**
 * The {@link MappingContext} is provided to the {@link DecoratorMappingProcessor} to allow it to convert types that it cannot convert by itself.
 * @param <R> the type of value that this {@link MappingContext} can convert to.
 * Created by Navíd Mitchell 🤪 on 5/12/23.
 */
public interface MappingContext<R, S> {

    /**
     * This allows the {@link DecoratorMappingProcessor} to convert a type that it cannot convert by itself.
     * @param c3Type to use the internal logic to convert
     * @return the converted value
     */
    R convertInternal(C3Type c3Type);

    /**
     * @return the state of this {@link MappingContext}
     */
    S state();
}
