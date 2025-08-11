package org.kinotic.structuresserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;    
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.kinotic.structuresserver.config.ElasticsearchTestConfiguration;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom BeanPostProcessor for test environment that creates proxy beans
 * for problematic beans until TestContainers are ready.
 */
@Component
@Profile("test") 
public class TestBeanPostProcessor implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(TestBeanPostProcessor.class);

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        // log.info("TestBeanPostProcessor starting - ensuring TestContainers are ready...");
        
        // // Ensure containers are started and ready before proceeding
        // if (!ElasticsearchTestConfiguration.areContainersReady()) {
        //     log.info("Containers not ready yet, starting them synchronously...");
        //     try {
        //         // Start containers synchronously if they haven't been started
        //         if (ElasticsearchTestConfiguration.ELASTICSEARCH_CONTAINER == null || !ElasticsearchTestConfiguration.ELASTICSEARCH_CONTAINER.isRunning()) {
        //             log.info("Starting Elasticsearch container...");
        //             ElasticsearchTestConfiguration.startContainersSynchronously();
        //         } else {
        //             log.info("Containers already running, waiting for them to be ready...");
        //             ElasticsearchTestConfiguration.waitForContainersReady();
        //         }
        //     } catch (Exception e) {
        //         log.error("Failed to start TestContainers during Spring context initialization", e);
        //         throw new RuntimeException("Failed to start TestContainers", e);
        //     }
        // } else {
        //     log.info("Containers already ready, proceeding with Spring context initialization");
        // }
        

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        
    }
}
