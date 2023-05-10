package org.kinotic.structures.internal.idl.converters.elastic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 5/5/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class DecoratorInfo {

    private boolean containsDecoratorForObject = false;

    List<DecoratedProperty> decoratedProperties = new LinkedList<>();

    public DecoratorInfo addDecoratedProperty(DecoratedProperty decoratedProperty){
        decoratedProperties.add(decoratedProperty);
        return this;
    }

}
