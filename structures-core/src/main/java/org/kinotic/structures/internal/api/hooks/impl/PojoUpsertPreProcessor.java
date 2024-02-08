package org.kinotic.structures.internal.api.hooks.impl;

import org.apache.commons.lang3.NotImplementedException;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.internal.api.hooks.UpsertPreProcessor;
import org.kinotic.structures.internal.api.services.EntityHolder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 6/7/23.
 */
public class PojoUpsertPreProcessor implements UpsertPreProcessor<Object, List<Object>> {

    @Override
    public CompletableFuture<EntityHolder> process(Object entity, EntityContext context) {
        return null;
    }

    @Override
    public CompletableFuture<List<EntityHolder>> processArray(List<Object> entities, EntityContext context) {
        throw new NotImplementedException("Pojo upsert is not implemented yet");
    }
}
