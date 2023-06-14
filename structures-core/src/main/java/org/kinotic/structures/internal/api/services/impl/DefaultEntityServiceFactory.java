package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.Triple;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.decorators.runtime.crud.*;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.decorators.DecoratorLogic;
import org.kinotic.structures.internal.api.decorators.DelegatingReadPreProcessor;
import org.kinotic.structures.internal.api.decorators.DelegatingUpsertPreProcessor;
import org.kinotic.structures.internal.api.services.EntityService;
import org.kinotic.structures.internal.api.services.EntityServiceFactory;
import org.kinotic.structures.internal.idl.converters.common.DecoratedProperty;
import org.kinotic.structures.internal.utils.StructuresUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * This simplifies the creation of the EntityService, since a lot of dependencies are needed.
 * Created by Navíd Mitchell 🤪 on 5/10/23.
 */
@Component
public class DefaultEntityServiceFactory implements EntityServiceFactory {

    private final ObjectMapper objectMapper;
    private final ElasticsearchAsyncClient esAsyncClient;
    private final CrudServiceTemplate crudServiceTemplate;
    private final Map<String, UpsertFieldPreProcessor<?, ?, ?>> upsertFieldPreProcessors;
    private final Map<String, CountEntityPreProcessor<?>> countEntityPreProcessorMap;
    private final Map<String, DeleteEntityPreProcessor<?>> deleteEntityPreProcessorMap;
    private final Map<String, FindAllPreProcessor<?>> findAllPreProcessorMap;
    private final Map<String, FindByIdPreProcessor<?>> findByIdPreProcessorMap;
    private final Map<String, SearchPreProcessor<?>> searchPreProcessorMap;

    public DefaultEntityServiceFactory(ObjectMapper objectMapper,
                                       ElasticsearchAsyncClient esAsyncClient,
                                       CrudServiceTemplate crudServiceTemplate,
                                       List<UpsertFieldPreProcessor<?, ?, ?>> upsertFieldPreProcessors,
                                       List<CountEntityPreProcessor<?>> countEntityPreProcessors,
                                       List<DeleteEntityPreProcessor<?>> deleteEntityPreProcessors,
                                       List<FindAllPreProcessor<?>> findAllPreProcessors,
                                       List<FindByIdPreProcessor<?>> findByIdPreProcessors,
                                       List<SearchPreProcessor<?>> searchPreProcessors) {
        this.objectMapper = objectMapper;
        this.esAsyncClient = esAsyncClient;
        this.crudServiceTemplate = crudServiceTemplate;

        this.upsertFieldPreProcessors = StructuresUtil.listToMap(upsertFieldPreProcessors,
                                                                 p -> p.implementsDecorator().getName());

        this.countEntityPreProcessorMap = StructuresUtil.listToMap(countEntityPreProcessors, p -> p.implementsDecorator().getName());
        this.deleteEntityPreProcessorMap = StructuresUtil.listToMap(deleteEntityPreProcessors, p -> p.implementsDecorator().getName());
        this.findAllPreProcessorMap = StructuresUtil.listToMap(findAllPreProcessors, p -> p.implementsDecorator().getName());
        this.findByIdPreProcessorMap = StructuresUtil.listToMap(findByIdPreProcessors, p -> p.implementsDecorator().getName());
        this.searchPreProcessorMap = StructuresUtil.listToMap(searchPreProcessors, p -> p.implementsDecorator().getName());
    }

    @SuppressWarnings("unchecked")
    @Override
    public CompletableFuture<EntityService> createEntityService(Structure structure) {

        // Map of jsonPath to DecoratorLogic
        Map<String, DecoratorLogic> fieldPreProcessors = new LinkedHashMap<>();

        List<Triple<String, C3Decorator, CountEntityPreProcessor<C3Decorator>>> countPreProcessors = new ArrayList<>();
        List<Triple<String, C3Decorator, DeleteEntityPreProcessor<C3Decorator>>> deletePreProcessors = new ArrayList<>();
        List<Triple<String, C3Decorator, FindAllPreProcessor<C3Decorator>>>findAllPreProcessors = new ArrayList<>();
        List<Triple<String, C3Decorator, FindByIdPreProcessor<C3Decorator>>>findByIdPreProcessors = new ArrayList<>();
        List<Triple<String, C3Decorator, SearchPreProcessor<C3Decorator>>> searchPreProcessors = new ArrayList<>();

        for(DecoratedProperty decoratedProperty : structure.getDecoratedProperties()){

            for(C3Decorator decorator : decoratedProperty.getDecorators()){

                UpsertFieldPreProcessor<C3Decorator, Object, Object> processor =
                        (UpsertFieldPreProcessor<C3Decorator, Object, Object>)upsertFieldPreProcessors.get(decorator.getClass().getName());
                if(processor != null){
                    // FIXME: without validation the last implementation will win, should we allow multiple per field?
                    fieldPreProcessors.put(decoratedProperty.getJsonPath(), new DecoratorLogic(decorator, processor));
                }

                CountEntityPreProcessor<C3Decorator> countEntityPreProcessor =
                        (CountEntityPreProcessor<C3Decorator>)countEntityPreProcessorMap.get(decorator.getClass().getName());
                if(countEntityPreProcessor != null){
                    countPreProcessors.add(Triple.of(decoratedProperty.getJsonPath(),
                                                     decorator,
                                                     countEntityPreProcessor));
                }

                DeleteEntityPreProcessor<C3Decorator> deleteEntityPreProcessor =
                        (DeleteEntityPreProcessor<C3Decorator>)deleteEntityPreProcessorMap.get(decorator.getClass().getName());
                if(deleteEntityPreProcessor != null){
                    deletePreProcessors.add(Triple.of(decoratedProperty.getJsonPath(),
                                                      decorator,
                                                      deleteEntityPreProcessor));
                }

                FindAllPreProcessor<C3Decorator> findAllPreProcessor =
                        (FindAllPreProcessor<C3Decorator>)findAllPreProcessorMap.get(decorator.getClass().getName());
                if(findAllPreProcessor != null){
                    findAllPreProcessors.add(Triple.of(decoratedProperty.getJsonPath(),
                                                       decorator,
                                                       findAllPreProcessor));
                }

                FindByIdPreProcessor<C3Decorator> findByIdPreProcessor =
                        (FindByIdPreProcessor<C3Decorator>)findByIdPreProcessorMap.get(decorator.getClass().getName());
                if(findByIdPreProcessor != null){
                    findByIdPreProcessors.add(Triple.of(decoratedProperty.getJsonPath(),
                                                         decorator,
                                                         findByIdPreProcessor));
                }

                SearchPreProcessor<C3Decorator> searchPreProcessor =
                        (SearchPreProcessor<C3Decorator>)searchPreProcessorMap.get(decorator.getClass().getName());
                if(searchPreProcessor != null){
                    searchPreProcessors.add(Triple.of(decoratedProperty.getJsonPath(),
                                                       decorator,
                                                       searchPreProcessor));
                }
            }
        }

        return CompletableFuture.completedFuture(new DefaultEntityService(structure,
                                                                          esAsyncClient,
                                                                          crudServiceTemplate,
                                                                          new DelegatingUpsertPreProcessor(objectMapper,
                                                                                                           structure,
                                                                                                           fieldPreProcessors),
                                                                          new DelegatingReadPreProcessor(countPreProcessors,
                                                                                                         deletePreProcessors,
                                                                                                         findAllPreProcessors,
                                                                                                         findByIdPreProcessors,
                                                                                                         searchPreProcessors)));
    }

}
