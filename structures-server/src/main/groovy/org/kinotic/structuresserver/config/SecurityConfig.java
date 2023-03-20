package org.kinotic.structuresserver.config;

import org.kinotic.continuum.core.api.security.SecurityService;
import org.kinotic.continuum.internal.core.api.security.DummySecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Setups correct {@link SecurityService} implementation for production use.
 */
@Configuration
public class SecurityConfig {

    @Bean
    SecurityService dummySecurityService(){
        return new TemporarySecurityService();
    }

}
