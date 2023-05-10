package org.kinotic.structures.internal.idl.converters.graphql;

import org.kinotic.continuum.idl.api.converter.GenericC3TypeConverter;
import org.kinotic.continuum.idl.api.converter.IdlConverterStrategy;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;

import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 5/2/23.
 */
public class C3ToGraphConverterStrategy implements IdlConverterStrategy<C3TypeToGraphTypeHolder> {

    @Override
    public List<SpecificC3TypeConverter<C3TypeToGraphTypeHolder, ?>> specificTypeConverters() {
        return null;
    }

    @Override
    public List<GenericC3TypeConverter<C3TypeToGraphTypeHolder, ?>> genericTypeConverters() {
        return null;
    }

    @Override
    public boolean shouldCache() {
        return true;
    }
}
