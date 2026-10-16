package org.kinotic.domain.api.services.security;

import io.vertx.core.Future;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.domain.api.model.security.Member;
import org.kinotic.domain.api.model.security.identity.Membership;

import java.util.List;

/**
 * The {@link Membership}s of accounts in organizations and in the platform. Checks no participant: the
 * callers, the login routes and the member management services, hold the scope they act in.
 */
public interface MembershipService {

    /**
     * Makes account {@code identityId} a member of organization {@code organizationId}, or of the platform
     * when it is {@code null}, enabled. An account already a member keeps its membership as it is.
     *
     * @return the membership, readable at once
     */
    Future<Membership> addMember(String identityId, String organizationId);

    /**
     * The membership of account {@code identityId} in organization {@code organizationId}, or in the
     * platform when it is {@code null}; {@code null} when the account is not a member.
     */
    Future<Membership> findMembership(String identityId, String organizationId);

    /** Every membership of account {@code identityId}, disabled ones included, oldest first. */
    Future<List<Membership>> findMemberships(String identityId);

    /**
     * The members of organization {@code organizationId}, or of the platform when it is {@code null},
     * each as the account joined with its membership.
     */
    Future<Page<Member>> findMembers(String organizationId, Pageable pageable);

    /**
     * The members of the scope whose email or display name matches {@code searchText}; blank text lists
     * them all as {@link #findMembers} does.
     */
    Future<Page<Member>> searchMembers(String searchText, String organizationId, Pageable pageable);

    /**
     * Enables or disables account {@code identityId}'s membership of the scope. A disabled membership
     * refuses the scope's logins while the account and its other memberships are untouched.
     *
     * @return fails with {@link IllegalArgumentException} when the account is not a member
     */
    Future<Void> setMemberEnabled(String identityId, String organizationId, boolean enabled);

    /**
     * Removes account {@code identityId}'s membership of the scope, and the delegates the account
     * authorized in it. The account itself and its other memberships remain.
     *
     * @return fails with {@link IllegalArgumentException} when the account is not a member
     */
    Future<Void> removeMember(String identityId, String organizationId);
}
