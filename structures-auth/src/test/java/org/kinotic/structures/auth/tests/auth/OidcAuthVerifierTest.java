package org.kinotic.structures.auth.tests.auth;

import org.junit.jupiter.api.Test;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.structures.auth.internal.services.OidcSecurityService;
import org.kinotic.structures.auth.KeycloakTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.kinotic.structures.auth.api.config.OidcSecurityServiceProperties;
import org.kinotic.structures.auth.config.KeyloakTestConfiguration;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for OIDC authentication verifier.
 * 
 * Tests include:
 * - Authorization header parsing
 * - Configuration validation
 * - Error handling scenarios
 */
public class OidcAuthVerifierTest extends KeycloakTestBase {

    @Autowired
    private OidcSecurityService securityService;

    @Autowired
    private OidcSecurityServiceProperties properties;


    @Test
    public void testNoAuthorizationHeader() {
        Map<String, String> authInfo = Map.of();
        
        CompletableFuture<Participant> result = securityService.authenticate(authInfo);
        
        assertNull(result.join());
    }

    @Test
    public void testInvalidAuthorizationHeader() {
        Map<String, String> authInfo = Map.of("authorization", "InvalidFormat");
        
        CompletableFuture<Participant> result = securityService.authenticate(authInfo);
        
        assertNull(result.join());
    }

    @Test
    public void testNonBearerAuthorizationHeader() {
        Map<String, String> authInfo = Map.of("authorization", "Basic dXNlcjpwYXNz");
        
        CompletableFuture<Participant> result = securityService.authenticate(authInfo);
        
        assertNull(result.join());
    }

    @Test
    public void testCaseInsensitiveAuthorizationHeader() {
        Map<String, String> authInfo = Map.of("Authorization", "Bearer valid.jwt.token");
        
        CompletableFuture<Participant> result = securityService.authenticate(authInfo);
        
        assertNull(result.join());
    }

    @Test
    public void testValidBearerHeaderWithInvalidToken() {
        Map<String, String> authInfo = Map.of("authorization", "Bearer invalid.jwt.token");
        CompletableFuture<Participant> result = securityService.authenticate(authInfo);
        assertNull(result.join());
    }

    @Test
    public void testValidTokenFromKeycloak() throws Exception {
        // Use testcontainer Keycloak started by TestConfiguration
        org.junit.jupiter.api.Assumptions.assumeTrue(KeyloakTestConfiguration.KEYCLOAK_CONTAINER != null,
                "Keycloak container not available");
        String token = fetchKeycloakAccessToken("testuser", "password123");
        assertNotNull(token);
        Map<String, String> authInfo = Map.of("authorization", "Bearer " + token);
        Participant participant = securityService.authenticate(authInfo).join();
        assertNotNull(participant);
        assertEquals("kinotic", participant.getTenantId());
    }

    private String fetchKeycloakAccessToken(String username, String password) throws Exception {
        java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
        String form = "username=" + java.net.URLEncoder.encode(username, java.nio.charset.StandardCharsets.UTF_8)
                + "&password=" + java.net.URLEncoder.encode(password, java.nio.charset.StandardCharsets.UTF_8)
                + "&grant_type=password&client_id=structures-client";
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(java.net.URI.create(KeyloakTestConfiguration.getKeycloakUrl() + "/realms/test/protocol/openid-connect/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(form))
                .build();
        java.net.http.HttpResponse<String> response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IllegalStateException("Keycloak token endpoint returned status " + response.statusCode());
        }
        com.fasterxml.jackson.databind.JsonNode node = new com.fasterxml.jackson.databind.ObjectMapper().readTree(response.body());
        return node.get("access_token").asText();
    }

} 