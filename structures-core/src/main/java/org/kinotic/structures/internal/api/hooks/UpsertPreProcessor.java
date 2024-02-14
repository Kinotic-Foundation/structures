package org.kinotic.structures.internal.api.hooks;

import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.internal.api.services.EntityHolder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Performs logic on Entity Objects before they are upserted.
 *
 * Created by Navíd Mitchell 🤪 on 5/5/23.
 */
public interface UpsertPreProcessor<T, ARRAY_TYPE> {

    /**
     * Processes a single entity before it is upserted.
     * This applies all decorator logic to fields that have decorators.
     * @param entity to process
     * @param context the context for this operation
     * @return {@link CompletableFuture} emitting the processed entity
     */
    CompletableFuture<EntityHolder> process(T entity, EntityContext context);

    /**
     * Processes an array of entities before they are upserted.
     * This applies all decorator logic to fields that have decorators.
     * @param entities to process
     * @param context the context for this operation
     * @return {@link CompletableFuture} emitting the processed entities
     */
    CompletableFuture<List<EntityHolder>> processArray(ARRAY_TYPE entities, EntityContext context);

}
