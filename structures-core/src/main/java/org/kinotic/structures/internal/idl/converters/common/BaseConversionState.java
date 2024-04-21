package org.kinotic.structures.internal.idl.converters.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.continuum.idl.api.schema.C3Type;
import org.kinotic.continuum.idl.api.schema.PropertyDefinition;
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
     * @param propertyDefinition the property definition to begin processing
     */
    public void beginProcessingField(PropertyDefinition propertyDefinition){
        currentFieldName = propertyDefinition.getName();
        // Store decorators for use later with their corresponding json path and type
        currentJsonPath = !propertyStack.isEmpty()
                ? propertyStack.peekFirst() + "." + propertyDefinition.getName() : propertyDefinition.getName();
        propertyStack.addFirst(currentJsonPath);

        C3Type value = propertyDefinition.getType();
        if(propertyDefinition.hasDecorators()){
            decoratedProperties.add(new DecoratedProperty(currentJsonPath,
                                                          value.getClass(),
                                                          propertyDefinition.getDecorators()));
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
