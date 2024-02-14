package org.kinotic.structures.internal.idl.converters.graphql;

import graphql.scalars.ExtendedScalars;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.DateC3Type;

import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 5/15/23.
 */
public class DateC3TypeToGql implements SpecificC3TypeConverter<GqlTypeHolder, DateC3Type, GqlConversionState> {

    private static final Set<Class<? extends C3Type>> supports = Set.of(DateC3Type.class);

    private static final GqlTypeHolder DATE = new GqlTypeHolder(ExtendedScalars.DateTime, ExtendedScalars.DateTime);

    @Override
    public GqlTypeHolder convert(DateC3Type dateC3Type, C3ConversionContext<GqlTypeHolder, GqlConversionState> conversionContext) {
        return DATE;
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }
}
