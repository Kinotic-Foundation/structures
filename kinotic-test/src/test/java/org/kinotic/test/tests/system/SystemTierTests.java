package org.kinotic.test.tests.system;

import io.vertx.core.Future;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kinotic.authz.api.model.AccessExplanation;
import org.kinotic.authz.api.model.AuthzModel;
import org.kinotic.authz.api.model.Grant;
import org.kinotic.authz.api.model.RoleDefinition;
import org.kinotic.authz.api.model.Subject;
import org.kinotic.authz.api.model.SubjectType;
import org.kinotic.authz.api.services.AuthzModelGenerator;
import org.kinotic.authz.api.services.AuthzStoreService;
import org.kinotic.authz.api.services.RelationshipService;
import org.kinotic.core.api.directory.ServiceDirectory;
import org.kinotic.core.api.exceptions.AuthorizationException;
import org.kinotic.core.api.security.Participant;
import org.kinotic.core.api.security.ParticipantConstants;
import org.kinotic.domain.api.model.AuthzModelRevision;
import org.kinotic.domain.api.model.AuthzStore;
import org.kinotic.domain.api.model.Organization;
import org.kinotic.domain.api.model.ReconcileState;
import org.kinotic.domain.api.model.security.identity.MachineType;
import org.kinotic.domain.api.model.security.identity.MachineParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.OrganizationParticipantIdentity;
import org.kinotic.domain.api.model.security.identity.SystemParticipantIdentity;
import org.kinotic.domain.api.model.security.participant.DefaultSystemParticipant;
import org.kinotic.domain.api.repositories.AuthzStoreRepository;
import org.kinotic.domain.api.services.OrganizationService;
import org.kinotic.domain.api.services.security.ParticipantIdentityService;
import org.kinotic.idl.api.utils.AuthzUtil;
import org.kinotic.management.api.model.telemetry.ServerLogQuery;
import org.kinotic.management.api.services.telemetry.LogService;
import org.kinotic.system.api.model.workload.VmNodeRegistration;
import org.kinotic.system.api.services.SystemAccessService;
import org.kinotic.system.api.services.workload.VmNodeOrchestrationService;
import org.kinotic.system.api.services.workload.VmNodeService;
import org.kinotic.test.support.kinotic.KinoticTestBase;
import org.kinotic.test.support.system.NodeFixtures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.kinotic.authz.api.services.AuthzStoreService.SYSTEM;

/**
 * Verifies the system tier is authorized as the gateway authorizes it, against the system store: the
 * system's seeded administrator and vm-manager are bound once; a support system user reads every organization,
 * the cluster and the servers' logs and is refused the system's writes; a maintainer manages machines and
 * workloads and is refused a node's reports; a machine granted the registrar role registers a node, reports
 * for that node alone, and loses its agent grant when the node is deregistered; a system grant names only
 * the system's own staff; and the telemetry tenant a system participant may read follows its grant.
 */
@SpringBootTest
public class SystemTierTests extends KinoticTestBase {

    private static final String ORGANIZATION_SERVICE = "system-api~org.kinotic.system.api.services.SystemOrganizationService";
    private static final String MEMBER_SERVICE = "system-api~org.kinotic.system.api.services.SystemMemberService";
    private static final String CLUSTER_SERVICE = "system-api~org.kinotic.system.api.services.KinoticClusterInfoService";
    private static final String ACCESS_SERVICE = "system-api~org.kinotic.system.api.services.SystemAccessService";
    private static final String NODE_SERVICE = "system-api~org.kinotic.system.api.services.workload.VmNodeOrchestrationService";
    private static final String NODE_RECORD_SERVICE = "system-api~org.kinotic.system.api.services.workload.VmNodeService";
    private static final String WORKLOAD_SERVICE = "system-api~org.kinotic.system.api.services.workload.WorkloadOrchestrationService";
    private static final String LOG_SERVICE = "management-api~org.kinotic.management.api.services.telemetry.LogService";
    // the system user and the vm-manager V2__kinotic_test_users seeds
    private static final String SEEDED_ADMIN_ID = "00000000-0000-0000-0000-000000000001";
    private static final String SEEDED_VM_MANAGER_ID = "00000000-0000-0000-0000-000000000011";
    private static final Map<String, Object> PAGE = Map.of("pageNumber", 0, "pageSize", 10);

