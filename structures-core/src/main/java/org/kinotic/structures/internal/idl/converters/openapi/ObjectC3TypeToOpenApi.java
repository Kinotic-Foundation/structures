package org.kinotic.structures.internal.idl.converters.openapi;

import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.Cacheable;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.continuum.idl.api.schema.PropertyDefinition;
import org.kinotic.continuum.idl.api.schema.decorators.NotNullC3Decorator;
import org.kinotic.structures.api.decorators.ReadOnlyDecorator;

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
        objectSchema.setName(objectC3Type.getName());

        for(PropertyDefinition property : objectC3Type.getProperties()){

            String fieldName = property.getName();
            C3Type type = property.getType();

            conversionContext.state().beginProcessingField(fieldName, type);

            Schema<?> fieldValue = conversionContext.convert(type);

            conversionContext.state().endProcessingField();

            if(isReadOnly(type)){
                fieldValue.setReadOnly(true);
            }

            // if this is an object we create a reference schema
            // TODO: handle cases where the same object name is used across multiple different types in the same namespace
            //       To handle this we will need to keep track of all "Models" per namespace and check for conflicts
            //       Or this could be done by keeping the Conversion State around and converting all Structures for a namespace at once
            if(type instanceof ObjectC3Type){
                ObjectC3Type objectField = (ObjectC3Type) type;
                conversionContext.state().addReferencedSchema(objectField.getName(), fieldValue);
                fieldValue = new Schema<>().$ref("#/components/schemas/"+objectField.getName());
            }

            objectSchema.addProperty(fieldName, fieldValue);

            if(isRequired(type)){
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
