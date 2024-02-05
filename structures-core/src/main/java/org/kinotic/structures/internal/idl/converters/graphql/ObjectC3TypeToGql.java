package org.kinotic.structures.internal.idl.converters.graphql;

import graphql.schema.*;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.Cacheable;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.continuum.idl.api.schema.decorators.NotNullC3Decorator;
import org.kinotic.structures.api.decorators.ReadOnlyDecorator;

import java.util.Map;
import java.util.Set;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLInputObjectField.newInputObjectField;
import static graphql.schema.GraphQLInputObjectType.newInputObject;
import static graphql.schema.GraphQLObjectType.newObject;
import static graphql.schema.GraphQLTypeReference.typeRef;
import static graphql.schema.GraphQLNonNull.nonNull;
/**
 * Created by Navíd Mitchell 🤪 on 5/2/23.
 */
public class ObjectC3TypeToGql implements SpecificC3TypeConverter<GqlTypeHolder, ObjectC3Type, GqlConversionState>, Cacheable {

    private static final Set<Class<? extends C3Type>> supports = Set.of(ObjectC3Type.class);

    @Override
    public GqlTypeHolder convert(ObjectC3Type objectC3Type,
                                 C3ConversionContext<GqlTypeHolder, GqlConversionState> conversionContext) {

        GraphQLObjectType.Builder outputBuilder = newObject().name(objectC3Type.getName());
        GraphQLInputObjectType.Builder inputBuilder = newInputObject().name(objectC3Type.getName() + "Input");
        boolean nullInputTypeFound = false;

        for (Map.Entry<String, C3Type> entry : objectC3Type.getProperties().entrySet()) {

            String fieldName = entry.getKey();

            conversionContext.state().beginProcessingField(fieldName, entry.getValue());

            GqlTypeHolder fieldValue = conversionContext.convert(entry.getValue());

            // If the field is an object type, we need to replace it with a reference to the object type
            // TODO: handle cases where the same object name is used across multiple different types in the same namespace
            //       To handle this we will need to keep track of all "Models" per namespace and check for conflicts
            //       Or this could be done by keeping the Conversion State around and converting all Structures for a namespace at once
            if(fieldValue.getOutputType() instanceof GraphQLObjectType){
                GraphQLObjectType objectType = (GraphQLObjectType) fieldValue.getOutputType();
                String objectTypeName = objectType.getName();

                conversionContext.state().getReferencedTypes().put(objectTypeName, objectType);
                fieldValue.setOutputType(typeRef(objectTypeName));
            }

            if(fieldValue.getInputType() instanceof GraphQLInputObjectType){
                GraphQLInputObjectType inputObjectType = (GraphQLInputObjectType) fieldValue.getInputType();
                String inputTypeName = inputObjectType.getName();

                conversionContext.state().getReferencedTypes().put(inputTypeName, inputObjectType);
                fieldValue.setInputType(typeRef(inputTypeName));
            }

            if (isNotNull(entry.getValue())) {
                fieldValue.setOutputType(nonNull(fieldValue.getOutputType()));
                fieldValue.setInputType(nonNull(fieldValue.getInputType()));
            }

            conversionContext.state().endProcessingField();

            outputBuilder.field(newFieldDefinition()
                                        .name(fieldName)
                                        .type(fieldValue.getOutputType()));

            // input type can be null in some cases such as a Union type.
            // This can create a paradigm mismatch between OpenApi and GraphQL, but we cannot do anything about it.
            // For now, we will not create an input type for these cases.
            if (fieldValue.getInputType() != null) {
                if (isTypeRequiredForInput(entry.getValue())) {
                    inputBuilder.field(newInputObjectField()
                                               .name(fieldName)
                                               .type(fieldValue.getInputType()));
                }
            } else {
                nullInputTypeFound = true;
            }
        }

        // if this is the top level object, add any directives
        if(conversionContext.state().fieldDepth() == 0){
            for(GraphQLAppliedDirective directive : conversionContext.state().getOutputTypeDirectives()){
                outputBuilder.withAppliedDirective(directive);
            }
        }

        return new GqlTypeHolder(!nullInputTypeFound ? inputBuilder.build() : null, outputBuilder.build());
    }

    private boolean isTypeRequiredForInput(C3Type c3Type) {
        return !c3Type.containsDecorator(ReadOnlyDecorator.class);
    }

    private boolean isNotNull(C3Type c3Type) {
        return c3Type.containsDecorator(NotNullC3Decorator.class);
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }
}
