package org.kinotic.structures.api.decorators.runtime;

import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;

/**
 * Provides the implementation of a {@link C3Decorator}
 *
 * @param <T> the {@link C3Decorator} class that this instance implements
 *
 * Created by Navíd Mitchell 🤪 on 5/9/23.
 */
public interface C3DecoratorInstance<T extends C3Decorator> {

    /**
     * @return the {@link C3Decorator} class that this instance implements
     */
    Class<T> implementsDecorator();

    /**
     * FIXME: remove this since it is kinda hacky, instead scan for all class that extend {@link C3Decorator}
     * Logical type name used to register the {@link C3Decorator} with Jackson as a subtype
     * @return string containing the logical type name or null if the {@link C3Decorator} should not be registered
     */
    String decoratorTypeName();

}
