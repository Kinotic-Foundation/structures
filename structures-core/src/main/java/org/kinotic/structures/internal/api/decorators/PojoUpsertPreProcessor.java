package org.kinotic.structures.internal.api.decorators;

import org.kinotic.structures.api.domain.EntityContext;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 6/7/23.
 */
public class PojoUpsertPreProcessor implements UpsertPreProcessor<Object>{

    @Override
    public CompletableFuture<EntityHolder<Object>> process(Object entity, EntityContext context) {
        return null;
    }
}
