package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.*;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.Cacheable;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.ArrayC3Type;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.continuum.idl.api.schema.UnionC3Type;
import org.kinotic.structures.api.decorators.EntityDecorator;
import org.kinotic.structures.api.decorators.MultiTenancyType;
import org.kinotic.structures.api.decorators.NestedDecorator;
import org.kinotic.structures.internal.utils.StructuresUtil;

import java.util.Map;
import java.util.Set;

/**
 * Converts a {@link ObjectC3Type} to a {@link Property}
 * Created by Navíd Mitchell 🤪 on 4/27/23.
 */
public class ObjectC3TypeToElastic implements SpecificC3TypeConverter<Property, ObjectC3Type, ElasticConversionState>, Cacheable {

    private static final Set<Class<? extends C3Type>> supports = Set.of(ObjectC3Type.class);

    @Override
    public Property convert(ObjectC3Type objectC3Type, C3ConversionContext<Property, ElasticConversionState> conversionContext) {
        ObjectProperty.Builder builder = new ObjectProperty.Builder()
                                                           .dynamic(DynamicMapping.False);

        ElasticConversionState state = conversionContext.state();

        // Add multi tenancy support if needed
        // LOOK: if we should move this logic to a ElasticMappingPreProcessor.
        // The current barrier is that if this is done then when you try to use the convert internal you end up with a recursive loop.
        // Since the mapping preprocessor still needs to convert the object. But the way the strategy is defined you end up calling the mapping processor again.
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
        for(Map.Entry<String, C3Type> entry : objectC3Type.getProperties().entrySet()){

            String fieldName = entry.getKey();
            StructuresUtil.fieldNameValidation(fieldName);

            // This will also store decorators encountered
            state.beginProcessingField(fieldName, entry.getValue());

            // We have to apply nested decorators here as well due to the same problem mentioned above on line 35
            C3Type type = entry.getValue();
            if(type.containsDecorator(NestedDecorator.class)) {

                if(type instanceof ArrayC3Type){
                    ArrayC3Type arrayC3Type = (ArrayC3Type) type;
                    if(!(arrayC3Type.getContains() instanceof ObjectC3Type)){
                        throw new IllegalArgumentException("Nested decorator can only be applied to Arrays of Objects");
                    }
                }else if(!(type instanceof ObjectC3Type || type instanceof UnionC3Type)){
                    throw new IllegalArgumentException("Nested decorator can only be applied to Objects, Arrays of Objects, or Unions");
                }

                builder.properties(entry.getKey(),
                                   NestedProperty.of(nb -> nb.properties(conversionContext.convert(type)
                                                                                          .object()
                                                                                          .properties()))
                                                 ._toProperty());
            }else{
                builder.properties(entry.getKey(), conversionContext.convert(type));
            }

            state.endProcessingField();
        }

        return builder.build()._toProperty();
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }

}
