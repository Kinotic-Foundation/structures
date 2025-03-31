package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.mapping.DynamicMapping;
import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.RequiredArgsConstructor;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.internal.api.services.CacheEvictionService;
import org.kinotic.structures.internal.api.services.ElasticConversionResult;
import org.kinotic.structures.internal.api.services.StructureConversionService;
import org.kinotic.structures.internal.api.services.StructureDAO;
import org.kinotic.structures.internal.utils.StructuresUtil;
import org.springframework.stereotype.Component;

import java.util.Date;
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
    public CompletableFuture<Long> countForNamespace(@SpanAttribute("namespace") String namespace) {
        return structureDAO.countForNamespace(namespace);
    }

    @WithSpan
    @Override
    public CompletableFuture<Structure> create(@SpanAttribute("structure") Structure structure) {
        String logicalIndexName;
        try {
            // will throw an exception if invalid
            StructuresUtil.validateStructure(structure);

            structure.setNamespace(structure.getNamespace().trim());
            structure.setName(structure.getName().trim());
            logicalIndexName = StructuresUtil.structureNameToId(structure.getNamespace(), structure.getName());

            if(logicalIndexName.length() > 255){
                throw new IllegalArgumentException("Structure Id is too long, 'namespace.name' must be less than 256 characters");
            }

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

                    // TODO: how to ensure structures namespace name match the C3Type name
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
    public CompletableFuture<Page<Structure>> findAllPublishedForNamespace(@SpanAttribute("namespace") String namespace, Pageable pageable) {
        return structureDAO.findAllPublishedForNamespace(namespace, pageable);
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
                                                            .properties(result.objectProperty().properties()));

                            })
                            .thenCompose(createIndexResponse -> {
                                // update tracking fields
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
                    // short circuit validation
                    if(existingStructure == null){
                        return CompletableFuture.failedFuture(new IllegalArgumentException("Structure cannot be found for id: " + structure.getId()));
                    }

                    // TODO: how to ensure structures namespace name match the C3Type name
                    // Should we just use the Structures one?

                    // FIXME: handle namespace/name changes
                    structure.setUpdated(new Date());
                    structure.setCreated(existingStructure.getCreated());
                    structure.setName(existingStructure.getName());
                    structure.setNamespace(existingStructure.getNamespace());
                    structure.setItemIndex(existingStructure.getItemIndex());
                    structure.setPublished(existingStructure.isPublished());
                    structure.setPublishedTimestamp(existingStructure.getPublishedTimestamp());

                    ElasticConversionResult result = structureConversionService.convertToElasticMapping(structure);

                    structure.setDecoratedProperties(result.decoratedProperties());
                    structure.setMultiTenancyType(result.entityDecorator().getMultiTenancyType());
                    structure.setVersionFieldName(result.versionFieldName());
                    structure.setTenantIdFieldName(result.tenantIdFieldName());
                    structure.setTimeReferenceFieldName(result.timeReferenceFieldName());

                    if(structure.isPublished()) {

                        if(!existingStructure.isMultiTenantSelectionEnabled()
                                && structure.isMultiTenantSelectionEnabled()
                                && !structuresProperties.getTenantIdFieldName().equals(structure.getTenantIdFieldName())){
                            return CompletableFuture.failedFuture(new IllegalArgumentException("When enabling multi-tenant selection for an existing published Structure, the tenantId field must be set to: " + structuresProperties.getTenantIdFieldName()));
                        }

                        if(!existingStructure.isStream()
                            && structure.isStream()){
                            return CompletableFuture.failedFuture(new IllegalArgumentException("Cannot change an existing published Structure from a non-stream to a stream"));
                        }

                        // FIXME: how to best handle an operation where the mapping completes but the save fails.
                        //        Additionally this could have serious race conditions if multiple clients are updating the same structure
                        //        This could probably be solved by verifying the mapping is still valid before saving
                        //        (diff the fields and make sure only fields are added and no types are changed)
                        //        Then this could be moved to save the structure first with optimistic locking, and if that succeeds then update the mapping
                        return crudServiceTemplate
                                .updateIndexMapping(structure.getItemIndex(),
                                                    mappingBuilder -> mappingBuilder.dynamic(DynamicMapping.Strict)
                                                                                    .properties(result.objectProperty()
                                                                                                      .properties()))
                                .thenCompose(v -> structureDAO
                                        .save(structure)
                                        .thenApply(structure1 -> {
                                            cacheEvictionService.evictCachesFor(structure1);
                                            return structure1;
                                        }));
                    }else{
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
    public CompletableFuture<Void> syncIndex() {
        return structureDAO.syncIndex();
    }

}
