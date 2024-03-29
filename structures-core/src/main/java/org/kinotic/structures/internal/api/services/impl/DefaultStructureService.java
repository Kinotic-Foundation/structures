package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.mapping.DynamicMapping;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.internal.api.services.CacheEvictionService;
import org.kinotic.structures.internal.api.services.ElasticConversionResult;
import org.kinotic.structures.internal.api.services.StructureConversionService;
import org.kinotic.structures.internal.api.services.StructureDAO;
import org.kinotic.structures.internal.utils.StructuresUtil;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.CompletableFuture;


@Component
public class DefaultStructureService implements StructureService {

    private final StructuresProperties structuresProperties;
    private final StructureConversionService structureConversionService;
    private final CacheEvictionService cacheEvictionService;
    private final StructureDAO structureDAO;
    private final ElasticsearchAsyncClient esAsyncClient;
    private final CrudServiceTemplate crudServiceTemplate;

    public DefaultStructureService(StructuresProperties structuresProperties,
                                   StructureConversionService structureConversionService,
                                   CacheEvictionService cacheEvictionService,
                                   StructureDAO structureDAO,
                                   ElasticsearchAsyncClient esAsyncClient,
                                   CrudServiceTemplate crudServiceTemplate) {
        this.structuresProperties = structuresProperties;
        this.structureConversionService = structureConversionService;
        this.cacheEvictionService = cacheEvictionService;
        this.structureDAO = structureDAO;
        this.esAsyncClient = esAsyncClient;
        this.crudServiceTemplate = crudServiceTemplate;
    }

    @Override
    public CompletableFuture<Long> countForNamespace(String namespace) {
        return structureDAO.countForNamespace(namespace);
    }

    @Override
    public CompletableFuture<Structure> create(Structure structure) {
        String logicalIndexName;
        try {
            // will throw an exception if invalid
            validateStructure(structure);

            structure.setNamespace(structure.getNamespace().trim());
            structure.setName(structure.getName().trim());
            logicalIndexName = StructuresUtil.structureNameToId(structure.getNamespace(), structure.getName());

            StructuresUtil.indexNameValidation(logicalIndexName);

        } catch (IllegalArgumentException e) {
            return CompletableFuture.failedFuture(e);
        }

        return findById(logicalIndexName)
                .thenCompose(existingStructure -> {

                    // Check if this is an existing structure or new one
                    if (existingStructure != null) {
                        return CompletableFuture.failedFuture(new IllegalArgumentException(
                                "Structure Namespace+Name must be unique, '" + logicalIndexName + "' already exists."));
                    }

                    // new structure
                    structure.setId(logicalIndexName);
                    structure.setCreated(new Date());
                    structure.setUpdated(structure.getCreated());
                    // Store name of the elastic search index for items
                    structure.setItemIndex(this.structuresProperties.getIndexPrefix()
                                                                    .trim()
                                                                    .toLowerCase() + logicalIndexName);

                    // Try and create ES mapping to make sure IDL is valid
                    ElasticConversionResult result = structureConversionService.convertToElasticMapping(structure);

                    // TODO: how to ensure structures namespace name match the C3Type name
                    // Should we just use the Structures one?

                    structure.setMultiTenancyType(result.getMultiTenancyType());

                    return  structureDAO.save(structure);
                });
    }

    @Override
    public CompletableFuture<Structure> save(Structure structure) {

        try {
            if (structure.getId() == null || structure.getId().isBlank()) {
                throw new IllegalArgumentException("Structure Id Invalid");
            }
            validateStructure(structure);
        } catch (IllegalArgumentException e) {
            return CompletableFuture.failedFuture(e);
        }

        return findById(structure.getId())
                .thenCompose(existingStructure -> {
                    // short circuit validation
                    if(existingStructure == null){
                        return CompletableFuture.failedFuture(new IllegalArgumentException("Structure cannot be found for id: " + structure.getId()));
                    }

                    // FIXME: handle namespace/name changes
                    // updating an existing structure, reconcile the differences
                    structure.setUpdated(new Date());
                    structure.setCreated(existingStructure.getCreated());
                    structure.setName(existingStructure.getName());
                    structure.setNamespace(existingStructure.getNamespace());
                    structure.setItemIndex(existingStructure.getItemIndex());
                    structure.setPublished(existingStructure.isPublished());
                    structure.setPublishedTimestamp(null);
                    structure.setDecoratedProperties(null);

                    // Try and create ES mapping to make sure IDL is valid
                    ElasticConversionResult conversionResult = structureConversionService.convertToElasticMapping(structure);

                    // TODO: how to ensure structures namespace name match the C3Type name
                    // Should we just use the Structures one?
                    structure.setMultiTenancyType(conversionResult.getMultiTenancyType());

                    if(structure.isPublished()) {
                        // FIXME: how to best handle an operation where the mapping completes but the save fails.
                        //        Additionally this could have serious race conditions if multiple clients are updating the same structure
                        //        This could probably be solved by verifying the mapping is still valid before saving
                        //        (diff the fields and make sure only fields are added and no types are changed)
                        //        Then this could be moved to save the structure first with optimistic locking, and if that succeeds then update the mapping
                        return crudServiceTemplate
                                .updateIndexMapping(structure.getItemIndex(),
                                                    mappingBuilder -> mappingBuilder.dynamic(DynamicMapping.Strict)
                                                                                    .properties(conversionResult.getObjectProperty()
                                                                                                                .properties()))
                                .thenCompose(v -> {
                                    structure.setDecoratedProperties(conversionResult.getDecoratedProperties());
                                    return structureDAO.save(structure)
                                                       .thenApply(structure1 -> {
                                                           cacheEvictionService.evictCachesFor(structure1);
                                                           return structure1;
                                                       });
                                });
                    }else{
                        return structureDAO.save(structure);
                    }
                });
    }

