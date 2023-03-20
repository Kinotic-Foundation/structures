package org.kinotic.structuresserver.structures

import org.kinotic.continuum.api.annotations.Publish
import org.kinotic.continuum.api.annotations.Version
import org.kinotic.structures.api.domain.AlreadyExistsException
import org.kinotic.structures.api.domain.Structure
import org.kinotic.structures.api.domain.Trait
import org.kinotic.structuresserver.domain.StructureHolder
import org.kinotic.structuresserver.serializer.Structures


@Publish
@Version("1.0.0")
interface IStructureManager {

    StructureHolder save(StructureHolder structureHolder) throws AlreadyExistsException

    Structures getAll(int numberPerPage, int page, String columnToSortBy, boolean descending)

    StructureHolder getStructureById(String id)

    Structures getAllIdLike(String IdLike, int numberPerPage, int page, String columnToSortBy, boolean descending)

    Structures getAllPublishedAndIdLike(String IdLike, int numberPerPage, int page, String columnToSortBy, boolean descending)

    Structures getAllPublished(int numberPerPage, int page, String columnToSortBy, boolean descending)

    void delete(String structureId)

    void publish(String structureId)

    void addTraitToStructure(String structureId, String fieldId, Trait newTrait)

    void insertTraitBeforeAnotherForStructure(String structureId, String movingTraitId, String insertBeforeTraitId)

    void insertTraitAfterAnotherForStructure(String structureId, String movingTraitId, String insertAfterTraitId)

    String getJsonSchema(String structureId);

    String getElasticSearchBaseMapping(String structureId);

}