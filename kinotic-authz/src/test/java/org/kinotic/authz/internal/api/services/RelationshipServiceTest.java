package org.kinotic.authz.internal.api.services;

import dev.openfga.sdk.api.OpenFgaApi;
import dev.openfga.sdk.api.configuration.Configuration;
import dev.openfga.sdk.api.model.CreateStoreRequest;
import io.vertx.core.Future;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kinotic.authz.KinoticAuthzLibrary;
import org.kinotic.authz.api.config.KinoticAuthzProperties;
import org.kinotic.authz.api.model.AuthzObject;
import org.kinotic.authz.api.model.AuthzRelation;
import org.kinotic.authz.api.model.AuthzType;
import org.kinotic.authz.api.model.RelationshipTuple;
import org.kinotic.authz.api.services.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.kinotic.authz.api.model.AuthzRelation.ADMIN;
import static org.kinotic.authz.api.model.AuthzRelation.CAN_DELETE;
import static org.kinotic.authz.api.model.AuthzRelation.CAN_EDIT;
import static org.kinotic.authz.api.model.AuthzRelation.CAN_READ;
import static org.kinotic.authz.api.model.AuthzRelation.CAN_REGISTER_SERVICES;
import static org.kinotic.authz.api.model.AuthzRelation.EDITOR;
import static org.kinotic.authz.api.model.AuthzRelation.MEMBER;
import static org.kinotic.authz.api.model.AuthzRelation.RUNTIME;

/**
 * Exercises the platform store and the relationships against a real OpenFGA: the platform model written once
 * and reused by every node, relationships written in batches and idempotently, and checks and listings
 * answering through the model. OpenFGA runs from the image the compose stack declares when Docker is available, else at the URL in the
 * {@code OPENFGA_URL} environment variable, and the test is skipped when there is neither.
 */
@SpringBootTest(classes = KinoticAuthzLibrary.class)
class RelationshipServiceTest {

    private static GenericContainer<?> openfga;
    private static String apiUrl;

    @Autowired
    private RelationshipService relationships;
    @Autowired
    private PlatformAuthzStore store;
    @Autowired
    private OpenFgaService fga;
    @Autowired
    private KinoticAuthzProperties properties;

    @BeforeAll
    static void startEngine() throws Exception {
        apiUrl = System.getenv("OPENFGA_URL");
        if (apiUrl == null) {
            Assumptions.assumeTrue(DockerClientFactory.instance().isDockerAvailable(),
                                   "Docker or OPENFGA_URL is required for the OpenFGA integration test");
            openfga = new GenericContainer<>(openFgaImageFromCompose())
                    .withExposedPorts(8080)
                    .withCommand("run")
                    .waitingFor(Wait.forHttp("/healthz").forPort(8080).withStartupTimeout(Duration.ofMinutes(2)));
            openfga.start();
            apiUrl = "http://" + openfga.getHost() + ":" + openfga.getMappedPort(8080);
        }
        createPlatformStoreAheadOfTheServers();
    }

    @DynamicPropertySource
    static void engineUrl(DynamicPropertyRegistry registry) {
        registry.add("kinotic.authz.apiUrl", () -> apiUrl);
    }

    @AfterAll
    static void stopEngine() {
        if (openfga != null) {
            openfga.stop();
        }
    }

    @Test
    void everyNodeRunsOneVersionOfThePlatformModel() throws Exception {
        String modelId = await(store.modelId());

        // a second node compares the store's version with its own model and writes nothing
        assertEquals(modelId, await(new PlatformAuthzStore(fga, properties).modelId()));
        String storeId = await(store.storeId());
        assertEquals(1, await(fga.readAuthorizationModels(storeId, 100, null)).getAuthorizationModels().size());
    }

    @Test
    void permissionsFollowTheContainmentTree() throws Exception {
        Tree tree = new Tree();
        AuthzObject staff = membership();
        AuthzObject founder = membership();
        AuthzObject member = membership();
        AuthzObject runtime = machine();
        AuthzObject sync = machine();
        await(relationships.write(List.of(tuple(staff, ADMIN, tree.platform),
                                          tuple(founder, ADMIN, tree.organization),
                                          tuple(member, MEMBER, tree.organization),
                                          tuple(runtime, RUNTIME, tree.application),
                                          tuple(sync, EDITOR, tree.project)),
                                  List.of()));

        assertTrue(await(relationships.check(staff, CAN_DELETE, tree.project)));
        assertTrue(await(relationships.check(founder, CAN_DELETE, tree.application)));
        assertTrue(await(relationships.check(founder, CAN_REGISTER_SERVICES, tree.application)));
        assertTrue(await(relationships.check(member, CAN_READ, tree.project)));
        assertFalse(await(relationships.check(member, CAN_EDIT, tree.application)));
        assertFalse(await(relationships.check(member, CAN_REGISTER_SERVICES, tree.application)));
        assertTrue(await(relationships.check(runtime, CAN_REGISTER_SERVICES, tree.application)));
        assertFalse(await(relationships.check(runtime, CAN_READ, tree.application)));
        assertTrue(await(relationships.check(sync, CAN_EDIT, tree.project)));
        assertFalse(await(relationships.check(sync, CAN_DELETE, tree.project)));
    }

