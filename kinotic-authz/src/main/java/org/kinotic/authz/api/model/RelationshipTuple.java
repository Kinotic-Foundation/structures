package org.kinotic.authz.api.model;

/**
 * One relationship in the authorization store: {@code user} holds {@code relation} on {@code object}.
 *
 * @param user     who holds the relation
 * @param relation the relation held
 * @param object   what it is held on
 */
public record RelationshipTuple(AuthzObject user, AuthzRelation relation, AuthzObject object) {
}
