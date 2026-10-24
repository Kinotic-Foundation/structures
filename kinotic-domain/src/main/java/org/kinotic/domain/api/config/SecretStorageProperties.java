package org.kinotic.domain.api.config;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Configuration settings for secret storage.
 */
@Getter
@Setter
@Accessors(chain = true)
public class SecretStorageProperties {
    /**
     * Base64-encoded master key used by {@code SecretNameDeriver} to derive opaque,
     * deterministic storage names from {@code (secretScope, key)} pairs.
     */
    private String masterKey;
    /**
     * Backend type. Defaults to {@link SecretStorageBackendType#IN_MEMORY}.
     */
    private SecretStorageBackendType backend = SecretStorageBackendType.IN_MEMORY;
    /**
     * Azure Key Vault settings. Required when {@code backend} is {@link SecretStorageBackendType#AZURE}.
     */
    private AzureProperties azure;
}
