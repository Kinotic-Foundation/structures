package org.kinotic.structures.api.decorators.runtime;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.schema.C3Type;

/**
 * The {@link MappingContext} is provided to the {@link ElasticMappingPreProcessor} to provide the {@link C3Type} that is currently being converted.
 * Additionally, it provides a method to convert a {@link C3Type} that the {@link ElasticMappingPreProcessor} cannot convert by itself.
 * @param <R> the type of value that this {@link MappingContext} can convert
 * Created by Navíd Mitchell 🤪 on 5/12/23.
 */
public interface MappingContext<R> {

    /**
     * This is provided to the {@link ElasticMappingPreProcessor} to allows it to have the C3Type that needs to be mapped.
     * @return the {@link C3Type} currently being mapped
     */
    C3Type value();

    /**
     * This allows the {@link ElasticMappingPreProcessor} to convert a type that it cannot convert by itself.
     * @param c3Type to use the internal logic to convert
     * @return the converted {@link Property}
     */
    R convertInternal(C3Type c3Type);

}
