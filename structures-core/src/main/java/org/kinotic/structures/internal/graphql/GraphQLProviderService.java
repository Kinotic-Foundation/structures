package org.kinotic.structures.internal.graphql;

import graphql.GraphQL;
import org.kinotic.structures.api.domain.Structure;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪on 6/25/23.
 */
public interface GraphQLProviderService {

    /**
     * Provides a {@link GraphQL} for a given namespace
     * @param namespace the structure namespace to provide a service for
     * @return the {@link GraphQL}
     */
    CompletableFuture<GraphQL> getOrCreateGraphQL(String namespace);

    /**
     * Evicts the cache for a given structure
     * @param structure to evict the cache for
     */
    void evictCachesFor(Structure structure);

}
