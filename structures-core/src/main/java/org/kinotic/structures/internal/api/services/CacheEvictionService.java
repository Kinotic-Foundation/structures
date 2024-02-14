package org.kinotic.structures.internal.api.services;

import org.kinotic.structures.api.domain.Structure;

/**
 * Created by Navíd Mitchell 🤪on 6/25/23.
 */
public interface CacheEvictionService {

    /**
     * Evicts the cache for a given structure
     * @param structure to evict the cache for
     */
    void evictCachesFor(Structure structure);

}
