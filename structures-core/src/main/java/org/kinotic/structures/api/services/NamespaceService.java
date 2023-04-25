package org.kinotic.structures.api.services;

import org.kinotic.continuum.api.annotations.Publish;
import org.kinotic.continuum.core.api.crud.CrudService;
import org.kinotic.structures.api.domain.Namespace;

@Publish
public interface NamespaceService extends CrudService<Namespace, String> {

}
