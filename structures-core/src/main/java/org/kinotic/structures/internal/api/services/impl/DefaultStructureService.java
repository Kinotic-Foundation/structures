package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch.indices.DataStreamVisibility;
import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.RequiredArgsConstructor;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.idl.decorators.MultiTenancyType;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.internal.api.services.CacheEvictionService;
import org.kinotic.structures.internal.api.services.ElasticConversionResult;
import org.kinotic.structures.internal.api.services.StructureConversionService;
import org.kinotic.structures.internal.api.services.StructureDAO;
import org.kinotic.structures.internal.utils.StructuresUtil;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Component
@RequiredArgsConstructor
public class DefaultStructureService implements StructureService {

    private final CacheEvictionService cacheEvictionService;
    private final CrudServiceTemplate crudServiceTemplate;
    private final ElasticsearchAsyncClient esAsyncClient;
    private final StructureConversionService structureConversionService;
    private final StructureDAO structureDAO;
    private final StructuresProperties structuresProperties;


    @WithSpan
    @Override
    public CompletableFuture<Long> count() {
        return structureDAO.count();
    }

    @WithSpan
    @Override
    public CompletableFuture<Long> countForApplication(@SpanAttribute("applicationId") String applicationId) {
        return structureDAO.countForApplication(applicationId);
    }

    @WithSpan
    @Override
    public CompletableFuture<Long> countForProject(@SpanAttribute("applicationId") String applicationId, 
                                                   @SpanAttribute("projectId") String projectId) {
        return structureDAO.countForProject(applicationId, projectId);
    }

    @WithSpan
    @Override
    public CompletableFuture<Structure> create(@SpanAttribute("structure") Structure structure) {
        String logicalIndexName;
        try {
            // will throw an exception if invalid
            StructuresUtil.validateStructure(structure);

            structure.setApplicationId(structure.getApplicationId().trim());
            structure.setName(structure.getName().trim());
            logicalIndexName = StructuresUtil.structureNameToId(structure.getApplicationId(), structure.getName());

            if(logicalIndexName.length() > 255){
                throw new IllegalArgumentException("Structure Id is too long, 'applicationId.name' must be less than 256 characters");
            }

        } catch (IllegalArgumentException e) {
            return CompletableFuture.failedFuture(e);
        }

        return findById(logicalIndexName)
                .thenCompose(existingStructure -> {

                    // Check if this is an existing structure or new one
                    if (existingStructure != null) {
                        return CompletableFuture.failedFuture(new IllegalArgumentException(
                                "Structure Application+Name must be unique, '" + logicalIndexName + "' already exists."));
                    }

                    // TODO: how to ensure structures application name match the C3Type name
                    // Should we just use the Structures one?

                    structure.setId(logicalIndexName);
                    structure.setCreated(new Date());
                    structure.setUpdated(structure.getCreated());
                    // Store name of the elastic search index for items
                    structure.setItemIndex(this.structuresProperties.getIndexPrefix()
                                                                    .trim()
                                                                    .toLowerCase() + logicalIndexName);

                    ElasticConversionResult result = structureConversionService.convertToElasticMapping(structure);

                    structure.setDecoratedProperties(result.decoratedProperties());
                    structure.setMultiTenancyType(result.entityDecorator().getMultiTenancyType());
                    structure.setEntityType(result.entityDecorator().getEntityType());
                    structure.setVersionFieldName(result.versionFieldName());
                    structure.setTenantIdFieldName(result.tenantIdFieldName());
                    structure.setTimeReferenceFieldName(result.timeReferenceFieldName());

                    return  structureDAO.save(structure);
                });
    }

