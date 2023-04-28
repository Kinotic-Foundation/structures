package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.ObjectProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.C3Type;
import org.kinotic.continuum.idl.api.ObjectC3Type;
import org.kinotic.continuum.idl.converter.C3ConversionContext;
import org.kinotic.continuum.idl.converter.Cacheable;
import org.kinotic.continuum.idl.converter.SpecificC3TypeConverter;

import java.util.Map;
import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 4/27/23.
 */
public class ObjectC3TypeToEsObjectProperty implements SpecificC3TypeConverter<Property>, Cacheable {

    private static final Set<Class<? extends C3Type>> supports = Set.of(ObjectC3Type.class);

    @Override
    public Property convert(C3Type c3Type, C3ConversionContext<Property> conversionContext) {
        if(c3Type instanceof ObjectC3Type){
            ObjectC3Type objectC3Type = (ObjectC3Type) c3Type;
            ObjectProperty.Builder builder = new ObjectProperty.Builder();

            for(Map.Entry<String, C3Type> entry : objectC3Type.getProperties().entrySet()){

                checkFieldNameFormat(entry.getKey());

                builder.properties(entry.getKey(), conversionContext.convert(entry.getValue()));
            }

            return builder.build()._toProperty();
        }else{
            throw new IllegalStateException("Unexpected C3Type: "+c3Type.getClass().getName()+" expected: "+ObjectC3Type.class.getName());
        }
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }

    static void checkFieldNameFormat(String fieldName){
        if(fieldName.contains("-")
                || fieldName.contains("+")
                || fieldName.contains(".")
                || fieldName.contains("..")
                || fieldName.contains("\\")
                || fieldName.contains("/")
                || fieldName.contains("*")
                || fieldName.contains("?")
                || fieldName.contains("\"")
                || fieldName.contains("<")
                || fieldName.contains(">")
                || fieldName.contains("|")
                || fieldName.contains(" ")
                || fieldName.contains(",")
                || fieldName.contains("#")
                || fieldName.contains(":")
                || fieldName.contains(";")
                || fieldName.getBytes().length > 255){
            throw new IllegalArgumentException("Field Name is not in correct format, \ncannot contain - + . .. \\ / * ? \" < > | , # : ; \ncannot contain a space or be longer than 255 bytes");
        }
    }
}
