package org.kinotic.grind.internal.api.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.kinotic.grind.api.model.JobContext;
import org.kinotic.grind.api.model.Store;
import org.kinotic.grind.api.model.Task;
import org.kinotic.grind.internal.model.TaskBody;

/**
 * The {@link Task} every factory builds: a {@link TaskBody} with the settings the fluent methods
 * give it, each of which returns a copy.
 *
 * @param <R> the result type
 */
@RequiredArgsConstructor
public final class DefaultTask<R> implements Task<R> {

    @Getter
    private final String description;

    @Getter
    private final Store store;

    private final TaskBody<R> body;

    /**
     * Creates a task that declares no {@link Store} of its own.
     * @param description of the task
     * @param body the work it performs
     * @param <R> the result type
     * @return the task
     */
    public static <R> DefaultTask<R> of(String description, TaskBody<R> body) {
        Validate.notNull(body, "body cannot be null");
        return new DefaultTask<>(description, null, body);
    }

    @Override
    public Task<R> store(Store store) {
        Validate.notNull(store, "store cannot be null");
        return new DefaultTask<>(description, store, body);
    }

    @Override
    public R execute(JobContext context) throws Exception {
        return body.execute(context);
    }

}
