package org.kinotic.structures.internal.api.services.impl;

import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import org.kinotic.structures.internal.api.services.EntityService;
import org.kinotic.structures.internal.api.services.EntityServiceFactory;
import org.kinotic.structures.internal.api.services.StructureDAO;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Created by Navíd Mitchell 🤪on 6/25/23.
 */
@Component
public class EntityServiceCacheLoader implements AsyncCacheLoader<String, EntityService> {

    private final StructureDAO structureDAO;
    private final EntityServiceFactory entityServiceFactory;

    public EntityServiceCacheLoader(StructureDAO structureDAO, EntityServiceFactory entityServiceFactory) {
        this.structureDAO = structureDAO;
        this.entityServiceFactory = entityServiceFactory;
    }

    @Override
    public CompletableFuture<EntityService> asyncLoad(String key, Executor executor) throws Exception {
        return structureDAO.findById(key)
                           .thenComposeAsync(entityServiceFactory::createEntityService,
                                             executor);
    }

}
