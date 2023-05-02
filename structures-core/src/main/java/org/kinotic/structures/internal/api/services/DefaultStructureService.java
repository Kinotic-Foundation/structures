package org.kinotic.structures.internal.api.services;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.mapping.ObjectProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import org.kinotic.continuum.idl.api.ObjectC3Type;
import org.kinotic.continuum.idl.converter.IdlConverter;
import org.kinotic.continuum.idl.converter.IdlConverterFactory;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.internal.api.services.util.StructureHelper;
import org.kinotic.structures.internal.config.StructuresProperties;
import org.kinotic.structures.internal.idl.converters.elastic.C3ToEsConverterStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;


@Component
public class DefaultStructureService extends AbstractCrudService<Structure> implements StructureService {

    private final StructuresProperties structuresProperties;
    private final IdlConverterFactory idlConverterFactory;
    private final C3ToEsConverterStrategy c3ToEsConverterStrategy = new C3ToEsConverterStrategy();

    public DefaultStructureService(ElasticsearchAsyncClient esAsyncClient,
                                   ReactiveElasticsearchOperations esOperations,
                                   IdlConverterFactory idlConverterFactory,
                                   StructuresProperties structuresProperties) {
        super("structure", Structure.class, esAsyncClient, esOperations);
        this.idlConverterFactory = idlConverterFactory;
        this.structuresProperties = structuresProperties;
    }

    @Override
    public CompletableFuture<Long> countForNamespace(String namespace) {
        return count(builder -> builder
                .query(q -> q
                    .bool(b -> b
                        .filter(TermQuery.of(tq -> tq.field("namespace").value(namespace))._toQuery()
                    )
                )));
    }

    @Override
    public CompletableFuture<Structure> save(Structure structure) {

        if(structure.getName() == null || structure.getName().isBlank()){
            return CompletableFuture.failedFuture(new IllegalArgumentException("Structures must provide a proper Structure Name."));
        }
        if(structure.getNamespace() == null || structure.getNamespace().isBlank()){
            return CompletableFuture.failedFuture(new IllegalArgumentException("Structures must provide a proper Structure Namespace."));
        }
        if(structure.getItemDefinition() == null){
            return CompletableFuture.failedFuture(new IllegalArgumentException("Structures must provide a proper Structure ItemDefinition."));
        }

        String logicalIndexName = (structure.getNamespace().trim()+structure.getName().trim()).toLowerCase();

        // will throw an exception if invalid
        try {
            StructureHelper.indexNameValidation(logicalIndexName);
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
                        structure.setUpdated(System.currentTimeMillis());

                        // FIXME: reconcile structure differences (should be async)
                        ret = super.save(structure);
                    }
                }else{
                    // new structure
                    structure.setId(logicalIndexName);
                    structure.setCreated(System.currentTimeMillis());
                    structure.setUpdated(structure.getCreated());
                    // Store name of the elastic search index for items
                    structure.setItemIndex(this.structuresProperties.getIndexPrefix().trim().toLowerCase()+logicalIndexName);

                    // Try and create ES mapping to make sure IDL is valid
                    createEsObject(structure.getItemDefinition());

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
        return findAll(pageable, builder -> builder
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

                        ret = createIndex(structure.getItemIndex(), true, indexBuilder -> {

                           indexBuilder.mappings(m -> m.properties(createEsObject(structure.getItemDefinition()).properties()));

                        })
                        .thenCompose(createIndexResponse -> {
                            // update tracking fields
                            structure.setPublished(true);
                            structure.setPublishedTimestamp(System.currentTimeMillis());
                            structure.setUpdated(structure.getPublishedTimestamp());

                            // FIXME: add optimizations for decorators needed by structure (should be async)

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
                                    structure.setPublishedTimestamp(0);
                                    structure.setUpdated(System.currentTimeMillis());
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

    private ObjectProperty createEsObject(ObjectC3Type objectC3Type){
        ObjectProperty ret;
        IdlConverter<Property> converter = idlConverterFactory.createConverter(c3ToEsConverterStrategy);
        Property esProperty = converter.convert(objectC3Type);

        if(esProperty.isObject()){
            ret = esProperty.object();
        }else{
            throw new IllegalStateException("ItemDefinition must be an object");
        }
        return ret;
    }

}
