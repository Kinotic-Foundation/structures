package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.*;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.C3TypeConverter;
import org.kinotic.continuum.idl.api.converter.Cacheable;
import org.kinotic.continuum.idl.api.schema.*;
import org.kinotic.structures.api.decorators.*;
import org.kinotic.structures.internal.utils.StructuresUtil;

/**
 * Converts a {@link ObjectC3Type} to a {@link Property}
 * Created by Navíd Mitchell 🤪 on 4/27/23.
 */
public class ObjectC3TypeToElastic implements C3TypeConverter<Property, ObjectC3Type, ElasticConversionState>, Cacheable {

    @Override
    public Property convert(ObjectC3Type objectC3Type,
                            C3ConversionContext<Property, ElasticConversionState> conversionContext) {
        ObjectProperty.Builder builder = new ObjectProperty.Builder()
                                                           .dynamic(DynamicMapping.False);

        ElasticConversionState state = conversionContext.state();

        // Add multi tenancy support if needed
        if(conversionContext.state().fieldDepth() == 0){
            EntityDecorator decorator = objectC3Type.findDecorator(EntityDecorator.class);
            if(decorator != null){

                state.setMultiTenancyType(decorator.getMultiTenancyType());

                if(decorator.getMultiTenancyType() == MultiTenancyType.SHARED) {
                    builder.properties(state.getStructuresProperties()
                                            .getTenantIdFieldName(),
                                       KeywordProperty.of(f -> f)._toProperty());
                }
            }else{
                throw new IllegalArgumentException("Structure Entity Definition must have an EntityDecorator");
            }
        }

        // now convert the rest of the properties
        for(PropertyDefinition property : objectC3Type.getProperties()){

            String fieldName = property.getName();
            C3Type type = property.getType();
            StructuresUtil.fieldNameValidation(fieldName);

            // This will also store decorators encountered
            state.beginProcessingField(property);

            // We have to apply nested decorators here as well due to the same problem mentioned above on line 35
            if(property.hasDecorators()){
                if(property.containsDecorator(NestedDecorator.class)) {

                    if (type instanceof ArrayC3Type) {
                        ArrayC3Type arrayC3Type = (ArrayC3Type) type;
                        if (!(arrayC3Type.getContains() instanceof ObjectC3Type)) {
                            throw new IllegalArgumentException("Nested decorator can only be applied to Arrays of Objects");
                        }
                    } else if (!(type instanceof ObjectC3Type || type instanceof UnionC3Type)) {
                        throw new IllegalArgumentException(
                                "Nested decorator can only be applied to Objects, Arrays of Objects, or Unions");
                    }

                    builder.properties(fieldName,
                                       NestedProperty.of(nb -> nb.properties(conversionContext.convert(type)
                                                                                              .object()
                                                                                              .properties()))
                                                     ._toProperty());

                } else if (property.containsDecorator(TextDecorator.class)) {

                    if(!(type instanceof StringC3Type)){
                        throw new IllegalArgumentException("Text decorator can only be applied to String fields");
                    }

                    builder.properties(fieldName, TextProperty.of(f -> f)._toProperty());

                } else if (property.containsDecorator(FlattenedDecorator.class)){

                    if(!(type instanceof ObjectC3Type || type instanceof UnionC3Type)){
                        throw new IllegalArgumentException("Flattened decorator can only be applied to Objects or Unions");
                    }

                    FlattenedDecorator decorator = property.findDecorator(FlattenedDecorator.class);
                    builder.properties(fieldName, FlattenedProperty.of(f -> f.depthLimit(decorator.getDepthLimit())
                                                                             .index(decorator.isIndex()))
                                                                   ._toProperty());
                }
            }else{

                builder.properties(fieldName, conversionContext.convert(type));

            }

            state.endProcessingField();
        }

        return builder.build()._toProperty();
    }

    @Override
    public boolean supports(C3Type c3Type) {
        return c3Type instanceof ObjectC3Type;
    }

}
