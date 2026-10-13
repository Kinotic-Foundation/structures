package org.kinotic.domain.internal.config;

import lombok.extern.slf4j.Slf4j;
import org.kinotic.domain.api.config.KinoticDomainProperties;
import org.kinotic.domain.api.config.SecretStorageProperties;
import org.kinotic.domain.api.services.SecretStorageBackend;
import org.kinotic.domain.internal.api.services.secret.InMemoryBackend;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class SecretStorageConfiguration {

    // KinoticEeAutoConfiguration declares before = KinoticDomainAutoConfiguration, so a configured
    // backend from kinotic-ee is registered before this condition is evaluated
    @Bean
    @ConditionalOnMissingBean(SecretStorageBackend.class)
    public SecretStorageBackend secretStorageBackend(KinoticDomainProperties properties) {
        SecretStorageProperties settings = properties.getDomain().getSecretStorage();
        if (settings != null && settings.getBackend() != null) {
            throw new IllegalStateException("Secret storage backend " + settings.getBackend()
                                            + " is configured but the kinotic-ee module that provides it is not on the classpath");
        }
        log.info("No secret storage backend configured, using in-memory storage");
        return new InMemoryBackend();
    }
}
