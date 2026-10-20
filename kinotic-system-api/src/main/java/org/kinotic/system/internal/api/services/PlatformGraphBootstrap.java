package org.kinotic.system.internal.api.services;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kinotic.authz.api.services.AuthzStoreService;
import org.kinotic.authz.api.services.RelationshipService;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.domain.api.model.security.Member;
import org.kinotic.domain.api.model.security.identity.MachineParticipantIdentity;
import org.kinotic.domain.api.services.security.MembershipService;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.domain.api.utils.DomainUtil;
import org.kinotic.idl.api.utils.AuthzUtil;
import org.kinotic.system.api.services.workload.VmNodeOrchestrationService;
import org.kinotic.system.api.services.workload.VmNodeService;
import org.kinotic.system.internal.api.repositories.VmNodeRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.kinotic.authz.api.services.AuthzStoreService.PLATFORM;

/**
 * Places the platform's staff and nodes in the authorization graph when the platform has no administrator, once
 * the server is up and the platform store runs a model: every node on the platform, every enabled platform
 * machine as a registrar of nodes, and every enabled platform operator as the platform's administrator. A
 * platform a migration seeds has operators, a vm-manager and nodes but no tuples, and this gives it the ones it
 * needs to be run; a platform with an administrator, whatever else was granted or revoked since, is left as it is.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PlatformGraphBootstrap {

    private static final long RETRY_MILLIS = 2_000;
    // The platform's operators, machines and nodes fit one page: a platform has a handful of each
    private static final int PAGE_SIZE = 1000;
    private static final String PLATFORM_OBJECT = AuthzUtil.object(AuthzUtil.PLATFORM_TYPE, AuthzUtil.PLATFORM_OBJECT_ID);

    private final Vertx vertx;
    private final ParticipantIdentityService identities;
    private final MembershipService memberships;
    private final VmNodeRepository nodes;
    private final AuthzStoreService stores;
    private final RelationshipService relationships;

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        vertx.runOnContext(v -> run());
    }

    private void run() {
        stores.modelId(PLATFORM)
              .compose(modelId -> administered())
              .compose(administered -> administered ? Future.succeededFuture(0) : placeStaff())
              .onSuccess(bound -> {
                  if (bound > 0) {
                      log.info("Bound {} platform operator(s) and machine(s) as the platform's staff", bound);
                  }
              })
              // the reconciler writes the platform model shortly after the servers start, so a store that runs
              // none yet, like one that cannot be reached, is asked again
              .onFailure(e -> {
                  log.debug("The platform staff sweep waits: {}", e.getMessage());
                  vertx.setTimer(RETRY_MILLIS, t -> run());
              });
    }

    private Future<Boolean> administered() {
        return relationships.findGrants(PLATFORM, PLATFORM_OBJECT)
                            .map(grants -> grants.stream().anyMatch(grant -> AuthzUtil.PLATFORM_ADMIN_ROLE.equals(grant.roleId())));
    }

    private Future<Integer> placeStaff() {
        Pageable page = Pageable.create(0, PAGE_SIZE, null);
        // an administrator ends the sweep for every server, so the nodes and machines are placed before it: a sweep
        // cut short before the administrator leaves none, and the next sweep places everything again
        return nodes.findAll(page)
                    .compose(found -> relationships.ensure(PLATFORM, found.getContent().stream()
                                                                         .map(node -> DomainUtil.containment(PLATFORM_OBJECT,
                                                                                                             AuthzUtil.object(VmNodeService.RESOURCE_TYPE, node.getId())))
                                                                         .toList()))
                    .compose(v -> identities.findMachinesByScope(null, null, page))
                    .compose(machines -> bindEach(machines.getContent().stream()
                                                          .filter(MachineParticipantIdentity::isEnabled)
                                                          .map(machine -> AuthzUtil.object(AuthzUtil.USER_TYPE, machine.getId()))
                                                          .toList(),
                                                  VmNodeOrchestrationService.REGISTRAR_ROLE))
                    .compose(bound -> memberships.findMembers(null, page)
                                                 .compose(operators -> bindEach(operators.getContent().stream()
                                                                                         .filter(Member::enabled)
                                                                                         .map(operator -> DomainUtil.authzMember(operator.id(), null))
                                                                                         .toList(),
                                                                                AuthzUtil.PLATFORM_ADMIN_ROLE))
                                                 .map(more -> bound + more));
    }

    // Each user is bound under an id of its own, so every server sweeping at once binds it once
    private Future<Integer> bindEach(List<String> users, String roleId) {
        Future<Integer> ret = Future.succeededFuture(0);
        for (String user : users) {
            String bindingId = roleId + "-of-" + AuthzUtil.idOf(user);
            ret = ret.compose(count -> relationships.ensureBound(PLATFORM, bindingId, roleId, user, PLATFORM_OBJECT).map(count + 1));
        }
        return ret;
    }
}
