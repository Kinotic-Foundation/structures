package org.kinotic.ee_autoconfig;

import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.kinotic.domain.api.config.KinoticDomainProperties;
import org.kinotic.domain.api.ee.services.SecretStorageBackend;
import org.kinotic.domain_autoconfig.KinoticDomainAutoConfiguration;
import org.kinotic.ee.internal.api.services.AzureKeyVaultBackend;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * This is the autoconfiguration class for the Kinotic Enterprise Features.
 * It is defined in a separate package because it must not be scanned by the spring context
 */
// before = domain: the backend declared here must be registered before SecretStorageConfiguration's
// @ConditionalOnMissingBean falls back to the in-memory backend
@Slf4j
@AutoConfiguration(before = KinoticDomainAutoConfiguration.class)
public class KinoticEeAutoConfiguration {

    @Bean
    @ConditionalOnProperty(value = "kinotic.domain.secretStorage.backend", havingValue = "AZURE")
    public SecretStorageBackend azureKeyVaultBackend(KinoticDomainProperties properties, Vertx vertx) {
        log.info("Using Azure Key Vault secret storage");
        return new AzureKeyVaultBackend(properties.getDomain().getSecretStorage().getAzure(), vertx);
    }
}
