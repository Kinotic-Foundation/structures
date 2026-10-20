package org.kinotic.authz.internal.api.services;

import dev.openfga.sdk.api.OpenFgaApi;
import dev.openfga.sdk.api.client.ApiResponse;
import dev.openfga.sdk.api.configuration.ApiToken;
import dev.openfga.sdk.api.configuration.Configuration;
import dev.openfga.sdk.api.configuration.Credentials;
import dev.openfga.sdk.api.model.CheckRequest;
import dev.openfga.sdk.api.model.CheckResponse;
import dev.openfga.sdk.api.model.ListStoresResponse;
import dev.openfga.sdk.api.model.ReadAuthorizationModelsResponse;
import dev.openfga.sdk.api.model.TupleKey;
import dev.openfga.sdk.api.model.TupleKeyWithoutCondition;
import dev.openfga.sdk.api.model.WriteAuthorizationModelRequest;
import dev.openfga.sdk.api.model.WriteAuthorizationModelResponse;
import dev.openfga.sdk.api.model.WriteRequest;
import dev.openfga.sdk.api.model.WriteRequestDeletes;
import dev.openfga.sdk.api.model.WriteRequestWrites;
import dev.openfga.sdk.errors.FgaInvalidParameterException;
import io.vertx.core.Future;
import org.kinotic.authz.api.config.AuthzProperties;
import org.kinotic.authz.api.config.KinoticAuthzProperties;
import org.kinotic.core.api.utils.KinoticUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * The OpenFGA client as the authorization services call it: the engine named by {@link AuthzProperties}, and
 * each call yielding its response's data as a Vert.x future on the caller's context, a failure to build or
 * send the request observed on the future like any other.
 */
@Component
public class OpenFgaService {

    // the most tuples one Write request may carry
    static final int WRITE_BATCH_SIZE = 100;

    private final OpenFgaApi api;

    public OpenFgaService(KinoticAuthzProperties properties) {
        AuthzProperties authz = properties.getAuthz();
        Configuration configuration = new Configuration().apiUrl(authz.getApiUrl());
        if (StringUtils.hasText(authz.getApiToken())) {
            configuration.credentials(new Credentials(new ApiToken(authz.getApiToken())));
        }
        try {
            this.api = new OpenFgaApi(configuration);
        } catch (FgaInvalidParameterException e) {
            throw new IllegalArgumentException("Invalid OpenFGA client configuration", e);
        }
    }

    public Future<ListStoresResponse> listStores(int pageSize, String continuationToken, String name) {
        return call(() -> api.listStores(pageSize, continuationToken, name));
    }

    public Future<ReadAuthorizationModelsResponse> readAuthorizationModels(String storeId, int pageSize, String continuationToken) {
        return call(() -> api.readAuthorizationModels(storeId, pageSize, continuationToken));
    }

    public Future<WriteAuthorizationModelResponse> writeAuthorizationModel(String storeId, WriteAuthorizationModelRequest request) {
        return call(() -> api.writeAuthorizationModel(storeId, request));
    }

    /**
     * Adds and removes relationships, in as many requests as the engine's limit per request takes, the writes
     * before the deletes. Adding a relationship the store already holds, or removing one it does not, leaves the
     * store as it is.
     */
    public Future<Void> write(String storeId, List<TupleKey> writes, List<TupleKeyWithoutCondition> deletes) {
        Future<Void> ret = Future.succeededFuture();
        for (int from = 0; from < writes.size(); from += WRITE_BATCH_SIZE) {
            WriteRequest request = new WriteRequest().writes(new WriteRequestWrites()
                                                                     .tupleKeys(writes.subList(from, Math.min(writes.size(), from + WRITE_BATCH_SIZE)))
                                                                     .onDuplicate(WriteRequestWrites.OnDuplicateEnum.IGNORE));
            ret = ret.compose(v -> call(() -> api.write(storeId, request)).mapEmpty());
        }
        for (int from = 0; from < deletes.size(); from += WRITE_BATCH_SIZE) {
            WriteRequest request = new WriteRequest().deletes(new WriteRequestDeletes()
                                                                      .tupleKeys(deletes.subList(from, Math.min(deletes.size(), from + WRITE_BATCH_SIZE)))
                                                                      .onMissing(WriteRequestDeletes.OnMissingEnum.IGNORE));
            ret = ret.compose(v -> call(() -> api.write(storeId, request)).mapEmpty());
        }
        return ret;
    }

    public Future<CheckResponse> check(String storeId, CheckRequest request) {
        return call(() -> api.check(storeId, request));
    }

    private static <T> Future<T> call(Callable<CompletableFuture<ApiResponse<T>>> call) {
        Future<T> ret;
        try {
            ret = KinoticUtil.toFuture(call.call()).map(ApiResponse::getData);
        } catch (Exception e) {
            ret = Future.failedFuture(e);
        }
        return ret;
    }

}
