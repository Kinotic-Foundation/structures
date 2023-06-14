package org.kinotic.structures.api.decorators.runtime.crud;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.decorators.runtime.C3DecoratorInstance;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.Structure;

/**
 * Created by Navíd Mitchell 🤪on 6/12/23.
 */
public interface SearchPreProcessor <D extends C3Decorator> extends C3DecoratorInstance<D> {

    void beforeSearch(Structure structure,
                      String jsonPath,
                      D decorator,
                      SearchRequest.Builder builder,
                      EntityContext context);
}
