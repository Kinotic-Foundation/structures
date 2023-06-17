package org.kinotic.structures.api.decorators.runtime.crud;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.decorators.runtime.C3DecoratorInstance;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.Structure;

/**
 * Created by Navíd Mitchell 🤪on 6/12/23.
 */
public interface FindAllPreProcessor <D extends C3Decorator> extends C3DecoratorInstance<D> {

    void beforeFindAll(Structure structure,
                       String jsonPath,
                       D decorator,
                       Query.Builder queryBuilder,
                       EntityContext context);

}
