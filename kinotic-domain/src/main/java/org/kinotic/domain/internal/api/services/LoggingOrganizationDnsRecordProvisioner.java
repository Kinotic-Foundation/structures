package org.kinotic.domain.internal.api.services;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kinotic.domain.api.config.KinoticDomainProperties;
import org.kinotic.domain.api.services.OrganizationDnsRecordProvisioner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * The {@link OrganizationDnsRecordProvisioner} of an environment with no DNS zone,
 * {@code kinotic.domain.publishedSites.cloudflare.enabled=false}: logs the record each organization would get
 * and creates none, so sign-up and publishing run where no site is ever served, such as a developer's
 * machine or the test suite.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "kinotic.domain.publishedSites.cloudflare.enabled", havingValue = "false", matchIfMissing = true)
public class LoggingOrganizationDnsRecordProvisioner implements OrganizationDnsRecordProvisioner {

    private final KinoticDomainProperties properties;

    @Override
    public Future<Void> ensure(String organizationId) {
        log.info("No DNS zone is configured; organization {} would be served under *.{}.{}",
                 organizationId, organizationId, properties.getDomain().getPublishedSites().getDomain());
        return Future.succeededFuture();
    }
}
