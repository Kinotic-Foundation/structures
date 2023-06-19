package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.mapping.DynamicMapping;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import org.apache.commons.lang3.Validate;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.internal.api.services.ElasticConversionResult;
import org.kinotic.structures.internal.api.services.StructureConversionService;
import org.kinotic.structures.internal.utils.StructuresUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.CompletableFuture;


@Component
public class DefaultStructureService extends AbstractCrudService<Structure> implements StructureService {

    private final StructuresProperties structuresProperties;
    private final StructureConversionService structureConversionService;


    public DefaultStructureService(ElasticsearchAsyncClient esAsyncClient,
                                   ReactiveElasticsearchOperations esOperations,
                                   StructuresProperties structuresProperties,
                                   StructureConversionService structureConversionService,
                                   CrudServiceTemplate crudServiceTemplate) {
        // FIXME: should we use the prefix in the structure index name?
        super("structure", Structure.class, esAsyncClient, esOperations, crudServiceTemplate);
        this.structuresProperties = structuresProperties;
        this.structureConversionService = structureConversionService;
    }

    @Override
    public CompletableFuture<Long> countForNamespace(String namespace) {
        return crudServiceTemplate.count(indexName, builder -> builder
                .query(q -> q
                        .bool(b -> b
                                .filter(TermQuery.of(tq -> tq.field("namespace").value(namespace))._toQuery()
                                )
                        )));
    }

    @Override
    public CompletableFuture<Structure> create(Structure entity) {
        Validate.notNull(entity);
        return save(entity);
    }

    @Override
    public CompletableFuture<Structure> save(Structure structure) {

        if(structure.getName() == null || structure.getName().isBlank() || structure.getName().contains(".")){
            return CompletableFuture.failedFuture(new IllegalArgumentException("Structure Name Invalid"));
        }
        if(structure.getNamespace() == null || structure.getNamespace().isBlank()){
            return CompletableFuture.failedFuture(new IllegalArgumentException("Structure Namespace Invalid"));
        }
        if(structure.getEntityDefinition() == null){
            return CompletableFuture.failedFuture(new IllegalArgumentException("Structure entityDefinition must not be null"));
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
                    if (existingStructure != null){

                        // trying to save a new structure with an existing name
                        if(structure.getId() == null){
                            ret = CompletableFuture.failedFuture(new IllegalArgumentException("Structure Namespace+Name must be unique, '"+logicalIndexName+"' already exists."));
                        }else{
                            // updating an existing structure, reconcile the differences
                            structure.setUpdated(new Date());

                            // FIXME: reconcile structure differences (should be async)
                            ret = super.save(structure);
                        }
                    }else{
                        // new structure
                        structure.setId(logicalIndexName);
                        structure.setCreated(new Date());
                        structure.setUpdated(structure.getCreated());
                        // Store name of the elastic search index for items
                        structure.setItemIndex(this.structuresProperties.getIndexPrefix().trim().toLowerCase()+logicalIndexName);

                        // Try and create ES mapping to make sure IDL is valid
                        ElasticConversionResult result = structureConversionService.convertToElasticMapping(structure);

                        structure.setMultiTenancyType(result.getMultiTenancyType());

                        ret = super.save(structure);
                    }
                    return ret;
                });
    }

    @Override
    public CompletableFuture<Void> deleteById(String id) {
        return findById(id)
                .thenCompose(structure -> {
                    CompletableFuture<Void> ret;
                    if(structure != null){
                        if(structure.isPublished()){
                            ret = CompletableFuture.failedFuture(new IllegalArgumentException("Structure must be unpublished before deleting"));
                        }else{
                            ret = super.deleteById(id);
                        }
                    }else{
                        ret = CompletableFuture.failedFuture(new IllegalArgumentException("No Structure found with id: "+id));
                    }
                    return ret;
                });
    }

    @Override
    public CompletableFuture<Page<Structure>> findAllPublishedForNamespace(String namespace, Pageable pageable) {
        return crudServiceTemplate.search(indexName, pageable, type, builder -> builder
                .query(q -> q
                        .bool(b -> b
                                .filter(TermQuery.of(tq -> tq.field("namespace").value(namespace))._toQuery(),
                                        TermQuery.of(tq -> tq.field("published").value(true))._toQuery())
                        )
                ));
    }

    @Override
    public CompletableFuture<Void> publish(String structureId) {
        return findById(structureId)
                .thenCompose(structure -> {

                    CompletableFuture<Void> ret;

                    if(structure != null) {

                        if (structure.isPublished()) {
                            ret = CompletableFuture.failedFuture(new IllegalArgumentException("Structure is already published"));
                        } else {

                            ElasticConversionResult result = structureConversionService.convertToElasticMapping(structure);

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

                                return super.save(structure).thenApply(structure1 -> null);
                            });
                        }
                    }else{
                        ret = CompletableFuture.failedFuture(new IllegalArgumentException("No Structure found with id: "+structureId));
                    }
                    return ret;
                });
    }

    @Override
    public CompletableFuture<Void> unPublish(String structureId) {
        return findById(structureId)
                .thenCompose(structure -> {

                    CompletableFuture<Void> ret;

                    if(structure != null){

                        if(structure.isPublished()){

                            ret = esAsyncClient.indices().delete(builder -> builder.index(structure.getItemIndex()))
                                               .thenCompose(deleteIndexResponse -> {
                                                   structure.setPublished(false);
                                                   structure.setPublishedTimestamp(null);
                                                   structure.setUpdated(new Date());
                                                   return super.save(structure).thenApply(structure1 -> null);
                                               });
                        }else {
                            ret = CompletableFuture.failedFuture(new IllegalArgumentException("Structure is not published"));
                        }
                    }else{
                        ret = CompletableFuture.failedFuture(new IllegalArgumentException("No Structure found with id: "+structureId));
                    }
                    return ret;
                });
    }

    @Override
    public CompletableFuture<Page<Structure>> search(String searchText, Pageable pageable) {
        return null;
    }

}
