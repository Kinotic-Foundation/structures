package org.kinotic.authz.api.services;

import io.vertx.core.Future;
import org.kinotic.authz.api.model.AuthzObject;
import org.kinotic.authz.api.model.AuthzRelation;
import org.kinotic.authz.api.model.AuthzType;
import org.kinotic.authz.api.model.RelationshipTuple;

import java.util.Collection;
import java.util.List;

/**
 * The relationships held in the platform's authorization store, and the checks the store answers from them
 * through the platform's authorization model.
 */
public interface RelationshipService {

    /**
     * Adds and removes relationships. Adding a relationship the store already holds, or removing one it does not,
     * leaves the store as it is, so a write made again after a failure changes nothing twice.
     *
     * @param writes  the relationships to add
     * @param deletes the relationships to remove
     * @return completes when every relationship is added and removed
     */
    Future<Void> write(Collection<RelationshipTuple> writes, Collection<RelationshipTuple> deletes);

    /**
     * Whether the user holds the relation on the object, directly or through the model's rules.
     *
     * @param user     the subject asked about
     * @param relation the relation, usually a permission such as {@link AuthzRelation#CAN_EDIT}
     * @param object   the resource asked about
     * @return true when the relation is held
     */
    Future<Boolean> check(AuthzObject user, AuthzRelation relation, AuthzObject object);

    /**
     * The objects of the type on which the user holds the relation, directly or through the model's rules.
     *
     * @param user     the subject asked about
     * @param relation the relation, usually a permission such as {@link AuthzRelation#CAN_REGISTER_SERVICES}
     * @param type     the type of the objects listed
     * @return the objects, in no particular order
     */
    Future<List<AuthzObject>> listObjects(AuthzObject user, AuthzRelation relation, AuthzType type);

}
