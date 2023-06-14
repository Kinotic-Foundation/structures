package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.DynamicMapping;
import co.elastic.clients.elasticsearch._types.mapping.ObjectProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.Cacheable;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
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

        for(Map.Entry<String, C3Type> entry : objectC3Type.getProperties().entrySet()){

            String fieldName = entry.getKey();
            StructuresUtil.fieldNameValidation(fieldName);

            // This will also store decorators encountered
            conversionContext.state().beginProcessingField(fieldName, entry.getValue());

            builder.properties(entry.getKey(), conversionContext.convert(entry.getValue()));

            conversionContext.state().endProcessingField();
        }
        return builder.build()._toProperty();
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }

}
