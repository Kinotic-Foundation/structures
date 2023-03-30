package org.kinotic.structures.internal.api.services;

import org.kinotic.structures.api.domain.AlreadyExistsException;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.StructureService;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by Navíd Mitchell 🤪 on 3/30/23.
 */
public interface StructureServiceInternal extends StructureService {

    Structure save(Structure structure) throws AlreadyExistsException;

    Optional<Structure> getById(String id) throws IOException;

}
