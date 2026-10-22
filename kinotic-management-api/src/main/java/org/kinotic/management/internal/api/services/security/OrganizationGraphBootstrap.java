package org.kinotic.management.internal.api.services.security;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kinotic.authz.api.model.RelationshipTuple;
import org.kinotic.authz.api.services.AuthzStoreService;
import org.kinotic.authz.api.services.RelationshipService;
import org.kinotic.core.api.crud.CursorPage;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.core.api.crud.Sort;
import org.kinotic.domain.api.model.Organization;
import org.kinotic.domain.api.model.security.OrganizationMember;
import org.kinotic.domain.api.services.OrganizationService;
import org.kinotic.domain.api.services.security.OrganizationMembershipService;
import org.kinotic.domain.api.utils.DomainUtil;
import org.kinotic.idl.api.utils.AuthzConstants;
import org.kinotic.idl.api.utils.AuthzUtil;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.kinotic.authz.api.services.AuthzStoreService.SYSTEM;

/**
 * Places every organization that has no administrator in the authorization graph, once the server is up and the
 * system store runs a model: the organization on the system, its enabled members as its members, and the
 * membership of its creator as its administrator. A sign-up does the same for the organization it creates; an
 * organization a migration seeds has records but no tuples, and this gives it the ones a sign-up would have. An
 * organization with an administrator, whatever else was granted or revoked since, is left as it is.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrganizationGraphBootstrap {

    private static final long RETRY_MILLIS = 2_000;
    private static final int PAGE_SIZE = 100;

    private final Vertx vertx;
    private final OrganizationService organizations;
    private final OrganizationMembershipService memberships;
    private final AuthzStoreService stores;
    private final RelationshipService relationships;

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        vertx.runOnContext(v -> run());
    }

    private void run() {
        stores.modelId(SYSTEM)
              .compose(modelId -> page(null, 0))
              .onSuccess(placed -> {
                  if (placed > 0) {
                      log.info("Placed {} organization(s) in the authorization graph", placed);
                  }
              })
              // the reconciler writes the system model shortly after the servers start, so a store that runs
              // none yet, like one that cannot be reached, is asked again
              .onFailure(e -> {
                  log.debug("The organization graph sweep waits: {}", e.getMessage());
                  vertx.setTimer(RETRY_MILLIS, t -> run());
              });
    }

    private Future<Integer> page(String cursor, int placed) {
        return organizations.findAll(Pageable.create(cursor, PAGE_SIZE, Sort.by("id")))
                            .compose(page -> {
                                Future<Integer> ret = Future.succeededFuture(placed);
                                for (Organization organization : page.getContent()) {
                                    ret = ret.compose(count -> place(organization).map(did -> did ? count + 1 : count));
                                }
                                String next = nextCursor(page);
                                return next == null ? ret : ret.compose(count -> page(next, count));
                            });
    }

    private static String nextCursor(Page<Organization> page) {
        return page instanceof CursorPage<Organization> cursorPage && !page.getContent().isEmpty() ? cursorPage.getCursor() : null;
    }

    private Future<Boolean> place(Organization organization) {
        String object = AuthzUtil.object(AuthzConstants.ORGANIZATION_TYPE, organization.getId());
        return administered(object).compose(administered -> administered
                ? Future.succeededFuture(false)
                : relationships.ensure(SYSTEM, List.of(DomainUtil.containment(AuthzUtil.object(AuthzConstants.SYSTEM_TYPE, AuthzConstants.SYSTEM_OBJECT_ID),
                                                                                object)))
                               .compose(v -> members(organization.getId(), 0))
                               .compose(v -> organization.getCreatedBy() == null
                                       ? Future.succeededFuture()
                                       : relationships.bind(SYSTEM,
                                                            AuthzConstants.ORGANIZATION_ADMIN_ROLE,
                                                            DomainUtil.authzMember(organization.getCreatedBy(), organization.getId()),
                                                            object).mapEmpty())
                               .map(true));
    }

    private Future<Void> members(String organizationId, int pageNumber) {
        return memberships.findMembers(organizationId, Pageable.create(pageNumber, PAGE_SIZE, null)).compose(page -> {
            List<RelationshipTuple> enabled = page.getContent().stream()
                                                  .filter(OrganizationMember::enabled)
                                                  .map(member -> new RelationshipTuple(DomainUtil.authzMember(member.id(), organizationId),
                                                                                       AuthzConstants.MEMBER_RELATION,
                                                                                       AuthzUtil.object(AuthzConstants.ORGANIZATION_TYPE, organizationId)))
                                                  .toList();
            Future<Void> ret = relationships.ensure(SYSTEM, enabled);
            return page.getContent().size() < PAGE_SIZE ? ret : ret.compose(v -> members(organizationId, pageNumber + 1));
        });
    }

    private Future<Boolean> administered(String object) {
        return relationships.findGrants(SYSTEM, object)
                            .map(grants -> grants.stream().anyMatch(grant -> AuthzConstants.ORGANIZATION_ADMIN_ROLE.equals(grant.roleId())));
    }
}
