package org.kinotic.queue.internal.log;

/**
 * The versions of the positions a {@link QueueLog} stores for one shard: of its consumers' positions and of its worker
 * groups'. Each grows whenever a position it covers changes.
 */
public record PositionsVersion(long consumers, long groups) {

    /**
     * Older than any version, so every position stored is newer.
     */
    public static final PositionsVersion NONE = new PositionsVersion(-1, -1);

    /**
     * @return a number that grows whenever either version does
     */
    public long total() {
        return consumers + groups;
    }
}
