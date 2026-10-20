

package org.kinotic.gateway.api.config;

import io.vertx.core.http.CookieSameSite;
import lombok.Getter;
import lombok.Setter;
import org.kinotic.domain.api.utils.DomainUtil;

import java.util.Set;

/**
 * Created by Navid Mitchell on 7/19/17.
 */
@Getter
@Setter
public class ApiGatewayProperties {
    public static long DEFAULT_SESSION_TIMEOUT = 1000 * 60 * 30;
    public static int DEFAULT_STOMP_PORT = 58503;

    /**
     * How long a session should last in milliseconds.
     */
    private long sessionTimeout = DEFAULT_SESSION_TIMEOUT;

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
     * Zones of services, beyond Kinotic's own, that the organizations' users connected to this gateway may call,
     * such as those of an application that joins the cluster to serve the platform's users. Each zone covers its
     * sub-zones, and none may be, fall under, or cover a zone of the platform's own. The management server reaches
     * these zones, so its connections can call them.
     */
    private Set<String> organizationServiceZones = Set.of();

    /**
     * Static-file web server configuration. Disabled in KinD/Azure where the SPA
     * is hosted outside the cluster.
     */
    private WebServerProperties webServer = new WebServerProperties();

    /**
     * SSL/TLS configuration for all Vert.x HTTP servers.
     */
    private SslProperties ssl = new SslProperties();

    public void setOrganizationServiceZones(Set<String> organizationServiceZones) {
        // a zone overlapping the platform's would let every organization's users reach system or other organizations' services
        organizationServiceZones.forEach(DomainUtil::validateOrganizationServiceZone);
        this.organizationServiceZones = organizationServiceZones;
    }

}
