package org.kinotic.structures.api.decorators;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.continuum.idl.api.schema.decorators.DecoratorTarget;

import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 5/26/23.
 */
@Getter
@Setter
@Accessors(chain = true)
public class FlattenedDecorator extends C3Decorator {

    @JsonIgnore
    public static final String type = "Flattened";

    /**
     * The maximum allowed depth of the flattened object field, in terms of nested inner objects.
     * If a flattened object field exceeds this limit, then an error will be thrown. Defaults to 20.
     */
    private int depthLimit = 20;

    /**
     * Determines if the field should be searchable. Accepts true (default) or false.
     */
    private boolean index = true;

    public FlattenedDecorator() {
        this.targets = List.of(DecoratorTarget.FIELD);
    }

}
