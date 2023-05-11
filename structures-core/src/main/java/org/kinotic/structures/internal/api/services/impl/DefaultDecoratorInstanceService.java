package org.kinotic.structures.internal.api.services.impl;

import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.decorators.lifecycle.UpsertFieldPreProcessor;
import org.kinotic.structures.api.decorators.runtime.C3DecoratorInstance;
import org.kinotic.structures.internal.api.services.DecoratorInstanceService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Navíd Mitchell 🤪 on 5/11/23.
 */
@Component
public class DefaultDecoratorInstanceService implements DecoratorInstanceService {

    private final Map<Class<? extends C3Decorator>, C3DecoratorInstance<?>> decoratorInstances;
    private final Map<Class<? extends C3Decorator>, UpsertFieldPreProcessor<?, ?, ?>> upsertFieldPreProcessors = new HashMap<>();

    public DefaultDecoratorInstanceService(List<C3DecoratorInstance<?>> decoratorInstances){
        this.decoratorInstances = new HashMap<>(decoratorInstances.size());

        for(C3DecoratorInstance<?> instance : decoratorInstances){
            this.decoratorInstances.put(instance.implementsDecorator(), instance);

            if(instance instanceof UpsertFieldPreProcessor){
                UpsertFieldPreProcessor<?, ?, ?> processor = (UpsertFieldPreProcessor<?, ?, ?>) instance;
                this.upsertFieldPreProcessors.put(processor.implementsDecorator(), processor);
            }
        }
    }

    @Override
    public <T extends C3Decorator> C3DecoratorInstance<T> findDecoratorInstance(Class<T> decoratorClass) {
        //noinspection unchecked
        return (C3DecoratorInstance<T>) decoratorInstances.get(decoratorClass);
    }

    @Override
    public <D extends C3Decorator, R, T> UpsertFieldPreProcessor<D, R, T> findUpsertFieldPreProcessor(Class<D> decoratorClass) {
        //noinspection unchecked
        return (UpsertFieldPreProcessor<D, R, T>) upsertFieldPreProcessors.get(decoratorClass);
    }

}
