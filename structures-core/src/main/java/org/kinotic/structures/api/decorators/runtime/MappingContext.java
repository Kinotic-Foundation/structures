package org.kinotic.structures.api.decorators.runtime;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.schema.C3Type;

/**
 * The {@link MappingContext} is provided to the {@link MappingPreProcessor} to allow it to have access to the
 * {@link C3Type} that is currently being mapped and to allow it to convert a {@link C3Type} that does not need to convert
 * Created by Navíd Mitchell 🤪 on 5/12/23.
 */
public interface MappingContext {

    /**
     * This is provided to the {@link MappingPreProcessor} to allows it to have the C3Type that needs to be mapped.
     * @return the {@link C3Type} currently being mapped
     */
    C3Type value();

    /**
     * This allows the {@link MappingPreProcessor} to convert a type that it cannot convert by itself.
     * @param c3Type to use the internal logic to convert
     * @return the converted {@link Property}
     */
    Property convertInternal(C3Type c3Type);

}
