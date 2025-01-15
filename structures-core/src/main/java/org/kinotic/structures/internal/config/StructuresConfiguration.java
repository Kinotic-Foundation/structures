package org.kinotic.structures.internal.config;

import io.vertx.core.Vertx;
import io.vertx.ext.healthchecks.HealthChecks;
import org.kinotic.structures.api.services.security.AuthorizationServiceFactory;
import org.kinotic.structures.internal.api.services.impl.security.NoopAuthorizationServiceFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Enables component scanning for structures classes when {@link org.kinotic.continuum.api.annotations.EnableContinuum} is present
 * Created by Navíd Mitchell 🤪 on 5/30/23.
 */
@Configuration
@EnableConfigurationProperties
@ComponentScan(basePackages = "org.kinotic.structures")
public class StructuresConfiguration {

    @Bean
    public HealthChecks healthChecks(Vertx vertx){
        return HealthChecks.create(vertx);
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthorizationServiceFactory authorizationServiceFactory(){
        return new NoopAuthorizationServiceFactory();
    }

}
