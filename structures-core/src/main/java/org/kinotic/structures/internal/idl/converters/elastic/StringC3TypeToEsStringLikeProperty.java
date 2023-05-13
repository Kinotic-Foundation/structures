package org.kinotic.structures.internal.idl.converters.elastic;

import co.elastic.clients.elasticsearch._types.mapping.KeywordProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.StringC3Type;
import org.kinotic.continuum.idl.api.converter.C3ConversionContext;
import org.kinotic.continuum.idl.api.converter.SpecificC3TypeConverter;

import java.util.Set;

/**
 * Creates an ES property that is either a Text or Keyword type, depending on the decorators on the {@lnik C3Type}
 * Created by Navíd Mitchell 🤪 on 4/28/23.
 */
public class StringC3TypeToEsStringLikeProperty implements SpecificC3TypeConverter<Property, StringC3Type, ElasticConversionState>{

    private static final Set<Class<? extends C3Type>> supports = Set.of(StringC3Type.class);

    @Override
    public Property convert(StringC3Type c3Type, C3ConversionContext<Property, ElasticConversionState> conversionContext) {
        // FIXME: look at decorators to determine if this should be a keyword or text
        return KeywordProperty.of(f -> f)._toProperty();
    }

    @Override
    public Set<Class<? extends C3Type>> supports() {
        return supports;
    }
}
