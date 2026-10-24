package org.kinotic.grind;

import org.kinotic.grind.api.annotations.Task;

/**
 * Tasks class inheriting a reporter field, which a tasks class may not hold.
 */
public class InheritedReporterFieldTasks extends ReporterFieldTasks {

    @Task(order = 2, value = "more work")
    public void more() {
    }

}
