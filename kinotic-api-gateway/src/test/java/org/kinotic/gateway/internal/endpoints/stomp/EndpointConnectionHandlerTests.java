package org.kinotic.gateway.internal.endpoints.stomp;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.sstore.ClusteredSessionStore;
import io.vertx.spi.cluster.ignite.IgniteClusterManager;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kinotic.core.api.config.KinoticProperties;
import org.kinotic.core.api.event.CRI;
import org.kinotic.core.api.event.Event;
import org.kinotic.core.api.event.EventBusService;
import org.kinotic.core.api.event.EventConstants;
import org.kinotic.core.api.event.EventConsumer;
import org.kinotic.core.api.event.Metadata;
import org.kinotic.core.api.security.SecurityService;
import org.kinotic.core.api.service.RequestLivenessWatcher;
import org.kinotic.core.internal.api.service.json.JacksonExceptionConverter;
import org.kinotic.domain.api.model.security.participant.DefaultOrganizationParticipant;
import org.kinotic.gateway.api.config.ApiGatewayProperties;
import org.kinotic.gateway.internal.endpoints.Services;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.ObjectProvider;
import tools.jackson.databind.json.JsonMapper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Pins what a STOMP connection's caller side owes its client: a request whose serving node leaves the
 * cluster is answered on the client's reply destination with the typed error, a reply that settles a
 * request releases it, a session under an open connection outlives its timeout, and a connection that closes
 * answers every invocation still outstanding on the services it published.
 *
 * Created by Navíd Mitchell 🤪 on 9/9/26.
 */
public class EndpointConnectionHandlerTests {

    private static final long SESSION_TIMEOUT_MS = 1000;
    private static final String SERVICE_DESTINATION = "srv://app.acme-org.orders-app~OrderService/create#1.0.0";

    private static Ignite ignite;
    private static Vertx vertx;
    private Services services;
    private EventBusService eventBusService;
    private RequestLivenessWatcher requestLivenessWatcher;
    private EventConsumer replyConsumer;
    private Map<String, String> lastConnected;
    private final AtomicReference<Handler<Event<byte[]>>> replyDelivery = new AtomicReference<>();

    // The clustered session store needs a clustered Vert.x; one Ignite node on ports of its own, so a
    // core test JVM running alongside never joins it
    @BeforeAll
    public static void startCluster() throws Exception {
        IgniteConfiguration configuration = new IgniteConfiguration()
                .setIgniteInstanceName("gateway-endpoint-tests")
                .setMetricsLogFrequency(0)
                .setWorkDirectory("/tmp/ignite-gateway-tests")
                .setDiscoverySpi(new TcpDiscoverySpi().setLocalPort(48500)
                                                      .setIpFinder(new TcpDiscoveryVmIpFinder().setAddresses(List.of("127.0.0.1:48500"))))
                .setCommunicationSpi(new TcpCommunicationSpi().setLocalPort(48100));
        ignite = Ignition.start(configuration);
        vertx = Vertx.builder()
                     .withClusterManager(new IgniteClusterManager(ignite))
                     .buildClustered()
                     .toCompletionStage().toCompletableFuture().get(2, TimeUnit.MINUTES);
    }

    @AfterAll
    public static void stopCluster() throws Exception {
        vertx.close().toCompletionStage().toCompletableFuture().get(1, TimeUnit.MINUTES);
        ignite.close();
    }

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        eventBusService = mock(EventBusService.class);
        requestLivenessWatcher = mock(RequestLivenessWatcher.class);

        // the connection's reply subscription, with the handler the gateway installs captured for delivery
        replyConsumer = mock(EventConsumer.class);
        when(replyConsumer.handler(any())).thenAnswer(invocation -> {
            replyDelivery.set(invocation.getArgument(0));
            return replyConsumer;
        });
        when(replyConsumer.exceptionHandler(any())).thenReturn(replyConsumer);
        when(replyConsumer.completion()).thenReturn(Future.succeededFuture());
        when(replyConsumer.unregister()).thenReturn(Future.succeededFuture());
        when(eventBusService.listen(any())).thenReturn(replyConsumer);

