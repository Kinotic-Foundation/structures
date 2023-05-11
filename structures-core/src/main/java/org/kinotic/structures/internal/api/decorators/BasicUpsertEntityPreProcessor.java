package org.kinotic.structures.internal.api.decorators;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.async.ByteArrayFeeder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.decorators.IdDecorator;
import org.kinotic.structures.api.decorators.lifecycle.UpsertFieldPreProcessor;
import org.kinotic.structures.api.domain.Structure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 5/5/23.
 */
public class BasicUpsertEntityPreProcessor implements UpsertEntityPreProcessor {

    private static final Logger log = LoggerFactory.getLogger(BasicUpsertEntityPreProcessor.class);

    private final ObjectMapper objectMapper;
    private final Structure structure;
    // Map of json path to decorator logic
    private final Map<String, DecoratorLogic<C3Decorator, Object, Object,
                                             UpsertFieldPreProcessor<C3Decorator, Object, Object>>> fieldPreProcessors;


    public BasicUpsertEntityPreProcessor(ObjectMapper objectMapper,
                                         Structure structure,
                                         Map<String, DecoratorLogic<C3Decorator, Object, Object,
                                                 UpsertFieldPreProcessor<C3Decorator, Object, Object>>> fieldPreProcessors) {
        this.objectMapper = objectMapper;
        this.structure = structure;
        this.fieldPreProcessors = fieldPreProcessors;
    }

    @Override
    public CompletableFuture<RawEntity> process(byte[] bytes) {
        Deque<String> jsonPathStack = new ArrayDeque<>();
        String id = null;
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

                        jsonParser.nextToken(); // move to value token

                        C3Decorator decorator = preProcessorLogic.getDecorator();
                        UpsertFieldPreProcessor<C3Decorator, Object, Object> preProcessor = preProcessorLogic.getProcessor();
                        Object input = objectMapper.readValue(jsonParser, preProcessor.getFieldType());
                        Object value = preProcessor.process(structure, fieldName, decorator, input);

                        if(value != null) {
                            jsonGenerator.writeFieldName(fieldName);
                            jsonGenerator.writeObject(value);

                            // We hard code the id logic here, in the future we may create a more general approach if the need arises
                            if(decorator instanceof IdDecorator){
                                id = (String) value;
                            }
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

            if(id == null){
                return CompletableFuture.failedFuture(new IllegalArgumentException("No id field found in entity"));
            }else{
                // FIXME: remove
                byte[] entityBytes = outputStream.toByteArray();
                log.debug("Json\n"+ new String(entityBytes));
                return CompletableFuture.completedFuture(new RawEntity(id, entityBytes));
            }

        } catch (IOException e) {
            return CompletableFuture.failedFuture(e);
        }
    }

}
