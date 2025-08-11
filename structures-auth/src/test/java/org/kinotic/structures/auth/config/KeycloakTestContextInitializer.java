package org.kinotic.structures.auth.config;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TestContextInitializer that ensures TestContainers are ready before Spring context initialization.
 * This works in conjunction with TestBeanPostProcessor to provide a complete test setup.
 * 
 * The TestBeanPostProcessor handles the detailed container setup and property injection,
 * while this initializer ensures containers are started early in the context lifecycle.
 */
public class KeycloakTestContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    
    private static final Logger log = LoggerFactory.getLogger(KeycloakTestContextInitializer.class);
    
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.info("TestContextInitializer: Ensuring TestContainers are ready before Spring context initialization...");
        
        try {
            // Ensure containers are started and ready before Spring context creation
            // This is a lightweight check - TestBeanPostProcessor will handle the detailed setup
            if (!KeyloakTestConfiguration.areContainersRunning()) {
                log.info("TestContextInitializer: Starting TestContainers...");
                KeyloakTestConfiguration.startContainersSynchronously();
            } else if (!KeyloakTestConfiguration.areContainersReady()) {
                log.info("TestContextInitializer: Waiting for containers to be ready...");
                KeyloakTestConfiguration.waitForContainersReady();
            }

            KeyloakTestConfiguration.ensureContainersReady();
            
            log.info("TestContextInitializer: TestContainers are ready, proceeding with Spring context initialization");

            // Keycloak OIDC properties
            String keycloakUrl = KeyloakTestConfiguration.getKeycloakUrl();
            String keycloakAuthUrl = KeyloakTestConfiguration.getKeycloakAuthUrl();
            
            TestPropertyValues.of(
                "oidc-security-service.enabled=true",
                "oidc-security-service.allowed-issuers[0]=" + keycloakAuthUrl,
                "oidc-security-service.authorization-audiences[0]=structures-client",
                "spring.security.oauth2.resourceserver.jwt.issuer-uri=" + keycloakAuthUrl,
                "spring.security.oauth2.resourceserver.jwt.jwk-set-uri=" + keycloakAuthUrl + "/protocol/openid-connect/certs"
            ).applyTo(applicationContext);
            
        } catch (Exception e) {
            log.error("TestContextInitializer: Failed to ensure TestContainers are ready", e);
            throw new RuntimeException("TestContainers failed to start during context initialization", e);
        }
    }
}
