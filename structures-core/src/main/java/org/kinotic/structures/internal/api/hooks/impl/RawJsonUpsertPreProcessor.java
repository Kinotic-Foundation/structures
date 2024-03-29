package org.kinotic.structures.internal.api.hooks.impl;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.async.ByteArrayFeeder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.decorators.AutoGeneratedIdDecorator;
import org.kinotic.structures.api.decorators.IdDecorator;
import org.kinotic.structures.api.decorators.MultiTenancyType;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.hooks.DecoratorLogic;
import org.kinotic.structures.internal.api.hooks.UpsertFieldPreProcessor;
import org.kinotic.structures.internal.api.hooks.UpsertPreProcessor;
import org.kinotic.structures.internal.api.services.EntityHolder;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 5/5/23.
 */
public class RawJsonUpsertPreProcessor implements UpsertPreProcessor<RawJson, RawJson> {

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

    /**
     * Will process the given {@link RawJson} and return a new {@link RawJson} with the appropriate decorators applied
     * Handles both single and array of entities
     * @param json the json to process
     * @param context the context of the entity
     * @return a new {@link RawJson} with the appropriate decorators applied
     */
    private CompletableFuture<List<EntityHolder>> doProcess(RawJson json, EntityContext context, boolean processArray){
        Deque<String> jsonPathStack = new ArrayDeque<>();
        byte[] bytes = json.data();
        List<EntityHolder> ret = new ArrayList<>();
        int objectDepth = 0;
        int arrayDepth = 0;

        try(JsonParser jsonParser = objectMapper.createNonBlockingByteArrayParser()) {
            ByteArrayFeeder feeder = (ByteArrayFeeder) jsonParser.getNonBlockingInputFeeder();
            feeder.feedInput(bytes, 0, bytes.length);
            feeder.endOfInput();

            String currentId = null;
            String currentTenantId = null;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputStream, JsonEncoding.UTF8);

            while (jsonParser.nextToken() != null) {

                JsonToken token = jsonParser.getCurrentToken();

                if (token == JsonToken.FIELD_NAME) {

                    String fieldName = jsonParser.getCurrentName();

                    // if the stack depth and the object depth are the same we are at a new field in the same object so pop the stack
                    if(jsonPathStack.size() == objectDepth){
                        jsonPathStack.removeFirst();
                    }

                    String currentJsonPath = !jsonPathStack.isEmpty() ? jsonPathStack.peekFirst() + "." + fieldName : fieldName;
                    jsonPathStack.addFirst(currentJsonPath);

                    DecoratorLogic preProcessorLogic = fieldPreProcessors.get(currentJsonPath);

                    // Apply preProcessor if any exist
                    if(preProcessorLogic != null){

                        jsonParser.nextToken(); // move to value token

                        C3Decorator decorator = preProcessorLogic.getDecorator();
                        UpsertFieldPreProcessor<C3Decorator, Object, Object> preProcessor = preProcessorLogic.getProcessor();
                        Object input = objectMapper.readValue(jsonParser, preProcessor.supportsFieldType());
                        Object value = preProcessor.process(structure, fieldName, decorator, input, context);

                        if(value != null) {
                            jsonGenerator.writeFieldName(fieldName);
                            jsonGenerator.writeObject(value);
                        }else{
                            jsonGenerator.writeNullField(fieldName);
                        }

                        // We hard code the id logic here, in the future we may create a more general approach if the need arises
                        if(decorator instanceof IdDecorator
                                || decorator instanceof AutoGeneratedIdDecorator){

                            // make sure the id is not null before appending to it
                            if(value == null || ((String)value).isBlank()){
                                throw new IllegalArgumentException("Id field cannot be null or blank");
                            }

                            if(currentId != null){
                                throw new IllegalArgumentException("Found multiple id fields in entity");
                            }

                            // if this is the id we add the special _id field for elasticsearch to use
                            currentId = (String) value;
                        }
                    }else{
                        // Check if this is the tenant id if MultiTenancyType.SHARED is enabled
                        if(structure.getMultiTenancyType() == MultiTenancyType.SHARED
                            && currentJsonPath.equals(structuresProperties.getTenantIdFieldName())){

                            // since the tenant id field is already present check its value to make sure it is null
                            // or matches the logged in tenant
                            jsonParser.nextToken(); // move to value token
                            currentTenantId = objectMapper.readValue(jsonParser, String.class);
                            if(currentTenantId != null && !currentTenantId.equals(context.getParticipant().getTenantId())){
                                throw new IllegalArgumentException("Tenant Id invalid for logged in participant");
                            }

                            jsonGenerator.writeFieldName(fieldName);
                            jsonGenerator.writeObject(currentTenantId);

                        }else{
                            // not tenant so just copy raw data
                            jsonGenerator.copyCurrentEvent(jsonParser);
                        }
                    }

                }else{

                    // End of root level object
                    if(token == JsonToken.END_OBJECT && objectDepth == 1){

                        if(currentId == null){
                            throw new IllegalArgumentException("Could not find id for entity");
                        }

                        // if this is a multi tenant structure add the tenant if necessary
                        if(structure.getMultiTenancyType() == MultiTenancyType.SHARED
                           && currentTenantId == null){
                            jsonGenerator.writeFieldName(structuresProperties.getTenantIdFieldName());
                            jsonGenerator.writeString(context.getParticipant().getTenantId());
                        }

                        // This is the end of the object, so we store the object
                        jsonGenerator.writeEndObject();
                        jsonGenerator.flush();
                        ret.add(new EntityHolder(currentId,
                                                 context.getParticipant().getTenantId(),
                                                 structure.getMultiTenancyType(),
                                                 new RawJson(outputStream.toByteArray())));
                        outputStream.reset();
                        currentId = null;
                        currentTenantId = null;
                    }else{
                        if(!shouldSkipToken(token, jsonParser.getCurrentValue(), arrayDepth, processArray)){
                            jsonGenerator.copyCurrentEvent(jsonParser);
                        }
                    }

                    if(token == JsonToken.START_OBJECT){
                        objectDepth++;
                    }else if(token == JsonToken.END_OBJECT){

                        // if the stack depth and the object depth are the same, the last field is done so pop the stack
                        if(jsonPathStack.size() == objectDepth){
                            jsonPathStack.removeFirst();
                        }

                        objectDepth--;
                    }

                    if(token == JsonToken.START_ARRAY){
                        arrayDepth++;
                    }else if(token == JsonToken.END_ARRAY){
                        arrayDepth--;
                    }
                }
            }

            jsonGenerator.flush();

            return CompletableFuture.completedFuture(ret);

        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    private boolean shouldSkipToken(JsonToken token, Object currentValue, int arrayDepth, boolean processArray){
        boolean ret = false;
        if(processArray) {
            if (token == JsonToken.START_ARRAY && arrayDepth == 0) {
                ret = true;
            } else if (token == JsonToken.END_ARRAY && arrayDepth == 1) {
                ret = true;
            } else if (arrayDepth == 1 && (  ",".equals(currentValue) || " ".equals(currentValue) )) {
                ret = true;
            }
        }
        return ret;
    }

    @Override
    public CompletableFuture<EntityHolder> process(RawJson entity, EntityContext context) {
        return doProcess(entity, context, false).thenApply(list -> {
            if(list.size() != 1){
                throw new IllegalStateException("Expected exactly one entity to be returned");
            }
            return list.get(0);
        });
    }

    @Override
    public CompletableFuture<List<EntityHolder>> processArray(RawJson entities, EntityContext context) {
        return doProcess(entities, context, true);
    }


}
