package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.hooks.DecoratorLogic;
import org.kinotic.structures.internal.api.hooks.DelegatingReadPreProcessor;
import org.kinotic.structures.internal.api.hooks.DelegatingUpsertPreProcessor;
import org.kinotic.structures.internal.api.hooks.UpsertFieldPreProcessor;
import org.kinotic.structures.internal.api.services.EntityService;
import org.kinotic.structures.internal.api.services.EntityServiceFactory;
import org.kinotic.structures.internal.idl.converters.common.DecoratedProperty;
import org.kinotic.structures.internal.utils.StructuresUtil;
import org.springframework.stereotype.Component;

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

    private final CrudServiceTemplate crudServiceTemplate;
    private final ElasticsearchAsyncClient esAsyncClient;
    private final ObjectMapper objectMapper;
    private final StructuresProperties structuresProperties;
    private final Map<String, UpsertFieldPreProcessor<?, ?, ?>> upsertFieldPreProcessors;

    public DefaultEntityServiceFactory(StructuresProperties structuresProperties,
                                       ObjectMapper objectMapper,
                                       ElasticsearchAsyncClient esAsyncClient,
                                       CrudServiceTemplate crudServiceTemplate,
                                       List<UpsertFieldPreProcessor<?, ?, ?>> upsertFieldPreProcessors) {

        this.structuresProperties = structuresProperties;
        this.objectMapper = objectMapper;
        this.esAsyncClient = esAsyncClient;
        this.crudServiceTemplate = crudServiceTemplate;

        this.upsertFieldPreProcessors = StructuresUtil.listToMap(upsertFieldPreProcessors,
                                                                 p -> p.implementsDecorator().getName());
    }

    @SuppressWarnings("unchecked")
    @Override
    public CompletableFuture<EntityService> createEntityService(Structure structure) {

        if(structure == null){
            return CompletableFuture.failedFuture(new IllegalArgumentException("Structure must not be null"));
        } else if (!structure.isPublished()) {
            return CompletableFuture.failedFuture(new IllegalArgumentException("Structure must be published"));
        }

        // Map of jsonPath to DecoratorLogic
        Map<String, DecoratorLogic> fieldPreProcessors = new LinkedHashMap<>();

        for(DecoratedProperty decoratedProperty : structure.getDecoratedProperties()){

            for(C3Decorator decorator : decoratedProperty.getDecorators()){

                UpsertFieldPreProcessor<C3Decorator, Object, Object> processor =
                        (UpsertFieldPreProcessor<C3Decorator, Object, Object>)upsertFieldPreProcessors.get(decorator.getClass().getName());
                if(processor != null){
                    // FIXME: without validation the last implementation will win, should we allow multiple per field?
                    fieldPreProcessors.put(decoratedProperty.getJsonPath(), new DecoratorLogic(decorator, processor));
                }
            }
        }

        return CompletableFuture.completedFuture(new DefaultEntityService(structure,
                                                                          structuresProperties,
                                                                          objectMapper,
                                                                          esAsyncClient,
                                                                          crudServiceTemplate,
                                                                          new DelegatingUpsertPreProcessor(structuresProperties,
                                                                                                           objectMapper,
                                                                                                           structure,
                                                                                                           fieldPreProcessors),
                                                                          new DelegatingReadPreProcessor(structuresProperties)));
    }

}
