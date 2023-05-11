package org.kinotic.structures.internal.api.services;

import org.kinotic.continuum.idl.api.schema.ObjectC3Type;

/**
 * Created by Navíd Mitchell 🤪 on 5/11/23.
 */
public interface C3ToEsConversionService {

    EsConversionResult convert(ObjectC3Type objectC3Type);

}
