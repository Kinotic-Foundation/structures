package org.kinotic.domain.internal.config;

import lombok.extern.slf4j.Slf4j;
import org.kinotic.domain.api.ee.services.SecretStorageBackend;
import org.kinotic.domain.internal.api.services.secret.InMemoryBackend;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class SecretStorageConfiguration {

    @Bean
    @ConditionalOnProperty(value = "kinotic.domain.secretStorage.backend", havingValue = "IN_MEMORY", matchIfMissing = true)
    public SecretStorageBackend inMemorySecretStorageBackend() {
        log.info("Using in-memory secret storage");
        return new InMemoryBackend();
    }
}
