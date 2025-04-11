package org.kinotic.structures.internal.api.services;

import org.kinotic.structures.api.domain.NamedQueriesDefinition;
import org.kinotic.structures.api.domain.Structure;

/**
 * Created By Navíd Mitchell 🤪on 2/12/25
 */
public interface CacheEvictionService {

    /**
     * Evicts the cache for a given structure
     * @param structure to evict the cache for
     */
    void evictCachesFor(Structure structure);

    /**
     * Evicts the cache for a given {@link NamedQueriesDefinition}
     * @param namedQueriesDefinition to evict the cache for
     */
    void evictCachesFor(NamedQueriesDefinition namedQueriesDefinition);

}
