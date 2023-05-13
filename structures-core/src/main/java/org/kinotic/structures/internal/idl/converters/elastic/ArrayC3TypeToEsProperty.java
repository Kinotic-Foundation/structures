package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.schema.ArrayC3Type;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;

import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 4/28/23.
 */
public class ArrayC3TypeToEsProperty implements SpecificC3TypeConverter<Property, ArrayC3Type, ElasticConversionState> {

    private static final Set<Class<? extends C3Type>> supports = Set.of(ArrayC3Type.class);

    @Override
    public Property convert(ArrayC3Type arrayC3Type, C3ConversionContext<Property, ElasticConversionState> conversionContext) {
        // There is no specific type for arrays in elastic search, so we just convert the inner type
        return conversionContext.convert(arrayC3Type.getContains());
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }
}
