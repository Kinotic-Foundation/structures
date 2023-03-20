package org.kinotic.structuresserver.structures

import org.kinotic.structures.api.domain.AlreadyExistsException
import org.kinotic.structures.api.domain.Structure
import org.kinotic.structures.api.domain.Trait
import org.kinotic.structures.api.services.StructureService
import org.kinotic.structures.internal.api.services.util.EsHighLevelClientUtil
import org.kinotic.structuresserver.domain.StructureHolder
import org.kinotic.structuresserver.domain.TraitHolder
import org.kinotic.structuresserver.serializer.Structures
import org.elasticsearch.search.SearchHit
import org.elasticsearch.search.SearchHits
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class StructureManager implements IStructureManager {

    @Autowired
    StructureService structureService

    @Override
    StructureHolder save(StructureHolder structureHolder) throws AlreadyExistsException {
        LinkedHashMap<String, Trait> traits = new LinkedHashMap<>()
        structureHolder.traits.sort(new Comparator<TraitHolder>() {
            @Override
            int compare(TraitHolder o1, TraitHolder o2) {
                return o1.order - o2.order
            }
        })

        for(TraitHolder holder : structureHolder.traits){
            traits.put(holder.fieldName, holder.fieldTrait)
        }

        structureHolder.structure.setTraits(traits)

        Structure savedStructure = structureService.save(structureHolder.structure)

        new StructureHolder(savedStructure, structureHolder.traits)
    }

    @Override
    Structures getAll(int numberPerPage, int page, String columnToSortBy, boolean descending) {
        gatherStructures(structureService.getAll(numberPerPage, page, columnToSortBy, descending))
    }

    @Override
    Structures getAllIdLike(String IdLike, int numberPerPage, int page, String columnToSortBy, boolean descending) {
        gatherStructures(structureService.getAllIdLike(IdLike, numberPerPage, page, columnToSortBy, descending))
    }

    @Override
    StructureHolder getStructureById(String id) {
        Structure structure = structureService.getStructureById(id).get()
        LinkedList<TraitHolder> traits = new LinkedList<>()
        int index = 0
        for(def traitEntry : structure.traits.entrySet()){
            traits.add(new TraitHolder(index, traitEntry.key, traitEntry.value))
            index++
        }
        return new StructureHolder(structure, traits)
    }

    @Override
    Structures getAllPublishedAndIdLike(String IdLike, int numberPerPage, int page, String columnToSortBy, boolean descending) {
        gatherStructures(structureService.getAllPublishedAndIdLike(IdLike, numberPerPage, page, columnToSortBy, descending))
    }

    @Override
    Structures getAllPublished(int numberPerPage, int page, String columnToSortBy, boolean descending) {
        gatherStructures(structureService.getAllPublished(numberPerPage, page, columnToSortBy, descending))
    }

    @Override
    void delete(String structureId) {
        structureService.delete(structureId)
    }

    @Override
    void publish(String structureId) {
        structureService.publish(structureId)
    }

    @Override
    void addTraitToStructure(String structureId, String fieldId, Trait newTrait) {
        structureService.addTraitToStructure(structureId, fieldId, newTrait)
    }

    @Override
    void insertTraitBeforeAnotherForStructure(String structureId, String movingTraitId, String insertBeforeTraitId) {
        structureService.insertTraitBeforeAnotherForStructure(structureId, movingTraitId, insertBeforeTraitId)
    }

    @Override
    void insertTraitAfterAnotherForStructure(String structureId, String movingTraitId, String insertAfterTraitId) {
        structureService.insertTraitAfterAnotherForStructure(structureId, movingTraitId, insertAfterTraitId)
    }

    @Override
    String getJsonSchema(String structureId) {
        Structure structure = structureService.getStructureById(structureId).get()
        structureService.getJsonSchema(structure)
    }

    @Override
    String getElasticSearchBaseMapping(String structureId) {
        Structure structure = structureService.getStructureById(structureId).get()
        structureService.getElasticSearchBaseMapping(structure)
    }

    static Structures gatherStructures(SearchHits hits){
        LinkedList<StructureHolder> holderList = new LinkedList<>()
        for(SearchHit hit : hits){
            Structure structure = EsHighLevelClientUtil.getTypeFromBytesReference(hit.getSourceRef(), Structure.class)
            LinkedList<TraitHolder> traits = new LinkedList<>()
            int index = 0
            for(def traitEntry : structure.traits.entrySet()){
                traits.add(new TraitHolder(index, traitEntry.key, traitEntry.value))
                index++
            }
            holderList.add(new StructureHolder(structure, traits))
        }
        new Structures(holderList, hits.getTotalHits().value)
    }
}
