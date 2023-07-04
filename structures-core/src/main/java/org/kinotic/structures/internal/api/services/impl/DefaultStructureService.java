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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                    CompletableFuture<Structure> ret;
                    // Check if this is an existing structure or new one
                    if (existingStructure != null) {
                        ret = CompletableFuture.failedFuture(new IllegalArgumentException(
                                "Structure Namespace+Name must be unique, '" + logicalIndexName + "' already exists."));
                    } else {
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

                        ret = structureDAO.save(structure);
                    }
                    return ret;
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

        // updating an existing structure, reconcile the differences
        structure.setUpdated(new Date());

        // FIXME: when we try and index an entity we have not published the index gets created automatically, so we need to not save any entities \
        //  when the structure is not published.

        // FIXME: what to do when the namespace changes on an unpublished structure?  right now you end up with a broken id and index name

        // FIXME: reconcile structure differences (should be async)
        return structureDAO.save(structure);
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
    public CompletableFuture<Void> deleteById(String id) {
        return findById(id)
                .thenCompose(structure -> {
                    CompletableFuture<Void> ret;
                    if (structure != null) {
                        if (structure.isPublished()) {
                            ret = CompletableFuture.failedFuture(new IllegalArgumentException(
                                    "Structure must be unpublished before deleting"));
                        } else {
                            ret = structureDAO.deleteById(id);
                        }
                    } else {
                        ret = CompletableFuture.failedFuture(new IllegalArgumentException("No Structure found with id: " + id));
                    }
                    return ret;
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

                    CompletableFuture<Void> ret;

                    if (structure != null) {

                        if (structure.isPublished()) {
                            ret = CompletableFuture.failedFuture(new IllegalArgumentException(
                                    "Structure is already published"));
                        } else {

                            ElasticConversionResult result = structureConversionService.convertToElasticMapping(structure);

                            ret = crudServiceTemplate
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
                                                           .thenApply(structure1 -> null);
                                    });
                        }
                    } else {
                        ret = CompletableFuture.failedFuture(new IllegalArgumentException("No Structure found with id: " + structureId));
                    }
                    return ret;
                });
    }

    @Override
    public CompletableFuture<Void> unPublish(String structureId) {
        return findById(structureId)
                .thenCompose(structure -> {

                    CompletableFuture<Void> ret;

                    if (structure != null) {

                        if (structure.isPublished()) {

                            ret = esAsyncClient.indices()
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
                        } else {
                            ret = CompletableFuture.failedFuture(new IllegalArgumentException(
                                    "Structure is not published"));
                        }
                    } else {
                        ret = CompletableFuture.failedFuture(new IllegalArgumentException("No Structure found with id: " + structureId));
                    }
                    return ret;
                });
    }

    @Override
    public CompletableFuture<Page<Structure>> search(String searchText, Pageable pageable) {
        return structureDAO.search(searchText, pageable);
    }

}
