/*
 *
 * Copyright 2008-2021 Kinotic and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kinotic.structures.internal;

import org.kinotic.continuum.internal.utils.ClassPathScanningMetadataReaderProvider;
import org.kinotic.structures.api.annotations.StructuresPackages;
import org.kinotic.structures.api.services.ItemDao;
import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * Created by Navid Mitchell on 2/12/21.
 */
public class DaoRegistrationBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(DaoRegistrationBeanDefinitionRegistryPostProcessor.class);

    private ApplicationContext applicationContext;

    /**
     * Creates a {@link DaoRegistrationBeanDefinitionRegistryPostProcessor} for all Interfaces on the classpath that extend {@link ItemDao}
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // scan classpath for all Interfaces extending ItemDao
        ClassPathScanningMetadataReaderProvider scanner
                = new ClassPathScanningMetadataReaderProvider(applicationContext.getEnvironment());
        scanner.setResourceLoader(applicationContext);
        scanner.addIncludeFilter(new AssignableTypeFilter(ItemDao.class));

        List<String> packages = StructuresPackages.get(this.applicationContext);

        Set<MetadataReader> readers = new HashSet<>();

        for(String pack : packages){
            readers.addAll(scanner.findCandidateComponents(pack));
        }

        for(MetadataReader reader: readers){
            String serviceClassName = reader.getClassMetadata().getClassName();

            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ItemDaoBeanFactory.class);
            builder.addConstructorArgValue(serviceClassName);
            log.debug("Registering bean factory for ItemDao: "+serviceClassName);
            registry.registerBeanDefinition(ClassUtils.getShortClassName(serviceClassName)+"Factory", builder.getBeanDefinition());
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
