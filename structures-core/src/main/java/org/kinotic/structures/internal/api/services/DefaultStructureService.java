package org.kinotic.structures.internal.api.services;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import org.kinotic.structures.api.domain.AlreadyExistsException;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.internal.api.services.util.StructureHelper;
import org.kinotic.structures.internal.config.StructuresProperties;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.stereotype.Component;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;


@Component
public class DefaultStructureService extends AbstractCrudService<Structure> implements StructureService {

    private final StructuresProperties structuresProperties;

    public DefaultStructureService(ElasticsearchAsyncClient esAsyncClient,
                                   ReactiveElasticsearchOperations esOperations,
                                   StructuresProperties structuresProperties) {
        super("structure", Structure.class, esAsyncClient, esOperations);
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
            return CompletableFuture.failedFuture(new IllegalArgumentException("Structures must provide proper Structure Name."));
        }
        if(structure.getNamespace() == null || structure.getNamespace().isBlank()){
            return CompletableFuture.failedFuture(new IllegalArgumentException("Structures must provide proper Structure Namespace."));
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
                        ret = CompletableFuture.failedFuture(new AlreadyExistsException("Structure Namespace+Name must be unique, '"+logicalIndexName+"' already exists."));
                    }else{
                        if(Objects.equals(existingStructure.getUpdated(), structure.getUpdated())){
                            // updating an existing structure, reconcile the differences
                            structure.setUpdated(System.currentTimeMillis());

                            // FIXME: reconcile structure differences (should be async)
                            ret = super.save(structure);
                        }else{
                            ret = CompletableFuture.failedFuture(new OptimisticLockingFailureException("Attempting to update a Structure, but out of sync with database; please re-fetch from database and try again"));
                        }
                    }
                }else{
                    // new structure
                    structure.setId(logicalIndexName);
                    structure.setCreated(System.currentTimeMillis());
                    structure.setUpdated(structure.getCreated());
                    // Store name of the elastic search index for items
                    structure.setItemIndex(this.structuresProperties.getIndexPrefix().trim().toLowerCase()+logicalIndexName);

                    // FIXME: Preprocess itemDefinition to ensure it is valid (should be async)

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

                        ret = createIndex(structure.getItemIndex(), true, new Consumer<CreateIndexRequest.Builder>() {
                                        @Override
                                        public void accept(CreateIndexRequest.Builder builder) {
                                            // FIXME: add mappings for schema
                                        }
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

    static void checkFieldNameFormat(String fieldName){
        if(fieldName.contains("-")
                || fieldName.contains("+")
                || fieldName.contains(".")
                || fieldName.contains("..")
                || fieldName.contains("\\")
                || fieldName.contains("/")
                || fieldName.contains("*")
                || fieldName.contains("?")
                || fieldName.contains("\"")
                || fieldName.contains("<")
                || fieldName.contains(">")
                || fieldName.contains("|")
                || fieldName.contains(" ")
                || fieldName.contains(",")
                || fieldName.contains("#")
                || fieldName.contains(":")
                || fieldName.contains(";")
                || fieldName.getBytes().length > 255){
            throw new IllegalStateException("Field Name is not in correct format, \ncannot contain - + . .. \\ / * ? \" < > | , # : ; \ncannot contain a space or be longer than 255 bytes");
        }
    }

}
