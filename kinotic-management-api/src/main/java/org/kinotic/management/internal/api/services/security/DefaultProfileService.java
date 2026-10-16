package org.kinotic.management.internal.api.services.security;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.security.Participant;
import org.kinotic.domain.api.model.Organization;
import org.kinotic.domain.api.model.security.identity.Membership;
import org.kinotic.domain.api.model.security.identity.OidcBinding;
import org.kinotic.domain.api.model.security.identity.UserParticipantIdentity;
import org.kinotic.domain.api.services.OrganizationService;
import org.kinotic.domain.api.services.security.LocalAuthenticationService;
import org.kinotic.domain.api.services.security.MembershipService;
import org.kinotic.domain.api.services.security.OidcConfigurationService;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.domain.api.utils.DomainUtil;
import org.kinotic.management.api.model.SignInMethod;
import org.kinotic.management.api.model.SignInMethodKind;
import org.kinotic.management.api.services.security.ProfileService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DefaultProfileService implements ProfileService {

    private final ParticipantIdentityService identityService;
    private final OidcConfigurationService oidcConfigurationService;
    private final LocalAuthenticationService localAuthenticationService;
    private final MembershipService membershipService;
    private final OrganizationService organizationService;

    @Override
    public Future<UserParticipantIdentity> findMyProfile(Participant participant) {
        return loadCallingUser(participant);
    }

    @Override
    public Future<UserParticipantIdentity> updateDisplayName(String displayName, Participant participant) {
        Validate.notBlank(displayName, "displayName is required");
        return loadCallingUser(participant)
                .compose(user -> identityService.save(user.setDisplayName(displayName)))
                .map(UserParticipantIdentity.class::cast);
    }

    @Override
    public Future<List<SignInMethod>> findMySignInMethods(Participant participant) {
        DomainUtil.requireUserParticipant(participant);
        Future<Boolean> hasPassword = identityService.hasPassword(participant.getId());
        Future<List<SignInMethod>> bindings = identityService.findOidcBindings(participant.getId())
                .compose(found -> Future.all(found.stream().map(this::methodOf).toList()))
                .map(cf -> cf.<SignInMethod>list().stream().filter(Objects::nonNull).toList());
        return Future.all(hasPassword, bindings).map(cf -> {
            List<SignInMethod> methods = new ArrayList<>();
            if (hasPassword.result()) {
                methods.add(new SignInMethod(SignInMethod.PASSWORD_ID, SignInMethodKind.PASSWORD, "Password", null, null));
            }
            methods.addAll(bindings.result());
            return methods;
        });
    }

    // the binding as a method, named by its configuration; a binding whose configuration is gone is left out
    private Future<SignInMethod> methodOf(OidcBinding binding) {
        return oidcConfigurationService.findById(binding.getOidcConfigId())
                                       .map(configuration -> configuration == null ? null
                                               : new SignInMethod(binding.getId(), SignInMethodKind.OIDC, configuration.getName(),
                                                                  configuration.getProvider(), binding.getCreated()));
    }

    @Override
    public Future<Void> setPassword(String currentPassword, String newPassword, Participant participant) {
        Validate.notBlank(newPassword, "newPassword is required");
        return loadCallingUser(participant)
                .compose(user -> identityService.hasPassword(user.getId()).compose(hasPassword -> {
                    Future<Void> proven;
                    if (!hasPassword) {
                        // the session is proof enough for an account with no password yet
                        proven = Future.succeededFuture();
                    } else {
                        Validate.notBlank(currentPassword, "currentPassword is required");
                        proven = localAuthenticationService.authenticateLocal(user.getEmail(), currentPassword, null, null)
                                                           .map(authenticated -> {
                                                               Validate.isTrue(authenticated != null, "The current password is incorrect.");
                                                               return null;
                                                           });
                    }
                    return proven;
                }))
                .compose(v -> identityService.setPassword(participant.getId(), newPassword));
    }

    @Override
    public Future<Void> removeSignInMethod(String methodId, Participant participant) {
        Validate.notBlank(methodId, "methodId is required");
        Validate.isTrue(!SignInMethod.PASSWORD_ID.equals(methodId), "A password is changed, not removed.");
        return findMySignInMethods(participant).compose(methods -> {
            Validate.isTrue(methods.stream().anyMatch(method -> method.id().equals(methodId)), "Sign-in method not found.");
            Validate.isTrue(methods.size() > 1, "The last sign-in method cannot be removed.");
            return identityService.removeOidcBinding(participant.getId(), methodId);
        });
    }

    @Override
    public Future<List<Organization>> findMyOrganizations(Participant participant) {
        DomainUtil.requireUserParticipant(participant);
        return membershipService.findMemberships(participant.getId())
                                .compose(memberships -> Future.all(memberships.stream()
                                                                              .filter(m -> m.getOrganizationId() != null && m.isEnabled())
                                                                              .map(Membership::getOrganizationId)
                                                                              .map(organizationService::findById)
                                                                              .toList()))
                                .map(cf -> cf.<Organization>list().stream().filter(Objects::nonNull).toList());
    }

    /** Loads the account behind the calling participant. */
    private Future<UserParticipantIdentity> loadCallingUser(Participant participant) {
        DomainUtil.requireUserParticipant(participant);
        return identityService.findById(participant.getId())
                              .map(identity -> {
                                  // a session outlives its row when the account is deleted mid-session
                                  if (!(identity instanceof UserParticipantIdentity user)) {
                                      throw new IllegalArgumentException("Profile not found.");
                                  }
                                  return user;
                              });
    }
}
