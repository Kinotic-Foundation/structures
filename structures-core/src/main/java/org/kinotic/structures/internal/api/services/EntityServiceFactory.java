package org.kinotic.structures.internal.api.services;

import org.kinotic.structures.api.domain.Structure;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 5/10/23.
 */
public interface EntityServiceFactory {

    CompletableFuture<EntityService> createEntityService(Structure structure);

}
