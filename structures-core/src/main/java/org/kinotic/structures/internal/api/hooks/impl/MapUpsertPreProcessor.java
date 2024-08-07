package org.kinotic.structures.internal.api.hooks.impl;

import org.apache.commons.lang3.tuple.Pair;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.domain.idl.decorators.AutoGeneratedIdDecorator;
import org.kinotic.structures.api.domain.idl.decorators.IdDecorator;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.hooks.DecoratorLogic;
import org.kinotic.structures.internal.api.hooks.UpsertFieldPreProcessor;
import org.kinotic.structures.internal.api.hooks.UpsertPreProcessor;
import org.kinotic.structures.internal.api.services.EntityHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 6/7/23.
 */
public class MapUpsertPreProcessor implements UpsertPreProcessor<Map<Object, Object>, List<Map<Object, Object>>> {

    private static final Logger log = LoggerFactory.getLogger(MapUpsertPreProcessor.class);
    // Map of json path to decorator logic
    private final Map<String, DecoratorLogic> fieldPreProcessors;
    private final Structure structure;
    private Pair<String, DecoratorLogic> idFieldPreProcessor = null;

    public MapUpsertPreProcessor(Structure structure,
                                 Map<String, DecoratorLogic> fieldPreProcessors) {
        this.structure = structure;
        this.fieldPreProcessors = fieldPreProcessors;

        // this is a temporary solution since we only have an id field preprocessor now.
        for(Map.Entry<String, DecoratorLogic> entry : fieldPreProcessors.entrySet()) {
            C3Decorator decorator = entry.getValue().getDecorator();
            if(decorator instanceof IdDecorator
                || decorator instanceof AutoGeneratedIdDecorator) {
                idFieldPreProcessor = Pair.of(entry.getKey(), entry.getValue());
                break;
            }
        }
        if(idFieldPreProcessor == null) {
            log.warn("No id field preprocessor found for structure: {}", structure);
        }
    }

    @Override
    public CompletableFuture<EntityHolder> process(Map<Object, Object> entity,
                                                   EntityContext context) {
        try {
            // ids are not allowed to be nested
            // FIXME: why isn't contains(.) just checked during C3Type conversion, and not needed here?
            if(idFieldPreProcessor != null && !idFieldPreProcessor.getLeft().contains(".")){
                return CompletableFuture.completedFuture(processIdField(entity, context));
            }else{
                return CompletableFuture.failedFuture(new IllegalStateException("No id field found"));
            }
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public CompletableFuture<List<EntityHolder>> processArray(List<Map<Object, Object>> entities,
                                                              EntityContext context) {
        try {
            // ids are not allowed to be nested
            // FIXME: why isn't contains(.) just checked during C3Type conversion, and not needed here?
            if(idFieldPreProcessor != null && !idFieldPreProcessor.getLeft().contains(".")){
                List<EntityHolder> entityHolders = new ArrayList<>();
                for(Map<Object, Object> entity : entities) {
                    entityHolders.add(processIdField(entity, context));
                }
                return CompletableFuture.completedFuture(entityHolders);
            }else{
                return CompletableFuture.failedFuture(new IllegalStateException("No id field found"));
            }
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    private EntityHolder processIdField(Map<Object, Object> entity, EntityContext context) {
        String fieldName = idFieldPreProcessor.getLeft();
        Object fieldValue = entity.get(fieldName);
        DecoratorLogic decoratorLogic = idFieldPreProcessor.getRight();
        C3Decorator decorator = decoratorLogic.getDecorator();
        UpsertFieldPreProcessor<C3Decorator, Object, Object> preProcessor = decoratorLogic.getProcessor();

        // we know this is the IdDecorator, so we can cast it
        String id = (String) preProcessor.process(structure, fieldName, decorator, fieldValue, context);

        // make sure the id is not null before appending to it
        if(id == null || id.isBlank()){
            throw new IllegalArgumentException("Id field cannot be null or blank");
        }

        // entity data gets id without tenant
        entity.put(fieldName, id);

        // if this is the id we add the special _id field for elasticsearch to use
        return new EntityHolder(id,
                                context.getParticipant().getTenantId(),
                                structure.getMultiTenancyType(),
                                entity);
    }
}
