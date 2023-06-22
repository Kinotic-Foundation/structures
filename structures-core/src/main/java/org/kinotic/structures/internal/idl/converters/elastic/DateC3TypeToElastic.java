package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.DateProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.DateC3Type;

import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 4/28/23.
 */
public class DateC3TypeToElastic implements SpecificC3TypeConverter<Property, DateC3Type, ElasticConversionState> {

    private static final Set<Class<? extends C3Type>> supports = Set.of(DateC3Type.class);

    @Override
    public Property convert(DateC3Type dateC3Type, C3ConversionContext<Property, ElasticConversionState> conversionContext) {
        DateProperty.Builder builder = new DateProperty.Builder();
        return builder.build()._toProperty();
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }
}
