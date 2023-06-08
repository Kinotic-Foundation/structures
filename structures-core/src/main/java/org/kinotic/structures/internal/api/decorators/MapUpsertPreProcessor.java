package org.kinotic.structures.internal.api.decorators;

import org.kinotic.structures.api.domain.EntityContext;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 6/7/23.
 */
public class MapUpsertPreProcessor implements UpsertPreProcessor<Map<?,?>>{

    @Override
    public CompletableFuture<EntityHolder<Map<?, ?>>> process(Map<?, ?> entity, EntityContext context) {



        return null;
    }
}
