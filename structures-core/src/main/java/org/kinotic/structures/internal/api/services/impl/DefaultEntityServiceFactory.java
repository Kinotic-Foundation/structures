package org.kinotic.structures.internal.api.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.decorators.lifecycle.UpsertFieldPreProcessor;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.decorators.DecoratorLogic;
import org.kinotic.structures.internal.api.services.EntityService;
import org.kinotic.structures.internal.api.services.EntityServiceFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * This simplifies the creation of the EntityService, since a lot of dependencies are needed.
 * Created by Navíd Mitchell 🤪 on 5/10/23.
 */
@Component
public class DefaultEntityServiceFactory implements EntityServiceFactory {

    private final ObjectMapper objectMapper;

    public DefaultEntityServiceFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public CompletableFuture<EntityService> createEntityService(Structure structure) {

        Map<String, DecoratorLogic<C3Decorator, Object, Object,
                UpsertFieldPreProcessor<C3Decorator, Object, Object>>> fieldPreProcessors;



//        return CompletableFuture.completedFuture(new DefaultEntityService(objectMapper, structure));
        return null;
    }
}
