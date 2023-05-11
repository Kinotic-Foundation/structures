package org.kinotic.structures.internal.api.services;

import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.decorators.lifecycle.UpsertFieldPreProcessor;
import org.kinotic.structures.api.decorators.runtime.C3DecoratorInstance;

/**
 * Created by Navíd Mitchell 🤪 on 5/10/23.
 */
public interface DecoratorInstanceService {

    /**
     * Finds the {@link C3DecoratorInstance} for the given {@link C3Decorator} class
     * @param decoratorClass to find the instance for
     * @return the instance or null if not found
     * @param <T> the type of {@link C3Decorator}
     */
    <T extends C3Decorator> C3DecoratorInstance<T> findDecoratorInstance(Class<T> decoratorClass);

    /**
     * Finds the {@link UpsertFieldPreProcessor} for the given {@link C3Decorator} class
     * @param decoratorClass to find the instance for
     * @return the instance or null if not found
     * @param <D> the type of {@link C3Decorator}
     * @param <R> the type of the field after processing
     * @param <T> the type of the field being processed
     */
    <D extends C3Decorator, R, T> UpsertFieldPreProcessor<D, R, T> findUpsertFieldPreProcessor(Class<D> decoratorClass);

}
