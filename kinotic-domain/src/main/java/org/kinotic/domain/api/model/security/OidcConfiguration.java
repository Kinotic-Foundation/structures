package org.kinotic.domain.api.model.security;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.core.api.crud.Identifiable;

import java.util.Date;

/**
 * A persisted OIDC provider configuration: the provider, the client Kinotic registered with it, and
 * the issuer a sign-in through it uses. The concrete subtype states who owns the
 * configuration and carries the fields only that owner has — {@link OrgSignupOidcConfiguration} for
 * a social provider the platform operator curates, through which organization users sign up and in,
 * {@link OrganizationOidcConfiguration} for one an organization configures for itself. The client secret is never a field of a configuration: each
 * subtype says where its secret is held and who may read it. All subtypes share one id space and
 * one index; {@link #getType()} is the persisted discriminator the document is deserialized by.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = OrgSignupOidcConfiguration.class, name = "ORG_SIGNUP"),
        @JsonSubTypes.Type(value = OrganizationOidcConfiguration.class, name = "ORGANIZATION")
})
public abstract sealed class OidcConfiguration implements Identifiable<String>
        permits OrgSignupOidcConfiguration, OrganizationOidcConfiguration {

    private String id;

    /**
     * Human-readable name shown in the admin UI and on social-button labels.
     */
    private String name;

    /**
     * Provider kind selector — drives the Vert.x provider factory chosen at runtime.
     */
    private OidcProviderKind provider;

    /**
     * The OAuth 2.0 client identifier issued by the provider when Kinotic was registered
     * as an application. Sent during the authorization flow and used to validate the
     * JWT's audience claim.
     */
    private String clientId;

    /**
     * The browser-facing issuer URL, whose OIDC discovery document names the provider's endpoints.
     * Must match the {@code iss} claim in JWTs from this provider.
     */
    private String authority;

    /**
     * Expected {@code aud} claim, or {@code null} to default to {@link #clientId}.
     */
    private String audience;

    /**
     * Disabled rows are kept in their table for audit/history but excluded from runtime
     * provider lists.
     */
    private boolean enabled;

    private Date created;

    private Date updated;

    /**
     * Who owns this configuration; fixed by the concrete subtype and persisted as the polymorphic
     * discriminator of the document.
     */
    public abstract OidcConfigurationType getType();
}
