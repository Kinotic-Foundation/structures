package org.kinotic.structures.internal.endpoints.graphql;

import graphql.ExecutionResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Function is responsible for executing a predefined GQL operation
 * Created by Navíd Mitchell 🤪 on 6/11/24.
 */
public interface GqlOperationExecutionFunction extends Function<GqlOperationArguments, CompletableFuture<ExecutionResult>> {
}