    @Autowired
    private SystemAccessService access;

    @Autowired
    private ParticipantIdentityService identityService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private VmNodeOrchestrationService nodeOrchestration;

    @Autowired
    private VmNodeService vmNodeService;

    @Autowired
    private LogService logService;

    @Autowired
    private RelationshipService relationships;

    @Autowired
    private AuthzStoreService storeService;

    @Autowired
    private AuthzStoreRepository stores;

    @Autowired
    private AuthzModelGenerator modelGenerator;

    @Autowired
    private ServiceDirectory serviceDirectory;

    private final List<String> nodes = new ArrayList<>();

    @BeforeEach
    public void awaitSystemModel() throws Exception {
        AuthzModel model = modelGenerator.systemModel(await(serviceDirectory.findSystemDefinitions()));
        assertTrue(awaitUntil(() -> reconciledTo(model.hash())), "the system store never reconciled to the directory's model");
    }

    @AfterEach
    public void removeNodes() throws Exception {
        for (String nodeId : nodes) {
            if (await(vmNodeService.findById(nodeId)) != null) {
                await(vmNodeService.deleteById(nodeId));
            }
        }
        nodes.clear();
    }

    @Test
    public void theSeededStaffAreBoundOnce() throws Exception {
        // the system server sweeps the system once the store runs a model, shortly after the servers start
        assertTrue(awaitUntil(() -> grantsOf(SEEDED_ADMIN_ID, AuthzUtil.SYSTEM_ADMIN_ROLE) == 1), "the seeded system user never administered the system");
        assertTrue(awaitUntil(() -> grantsOf(SEEDED_VM_MANAGER_ID, VmNodeOrchestrationService.REGISTRAR_ROLE) == 1), "the seeded vm-manager never registered nodes");

        // the administrator holds everything on the system and inside it
        Participant admin = userParticipant(SEEDED_ADMIN_ID);
        authorize(MEMBER_SERVICE, "createMachine", admin, List.of("a machine"));
        authorize(ACCESS_SERVICE, "grant", admin, List.of(new Subject(SubjectType.USER, SEEDED_ADMIN_ID), AuthzUtil.SYSTEM_SUPPORT_ROLE));
        authorize(ORGANIZATION_SERVICE, "findMembers", admin, Arrays.asList(organization().getId(), null, PAGE));
        authorize(NODE_SERVICE, "registerNode", admin, List.of(NodeFixtures.registration("any-node", 1, 1024, 1024)));
    }

    @Test
    public void supportReadsTheSystemAndIsRefusedItsWrites() throws Exception {
        SystemParticipantIdentity support = systemUser("system-user");
        Subject subject = new Subject(SubjectType.USER, support.getId());
        Grant grant = await(access.grant(subject, AuthzUtil.SYSTEM_SUPPORT_ROLE));
        Organization organization = organization();
        Participant caller = userParticipant(support.getId());

        assertTrue(awaitUntil(() -> admitted(ORGANIZATION_SERVICE, "findOrganizations", caller, List.of(PAGE))), "support never saw the organizations");
        // a read of one organization is checked on it, which the system grant reaches through the graph
        authorize(ORGANIZATION_SERVICE, "findApplications", caller, List.of(organization.getId(), PAGE));
        authorize(ORGANIZATION_SERVICE, "findMembers", caller, Arrays.asList(organization.getId(), null, PAGE));
        authorize(CLUSTER_SERVICE, "getClusterInfo", caller, List.of());
        // a check on the caller's organization, which a system user has none of, is made on the system
        authorize(LOG_SERVICE, "serverHistory", caller, List.of(Map.of("telemetryServiceName", "kinotic-test")));
        assertRefused(MEMBER_SERVICE, "createMachine", caller, List.of("a machine"), "system_can_manage_machines");
        assertRefused(WORKLOAD_SERVICE, "stopWorkload", caller, List.of("wl-1"), "system_can_manage_workloads");
        assertRefused(ACCESS_SERVICE, "grant", caller, List.of(subject, AuthzUtil.SYSTEM_SUPPORT_ROLE), "system_can_manage_access");

        AccessExplanation explanation = await(access.explain(subject, "can_read_cluster"));
        assertTrue(explanation.allowed());
        assertEquals(List.of(grant.id()), explanation.through().stream().map(Grant::id).toList());
        assertFalse(await(access.explain(subject, "can_manage_machines")).allowed());
        assertTrue(await(access.findGrants()).contains(grant));

        await(access.revoke(grant.id()));
        assertTrue(awaitUntil(() -> !admitted(CLUSTER_SERVICE, "getClusterInfo", caller, List.of())), "support still read the cluster after its revocation");
        assertFalse(await(access.findGrants()).contains(grant));
    }

