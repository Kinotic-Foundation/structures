package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.ArrayC3Type;
import org.kinotic.continuum.idl.api.C3Type;
import org.kinotic.continuum.idl.converter.C3ConversionContext;
import org.kinotic.continuum.idl.converter.SpecificC3TypeConverter;

import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 4/28/23.
 */
public class ArrayC3TypeToEsProperty implements SpecificC3TypeConverter<Property> {

    private static final Set<Class<? extends C3Type>> supports = Set.of(ArrayC3Type.class);

    @Override
    public Property convert(C3Type c3Type, C3ConversionContext<Property> conversionContext) {
        if(c3Type instanceof ArrayC3Type){
            ArrayC3Type arrayC3Type = (ArrayC3Type) c3Type;
            // There is no specific type for arrays in elastic search, so we just convert the inner type
            return conversionContext.convert(arrayC3Type.getContains());
        }else{
            throw new IllegalStateException("Unexpected C3Type: "+c3Type.getClass().getName()+" expected: "+ArrayC3Type.class.getName());
        }
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }
}
