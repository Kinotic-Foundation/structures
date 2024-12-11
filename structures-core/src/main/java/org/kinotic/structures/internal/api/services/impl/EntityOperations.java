package org.kinotic.structures.internal.api.services.impl;

public enum EntityOperations {

    BULK_SAVE("bulkSave"),
    BULK_UPDATE("bulkUpdate"),
    COUNT("count"),
    COUNT_BY_QUERY("countByQuery"),
    DELETE_BY_ID("deleteById"),
    DELETE_BY_QUERY("deleteByQuery"),
    FIND_ALL("findAll"),
    FIND_BY_ID("findById"),
    FIND_BY_IDS("findByIds"),
    NAMED_QUERY("namedQuery"),
    NAMED_QUERY_PAGE("namedQueryPage"),
    SYNC_INDEX("syncIndex"),
    SAVE("save"),
    SEARCH("search"),
    UPDATE("update");

    private final String methodName;

    EntityOperations(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }
}

