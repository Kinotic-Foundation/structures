package org.kinotic.structures.internal.api.services;

import org.springframework.graphql.ExecutionGraphQlService;
import reactor.core.publisher.Mono;

/**
 * Supports the creation of {@link ExecutionGraphQlService} instances for a given namespace.
 * This is used to support multiple namespaces in a single application.
 *
 * Created by Navíd Mitchell 🤪 on 4/16/23.
 */
public interface ExecutionGraphQlServiceProvider {

    /**
     * Provides a {@link ExecutionGraphQlService} for a given namespace
     * @param namespace the structure namespace to provide a service for
     * @return the {@link ExecutionGraphQlService}
     */
    Mono<ExecutionGraphQlService> getService(String namespace);

    /**
     * Evicts the cache for a given namespace
     * @param namespace the namespace to evict
     */
    void evictCacheFor(String namespace);

}
