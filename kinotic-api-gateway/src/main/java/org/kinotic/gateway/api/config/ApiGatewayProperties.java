

package org.kinotic.gateway.api.config;

import io.vertx.core.http.CookieSameSite;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Navid Mitchell on 7/19/17.
 */
@Getter
@Setter
public class ApiGatewayProperties {
    public static long DEFAULT_SESSION_TIMEOUT = 1000 * 60 * 30;
    public static long DEFAULT_STOMP_HEARTBEAT = 30_000;
    public static long DEFAULT_REPLY_BUFFER_WINDOW = 10_000;
    public static int DEFAULT_REPLY_BUFFER_MAX_BYTES = 4 * 1024 * 1024;
    public static int DEFAULT_STOMP_PORT = 58503;

    /**
     * How long a session should last in milliseconds.
     */
    private long sessionTimeout = DEFAULT_SESSION_TIMEOUT;

    /**
     * The STOMP heartbeat interval in milliseconds the gateway offers and expects in both directions.
     * A connection that stays silent for two intervals is closed, which is what makes a vanished
     * client's disconnect deterministic, with every registration and outstanding invocation it held.
     */
    private long stompHeartbeat = DEFAULT_STOMP_HEARTBEAT;

    /**
     * How long, in milliseconds, a sticky session's reply state is held after its connection closes: the
     * replies its calls produce in the meantime are buffered and delivered when the same client
     * reconnects to this node. A session not reclaimed within the window is disposed and its reply
     * destination rotated, so the client fails the calls it was waiting on. The client is told the window
     * on connect and fails those calls itself once a disconnect outlasts it.
     */
    private long replyBufferWindow = DEFAULT_REPLY_BUFFER_WINDOW;

    /**
     * The most reply bytes held for one disconnected session. A session whose buffered replies exceed
     * this is disposed and its reply destination rotated, the same exit as the window expiring.
     */
    private int replyBufferMaxBytes = DEFAULT_REPLY_BUFFER_MAX_BYTES;

    /**
     * The {@code SameSite} attribute of the session cookie. {@code LAX} sends it on requests
     * from the API's own site, which the sites domain shares in production. {@code NONE} sends
     * it from any origin the CORS pattern admits, for an environment where published sites and
     * the API live on unrelated domains, such as a developer's tunnel.
     */
    private CookieSameSite sessionCookieSameSite = CookieSameSite.LAX;

    /**
     * Port the STOMP server listens on.
     */
    private int stompPort = DEFAULT_STOMP_PORT;

    /**
     * Static-file web server configuration. Disabled in KinD/Azure where the SPA
     * is hosted outside the cluster.
     */
    private WebServerProperties webServer = new WebServerProperties();

    /**
     * CORS configuration applied to all Vert.x HTTP servers that expose browser-facing routes.
     */
    private CorsProperties cors = new CorsProperties();

    /**
     * SSL/TLS configuration for all Vert.x HTTP servers.
     */
    private SslProperties ssl = new SslProperties();

}
