package org.kinotic.authz.internal.api.services;

import dev.openfga.sdk.api.model.CheckRequest;
import dev.openfga.sdk.api.model.CheckRequestTupleKey;
import dev.openfga.sdk.api.model.CheckResponse;
import dev.openfga.sdk.api.model.TupleKey;
import dev.openfga.sdk.api.model.TupleKeyWithoutCondition;
import dev.openfga.sdk.errors.ApiException;
import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.kinotic.authz.api.model.AuthzObject;
import org.kinotic.authz.api.model.AuthzRelation;
import org.kinotic.authz.api.model.RelationshipTuple;
import org.kinotic.authz.api.services.RelationshipService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class DefaultRelationshipService implements RelationshipService {

    private static final int HTTP_CONFLICT = 409;

    private final OpenFgaService fga;
    private final PlatformAuthzStore store;

    @Override
    public Future<Void> write(Collection<RelationshipTuple> writes, Collection<RelationshipTuple> deletes) {
        List<TupleKey> writeKeys = new ArrayList<>();
        for (RelationshipTuple tuple : writes) {
            writeKeys.add(new TupleKey().user(tuple.user().toString())
                                        .relation(tuple.relation().wireName())
                                        ._object(tuple.object().toString()));
        }
        List<TupleKeyWithoutCondition> deleteKeys = new ArrayList<>();
        for (RelationshipTuple tuple : deletes) {
            deleteKeys.add(new TupleKeyWithoutCondition().user(tuple.user().toString())
                                                         .relation(tuple.relation().wireName())
                                                         ._object(tuple.object().toString()));
        }
        return writeKeys.isEmpty() && deleteKeys.isEmpty()
                ? Future.succeededFuture()
                : converging(() -> store.storeId().compose(id -> fga.write(id, writeKeys, deleteKeys)));
    }

    @Override
    public Future<Boolean> check(AuthzObject user, AuthzRelation relation, AuthzObject object) {
        CheckRequestTupleKey key = new CheckRequestTupleKey().user(user.toString())
                                                             .relation(relation.wireName())
                                                             ._object(object.toString());
        return Future.all(store.storeId(), store.modelId())
                     .compose(ids -> fga.check(ids.resultAt(0), new CheckRequest().authorizationModelId(ids.resultAt(1)).tupleKey(key)))
                     .map(CheckResponse::getAllowed);
    }

    // The engine refuses a write as a conflict when another writer changed the same tuples in the same moment;
    // the writes are idempotent, so one more attempt brings them to the state both wanted, and a second conflict
    // is reported
    private static Future<Void> converging(Supplier<Future<Void>> attempt) {
        return attempt.get().recover(e -> e instanceof ApiException api && api.getStatusCode() == HTTP_CONFLICT
                ? attempt.get()
                : Future.failedFuture(e));
    }
}
