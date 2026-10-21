package org.kinotic.grind;

import org.kinotic.grind.api.annotations.Task;
import org.kinotic.grind.api.model.TaskIcon;

/**
 * Tasks class declaring an icon on one task and leaving the other at the console's default.
 */
public class IconTasks {

    @Task(order = 1, value = "checkout", icon = TaskIcon.GIT)
    public void checkout() {
    }

    @Task(order = 2, value = "plain")
    public void plain() {
    }

}