    @Test
    public void aMaintainerRunsTheSystemAndIsRefusedANodesReports() throws Exception {
        SystemParticipantIdentity maintainer = systemUser("system-user");
        await(access.grant(new Subject(SubjectType.USER, maintainer.getId()), AuthzUtil.SYSTEM_MAINTAINER_ROLE));
        Participant caller = userParticipant(maintainer.getId());

        assertTrue(awaitUntil(() -> admitted(WORKLOAD_SERVICE, "stopWorkload", caller, List.of("wl-1"))), "the maintainer never managed workloads");
        authorize(MEMBER_SERVICE, "createMachine", caller, List.of("a machine"));
        authorize(MEMBER_SERVICE, "rotateSecret", caller, List.of(SEEDED_VM_MANAGER_ID));
        authorize(ORGANIZATION_SERVICE, "findOrganizations", caller, List.of(PAGE));
        // a listing of nodes with room is a read of nodes, checked on the system
        authorize(NODE_SERVICE, "findAvailableNode", caller, List.of(1, 1024, 1024));
        assertRefused(NODE_SERVICE, "registerNode", caller, List.of(NodeFixtures.registration("any-node", 1, 1024, 1024)), "vm_node_can_register_node");
    }

    @Test
    public void aRegistrarRegistersANodeAndReportsForItAlone() throws Exception {
        MachineParticipantIdentity registrar = machine("tier registrar");
        MachineParticipantIdentity other = machine("tier bystander");
        SystemParticipantIdentity maintainer = systemUser("system-user");
        await(access.grant(new Subject(SubjectType.USER, registrar.getId()), VmNodeOrchestrationService.REGISTRAR_ROLE));
        await(access.grant(new Subject(SubjectType.USER, maintainer.getId()), AuthzUtil.SYSTEM_MAINTAINER_ROLE));
        Participant registrarCaller = machineParticipant(registrar.getId());
        Participant otherCaller = machineParticipant(other.getId());
        VmNodeRegistration node = NodeFixtures.registration("tier-" + suffix(), 4, 4096, 10240);
        nodes.add(node.getId());
        String nodeObject = AuthzUtil.object(VmNodeService.RESOURCE_TYPE, node.getId());

        assertTrue(awaitUntil(() -> admitted(NODE_SERVICE, "registerNode", registrarCaller, List.of(node))), "the registrar never registered");
        assertRefused(NODE_SERVICE, "registerNode", otherCaller, List.of(node), "vm_node_can_register_node");
        // before the registration nothing reports for the node, the registrar included
        assertRefused(NODE_SERVICE, "heartbeat", registrarCaller, List.of(node.getId(), List.of()), "vm_node_can_report");

        // registered by the machine, the node is on the system and the machine is its agent
        await(runAs(registrarCaller, () -> nodeOrchestration.registerNode(node)));
        assertTrue(awaitUntil(() -> admitted(NODE_SERVICE, "heartbeat", registrarCaller, List.of(node.getId(), List.of()))), "the agent never reported");
        authorize(NODE_SERVICE, "reportWorkloadStatus", registrarCaller, List.of(node.getId(), List.of()));
        authorize(NODE_SERVICE, "deregisterNode", registrarCaller, List.of(node.getId()));
        assertRefused(NODE_SERVICE, "heartbeat", registrarCaller, List.of("tier-other-" + suffix(), List.of()), "vm_node_can_report");
        assertRefused(NODE_SERVICE, "heartbeat", otherCaller, List.of(node.getId(), List.of()), "vm_node_can_report");
        // the system's administrator reaches the node through the system, a maintainer reads it and reports nothing
        assertTrue(awaitUntil(() -> admitted(NODE_SERVICE, "heartbeat", userParticipant(SEEDED_ADMIN_ID), List.of(node.getId(), List.of()))), "the administrator never reached the node");
        assertTrue(awaitUntil(() -> admitted(NODE_RECORD_SERVICE, "findHistory", userParticipant(maintainer.getId()), List.of(node.getId(), PAGE))), "the maintainer never read the node");
        assertRefused(NODE_SERVICE, "heartbeat", userParticipant(maintainer.getId()), List.of(node.getId(), List.of()), "vm_node_can_report");
        List<Grant> grants = await(relationships.findGrants(SYSTEM, nodeObject));
        assertEquals(1, grants.size());
        assertEquals(VmNodeOrchestrationService.AGENT_ROLE, grants.getFirst().roleId());
        assertEquals(new Subject(SubjectType.USER, registrar.getId()), grants.getFirst().subject());

        // a node registering again binds nothing twice
        await(runAs(registrarCaller, () -> nodeOrchestration.registerNode(node)));
        assertEquals(grants, await(relationships.findGrants(SYSTEM, nodeObject)));

        // the deregistration's finalizer takes the node out of the graph with its agent's grant
        await(nodeOrchestration.deregisterNode(node.getId()));
        assertTrue(awaitUntil(() -> await(vmNodeService.findById(node.getId())) == null), "the node was never finalized");
        assertTrue(awaitUntil(() -> await(relationships.read(SYSTEM, nodeObject)).isEmpty()), "the node is still in the graph");
        assertTrue(await(relationships.read(SYSTEM, AuthzUtil.bindingObject(nodeObject, grants.getFirst().id()))).isEmpty());
    }

