package org.kinotic.structures.internal.api.services;

import graphql.GraphQL;
import org.springframework.graphql.ExecutionGraphQlService;

import java.util.concurrent.CompletableFuture;

/**
 * Supports the creation of {@link ExecutionGraphQlService} instances for a given namespace.
 * This is used to support multiple namespaces in a single application.
 *
 * Created by Navíd Mitchell 🤪on 4/16/23.
 */
public interface GraphQlProviderService {

    /**
     * Provides a {@link GraphQL} for a given namespace
     * @param namespace the structure namespace to provide a service for
     * @return the {@link GraphQL}
     */
    CompletableFuture<GraphQL> getGraphQL(String namespace);

    /**
     * Evicts the cache for a given namespace
     * @param namespace the namespace to evict
     */
    void evictCacheFor(String namespace);

}
