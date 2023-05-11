package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.ObjectProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.Cacheable;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.ObjectC3Type;
import org.kinotic.structures.internal.api.services.impl.StructuresHelper;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Set;

/**
 * Converts a {@link ObjectC3Type} to a {@link Property}
 * Created by Navíd Mitchell 🤪 on 4/27/23.
 */
public class ObjectC3TypeToEsObjectProperty implements SpecificC3TypeConverter<Property, ObjectC3Type, EsConversionState>, Cacheable {

    private static final Set<Class<? extends C3Type>> supports = Set.of(ObjectC3Type.class);

    private final Deque<String> propertyStack = new ArrayDeque<>();

    @Override
    public Property convert(ObjectC3Type objectC3Type, C3ConversionContext<Property, EsConversionState> conversionContext) {
        ObjectProperty.Builder builder = new ObjectProperty.Builder();

        for(Map.Entry<String, C3Type> entry : objectC3Type.getProperties().entrySet()){

            StructuresHelper.fieldNameValidation(entry.getKey());

            // Store decorators for use later with their corresponding json path and type
            String currentJsonPath = !propertyStack.isEmpty() ? propertyStack.peekFirst() + "." +entry.getKey() : entry.getKey();
            propertyStack.addFirst(currentJsonPath);

            if(entry.getValue().hasDecorators()){

                C3Type decoratedType = entry.getValue();

                conversionContext.state()
                                 .addDecoratedProperty(new DecoratedProperty(currentJsonPath,
                                                                             decoratedType.getClass(),
                                                                             decoratedType.getDecorators()));

            }

            builder.properties(entry.getKey(), conversionContext.convert(entry.getValue()));

            propertyStack.removeFirst();
        }

        return builder.build()._toProperty();
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }

}
