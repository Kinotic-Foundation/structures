package org.kinotic.authz.internal.api.services;

import dev.openfga.sdk.api.model.CheckRequest;
import dev.openfga.sdk.api.model.CheckRequestTupleKey;
import dev.openfga.sdk.api.model.CheckResponse;
import dev.openfga.sdk.api.model.ConsistencyPreference;
import dev.openfga.sdk.api.model.ContextualTupleKeys;
import dev.openfga.sdk.api.model.ListObjectsRequest;
import dev.openfga.sdk.api.model.ListObjectsResponse;
import dev.openfga.sdk.api.model.ReadRequestTupleKey;
import dev.openfga.sdk.api.model.Tuple;
import dev.openfga.sdk.api.model.TupleKey;
import dev.openfga.sdk.api.model.TupleKeyWithoutCondition;
import dev.openfga.sdk.errors.FgaApiValidationError;
import dev.openfga.sdk.errors.ApiException;
import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kinotic.authz.api.model.AuthzModel;
import org.kinotic.authz.api.model.Consistency;
import org.kinotic.authz.api.model.Grant;
import org.kinotic.authz.api.model.RelationshipTuple;
import org.kinotic.authz.api.model.Resource;
import org.kinotic.authz.api.model.Subject;
import org.kinotic.authz.api.model.SubjectType;
import org.kinotic.authz.api.services.RelationshipService;
import org.kinotic.idl.api.utils.AuthzUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultRelationshipService implements RelationshipService {

    private static final int HTTP_CONFLICT = 409;

    private final OpenFgaService fga;
    private final DefaultAuthzStoreService stores;

    @Override
    public Future<Void> write(String store, List<RelationshipTuple> writes, List<RelationshipTuple> deletes) {
        List<TupleKey> writeKeys = new ArrayList<>();
        for (RelationshipTuple tuple : writes) {
            writeKeys.add(new TupleKey().user(tuple.user()).relation(tuple.relation())._object(tuple.object()));
        }
        List<TupleKeyWithoutCondition> deleteKeys = new ArrayList<>();
        for (RelationshipTuple tuple : deletes) {
            deleteKeys.add(new TupleKeyWithoutCondition().user(tuple.user()).relation(tuple.relation())._object(tuple.object()));
        }
        return writeKeys.isEmpty() && deleteKeys.isEmpty()
                ? Future.succeededFuture()
                : stores.storeIdOf(store).compose(id -> fga.write(id, writeKeys, deleteKeys));
    }

    @Override
    public Future<List<RelationshipTuple>> read(String store, String object) {
        return read(store, new ReadRequestTupleKey()._object(object));
    }

    @Override
    public Future<List<RelationshipTuple>> readByUser(String store, String user, String objectType) {
        // the engine reads by user within one object type, named by the type alone
        return read(store, new ReadRequestTupleKey().user(user)._object(objectType + ":"));
    }

    @Override
    public Future<Boolean> holds(String store, RelationshipTuple relationship) {
        return read(store, new ReadRequestTupleKey().user(relationship.user())
                                                    .relation(relationship.relation())
                                                    ._object(relationship.object()))
                .map(tuples -> !tuples.isEmpty());
    }

    @Override
    public Future<Void> ensure(String store, List<RelationshipTuple> relationships) {
        return converging(() -> write(store, relationships, List.of()));
    }

    @Override
    public Future<Void> remove(String store, List<RelationshipTuple> relationships) {
        return converging(() -> write(store, List.of(), relationships));
    }

    // A write the engine refuses as a conflict was raced by another node bringing the same tuples to the same
    // state, as the provisioner and the reconciler do for a new application's roles, so it is made again; a second
    // conflict is reported
    private static Future<Void> converging(Supplier<Future<Void>> attempt) {
        return attempt.get().recover(e -> isConflict(e) ? attempt.get() : Future.failedFuture(e));
    }

    private static boolean isConflict(Throwable e) {
        return e instanceof ApiException api && api.getStatusCode() == HTTP_CONFLICT;
    }

    @Override
    public Future<Void> ensureRoles(String store, Map<String, Set<String>> roles) {
        return converging(() -> reconcileRoles(store, roles));
    }

    @Override
    public Future<String> ensureModelWithRoles(String store, AuthzModel model) {
        // A check that runs while a role's tuples are being written can cache the role's answer from before the
        // write, valid for the engine's cache lifetime; the roles are written while no version yet grants through
        // their new relations, so no check against the model can read them before they are in step
        return stores.ensureRoleRelations(store, model)
                     .compose(v -> ensureRoles(store, model.roles()))
                     .compose(v -> stores.ensureModel(store, model));
    }

    private Future<Void> reconcileRoles(String store, Map<String, Set<String>> roles) {
        List<Future<List<RelationshipTuple>>> reads = new ArrayList<>();
        List<String> ids = new ArrayList<>(roles.keySet());
        for (String roleId : ids) {
            reads.add(read(store, AuthzUtil.roleObject(roleId)));
        }
        return Future.all(reads).compose(held -> {
            List<RelationshipTuple> writes = new ArrayList<>();
            List<RelationshipTuple> deletes = new ArrayList<>();
            for (int i = 0; i < ids.size(); i++) {
                String role = AuthzUtil.roleObject(ids.get(i));
                Set<String> desired = roles.get(ids.get(i));
                Set<String> bundled = new HashSet<>();
                for (RelationshipTuple tuple : held.<List<RelationshipTuple>>resultAt(i)) {
                    // a role's permissions are the tuples it holds for everyone; a binding or anything else
                    // attached to the role object is not a permission and is left alone
                    if (AuthzUtil.EVERYONE.equals(tuple.user())) {
                        bundled.add(tuple.relation());
                        if (!desired.contains(tuple.relation())) {
                            deletes.add(tuple);
                        }
                    }
                }
                for (String permission : desired) {
                    if (!bundled.contains(permission)) {
                        writes.add(new RelationshipTuple(AuthzUtil.EVERYONE, permission, role));
                    }
                }
            }
            return write(store, writes, deletes);
        });
    }

    @Override
    public Future<String> bind(String store, String roleId, String member, String object) {
        String bindingId = UUID.randomUUID().toString();
        return write(store, binding(bindingId, roleId, member, object), List.of()).map(bindingId);
    }

    @Override
    public Future<Void> ensureBound(String store, String bindingId, String roleId, String member, String object) {
        return ensure(store, binding(bindingId, roleId, member, object));
    }

    // A binding is three relationships: its role, its member, and its attachment to the object. The role is typed by
    // its home and the binding by the object's type, so the engine refuses a role the object's type may not grant
    private static List<RelationshipTuple> binding(String bindingId, String roleId, String member, String object) {
        String binding = AuthzUtil.bindingObject(object, bindingId);
        return List.of(new RelationshipTuple(AuthzUtil.roleObject(roleId), AuthzUtil.ROLE_RELATION, binding),
                       new RelationshipTuple(member, AuthzUtil.MEMBER_RELATION, binding),
                       new RelationshipTuple(binding, AuthzUtil.ROLE_BINDING_RELATION, object));
    }

    @Override
    public Future<Void> unbind(String store, String bindingId, String object) {
        String binding = AuthzUtil.bindingObject(object, bindingId);
        return read(store, binding).compose(held -> {
            List<RelationshipTuple> tuples = new ArrayList<>(held);
            tuples.add(new RelationshipTuple(binding, AuthzUtil.ROLE_BINDING_RELATION, object));
            return remove(store, tuples);
        });
    }

    @Override
    public Future<Void> revoke(String store, String bindingId, String object) {
        String binding = AuthzUtil.bindingObject(object, bindingId);
        return holds(store, new RelationshipTuple(binding, AuthzUtil.ROLE_BINDING_RELATION, object)).compose(made -> {
            if (!made) {
                throw new IllegalArgumentException("No grant " + bindingId + " was made on " + object);
            }
            return unbind(store, bindingId, object);
        });
    }

    @Override
    public Future<List<Grant>> findGrants(String store, String object) {
        Resource resource = new Resource(AuthzUtil.typeOf(object), AuthzUtil.idOf(object));
        return read(store, object).compose(held -> {
            List<Future<Grant>> bindings = new ArrayList<>();
            for (RelationshipTuple tuple : held) {
                if (AuthzUtil.ROLE_BINDING_RELATION.equals(tuple.relation())) {
                    bindings.add(grantOf(store, tuple.user(), resource));
                }
            }
            return Future.all(bindings).map(all -> all.<Grant>list().stream().filter(grant -> grant != null).toList());
        });
    }

    // The binding's role and member; null for a binding made to a userset no grant names
    private Future<Grant> grantOf(String store, String binding, Resource resource) {
        return read(store, binding).map(held -> {
            String roleId = null;
            Subject subject = null;
            for (RelationshipTuple tuple : held) {
                if (AuthzUtil.ROLE_RELATION.equals(tuple.relation())) {
                    roleId = AuthzUtil.idOf(tuple.user());
                } else if (AuthzUtil.MEMBER_RELATION.equals(tuple.relation())) {
                    subject = subjectOf(tuple.user());
                }
            }
            return roleId == null || subject == null ? null : new Grant(AuthzUtil.idOf(binding), roleId, subject, resource);
        });
    }

    private static Subject subjectOf(String user) {
        Subject ret = null;
        String type = AuthzUtil.typeOf(user);
        if (AuthzUtil.USER_TYPE.equals(type)) {
            ret = new Subject(SubjectType.USER, AuthzUtil.idOf(user));
        } else if (AuthzUtil.GROUP_TYPE.equals(type) && user.endsWith("#" + AuthzUtil.MEMBER_RELATION)) {
            ret = new Subject(SubjectType.GROUP, AuthzUtil.idOf(user));
        }
        return ret;
    }

    @Override
    public Future<Boolean> explains(String store, String modelId, Grant grant, Subject subject, String permission) {
        Future<Boolean> bundled = holds(store, new RelationshipTuple(AuthzUtil.EVERYONE, permission,
                                                                     AuthzUtil.roleObject(grant.roleId())));
        Future<Boolean> member;
        if (grant.subject().equals(subject)) {
            member = Future.succeededFuture(true);
        } else if (grant.subject().type() == SubjectType.GROUP && subject.type() == SubjectType.USER) {
            RelationshipTuple membership = new RelationshipTuple(AuthzUtil.object(AuthzUtil.USER_TYPE, subject.id()),
                                                                 AuthzUtil.MEMBER_RELATION,
                                                                 AuthzUtil.object(AuthzUtil.GROUP_TYPE, grant.subject().id()));
            // an admin asks after changing a group, so the answer must not predate the change
            member = check(store, modelId, membership, Consistency.HIGHER_CONSISTENCY);
        } else {
            member = Future.succeededFuture(false);
        }
        return Future.all(bundled, member).map(both -> both.<Boolean>resultAt(0) && both.<Boolean>resultAt(1));
    }

    @Override
    public Future<Boolean> check(String store, String modelId, RelationshipTuple relationship, Consistency consistency, List<RelationshipTuple> context) {
        CheckRequestTupleKey key = new CheckRequestTupleKey().user(relationship.user())
                                                             .relation(relationship.relation())
                                                             ._object(relationship.object());
        List<TupleKey> contextual = new ArrayList<>();
        for (RelationshipTuple tuple : context) {
            contextual.add(new TupleKey().user(tuple.user()).relation(tuple.relation())._object(tuple.object()));
        }
        return check(store, modelId, key, contextual, consistency)
                .recover(e -> onNewestModel(store, modelId, e, newest -> check(store, newest, key, contextual, consistency),
                                            false, relationship));
    }

    // The engine refuses a request for what it names: a version of the store deleted and re-created under its name
    // since the caller read it, or a relation the version lacked when it was read and the newest has; the newest
    // version is read and answers once
    private <T> Future<T> onNewestModel(String store,
                                        String modelId,
                                        Throwable refusal,
                                        Function<String, Future<T>> request,
                                        T heldByNone,
                                        Object asked) {
        Future<T> ret;
        if (refusal instanceof FgaApiValidationError) {
            ret = stores.refreshModelId(store)
                        .compose(newest -> newest.equals(modelId) ? Future.failedFuture(refusal) : request.apply(newest))
                        // a relation or type the newest version lacks is held by no one: a permission of a type
                        // another store models, or of a service whose model the reconciler has yet to write
                        .recover(refused -> {
                            Future<T> held;
                            if (refused instanceof FgaApiValidationError) {
                                log.warn("The model of store {} cannot answer {}: {}", store, asked, refused.getMessage());
                                held = Future.succeededFuture(heldByNone);
                            } else {
                                held = Future.failedFuture(refused);
                            }
                            return held;
                        });
        } else {
            ret = Future.failedFuture(refusal);
        }
        return ret;
    }

    private Future<Boolean> check(String store, String modelId, CheckRequestTupleKey key, List<TupleKey> contextual, Consistency consistency) {
        CheckRequest request = new CheckRequest().authorizationModelId(modelId).consistency(preference(consistency)).tupleKey(key);
        if (!contextual.isEmpty()) {
            request.contextualTuples(new ContextualTupleKeys().tupleKeys(contextual));
        }
        return stores.storeIdOf(store).compose(id -> fga.check(id, request)).map(CheckResponse::getAllowed);
    }

    @Override
    public Future<List<String>> listObjects(String store, String modelId, String user, String relation, String type, Consistency consistency) {
        return listObjects(store, modelId, user, relation, type, preference(consistency))
                .recover(e -> onNewestModel(store, modelId, e,
                                            newest -> listObjects(store, newest, user, relation, type, preference(consistency)),
                                            List.of(), user + " " + relation + " " + type));
    }

    private Future<List<String>> listObjects(String store, String modelId, String user, String relation, String type, ConsistencyPreference consistency) {
        ListObjectsRequest request = new ListObjectsRequest()
                .authorizationModelId(modelId)
                .consistency(consistency)
                .user(user)
                .relation(relation)
                .type(type);
        return stores.storeIdOf(store).compose(id -> fga.listObjects(id, request)).map(ListObjectsResponse::getObjects);
    }

    private static ConsistencyPreference preference(Consistency consistency) {
        return consistency == Consistency.HIGHER_CONSISTENCY
                ? ConsistencyPreference.HIGHER_CONSISTENCY
                : ConsistencyPreference.MINIMIZE_LATENCY;
    }

    private Future<List<RelationshipTuple>> read(String store, ReadRequestTupleKey key) {
        return stores.storeIdOf(store).compose(id -> fga.read(id, key)).map(tuples -> {
            List<RelationshipTuple> ret = new ArrayList<>();
            for (Tuple tuple : tuples) {
                ret.add(new RelationshipTuple(tuple.getKey().getUser(), tuple.getKey().getRelation(), tuple.getKey().getObject()));
            }
            return ret;
        });
    }
}
