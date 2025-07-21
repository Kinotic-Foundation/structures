package org.kinotic.structures.internal.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.Keys;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.kinotic.continuum.api.security.DefaultParticipant;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.continuum.api.security.ParticipantConstants;
import org.kinotic.continuum.api.security.SecurityService;
import org.kinotic.structures.api.config.StructuresProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.PublicKey;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Slf4j
@AllArgsConstructor
@Component
@ConditionalOnProperty(prefix = "structures.oidc-auth-verifier", name = "enabled", havingValue = "true")
public class OidcAuthVerifier implements SecurityService {
    
    private final StructuresProperties properties;
    private final JwksService jwksService;

    @Override
    public CompletableFuture<Participant> authenticate(Map<String, String> authenticationInfo) {
        String authorizationHeader = getAuthorizationHeader(authenticationInfo);
        if (authorizationHeader == null) {
            log.warn("No authorization header found");
            return CompletableFuture.completedFuture(null);
        }

        String[] parts = authorizationHeader.split(" ");
        if (parts.length != 2 || !"Bearer".equalsIgnoreCase(parts[0])) {
            log.warn("Invalid authorization header format, expected 'Bearer <token>'");
            return CompletableFuture.completedFuture(null);
        }

        String token = parts[1];
        return verifyJwtToken(token);
    }

    private String getAuthorizationHeader(Map<String, String> authenticationInfo) {
        if (authenticationInfo.containsKey("authorization")) {
            return authenticationInfo.get("authorization");
        } else if (authenticationInfo.containsKey("Authorization")) {
            return authenticationInfo.get("Authorization");
        }
        return null;
    }

    private CompletableFuture<Participant> verifyJwtToken(String token) {
        return jwksService.getKeyFromToken(token)
                .thenCompose(key -> validateTokenWithKey(token, key))
                .exceptionally(throwable -> {
                    log.error("JWT token verification failed", throwable);
                    return null;
                });
    }

    private CompletableFuture<Participant> validateTokenWithKey(String token, Jwk<? extends Key> jwk) {
        try {
            // Extract the actual key from the Jwk
            Key key = jwk.toKey();
            
            // Verify it's a PublicKey for JWT verification
            if (!(key instanceof PublicKey)) {
                log.error("Jwk does not contain a PublicKey instance");
                return CompletableFuture.completedFuture(null);
            }
            
            PublicKey publicKey = (PublicKey) key;
            
            // Parse and validate the JWT token
            Claims claims = Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // Validate issuer
            String issuer = claims.getIssuer();
            if (!isValidIssuer(issuer)) {
                log.warn("Invalid issuer: {}", issuer);
                return CompletableFuture.completedFuture(null);
            }

            // Validate audience
            Set<String> audiences = claims.getAudience();
            if (!isValidAudience(audiences)) {
                log.warn("Invalid audience: {}", audiences);
                return CompletableFuture.completedFuture(null);
            }

            // Validate expiration
            if (claims.getExpiration() != null && claims.getExpiration().before(java.util.Date.from(Instant.now()))) {
                log.warn("Token has expired");
                return CompletableFuture.completedFuture(null);
            }

            // Create participant from claims
            Participant participant = createParticipantFromClaims(claims);
            return CompletableFuture.completedFuture(participant);

        } catch (JwtException e) {
            log.error("JWT parsing/validation failed", e);
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            log.error("Unexpected error during JWT validation", e);
            return CompletableFuture.completedFuture(null);
        }
    }

    private boolean isValidIssuer(String issuer) {
        if (issuer == null) {
            return false;
        }

        List<String> allowedIssuers = properties.getOidcAuthVerifier().getAllowedIssuers();
        if (allowedIssuers == null || allowedIssuers.isEmpty()) {
            log.warn("No allowed issuers configured");
            return false;
        }

        return allowedIssuers.contains(issuer);
    }

    private boolean isValidAudience(Set<String> audiences) {
        if (audiences == null || audiences.isEmpty()) {
            return false;
        }

        List<String> allowedAudiences = properties.getOidcAuthVerifier().getAuthorizationAudiences();
        if (allowedAudiences == null || allowedAudiences.isEmpty()) {
            log.warn("No allowed audiences configured");
            return false;
        }

        // Check if any of the token audiences match any of the allowed audiences
        return audiences.stream().anyMatch(allowedAudiences::contains);
    }

    private Participant createParticipantFromClaims(Claims claims) {
        // Extract user information from claims
        String subject = claims.getSubject();
        String email = claims.get("email", String.class);
        String name = claims.get("name", String.class);
        String preferredUsername = claims.get("preferred_username", String.class);
        
        // Extract tenant ID from claims or use a default
        String tenantId = claims.get(properties.getTenantIdFieldName(), String.class);
        
        if(tenantId == null){
            tenantId = claims.get("tenant_id", String.class);
        }
        if (tenantId == null) {
            tenantId = claims.get("tid", String.class); // Alternative claim name for tenant
        }
        if (tenantId == null) {
            tenantId = "default"; // Default tenant if not specified
        }
        // TODO: does it make sense to try and support pulling a "tenant" from the issuer url - like for keycloak?

        // Extract roles from claims
        List<String> roles = extractRolesFromClaims(claims);

        // Create metadata
        Map<String, String> metadata = Map.of(
            ParticipantConstants.PARTICIPANT_TYPE_METADATA_KEY,
            ParticipantConstants.PARTICIPANT_TYPE_USER,
            "email", email != null ? email : "",
            "name", name != null ? name : (preferredUsername != null ? preferredUsername : subject),
            "iss", claims.getIssuer(),
            "aud", claims.getAudience().stream().collect(Collectors.joining(", "))
        );

        return new DefaultParticipant(tenantId, subject, metadata, roles);
    }

    private List<String> extractRolesFromClaims(Claims claims) {
        // Try to extract roles from various possible claim names
        Object rolesClaim = claims.get("roles");
        if (rolesClaim == null) {
            rolesClaim = claims.get("role");
        }
        if (rolesClaim == null) {
            rolesClaim = claims.get("groups");
        }
        if (rolesClaim == null) {
            rolesClaim = claims.get("realm_access");
        }

        if (rolesClaim instanceof List) {
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) rolesClaim;
            return roles;
        } else if (rolesClaim instanceof Map) {
            // Handle realm_access format from Keycloak
            @SuppressWarnings("unchecked")
            Map<String, Object> realmAccess = (Map<String, Object>) rolesClaim;
            Object rolesObj = realmAccess.get("roles");
            if (rolesObj instanceof List) {
                @SuppressWarnings("unchecked")
                List<String> roles = (List<String>) rolesObj;
                return roles;
            }
        }

        // Default to empty list if no roles found
        return List.of();
    }
}
