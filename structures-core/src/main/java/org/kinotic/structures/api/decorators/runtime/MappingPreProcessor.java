package org.kinotic.structures.api.decorators.runtime;

import co.elastic.clients.elasticsearch._types.mapping.Property;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;

/**
 * {@link MappingPreProcessor} is used to create or modify an ElasticSearch mapping based on data available in the {@link C3Decorator}
 * Created by Navíd Mitchell 🤪 on 5/12/23.
 */
public interface MappingPreProcessor<D extends C3Decorator> extends DecoratorProcessor<D, Property, MappingContext> {

}
