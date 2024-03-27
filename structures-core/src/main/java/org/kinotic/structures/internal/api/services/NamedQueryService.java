package org.kinotic.structures.internal.api.services;

import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.continuum.idl.api.schema.FunctionDefinition;
import org.kinotic.continuum.idl.api.schema.ServiceDefinition;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.NamedQueryServiceDefinition;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 3/20/24.
 */
public interface NamedQueryService {

    /**
     * Executes a named query. Named Queries are defined with a {@link ServiceDefinition} and stored in a {@link NamedQueryServiceDefinition}
     * @param namespace the namespace that this named query is defined in.
     * @param serviceName the name of the {@link ServiceDefinition} that defines the query
     * @param queryName the name of {@link FunctionDefinition} that defines the query
     * @param type the type of the entity
     * @param context the context for this operation
     * @param args any arguments to pass to the query
     * @return {@link CompletableFuture} with the result of the query
     */
    <T> CompletableFuture<T> namedQuery(String namespace, String serviceName, String queryName, Class<T> type, EntityContext context, Object ...args);

    /**
     * Executes a named query and returns a {@link Page} of results. Named Queries are defined with a {@link ServiceDefinition} and stored in a {@link NamedQueryServiceDefinition}
     * @param namespace the namespace that this named query is defined in.
     * @param serviceName the name of the {@link ServiceDefinition} that defines the query
     * @param queryName the name of {@link FunctionDefinition} that defines the query
     * @param pageable the page settings to be used
     * @param type the type of the entity
     * @param context the context for this operation
     * @param args any arguments to pass to the query
     * @return {@link CompletableFuture} with the result of the query
     */
    <T> CompletableFuture<Page<T>> namedQueryByPage(String namespace, String serviceName, String queryName, Pageable pageable, Class<T> type, EntityContext context, Object ...args);


}
