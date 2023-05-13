package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.decorators.runtime.UpsertFieldPreProcessor;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.internal.api.decorators.BasicUpsertEntityPreProcessor;
import org.kinotic.structures.internal.api.decorators.DecoratorLogic;
import org.kinotic.structures.internal.api.services.EntityService;
import org.kinotic.structures.internal.api.services.EntityServiceFactory;
import org.kinotic.structures.internal.api.services.DecoratedProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
    private final Map<Class<? extends C3Decorator>, UpsertFieldPreProcessor<C3Decorator, Object, Object>> upsertFieldPreProcessors;

    public DefaultEntityServiceFactory(ObjectMapper objectMapper,
                                       ElasticsearchAsyncClient esAsyncClient,
                                       CrudServiceTemplate crudServiceTemplate,
                                       List<UpsertFieldPreProcessor<?, ?, ?>> upsertFieldPreProcessors) {
        this.objectMapper = objectMapper;
        this.esAsyncClient = esAsyncClient;
        this.crudServiceTemplate = crudServiceTemplate;

        this.upsertFieldPreProcessors = new HashMap<>(upsertFieldPreProcessors.size());

        for(UpsertFieldPreProcessor<?, ?, ?> processor : upsertFieldPreProcessors){

            if(this.upsertFieldPreProcessors.containsKey(processor.implementsDecorator())){
                throw new IllegalStateException("Found multiple UpsertFieldPreProcessor for decorator: " + processor.implementsDecorator().getName());
            }
            //noinspection unchecked
            this.upsertFieldPreProcessors.put(processor.implementsDecorator(),
                                              (UpsertFieldPreProcessor<C3Decorator, Object, Object>) processor);
        }
    }

    @Override
    public CompletableFuture<EntityService> createEntityService(Structure structure) {

        // Map of jsonPath to DecoratorLogic
        Map<String, DecoratorLogic<C3Decorator, Object, Object,
                UpsertFieldPreProcessor<C3Decorator, Object, Object>>> fieldPreProcessors = new HashMap<>();

        for(DecoratedProperty decoratedProperty : structure.getDecoratedProperties()){

            for(C3Decorator decorator : decoratedProperty.getDecorators()){

                UpsertFieldPreProcessor<C3Decorator, Object, Object> processor = upsertFieldPreProcessors.get(decorator.getClass());
                if(processor != null){
                    // FIXME: without validation the last implementation will win, should we allow multiple per field?
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