    @Test
    public void aSystemGrantNamesOnlyTheSystemsStaff() throws Exception {
        OrganizationParticipantIdentity member = member(TEST_ORG_ID, "member");
        SystemParticipantIdentity user = systemUser("system-user");

        assertInstanceOf(IllegalArgumentException.class,
                         failure(() -> access.grant(new Subject(SubjectType.USER, member.getId()), AuthzUtil.SYSTEM_SUPPORT_ROLE)));
        assertInstanceOf(IllegalArgumentException.class,
                         failure(() -> access.grant(new Subject(SubjectType.GROUP, user.getId()), AuthzUtil.SYSTEM_SUPPORT_ROLE)));
        assertInstanceOf(IllegalArgumentException.class,
                         failure(() -> access.grant(new Subject(SubjectType.USER, user.getId()), "project.editor-of-nothing")));
        assertInstanceOf(IllegalArgumentException.class, failure(() -> access.revoke("no-such-grant")));
        // the roles a system grant can name are the system's own and those of the types on it, none of an
        // organization's or a tenant's
        List<RoleDefinition> defined = await(access.findRoles());
        List<String> roles = defined.stream().map(RoleDefinition::id).toList();
        assertTrue(roles.containsAll(List.of(AuthzUtil.SYSTEM_ADMIN_ROLE, AuthzUtil.SYSTEM_MAINTAINER_ROLE, AuthzUtil.SYSTEM_SUPPORT_ROLE,
                                             VmNodeOrchestrationService.REGISTRAR_ROLE, VmNodeOrchestrationService.AGENT_ROLE)), roles.toString());
        Set<String> organizationTree = Set.of(AuthzUtil.ORGANIZATION_TYPE, AuthzUtil.APPLICATION_TYPE, AuthzUtil.PROJECT_TYPE,
                                              AuthzUtil.ENTITY_DEFINITION_TYPE, AuthzUtil.TENANT_TYPE);
        assertTrue(defined.stream().noneMatch(role -> organizationTree.contains(role.resourceType())), roles.toString());
        assertTrue(defined.stream().allMatch(role -> role.grantableOn().contains(AuthzUtil.SYSTEM_TYPE)), roles.toString());
        assertInstanceOf(IllegalArgumentException.class,
                         failure(() -> access.grant(new Subject(SubjectType.USER, user.getId()), AuthzUtil.ORGANIZATION_ADMIN_ROLE)));
    }

