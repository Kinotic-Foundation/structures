package org.kinotic.structuresserver.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class SearchHitsSerializer extends JsonSerializer<SearchHits> {

    @Override
    public void serialize(SearchHits value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("totalElements", value.getTotalHits().value);
        jsonGenerator.writeArrayFieldStart("content");
        boolean first = true;
        for (SearchHit hit : value) {
            if(!first){
                jsonGenerator.writeRaw(",");
            }
            jsonGenerator.writeRaw(hit.getSourceAsString());
            first = false;
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }

}
