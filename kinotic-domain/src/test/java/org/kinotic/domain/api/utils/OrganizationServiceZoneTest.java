package org.kinotic.domain.api.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Covers {@link DomainUtil#validateOrganizationServiceZone}: a zone of its own is accepted, and a zone that is, falls
 * under, or covers one of the platform's zones, or breaks the zone grammar, is refused.
 */
class OrganizationServiceZoneTest {

    @Test
    void aZoneOfItsOwnIsAccepted() {
        assertDoesNotThrow(() -> DomainUtil.validateOrganizationServiceZone("summit-42"));
        assertDoesNotThrow(() -> DomainUtil.validateOrganizationServiceZone("summit-42.builder"));
        assertDoesNotThrow(() -> DomainUtil.validateOrganizationServiceZone("apps"));
        assertDoesNotThrow(() -> DomainUtil.validateOrganizationServiceZone("system-api-tools"));
    }

    @Test
    void aZoneOverlappingThePlatformsIsRefused() {
        for (String zone : List.of("system-api", "system-api.llm", "management-api", "app-api", "app", "app.acme", "app.acme.orders")) {
            assertThrows(IllegalArgumentException.class, () -> DomainUtil.validateOrganizationServiceZone(zone), zone);
        }
    }

    @Test
    void aZoneBreakingTheGrammarIsRefused() {
        for (String zone : List.of("", "Summit42", "summit_42", "-summit", "summit..42")) {
            assertThrows(IllegalArgumentException.class, () -> DomainUtil.validateOrganizationServiceZone(zone), zone);
        }
    }
}
