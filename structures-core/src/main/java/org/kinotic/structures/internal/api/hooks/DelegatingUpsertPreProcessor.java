package org.kinotic.structures.internal.api.hooks;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.hooks.impl.MapUpsertPreProcessor;
import org.kinotic.structures.internal.api.hooks.impl.PojoUpsertPreProcessor;
import org.kinotic.structures.internal.api.hooks.impl.RawJsonUpsertPreProcessor;
import org.kinotic.structures.internal.api.services.EntityHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 6/7/23.
 */
public class DelegatingUpsertPreProcessor implements UpsertPreProcessor<Object, Object> {

    private final RawJsonUpsertPreProcessor rawJsonUpsertPreProcessor;
    private final MapUpsertPreProcessor mapUpsertPreProcessor;
    private final PojoUpsertPreProcessor pojoUpsertPreProcessor;

    public DelegatingUpsertPreProcessor(StructuresProperties structuresProperties,
                                        ObjectMapper objectMapper,
                                        Structure structure,
                                        Map<String, DecoratorLogic> fieldPreProcessors) {

        rawJsonUpsertPreProcessor = new RawJsonUpsertPreProcessor(structuresProperties,
                                                                  objectMapper,
                                                                  structure,
                                                                  fieldPreProcessors);
        mapUpsertPreProcessor = new MapUpsertPreProcessor(structure,
                                                          structuresProperties,
                                                          fieldPreProcessors);
        pojoUpsertPreProcessor = new PojoUpsertPreProcessor();
    }

    @SuppressWarnings("unchecked")
    @Override
    public CompletableFuture<EntityHolder> process(Object entity, EntityContext context) {
        Validate.notNull(entity, "entity must not be null");

        CompletableFuture<EntityHolder> ret;
        if(entity instanceof RawJson) {
            ret = rawJsonUpsertPreProcessor.process((RawJson) entity, context);
        } else if(entity instanceof Map) {
            ret = mapUpsertPreProcessor.process((Map<Object, Object>) entity, context);
        } else {
            ret = pojoUpsertPreProcessor.process(entity, context);
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public CompletableFuture<List<EntityHolder>> processArray(Object entities, EntityContext context) {
        Validate.notNull(entities, "entities must not be null");

        Object ret;
        if(entities instanceof RawJson) {
            ret = rawJsonUpsertPreProcessor.processArray((RawJson) entities, context);
        } else if(entities instanceof List) {
            List<?> list = (List<?>) entities;
            if(!list.isEmpty()){
                Object first = list.get(0);
                if(first instanceof Map){
                    ret = mapUpsertPreProcessor.processArray((List<Map<Object, Object>>) entities, context);
                }else{
                    ret = pojoUpsertPreProcessor.processArray((List<Object>) entities, context);
                }
            }else{
                ret = CompletableFuture.completedFuture(new ArrayList<>());
            }
        }else {
            throw new IllegalArgumentException("Unsupported type: " + entities.getClass().getName());
        }
        return (CompletableFuture<List<EntityHolder>>) ret;
    }
}
