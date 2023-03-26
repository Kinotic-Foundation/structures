package org.kinotic.structuresserver.structures


import org.kinotic.continuum.api.annotations.Publish
import org.kinotic.continuum.api.annotations.Version
import org.kinotic.structures.api.domain.AlreadyExistsException
import org.kinotic.structures.api.domain.PermenentTraitException
import org.kinotic.structures.api.domain.Trait
import org.kinotic.structuresserver.domain.StructureHolder
import org.kinotic.structuresserver.serializer.Structures

@Publish
@Version("1.0.0")
interface IStructureManager {

    StructureHolder save(StructureHolder structureHolder) throws AlreadyExistsException

    Structures getAll(int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException

    StructureHolder getStructureById(String id) throws IOException

    Structures getAllIdLike(String IdLike, int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException

    Structures getAllPublishedAndIdLike(String IdLike, int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException

    Structures getAllNamespaceEquals(String namespace, int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException

    Structures getAllPublishedForNamespace(String namespace, int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException

    Structures getAllPublished(int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException

    void delete(String structureId) throws IOException, PermenentTraitException

    void publish(String structureId) throws IOException

    void addTraitToStructure(String structureId, String fieldId, Trait newTrait) throws IOException

    void insertTraitBeforeAnotherForStructure(String structureId, String movingTraitId, String insertBeforeTraitId) throws IOException

    void insertTraitAfterAnotherForStructure(String structureId, String movingTraitId, String insertAfterTraitId) throws IOException

    String getJsonSchema(String structureId);

    String getElasticSearchBaseMapping(String structureId);

}