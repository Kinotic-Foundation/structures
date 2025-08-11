/*
 *
 * Copyright 2008-2021 Kinotic and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kinotic.structures.auth.api.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Configuration class for OIDC authentication.
 * 
 * Example configuration in application.yml:
 * 
 * [root]:
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
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "oidc-security-service")
public class OidcSecurityServiceProperties {

    /**
     * Master switch for enabling/disabling OIDC security service beans.
     */
    private boolean enabled = true;

    private String tenantIdFieldName = "tenantId";

    private List<String> allowedIssuers;

    private List<String> authorizationAudiences;
    
}
