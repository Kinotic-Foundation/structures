package org.kinotic.structures.api.domain.idl.decorators;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.kinotic.structures.internal.api.services.EntityService;

import java.util.List;

/**
 * Configuration for the {@link EntityServiceDecoratorsDecorator}
 * This maps {@link EntityService} method names to decorators that should be applied to the method
 */
@Getter
@Setter
@Accessors(chain = true)
public class EntityServiceDecoratorsConfig{

    /**
     * The following map crud operations to decorators
     * These will be applied to the corresponding method type in the {@link EntityService}
     * i.e. create will be applied to the save method, read will be applied to count, findAll, findById, ect
     */
    private List<EntityServiceDecorator> allCreate = List.of();
    private List<EntityServiceDecorator> allRead = List.of();
    private List<EntityServiceDecorator> allUpdate = List.of();
    private List<EntityServiceDecorator> allDelete = List.of();

    /**
     * The following map individual methods to decorators
     * These will be applied to the corresponding method in the {@link EntityService}
     * i.e. bulkSave will be applied to the bulkSave method
     */
    private List<EntityServiceDecorator> bulkSave = List.of();
    private List<EntityServiceDecorator> bulkUpdate = List.of();
    private List<EntityServiceDecorator> count = List.of();
    private List<EntityServiceDecorator> countByQuery = List.of();
    private List<EntityServiceDecorator> deleteById = List.of();
    private List<EntityServiceDecorator> deleteByQuery = List.of();
    private List<EntityServiceDecorator> findAll = List.of();
    private List<EntityServiceDecorator> findById = List.of();
    private List<EntityServiceDecorator> findByIds = List.of();
    private List<EntityServiceDecorator> syncIndex = List.of();
    private List<EntityServiceDecorator> save = List.of();
    private List<EntityServiceDecorator> search = List.of();
    private List<EntityServiceDecorator> update = List.of();
}
