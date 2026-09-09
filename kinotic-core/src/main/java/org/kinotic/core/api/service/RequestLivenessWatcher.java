package org.kinotic.core.api.service;

/**
 * Fails a pending RPC request when the node that acknowledged it leaves the cluster. A caller pins each
 * request to the node id its acknowledgement named and settles it once the reply arrives; a request
 * still pending when that node leaves has its {@code onLost} callback run once, on the Vert.x context
 * the request was pinned from.
 *
 * Created by Navíd Mitchell 🤪 on 9/9/26.
 */
public interface RequestLivenessWatcher {

    /**
     * Pins a pending request to the node that acknowledged it.
     * @param correlationId the request's correlation id, unique per request
     * @param nodeId the id the acknowledgement named
     * @param onLost run once if the node leaves the cluster before the request is settled
     */
    void watch(String correlationId, String nodeId, Runnable onLost);

    /**
     * Releases a pinned request. Settling an unknown or already lost request has no effect.
     * @param correlationId the request's correlation id
     */
    void settle(String correlationId);

    /**
     * @return the number of requests currently pinned and not yet settled or lost
     */
    int pendingCount();
}
