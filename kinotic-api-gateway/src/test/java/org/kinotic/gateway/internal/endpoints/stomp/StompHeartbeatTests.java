package org.kinotic.gateway.internal.endpoints.stomp;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.WebSocket;
import io.vertx.core.http.WebSocketClient;
import io.vertx.core.http.WebSocketConnectOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.stomp.lite.StompServerOptions;
import io.vertx.ext.stomp.lite.StompServerVerticleFactory;
import io.vertx.ext.web.Router;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kinotic.core.api.event.EventBusService;
import org.kinotic.core.api.event.EventConstants;
import org.kinotic.core.api.security.SecurityService;
import org.kinotic.core.api.service.RequestLivenessWatcher;
import org.kinotic.domain.api.model.security.participant.DefaultSystemParticipant;
import org.kinotic.gateway.api.config.ApiGatewayProperties;
import org.kinotic.gateway.internal.endpoints.Services;
import org.springframework.beans.factory.ObjectProvider;
import tools.jackson.databind.json.JsonMapper;

import java.net.ServerSocket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Pins that a client which goes silent is disconnected by the gateway's STOMP heartbeat: the socket a
 * vanished workload leaves behind closes on its own, and with it everything the connection held.
 *
 * Created by Navíd Mitchell 🤪 on 9/9/26.
 */
public class StompHeartbeatTests {

    private static final long HEARTBEAT_MS = 500;

    private Vertx vertx;
    private int port;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void startServer() throws Exception {
        vertx = Vertx.vertx();
        try (ServerSocket socket = new ServerSocket(0)) {
            port = socket.getLocalPort();
        }
        Services services = new Services();
        services.vertx = vertx;
        services.apiGatewayProperties = new ApiGatewayProperties();
        services.apiGatewayProperties.setStompHeartbeat(HEARTBEAT_MS);
        services.jsonMapper = JsonMapper.builder().build();
        services.stompAuthorizerFactory = new StompAuthorizerFactory();
        services.securityService = mock(SecurityService.class);
        when(services.securityService.authenticate(any()))
                .thenReturn(Future.succeededFuture(DefaultSystemParticipant.builder().id("system").metadata(Map.of()).roles(List.of()).build()));
        services.eventBusService = mock(EventBusService.class);
        services.requestLivenessWatcher = mock(RequestLivenessWatcher.class);
        services.serviceDirectoryProvider = mock(ObjectProvider.class);

        // The same options ApiGatewayVertcleFactory builds from the property
        long heartbeat = services.apiGatewayProperties.getStompHeartbeat();
        StompServerOptions stompServerOptions = new StompServerOptions()
                .setWebsocketPath("/v1")
                .setHeartbeat(new JsonObject().put("x", heartbeat).put("y", heartbeat));
        HttpServerOptions serverOptions = new HttpServerOptions().setPort(port).setWebSocketSubProtocols(List.of("v12.stomp"));
        vertx.deployVerticle(StompServerVerticleFactory.create(serverOptions,
                                                               stompServerOptions,
                                                               () -> new DefaultStompServerHandler(services),
                                                               Router.router(vertx)))
             .toCompletionStage().toCompletableFuture().get(30, TimeUnit.SECONDS);
    }

    @AfterEach
    public void stopServer() throws Exception {
        vertx.close().toCompletionStage().toCompletableFuture().get(10, TimeUnit.SECONDS);
    }

    @Test
    public void testSilentClientIsDisconnectedAfterTwoHeartbeats() throws Exception {
        WebSocketClient client = vertx.createWebSocketClient();
        WebSocket webSocket = client.connect(new WebSocketConnectOptions().setPort(port)
                                                                          .setHost("localhost")
                                                                          .setURI("/v1")
                                                                          .setSubProtocols(List.of("v12.stomp")))
                                    .toCompletionStage().toCompletableFuture().get(10, TimeUnit.SECONDS);
        CompletableFuture<String> connected = new CompletableFuture<>();
        CountDownLatch closed = new CountDownLatch(1);
        // the server writes frames as binary websocket messages
        webSocket.handler(buffer -> connected.complete(buffer.toString()));
        webSocket.closeHandler(v -> closed.countDown());

        // The client offers the same heartbeat, then never sends one
        webSocket.writeTextMessage("CONNECT\naccept-version:1.2\nheart-beat:" + HEARTBEAT_MS + "," + HEARTBEAT_MS
                                   + "\n" + EventConstants.SESSION_KEEP_ALIVE_HEADER + ":NONE\n\n\0");
        Assertions.assertTrue(connected.get(10, TimeUnit.SECONDS).startsWith("CONNECTED"));

        long start = System.currentTimeMillis();
        Assertions.assertTrue(closed.await(HEARTBEAT_MS * 6, TimeUnit.MILLISECONDS), "the silent client was never disconnected");
        Assertions.assertTrue(System.currentTimeMillis() - start >= HEARTBEAT_MS, "the client was disconnected before it could have missed a heartbeat");
    }
}
