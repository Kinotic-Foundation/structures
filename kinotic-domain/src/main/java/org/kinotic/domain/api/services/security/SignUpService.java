package org.kinotic.domain.api.services.security;

import io.vertx.core.Future;
import org.kinotic.domain.api.exceptions.ExistingUserException;
import org.kinotic.domain.api.exceptions.PendingMemberException;
import org.kinotic.core.api.exceptions.AuthorizationException;
import org.kinotic.core.api.security.Participant;
import org.kinotic.domain.api.model.Application;
import org.kinotic.domain.api.model.ApplicationKey;
import org.kinotic.domain.api.model.EmailTemplate;
import org.kinotic.domain.api.model.security.PendingSignUp;

/**
 * Drives every sign-up through one short-lived {@link PendingSignUp} record. Two phases:
 * <em>initiate</em> stashes the record and verifies the email (a link for local, the IdP callback
 * for OIDC); <em>complete</em> consumes the record and creates the {@link UserParticipantIdentity} — and, for a
 * brand-new organization, the organization too.
 */
public interface SignUpService {

    /**
     * Starts an email/password sign-up: validates and stores a pending record and emails a
     * verification link. The organization name and password are collected later, at completion. Fails with
     * {@link ExistingUserException} when the email has an organization user someone signs in as, and with
     * {@link PendingMemberException} when it has one an administrator added that nobody signs in as yet.
     *
     * @param email           the email to verify
     * @param displayName     the founder's display name
     * @param emailTemplate   the verification email to send in place of Kinotic's, whose UI's {@code /signup/verify}
     *                        page the link opens, or {@code null} for Kinotic's, which opens the portal's
     * @throws IllegalArgumentException when {@code emailTemplate} is not valid, as {@link EmailTemplate} describes
     */
    Future<Void> initiateLocalSignUp(String email, String displayName, EmailTemplate emailTemplate);

    /**
     * Stores a pending sign-up for an identity already verified by an OIDC provider, returning it
     * with its {@code id} and {@code verificationToken} populated. The caller supplies the verified
     * identity (and, for an existing-org registration, the {@code organizationId}). Fails as
     * {@link #initiateLocalSignUp} does when the identity, or its verified email, has an organization user.
     */
    Future<PendingSignUp> createOidcPending(PendingSignUp pending);

    /**
     * Completes an email/password sign-up: validates the token, creates the organization with the
     * given name (failing if it is taken), the founder's organization user with its password and its
     * membership of the organization, then deletes the pending record.
     *
     * @return the founder as the new organization's participant, for the session to sign in
     */
    Future<Participant> completeLocalSignUp(String token, String orgName, String orgDescription, String password);

    /**
     * Completes an OIDC sign-up by creating a new organization with the given name (failing if it is
     * taken), the founder's organization user bound to the verified identity and its membership of the
     * organization, then deleting the pending record.
     *
     * @return the founder as the new organization's participant, for the session to sign in
     */
    Future<Participant> completeOidcWithNewOrg(String token, String orgName, String orgDescription);

    /**
     * Starts a sign-up to an application with an email and password: stores a pending record and emails a
     * verification link that opens the {@code /signup/verify} page of the application's UI at {@code uiBaseUrl}. The
     * password is collected at completion, and a later start for the same email replaces the link of an earlier one.
     * An email that already has a user of the application is sent nothing, and the call succeeds as it does for any
     * other email, so it does not tell whether an email has a user.
     *
     * @param application the application signed up to
     * @param email       the email to verify
     * @param displayName the new user's display name
     * @param uiBaseUrl   the origin of the application's UI the sign-up was made from, with no trailing slash
     * @return fails with {@link AuthorizationException} unless the application allows
     *         {@link Application#isPasswordSignUp() password sign-up}
     */
    Future<Void> initiateApplicationSignUp(ApplicationKey application, String email, String displayName, String uiBaseUrl);

    /**
     * Completes a sign-up to an application: validates that the token was issued for the application, creates the
     * application's user with the email the link verified and {@code password}, with a tenant of its own, and
     * deletes the pending record. Fails with {@link ExistingUserException} when the email became a user of the
     * application meanwhile.
     *
     * @return the new user as the application's participant, for the session to sign in; fails with
     *         {@link AuthorizationException} unless the application allows {@link Application#isPasswordSignUp()
     *         password sign-up}
     */
    Future<Participant> completeApplicationSignUp(ApplicationKey application, String token, String password);
}
