package org.kinotic.structures.api.decorators.runtime;

import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.domain.Structure;

/**
 * Marker interface for all decorator processing logic.
 * NOTE: This interface is not intended to be implemented directly
 *
 * @param <D> the {@link C3Decorator} class that this instance implements
 * @param <R> the type of the value that this processor will return
 * @param <T> the type of the value that this processor will process
 *
 * Created by Navíd Mitchell 🤪 on 5/10/23.
 */
public interface DecoratorProcessor<D extends C3Decorator, R, T> extends C3DecoratorInstance<D> {

    R process(Structure structure, String fieldName, D decorator, T value);

}
