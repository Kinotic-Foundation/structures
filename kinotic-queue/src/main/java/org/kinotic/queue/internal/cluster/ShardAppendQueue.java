package org.kinotic.queue.internal.cluster;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.kinotic.queue.internal.cluster.message.ShardId;

import java.util.ArrayDeque;

/**
 * The appends one client sends to one shard of one queue incarnation: those waiting for the next batch, whether a batch
 * is on its way, and the sequence the next batch gets.
 */
@Getter
@Setter
@RequiredArgsConstructor
final class ShardAppendQueue {

    private final ShardId shardId;
    private final ArrayDeque<QueuedAppend> waiting = new ArrayDeque<>();
    private boolean inFlight;
    private long nextSequence;
}
