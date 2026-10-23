package org.kinotic.domain.api.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Duration;

/**
 * Configuration for outbound email delivered via Azure Communication Services.
 * Bound under {@code kinotic.domain.email.*} — the {@code domain} field on
 * {@link KinoticDomainProperties} carries a {@link DomainProperties} that owns this.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class EmailProperties {

    /**
     * When {@code false}, no email is sent and each link a person must open is logged.
     */
    private boolean enabled = true;

    /**
     * When {@code true} and {@link #enabled} is {@code false}, each link a person must open is returned to the UI that
     * asked for it, which shows it: the verification link to the one signing up and an invitation's accept link to the
     * inviter, as a demonstration server does. Nothing then proves an address belongs to the person using it, so this
     * is never set on a server reachable by anyone but the people trying it.
     */
    private boolean showLinksInUI = false;

    /**
     * Whether the links a person must open are returned for the UI to show, because no email carries them:
     * {@link #enabled} is {@code false} and {@link #showLinksInUI} is {@code true}.
     */
    public boolean showsLinksInUI() {
        return !enabled && showLinksInUI;
    }

    /**
     * ACS endpoint URL, e.g. {@code https://my-acs-resource.communication.azure.com}.
     * Required when {@link #enabled} is {@code true}.
     */
    private String endpoint;

    /**
     * Verified ACS domain sender address, e.g. {@code DoNotReply@xyz.azurecomm.net}.
     */
    private String senderAddress;

    /**
     * Maximum time to wait for the ACS send operation to complete.
     */
    private Duration sendTimeout = Duration.ofSeconds(30);

    /**
     * Optional client id of a User-Assigned Managed Identity, forwarded to
     * {@code DefaultAzureCredentialBuilder.managedIdentityClientId(...)}. When null,
     * {@code DefaultAzureCredential} auto-resolves via AKS Workload-Identity env vars
     * ({@code AZURE_CLIENT_ID}, {@code AZURE_TENANT_ID}, {@code AZURE_FEDERATED_TOKEN_FILE}).
     */
    // FIXME: we probably should ditch this
    private String managedIdentityClientId;

}