        JsonMapper jsonMapper = JsonMapper.builder().build();
        services = new Services();
        services.vertx = vertx;
        services.apiGatewayProperties = new ApiGatewayProperties();
        services.apiGatewayProperties.setSessionTimeout(SESSION_TIMEOUT_MS);
        services.jsonMapper = jsonMapper;
        services.exceptionConverter = new JacksonExceptionConverter(new KinoticProperties(), jsonMapper);
        services.stompAuthorizerFactory = new StompAuthorizerFactory();
        services.securityService = mock(SecurityService.class);
        when(services.securityService.authenticate(any())).thenReturn(Future.succeededFuture(participant()));
        services.eventBusService = eventBusService;
        services.requestLivenessWatcher = requestLivenessWatcher;
        services.serviceDirectoryProvider = mock(ObjectProvider.class);
        services.sessionStore = ClusteredSessionStore.create(vertx);
        services.parkedReplySessions = new ParkedReplySessions(services.apiGatewayProperties, vertx, services.sessionStore);
    }

    @Test
    public void testLostNodeFailsTheRequestOnTheReplyDestination() throws Exception {
        EndpointConnectionHandler handler = connect(Map.of());
        String replyTo = subscribeReplies(handler);

        handler.send(request(replyTo, "corr-1")).toCompletionStage().toCompletableFuture().get(5, TimeUnit.SECONDS);

        ArgumentCaptor<Runnable> onLost = ArgumentCaptor.forClass(Runnable.class);
        verify(requestLivenessWatcher).watch(eq("corr-1"), eq("node-2"), onLost.capture());
        onLost.getValue().run();

        ArgumentCaptor<Event<byte[]>> sent = ArgumentCaptor.forClass(Event.class);
        verify(eventBusService).send(sent.capture());
        Event<byte[]> errorReply = sent.getValue();
        Assertions.assertEquals(replyTo, errorReply.cri().raw());
        Assertions.assertEquals("corr-1", errorReply.metadata().get(EventConstants.CORRELATION_ID_HEADER));
        Assertions.assertTrue(errorReply.metadata().get(EventConstants.ERROR_HEADER).contains("node-2"));
        Assertions.assertTrue(new String(errorReply.data(), StandardCharsets.UTF_8).contains("RpcServiceUnavailableException"));
    }

    @Test
    public void testTerminalReplySettlesTheRequest() throws Exception {
        EndpointConnectionHandler handler = connect(Map.of());
        String replyTo = subscribeReplies(handler);
        List<Event<byte[]>> delivered = new ArrayList<>();
        // the subscription handler the test installed receives what the reply consumer delivers
        handler.send(request(replyTo, "corr-2")).toCompletionStage().toCompletableFuture().get(5, TimeUnit.SECONDS);
        ArgumentCaptor<Runnable> onLost = ArgumentCaptor.forClass(Runnable.class);
        verify(requestLivenessWatcher).watch(eq("corr-2"), eq("node-2"), onLost.capture());

        Metadata replyMetadata = Metadata.create(Map.of(EventConstants.CORRELATION_ID_HEADER, "corr-2",
                                                        EventConstants.CONTROL_HEADER, EventConstants.CONTROL_VALUE_COMPLETE));
        replyDelivery.get().handle(Event.create(CRI.create(replyTo), replyMetadata, new byte[0]));

        verify(requestLivenessWatcher).settle("corr-2");
        // a node loss reported after the reply has nothing left to answer
        onLost.getValue().run();
        verify(eventBusService, never()).send(any());
    }

    @Test
    public void testShutdownSettlesEveryOutstandingRequest() throws Exception {
        // a NONE session's reply state ends with the connection; a sticky session's is parked instead
        EndpointConnectionHandler handler = connect(Map.of(EventConstants.SESSION_KEEP_ALIVE_HEADER, "NONE"));
        String replyTo = subscribeReplies(handler);
        handler.send(request(replyTo, "corr-3")).toCompletionStage().toCompletableFuture().get(5, TimeUnit.SECONDS);

        handler.shutdown();

        verify(requestLivenessWatcher).settle("corr-3");
        verify(replyConsumer).unregister();
    }

    @Test
    public void testSessionOutlivesItsTimeoutUnderAnOpenConnection() throws Exception {
        Session session = services.sessionStore.createSession(SESSION_TIMEOUT_MS);
        services.sessionStore.put(session).toCompletionStage().toCompletableFuture().get(5, TimeUnit.SECONDS);

        EndpointConnectionHandler handler = connect(session, Map.of(EventConstants.SESSION_KEEP_ALIVE_HEADER, "CONNECTION"));
        Thread.sleep(SESSION_TIMEOUT_MS * 5 / 2);
        Assertions.assertNotNull(storedSession(session.id()), "the session expired under an open connection");

        // the last store write happened at most one timeout before the connection closed
        handler.shutdown();
        Thread.sleep(SESSION_TIMEOUT_MS * 3 / 2);
        Assertions.assertNull(storedSession(session.id()), "the session outlived its timeout after the connection closed");
    }

    @Test
    public void testClosedConnectionFailsTheInvocationsItStillOwes() throws Exception {
        EndpointConnectionHandler handler = connect(Map.of());
        String requester = EventConstants.REPLY_DESTINATION_SCHEME + "://other:replies@kinotic.js.EventBus/replyHandler";
        deliverInvocation(handler, requester, "inv-1");

        handler.shutdown();

        ArgumentCaptor<Event<byte[]>> sent = ArgumentCaptor.forClass(Event.class);
        verify(eventBusService).send(sent.capture());
        Event<byte[]> errorReply = sent.getValue();
        Assertions.assertEquals(requester, errorReply.cri().raw());
        Assertions.assertEquals("inv-1", errorReply.metadata().get(EventConstants.CORRELATION_ID_HEADER));
        Assertions.assertTrue(new String(errorReply.data(), StandardCharsets.UTF_8).contains("RpcServiceUnavailableException"));
    }

    @Test
    public void testTerminalReplyFromTheConnectionSettlesTheInvocation() throws Exception {
        EndpointConnectionHandler handler = connect(Map.of());
        String requester = EventConstants.REPLY_DESTINATION_SCHEME + "://other:replies@kinotic.js.EventBus/replyHandler";
        deliverInvocation(handler, requester, "inv-2");

        // the service instance answers through the connection; the reply passes back on the reply scheme
        Metadata replyMetadata = Metadata.create(Map.of(EventConstants.CORRELATION_ID_HEADER, "inv-2",
                                                        EventConstants.CONTROL_HEADER, EventConstants.CONTROL_VALUE_COMPLETE));
        handler.send(Event.create(CRI.create(requester), replyMetadata, new byte[0])).toCompletionStage().toCompletableFuture().get(5, TimeUnit.SECONDS);
        handler.shutdown();

        // the forwarded reply is the only send; no error follows it
        ArgumentCaptor<Event<byte[]>> sent = ArgumentCaptor.forClass(Event.class);
        verify(eventBusService).send(sent.capture());
        Assertions.assertFalse(sent.getValue().metadata().contains(EventConstants.ERROR_HEADER));
    }

    @Test
    public void testParkedRepliesReachTheReconnectedClient() throws Exception {
        Session session = services.sessionStore.createSession(SESSION_TIMEOUT_MS * 10);
        services.sessionStore.put(session).toCompletionStage().toCompletableFuture().get(5, TimeUnit.SECONDS);
        EndpointConnectionHandler first = connect(session, Map.of());
        String replyTo = subscribeReplies(first);
        first.send(request(replyTo, "corr-p")).toCompletionStage().toCompletableFuture().get(5, TimeUnit.SECONDS);

        // the connection drops; the reply subscription stays registered and the reply lands while parked
        first.shutdown();
        verify(replyConsumer, never()).unregister();
        Metadata replyMetadata = Metadata.create(Map.of(EventConstants.CORRELATION_ID_HEADER, "corr-p",
                                                        EventConstants.CONTROL_HEADER, EventConstants.CONTROL_VALUE_COMPLETE));
        replyDelivery.get().handle(Event.create(CRI.create(replyTo), replyMetadata, new byte[0]));
        verify(requestLivenessWatcher).settle("corr-p");

        // the same client reconnects on its session and re-subscribes the same reply destination
        EndpointConnectionHandler second = connect(session, Map.of());
        List<Event<byte[]>> delivered = new ArrayList<>();
        second.subscribe(CRI.create(replyTo), "sub-2", new StompSubscriptionHandler() {
            @Override
            public void handleEvent(Event<byte[]> event) { delivered.add(event); }

            @Override
            public void handleError(Throwable throwable) {}
        });
        Assertions.assertEquals(1, delivered.size());
        Assertions.assertEquals("corr-p", delivered.get(0).metadata().get(EventConstants.CORRELATION_ID_HEADER));
        // the parked subscription was rebound, not registered again
        verify(eventBusService).listen(any());
    }

    @Test
    public void testUnclaimedParkedStateRotatesTheReplyDestination() throws Exception {
        services.apiGatewayProperties.setReplyBufferWindow(200);
        Session session = services.sessionStore.createSession(SESSION_TIMEOUT_MS * 10);
        services.sessionStore.put(session).toCompletionStage().toCompletableFuture().get(5, TimeUnit.SECONDS);
        EndpointConnectionHandler first = connect(session, Map.of());
        String firstReplyToId = replyToId();
        subscribeReplies(first);

        first.shutdown();
        Thread.sleep(600);

        verify(replyConsumer).unregister();
        EndpointConnectionHandler second = connect(session, Map.of());
        Assertions.assertNotEquals(firstReplyToId, replyToId(), "the reply destination was not rotated after the window");
    }

    @Test
    public void testOverflowingParkedStateRotatesTheReplyDestination() throws Exception {
        services.apiGatewayProperties.setReplyBufferMaxBytes(8);
        Session session = services.sessionStore.createSession(SESSION_TIMEOUT_MS * 10);
        services.sessionStore.put(session).toCompletionStage().toCompletableFuture().get(5, TimeUnit.SECONDS);
        EndpointConnectionHandler first = connect(session, Map.of());
        String firstReplyToId = replyToId();
        String replyTo = subscribeReplies(first);

        first.shutdown();
        Metadata replyMetadata = Metadata.create(Map.of(EventConstants.CORRELATION_ID_HEADER, "corr-big"));
        replyDelivery.get().handle(Event.create(CRI.create(replyTo), replyMetadata, new byte[16]));

        verify(replyConsumer).unregister();
        EndpointConnectionHandler second = connect(session, Map.of());
        Assertions.assertNotEquals(firstReplyToId, replyToId(), "the reply destination was not rotated on overflow");
    }

    @Test
    public void testParkedStateHandsOverToTheNodeThatTookTheDestination() throws Exception {
        Session session = services.sessionStore.createSession(SESSION_TIMEOUT_MS * 10);
        services.sessionStore.put(session).toCompletionStage().toCompletableFuture().get(5, TimeUnit.SECONDS);
        EndpointConnectionHandler first = connect(session, Map.of());
        String replyTo = subscribeReplies(first);
        first.send(request(replyTo, "corr-h")).toCompletionStage().toCompletableFuture().get(5, TimeUnit.SECONDS);
        first.shutdown();
        // a stream value lands while parked; the request stays pending
        replyDelivery.get().handle(Event.create(CRI.create(replyTo), Metadata.create(Map.of(EventConstants.CORRELATION_ID_HEADER, "corr-h")), new byte[]{1}));

        // another node's connection announces it took the destination over
        Metadata release = Metadata.create(Map.of(EventConstants.CONTROL_HEADER, EventConstants.CONTROL_VALUE_REPLY_SESSION_RELEASE,
                                                  EventConstants.RELEASE_ORIGIN_HEADER, "some-other-state"));
        replyDelivery.get().handle(Event.create(CRI.create(replyTo), release, null));

        verify(replyConsumer).unregister();
        ArgumentCaptor<Event<byte[]>> sent = ArgumentCaptor.forClass(Event.class);
        verify(eventBusService, org.mockito.Mockito.times(3)).send(sent.capture());
        List<Event<byte[]>> handoff = sent.getAllValues();
        Assertions.assertEquals(EventConstants.CONTROL_VALUE_FLUSH_BEGIN, handoff.get(0).metadata().get(EventConstants.CONTROL_HEADER));
        Assertions.assertEquals("corr-h", handoff.get(1).metadata().get(EventConstants.CORRELATION_ID_HEADER));
        Assertions.assertTrue(handoff.get(1).metadata().contains(EventConstants.REPLAYED_HEADER));
        Assertions.assertEquals(EventConstants.CONTROL_VALUE_FLUSH_COMPLETE, handoff.get(2).metadata().get(EventConstants.CONTROL_HEADER));
        String records = new String(handoff.get(2).data(), StandardCharsets.UTF_8);
        Assertions.assertTrue(records.contains("corr-h") && records.contains("node-2"), records);
        // the request is watched by the other node from now on
        verify(requestLivenessWatcher).settle("corr-h");
    }

    @Test
    public void testTakingOverADestinationHoldsLiveRepliesBehindTheReplay() throws Exception {
        EndpointConnectionHandler handler = connect(Map.of());
        String replyTo = EventConstants.REPLY_DESTINATION_SCHEME + "://" + replyToId() + ":replies@kinotic.js.EventBus/replyHandler";
        List<Event<byte[]>> delivered = new ArrayList<>();
        handler.subscribe(CRI.create(replyTo), "sub-1", new StompSubscriptionHandler() {
            @Override
            public void handleEvent(Event<byte[]> event) { delivered.add(event); }

            @Override
            public void handleError(Throwable throwable) {}
        });
        // a fresh subscription announces itself, so a parked state elsewhere hands over
        ArgumentCaptor<Event<byte[]>> published = ArgumentCaptor.forClass(Event.class);
        verify(eventBusService).publish(published.capture());
        Assertions.assertEquals(EventConstants.CONTROL_VALUE_REPLY_SESSION_RELEASE, published.getValue().metadata().get(EventConstants.CONTROL_HEADER));

        Handler<Event<byte[]>> consumer = replyDelivery.get();
        consumer.handle(Event.create(CRI.create(replyTo), Metadata.create(Map.of(EventConstants.CONTROL_HEADER, EventConstants.CONTROL_VALUE_FLUSH_BEGIN)), null));
        consumer.handle(Event.create(CRI.create(replyTo), Metadata.create(Map.of(EventConstants.CORRELATION_ID_HEADER, "corr-live")), new byte[0]));
        consumer.handle(Event.create(CRI.create(replyTo), Metadata.create(Map.of(EventConstants.CORRELATION_ID_HEADER, "corr-x",
                                                                                 EventConstants.REPLAYED_HEADER, "true")), new byte[0]));
        Assertions.assertTrue(delivered.isEmpty(), "nothing is delivered until the flush completes");

        byte[] records = services.jsonMapper.writeValueAsBytes(List.of(new PendingRequestRecord("corr-x", "node-2", Map.of(EventConstants.REPLY_TO_HEADER, replyTo))));
        consumer.handle(Event.create(CRI.create(replyTo), Metadata.create(Map.of(EventConstants.CONTROL_HEADER, EventConstants.CONTROL_VALUE_FLUSH_COMPLETE)), records));

        Assertions.assertEquals(List.of("corr-x", "corr-live"),
                                delivered.stream().map(event -> event.metadata().get(EventConstants.CORRELATION_ID_HEADER)).toList());
        Assertions.assertFalse(delivered.get(0).metadata().contains(EventConstants.REPLAYED_HEADER));
        verify(requestLivenessWatcher).watch(eq("corr-x"), eq("node-2"), any());
    }

    @Test
    public void testNoneKeepAliveSessionEndsWithTheConnection() throws Exception {
        Session session = services.sessionStore.createSession(SESSION_TIMEOUT_MS * 10);
        services.sessionStore.put(session).toCompletionStage().toCompletableFuture().get(5, TimeUnit.SECONDS);

        EndpointConnectionHandler handler = connect(session, Map.of(EventConstants.SESSION_KEEP_ALIVE_HEADER, "NONE"));
        Assertions.assertNotNull(storedSession(session.id()));

        // a network close, not a DISCONNECT frame: the session must not outlive the connection either way
        handler.shutdown();
        long deadline = System.currentTimeMillis() + 5000;
        while (storedSession(session.id()) != null && System.currentTimeMillis() < deadline) {
            Thread.sleep(50);
        }
        Assertions.assertNull(storedSession(session.id()), "a NONE session survived its connection");
    }

    // Subscribes the connection to a service address it publishes and delivers one invocation to it
    private void deliverInvocation(EndpointConnectionHandler handler, String replyTo, String correlationId) {
        handler.subscribe(CRI.create("srv://app.acme-org.orders-app~OrderService#1.0.0"), "svc-1", new StompSubscriptionHandler() {
            @Override
            public void handleEvent(Event<byte[]> event) {}

            @Override
            public void handleError(Throwable throwable) {}
        });
        Metadata metadata = Metadata.create(Map.of(EventConstants.REPLY_TO_HEADER, replyTo,
                                                   EventConstants.CORRELATION_ID_HEADER, correlationId));
        replyDelivery.get().handle(Event.create(CRI.create(SERVICE_DESTINATION), metadata, new byte[0]));
    }

    private EndpointConnectionHandler connect(Map<String, String> connectHeaders) throws Exception {
        return connect(services.sessionStore.createSession(SESSION_TIMEOUT_MS), connectHeaders);
    }

    private EndpointConnectionHandler connect(Session session, Map<String, String> connectHeaders) throws Exception {
        HttpServerRequest request = mock(HttpServerRequest.class);
        when(request.headers()).thenReturn(MultiMap.caseInsensitiveMultiMap());
        RoutingContext routingContext = mock(RoutingContext.class);
        when(routingContext.session()).thenReturn(session);
        when(routingContext.request()).thenReturn(request);

        EndpointConnectionHandler handler = new EndpointConnectionHandler(services);
        handler.handshake(routingContext).toCompletionStage().toCompletableFuture().get(5, TimeUnit.SECONDS);
        lastConnected = handler.connect(connectHeaders).toCompletionStage().toCompletableFuture().get(5, TimeUnit.SECONDS);
        return handler;
    }

    // Subscribes the connection's reply destination and returns its CRI; requests carry it as reply-to
    private String subscribeReplies(EndpointConnectionHandler handler) throws Exception {
        String replyTo = EventConstants.REPLY_DESTINATION_SCHEME + "://" + replyToId() + ":replies@kinotic.js.EventBus/replyHandler";
        handler.subscribe(CRI.create(replyTo), "sub-1", new StompSubscriptionHandler() {
            @Override
            public void handleEvent(Event<byte[]> event) {}

            @Override
            public void handleError(Throwable throwable) {}
        });
        return replyTo;
    }

    // The CONNECT frame is answered with the connected info, whose replyToId scopes every reply destination
    private String replyToId() {
        return services.jsonMapper.readTree(lastConnected.get(EventConstants.CONNECTED_INFO_HEADER)).get("replyToId").asString();
    }

    private Event<byte[]> request(String replyTo, String correlationId) {
        when(eventBusService.sendWithAck(any())).thenReturn(Future.succeededFuture("node-2"));
        Metadata metadata = Metadata.create(Map.of(EventConstants.REPLY_TO_HEADER, replyTo,
                                                   EventConstants.CORRELATION_ID_HEADER, correlationId));
        return Event.create(CRI.create(SERVICE_DESTINATION), metadata, new byte[0]);
    }

    private Session storedSession(String id) throws Exception {
        return services.sessionStore.get(id).toCompletionStage().toCompletableFuture().get(5, TimeUnit.SECONDS);
    }

    // An organization participant may both call and publish services in its own application zones
    private static DefaultOrganizationParticipant participant() {
        return DefaultOrganizationParticipant.builder()
                                             .id("org-user")
                                             .organizationId("acme-org")
                                             .metadata(Map.of())
                                             .roles(List.of())
                                             .build();
    }
}
