package org.kinotic.domain.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.domain.api.model.security.OidcProviderType;
import org.kinotic.domain.api.model.security.identity.ParticipantIdentity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Application implements OrganizationScoped<String> {

    /**
     * The slugified {@link #name}, minted at creation
     */
    private String id;

    private String organizationId;

    private String name;

    private String description;

    /**
     * Who creates and administers this application's tenants. Under {@link TenantManagement#PLATFORM}
     * every APPLICATION-scope {@link ParticipantIdentity} created for the application receives a tenant
     * of its own; it applies to users created while it is in force, and existing users are not
     * backfilled.
     */
    private TenantManagement tenantManagement = TenantManagement.TENANT_ADMINISTRATORS;

    /**
     * Name of the UI whose site this application's browser flows return to, such as its OAuth consent
     * page: one of the application's published UIs, or {@code null} while none is designated. A UI whose site is
     * minted while none is designated becomes the primary UI; the owner can designate another.
     */
    private String primaryUiId;

    /**
     * Where the {@link #primaryUiId primary UI}'s site is served, or {@code null} while none is designated. Set
     * by the platform when the primary UI is designated; a value a caller saves is replaced.
     */
    private String primaryUiUrl;

    /**
     * The platform's social registrations this application's login offers, by provider kind, in the order
     * to present them: Google, Microsoft. The application's users sign in with Kinotic's registration at
     * the provider, so the application registers nothing of its own; the organization's own providers
     * for the application are its {@code ApplicationOidcConfiguration}s. Empty offers none.
     */
    private List<OidcProviderType> socialProviders = new ArrayList<>();

    /**
     * Whether an identity one of the {@link #socialProviders} verifies, which no user of this application is bound
     * to, becomes a user of the application, with a tenant of its own, on its first sign-in. Only an application whose
     * tenants the platform manages ({@link TenantManagement#PLATFORM}) may turn it on.
     */
    private boolean socialSignUp = false;

    /**
     * Whether people become users of this application by signing up with their email and a password, once they
     * open the verification link emailed to them; each gets a tenant of its own. Only an application whose tenants
     * the platform manages ({@link TenantManagement#PLATFORM}) may turn it on.
     */
    private boolean passwordSignUp = false;

    private Date updated = null;

    public Application(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
