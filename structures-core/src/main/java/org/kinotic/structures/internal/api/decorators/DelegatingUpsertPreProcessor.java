package org.kinotic.structures.internal.api.decorators;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.domain.Structure;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 6/7/23.
 */
public class DelegatingUpsertPreProcessor implements UpsertPreProcessor<Object> {

    private final RawJsonUpsertPreProcessor rawJsonUpsertPreProcessor;
    private final MapUpsertPreProcessor mapUpsertPreProcessor;
    private final PojoUpsertPreProcessor pojoUpsertPreProcessor;

    public DelegatingUpsertPreProcessor(ObjectMapper objectMapper,
                                        Structure structure,
                                        Map<String, DecoratorLogic> fieldPreProcessors) {
        rawJsonUpsertPreProcessor = new RawJsonUpsertPreProcessor(objectMapper, structure, fieldPreProcessors);

        mapUpsertPreProcessor = new MapUpsertPreProcessor();
        pojoUpsertPreProcessor = new PojoUpsertPreProcessor();
    }

    @Override
    public CompletableFuture<EntityHolder<Object>> process(Object entity, EntityContext context) {
        Validate.notNull(entity, "entity must not be null");

        Object ret;
        if(entity instanceof RawJson) {
            ret = rawJsonUpsertPreProcessor.process((RawJson) entity, context);
        } else if(entity instanceof Map) {
            ret = mapUpsertPreProcessor.process((Map<?, ?>) entity, context);
        } else {
            ret = pojoUpsertPreProcessor.process(entity, context);
        }
        //noinspection unchecked
        return (CompletableFuture<EntityHolder<Object>>) ret;
    }
}