    @Test
    void aMembershipGrantsNothingInAnotherOrganization() throws Exception {
        Tree mine = new Tree();
        Tree other = new Tree();
        String account = UUID.randomUUID().toString();
        AuthzObject inMine = new AuthzObject(AuthzType.MEMBERSHIP, account + "@" + mine.organization.id());
        AuthzObject inOther = new AuthzObject(AuthzType.MEMBERSHIP, account + "@" + other.organization.id());
        await(relationships.write(List.of(tuple(inMine, ADMIN, mine.organization),
                                          tuple(inOther, MEMBER, other.organization)),
                                  List.of()));

        assertTrue(await(relationships.check(inMine, CAN_DELETE, mine.application)));
        assertFalse(await(relationships.check(inMine, CAN_READ, other.application)));
        assertFalse(await(relationships.check(inOther, CAN_EDIT, other.application)));
    }

    @Test
    void writesAndDeletesAreIdempotent() throws Exception {
        Tree tree = new Tree();
        RelationshipTuple grant = tuple(membership(), ADMIN, tree.organization);

        await(relationships.write(List.of(grant), List.of()));
        await(relationships.write(List.of(grant), List.of()));
        assertTrue(await(relationships.check(grant.user(), CAN_EDIT, tree.application)));

        await(relationships.write(List.of(), List.of(grant)));
        await(relationships.write(List.of(), List.of(grant)));
        assertFalse(await(relationships.check(grant.user(), CAN_EDIT, tree.application)));
    }

    @Test
    void aWriteLargerThanOneRequestIsWrittenWhole() throws Exception {
        Tree tree = new Tree();
        List<RelationshipTuple> members = new ArrayList<>();
        for (int i = 0; i < OpenFgaService.WRITE_BATCH_SIZE * 2 + 1; i++) {
            members.add(tuple(membership(), MEMBER, tree.organization));
        }

        await(relationships.write(members, List.of()));

        assertTrue(await(relationships.check(members.getFirst().user(), CAN_READ, tree.project)));
        assertTrue(await(relationships.check(members.getLast().user(), CAN_READ, tree.project)));
    }

    @Test
    void theObjectsAUserHoldsAPermissionOnAreListed() throws Exception {
        Tree hosted = new Tree();
        Tree other = new Tree();
        AuthzObject runtime = machine();
        await(relationships.write(List.of(tuple(runtime, RUNTIME, hosted.application)), List.of()));

        assertEquals(List.of(hosted.application), await(relationships.listObjects(runtime, CAN_REGISTER_SERVICES, AuthzType.APPLICATION)));
        assertEquals(List.of(), await(relationships.listObjects(machine(), CAN_REGISTER_SERVICES, AuthzType.APPLICATION)));
        assertFalse(await(relationships.check(runtime, CAN_REGISTER_SERVICES, other.application)));
    }

    @Test
    void anIdTheEngineCannotHoldIsRefused() {
        assertThrows(IllegalArgumentException.class, () -> new AuthzObject(AuthzType.MEMBERSHIP, "account:organization"));
        assertThrows(IllegalArgumentException.class, () -> new AuthzObject(AuthzType.PROJECT, "a project"));
    }

    // An organization with one application holding one project, under the platform
    private final class Tree {
        final AuthzObject platform = new AuthzObject(AuthzType.PLATFORM, "kinotic");
        final AuthzObject organization = new AuthzObject(AuthzType.ORGANIZATION, UUID.randomUUID().toString());
        final AuthzObject application = new AuthzObject(AuthzType.APPLICATION, UUID.randomUUID().toString());
        final AuthzObject project = new AuthzObject(AuthzType.PROJECT, UUID.randomUUID().toString());

        Tree() throws Exception {
            await(relationships.write(List.of(tuple(platform, AuthzRelation.PLATFORM, organization),
                                              tuple(organization, AuthzRelation.ORGANIZATION, application),
                                              tuple(application, AuthzRelation.APPLICATION, project)),
                                      List.of()));
        }
    }

    private static AuthzObject membership() {
        return new AuthzObject(AuthzType.MEMBERSHIP, UUID.randomUUID().toString());
    }

    private static AuthzObject machine() {
        return new AuthzObject(AuthzType.MACHINE, UUID.randomUUID().toString());
    }

    private static RelationshipTuple tuple(AuthzObject user, AuthzRelation relation, AuthzObject object) {
        return new RelationshipTuple(user, relation, object);
    }

    /**
     * The role openfga-init plays before a server starts: the one store of the platform's name.
     */
    private static void createPlatformStoreAheadOfTheServers() throws Exception {
        OpenFgaApi api = new OpenFgaApi(new Configuration().apiUrl(apiUrl));
        boolean exists = api.listStores(100, null, PlatformAuthzStore.STORE_NAME).get().getData()
                            .getStores().stream()
                            .anyMatch(store -> PlatformAuthzStore.STORE_NAME.equals(store.getName()));
        if (!exists) {
            api.createStore(new CreateStoreRequest().name(PlatformAuthzStore.STORE_NAME)).get();
        }
    }

    // The engine image the compose stack runs, so the test and the stack upgrade together
    private static String openFgaImageFromCompose() throws IOException {
        String compose = Files.readString(Path.of("../deployment/docker-compose/compose.openfga.yml"));
        Matcher matcher = Pattern.compile("image:\\s*(openfga/openfga:\\S+)").matcher(compose);
        if (!matcher.find()) {
            throw new IllegalStateException("compose.openfga.yml declares no openfga/openfga image");
        }
        return matcher.group(1);
    }

    private static <T> T await(Future<T> future) throws Exception {
        return future.toCompletionStage().toCompletableFuture().get(30, TimeUnit.SECONDS);
    }
}
