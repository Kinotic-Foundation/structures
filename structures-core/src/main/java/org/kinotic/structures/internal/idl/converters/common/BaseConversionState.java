package org.kinotic.structures.internal.idl.converters.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.Structure;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 5/14/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class BaseConversionState {

    private Structure structureBeingConverted;

    private StructuresProperties structuresProperties;

    private final List<DecoratedProperty> decoratedProperties = new LinkedList<>();

    @Getter(AccessLevel.NONE) private final Deque<String> propertyStack = new ArrayDeque<>();

    @Setter(AccessLevel.NONE) private String currentFieldName;

    @Setter(AccessLevel.NONE) private String currentJsonPath;

    /**
     * Must be called before processing a field.
     * This ensures the current field name and json path are set correctly
     * @param fieldName being processed
     * @param value being processed
     */
    public void beginProcessingField(String fieldName, C3Type value){
        currentFieldName = fieldName;
        // Store decorators for use later with their corresponding json path and type
        currentJsonPath = !propertyStack.isEmpty() ? propertyStack.peekFirst() + "." + fieldName : fieldName;
        propertyStack.addFirst(currentJsonPath);

        if(value.hasDecorators()){
            decoratedProperties.add(new DecoratedProperty(currentJsonPath,
                                                          value.getClass(),
                                                          value.getDecorators()));
        }
    }

    /**
     * Must be called after processing a field.
     */
    public void endProcessingField(){
        propertyStack.removeFirst();
    }

    /**
     * @return the nesting depth of the current property being processed
     */
    public int fieldDepth(){
        return propertyStack.size();
    }

}
