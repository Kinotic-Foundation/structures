package org.kinotic.structures.internal.idl.converters.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;

import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 5/3/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DecoratedProperty {

    private String jsonPath;

    private Class<? extends C3Type> decoratedTypeClass;

    private List<C3Decorator> decorators;

}
