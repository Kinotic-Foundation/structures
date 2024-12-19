package org.kinotic.structures.internal.config;

import org.kinotic.structures.internal.api.services.impl.security.NoopAuthorizationServiceFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StructuresSecurityConfig {

    @Bean
    @ConditionalOnMissingBean
    public NoopAuthorizationServiceFactory authorizationServiceFactory(){
        return new NoopAuthorizationServiceFactory();
    }

}
