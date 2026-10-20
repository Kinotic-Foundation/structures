package org.kinotic.domain.internal.api.services.security;

import io.vertx.core.Future;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.kinotic.authz.api.model.RelationshipTuple;
import org.kinotic.authz.api.services.AuthzModelGenerator;
import org.kinotic.authz.api.services.AuthzStoreService;
import org.kinotic.authz.api.services.RelationshipService;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.core.api.directory.ServiceDirectory;
import org.kinotic.core.api.security.Participant;
import org.kinotic.domain.api.exceptions.AccountDisabledException;
import org.kinotic.domain.api.model.security.Member;
import org.kinotic.domain.api.model.security.identity.DelegatingParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.Membership;
import org.kinotic.domain.api.model.security.identity.OidcBinding;
import org.kinotic.domain.api.model.security.identity.ParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.UserParticipantIdentity;
import org.kinotic.domain.api.services.security.MembershipService;
import org.kinotic.domain.api.utils.DomainUtil;
import org.kinotic.domain.internal.api.model.IdentityCredential;
import org.kinotic.domain.internal.api.repositories.IdentityCredentialRepository;
import org.kinotic.domain.internal.api.repositories.MembershipRepository;
import org.kinotic.domain.internal.api.repositories.OidcBindingRepository;
import org.kinotic.domain.internal.api.repositories.ParticipantIdentityRepository;
import org.kinotic.idl.api.utils.AuthzUtil;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DefaultMembershipService implements MembershipService {

    // the most delegates one account authorized in one scope, removed with its membership
    private static final int MAX_DELEGATES = 1000;

    private final MembershipRepository membershipRepository;
    private final ParticipantIdentityRepository identityRepository;
    private final IdentityCredentialRepository credentialRepository;
    private final OidcBindingRepository bindingRepository;
    private final RelationshipService relationships;
    private final AuthzModelGenerator generator;
    // the directory's strategy is built from this module's beans, so it is resolved when a membership is removed
    private final ObjectProvider<ServiceDirectory> directoryProvider;

    @Override
    public Future<Membership> addMember(String identityId, String organizationId) {
        Validate.notBlank(identityId, "identityId cannot be blank");
        return findMembership(identityId, organizationId).compose(existing -> {
            Future<Membership> ret;
            if (existing != null) {
                ret = Future.succeededFuture(existing);
            } else {
                ret = requireMembershipsOfOneKind(identityId, organizationId).compose(v -> {
                    Date now = new Date();
                    Membership membership = new Membership().setId(Membership.idOf(identityId, organizationId))
                                                            .setIdentityId(identityId)
                                                            .setOrganizationId(organizationId)
                                                            .setEnabled(true)
                                                            .setCreated(now)
                                                            .setUpdated(now);
                    // sync so the member list a console re-queries at once shows the new member
                    return membershipRepository.saveSync(membership)
                                               .compose(saved -> joined(saved).map(saved));
                });
            }
            return ret;
        });
    }

    // an account is a platform operator's or an organization member's, never both, so an operator signs in to
    // the system console alone and a member to their organizations alone
    private Future<Void> requireMembershipsOfOneKind(String identityId, String organizationId) {
        boolean platform = organizationId == null;
        return findMemberships(identityId).map(memberships -> {
            Validate.isTrue(memberships.stream().allMatch(membership -> (membership.getOrganizationId() == null) == platform),
                            platform ? "An organization's member cannot become a platform operator"
                                     : "A platform operator cannot become an organization's member");
            return null;
        });
    }

    @Override
    public Future<Membership> findMembership(String identityId, String organizationId) {
        Validate.notBlank(identityId, "identityId cannot be blank");
        return membershipRepository.findById(Membership.idOf(identityId, organizationId));
    }

    @Override
    public Future<List<Membership>> findMemberships(String identityId) {
        return membershipRepository.findByIdentity(identityId);
    }

    @Override
    public Future<Page<Member>> findMembers(String organizationId, Pageable pageable) {
        return membershipRepository.findByOrganization(organizationId, pageable)
                                   .compose(page -> {
                                       List<String> ids = page.getContent().stream().map(Membership::getIdentityId).toList();
                                       return Future.all(identityRepository.findAllById(ids), claimed(ids))
                                                    .map(cf -> members(page, cf.resultAt(0), cf.resultAt(1)));
                                   });
    }

    // the page's memberships joined with their accounts, in the page's order; a membership whose account is gone is left out
    private static Page<Member> members(Page<Membership> page, List<ParticipantIdentity> accounts, Set<String> claimed) {
        Map<String, UserParticipantIdentity> byId = accounts.stream()
                                                            .filter(UserParticipantIdentity.class::isInstance)
                                                            .map(UserParticipantIdentity.class::cast)
                                                            .collect(Collectors.toMap(ParticipantIdentity::getId, Function.identity()));
        List<Member> members = page.getContent().stream()
                                   .filter(membership -> byId.containsKey(membership.getIdentityId()))
                                   .map(membership -> member(byId.get(membership.getIdentityId()), membership, claimed.contains(membership.getIdentityId())))
                                   .toList();
        return new Page<>(members, page.getTotalElements() - (page.getContent().size() - members.size()));
    }

    private static Member member(UserParticipantIdentity account, Membership membership, boolean claimed) {
        return new Member(account.getId(), account.getEmail(), account.getDisplayName(), membership.isEnabled(), membership.getCreated(), !claimed);
    }

    // the accounts among ids that a sign-in method signs in: a password, or a provider identity bound to them
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
            // the text matches accounts, so the search is held to the organization's members by their ids and
            // every page, and the total, counts members only
            ret = membershipRepository.findAllByOrganization(organizationId)
                                      .compose(memberships -> {
                                          Map<String, Membership> byIdentity = memberships.stream()
                                                  .collect(Collectors.toMap(Membership::getIdentityId, Function.identity()));
                                          return identityRepository.searchUsersById(searchText, byIdentity.keySet(), pageable)
                                                                   .compose(accounts -> claimed(accounts.getContent().stream().map(ParticipantIdentity::getId).toList())
                                                                           .map(claimed -> accounts.map(account -> member(account, byIdentity.get(account.getId()),
                                                                                                                          claimed.contains(account.getId())))));
                                      });
        }
        return ret;
    }

    @Override
    public Future<Participant> resolveMember(UserParticipantIdentity account, String organizationId) {
        Validate.notNull(account, "account cannot be null");
        Validate.isTrue(account.isAccount(), "%s is not an account", account.getId());
        Future<Participant> ret;
        if (Membership.PLATFORM.equals(organizationId)) {
            // names no organization, and the platform membership is addressed by null alone; answered as a
            // non-member so a login naming it fails as a wrong password does, with no account oracle
            ret = Future.succeededFuture();
        } else {
            ret = membershipRepository.findById(Membership.idOf(account.getId(), organizationId)).map(membership -> {
                Participant participant = null;
                if (membership != null) {
                    if (!account.isEnabled() || !membership.isEnabled()) {
                        throw new AccountDisabledException("This account cannot sign in to the organization.");
                    }
                    participant = DomainUtil.createParticipant(account, membership);
                }
                return participant;
            });
        }
        return ret;
    }

    @Override
    public Future<Void> setMemberEnabled(String identityId, String organizationId, boolean enabled) {
        return requireMembership(identityId, organizationId)
                // sync so the console's immediate re-query sees the change
                .compose(membership -> membershipRepository.saveSync(membership.setEnabled(enabled).setUpdated(new Date())))
                .compose(membership -> enabled ? joined(membership) : left(identityId, organizationId));
    }

    @Override
    public Future<Void> removeMember(String identityId, String organizationId) {
        return requireMembership(identityId, organizationId)
                .compose(membership -> identityRepository.findDelegatesByOwner(identityId, organizationId, null,
                                                                               Pageable.ofSize(MAX_DELEGATES)))
                // a delegate wields the membership's authority, so it does not outlive the membership
                .compose(delegates -> Future.all(delegates.getContent().stream()
                                                          .map(DelegatingParticipantIdentity::getId)
                                                          .map(identityRepository::deleteById)
                                                          .toList()))
                // the graph before the record: a removal that fails leaves the member to be removed again
                .compose(v -> left(identityId, organizationId))
                .compose(v -> withdrawn(DomainUtil.authzMember(identityId, organizationId), organizationId))
                // sync so the console's immediate re-query no longer shows the member
                .compose(v -> membershipRepository.deleteByIdSync(Membership.idOf(identityId, organizationId)));
    }

    // An enabled membership of an organization is a member of it in the graph, which a grant to the organization's
    // members reaches; the platform has no such relation
    private Future<Void> joined(Membership membership) {
        return membership.getOrganizationId() == null || !membership.isEnabled()
                ? Future.succeededFuture()
                : relationships.ensure(AuthzStoreService.PLATFORM, List.of(memberOf(membership.getIdentityId(), membership.getOrganizationId())));
    }

    private Future<Void> left(String identityId, String organizationId) {
        return organizationId == null
                ? Future.succeededFuture()
                : relationships.remove(AuthzStoreService.PLATFORM, List.of(memberOf(identityId, organizationId)));
    }

    // The membership's user leaves every group and every grant, so an account joining again starts with none; a
    // binding is typed by what it is attached to, so the grants are read on each type of the membership's tier
    private Future<Void> withdrawn(String user, String organizationId) {
        String tier = organizationId == null ? AuthzUtil.PLATFORM_TYPE : AuthzUtil.ORGANIZATION_TYPE;
        return directoryProvider.getObject().findSystemDefinitions().map(generator::platformModel).compose(model -> {
            List<Future<List<RelationshipTuple>>> reads = new ArrayList<>();
            reads.add(relationships.readByUser(AuthzStoreService.PLATFORM, user, AuthzUtil.GROUP_TYPE));
            for (String type : model.tier(tier)) {
                reads.add(relationships.readByUser(AuthzStoreService.PLATFORM, user, AuthzUtil.roleBindingType(type)));
            }
            return Future.all(reads);
        }).compose(held -> {
            List<RelationshipTuple> all = new ArrayList<>();
            for (int i = 0; i < held.size(); i++) {
                all.addAll(held.<List<RelationshipTuple>>resultAt(i));
            }
            return relationships.remove(AuthzStoreService.PLATFORM, all);
        });
    }

    private static RelationshipTuple memberOf(String identityId, String organizationId) {
        return new RelationshipTuple(DomainUtil.authzMember(identityId, organizationId),
                                     AuthzUtil.MEMBER_RELATION,
                                     AuthzUtil.object(AuthzUtil.ORGANIZATION_TYPE, organizationId));
    }

    private Future<Membership> requireMembership(String identityId, String organizationId) {
        return findMembership(identityId, organizationId).map(membership -> {
            Validate.isTrue(membership != null, "Member not found.");
            return membership;
        });
    }
}
