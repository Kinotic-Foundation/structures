package org.kinotic.system.internal.api.services;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kinotic.authz.api.services.AuthzStoreService;
import org.kinotic.authz.api.services.RelationshipService;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.domain.api.model.security.identity.SystemParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.MachineParticipantIdentity;
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

import static org.kinotic.authz.api.services.AuthzStoreService.SYSTEM;

/**
 * Places the system's staff and nodes in the authorization graph when the system has no administrator, once
 * the server is up and the system store runs a model: every node on the system, every enabled system
 * machine as a registrar of nodes, and every enabled system user as the system's administrator. A
 * system a migration seeds has system users, a vm-manager and nodes but no tuples, and this gives it the ones it
 * needs to be run; a system with an administrator, whatever else was granted or revoked since, is left as it is.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SystemGraphBootstrap {

    private static final long RETRY_MILLIS = 2_000;
    // The system users, machines and nodes fit one page: a system has a handful of each
    private static final int PAGE_SIZE = 1000;
    private static final String SYSTEM_OBJECT = AuthzUtil.object(AuthzUtil.SYSTEM_TYPE, AuthzUtil.SYSTEM_OBJECT_ID);

    private final Vertx vertx;
    private final ParticipantIdentityService identities;
    private final VmNodeRepository nodes;
    private final AuthzStoreService stores;
    private final RelationshipService relationships;

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        vertx.runOnContext(v -> run());
    }

    private void run() {
        stores.modelId(SYSTEM)
              .compose(modelId -> administered())
              .compose(administered -> administered ? Future.succeededFuture(0) : placeStaff())
              .onSuccess(bound -> {
                  if (bound > 0) {
                      log.info("Bound {} system user(s) and machine(s) as the system's staff", bound);
                  }
              })
              // the reconciler writes the system model shortly after the servers start, so a store that runs
              // none yet, like one that cannot be reached, is asked again
              .onFailure(e -> {
                  log.debug("The system staff sweep waits: {}", e.getMessage());
                  vertx.setTimer(RETRY_MILLIS, t -> run());
              });
    }

    private Future<Boolean> administered() {
        return relationships.findGrants(SYSTEM, SYSTEM_OBJECT)
                            .map(grants -> grants.stream().anyMatch(grant -> AuthzUtil.SYSTEM_ADMIN_ROLE.equals(grant.roleId())));
    }

    private Future<Integer> placeStaff() {
        Pageable page = Pageable.create(0, PAGE_SIZE, null);
        // an administrator ends the sweep for every server, so the nodes and machines are placed before it: a sweep
        // cut short before the administrator leaves none, and the next sweep places everything again
        return nodes.findAll(page)
                    .compose(found -> relationships.ensure(SYSTEM, found.getContent().stream()
                                                                         .map(node -> DomainUtil.containment(SYSTEM_OBJECT,
                                                                                                             AuthzUtil.object(VmNodeService.RESOURCE_TYPE, node.getId())))
                                                                         .toList()))
                    .compose(v -> identities.findMachinesByScope(null, null, page))
                    .compose(machines -> bindEach(machines.getContent().stream()
                                                          .filter(MachineParticipantIdentity::isEnabled)
                                                          .map(machine -> AuthzUtil.object(AuthzUtil.USER_TYPE, machine.getId()))
                                                          .toList(),
                                                  VmNodeOrchestrationService.REGISTRAR_ROLE))
                    .compose(bound -> identities.findSystemUsers(page)
                                                .compose(users -> bindEach(users.getContent().stream()
                                                                                .filter(SystemParticipantIdentity::isEnabled)
                                                                                .map(user -> AuthzUtil.object(AuthzUtil.USER_TYPE, user.getId()))
                                                                                .toList(),
                                                                           AuthzUtil.SYSTEM_ADMIN_ROLE))
                                                .map(more -> bound + more));
    }

    // Each user is bound under an id of its own, so every server sweeping at once binds it once
    private Future<Integer> bindEach(List<String> users, String roleId) {
        Future<Integer> ret = Future.succeededFuture(0);
        for (String user : users) {
            String bindingId = roleId + "-of-" + AuthzUtil.idOf(user);
            ret = ret.compose(count -> relationships.ensureBound(SYSTEM, bindingId, roleId, user, SYSTEM_OBJECT).map(count + 1));
        }
        return ret;
    }
}
