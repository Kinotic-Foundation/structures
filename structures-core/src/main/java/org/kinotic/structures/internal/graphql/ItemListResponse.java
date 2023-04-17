package org.kinotic.structures.internal.graphql;

import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Navíd Mitchell 🤪 on 4/17/23.
 */
public class ItemListResponse {

    private final SearchHits searchHits;
    private List<Map<String, Object>> content;

    public ItemListResponse(SearchHits searchHits) {
        this.searchHits = searchHits;
    }

    public long getTotalElements() {
        return searchHits.getTotalHits().value;
    }

    public List<Map<String, Object>> getContent() {
        if(content == null){
            content = new ArrayList<>();
            for(SearchHit hit : searchHits){
                content.add(hit.getSourceAsMap());
            }
        }
        return content;
    }

}
