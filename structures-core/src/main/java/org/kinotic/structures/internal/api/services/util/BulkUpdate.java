package org.kinotic.structures.internal.api.services.util;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.kinotic.structures.api.domain.Structure;

public class BulkUpdate {
    private BulkProcessor bulkProcessor;
    private Structure structure;

    public BulkUpdate(BulkProcessor bulkProcessor, Structure structure) {
        this.bulkProcessor = bulkProcessor;
        this.structure = structure;
    }

    public BulkProcessor getBulkProcessor() {
        return bulkProcessor;
    }

    public void setBulkProcessor(BulkProcessor bulkProcessor) {
        this.bulkProcessor = bulkProcessor;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }
}
