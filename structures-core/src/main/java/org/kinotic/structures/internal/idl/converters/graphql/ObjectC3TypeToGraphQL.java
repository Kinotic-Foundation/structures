package org.kinotic.structures.internal.idl.converters.graphql;

import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLObjectType;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.Cacheable;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.structures.api.decorators.ReadOnlyDecorator;
import org.kinotic.structures.api.decorators.runtime.GraphQLTypeHolder;

import java.util.Map;
import java.util.Set;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLInputObjectField.newInputObjectField;
import static graphql.schema.GraphQLInputObjectType.newInputObject;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * Created by Navíd Mitchell 🤪 on 5/2/23.
 */
public class ObjectC3TypeToGraphQL implements SpecificC3TypeConverter<GraphQLTypeHolder, ObjectC3Type, GraphQLConversionState>, Cacheable {

    private static final Set<Class<? extends C3Type>> supports = Set.of(ObjectC3Type.class);

    @Override
    public GraphQLTypeHolder convert(ObjectC3Type objectC3Type,
                                     C3ConversionContext<GraphQLTypeHolder, GraphQLConversionState> conversionContext) {

        GraphQLObjectType.Builder outputBuilder = newObject().name(objectC3Type.getName());
        GraphQLInputObjectType.Builder inputBuilder = newInputObject().name(objectC3Type.getName()+"Input");

        for(Map.Entry<String, C3Type> entry : objectC3Type.getProperties().entrySet()){

            String fieldName = entry.getKey();

            conversionContext.state().beginProcessingField(fieldName, entry.getValue());

            GraphQLTypeHolder fieldValue = conversionContext.convert(entry.getValue());

            conversionContext.state().endProcessingField();

            outputBuilder.field(newFieldDefinition()
                                        .name(fieldName)
                                        .type(fieldValue.getOutputType()));

            if(isTypeRequiredForInput(entry.getValue())){
                inputBuilder.field(newInputObjectField()
                                           .name(fieldName)
                                           .type(fieldValue.getInputType()));
            }
        }
        return new GraphQLTypeHolder(inputBuilder.build(), outputBuilder.build());
    }

    private boolean isTypeRequiredForInput(C3Type c3Type){
        return !c3Type.containsDecorator(ReadOnlyDecorator.class);
    }


    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }
}