    private void validateStructure(Structure structure){
        if (structure.getName() == null || structure.getName().isBlank() || structure.getName().contains(".")) {
            throw new IllegalArgumentException("Structure Name Invalid");
        }
        if (structure.getNamespace() == null || structure.getNamespace().isBlank()) {
            throw new IllegalArgumentException("Structure Namespace Invalid");
        }
        if (structure.getEntityDefinition() == null) {
            throw new IllegalArgumentException("Structure entityDefinition must not be null");
        }
    }

    @Override
    public CompletableFuture<Structure> findById(String id) {
        return structureDAO.findById(id);
    }

    @Override
    public CompletableFuture<Long> count() {
        return structureDAO.count();
    }

    @Override
    public CompletableFuture<Void> deleteById(String structureId) {
        return findById(structureId)
                .thenCompose(structure -> {

                    if(structure == null){
                        return CompletableFuture.failedFuture(new IllegalArgumentException("Structure cannot be found for id: " + structureId));
                    }

                    if(structure.isPublished()){
                        return CompletableFuture
                                .failedFuture(new IllegalStateException("Structure must be Un-Published before Deleting"));
                    }

                    return structureDAO.deleteById(structureId);
                });
    }

    @Override
    public CompletableFuture<Page<Structure>> findAll(Pageable pageable) {
        return structureDAO.findAll(pageable);
    }

    @Override
    public CompletableFuture<Page<Structure>> findAllPublishedForNamespace(String namespace, Pageable pageable) {
        return structureDAO.findAllPublishedForNamespace(namespace, pageable);
    }

    @Override
    public CompletableFuture<Void> publish(String structureId) {
        return findById(structureId)
                .thenCompose(structure -> {

                    if(structure == null){
                        return CompletableFuture.failedFuture(new IllegalArgumentException("Structure cannot be found for id: " + structureId));
                    }

                    if(structure.isPublished()){
                        return CompletableFuture
                                .failedFuture(new IllegalStateException("Structure is already published"));
                    }

                    ElasticConversionResult result = structureConversionService.convertToElasticMapping(structure);

                    return crudServiceTemplate
                            .createIndex(structure.getItemIndex(), true, indexBuilder -> {

                                indexBuilder.mappings(m -> m.dynamic(DynamicMapping.Strict)
                                                            .properties(result.getObjectProperty().properties()));

                            })
                            .thenCompose(createIndexResponse -> {
                                // update tracking fields
                                structure.setPublished(true);
                                structure.setPublishedTimestamp(new Date());
                                structure.setUpdated(structure.getPublishedTimestamp());
                                structure.setDecoratedProperties(result.getDecoratedProperties());

                                return structureDAO.save(structure)
                                                   .thenApply(structure1 -> {
                                                       cacheEvictionService.evictCachesFor(structure);
                                                       return null;
                                                   });
                            });
                });
    }

    @Override
    public CompletableFuture<Void> unPublish(String structureId) {
        return findById(structureId)
                .thenCompose(structure -> {

                    if(structure == null){
                        return CompletableFuture.failedFuture(new IllegalArgumentException("Structure cannot be found for id: " + structureId));
                    }

                    if(!structure.isPublished()){
                        return CompletableFuture
                                .failedFuture(new IllegalStateException("Structure is not published"));
                    }

                    return esAsyncClient.indices()
                                        .delete(builder -> builder.index(structure.getItemIndex()))
                                        .thenCompose(deleteIndexResponse -> {
                                            structure.setPublished(false);
                                            structure.setPublishedTimestamp(null);
                                            structure.setUpdated(new Date());
                                            return structureDAO.save(structure)
                                                               .thenApply(structure1 -> {
                                                                   cacheEvictionService.evictCachesFor(structure);
                                                                   return null;
                                                               });
                                        });
                });
    }

    @Override
    public CompletableFuture<Page<Structure>> search(String searchText, Pageable pageable) {
        return structureDAO.search(searchText, pageable);
    }

}
