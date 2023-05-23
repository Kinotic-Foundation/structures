package org.kinotic.structures.api.domain;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Class is used to represent raw json data
 * Created by Navíd Mitchell 🤪 on 5/22/23.
 */
public final class RawJson {

    private byte[] data;

    public RawJson() {
    }

    public RawJson(ByteBuffer buffer) {
        if(buffer.hasArray()){
            this.data = buffer.array();
        }else{
            this.data = new byte[buffer.remaining()];
            buffer.get(this.data);
        }
    }

    public RawJson(byte[] data) {
        this.data = data;
    }

    public byte[] data() {
        return data;
    }

    public ByteBuffer byteBuffer() {
        return ByteBuffer.wrap(data);
    }

    public static RawJson of(byte[] data) {
        return new RawJson(data);
    }

    public static RawJson of(ByteBuffer buffer) {
        return new RawJson(buffer);
    }

    /**
     * Helper method to get a RawJson from a JsonParser
     * @param parser the parser to get the raw json from
     * @param objectMapper the object mapper to use
     * @return a RawJson instance
     * @throws IOException if there is an error parsing the json
     */
    public static RawJson fromParser(JsonParser parser,
                                     ObjectMapper objectMapper) throws IOException {
        if(parser.currentToken() != JsonToken.START_ARRAY
                && parser.currentToken() != JsonToken.START_OBJECT) {
            throw new JsonParseException(parser, "The root of a RawJson must be an array or object", parser.getCurrentLocation());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try(JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputStream, JsonEncoding.UTF8)) {

            jsonGenerator.copyCurrentStructure(parser);

            jsonGenerator.flush();
            return new RawJson(outputStream.toByteArray());
        }
    }

}
