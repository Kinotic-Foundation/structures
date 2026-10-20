package org.kinotic.grind.internal.model;

import org.apache.commons.lang3.Validate;
import org.kinotic.grind.internal.api.model.DefaultTask;
import org.kinotic.grind.api.model.Store;
import org.kinotic.grind.api.model.Task;

/**
 * A task that executes one {@link Task}.
 *
 * @param sequence the node's position within its parent definition
 * @param task     the task to execute
 * @param store    how the task's result is kept: the task's own {@link Store}, or the one it
 *                 inherits when it declares none
 */
public record TaskNode(int sequence,
                       Task<?> task,
                       Store store) implements JobNode {

    public TaskNode {
        // the views a task declares are read off DefaultTask, so a task must be built by the Tasks factories
        Validate.isInstanceOf(DefaultTask.class, task, "Tasks are built with the Tasks factories, not by implementing Task");
    }

    @Override
    public String description() {
        return task.getDescription();
    }

    /**
     * @return the key of the icon a console shows for the task, or null for its default
     */
    public String icon() {
        return task.getIcon();
    }

    /**
     * @return the view the task shows from its start, or null when it declares none
     */
    public ViewProducer startView() {
        return ((DefaultTask<?>) task).getStartView();
    }

    /**
     * @return the view the task shows once it completes, or null when it declares none
     */
    public ViewProducer resultView() {
        return ((DefaultTask<?>) task).getResultView();
    }

}
