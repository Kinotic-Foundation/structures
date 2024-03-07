package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.Cacheable;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.continuum.idl.api.schema.UnionC3Type;

import java.util.Map;
import java.util.Set;

/**
 * TODO: should root / entity objects be allowed to be a union type.
 * Created by Navíd Mitchell 🤪 on 5/26/23.
 */
public class UnionC3TypeToElastic implements SpecificC3TypeConverter<Property, UnionC3Type, ElasticConversionState>, Cacheable {

    private static final Set<Class<? extends C3Type>> supports = Set.of(UnionC3Type.class);

    @Override
    public Property convert(UnionC3Type unionType,
                            C3ConversionContext<Property, ElasticConversionState> conversionContext) {

        // For elastic, we just merge all the fields into one object and map that
        ObjectC3Type merged = new ObjectC3Type();
        merged.setName(unionType.getName());
        merged.setNamespace(unionType.getNamespace());

        for (ObjectC3Type c3Type : unionType.getTypes()) {
            for(Map.Entry<String, C3Type> field : c3Type.getProperties().entrySet()) {

                C3Type prop = merged.getProperties().get(field.getKey());
                if(prop != null){
                    if(!prop.equals(field.getValue())){
                        throw new IllegalArgumentException("Field '" + field.getKey() + "' is defined in multiple types in the union '" + unionType.getName() + "' with different types");
                    }
                }else {
                    merged.addProperty(field.getKey(), field.getValue());
                }
            }
        }

        return conversionContext.convert(merged);
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }
}