    @WithSpan
    @Override
    public CompletableFuture<Void> deleteById(@SpanAttribute("structureId") String structureId) {
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

    @WithSpan
    @Override
    public CompletableFuture<Page<Structure>> findAll(Pageable pageable) {
        return structureDAO.findAll(pageable);
    }

    @WithSpan
    @Override
    public CompletableFuture<Page<Structure>> findAllPublishedForApplication(@SpanAttribute("applicationId") String applicationId, Pageable pageable) {
        return structureDAO.findAllPublishedForApplication(applicationId, pageable);
    }

    @WithSpan
    @Override
    public CompletableFuture<Page<Structure>> findAllForApplication(@SpanAttribute("applicationId") String applicationId, Pageable pageable) {
        return structureDAO.findAllForApplication(applicationId, pageable);
    }

    @WithSpan
    @Override
    public CompletableFuture<Page<Structure>> findAllForProject(@SpanAttribute("applicationId") String applicationId, 
                                                                @SpanAttribute("projectId") String projectId, Pageable pageable) {
        return structureDAO.findAllForProject(applicationId, projectId, pageable);
    }

    @WithSpan
    @Override
    public CompletableFuture<Structure> findById(@SpanAttribute("structureId") String structureId) {
        return structureDAO.findById(structureId);
    }

    @WithSpan
    @Override
    public CompletableFuture<Void> publish(@SpanAttribute("structureId") String structureId) {
        return findById(structureId)
                .thenCompose(structure -> {
                    if (structure == null) {
                        return CompletableFuture.failedFuture(
                                new IllegalArgumentException("Structure cannot be found for id: " + structureId));
                    }
                    if (structure.isPublished()) {
                        return CompletableFuture.failedFuture(
                                new IllegalStateException("Structure is already published"));
                    }

                    ElasticConversionResult result = structureConversionService.convertToElasticMapping(structure);
                    Map<String, Property> mappings = result.objectProperty().properties();
                    String templateName = structure.getItemIndex() + "_tpl";
                    boolean allowCustomRouting = structure.getMultiTenancyType() == MultiTenancyType.SHARED;

                    CompletableFuture<Void> creationFuture = structure.isStream()
                            ? crudServiceTemplate
                            .createIndexTemplate(templateName,
                                                 structure.getItemIndex() + "*",
                                                 DataStreamVisibility.of(b -> b.allowCustomRouting(allowCustomRouting)),
                                                 mappings)
                            .thenCompose(v -> crudServiceTemplate.createDataStream(structure.getItemIndex()))
                            : crudServiceTemplate
                            .createIndex(structure.getItemIndex(), true, mappings);

                    return creationFuture.thenCompose(v -> {
                        structure.setPublished(true);
                        structure.setPublishedTimestamp(new Date());
                        structure.setUpdated(structure.getPublishedTimestamp());
                        return structureDAO.save(structure)
                                           .thenApply(structure1 -> {
                                               cacheEvictionService.evictCachesFor(structure);
                                               return null;
                                           });
                    });
                });
    }

    @WithSpan
    @Override
    public CompletableFuture<Structure> save(@SpanAttribute("structure") Structure structure) {
        try {
            if (structure.getId() == null || structure.getId().isBlank()) {
                throw new IllegalArgumentException("Structure Id Invalid");
            }
            StructuresUtil.validateStructure(structure);
        } catch (IllegalArgumentException e) {
            return CompletableFuture.failedFuture(e);
        }

        return findById(structure.getId())
                .thenCompose(existingStructure -> {
                    if (existingStructure == null) {
                        return CompletableFuture.failedFuture(
                                new IllegalArgumentException("Structure cannot be found for id: " + structure.getId()));
                    }

                    structure.setUpdated(new Date());
                    structure.setCreated(existingStructure.getCreated());
                    structure.setName(existingStructure.getName());
                    structure.setApplicationId(existingStructure.getApplicationId());
                    structure.setItemIndex(existingStructure.getItemIndex());
                    structure.setPublished(existingStructure.isPublished());
                    structure.setPublishedTimestamp(existingStructure.getPublishedTimestamp());

                    ElasticConversionResult result = structureConversionService.convertToElasticMapping(structure);
                    Map<String, Property> mappings = result.objectProperty().properties();

                    structure.setDecoratedProperties(result.decoratedProperties());
                    structure.setMultiTenancyType(result.entityDecorator().getMultiTenancyType());
                    structure.setEntityType(result.entityDecorator().getEntityType());
                    structure.setVersionFieldName(result.versionFieldName());
                    structure.setTenantIdFieldName(result.tenantIdFieldName());
                    structure.setTimeReferenceFieldName(result.timeReferenceFieldName());

                    if (structure.isPublished()) {
                        if (!existingStructure.isMultiTenantSelectionEnabled()
                                && structure.isMultiTenantSelectionEnabled()
                                && !structuresProperties.getTenantIdFieldName().equals(structure.getTenantIdFieldName())) {
                            return CompletableFuture.failedFuture(
                                    new IllegalArgumentException("When enabling multi-tenant selection for an existing published Structure, the tenantId field must be set to: " + structuresProperties.getTenantIdFieldName()));
                        }

                        if (!existingStructure.isStream() && structure.isStream()) {
                            return CompletableFuture.failedFuture(
                                    new IllegalArgumentException("Cannot change an existing published Structure from a non-stream to a stream"));
                        }

                        // FIXME: how to best handle an operation where the mapping completes but the save fails.
                        //        Additionally this could have serious race conditions if multiple clients are updating the same structure
                        //        This could probably be solved by verifying the mapping is still valid before saving
                        //        (diff the fields and make sure only fields are added and no types are changed)
                        //        Then this could be moved to save the structure first with optimistic locking, and if that succeeds then update the mapping


                        CompletableFuture<Void> updateFuture;
                        if (structure.isStream()) {
                            String templateName = structure.getItemIndex() + "_tpl";
                            // Update both the template (for future indices) and the data stream's current indices
                            updateFuture = crudServiceTemplate.updateIndexTemplate(templateName, mappings)
                                                              .thenCompose(v -> crudServiceTemplate.updateIndexMapping(structure.getItemIndex(), mappings));
                        } else {
                            // For regular indices, just update the mappings
                            updateFuture = crudServiceTemplate.updateIndexMapping(structure.getItemIndex(), mappings);
                        }

                        return updateFuture.thenCompose(v -> structureDAO
                                .save(structure)
                                .thenApply(structure1 -> {
                                    cacheEvictionService.evictCachesFor(structure1);
                                    return structure1;
                                }));
                    } else {
                        return structureDAO.save(structure);
                    }
                });
    }


    @WithSpan
    @Override
    public CompletableFuture<Page<Structure>> search(@SpanAttribute("searchText") String searchText, Pageable pageable) {
        return structureDAO.search(searchText, pageable);
    }

    @WithSpan
    @Override
    public CompletableFuture<Void> unPublish(@SpanAttribute("structureId") String structureId) {
        return findById(structureId)
                .thenCompose(structure -> {
                    if (structure == null) {
                        return CompletableFuture.failedFuture(
                                new IllegalArgumentException("Structure cannot be found for id: " + structureId));
                    }

                    if (!structure.isPublished()) {
                        return CompletableFuture.failedFuture(
                                new IllegalStateException("Structure is not published"));
                    }

                    CompletableFuture<Void> deleteStorageFuture;
                    if (structure.isStream()) {
                        String templateName = structure.getItemIndex() + "_tpl";
                        // Delete the data stream and its template
                        deleteStorageFuture = crudServiceTemplate.deleteDataStream(structure.getItemIndex())
                                                                 .thenCompose(v -> crudServiceTemplate.deleteIndexTemplate(templateName));
                    } else {
                        // Delete the regular index
                        deleteStorageFuture = esAsyncClient.indices()
                                                           .delete(builder -> builder.index(structure.getItemIndex()))
                                                           .thenApply(response -> null);
                    }

                    return deleteStorageFuture.thenCompose(v -> {
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
    public CompletableFuture<Void> syncIndex() {
        return structureDAO.syncIndex();
    }

}
