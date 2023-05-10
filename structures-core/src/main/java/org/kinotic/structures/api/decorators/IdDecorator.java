package org.kinotic.structures.api.decorators;

import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.continuum.idl.api.schema.decorators.DecoratorTarget;

import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 5/9/23.
 */
public final class IdDecorator extends C3Decorator {

    public IdDecorator(){
        this.targets = List.of(DecoratorTarget.FIELD);
    }
}
