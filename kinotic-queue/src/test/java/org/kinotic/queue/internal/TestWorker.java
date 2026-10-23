package org.kinotic.queue.internal;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.kinotic.queue.api.model.WorkItem;
import org.kinotic.queue.api.model.WorkerOptions;
import org.kinotic.queue.api.services.QueueService;
import org.kinotic.queue.api.services.QueueWorker;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/**
 * Drives a {@link QueueWorker} from the test thread and collects the items leased to it.
 */
public final class TestWorker extends TestStream<QueueWorker, WorkItem> {

    private TestWorker(Vertx vertx, QueueService service, String queue, String groupName, WorkerOptions options) throws Exception {
        super(vertx, () -> service.work(queue, groupName, options));
    }

    public static TestWorker start(Vertx vertx,
                                   QueueService service,
                                   String queue,
                                   String groupName,
                                   WorkerOptions options) throws Exception {
        return new TestWorker(vertx, service, queue, groupName, options);
    }

    public static int value(WorkItem item) {
        return QueueTestSupport.value(item.record());
    }

    /**
     * @return the values leased until none arrives for a while
     */
    public Set<Integer> takeUntilQuiet() throws InterruptedException {
        Set<Integer> ret = new HashSet<>();
        WorkItem item = poll(30_000);
        while (item != null) {
            ret.add(value(item));
            item = poll(1_500);
        }
        return ret;
    }

    public Future<Void> settle(BiFunction<QueueWorker, WorkItem, Future<Void>> settlement, WorkItem item) throws Exception {
        return call(worker -> settlement.apply(worker, item));
    }

    /**
     * @return whether the accept succeeded; it fails when the item's lease ended with its shard's owner, and the item
     * is then leased again
     */
    public boolean tryAccept(WorkItem item) throws Exception {
        return settle(QueueWorker::accept, item).toCompletionStage().toCompletableFuture().handle((v, e) -> e == null).get(30, TimeUnit.SECONDS);
    }

    public Future<Void> close() throws Exception {
        return call(QueueWorker::close);
    }
}
