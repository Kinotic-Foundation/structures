package org.kinotic.management.internal.api.services.security;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.security.Participant;
import org.kinotic.domain.api.model.Organization;
import org.kinotic.domain.api.model.security.AuthType;
import org.kinotic.domain.api.model.security.LoginProviderSummary;
import org.kinotic.domain.api.model.security.OidcConfiguration;
import org.kinotic.domain.api.model.security.OrgSignupOidcConfiguration;
import org.kinotic.domain.api.model.security.OrganizationOidcConfiguration;
import org.kinotic.domain.api.model.security.identity.OrganizationMembership;
import org.kinotic.domain.api.model.security.participant.OrganizationParticipant;
import org.kinotic.domain.api.model.security.identity.OidcBinding;
import org.kinotic.domain.api.model.security.identity.UserParticipantIdentity;
import org.kinotic.domain.api.services.OrganizationService;
import org.kinotic.domain.api.services.security.LocalAuthenticationService;
import org.kinotic.domain.api.services.security.OrganizationMembershipService;
import org.kinotic.domain.api.services.security.OidcConfigurationService;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.domain.api.utils.DomainUtil;
import org.kinotic.management.api.model.SignInMethod;
import org.kinotic.management.api.services.security.ProfileService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DefaultProfileService implements ProfileService {

    private final ParticipantIdentityService identityService;
    private final OidcConfigurationService oidcConfigurationService;
    private final LocalAuthenticationService localAuthenticationService;
    private final OrganizationMembershipService membershipService;
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
                .compose(found -> oidcConfigurationService.findAllById(found.stream().map(OidcBinding::getOidcConfigId).toList())
                                                          .map(configurations -> methodsOf(found, configurations)));
        return Future.all(hasPassword, bindings).map(cf -> {
            List<SignInMethod> methods = new ArrayList<>();
            if (hasPassword.result()) {
                methods.add(new SignInMethod(SignInMethod.PASSWORD_ID, AuthType.PASSWORD, "Password", null, null));
            }
            methods.addAll(bindings.result());
            return methods;
        });
    }

    // the bindings as methods, each named by its configuration, in the bindings' order; a binding whose
    // configuration is gone is left out
    private static List<SignInMethod> methodsOf(List<OidcBinding> bindings, List<OidcConfiguration> configurations) {
        Map<String, OidcConfiguration> byId = configurations.stream().collect(Collectors.toMap(OidcConfiguration::getId, Function.identity()));
        return bindings.stream()
                       .filter(binding -> byId.containsKey(binding.getOidcConfigId()))
                       .map(binding -> {
                           OidcConfiguration configuration = byId.get(binding.getOidcConfigId());
                           return new SignInMethod(binding.getId(), AuthType.OIDC, configuration.getName(),
                                                   configuration.getProvider(), binding.getCreated());
                       })
                       .toList();
    }

    @Override
    public Future<List<LoginProviderSummary>> findConnectableProviders(Participant participant) {
        DomainUtil.requireUserParticipant(participant);
        // a system user signs in with a password alone
        return participant instanceof OrganizationParticipant
                ? findConnectableProviders(participant.getId())
                : Future.succeededFuture(List.of());
    }

    private Future<List<LoginProviderSummary>> findConnectableProviders(String organizationUserId) {
        Future<Set<String>> bound = identityService.findOidcBindings(organizationUserId)
                                                   .map(bindings -> bindings.stream().map(OidcBinding::getOidcConfigId).collect(Collectors.toSet()));
        Future<List<OidcConfiguration>> offered = membershipService.findMemberships(organizationUserId)
                .compose(memberships -> Future.all(memberships.stream()
                                                              .filter(OrganizationMembership::isEnabled)
                                                              .map(m -> oidcConfigurationService.findEnabledForScope(m.getOrganizationId(), null))
                                                              .toList()))
                .map(cf -> {
                    // every organization's login lists the system's registrations, so each appears once
                    Map<String, OidcConfiguration> byId = new LinkedHashMap<>();
                    for (List<OidcConfiguration> configurations : cf.<List<OidcConfiguration>>list()) {
                        configurations.forEach(configuration -> byId.putIfAbsent(configuration.getId(), configuration));
                    }
                    return List.copyOf(byId.values());
                })
                // an organization user of no organization still connects the system's registrations
                .compose(configurations -> configurations.isEmpty()
                        ? oidcConfigurationService.findEnabledOrgSignupProviders().map(signup -> List.<OidcConfiguration>copyOf(signup))
                        : Future.succeededFuture(configurations));
        return Future.all(bound, offered).map(cf -> offered.result().stream()
                                                           .filter(configuration -> !bound.result().contains(configuration.getId()))
                                                           .map(LoginProviderSummary::of)
                                                           .toList());
    }

    @Override
    public Future<Void> setPassword(String currentPassword, String newPassword, Participant participant) {
        Validate.notBlank(newPassword, "newPassword is required");
        return loadCallingUser(participant)
                .compose(user -> identityService.hasPassword(user.getId()).compose(hasPassword -> {
                    Future<Void> proven;
                    if (!hasPassword) {
                        // the session is proof enough for a user with no password yet
                        proven = Future.succeededFuture();
                    } else {
                        Validate.notBlank(currentPassword, "currentPassword is required");
                        proven = localAuthenticationService.verifyPassword(user, currentPassword)
                                                           .map(verified -> {
                                                               Validate.isTrue(verified, "The current password is incorrect.");
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
        DomainUtil.requireUserParticipant(participant);
        Future<Boolean> hasPassword = identityService.hasPassword(participant.getId());
        Future<List<OidcBinding>> bindings = identityService.findOidcBindings(participant.getId());
        Future<List<OrganizationMembership>> memberships = membershipService.findMemberships(participant.getId());
        return Future.all(hasPassword, bindings, memberships).compose(cf -> {
            Validate.isTrue(bindings.result().stream().anyMatch(binding -> binding.getId().equals(methodId)), "Sign-in method not found.");
            List<String> remaining = bindings.result().stream()
                                             .filter(binding -> !binding.getId().equals(methodId))
                                             .map(OidcBinding::getOidcConfigId)
                                             .toList();
            return oidcConfigurationService.findAllById(remaining).compose(configurations -> {
                boolean signsIn = hasPassword.result()
                                  || configurations.stream().anyMatch(configuration -> signsIn(configuration, memberships.result()));
                Validate.isTrue(signsIn, "The last sign-in method that still signs you in cannot be removed.");
                return identityService.removeOidcBinding(participant.getId(), methodId);
            });
        });
    }

    // a binding signs the organization user in where its configuration is offered: at every organization's login for
    // one of the system's registrations, and at its organization's for one of the organization's own while the
    // user is a member there
    private static boolean signsIn(OidcConfiguration configuration, List<OrganizationMembership> memberships) {
        boolean ret;
        if (!configuration.isEnabled()) {
            ret = false;
        } else if (configuration instanceof OrganizationOidcConfiguration owned) {
            ret = memberships.stream().anyMatch(m -> m.isEnabled() && owned.getOrganizationId().equals(m.getOrganizationId()));
        } else {
            ret = configuration instanceof OrgSignupOidcConfiguration;
        }
        return ret;
    }

    @Override
    public Future<List<Organization>> findMyOrganizations(Participant participant) {
        DomainUtil.requireUserParticipant(participant);
        return membershipService.findMemberships(participant.getId())
                                .compose(memberships -> organizationService.findAllById(memberships.stream()
                                                                                                  .filter(OrganizationMembership::isEnabled)
                                                                                                  .map(OrganizationMembership::getOrganizationId)
                                                                                                  .toList()));
    }

    /** Loads the user behind the calling participant. */
    private Future<UserParticipantIdentity> loadCallingUser(Participant participant) {
        DomainUtil.requireUserParticipant(participant);
        return identityService.findById(participant.getId())
                              .map(identity -> {
                                  // a session outlives its row when the user is deleted mid-session
                                  if (!(identity instanceof UserParticipantIdentity user)) {
                                      throw new IllegalArgumentException("Profile not found.");
                                  }
                                  return user;
                              });
    }
}
