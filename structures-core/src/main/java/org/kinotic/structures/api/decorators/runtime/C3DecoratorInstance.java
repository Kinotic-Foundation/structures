package org.kinotic.structures.api.decorators.runtime;

import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;

/**
 * Provides the implementation of a {@link C3Decorator}
 * Created by Navíd Mitchell 🤪 on 5/9/23.
 */
public interface C3DecoratorInstance<T extends C3Decorator> {

    /**
     * @return the {@link C3Decorator} class that this instance implements
     */
    Class<T> implementsDecorator();

    /**
     * Logical type name used to register the {@link C3Decorator} with Jackson as a subtype
     * @return string containing the logical type name
     */
    String decoratorTypeName();

}
