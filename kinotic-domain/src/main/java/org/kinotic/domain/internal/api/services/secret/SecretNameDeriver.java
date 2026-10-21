package org.kinotic.domain.internal.api.services.secret;

import lombok.extern.slf4j.Slf4j;
import org.kinotic.domain.api.config.KinoticDomainProperties;
import org.kinotic.domain.api.config.SecretStorageProperties;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.HexFormat;

/**
 * Derives opaque, deterministic secret names using a two-level HKDF chain (HMAC-SHA256).
 * <p>
 * Level 1: scopeKey = HMAC(masterKey, secretScope)
 * Level 2: derivedName = HMAC(scopeKey, key)  → lowercase hex (64 chars)
 * <p>
 */
@Slf4j
@Component
public class SecretNameDeriver {

    private static final String HMAC_ALGORITHM = "HmacSHA256";

    private final byte[] masterKey;

    public SecretNameDeriver(KinoticDomainProperties properties) {
        SecretStorageProperties settings = properties.getDomain().getSecretStorage();
        if (settings != null && settings.getMasterKey() != null) {
            this.masterKey = Base64.getDecoder().decode(settings.getMasterKey());
        } else {
            throw new IllegalStateException("No secret storage master key configured");
        }
    }

    /**
     * Derives an opaque, deterministic 64-character lowercase hex name for the given scope and key.
     * <p>
     * The derivation is a two-level HMAC chain:
     * <ol>
     *   <li>scopeKey = HMAC-SHA256(masterKey, secretScope)</li>
     *   <li>derivedName = hex(HMAC-SHA256(scopeKey, key))</li>
     * </ol>
     *
     * @param secretScope the isolation scope (e.g. tenant ID)
     * @param key         the logical secret key within the scope
     * @return a 64-character lowercase hex string suitable as a storage key
     */
    public String derive(String secretScope, String key) {
        byte[] scopeKey = hmac(masterKey, secretScope.getBytes(StandardCharsets.UTF_8));
        byte[] hash = hmac(scopeKey, key.getBytes(StandardCharsets.UTF_8));
        // Hex, because Azure Key Vault names allow only [0-9a-zA-Z-] and base64url emits '_'
        return HexFormat.of().formatHex(hash);
    }

    private static byte[] hmac(byte[] key, byte[] data) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(key, HMAC_ALGORITHM));
            return mac.doFinal(data);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("HMAC-SHA256 unavailable", e);
        }
    }
}
