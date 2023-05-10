package org.kinotic.structures.api.decorators.lifecycle;

import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.decorators.runtime.C3DecoratorInstance;
import org.kinotic.structures.api.domain.Structure;

/**
 * Marker interface for all decorator processing logic.
 * NOTE: This interface is not intended to be implemented directly.
 * Created by Navíd Mitchell 🤪 on 5/10/23.
 */
public interface DecoratorProcessor<D extends C3Decorator, T, R> extends C3DecoratorInstance<D> {

    R process(Structure structure, String fieldName, D decorator, T Value);

}
