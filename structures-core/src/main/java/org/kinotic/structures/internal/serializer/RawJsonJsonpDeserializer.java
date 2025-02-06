package org.kinotic.structures.internal.serializer;

import co.elastic.clients.json.JsonpDeserializer;
import co.elastic.clients.json.JsonpDeserializerBase;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.jackson.JacksonJsonpParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import jakarta.json.stream.JsonParser;
import org.kinotic.structures.api.domain.RawJson;

import java.io.IOException;
import java.util.EnumSet;

/**
 * Created by Navíd Mitchell 🤪 on 5/22/23.
 */
public class RawJsonJsonpDeserializer extends JsonpDeserializerBase<RawJson> implements JsonpDeserializer<RawJson> {

    private final ObjectMapper objectMapper;

    public RawJsonJsonpDeserializer(ObjectMapper objectMapper) {
        super(EnumSet.allOf(JsonParser.Event.class));
        this.objectMapper = objectMapper;
    }

    @Override
    public RawJson deserialize(JsonParser parser, JsonpMapper mapper) {
        JacksonJsonpParser jacksonJsonpParser;
        if (parser instanceof JacksonJsonpParser) {
            jacksonJsonpParser = (JacksonJsonpParser) parser;
        } else {
            throw new IllegalStateException("Expected JacksonJsonpParser but got " + parser.getClass().getName());
        }
        try {

            com.fasterxml.jackson.core.JsonParser jacksonParser = jacksonJsonpParser.jacksonParser();
            if (jacksonParser.currentToken() == JsonToken.FIELD_NAME
                    && jacksonParser.currentName().equals("_source")) { // What other cases are there?
                jacksonParser.nextToken();
            }

            return RawJson.from(jacksonParser, objectMapper);

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public RawJson deserialize(JsonParser parser, JsonpMapper mapper, JsonParser.Event event) {
        throw new UnsupportedOperationException();
    }
}
