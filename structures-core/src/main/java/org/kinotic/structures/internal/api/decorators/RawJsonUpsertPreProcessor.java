package org.kinotic.structures.internal.api.decorators;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.async.ByteArrayFeeder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.decorators.IdDecorator;
import org.kinotic.structures.api.decorators.MultiTenancyType;
import org.kinotic.structures.api.decorators.runtime.crud.UpsertFieldPreProcessor;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.RawJson;
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
public class RawJsonUpsertPreProcessor implements UpsertPreProcessor<RawJson> {

    private static final Logger log = LoggerFactory.getLogger(RawJsonUpsertPreProcessor.class);

    private final StructuresProperties structuresProperties;
    private final ObjectMapper objectMapper;
    private final Structure structure;
    // Map of json path to decorator logic
    private final Map<String, DecoratorLogic> fieldPreProcessors;


    public RawJsonUpsertPreProcessor(StructuresProperties structuresProperties,
                                     ObjectMapper objectMapper,
                                     Structure structure,
                                     Map<String, DecoratorLogic> fieldPreProcessors) {
        this.structuresProperties = structuresProperties;
        this.objectMapper = objectMapper;
        this.structure = structure;
        this.fieldPreProcessors = fieldPreProcessors;
    }

    @Override
    public CompletableFuture<EntityHolder<RawJson>> process(RawJson entity, EntityContext context) {

        Deque<String> jsonPathStack = new ArrayDeque<>();
        String id = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes = entity.data();

        try(JsonParser jsonParser = objectMapper.createNonBlockingByteArrayParser();
            JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputStream, JsonEncoding.UTF8)) {
            ByteArrayFeeder feeder = (ByteArrayFeeder) jsonParser.getNonBlockingInputFeeder();
            feeder.feedInput(bytes, 0, bytes.length);
            feeder.endOfInput();

            // if this is a multi tenant structure the first thing to do is add the tenant id
            if(structure.getMultiTenancyType() == MultiTenancyType.SHARED){
                if(jsonParser.nextToken() == JsonToken.START_OBJECT) {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeFieldName(structuresProperties.getTenantIdFieldName());
                    jsonGenerator.writeString(context.getParticipant().getTenantId());
                }else{
                    throw new IllegalStateException("Expected start object token");
                }
            }

            while (jsonParser.nextToken() != null) {

                JsonToken token = jsonParser.getCurrentToken();

                if (token == JsonToken.FIELD_NAME) {

                    String fieldName = jsonParser.getCurrentName();

                    String currentJsonPath = !jsonPathStack.isEmpty() ? jsonPathStack.peekFirst() + "." + fieldName : fieldName;
                    jsonPathStack.addFirst(currentJsonPath);

                    DecoratorLogic preProcessorLogic = fieldPreProcessors.get(currentJsonPath);

                    if(preProcessorLogic != null){

                        jsonParser.nextToken(); // move to value token

                        C3Decorator decorator = preProcessorLogic.getDecorator();
                        UpsertFieldPreProcessor<C3Decorator, Object, Object> preProcessor = preProcessorLogic.getProcessor();
                        Object input = objectMapper.readValue(jsonParser, preProcessor.supportsFieldType());
                        Object value = preProcessor.process(structure, fieldName, decorator, input, context);

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
                return CompletableFuture.failedFuture(new IllegalArgumentException("No id field found in entity data"));
            }else{
                return CompletableFuture.completedFuture(new EntityHolder<>(id, new RawJson(outputStream.toByteArray())));
            }

        } catch (IOException e) {
            return CompletableFuture.failedFuture(e);
        }
    }

}
