package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.decorators.lifecycle.UpsertFieldPreProcessor;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.decorators.BasicUpsertEntityPreProcessor;
import org.kinotic.structures.internal.api.decorators.DecoratorLogic;
import org.kinotic.structures.internal.api.services.DecoratorInstanceService;
import org.kinotic.structures.internal.api.services.EntityService;
import org.kinotic.structures.internal.api.services.EntityServiceFactory;
import org.kinotic.structures.internal.idl.converters.elastic.DecoratedProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * This simplifies the creation of the EntityService, since a lot of dependencies are needed.
 * Created by Navíd Mitchell 🤪 on 5/10/23.
 */
@Component
public class DefaultEntityServiceFactory implements EntityServiceFactory {

    private final ObjectMapper objectMapper;
    private final DecoratorInstanceService decoratorInstanceService;
    private final ElasticsearchAsyncClient esAsyncClient;
    private final CrudServiceTemplate crudServiceTemplate;

    public DefaultEntityServiceFactory(ObjectMapper objectMapper,
                                       DecoratorInstanceService decoratorInstanceService,
                                       ElasticsearchAsyncClient esAsyncClient,
                                       CrudServiceTemplate crudServiceTemplate) {
        this.objectMapper = objectMapper;
        this.decoratorInstanceService = decoratorInstanceService;
        this.esAsyncClient = esAsyncClient;
        this.crudServiceTemplate = crudServiceTemplate;
    }

    @Override
    public CompletableFuture<EntityService> createEntityService(Structure structure) {

        // Map of jsonPath to DecoratorLogic
        Map<String, DecoratorLogic<C3Decorator, Object, Object,
                UpsertFieldPreProcessor<C3Decorator, Object, Object>>> fieldPreProcessors = new HashMap<>();

        for(DecoratedProperty decoratedProperty : structure.getDecoratedProperties()){
            for(C3Decorator decorator : decoratedProperty.getDecorators()){
                //noinspection unchecked
                UpsertFieldPreProcessor<C3Decorator, Object, Object> processor
                        = decoratorInstanceService.findUpsertFieldPreProcessor((Class<C3Decorator>) decorator.getClass());
                if(processor != null){
                    fieldPreProcessors.put(decoratedProperty.getJsonPath(), new DecoratorLogic<>(decorator, processor));
                }
            }
        }

        BasicUpsertEntityPreProcessor upsertEntityPreProcessor
                = new BasicUpsertEntityPreProcessor(objectMapper, structure, fieldPreProcessors);

        return CompletableFuture.completedFuture(new DefaultEntityService(structure,
                                                                          esAsyncClient,
                                                                          crudServiceTemplate,
                                                                          upsertEntityPreProcessor));
    }
}
