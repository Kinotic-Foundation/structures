package org.kinotic.structures.api.domain;

import java.io.Serializable;
import java.util.LinkedList;

public class Structures implements Serializable {
    private LinkedList<StructureHolder> content;
    private long totalElements;

    public Structures(LinkedList<StructureHolder> content, long totalElements) {
        this.content = content;
        this.totalElements = totalElements;
    }

    public LinkedList<StructureHolder> getContent() {
        return content;
    }

    public void setContent(LinkedList<StructureHolder> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
