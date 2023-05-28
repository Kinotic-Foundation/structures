package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.UnionC3Type;

import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 5/26/23.
 */
public class UnionC3TypeToElastic implements SpecificC3TypeConverter<Property, UnionC3Type, ElasticConversionState> {

    private static final Set<Class<? extends C3Type>> supports = Set.of(UnionC3Type.class);

    @Override
    public Property convert(UnionC3Type c3Type,
                            C3ConversionContext<Property, ElasticConversionState> conversionContext) {
        return null;
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }
}
