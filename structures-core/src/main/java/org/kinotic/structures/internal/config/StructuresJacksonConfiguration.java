package org.kinotic.structures.internal.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import org.kinotic.structures.api.decorators.runtime.C3DecoratorInstance;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 5/9/23.
 */
@Configuration
public class StructuresJacksonConfiguration {

    private final ObjectMapper objectMapper;

    private final List<C3DecoratorInstance<?>> decoratorInstances;

    public StructuresJacksonConfiguration(ObjectMapper objectMapper,
                                          List<C3DecoratorInstance<?>> decoratorInstances) {
        this.objectMapper = objectMapper;
        this.decoratorInstances = decoratorInstances;
    }

    @PostConstruct
    public void configureObjectMapper() {
        // Register all C3Decorator's with Jackson
        NamedType[] namedTypes = new NamedType[decoratorInstances.size()];
        for (int i = 0; i < decoratorInstances.size(); i++) {
            C3DecoratorInstance<?> instance = decoratorInstances.get(i);
            namedTypes[i] = new NamedType(instance.implementsDecorator(), instance.decoratorTypeName());
        }

        objectMapper.getSubtypeResolver()
                    .registerSubtypes(namedTypes);
    }

}
