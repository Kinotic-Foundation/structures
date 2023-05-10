package org.kinotic.structures.api.decorators;

import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.continuum.idl.api.schema.decorators.DecoratorTarget;

import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 5/9/23.
 */
public abstract class ReadOnlyDecorator extends C3Decorator {

    public ReadOnlyDecorator(){
        this.targets = List.of(DecoratorTarget.FIELD);
    }

}
