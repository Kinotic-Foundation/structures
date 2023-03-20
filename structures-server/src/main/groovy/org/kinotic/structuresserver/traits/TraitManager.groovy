package org.kinotic.structuresserver.traits

import org.elasticsearch.search.SearchHits
import org.kinotic.structures.api.domain.AlreadyExistsException
import org.kinotic.structures.api.domain.PermenentTraitException
import org.kinotic.structures.api.domain.Trait
import org.kinotic.structures.api.services.TraitService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TraitManager implements ITraitManager {

    @Autowired
    TraitService traitService

    @Override
    Trait save(Trait saveTrait) throws AlreadyExistsException, PermenentTraitException {
        if(saveTrait.isSystemManaged()){
            throw new PermenentTraitException("An attempt to create/modify a System Managed Trait found, this is not allowed")
        }
        traitService.save(saveTrait)
    }

    @Override
    Optional<Trait> getTraitById(String id) {
        return traitService.getTraitById(id)
    }

    @Override
    Optional<Trait> getTraitByName(String name) {
        return traitService.getTraitByName(name)
    }

    @Override
    SearchHits getAll(int numberPerPage, int page, String columnToSortBy, boolean descending) {
        return traitService.getAll(numberPerPage, page, columnToSortBy, descending)
    }

    @Override
    SearchHits getAllNameLike(String query, int numberPerPage, int page, String columnToSortBy, boolean descending) {
        return traitService.getAllNameLike(query, numberPerPage, page, columnToSortBy, descending)
    }

    @Override
    void delete(String traitId) throws PermenentTraitException {
        traitService.delete(traitId)
    }
}
