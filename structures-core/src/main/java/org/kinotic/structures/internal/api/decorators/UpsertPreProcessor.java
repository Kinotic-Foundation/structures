package org.kinotic.structures.internal.api.decorators;

import org.kinotic.structures.api.domain.EntityContext;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Performs logic on Entity Objects before they are upserted.
 *
 * Created by Navíd Mitchell 🤪 on 5/5/23.
 */
public interface UpsertPreProcessor<T, ARRAY_TYPE> {

    CompletableFuture<EntityHolder<T>> process(T entity, EntityContext context);

    CompletableFuture<List<EntityHolder<T>>> processArray(ARRAY_TYPE entities, EntityContext context);

}

