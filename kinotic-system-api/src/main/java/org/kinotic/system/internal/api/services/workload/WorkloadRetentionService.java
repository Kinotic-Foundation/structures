package org.kinotic.system.internal.api.services.workload;

/**
 * The workload retention sweep as the cluster singleton runs it. Ignite routes a service proxy's
 * calls through an interface, so callers on any node reach the singleton through this one.
 */
public interface WorkloadRetentionService {

    /**
     * Runs a sweep on the singleton's node, or joins the sweep it is already running, and returns
     * once that sweep has ended.
     *
     * @throws IllegalStateException when the sweep fails, or when the calling thread is interrupted while
     *         it waits; the sweep then runs to its end
     */
    void sweep();

}
