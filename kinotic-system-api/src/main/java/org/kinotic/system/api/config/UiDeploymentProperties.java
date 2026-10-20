package org.kinotic.system.api.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Where the platform stores each published UI, bound under {@code kinotic.systemApi.uiDeployment.*}. The domain its
 * sites are served under is {@code kinotic.domain.publishedSites.domain}. The storage endpoint is validated at boot,
 * so an environment without Azure storage still sets it, to a placeholder.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class UiDeploymentProperties {

    /**
     * Blob endpoint of the sites storage account every published UI is written to, under
     * {@code sites/<hostname>/}, from which the edge serves it.
     */
    @NotBlank
    private String sitesStorageEndpoint;
}
