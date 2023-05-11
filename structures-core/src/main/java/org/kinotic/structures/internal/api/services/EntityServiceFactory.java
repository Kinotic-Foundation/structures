package org.kinotic.structures.internal.api.services;

import org.kinotic.structures.api.domain.Structure;

import java.util.concurrent.CompletableFuture;

/**
 * Factory Service for creating {@link EntityService} instances
 * Created by Navíd Mitchell 🤪 on 5/10/23.
 */
public interface EntityServiceFactory {

    /**
     * Creates a new {@link EntityService} for the given {@link Structure}
     * @param structure to create the {@link EntityService} for
     * @return a new {@link EntityService} for the given {@link Structure}
     */
    CompletableFuture<EntityService> createEntityService(Structure structure);

}
