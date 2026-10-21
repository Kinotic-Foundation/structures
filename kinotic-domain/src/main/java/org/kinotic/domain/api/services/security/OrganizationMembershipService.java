package org.kinotic.domain.api.services.security;

import io.vertx.core.Future;
import org.kinotic.core.api.security.Participant;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.domain.api.model.security.Member;
import org.kinotic.domain.api.model.security.identity.OrganizationMembership;
import org.kinotic.domain.api.model.security.identity.OrganizationUserParticipantIdentity;

import java.util.List;

/**
 * The {@link OrganizationMembership}s of organization users. Checks no participant: the callers, the login routes
 * and the member management services, hold the organization they act in.
 */
public interface OrganizationMembershipService {

    /**
     * Makes organization user {@code organizationUserId} a member of organization {@code organizationId}, enabled.
     * A user already a member keeps its membership as it is.
     *
     * @return the membership, readable at once
     */
    Future<OrganizationMembership> addMembership(String organizationUserId, String organizationId);

    /**
     * The membership of organization user {@code organizationUserId} in organization {@code organizationId}, or
     * {@code null} when the user is not a member.
     */
    Future<OrganizationMembership> findMembership(String organizationUserId, String organizationId);

    /** Every membership of organization user {@code organizationUserId}, disabled ones included, oldest first. */
    Future<List<OrganizationMembership>> findMemberships(String organizationUserId);

    /** The members of organization {@code organizationId}, each as the organization user joined with its membership. */
    Future<Page<Member>> findMembers(String organizationId, Pageable pageable);

    /**
     * The members of the organization whose email or display name matches {@code searchText}; blank text lists
     * them all as {@link #findMembers} does.
     */
    Future<Page<Member>> searchMembers(String searchText, String organizationId, Pageable pageable);

    /**
     * The participant {@code user} signs in to organization {@code organizationId} as, through its membership of it,
     * or {@code null} when it holds none. Every login, invitation and token that admits an organization user to an
     * organization resolves it here.
     *
     * @return fails with {@link org.kinotic.domain.api.exceptions.AccountDisabledException} when the user or its
     *         membership is disabled
     */
    Future<Participant> resolveParticipant(OrganizationUserParticipantIdentity user, String organizationId);

    /**
     * Enables or disables organization user {@code organizationUserId}'s membership of the organization. A disabled
     * membership refuses the organization's logins while the user and its other memberships are untouched.
     *
     * @return fails with {@link IllegalArgumentException} when the user is not a member
     */
    Future<Void> setMembershipEnabled(String organizationUserId, String organizationId, boolean enabled);

    /**
     * Removes organization user {@code organizationUserId}'s membership of the organization, and the delegates the
     * user authorized in it. The user itself and its other memberships remain.
     *
     * @return fails with {@link IllegalArgumentException} when the user is not a member
     */
    Future<Void> removeMembership(String organizationUserId, String organizationId);
}
