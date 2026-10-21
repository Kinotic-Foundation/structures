package org.kinotic.grind;

import org.kinotic.grind.api.annotations.Task;

/**
 * Tasks class declaring an icon on one task and leaving the other at the console's default.
 */
public class IconTasks {

    @Task(order = 1, value = "checkout", icon = "folder-git-2")
    public void checkout() {
    }

    @Task(order = 2, value = "plain")
    public void plain() {
    }

}
