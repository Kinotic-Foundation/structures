package org.kinotic.structures.api.services;

import com.fasterxml.jackson.databind.util.TokenBuffer;
import org.kinotic.continuum.api.annotations.Publish;
import org.kinotic.continuum.api.security.Participant;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.FastestType;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.TenantSpecificId;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Provides Admin access to entities for a given structure.
 * Admin access allows you to read and write data for tenants other than the one that is logged into by the participant.
 * Created by Nic Padilla 🤪on 6/18/23.
 */
@Publish
public interface AdminJsonEntitiesService {

    /**
     * Updates all given entities, this gives an opportunity to perform partial updates of the data structure.
     *
     * @param structureId the id of the structure to save the entities for
     * @param entities    all the entities to save
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} that will complete when all entities have been saved
     */
    CompletableFuture<Void> bulkSave(String structureId, TokenBuffer entities, Participant participant);

    /**
     * Saves all given entities.
     *
     * @param structureId the id of the structure to update the entities for
     * @param entities    all the entities to save
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} that will complete when all entities have been saved
     */
    CompletableFuture<Void> bulkUpdate(String structureId, TokenBuffer entities, Participant participant);

    /**
     * Returns the number of entities available.
     *
     * @param structureId the id of the structure to count
     * @param tenantSelection the list of tenants to use when retrieving the entity records
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} emitting the number of entities.
     */
    CompletableFuture<Long> count(String structureId, List<String> tenantSelection, Participant participant);

    /**
     * Returns the number of entities available for the given query.
     *
     * @param structureId the id of the structure to count. (this is the {@link Structure#getNamespace()} + "." + {@link Structure#getName()})
     * @param query       the query used to limit result
     * @param tenantSelection the list of tenants to use when retrieving the entity records
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} emitting the number of entities.
     */
    CompletableFuture<Long> countByQuery(String structureId, String query, List<String> tenantSelection, Participant participant);

    /**
     * Deletes the entity with the given id.
     *
     * @param structureId the id of the structure to delete the entity for
     * @param id          must not be {@literal null}
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} emitting when delete is complete
     */
    CompletableFuture<Void> deleteById(String structureId, TenantSpecificId id, Participant participant);

    /**
     * Deletes any entities that match the given query.
     *
     * @param structureId the id of the structure to delete the entity for. (this is the {@link Structure#getNamespace()} + "." + {@link Structure#getName()})
     * @param query       the query used to filter records to delete, must not be {@literal null}
     * @param tenant      the tenant to delete the entity for
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} emitting when delete is complete
     */
    CompletableFuture<Void> deleteByQuery(String structureId, String query, String tenant, Participant participant);

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param structureId the id of the structure to find the entity for
     * @param pageable    the page settings to be used
     * @param tenantSelection the list of tenants to use when retrieving the entity records
     * @param participant the participant of the logged-in user
     * @return a page of entities
     */
    CompletableFuture<Page<FastestType>> findAll(String structureId, Pageable pageable, List<String> tenantSelection, Participant participant);

    /**
     * Retrieves an entity by its id.
     *
     * @param structureId the id of the structure to find the entity for
     * @param id          must not be {@literal null}
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} with the entity with the given id or {@link CompletableFuture} emitting null if none found
     */
    CompletableFuture<FastestType> findById(String structureId, TenantSpecificId id, Participant participant);

    /**
     * Retrieves a list of entities by their id.
     *
     * @param structureId the id of the structure to find the entity for. (this is the {@link Structure#getNamespace()} + "." + {@link Structure#getName()})
     * @param ids         must not be {@literal null}
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} with the list of matched entities with the given ids or {@link CompletableFuture} emitting an empty list if none found
     */
    CompletableFuture<List<FastestType>> findByIds(String structureId, List<TenantSpecificId> ids, Participant participant);

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param structureId the id of the structure to save the entity for
     * @param entity      must not be {@literal null}
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} emitting the saved entity
     */
    CompletableFuture<TokenBuffer> save(String structureId, TokenBuffer entity, Participant participant);

    /**
     * Returns a {@link Page} of entities matching the search text and paging restriction provided in the {@code Pageable} object.
     * <p>
     * You can find more information about the search syntax <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-query-string-query.html#query-string-syntax">here</a>
     *
     * @param structureId the id of the structure to search
     * @param searchText  the text to search for entities for
     * @param pageable    the page settings to be used
     * @param tenantSelection the list of tenants to use when retrieving the entity records
     * @param participant the participant of the logged-in user
     * @return a {@link CompletableFuture} of a page of entities
     */
    CompletableFuture<Page<FastestType>> search(String structureId, String searchText, Pageable pageable, List<String> tenantSelection, Participant participant);

    /**
     * Updates a given entity. This will only override the fields that are present in the given entity.
     * If any fields are not present in the given entity data they will not be changed.
     * If the entity does not exist it will be created.
     *
     * @param structureId the id of the structure to update the entity for
     * @param entity      must not be {@literal null}
     * @param participant the participant of the logged-in user
     * @return {@link CompletableFuture} emitting the saved entity
     */
    CompletableFuture<TokenBuffer> update(String structureId, TokenBuffer entity, Participant participant);

}
