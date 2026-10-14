package org.kinotic.domain.api.services.security;

import io.vertx.core.Future;
import org.kinotic.core.api.crud.IdentifiableCrudService;
import org.kinotic.domain.api.model.security.ApplicationSocialOidcConfiguration;
import org.kinotic.domain.api.model.security.ManagedOidcConfiguration;
import org.kinotic.domain.api.model.security.OidcConfiguration;
import org.kinotic.domain.api.model.security.OidcProviderKind;
import org.kinotic.domain.api.model.security.OrganizationOidcConfiguration;
import org.kinotic.domain.api.model.security.OrgSignupOidcConfiguration;

import java.util.List;

/**
 * The OIDC provider configurations users sign up and in through: the platform's managed registrations
 * and each organization's own. A write is held to its kind's owner: a {@link ManagedOidcConfiguration}
 * is saved or deleted by a system participant only,
 * an {@link OrganizationOidcConfiguration} by an organization participant of the organization it
 * names. An organization's configuration must name a provider kind
 * {@link OidcProviderKind#offeredToOrganizations() offered to organizations} and its account there in the
 * form the kind expects; its authority is derived from the two on every save. The finders that serve
 * the sign-in flows, which run before a participant is bound, check no participant and say so.
 */
public interface OidcConfigurationService extends IdentifiableCrudService<OidcConfiguration, String> {

    /**
     * Every enabled registration offered to organization sign-up — the source of the social button list
     * rendered on the organization signup and login pages. Checks no participant.
     */
    Future<List<OrgSignupOidcConfiguration>> findEnabledOrgSignupProviders();

    /**
     * The single enabled org sign-up registration of the given provider kind, or {@code null} when none
     * is configured, for the social-button start endpoints resolving the {@code :provider} path param.
     * Checks no participant.
     */
    Future<OrgSignupOidcConfiguration> findEnabledOrgSignupByProvider(OidcProviderKind provider);

    /**
     * The org sign-up registration {@code id}, enabled or not, or {@code null} when no
     * {@link OrgSignupOidcConfiguration} has that id, for the social callbacks. Checks no participant.
     *
     * @param id the configuration id
     */
    Future<OrgSignupOidcConfiguration> findOrgSignupById(String id);

    /**
     * Fetches the given OIDC configurations in a single request, returning only those organization
     * {@code orgId} may offer at a sign-in — its own and the {@link ApplicationSocialOidcConfiguration}s —
     * and whose {@code enabled} flag is true. Missing, disabled and foreign configurations are silently
     * omitted. Checks no participant.
     *
     * @param ids the configuration ids to load; must be non-null and non-empty
     * @param orgId the organization offering the configurations
     * @return the enabled configurations
     */
    Future<List<OidcConfiguration>> findEnabledByIds(List<String> ids, String orgId);

    /**
     * The configuration {@code id} that organization {@code organizationId} may offer at a sign-in, enabled
     * or not: its own {@link OrganizationOidcConfiguration}, or an {@link ApplicationSocialOidcConfiguration}.
     * {@code null} when no such configuration has that id. Checks no participant.
     *
     * @param id             the configuration id
     * @param organizationId the organization offering the configuration
     */
    Future<OidcConfiguration> findOfferable(String id, String organizationId);

    /**
     * The OIDC configuration {@code id} of organization {@code organizationId}, enabled or not, or
     * {@code null} when the organization has none by that id. Checks no participant.
     *
     * @param id             the configuration id
     * @param organizationId the organization that owns the configuration
     */
    Future<OrganizationOidcConfiguration> findById(String id, String organizationId);

    /**
     * Saves the configuration as {@link #save(Object)} does and makes {@code clientSecret} the secret it
     * sends its provider, replacing the one it had. The secret is held in the owning organization's scope
     * of the secret storage, keyed by the configuration's id, so it is never a field of the configuration
     * and no other organization's configuration can read it; the configuration's
     * {@code confidentialClient} records whether one is held. A sign-in through the configuration picks
     * the new secret up on its next flow.
     *
     * @param configuration the configuration, owned by the caller's organization
     * @param clientSecret  the client secret's value, or {@code null} or blank for a public client, whose
     *                      stored secret is deleted
     * @return the configuration as saved
     */
    Future<OrganizationOidcConfiguration> save(OrganizationOidcConfiguration configuration, String clientSecret);

    /**
     * The client secret of {@code configuration}, or {@code null} for a public client, which the
     * configuration's {@code confidentialClient} tells without a read of the secret storage. Reads the
     * owning organization's scope of the secret storage only. Checks no participant.
     *
     * @param configuration the configuration as persisted, naming its organization
     */
    Future<String> findClientSecret(OrganizationOidcConfiguration configuration);
}
