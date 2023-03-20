/*
 *
 * Copyright 2008-2021 Kinotic and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kinotic.structures.internal.api.services.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.xcontent.XContentType;
import org.elasticsearch.core.Tuple;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.Map;

public class EsHighLevelClientUtil {

    private final static ObjectMapper mapper = new ObjectMapper();

    public static SearchSourceBuilder buildGeneric(int numberPerPage, int page, String columnToSortBy, boolean descending){
        SearchSourceBuilder builder = new SearchSourceBuilder()
                .from(page*numberPerPage)
                .size(numberPerPage);

        if(columnToSortBy != null){
            SortOrder order;
            if(descending){
                order = SortOrder.ASC;
            }else{
                order = SortOrder.DESC;
            }
            builder.sort(columnToSortBy, order);
        }

        return builder;
    }

    public static <T> T getTypeFromBytesReference(BytesReference bytes, Class<T> clazz){
        Tuple<XContentType, Map<String, Object>> linkedHashMap = XContentHelper.convertToMap(bytes, true, XContentType.JSON);
        linkedHashMap.v2().remove("_class");
        return mapper.convertValue(linkedHashMap.v2(), clazz);
    }
}
