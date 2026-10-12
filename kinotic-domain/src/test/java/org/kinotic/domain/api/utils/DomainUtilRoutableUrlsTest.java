package org.kinotic.domain.api.utils;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Covers {@link DomainUtil#requireRoutableUrls}: a URL that is not https, carries a username, names no host,
 * or whose host is a loopback, wildcard, link-local, private or multicast address is refused, and a publicly
 * routable one passes. Address literals keep the cases free of DNS.
 */
class DomainUtilRoutableUrlsTest {

    private static final Vertx VERTX = Vertx.vertx();

    @AfterAll
    static void closeVertx() {
        VERTX.close();
    }

    @Test
    void aPubliclyRoutableHttpsUrlPasses() throws Exception {
        await(DomainUtil.requireRoutableUrls(VERTX, List.of("https://203.0.113.10/authorize", "https://[2001:db8::1]/token")));
    }

    @Test
    void anInsecureMalformedOrSpecialUseUrlIsRefused() {
        for (String url : List.of("http://203.0.113.10", "https://user:pass@203.0.113.10", "https:///token", "not a url",
                                  "https://127.0.0.1", "https://0.0.0.0", "https://10.0.0.5", "https://172.16.0.1",
                                  "https://192.168.1.10", "https://169.254.169.254", "https://224.0.0.1",
                                  "https://[::1]", "https://[::]", "https://[fd00::1]", "https://[fe80::1]", "https://[ff02::1]")) {
            Throwable cause = assertThrows(ExecutionException.class,
                                           () -> await(DomainUtil.requireRoutableUrls(VERTX, List.of(url))),
                                           url).getCause();
            assertInstanceOf(IllegalArgumentException.class, cause, url);
        }
    }

    private static <T> T await(Future<T> future) throws Exception {
        return future.toCompletionStage().toCompletableFuture().get(10, TimeUnit.SECONDS);
    }
}
