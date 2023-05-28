package org.kinotic.structures.api.decorators.runtime;

import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.domain.Structure;

/**
 * The {@link MappingPreProcessor} is used to create a mapping based on data available in the {@link C3Decorator}
 * Created by Navíd Mitchell 🤪 on 5/26/23.
 */
public interface MappingPreProcessor<D extends C3Decorator,T extends C3Type, R> extends C3DecoratorInstance<D> {

    R process(Structure structure,
              String fieldName,
              D decorator,
              T type,
              MappingContext<R> context);

}
