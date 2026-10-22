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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.kinotic.authz.api.services.AuthzStoreService.SYSTEM;

/**
 * Places every organization that is not yet on the system, or holds no grant though it has a creator, in the
 * authorization graph, once the server is up and the system store runs a model: the organization on the system,
 * its enabled members as its members, and the membership of its creator as its administrator. A sign-up does the
 * same for the organization it creates; an organization a migration seeds has records but no tuples, and this
 * gives it the ones a sign-up would have. An organization on the system holding a grant, whatever was granted or
 * revoked since, is left as it is.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrganizationGraphBootstrap {

    private static final long RETRY_MILLIS = 2_000;
    // an organization that could not be placed is tried again by a later sweep, after a delay that doubles with each sweep
    // that leaves one, since a model the system store runs at startup may lack the types the sweep writes
    private static final long MAX_RETRY_MILLIS = 60_000;
    private static final int PAGE_SIZE = 100;

    private final Vertx vertx;
    private final OrganizationService organizations;
    private final OrganizationMembershipService memberships;
    private final AuthzStoreService stores;
    private final RelationshipService relationships;

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        vertx.runOnContext(v -> run(RETRY_MILLIS));
    }

    private void run(long failedDelay) {
        AtomicInteger failed = new AtomicInteger();
        stores.modelId(SYSTEM)
              .compose(modelId -> page(null, 0, failed))
              .onSuccess(placed -> {
                  if (placed > 0) {
                      log.info("Placed {} organization(s) in the authorization graph", placed);
                  }
                  if (failed.get() > 0) {
                      log.warn("{} organization(s) could not be placed in the authorization graph; the sweep is made again", failed.get());
                      vertx.setTimer(failedDelay, t -> run(Math.min(failedDelay * 2, MAX_RETRY_MILLIS)));
                  }
              })
              // the reconciler writes the system model shortly after the servers start, so a store that runs
              // none yet, like one that cannot be reached, is asked again
              .onFailure(e -> {
                  log.debug("The organization graph sweep waits: {}", e.getMessage());
                  vertx.setTimer(RETRY_MILLIS, t -> run(failedDelay));
              });
    }

    private Future<Integer> page(String cursor, int placed, AtomicInteger failed) {
        return organizations.findAll(Pageable.create(cursor, PAGE_SIZE, Sort.by("id")))
                            .compose(page -> {
                                Future<Integer> ret = Future.succeededFuture(placed);
                                for (Organization organization : page.getContent()) {
                                    // one organization that fails leaves the others to be placed
                                    ret = ret.compose(count -> place(organization).map(did -> did ? count + 1 : count)
                                                                                  .recover(e -> {
                                                                                      log.warn("Organization {} could not be placed in the authorization graph: {}",
                                                                                               organization.getId(), e.getMessage());
                                                                                      failed.incrementAndGet();
                                                                                      return Future.succeededFuture(count);
                                                                                  }));
                                }
                                String next = nextCursor(page);
                                return next == null ? ret : ret.compose(count -> page(next, count, failed));
                            });
    }

    private static String nextCursor(Page<Organization> page) {
        return page instanceof CursorPage<Organization> cursorPage && !page.getContent().isEmpty() ? cursorPage.getCursor() : null;
    }

    // An organization is placed when it is not on the system, or when its creator's sign-up stopped before binding
    // it, leaving the organization with no grant
    private Future<Boolean> place(Organization organization) {
        String object = AuthzUtil.object(AuthzConstants.ORGANIZATION_TYPE, organization.getId());
        RelationshipTuple containment = DomainUtil.containment(AuthzUtil.object(AuthzConstants.SYSTEM_TYPE, AuthzConstants.SYSTEM_OBJECT_ID), object);
        return relationships.holds(SYSTEM, containment)
                            .compose(held -> held && organization.getCreatedBy() != null
                                    ? relationships.findGrants(SYSTEM, object).map(grants -> !grants.isEmpty())
                                    : Future.succeededFuture(held))
                            .compose(done -> done ? Future.succeededFuture(false) : placed(organization, object, containment));
    }

    // The containment is written last, so a sweep cut short places the organization again, and the creator's
    // membership is bound under an id of its own, so every server sweeping at once binds it once
    private Future<Boolean> placed(Organization organization, String object, RelationshipTuple containment) {
        Set<String> enabled = new HashSet<>();
        return members(organization.getId(), 0, enabled)
                .compose(v -> {
                    Future<Void> ret = Future.succeededFuture();
                    // a creator who has since left the organization is not made its administrator
                    if (organization.getCreatedBy() != null && enabled.contains(organization.getCreatedBy())) {
                        String creator = DomainUtil.authzMember(organization.getCreatedBy(), organization.getId());
                        ret = relationships.ensureBound(SYSTEM,
                                                        AuthzConstants.ORGANIZATION_ADMIN_ROLE + "-of-" + AuthzUtil.idOf(creator),
                                                        AuthzConstants.ORGANIZATION_ADMIN_ROLE,
                                                        creator,
                                                        object);
                    }
                    return ret;
                })
                .compose(v -> relationships.ensure(SYSTEM, List.of(containment)))
                .map(true);
    }

    // Makes the enabled members the organization's members, collecting their ids
    private Future<Void> members(String organizationId, int pageNumber, Set<String> enabled) {
        return memberships.findMembers(organizationId, Pageable.create(pageNumber, PAGE_SIZE, null)).compose(page -> {
            List<RelationshipTuple> tuples = new ArrayList<>();
            for (OrganizationMember member : page.getContent()) {
                if (member.enabled()) {
                    enabled.add(member.id());
                    tuples.add(new RelationshipTuple(DomainUtil.authzMember(member.id(), organizationId),
                                                     AuthzConstants.MEMBER_RELATION,
                                                     AuthzUtil.object(AuthzConstants.ORGANIZATION_TYPE, organizationId)));
                }
            }
            Future<Void> ret = relationships.ensure(SYSTEM, tuples);
            return page.getContent().size() < PAGE_SIZE ? ret : ret.compose(v -> members(organizationId, pageNumber + 1, enabled));
        });
    }
}
