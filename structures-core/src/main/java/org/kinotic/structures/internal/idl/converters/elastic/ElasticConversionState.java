package org.kinotic.structures.internal.idl.converters.elastic;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.idl.decorators.EntityDecorator;
import org.kinotic.structures.internal.idl.converters.common.BaseConversionState;
import org.kinotic.structures.internal.idl.converters.common.DecoratedProperty;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 5/9/23.
 */
@Getter
@Setter
@Accessors(chain = true)
public class ElasticConversionState extends BaseConversionState {

    private final List<DecoratedProperty> decoratedProperties = new LinkedList<>();

    /**
     * The {@link EntityDecorator} that was found while converting the structure
     */
    private EntityDecorator entityDecorator;

    private String idFieldName;

    private String versionFieldName;

    private String tenantIdFieldName;


    public ElasticConversionState(StructuresProperties structuresProperties) {
        super(structuresProperties);
    }


}
