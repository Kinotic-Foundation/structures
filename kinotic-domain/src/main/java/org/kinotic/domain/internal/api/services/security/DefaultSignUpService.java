package org.kinotic.domain.internal.api.services.security;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.kinotic.domain.api.config.KinoticDomainProperties;
import org.kinotic.domain.api.model.Organization;
import org.kinotic.domain.api.model.security.AuthType;
import org.kinotic.domain.api.model.security.identity.OrganizationParticipantIdentity;
import org.kinotic.domain.api.model.security.PendingSignUp;
import org.kinotic.domain.api.services.OrganizationService;
import org.kinotic.domain.api.services.OrganizationDnsRecordProvisioner;
import org.kinotic.domain.api.services.security.OrganizationMembershipService;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.domain.api.utils.DomainUtil;
import org.kinotic.core.api.security.Participant;
import org.kinotic.domain.api.services.security.SignUpService;
import org.kinotic.domain.internal.api.repositories.PendingSignUpRepository;
import org.kinotic.domain.internal.api.services.EmailService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultSignUpService implements SignUpService {

    private static final long LOCAL_TTL_MS = 24 * 60 * 60 * 1000L; // 24 hours
    private static final long OIDC_TTL_MS = 10 * 60 * 1000L;       // 10 minutes

    private final PendingSignUpRepository pendingSignUpRepository;
    private final ParticipantIdentityService identityService;
    private final OrganizationMembershipService membershipService;
    private final OrganizationService organizationService;
    private final EmailService emailService;
    private final OrganizationDnsRecordProvisioner dnsRecordProvisioner;
    private final KinoticDomainProperties domainProperties;

    @Override
    public Future<Void> initiateLocalSignUp(String email, String displayName, String origin) {
        Validate.notBlank(email, "Email is required");
        Validate.notBlank(displayName, "Display name is required");
        // the origin can come from a Referer the requester chose, so only an allowed UI's origin may host the link
        String linkOrigin = domainProperties.getDomain().getCors().trustsOrigin(origin) ? origin : null;

        return pendingSignUpRepository.findByEmail(email)
                .compose(existing -> {
                    if (existing != null) {
                        return Future.failedFuture(new IllegalArgumentException(
                                "A sign-up is already pending for this email. Check your inbox for the verification link."));
                    }
                    return identityService.findOrganizationUserByEmail(email);
                })
                .compose(user -> {
                    if (user != null) {
                        return Future.failedFuture(new IllegalArgumentException(
                                "This email already signs in to Kinotic organizations. Sign in instead."));
                    }
                    String token = UUID.randomUUID().toString();
                    Date now = new Date();
                    PendingSignUp pending = new PendingSignUp()
                            .setId(UUID.randomUUID().toString())
                            .setEmail(email)
                            .setDisplayName(displayName)
                            .setAuthType(AuthType.PASSWORD)
                            .setVerificationToken(token)
                            .setCreated(now)
                            .setExpiresAt(new Date(now.getTime() + LOCAL_TTL_MS));
                    return pendingSignUpRepository.save(pending)
                            .compose(saved -> emailService.sendVerificationEmail(email, displayName, token, linkOrigin));
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
    public Future<Participant> completeLocalSignUp(String token, String orgName, String orgDescription, String password) {
        Validate.notBlank(token, "Verification token is required");
        Validate.notBlank(orgName, "Organization name is required");
        Validate.notBlank(password, "Password is required");

        return pendingSignUpRepository.findValidByToken(token)
                .compose(pending -> requireNoOrganizationUser(pending)
                        .compose(v -> createOrgWithFounder(orgName, orgDescription, pending, password))
                        .compose(founder -> pendingSignUpRepository.deleteById(pending.getId())
                                .map(founder)));
    }

    @Override
    public Future<Participant> completeOidcWithNewOrg(String token, String orgName, String orgDescription) {
        Validate.notBlank(orgName, "Organization name is required");
        return pendingSignUpRepository.findValidByToken(token)
                .compose(pending -> requireNoOrganizationUser(pending)
                        .compose(v -> createOrgWithFounder(orgName, orgDescription, pending, null))
                        .compose(founder -> pendingSignUpRepository.deleteById(pending.getId())
                                .map(founder)));
    }

    /**
     * Fails when the identity of {@code pending}, or its verified email, already has an organization user: a
     * person founds one organization at most, by signing up once, and joins others by invitation. Checked here, at
     * completion, as well as when the sign-up starts, so the organization is never created for an organization
     * user that cannot be.
     */
    private Future<Void> requireNoOrganizationUser(PendingSignUp pending) {
        Future<OrganizationParticipantIdentity> bound = pending.getAuthType() == AuthType.OIDC
                ? identityService.findOrganizationUserByOidcIdentity(pending.getOidcConfigId(), pending.getOidcSubject())
                : Future.succeededFuture();
        return bound.compose(found -> found != null ? Future.succeededFuture(found) : identityService.findOrganizationUserByEmail(pending.getEmail()))
                    .compose(found -> found == null
                            ? Future.succeededFuture()
                            : Future.failedFuture(new IllegalArgumentException("This identity already signs in to Kinotic organizations. Sign in instead.")));
    }

    /**
     * Creates the organization (failing if the name is taken), the founder's organization user from the pending
     * sign-up — with {@code password} as its credential, or bound to the pending record's verified identity
     * — its membership of the organization, which makes it the organization's creator, and provisions the
     * organization's sites subdomain at the edge.
     *
     * @return the founder as the new organization's participant
     */
    private Future<Participant> createOrgWithFounder(String orgName, String orgDescription, PendingSignUp pending, String password) {
        Organization org = new Organization().setName(orgName).setDescription(orgDescription);
        OrganizationParticipantIdentity user = new OrganizationParticipantIdentity();
        user.setEmail(pending.getEmail())
            .setDisplayName(pending.getDisplayName());
        return organizationService.create(org)
                .compose(savedOrg -> {
                    Future<OrganizationParticipantIdentity> created = pending.getAuthType() == AuthType.OIDC
                            ? identityService.createOidcUser(user, pending.getOidcConfigId(), pending.getOidcSubject())
                            : identityService.createUser(user, password);
                    return created
                            .compose(founder -> membershipService.addMembership(founder.getId(), savedOrg.getId())
                                                                 .compose(membership -> {
                                                                     savedOrg.setCreatedBy(founder.getId());
                                                                     return organizationService.save(savedOrg)
                                                                                               .map(DomainUtil.createParticipant(founder, membership));
                                                                 })
                                                                 // a founder whose membership or organization could not be
                                                                 // completed goes too: left behind, it could neither sign in
                                                                 // anywhere nor sign up again
                                                                 .recover(err -> identityService.deleteById(founder.getId())
                                                                                                .onFailure(deleteErr -> log.warn("Could not delete the founder {} of organization {}: {}",
                                                                                                                                 founder.getId(), savedOrg.getId(), deleteErr.getMessage()))
                                                                                                .transform(v -> Future.failedFuture(err))))
                            // and so does the organization, as when a sign-up of the same identity completed first, so
                            // its name is not held by an organization no one is in
                            .recover(err -> organizationService.deleteById(savedOrg.getId())
                                                               .transform(v -> Future.failedFuture(err)))
                            // the edge is provisioned again before the organization's first site is published, so a
                            // refusal here costs the sign-up nothing
                            .compose(founder -> dnsRecordProvisioner.ensure(savedOrg.getId())
                                                                     .recover(err -> {
                                                                         log.warn("Could not provision the sites subdomain of organization {}: {}",
                                                                                  savedOrg.getId(), err.getMessage());
                                                                         return Future.succeededFuture();
                                                                     })
                                                                     .map(founder));
                });
    }
}
