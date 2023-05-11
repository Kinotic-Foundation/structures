package org.kinotic.structures.internal.idl.converters.elastic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;

import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 5/3/23.
 */
@Getter
@RequiredArgsConstructor
public class DecoratedProperty {

    private final String jsonPath;

    private final Class<? extends C3Type> decoratedTypeClass;

    private final List<C3Decorator> decorators;

}
