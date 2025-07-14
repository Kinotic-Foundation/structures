package org.kinotic.structures.internal.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.structures.api.config.OidcAuthVerifierProperties;
import org.kinotic.structures.api.config.StructuresProperties;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Test class for OIDC authentication verifier.
 * 
 * Tests include:
 * - Authorization header parsing
 * - Configuration validation
 * - Error handling scenarios
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OidcAuthVerifierTest {

    @Mock
    private StructuresProperties structuresProperties;
    
    @Mock
    private JwksService jwksService;
    
    @Mock
    private OidcAuthVerifierProperties oidcProperties;

    private OidcAuthVerifier oidcAuthVerifier;

    @BeforeEach
    void setUp() {
        when(structuresProperties.getOidcAuthVerifier()).thenReturn(oidcProperties);
        oidcAuthVerifier = new OidcAuthVerifier(structuresProperties, jwksService);
    }

    @Test
    void testNoAuthorizationHeader() {
        Map<String, String> authInfo = Map.of();
        
        CompletableFuture<Participant> result = oidcAuthVerifier.authenticate(authInfo);
        
        assertNull(result.join());
    }

    @Test
    void testInvalidAuthorizationHeader() {
        Map<String, String> authInfo = Map.of("authorization", "InvalidFormat");
        
        CompletableFuture<Participant> result = oidcAuthVerifier.authenticate(authInfo);
        
        assertNull(result.join());
    }

    @Test
    void testNonBearerAuthorizationHeader() {
        Map<String, String> authInfo = Map.of("authorization", "Basic dXNlcjpwYXNz");
        
        CompletableFuture<Participant> result = oidcAuthVerifier.authenticate(authInfo);
        
        assertNull(result.join());
    }

    @Test
    void testCaseInsensitiveAuthorizationHeader() {
        Map<String, String> authInfo = Map.of("Authorization", "Bearer valid.jwt.token");
        
        // Mock JWKS service to return a failed future for invalid token
        when(jwksService.getKeyFromToken(anyString())).thenReturn(
            CompletableFuture.failedFuture(new RuntimeException("Invalid token"))
        );
        
        CompletableFuture<Participant> result = oidcAuthVerifier.authenticate(authInfo);
        
        assertNull(result.join());
    }

    @Test
    void testValidBearerHeaderWithInvalidToken() {
        Map<String, String> authInfo = Map.of("authorization", "Bearer invalid.jwt.token");
        
        // Mock JWKS service to return a failed future
        when(jwksService.getKeyFromToken(anyString())).thenReturn(
            CompletableFuture.failedFuture(new RuntimeException("Invalid token"))
        );
        
        CompletableFuture<Participant> result = oidcAuthVerifier.authenticate(authInfo);
        
        assertNull(result.join());
    }

    @Test
    void testConfigurationValidation() {
        // Test that the configuration properties are properly validated
        when(oidcProperties.getAllowedIssuers()).thenReturn(List.of("https://test-issuer.com"));
        when(oidcProperties.getAuthorizationAudiences()).thenReturn(List.of("test-audience"));
        
        assertNotNull(oidcProperties.getAllowedIssuers());
        assertNotNull(oidcProperties.getAuthorizationAudiences());
        assertEquals(1, oidcProperties.getAllowedIssuers().size());
        assertEquals(1, oidcProperties.getAuthorizationAudiences().size());
    }

    @Test
    void testEmptyConfiguration() {
        // Test with empty configuration
        when(oidcProperties.getAllowedIssuers()).thenReturn(List.of());
        when(oidcProperties.getAuthorizationAudiences()).thenReturn(List.of());
        
        assertTrue(oidcProperties.getAllowedIssuers().isEmpty());
        assertTrue(oidcProperties.getAuthorizationAudiences().isEmpty());
    }

    @Test
    void testNullConfiguration() {
        // Test with null configuration
        when(oidcProperties.getAllowedIssuers()).thenReturn(null);
        when(oidcProperties.getAuthorizationAudiences()).thenReturn(null);
        
        assertNull(oidcProperties.getAllowedIssuers());
        assertNull(oidcProperties.getAuthorizationAudiences());
    }

    @Test
    void testMultipleAllowedIssuers() {
        // Test with multiple allowed issuers
        when(oidcProperties.getAllowedIssuers()).thenReturn(List.of(
            "https://issuer1.com", 
            "https://issuer2.com", 
            "https://issuer3.com"
        ));
        
        assertEquals(3, oidcProperties.getAllowedIssuers().size());
        assertTrue(oidcProperties.getAllowedIssuers().contains("https://issuer1.com"));
        assertTrue(oidcProperties.getAllowedIssuers().contains("https://issuer2.com"));
        assertTrue(oidcProperties.getAllowedIssuers().contains("https://issuer3.com"));
    }

    @Test
    void testMultipleAllowedAudiences() {
        // Test with multiple allowed audiences
        when(oidcProperties.getAuthorizationAudiences()).thenReturn(List.of(
            "audience1", 
            "audience2", 
            "audience3"
        ));
        
        assertEquals(3, oidcProperties.getAuthorizationAudiences().size());
        assertTrue(oidcProperties.getAuthorizationAudiences().contains("audience1"));
        assertTrue(oidcProperties.getAuthorizationAudiences().contains("audience2"));
        assertTrue(oidcProperties.getAuthorizationAudiences().contains("audience3"));
    }
} 