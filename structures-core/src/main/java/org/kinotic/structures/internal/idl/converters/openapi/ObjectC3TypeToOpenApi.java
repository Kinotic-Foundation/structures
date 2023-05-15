package org.kinotic.structures.internal.idl.converters.openapi;

import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.Cacheable;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.continuum.idl.api.schema.decorators.NotNullC3Decorator;
import org.kinotic.structures.api.decorators.ReadOnlyDecorator;

import java.util.Map;
import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 5/15/23.
 */
public class ObjectC3TypeToOpenApi implements SpecificC3TypeConverter<Schema<?>, ObjectC3Type, OpenApiConversionState>, Cacheable {

    private static final Set<Class<? extends C3Type>> supports = Set.of(ObjectC3Type.class);

    @Override
    public Schema<?> convert(ObjectC3Type objectC3Type,
                             C3ConversionContext<Schema<?>, OpenApiConversionState> conversionContext) {

        ObjectSchema objectSchema = new ObjectSchema();

        for(Map.Entry<String, C3Type> entry : objectC3Type.getProperties().entrySet()){

            String fieldName = entry.getKey();

            conversionContext.state().beginProcessingField(fieldName, entry.getValue());

            Schema<?> fieldValue = conversionContext.convert(entry.getValue());

            conversionContext.state().endProcessingField();

            if(isReadOnly(entry.getValue())){
                fieldValue.setReadOnly(true);
            }

            // if this is an object we create a reference schema
            if(entry.getValue() instanceof ObjectC3Type){
                ObjectC3Type objectField = (ObjectC3Type) entry.getValue();
                conversionContext.state().addReferenceSchema(objectField.getName(), fieldValue);
                fieldValue = new Schema<>().$ref("#/components/schemas/"+objectField.getName());
            }

            objectSchema.addProperty(fieldName, fieldValue);

            if(isRequired(entry.getValue())){
                objectSchema.addRequiredItem(fieldName);
            }
        }

        return objectSchema;
    }

    private boolean isReadOnly(C3Type c3Type){
        return c3Type.containsDecorator(ReadOnlyDecorator.class);
    }

    private boolean isRequired(C3Type c3Type){
        return c3Type.containsDecorator(NotNullC3Decorator.class);
    }


    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }
}
