package org.kinotic.structures.internal.api.decorators;

import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.decorators.runtime.UpsertFieldPreProcessor;

/**
 * {@link DecoratorLogic} holds a {@link C3Decorator} and its {@link UpsertFieldPreProcessor}
 * The {@link C3Decorator} is the decorator that was assigned to some data.
 * The {@link UpsertFieldPreProcessor} will be provided with the {@link C3Decorator} and the value to process.
 *
 * Created by Navíd Mitchell 🤪 on 5/10/23.
 */
public class DecoratorLogic<D extends C3Decorator, R, T, P extends UpsertFieldPreProcessor<D, R, T>> {

    private final D decorator;

    private final P processor;

    public DecoratorLogic(D decorator, P processor) {
        this.decorator = decorator;
        this.processor = processor;
    }

    public D getDecorator() {
        return decorator;
    }

    public P getProcessor() {
        return processor;
    }
}
