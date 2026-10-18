package org.kinotic.test.tests.core.entity;

import io.vertx.core.Future;
import org.junit.jupiter.api.Test;
import org.kinotic.domain.api.model.Tenant;
import org.kinotic.domain.api.utils.HostLabelUtil;
import org.kinotic.management.api.services.ApplicationService;
import org.kinotic.management.api.services.TenantService;
import org.kinotic.test.support.kinotic.KinoticTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Verifies how an organization's tenants are created: a tenant id names one tenant of one of the organization's
 * applications, so creating it again for the same application answers the existing tenant and for another
 * application is refused, and an id is held to the room a site label leaves in front of the application's name.
 */
@SpringBootTest
public class TenantServiceTests extends KinoticTestBase {

    @Autowired
    private TenantService tenantService;

    @Autowired
    private ApplicationService applicationService;

    @Test
    public void aTenantIdNamesOneTenantOfOneApplication() throws Exception {
        String appId = "tenant-app-" + suffix();
        String otherAppId = "tenant-app-" + suffix();
        await(runAsOrganization(() -> applicationService.createApplicationIfNotExist(appId, "Tenant application", null)));
        await(runAsOrganization(() -> applicationService.createApplicationIfNotExist(otherAppId, "Other application", null)));
        String name = "Globex " + suffix();

        Tenant globex = await(runAsOrganization(() -> tenantService.createTenantIfNotExist(appId, name, "A customer")));
        Tenant again = await(runAsOrganization(() -> tenantService.createTenantIfNotExist(appId, name, "Again")));
        assertEquals(globex.getId(), again.getId());
        assertEquals("A customer", again.getDescription());

        assertInstanceOf(IllegalArgumentException.class, failure(() -> tenantService.createTenantIfNotExist(otherAppId, name, "Another customer")));
        Tenant kept = await(runAsOrganization(() -> tenantService.findById(globex.getId())));
        assertEquals(appId, kept.getApplicationId());
        assertEquals("A customer", kept.getDescription());
    }

    @Test
    public void aTenantIdIsHeldToTheRoomASiteLabelLeavesIt() throws Exception {
        String appId = "tenant-app-" + suffix();
        await(runAsOrganization(() -> applicationService.createApplicationIfNotExist(appId, "Tenant application", null)));
        String longest = ("t" + suffix() + "xxxxxxxxxxxxxxxxxxxxxxxx").substring(0, HostLabelUtil.MAX_TENANT_ID_LENGTH);

        assertEquals(longest, await(runAsOrganization(() -> tenantService.createTenantIfNotExist(appId, longest, null))).getId());
        assertInstanceOf(IllegalArgumentException.class, failure(() -> tenantService.createTenantIfNotExist(appId, longest + "x", null)));
    }

    private Throwable failure(Supplier<Future<?>> call) {
        return assertThrows(ExecutionException.class, () -> await(runAsOrganization(call::get))).getCause();
    }

    private static String suffix() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    private static <T> T await(Future<T> future) throws Exception {
        return future.toCompletionStage().toCompletableFuture().get(30, TimeUnit.SECONDS);
    }
}
