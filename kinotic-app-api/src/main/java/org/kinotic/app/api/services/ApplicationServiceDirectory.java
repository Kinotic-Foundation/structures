package org.kinotic.app.api.services;

import io.vertx.core.Future;
import org.kinotic.core.api.annotations.Publish;
import org.kinotic.core.api.annotations.Version;
import org.kinotic.core.api.annotations.Zone;
import org.kinotic.core.api.directory.ServiceDirectoryEntry;
import org.kinotic.domain.api.utils.DomainUtil;

/**
 * Where an application's runtimes register the services they serve in the platform's service directory, so
 * the directory lists them beside the platform's own: a runtime registers each service as it comes online.
 * The application must belong to the caller's organization, the caller must be a machine granted the right to
 * register the application's services, such as the runtime of one of its deployments, and the entry's zone must
 * be the application's.
 */
@Publish
@Version("1.0.0")
@Zone(DomainUtil.APP_API_ZONE)
public interface ApplicationServiceDirectory {

    /**
     * Registers a service a runtime of the application serves, replacing the entry stored for the service when
     * it differs. The entry names the application, the service's zone and version, whether it is advertised,
     * and its definition with the decorators it declares; the platform owns everything else about the entry.
     *
     * @param entry the service to register, in the application's zone
     * @return a future that completes once the entry is in the directory
     */
    Future<Void> register(ServiceDirectoryEntry entry);

}
