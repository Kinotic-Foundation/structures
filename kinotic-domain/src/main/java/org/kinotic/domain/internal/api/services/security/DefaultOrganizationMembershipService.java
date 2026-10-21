package org.kinotic.domain.internal.api.services.security;

import io.vertx.core.Future;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.core.api.security.Participant;
import org.kinotic.domain.api.exceptions.AccountDisabledException;
import org.kinotic.domain.api.model.security.Member;
import org.kinotic.domain.api.model.security.identity.DelegatingParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.OidcBinding;
import org.kinotic.domain.api.model.security.identity.OrganizationMembership;
import org.kinotic.domain.api.model.security.identity.OrganizationUserParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.ParticipantIdentity;
import org.kinotic.domain.api.services.security.OrganizationMembershipService;
import org.kinotic.domain.api.utils.DomainUtil;
import org.kinotic.domain.internal.api.model.IdentityCredential;
import org.kinotic.domain.internal.api.repositories.IdentityCredentialRepository;
import org.kinotic.domain.internal.api.repositories.OidcBindingRepository;
import org.kinotic.domain.internal.api.repositories.OrganizationMembershipRepository;
import org.kinotic.domain.internal.api.repositories.ParticipantIdentityRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DefaultOrganizationMembershipService implements OrganizationMembershipService {

    // the most delegates one organization user authorized in one organization, removed with its membership
    private static final int MAX_DELEGATES = 1000;

    private final OrganizationMembershipRepository membershipRepository;
    private final ParticipantIdentityRepository identityRepository;
    private final IdentityCredentialRepository credentialRepository;
    private final OidcBindingRepository bindingRepository;

    @Override
    public Future<OrganizationMembership> addMembership(String organizationUserId, String organizationId) {
        return findMembership(organizationUserId, organizationId).compose(existing -> {
            Future<OrganizationMembership> ret;
            if (existing != null) {
                ret = Future.succeededFuture(existing);
            } else {
                Date now = new Date();
                OrganizationMembership membership = new OrganizationMembership().setId(OrganizationMembership.idOf(organizationUserId, organizationId))
                                                                                .setOrganizationUserId(organizationUserId)
                                                                                .setOrganizationId(organizationId)
                                                                                .setEnabled(true)
                                                                                .setCreated(now)
                                                                                .setUpdated(now);
                // sync so the member list a console re-queries at once shows the new member
                ret = membershipRepository.saveSync(membership);
            }
            return ret;
        });
    }

    @Override
    public Future<OrganizationMembership> findMembership(String organizationUserId, String organizationId) {
        return membershipRepository.findById(OrganizationMembership.idOf(organizationUserId, organizationId));
    }

    @Override
    public Future<List<OrganizationMembership>> findMemberships(String organizationUserId) {
        return membershipRepository.findByOrganizationUser(organizationUserId);
    }

    @Override
    public Future<Page<Member>> findMembers(String organizationId, Pageable pageable) {
        return membershipRepository.findByOrganization(organizationId, pageable)
                                   .compose(page -> {
                                       List<String> ids = page.getContent().stream().map(OrganizationMembership::getOrganizationUserId).toList();
                                       return Future.all(identityRepository.findAllById(ids), claimed(ids))
                                                    .map(cf -> members(page, cf.resultAt(0), cf.resultAt(1)));
                                   });
    }

    // the page's memberships joined with their organization users, in the page's order; a membership whose user is gone is left out
    private static Page<Member> members(Page<OrganizationMembership> page, List<ParticipantIdentity> identities, Set<String> claimed) {
        Map<String, OrganizationUserParticipantIdentity> byId = identities.stream()
                                                                          .filter(OrganizationUserParticipantIdentity.class::isInstance)
                                                                          .map(OrganizationUserParticipantIdentity.class::cast)
                                                                          .collect(Collectors.toMap(ParticipantIdentity::getId, Function.identity()));
        List<Member> members = page.getContent().stream()
                                   .filter(membership -> byId.containsKey(membership.getOrganizationUserId()))
                                   .map(membership -> member(byId.get(membership.getOrganizationUserId()), membership,
                                                             claimed.contains(membership.getOrganizationUserId())))
                                   .toList();
        return new Page<>(members, page.getTotalElements() - (page.getContent().size() - members.size()));
    }

    private static Member member(OrganizationUserParticipantIdentity user, OrganizationMembership membership, boolean claimed) {
        return new Member(user.getId(), user.getEmail(), user.getDisplayName(), membership.isEnabled(), membership.getCreated(), !claimed);
    }

    // the organization users among ids that a sign-in method signs in: a password, or a provider identity bound to them
    private Future<Set<String>> claimed(Collection<String> ids) {
        return Future.all(credentialRepository.findAllById(ids), bindingRepository.findByIdentities(ids)).map(cf -> {
            Set<String> ret = new HashSet<>();
            cf.<List<IdentityCredential>>resultAt(0).forEach(credential -> ret.add(credential.getId()));
            cf.<List<OidcBinding>>resultAt(1).forEach(binding -> ret.add(binding.getIdentityId()));
            return ret;
        });
    }

    @Override
    public Future<Page<Member>> searchMembers(String searchText, String organizationId, Pageable pageable) {
        Future<Page<Member>> ret;
        if (searchText == null || searchText.isBlank()) {
            ret = findMembers(organizationId, pageable);
        } else {
            // the text matches organization users, so the search is held to the organization's members by their ids
            // and every page, and the total, counts members only
            ret = membershipRepository.findAllByOrganization(organizationId)
                                      .compose(memberships -> {
                                          Map<String, OrganizationMembership> byUser = memberships.stream()
                                                  .collect(Collectors.toMap(OrganizationMembership::getOrganizationUserId, Function.identity()));
                                          return identityRepository.searchOrganizationUsersById(searchText, byUser.keySet(), pageable)
                                                                   .compose(users -> claimed(users.getContent().stream().map(ParticipantIdentity::getId).toList())
                                                                           .map(claimed -> users.map(user -> member(user, byUser.get(user.getId()),
                                                                                                                    claimed.contains(user.getId())))));
                                      });
        }
        return ret;
    }

    @Override
    public Future<Participant> resolveParticipant(OrganizationUserParticipantIdentity user, String organizationId) {
        Validate.notNull(user, "user cannot be null");
        return membershipRepository.findById(OrganizationMembership.idOf(user.getId(), organizationId)).map(membership -> {
            Participant participant = null;
            if (membership != null) {
                if (!user.isEnabled() || !membership.isEnabled()) {
                    throw new AccountDisabledException("This account cannot sign in to the organization.");
                }
                participant = DomainUtil.createParticipant(user, membership);
            }
            return participant;
        });
    }

    @Override
    public Future<Void> setMembershipEnabled(String organizationUserId, String organizationId, boolean enabled) {
        return requireMembership(organizationUserId, organizationId)
                // sync so the console's immediate re-query sees the change
                .compose(membership -> membershipRepository.saveSync(membership.setEnabled(enabled).setUpdated(new Date())))
                .mapEmpty();
    }

    @Override
    public Future<Void> removeMembership(String organizationUserId, String organizationId) {
        return requireMembership(organizationUserId, organizationId)
                .compose(membership -> identityRepository.findDelegatesByOwner(organizationUserId, organizationId, null,
                                                                               Pageable.ofSize(MAX_DELEGATES)))
                // a delegate wields the membership's authority, so it does not outlive the membership
                .compose(delegates -> Future.all(delegates.getContent().stream()
                                                          .map(DelegatingParticipantIdentity::getId)
                                                          .map(identityRepository::deleteById)
                                                          .toList()))
                // sync so the console's immediate re-query no longer shows the member
                .compose(v -> membershipRepository.deleteByIdSync(OrganizationMembership.idOf(organizationUserId, organizationId)));
    }

    private Future<OrganizationMembership> requireMembership(String organizationUserId, String organizationId) {
        return findMembership(organizationUserId, organizationId).map(membership -> {
            Validate.isTrue(membership != null, "Member not found.");
            return membership;
        });
    }
}
