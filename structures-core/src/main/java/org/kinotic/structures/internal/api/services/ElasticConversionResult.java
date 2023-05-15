package org.kinotic.structures.internal.api.services;

import co.elastic.clients.elasticsearch._types.mapping.ObjectProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.internal.idl.converters.common.DecoratedProperty;

import java.util.List;

/**
 * This is the result produced by the {@link StructureConversionService}
 * Created by Navíd Mitchell 🤪 on 5/11/23.
 */
@Getter
@RequiredArgsConstructor
public class ElasticConversionResult {

    /**
     * The root object property that represents the converted C3Type
     */
    private final ObjectProperty objectProperty;

    /**
     * A list of all {@link DecoratedProperty} that were found during the conversion process
     */
    private final List<DecoratedProperty> decoratedProperties;

}
