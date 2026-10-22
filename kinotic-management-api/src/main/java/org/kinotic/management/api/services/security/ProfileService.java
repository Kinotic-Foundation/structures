package org.kinotic.management.api.services.security;

import io.vertx.core.Future;
import org.kinotic.core.api.annotations.Publish;
import org.kinotic.core.api.security.Participant;
import org.kinotic.domain.api.model.Organization;
import org.kinotic.domain.api.model.security.LoginProviderSummary;
import org.kinotic.domain.api.model.security.identity.UserParticipantIdentity;
import org.kinotic.idl.api.annotations.AuthzUnchecked;
import org.kinotic.management.api.model.SignInMethod;

import java.util.List;

/**
 * The signed-in user's own details, read and edited from the web app's profile settings: a system user's
 * from the system console, an organization user's from the portal. Every operation addresses the calling user — no parameter names whose profile is
 * meant — and only a person (never a delegate) may call these at all.
 */
@Publish
@AuthzUnchecked
public interface ProfileService {

    /** Returns the calling user's identity. */
    Future<UserParticipantIdentity> findMyProfile(Participant participant);

    /**
     * Sets the calling user's display name, the name shown wherever they appear in the
     * platform, and returns the saved identity.
     *
     * @param displayName the name to show, required
     */
    Future<UserParticipantIdentity> updateDisplayName(String displayName, Participant participant);

    /**
     * The ways the calling user signs in: its password, when it has one, and every provider identity
     * bound to it, in the order they were added.
     */
    Future<List<SignInMethod>> findMySignInMethods(Participant participant);

    /**
     * The configurations an organization user may connect as further sign-in methods and has not yet: the
     * platform's org sign-up registrations, and the own configurations of every organization it may sign in
     * to; none for a system user, which signs in with a password alone. Connecting one starts at
     * {@code POST /api/auth/org/methods/start/<configurationId>}.
     */
    Future<List<LoginProviderSummary>> findConnectableProviders(Participant participant);

    /**
     * Sets the calling user's password. A user that has one proves it is the caller's by giving the
     * current password; a user that signs in by a provider alone gives none and gains a password as a
     * second method.
     *
     * @param currentPassword the user's password, required when it has one
     * @param newPassword     the password to set
     */
    Future<Void> setPassword(String currentPassword, String newPassword, Participant participant);

    /**
     * Removes a provider identity from the calling user's sign-in methods. The last method that still signs
     * the user in cannot be removed, so the user always keeps a way to sign in: its password, a binding to
     * one of the platform's registrations, or a binding to an organization's own configuration while the user
     * is a member there.
     *
     * @param methodId the method's id, as {@link SignInMethod#id()} names it
     */
    Future<Void> removeSignInMethod(String methodId, Participant participant);

    /**
     * The organizations the calling organization user is a member of and may sign in to, oldest membership
     * first, for switching between them; none for a system user.
     */
    Future<List<Organization>> findMyOrganizations(Participant participant);
}
