package org.kinotic.structures.api.decorators;

import org.kinotic.continuum.idl.api.schema.StringC3Type;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.continuum.idl.api.schema.decorators.DecoratorTarget;

import java.util.List;

/**
 * {@link TextDecorator} defines a {@link StringC3Type} as a text field
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
public class TextDecorator extends C3Decorator {

    public TextDecorator() {
        this.targets = List.of(DecoratorTarget.FIELD);
    }
}
