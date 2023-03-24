package org.kinotic.structures.api.domain;

import java.io.Serializable;

public class Namespace implements Serializable {

    private String name = null;
    private String description = null;
    private long updated = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }
}
