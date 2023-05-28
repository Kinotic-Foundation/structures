package org.kinotic.structures.internal.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import org.apache.commons.lang3.tuple.Pair;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.continuum.internal.utils.MetaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.classreading.MetadataReader;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 5/9/23.
 */
@Configuration
public class StructuresJacksonConfiguration {

    private static final Logger log = LoggerFactory.getLogger(StructuresJacksonConfiguration.class);

    private final ObjectMapper objectMapper;

    private final ApplicationContext applicationContext;

    public StructuresJacksonConfiguration(ObjectMapper objectMapper,
                                          ApplicationContext applicationContext) {
        this.objectMapper = objectMapper;
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void configureObjectMapper() {
        Set<MetadataReader> decoratorMetas = MetaUtil.findClassesWithSuperClass(applicationContext,
                                                                                List.of("org.kinotic.structures.api.decorators"),
                                                                                C3Decorator.class.getName());
        // Register all C3Decorator's with Jackson
        List<NamedType> namedTypes = new ArrayList<>();

        for(MetadataReader decoratorMeta : decoratorMetas){
            try {
                Pair<Class<?>, String> decoratorInfo = getDecoratorInfo(decoratorMeta);
                namedTypes.add(new NamedType(decoratorInfo.getLeft(), decoratorInfo.getRight()));
            } catch (NoSuchFieldException e) {
                log.warn(decoratorMeta.getClassMetadata().getClassName() + " Could not be mapped. A public static final field named 'type' must exist on the class.");
            }
        }

        objectMapper.getSubtypeResolver()
                    .registerSubtypes(namedTypes.toArray(new NamedType[0]));
    }

    private Pair<Class<?>, String> getDecoratorInfo(MetadataReader metadataReader) throws NoSuchFieldException{
        try {
            Class<?> decoratorClass = Class.forName(metadataReader.getClassMetadata().getClassName());
            Field typeField = decoratorClass.getDeclaredField("type");
            String type = (String) typeField.get(null);
            return Pair.of(decoratorClass, type);
        } catch (IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
