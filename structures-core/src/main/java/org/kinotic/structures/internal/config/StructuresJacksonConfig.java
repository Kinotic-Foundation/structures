package org.kinotic.structures.internal.config;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.lang3.tuple.Pair;
import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.continuum.internal.utils.MetaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.classreading.MetadataReader;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * Created by Navíd Mitchell 🤪 on 5/9/23.
 */
@Configuration
public class StructuresJacksonConfig {

    private static final Logger log = LoggerFactory.getLogger(StructuresJacksonConfig.class);

    @Bean
    public SimpleModule structuresJacksonModule(ApplicationContext applicationContext){
        SimpleModule ret = new SimpleModule("StructuresModule", Version.unknownVersion());

        Set<MetadataReader> decoratorMetas = MetaUtil.findClassesWithSuperClass(applicationContext,
                                                                                List.of("org.kinotic.structures.api.decorators"),
                                                                                C3Decorator.class.getName());
        // Register all C3Decorator's with Jackson
        for(MetadataReader decoratorMeta : decoratorMetas){
            try {
                Pair<Class<?>, String> decoratorInfo = getDecoratorInfo(decoratorMeta);

                ret.registerSubtypes(new NamedType(decoratorInfo.getLeft(), decoratorInfo.getRight()));

            } catch (NoSuchFieldException e) {
                log.warn(decoratorMeta.getClassMetadata().getClassName() + " Could not be mapped. A public static final field named 'type' must exist on the class.");
            }
        }

        // register internal serializer deserializers
//        ret.addDeserializer(Pageable.class, new PageableDeserializer());
//        ret.addSerializer(Page.class, new PageSerializer());

        return ret;
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
