package org.kinotic.structures.internal.api.decorators;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.async.ByteArrayFeeder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.decorators.lifecycle.UpsertFieldPreProcessor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 5/5/23.
 */
public class BasicUpsertEntityPreProcessor implements UpsertEntityPreProcessor {

    private final ObjectMapper objectMapper;
    private final Structure structure;
    // Map of json path to decorator logic
    private final Map<String, DecoratorLogic<C3Decorator, Object, Object,
                                             UpsertFieldPreProcessor<C3Decorator, Object, Object>>> fieldPreProcessors = new HashMap<>();


    public BasicUpsertEntityPreProcessor(ObjectMapper objectMapper,
                                         Structure structure) {
        this.objectMapper = objectMapper;
        this.structure = structure;
    }

    @Override
    public CompletableFuture<byte[]> process(byte[] bytes) {
        Deque<String> jsonPathStack = new ArrayDeque<>();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try(JsonParser jsonParser = objectMapper.createNonBlockingByteArrayParser();
            JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputStream, JsonEncoding.UTF8)) {
            ByteArrayFeeder feeder = (ByteArrayFeeder) jsonParser.getNonBlockingInputFeeder();
            feeder.feedInput(bytes, 0, bytes.length);
            feeder.endOfInput();

            while (jsonParser.nextToken() != null) {
                JsonToken token = jsonParser.getCurrentToken();
                if (token == JsonToken.FIELD_NAME) {
                    String fieldName = jsonParser.getCurrentName();

                    String currentJsonPath = !jsonPathStack.isEmpty() ? jsonPathStack.peekFirst() + "." + fieldName : fieldName;
                    jsonPathStack.addFirst(currentJsonPath);
                    DecoratorLogic<C3Decorator, Object, Object,
                            UpsertFieldPreProcessor<C3Decorator, Object, Object>> preProcessorLogic = fieldPreProcessors.get(currentJsonPath);
                    if(preProcessorLogic != null){

                        C3Decorator decorator = preProcessorLogic.getDecorator();
                        UpsertFieldPreProcessor<C3Decorator, Object, Object> preProcessor = preProcessorLogic.getProcessor();
                        Object value = preProcessor.process(structure, fieldName, decorator, jsonParser.currentValue());

                        if(value != null) {
                            jsonGenerator.writeFieldName(fieldName);
                            jsonGenerator.writeObject(value);
                        }else{
                            // skip the field
                            jsonParser.nextToken(); // move to value token
                            // while loop will skip the value
                        }
                    }else{
                        jsonGenerator.copyCurrentEvent(jsonParser);
                    }

                    jsonPathStack.removeFirst();

                }else{
                    jsonGenerator.copyCurrentEvent(jsonParser);
                }
            }

            jsonGenerator.flush();
            return CompletableFuture.completedFuture(outputStream.toByteArray());
        } catch (IOException e) {
            return CompletableFuture.failedFuture(e);
        }
    }

}
