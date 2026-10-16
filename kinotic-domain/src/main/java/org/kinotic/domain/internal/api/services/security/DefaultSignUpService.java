package org.kinotic.domain.internal.api.services.security;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.kinotic.domain.api.model.Organization;
import org.kinotic.domain.api.model.security.AuthType;
import org.kinotic.domain.api.model.security.identity.UserParticipantIdentity;
import org.kinotic.domain.api.model.security.PendingSignUp;
import org.kinotic.domain.api.services.OrganizationService;
import org.kinotic.domain.api.services.SiteDomainProvisioner;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.domain.api.services.security.SignUpService;
import org.kinotic.domain.internal.api.repositories.PendingSignUpRepository;
import org.kinotic.domain.internal.api.services.EmailService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultSignUpService implements SignUpService {

    private static final long LOCAL_TTL_MS = 24 * 60 * 60 * 1000L; // 24 hours
    private static final long OIDC_TTL_MS = 10 * 60 * 1000L;       // 10 minutes

    private final PendingSignUpRepository pendingSignUpRepository;
    private final ParticipantIdentityService identityService;
    private final OrganizationService organizationService;
    private final EmailService emailService;
    private final SiteDomainProvisioner siteDomainProvisioner;

    @Override
    public Future<Void> initiateLocalSignUp(String email, String displayName) {
        Validate.notBlank(email, "Email is required");
        Validate.notBlank(displayName, "Display name is required");

        return pendingSignUpRepository.findByEmail(email)
                .compose(existing -> {
                    if (existing != null) {
                        return Future.failedFuture(new IllegalArgumentException(
                                "A sign-up is already pending for this email. Check your inbox for the verification link."));
                    }
                    return identityService.findOrgUsersByEmail(email);
                })
                .compose(memberships -> {
                    if (!memberships.isEmpty()) {
                        return Future.failedFuture(new IllegalArgumentException(
                                "An account with this email already exists."));
                    }
                    String token = UUID.randomUUID().toString();
                    Date now = new Date();
                    PendingSignUp pending = new PendingSignUp()
                            .setId(UUID.randomUUID().toString())
                            .setEmail(email)
                            .setDisplayName(displayName)
                            .setAuthType(AuthType.LOCAL)
                            .setVerificationToken(token)
                            .setCreated(now)
                            .setExpiresAt(new Date(now.getTime() + LOCAL_TTL_MS));
                    return pendingSignUpRepository.save(pending)
                            .compose(saved -> emailService.sendVerificationEmail(email, displayName, token));
                });
    }

    @Override
    public Future<PendingSignUp> createOidcPending(PendingSignUp pending) {
        Validate.notBlank(pending.getOidcSubject(), "oidcSubject is required");
        Validate.notBlank(pending.getOidcConfigId(), "oidcConfigId is required");
        Validate.notBlank(pending.getEmail(), "email is required");

        Date now = new Date();
        pending.setId(UUID.randomUUID().toString())
               .setAuthType(AuthType.OIDC)
               .setVerificationToken(UUID.randomUUID().toString())
               .setCreated(now)
               .setExpiresAt(new Date(now.getTime() + OIDC_TTL_MS));
        return pendingSignUpRepository.saveSync(pending);
    }

    @Override
    public Future<UserParticipantIdentity> completeLocalSignUp(String token, String orgName, String orgDescription, String password) {
        Validate.notBlank(token, "Verification token is required");
        Validate.notBlank(orgName, "Organization name is required");
        Validate.notBlank(password, "Password is required");

        return pendingSignUpRepository.findValidByToken(token)
                .compose(pending -> requireNoOrganization(pending)
                        .compose(v -> createOrgWithAdmin(orgName, orgDescription, newUser(pending), password))
                        .compose(savedAdmin -> pendingSignUpRepository.deleteById(pending.getId())
                                .map(savedAdmin)));
    }

    @Override
    public Future<UserParticipantIdentity> completeOidcWithNewOrg(String token, String orgName, String orgDescription) {
        Validate.notBlank(orgName, "Organization name is required");
        return pendingSignUpRepository.findValidByToken(token)
                .compose(pending -> requireNoOrganization(pending)
                        .compose(v -> createOrgWithAdmin(orgName, orgDescription, newUser(pending), null))
                        .compose(savedAdmin -> pendingSignUpRepository.deleteById(pending.getId())
                                .map(savedAdmin)));
    }

    /**
     * Fails when the identity of {@code pending} is already a member of an organization: an identity founds
     * one organization at most, and joins others by invitation. Checked here, at completion, as well as
     * when the sign-up starts, so two sign-ups of one identity in flight at once cannot both found an
     * organization.
     */
    private Future<Void> requireNoOrganization(PendingSignUp pending) {
        Future<List<UserParticipantIdentity>> memberships = pending.getAuthType() == AuthType.OIDC
                ? identityService.findOrgUsersByOidcIdentity(pending.getOidcSubject(), pending.getOidcConfigId())
                : identityService.findOrgUsersByEmail(pending.getEmail());
        return memberships.compose(found -> found.isEmpty()
                ? Future.succeededFuture()
                : Future.failedFuture(new IllegalArgumentException("An account for this identity already exists. Sign in instead.")));
    }

    /**
     * Creates the organization (failing if the name is taken), makes {@code admin} its first
     * member and creator, and provisions the organization's sites subdomain at the edge. The admin
     * (and its credential, when {@code password} is non-null) is created through
     * {@link ParticipantIdentityService#createUser} so member creation has a single code path.
     */
    private Future<UserParticipantIdentity> createOrgWithAdmin(String orgName, String orgDescription, UserParticipantIdentity admin, String password) {
        Organization org = new Organization().setName(orgName).setDescription(orgDescription);
        return organizationService.create(org)
                .compose(savedOrg -> {
                    admin.setOrganizationId(savedOrg.getId());
                    return identityService.createUser(admin, password)
                            .compose(savedAdmin -> {
                                savedOrg.setCreatedBy(savedAdmin.getId());
                                return organizationService.save(savedOrg).map(savedAdmin);
                            })
                            // the edge is provisioned again before the organization's first site is published, so a
                            // refusal here costs the sign-up nothing
                            .compose(savedAdmin -> siteDomainProvisioner.ensure(savedOrg.getId())
                                                                        .recover(err -> {
                                                                            log.warn("Could not provision the sites subdomain of organization {}: {}",
                                                                                     savedOrg.getId(), err.getMessage());
                                                                            return Future.succeededFuture();
                                                                        })
                                                                        .map(savedAdmin));
                });
    }

    /**
     * Builds an unsaved {@link UserParticipantIdentity} carrying the pending record's identity. Id, dates,
     * the enabled flag and the credential are applied by {@link ParticipantIdentityService#createUser}.
     */
    private UserParticipantIdentity newUser(PendingSignUp pending) {
        UserParticipantIdentity user = new UserParticipantIdentity();
        user.setEmail(pending.getEmail())
            .setDisplayName(pending.getDisplayName())
            .setAuthType(pending.getAuthType());
        if (pending.getAuthType() == AuthType.OIDC) {
            user.setOidcSubject(pending.getOidcSubject())
                .setOidcConfigId(pending.getOidcConfigId());
        }
        return user;
    }
}
