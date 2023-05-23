package org.kinotic.structures.internal.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kinotic.structures.api.domain.RawJson;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * Created by Navíd Mitchell 🤪 on 5/22/23.
 */
@JsonComponent
public class RawJsonDeserializer extends JsonDeserializer<RawJson> {

    private final ObjectMapper objectMapper;

    public RawJsonDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public RawJson deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JacksonException {
        return RawJson.fromParser(jsonParser, objectMapper);
    }

}
