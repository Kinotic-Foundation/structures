package org.kinotic.structures.internal.api.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.NamedQueriesService;
import org.kinotic.structures.api.services.security.AuthorizationServiceFactory;
import org.kinotic.structures.internal.api.hooks.DecoratorLogic;
import org.kinotic.structures.internal.api.hooks.DelegatingUpsertPreProcessor;
import org.kinotic.structures.internal.api.hooks.ReadPreProcessor;
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

    private final AuthorizationServiceFactory authServiceFactory;
    private final CrudServiceTemplate crudServiceTemplate;
    private final ElasticsearchAsyncClient esAsyncClient;
    private final NamedQueriesService namedQueriesService;
    private final ObjectMapper objectMapper;
    private final ReadPreProcessor readPreProcessor;
    private final StructuresProperties structuresProperties;
    private final Map<String, UpsertFieldPreProcessor<?, ?, ?>> upsertFieldPreProcessors;


    public DefaultEntityServiceFactory(AuthorizationServiceFactory authServiceFactory,
                                       CrudServiceTemplate crudServiceTemplate,
                                       ElasticsearchAsyncClient esAsyncClient,
                                       NamedQueriesService namedQueriesService,
                                       ObjectMapper objectMapper,
                                       ReadPreProcessor readPreProcessor,
                                       StructuresProperties structuresProperties,
                                       List<UpsertFieldPreProcessor<?, ?, ?>> upsertFieldPreProcessors) {
        this.authServiceFactory = authServiceFactory;
        this.crudServiceTemplate = crudServiceTemplate;
        this.esAsyncClient = esAsyncClient;
        this.namedQueriesService = namedQueriesService;
        this.objectMapper = objectMapper;
        this.readPreProcessor = readPreProcessor;
        this.structuresProperties = structuresProperties;

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
                    fieldPreProcessors.put(decoratedProperty.getJsonPath(), new DecoratorLogic(decorator, processor));
                }
            }
        }

        return authServiceFactory.createStructureAuthorizationService(structure)
                                 .thenApply(authService -> new DefaultEntityService(
                                         authService,
                                         crudServiceTemplate,
                                         new DelegatingUpsertPreProcessor(structuresProperties,
                                                                          objectMapper,
                                                                          structure,
                                                                          fieldPreProcessors),
                                         esAsyncClient,
                                         namedQueriesService,
                                         objectMapper,
                                         readPreProcessor,
                                         structure,
                                         structuresProperties));
    }

}
