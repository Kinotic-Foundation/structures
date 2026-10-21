package org.kinotic.grind;

import org.kinotic.grind.api.annotations.Task;
import org.kinotic.grind.api.model.TaskReporter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Tasks class holding a reporter in a field, which a tasks class may not do.
 */
public class ReporterFieldTasks {

    @Autowired
    private TaskReporter progress;

    @Task(order = 1, value = "measured work")
    public void work() {
        progress.progress(50, "halfway");
    }

}