    @Test
    public void theTelemetryTenantFollowsTheSystemGrant() throws Exception {
        SystemParticipantIdentity newcomer = systemUser("system-user");
        SystemParticipantIdentity support = systemUser("system-user");
        await(access.grant(new Subject(SubjectType.USER, support.getId()), AuthzUtil.SYSTEM_SUPPORT_ROLE));
        ServerLogQuery query = new ServerLogQuery().setTelemetryServiceName("kinotic-test").setStart(0).setEnd(1).setLimit(1);

        assertInstanceOf(AuthorizationException.class, failure(() -> runAs(userParticipant(newcomer.getId()), () -> logService.serverHistory(query))));
        assertInstanceOf(AuthorizationException.class, failure(() -> runAsOrganization(() -> logService.serverHistory(query))));
        // the system's tenant is read for support; whether the log store answers is not the tenant's to decide
        assertTrue(awaitUntil(() -> !(failure(() -> runAs(userParticipant(support.getId()), () -> logService.serverHistory(query))) instanceof AuthorizationException)),
                   "support was never admitted to the system's logs");
    }

    // How many grants of the role the system holds for the subject
    private long grantsOf(String subjectId, String roleId) throws Exception {
        Subject subject = new Subject(SubjectType.USER, subjectId);
        return await(access.findGrants()).stream().filter(grant -> roleId.equals(grant.roleId()) && subject.equals(grant.subject())).count();
    }

    private static Throwable failure(java.util.function.Supplier<Future<?>> call) {
        Throwable ret;
        try {
            await(call.get());
            ret = null;
        } catch (ExecutionException e) {
            ret = e.getCause();
        } catch (Exception e) {
            ret = e;
        }
        return ret;
    }

    private static Participant userParticipant(String id) {
        return new DefaultSystemParticipant(id, Map.of(ParticipantConstants.PARTICIPANT_TYPE_METADATA_KEY, ParticipantConstants.PARTICIPANT_TYPE_USER), List.of());
    }

    private static Participant machineParticipant(String id) {
        return new DefaultSystemParticipant(id, Map.of(ParticipantConstants.PARTICIPANT_TYPE_METADATA_KEY, ParticipantConstants.PARTICIPANT_TYPE_MACHINE), List.of());
    }



    // A system machine is a machine with no organization, as the console provisions one
    private MachineParticipantIdentity machine(String displayName) throws Exception {
        MachineParticipantIdentity machine = new MachineParticipantIdentity();
        machine.setMachineType(MachineType.CLIENT).setDisplayName(displayName);
        return await(identityService.createMachine(machine)).machine();
    }

    private Organization organization() throws Exception {
        Organization organization = new Organization();
        organization.setName("Tier " + suffix()).setDescription("system tier");
        return await(organizationService.createSync(organization));
    }

    private boolean reconciledTo(String hash) throws Exception {
        AuthzStore store = await(stores.findById(AuthzStore.SYSTEM));
        ReconcileState<AuthzModelRevision> state = store.getState();
        return state.isReconciled() && state.getObserved() != null && hash.equals(state.getObserved().hash());
    }
}
