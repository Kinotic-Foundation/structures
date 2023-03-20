package org.kinotic.structuresserver.serializer

import org.kinotic.structuresserver.domain.StructureHolder

class Structures implements Serializable {
    private LinkedList<StructureHolder> content
    private long totalElements

    Structures(LinkedList<StructureHolder> content, long totalElements) {
        this.content = content
        this.totalElements = totalElements
    }

    LinkedList<StructureHolder> getContent() {
        return content
    }

    void setContent(LinkedList<StructureHolder> content) {
        this.content = content
    }

    long getTotalElements() {
        return totalElements
    }

    void setTotalElements(long totalElements) {
        this.totalElements = totalElements
    }
}
