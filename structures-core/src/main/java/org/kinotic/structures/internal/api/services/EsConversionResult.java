package org.kinotic.structures.internal.api.services;

import co.elastic.clients.elasticsearch._types.mapping.ObjectProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.internal.idl.converters.elastic.DecoratedProperty;

import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 5/11/23.
 */
@Getter
@RequiredArgsConstructor
public class EsConversionResult {

    private final ObjectProperty objectProperty;

    private final List<DecoratedProperty> decoratedProperties;

}
