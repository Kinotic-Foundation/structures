package org.kinotic.domain.api.model.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.domain.api.model.OrganizationScoped;

import java.util.Date;

/**
 * An {@link OrganizationOidcConfiguration} offered at one sign-in scope of its organization. The scope is
 * encoded structurally, as a participant's is: {@link #applicationId} null means the organization's own
 * login, set means that application's login for its users. The same configuration may be offered at many
 * scopes, one row each, so an organization configures a provider once and offers it wherever it serves;
 * a scope offers a provider while a row names it and the configuration is enabled.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class LoginProvider implements OrganizationScoped<String> {

    private String id;

    /**
     * The organization whose sign-in the row belongs to, which also owns the configuration it names.
     */
    private String organizationId;

    /**
     * The application whose login offers the configuration, or {@code null} for the organization's own login.
     */
    private String applicationId;

    /**
     * The id of the {@link OrganizationOidcConfiguration} offered.
     */
    private String configurationId;

    private Date created;

    private Date updated;
}
