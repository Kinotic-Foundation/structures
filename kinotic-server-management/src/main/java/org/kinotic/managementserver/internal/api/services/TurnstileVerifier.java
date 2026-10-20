package org.kinotic.managementserver.internal.api.services;

import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.kinotic.managementserver.api.config.ManagementServerProperties;
import org.kinotic.managementserver.api.config.TurnstileProperties;
import org.springframework.stereotype.Component;

/**
 * Verifies the Cloudflare Turnstile token a browser earned by passing the challenge of the site
 * {@code kinotic.managementServer.turnstile.*} names, so a route open to anyone can require a person at the
 * browser. With the site turned off every request verifies, for an environment that has no site.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TurnstileVerifier {

    private static final String SITEVERIFY_URL = "https://challenges.cloudflare.com/turnstile/v0/siteverify";
    private static final long VERIFY_TIMEOUT_MS = 10_000;

    private final ManagementServerProperties properties;
    private final Vertx vertx;
    private WebClient webClient;

    @PostConstruct
    public void start() {
        TurnstileProperties turnstile = properties.getTurnstile();
        if (turnstile.isEnabled()) {
            Validate.notBlank(turnstile.getSiteKey(), "kinotic.managementServer.turnstile.siteKey is required");
            Validate.notBlank(turnstile.getSecretKey(), "kinotic.managementServer.turnstile.secretKey is required");
            webClient = WebClient.create(vertx, new WebClientOptions().setFollowRedirects(false));
        }
    }

    @PreDestroy
    public void stop() {
        if (webClient != null) {
            webClient.close();
        }
    }

    /**
     * Whether {@code token} is one the site issued for a challenge a browser just passed: {@code false} for no
     * token, a token already verified once, or one issued by another site. Fails when Cloudflare could not be
     * asked, which refuses the request rather than admitting it.
     */
    public Future<Boolean> verify(String token) {
        Future<Boolean> ret;
        if (!properties.getTurnstile().isEnabled()) {
            ret = Future.succeededFuture(true);
        } else if (token == null || token.isBlank()) {
            ret = Future.succeededFuture(false);
        } else {
            MultiMap form = MultiMap.caseInsensitiveMultiMap()
                                    .set("secret", properties.getTurnstile().getSecretKey())
                                    .set("response", token);
            ret = webClient.postAbs(SITEVERIFY_URL)
                           .timeout(VERIFY_TIMEOUT_MS)
                           .sendForm(form)
                           .map(response -> {
                               JsonObject body = response.statusCode() == 200 ? response.bodyAsJsonObject() : null;
                               boolean success = body != null && body.getBoolean("success", false);
                               if (!success) {
                                   log.info("Turnstile refused a token: {}", body != null ? body.getJsonArray("error-codes") : "HTTP " + response.statusCode());
                               }
                               return success;
                           });
        }
        return ret;
    }
}
