package org.kinotic.domain.api.services.security;

import io.vertx.core.Future;
import org.kinotic.core.api.crud.IdentifiableCrudService;
import org.kinotic.domain.api.model.security.ApplicationSocialOidcConfiguration;
import org.kinotic.domain.api.model.security.LoginProvider;
import org.kinotic.domain.api.model.security.OidcConfiguration;
import org.kinotic.domain.api.model.security.OrganizationOidcConfiguration;

import java.util.List;

/**
 * Which {@link OidcConfiguration}s each sign-in scope of an organization offers: the organization's own
 * {@link OrganizationOidcConfiguration}s at any of its scopes, and the {@link ApplicationSocialOidcConfiguration}s
 * Kinotic manages at its applications' logins. A row is written by a participant of the organization it
 * names, must name a configuration the organization may offer at that scope and, when it names an
 * application, one of the organization's, and is unique per scope and configuration. The finders serve the
 * sign-in flows, which run before a participant is bound, and check none.
 */
public interface LoginProviderService extends IdentifiableCrudService<LoginProvider, String> {

    /**
     * The enabled configurations a sign-in scope offers, in the order they should be presented. An
     * application scope ({@code applicationId} set) offers the configurations its rows name; the
     * organization scope offers the curated social providers followed by the configurations its rows name.
     * Empty when the scope offers none.
     *
     * @param organizationId the organization the scope belongs to
     * @param applicationId  the application within it, or {@code null} for the organization's own login
     */
    Future<List<OidcConfiguration>> findEnabledForScope(String organizationId, String applicationId);

    /**
     * The enabled configuration {@code configurationId} when the scope offers it, or {@code null} when the
     * scope does not offer it or the configuration is disabled.
     *
     * @param configurationId the configuration a sign-in names
     * @param organizationId  the organization the scope belongs to
     * @param applicationId   the application within it, or {@code null} for the organization's own login
     */
    Future<OidcConfiguration> findOffered(String configurationId, String organizationId, String applicationId);
}
