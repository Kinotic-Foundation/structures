package org.kinotic.structures.internal.api.decorators;

import java.util.concurrent.CompletableFuture;

/**
 * Performs logic on raw Entity Objects before they are upserted.
 *
 * Created by Navíd Mitchell 🤪 on 5/5/23.
 */
public interface UpsertEntityPreProcessor {

    CompletableFuture<RawEntity> process(byte[] bytes);

}

