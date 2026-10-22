package org.kinotic.system.internal.api.services.workload;

import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.internal.VertxInternal;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteServices;
import org.apache.ignite.resources.SpringResource;
import org.apache.ignite.services.Service;
import org.kinotic.core.api.event.ZonePartitioningService;
import org.kinotic.domain.api.utils.DomainUtil;

import java.util.concurrent.ExecutionException;

/**
 * Runs the {@link WorkloadRetentionSweeper} as one HA cluster singleton on the Ignite service grid,
 * once when it starts, hourly after, and whenever {@link #sweep()} is called through its proxy. It
 * runs one sweep at a time: a sweep requested while one is running joins it.
 */
@Slf4j
public class WorkloadCleanupService implements Service, WorkloadRetentionService {

    private static final String SINGLETON_NAME = "workload-cleanup";

    private static final long SWEEP_MS = 3_600_000;

    // Injected by Ignite on the node elected to host the singleton
    @SpringResource(resourceClass = WorkloadRetentionSweeper.class)
    private transient WorkloadRetentionSweeper sweeper;
    @SpringResource(resourceClass = Vertx.class)
    private transient Vertx vertx;

    private transient long timerId;
    // The context sweeps run on: one of the singleton's own, since a caller of sweep() blocks its thread
    // until the sweep ends, and a sweep queued on that caller's context would wait behind it forever
    private transient Context sweepContext;
    // The sweep in progress, null between sweeps; guarded by this
    private transient Future<Void> inFlight;

    /**
     * Deploys the singleton; Ignite elects its host among the nodes hosting system-api, the only ones
     * whose context holds the beans the sweep is injected with.
     *
     * @param ignite the local node
     */
    static void deploy(Ignite ignite) {
        services(ignite).deployClusterSingleton(SINGLETON_NAME, new WorkloadCleanupService());
    }

    /**
     * @param ignite the local node
     * @return a proxy calling the singleton wherever it is hosted
     */
    static WorkloadRetentionService proxy(Ignite ignite) {
        // Ignite proxies a service only through an interface the service implements, here WorkloadRetentionService
        return services(ignite).serviceProxy(SINGLETON_NAME, WorkloadRetentionService.class, false);
    }

    private static IgniteServices services(Ignite ignite) {
        return ignite.services(ignite.cluster().forAttribute(ZonePartitioningService.hostsAttribute(DomainUtil.SYSTEM_API_ZONE), true));
    }

    @Override
    public void init() {
        log.info("Starting workload cleanup singleton");
        sweepContext = ((VertxInternal) vertx).createEventLoopContext();
        startSweep();
        timerId = vertx.setPeriodic(SWEEP_MS, id -> startSweep());
    }

    @Override
    public void execute() {
        // passive service: all work is driven by the timer started in init and by calls through the proxy
    }

    @Override
    public void cancel() {
        log.info("Stopping workload cleanup singleton");
        vertx.cancelTimer(timerId);
    }

    @Override
    public void sweep() {
        // the calling thread blocks until the sweep it started or joined has ended, or until it is
        // interrupted, which leaves the sweep running
        try {
            startSweep().toCompletionStage().toCompletableFuture().get();
        } catch (ExecutionException e) {
            // the exception travels back to the calling node, so it carries the cause as text, which always serializes
            throw new IllegalStateException("Workload cleanup failed: " + e.getCause());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for the workload cleanup to end");
        }
    }

    private synchronized Future<Void> startSweep() {
        if (inFlight == null) {
            Promise<Void> sweep = Promise.promise();
            // assigned before the sweep starts, so its completion always finds it to clear
            inFlight = sweep.future();
            inFlight.onComplete(result -> sweepEnded(sweep.future()));
            // Future.future fails the sweep when sweeper.sweep() throws instead of returning a failed future,
            // so inFlight is always cleared
            sweepContext.runOnContext(v -> Future.<Void>future(promise -> sweeper.sweep().onComplete(promise))
                                                 .onFailure(error -> log.error("Workload cleanup failed; what it left is taken by the next run", error))
                                                 .onComplete(sweep));
        }
        return inFlight;
    }

    private synchronized void sweepEnded(Future<Void> sweep) {
        if (inFlight == sweep) {
            inFlight = null;
        }
    }
}
