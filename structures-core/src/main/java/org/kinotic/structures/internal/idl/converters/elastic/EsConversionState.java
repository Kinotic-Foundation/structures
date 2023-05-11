package org.kinotic.structures.internal.idl.converters.elastic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 5/9/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class EsConversionState {

    List<DecoratedProperty> decoratedProperties = new LinkedList<>();

    public EsConversionState addDecoratedProperty(DecoratedProperty decoratedProperty){
        decoratedProperties.add(decoratedProperty);
        return this;
    }

}
