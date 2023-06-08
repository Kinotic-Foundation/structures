package org.kinotic.structures.api.decorators.runtime;

import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.domain.Structure;

/**
 * The {@link MappingPreProcessor} is used to create a mapping based on data available in the {@link C3Decorator}
 * Created by Navíd Mitchell 🤪 on 5/26/23.
 */
public interface MappingPreProcessor<D extends C3Decorator, R> extends C3DecoratorInstance<D> {

    /**
     * Verify that this {@link MappingPreProcessor} supports the given {@link C3Type}
     * @param c3Type the {@link C3Type} to check
     * @return true if this {@link MappingPreProcessor} supports the given {@link C3Type}
     */
    boolean supportC3Type(C3Type c3Type);

    /**
     * Process the given {@link Structure} and {@link C3Decorator} to create or modify a mapping
     * @param structure the {@link Structure} that is being processed
     * @param fieldName the name of the field that is being processed or null if this is a root level mapping
     * @param decorator the {@link C3Decorator} that is being processed
     * @param type the {@link C3Type} that is being processed
     * @param context the {@link MappingContext} for use by this {@link MappingPreProcessor}
     * @return the mapping that was created or modified
     */
    R process(Structure structure,
              String fieldName,
              D decorator,
              C3Type type,
              MappingContext<R> context);

}
