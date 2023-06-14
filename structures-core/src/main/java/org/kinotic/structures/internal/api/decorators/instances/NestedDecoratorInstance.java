package org.kinotic.structures.internal.api.decorators.instances;

import co.elastic.clients.elasticsearch._types.mapping.NestedProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.schema.ArrayC3Type;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
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

            ArrayC3Type arrayC3Type = (ArrayC3Type) type;

            Property property = context.convertInternal(arrayC3Type.getContains());
            // sanity check
            if(!property.isObject()){
                throw new IllegalArgumentException("Nested decorator can only be applied to Arrays of ObjectC3Type");
            }

            builder.properties(property.object().properties());

            return builder;
        })._toProperty();
    }
}
