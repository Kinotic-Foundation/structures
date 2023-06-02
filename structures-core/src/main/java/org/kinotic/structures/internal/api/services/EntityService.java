package org.kinotic.structures.internal.api.services;

import org.kinotic.continuum.core.api.crud.CrudService;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.domain.Structure;

/**
 * Provides functionality to query data for a single "Entity" for a given {@link Structure}.
 * Created by Navíd Mitchell 🤪 on 5/2/23.
 */
public interface EntityService extends CrudService<RawJson, String> {

}
