package org.kinotic.queue.internal;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.kinotic.queue.api.model.QueueRecord;
import org.kinotic.queue.api.model.StartPosition;
import org.kinotic.queue.api.services.QueueService;
import org.kinotic.queue.api.services.QueueSubscription;

/**
 * Drives a {@link QueueSubscription} from the test thread and collects the records it delivers.
 */
public final class TestSubscriber extends TestStream<QueueSubscription, QueueRecord> {

    private TestSubscriber(Vertx vertx, QueueService service, String queue, String consumerName, StartPosition startPosition) throws Exception {
        super(vertx, () -> service.subscribe(queue, consumerName, startPosition));
    }

    public static TestSubscriber subscribe(Vertx vertx,
                                           QueueService service,
                                           String queue,
                                           String consumerName,
                                           StartPosition startPosition) throws Exception {
        return new TestSubscriber(vertx, service, queue, consumerName, startPosition);
    }

    public Future<Void> commit(QueueRecord record) throws Exception {
        return call(subscription -> subscription.commit(record));
    }

    public Future<Void> close() throws Exception {
        return call(QueueSubscription::close);
    }
}
