package org.kinotic.structures.internal.api.decorators.instances;

import co.elastic.clients.elasticsearch._types.mapping.NestedProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.schema.ArrayC3Type;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.continuum.idl.api.schema.UnionC3Type;
import org.kinotic.structures.api.decorators.NestedDecorator;
import org.kinotic.structures.api.decorators.runtime.mapping.ElasticMappingPreProcessor;
import org.kinotic.structures.api.decorators.runtime.mapping.MappingContext;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.idl.converters.elastic.ElasticConversionState;
import org.springframework.stereotype.Component;

/**
 * Created by Navíd Mitchell 🤪on 6/8/23.
 */
@Component
public class NestedDecoratorInstance implements ElasticMappingPreProcessor<NestedDecorator> {

    @Override
    public Class<NestedDecorator> implementsDecorator() {
        return NestedDecorator.class;
    }

    @Override
    public boolean supportC3Type(C3Type c3Type) {
        boolean ret = false;
        if(c3Type instanceof ArrayC3Type){
            ArrayC3Type arrayC3Type = (ArrayC3Type) c3Type;
            if(arrayC3Type.getContains() instanceof ObjectC3Type){
                ret = true;
            }
        }else if(c3Type instanceof ObjectC3Type
                 || c3Type instanceof UnionC3Type) {
            ret = true;
        }
        return ret;
    }

    @Override
    public Property process(Structure structure,
                            String fieldName,
                            NestedDecorator decorator,
                            C3Type type,
                            MappingContext<Property, ElasticConversionState> context) {
        return NestedProperty.of(builder -> {

            if(type instanceof ArrayC3Type){
                ArrayC3Type arrayC3Type = (ArrayC3Type) type;

                Property property = context.convertInternal(arrayC3Type.getContains());
                // sanity check
                if(!property.isObject()){
                    throw new IllegalArgumentException("Nested decorator can only be applied to Arrays of ObjectC3Types");
                }

                builder.properties(property.object().properties());
            }else if(type instanceof ObjectC3Type){
                ObjectC3Type objectC3Type = (ObjectC3Type) type;
                Property property = context.convertInternal(objectC3Type);
                builder.properties(property.object().properties());
            }else if(type instanceof UnionC3Type){
                UnionC3Type unionC3Type = (UnionC3Type) type;
                Property property = context.convertInternal(unionC3Type);
                builder.properties(property.object().properties());
            }

            return builder;
        })._toProperty();
    }
}
