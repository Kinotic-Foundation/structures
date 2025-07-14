package org.kinotic.structures.internal.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OIDC authentication.
 * 
 * Example configuration in application.yml:
 * 
 * structures:
 *   oidc-auth-verifier:
 *     enabled: true
 *     allowed-issuers:
 *       - "https://your-oidc-provider.com"
 *       - "https://keycloak.your-domain.com/auth/realms/your-realm"
 *     authorization-audiences:
 *       - "your-application-client-id"
 *       - "your-api-audience"
 * 
 * The OIDC verifier will:
 * 1. Extract the JWT token from the Authorization header
 * 2. Verify the token signature using JWKS from the issuer
 * 3. Validate the issuer against the allowed issuers list
 * 4. Validate the audience against the allowed audiences list
 * 5. Check token expiration
 * 6. Create a Participant with user information from the token claims
 * 
 * Caching:
 * - JWKS keys are cached for 1 hour
 * - Well-known configurations are cached for 24 hours
 * - Cache sizes are limited to prevent memory issues
 */
@Configuration
public class OidcConfiguration {
    
    // This class serves as documentation for the OIDC configuration
    // The actual configuration is handled by OidcAuthVerifierProperties
    // and the OidcAuthVerifier component
} 