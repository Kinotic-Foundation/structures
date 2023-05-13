package org.kinotic.structures.internal.idl.converters.elastic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.services.DecoratedProperty;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 5/9/23.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class ElasticConversionState {

    private List<DecoratedProperty> decoratedProperties = new LinkedList<>();

    private Structure structureBeingConverted;

    private String currentFieldName;

    public ElasticConversionState addDecoratedProperty(DecoratedProperty decoratedProperty){
        decoratedProperties.add(decoratedProperty);
        return this;
    }

}
