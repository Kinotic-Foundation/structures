package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.mapping.DynamicMapping;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
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
    public CompletableFuture<Structure> save(Structure structure) {

        if (structure.getName() == null || structure.getName().isBlank() || structure.getName().contains(".")) {
            return CompletableFuture.failedFuture(new IllegalArgumentException("Structure Name Invalid"));
        }
        if (structure.getNamespace() == null || structure.getNamespace().isBlank()) {
            return CompletableFuture.failedFuture(new IllegalArgumentException("Structure Namespace Invalid"));
        }
        if (structure.getEntityDefinition() == null) {
            return CompletableFuture.failedFuture(new IllegalArgumentException(
                    "Structure entityDefinition must not be null"));
        }

        structure.setNamespace(structure.getNamespace().trim());
        structure.setName(structure.getName().trim());

        String logicalIndexName = StructuresUtil.structureNameToId(structure.getNamespace(), structure.getName());

        // will throw an exception if invalid
        try {
            StructuresUtil.indexNameValidation(logicalIndexName);
        } catch (IllegalArgumentException e) {
            return CompletableFuture.failedFuture(e);
        }

        return findById(logicalIndexName)
                .thenCompose(existingStructure -> {

                    CompletableFuture<Structure> ret;

                    // Check if this is an existing structure or new one
                    if (existingStructure != null) {

                        // trying to save a new structure with an existing name
                        if (structure.getId() == null) {
                            ret = CompletableFuture.failedFuture(new IllegalArgumentException(
                                    "Structure Namespace+Name must be unique, '" + logicalIndexName + "' already exists."));
                        } else {
                            // updating an existing structure, reconcile the differences
                            structure.setUpdated(new Date());

                            // FIXME: reconcile structure differences (should be async)
                            ret = structureDAO.save(structure);
                        }
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

                        structure.setMultiTenancyType(result.getMultiTenancyType());

                        ret = structureDAO.save(structure);
                    }
                    return ret;
                });
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

                            ElasticConversionResult result = structureConversionService.convertToElasticMapping(
                                    structure);

                            ret = crudServiceTemplate.createIndex(structure.getItemIndex(), true, indexBuilder -> {

                                                         indexBuilder.mappings(m -> {
                                                             TypeMapping.Builder builder =
                                                                     m.dynamic(DynamicMapping.Strict)
                                                                      .properties(result.getObjectProperty().properties());

                                                             // if shared multi tenancy make sure routing is required
                                                             // TODO: see if we need this or it should be optional
//                                    if(structure.getMultiTenancyType() == MultiTenancyType.SHARED){
//                                        builder.routing(b -> b.required(true));
//                                    }

                                                             return builder;
                                                         });

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
