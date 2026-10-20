package org.kinotic.management.api.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * Created By Navíd Mitchell 🤪on 8/25/26
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class ManagementApiProperties {

    @Valid
    private GithubProperties github = new GithubProperties();

    @Valid
    private EntityDefinitionProperties entityDefinition = new EntityDefinitionProperties();

    /**
     * Base URL of the Loki HTTP API the {@code LogService} reads workload logs from; override per
     * environment via {@code kinotic.managementApi.lokiUrl} (env {@code KINOTIC_MANAGEMENTAPI_LOKIURL}).
     */
    @NotBlank
    private String lokiUrl = "http://localhost:3100";

    /**
     * Base URL of the Tempo HTTP API the {@code TelemetryService} searches traces in; override per
     * environment via {@code kinotic.managementApi.tempoUrl} (env {@code KINOTIC_MANAGEMENTAPI_TEMPOURL}).
     */
    @NotBlank
    private String tempoUrl = "http://localhost:3200";

    /**
     * Base URL of the Mimir HTTP API the {@code TelemetryService} queries metrics from, whose
     * Prometheus API is served under {@code /prometheus}; override per environment via
     * {@code kinotic.managementApi.mimirUrl} (env {@code KINOTIC_MANAGEMENTAPI_MIMIRURL}).
     */
    @NotBlank
    private String mimirUrl = "http://localhost:9009";

    /**
     * Base URL of the management server's API as a browser reaches it, under which an organization's login serves
     * the callback of each of the organization's own OIDC configurations of that login, and so the base of the
     * callback URL {@code OwnedOidcConfigurationService} gives them. The management server sets it to its
     * {@code kinotic.managementServer.apiBaseUrl}.
     */
    private String managementServerApiBaseUrl;

    /**
     * Base URL every application's API host is a label under, as a browser reaches it, on whose application host
     * an application's login serves the callback of each of the organization's OIDC configurations of that
     * application or one of its tenants, and so the base of the callback URL {@code OwnedOidcConfigurationService}
     * gives them. The same value the app server serves under {@code kinotic.appServer.apiBaseUrl}.
     */
    private String appApiBaseUrl;


}
