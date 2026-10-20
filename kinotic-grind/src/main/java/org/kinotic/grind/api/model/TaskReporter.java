package org.kinotic.grind.api.model;

import org.kinotic.grind.api.model.events.TaskProgressEvent;

/**
 * Reports what a running task is doing to watchers of the run, as {@link TaskProgressEvent}s:
 * its progress, and the {@link TaskView} it shows. Each task is given a reporter of its own in
 * the scope it executes in, so it injects it like any other dependency - an {@code @Autowired}
 * field of the task's callable, or a parameter of an annotated task method; a tasks class, one
 * instance for all its task methods, takes it as a method parameter. A reporter accepts reports
 * while its task is in flight - until the task's result has been awaited - from the task body and
 * from callbacks dispatched on the run's Vert.x context, where the callbacks of futures bound to
 * it run; a report made after that, or from a thread the task spawned, is dropped.
 */
public interface TaskReporter {

    /**
     * Reports the calling task's progress. Progress reaches watchers only; it is not recorded.
     * @param percentageComplete how close the task is to completion, 0 to 100
     * @param message what the task is currently doing, or null
     */
    void progress(int percentageComplete, String message);

    /**
     * Replaces the view the calling task shows. The task's record keeps the latest view.
     * @param view the view to show
     */
    void view(TaskView<?> view);

}
