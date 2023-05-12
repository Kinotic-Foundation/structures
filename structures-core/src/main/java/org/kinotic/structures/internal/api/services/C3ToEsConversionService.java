package org.kinotic.structures.internal.api.services;

import org.kinotic.continuum.idl.api.schema.ObjectC3Type;

/**
 * Handles converting a C3 ObjectC3Type to an ElasticSearch ObjectProperty
 * Created by Navíd Mitchell 🤪 on 5/11/23.
 */
public interface C3ToEsConversionService {

    /**
     * Converts the given ObjectC3Type to an ElasticSearch ObjectProperty
     * @param objectC3Type to convert
     * @return the converted ObjectProperty
     */
    EsConversionResult convert(ObjectC3Type objectC3Type);

}
