package org.kinotic.structuresserver.traits


import org.elasticsearch.search.SearchHits
import org.kinotic.continuum.api.annotations.Publish
import org.kinotic.continuum.api.annotations.Version
import org.kinotic.structures.api.domain.AlreadyExistsException
import org.kinotic.structures.api.domain.PermenentTraitException
import org.kinotic.structures.api.domain.Trait

@Publish
@Version("1.0.0")
interface ITraitManager {

    Trait save(Trait saveTrait) throws AlreadyExistsException, PermenentTraitException

    Optional<Trait> getTraitById(String id)

    Optional<Trait> getTraitByName(String name)

    SearchHits getAll(int numberPerPage, int page, String columnToSortBy, boolean descending)

    SearchHits getAllNameLike(String nameLike, int numberPerPage, int page, String columnToSortBy, boolean descending);

    void delete(String traitId) throws PermenentTraitException

}
