package org.kinotic.structuresserver.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.Trait;

/**
 * Created by Navíd Mitchell 🤪 on 3/18/23.
 */
public interface OpenApiService {

    /**
     * Gets the OpenAPI spec for all the structures
     * TODO: eventually this will be namespaced
     * @return the OpenAPI spec
     */
    OpenAPI getOpenApiSpec();

}
