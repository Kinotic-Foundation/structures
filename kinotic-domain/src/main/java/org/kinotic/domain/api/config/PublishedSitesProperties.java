package org.kinotic.domain.api.config;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Where organizations' published sites are hosted, bound under {@code kinotic.domain.publishedSites.*}: the
 * domain every site is under, {@code <app>--<ui>.<org>.<domain>}, and the Cloudflare DNS zone of that domain,
 * in which the platform creates the record each organization's sites resolve by.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class PublishedSitesProperties {

    /**
     * The domain every organization's sites are under, e.g. {@code kinotic.io}: the same value the system
     * server serves sites under as {@code kinotic.systemApi.uiDeployment.sitesDomain}. A subdomain of the
     * edge's zone names the environment, e.g. {@code dev-nm.kinotic.io}.
     */
    private String domain;

    /**
     * The Cloudflare zone the domain is in, where each organization's record is created.
     */
    @Valid
    private CloudflareDnsProperties cloudflare = new CloudflareDnsProperties();
}
