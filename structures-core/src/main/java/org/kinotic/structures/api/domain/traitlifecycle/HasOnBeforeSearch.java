package org.kinotic.structures.api.domain.traitlifecycle;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.kinotic.structures.api.domain.Structure;

import java.util.Map;

public interface HasOnBeforeSearch {
    BoolQueryBuilder beforeSearch(BoolQueryBuilder builder, Structure structure, String fieldName, Map<String, Object> context) throws Exception;
}
