package org.kinotic.authz.internal.api.services;

import dev.openfga.sdk.api.model.AuthorizationModel;
import dev.openfga.sdk.api.model.Store;
import dev.openfga.sdk.api.model.WriteAuthorizationModelResponse;
import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kinotic.authz.api.config.KinoticAuthzProperties;
import org.kinotic.core.api.utils.KinoticUtil;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * The platform's store in the engine and the version of {@link PlatformAuthzModel} it runs. The store is created
 * before the servers start and is found by its name; the model is written when the store's newest version
 * differs from it, so every node starting with the same model reuses one version.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PlatformAuthzStore implements SmartInitializingSingleton {

    static final String STORE_NAME = "kinotic-platform";
    private static final int STORE_PAGE_SIZE = 100;
    // the SDK's model classes bind by their own wire names, so the application's mapper customizations stay
    // out of the conversion
    private static final JsonMapper MAPPER = JsonMapper.builder().build();
    // an empty object under these keys is the meaning itself: `this` marks a directly assignable relation and
    // `wildcard` marks a type reference to every object of the type
    private static final Set<String> MEANINGFUL_EMPTY_OBJECTS = Set.of("this", "wildcard");
    private static final JsonNode CANONICAL_DEFINITION = canonical(MAPPER.valueToTree(PlatformAuthzModel.DEFINITION));

    private final OpenFgaService fga;
    private final KinoticAuthzProperties properties;

    // Each kept as a stage with no context of its own, so every caller bridges it onto the caller's context; a
    // failed lookup is replaced by the next caller's
    private final AtomicReference<CompletableFuture<String>> storeId = new AtomicReference<>();
    private final AtomicReference<CompletableFuture<String>> modelId = new AtomicReference<>();

    @Override
    public void afterSingletonsInstantiated() {
        modelId().onSuccess(id -> log.info("Authorization store {} runs the platform model {}", STORE_NAME, id))
                 .onFailure(e -> log.warn("The platform authorization model could not be ensured in {} at {}: {}",
                                          STORE_NAME, properties.getAuthz().getApiUrl(), e.getMessage()));
    }

    /**
     * The engine's id of the platform store.
     *
     * @return the id; fails when the engine has no store of the platform's name or cannot be reached
     */
    Future<String> storeId() {
        return memoized(storeId, () -> findStore().map(id -> {
            if (id == null) {
                throw new IllegalStateException("No authorization store is named '" + STORE_NAME
                                                        + "'; the platform store is created before the servers start");
            }
            return id;
        }));
    }

    /**
     * The id of the model version the platform store runs for {@link PlatformAuthzModel}, written to the store
     * when its newest version is a different model.
     *
     * @return the version's id, which every check names
     */
    Future<String> modelId() {
        return memoized(modelId, () -> storeId().compose(store -> fga.readAuthorizationModels(store, 1, null).compose(response -> {
            // versions are listed newest first, so a page of one is the current version
            List<AuthorizationModel> models = response.getAuthorizationModels();
            AuthorizationModel latest = models.isEmpty() ? null : models.getFirst();
            Future<String> ret;
            if (latest != null && canonical(definitionOf(latest)).equals(CANONICAL_DEFINITION)) {
                ret = Future.succeededFuture(latest.getId());
            } else {
                // nodes starting together may each write this version; versions are immutable, so the copies
                // are equal and every later comparison matches the newest
                ret = fga.writeAuthorizationModel(store, PlatformAuthzModel.DEFINITION)
                         .map(WriteAuthorizationModelResponse::getAuthorizationModelId)
                         .onSuccess(id -> log.info("Wrote the platform authorization model {} to store {}", id, store));
            }
            return ret;
        })));
    }

    private static Future<String> memoized(AtomicReference<CompletableFuture<String>> kept, Supplier<Future<String>> loader) {
        CompletableFuture<String> stage = kept.get();
        if (stage == null || stage.isCompletedExceptionally()) {
            CompletableFuture<String> loading = new CompletableFuture<>();
            if (kept.compareAndSet(stage, loading)) {
                loader.get().onComplete(loading::complete, loading::completeExceptionally);
                stage = loading;
            } else {
                stage = kept.get();
            }
        }
        return KinoticUtil.toFuture(stage);
    }

    // Store names are not unique, so the oldest store of the name is the platform's; the engine's ids are
    // time-ordered, so the smallest is the oldest
    private Future<String> findStore() {
        return findStores(null, new ArrayList<>()).map(ids -> ids.stream().min(String::compareTo).orElse(null));
    }

    private Future<List<String>> findStores(String continuationToken, List<String> collected) {
        return fga.listStores(STORE_PAGE_SIZE, continuationToken, STORE_NAME).compose(page -> {
            for (Store store : page.getStores()) {
                if (STORE_NAME.equals(store.getName())) {
                    collected.add(store.getId());
                }
            }
            String next = page.getContinuationToken();
            return next == null || next.isEmpty()
                    ? Future.succeededFuture(collected)
                    : findStores(next, collected);
        });
    }

    private static JsonNode definitionOf(AuthorizationModel model) {
        ObjectNode ret = MAPPER.valueToTree(model);
        ret.remove("id");
        return ret;
    }

    // The definition with its keys sorted and every default-valued field left out, which is the form the engine
    // returns a model in: a null, an empty string, an empty array and an empty object are dropped, except an
    // empty object under `this` or `wildcard`
    private static JsonNode canonical(JsonNode node) {
        JsonNode ret;
        if (node.isObject()) {
            ObjectNode object = JsonNodeFactory.instance.objectNode();
            for (String name : new TreeSet<>(node.propertyNames())) {
                JsonNode value = canonical(node.get(name));
                if (!isDefault(name, value)) {
                    object.set(name, value);
                }
            }
            ret = object;
        } else if (node.isArray()) {
            ArrayNode array = JsonNodeFactory.instance.arrayNode();
            for (JsonNode child : node) {
                array.add(canonical(child));
            }
            ret = array;
        } else {
            ret = node;
        }
        return ret;
    }

    private static boolean isDefault(String name, JsonNode value) {
        return value.isNull()
                || value.isMissingNode()
                || (value.isString() && value.asString().isEmpty())
                || (value.isArray() && value.isEmpty())
                || (value.isObject() && value.isEmpty() && !MEANINGFUL_EMPTY_OBJECTS.contains(name));
    }
}
