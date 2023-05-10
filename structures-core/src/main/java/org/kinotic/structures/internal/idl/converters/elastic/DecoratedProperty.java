package org.kinotic.structures.internal.idl.converters.elastic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.kinotic.continuum.idl.api.schema.C3Type;

/**
 * Created by Navíd Mitchell 🤪 on 5/3/23.
 */
@Getter
@RequiredArgsConstructor
public class DecoratedProperty {

    private final String jsonPath;

    private final C3Type decoratedType;

}
