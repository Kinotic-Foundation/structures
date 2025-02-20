package org.kinotic.structures.internal.endpoints;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.ext.healthchecks.HealthChecks;
import io.vertx.ext.healthchecks.Status;
import lombok.RequiredArgsConstructor;
import org.kinotic.continuum.api.config.ContinuumProperties;
import org.kinotic.structures.api.config.StructuresProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * This class is responsible for initializing the structures endpoints.
 * Created by Navíd Mitchell 🤪 on 5/30/23.
 */
@Component
@RequiredArgsConstructor
public class StructuresEndpointInitializer {

    private static final Logger log = LoggerFactory.getLogger(StructuresEndpointInitializer.class);
    private final ContinuumProperties continuumProperties;
    private final ElasticsearchAsyncClient esAsyncClient;
    private final HealthChecks healthChecks;
    private final StructuresProperties properties;
    private final StructuresVerticleFactory verticleFactory;
    private final Vertx vertx;
    private Throwable lastEsError = null;
    private boolean lastEsStatus = true;

    @PostConstruct
    public void init(){
        int numToDeploy = continuumProperties.getMaxNumberOfCoresToUse();
        log.info("{} Cores will be used for Structures Endpoints", numToDeploy);
        DeploymentOptions options = new DeploymentOptions().setInstances(numToDeploy);

        vertx.deployVerticle(verticleFactory::createOpenApiVerticle, options);

        vertx.deployVerticle(verticleFactory::createGqlVerticle, options);

        vertx.deployVerticle(verticleFactory::createWebServerVerticle, new DeploymentOptions()); // only 1 web server verticle

        healthChecks.register("elasticsearch", future -> {
            if(lastEsStatus){
                future.complete(Status.OK());
            }else{
                future.fail("Elasticsearch cluster is not healthy." + ( lastEsError != null ? " Exception: " + lastEsError.getMessage() : ""));
            }
        });

        vertx.setPeriodic(properties.getElasticHealthCheckInterval().toMillis(),
                          event -> esAsyncClient
                                  .cluster()
                                  .health(builder -> builder.index("namespace")
                                                            .index("structure"))
                                  .whenComplete((health, throwable) -> {
                                      if(throwable != null){
                                          log.error("Elasticsearch cluster health check failed", throwable);
                                          lastEsStatus = false;
                                          lastEsError = throwable;
                                      }else{
                                          log.debug("Elasticsearch cluster health check succeeded");
                                          lastEsStatus = true;
                                          lastEsError = null;
                                      }
                                  }));

    }

    @EventListener
    public void onApplicationReadyEvent(ApplicationReadyEvent event) {
        log.info("Rest API listening on port {}", properties.getOpenApiPort());
        log.info("OpenApi Json available at http://localhost:{}/api-docs/[STRUCTURE NAMESPACE]/openapi.json",
                 properties.getOpenApiPort());
        log.info("GraphQL listening on port {}", properties.getGraphqlPort());
        log.info("GraphQL available at http://localhost:{}{}[STRUCTURE NAMESPACE]/",
                 properties.getGraphqlPort(),
                 properties.getGraphqlPath());
        if(properties.isEnableStaticFileServer()) {
            log.info("Web Server listening on port {}", properties.getWebServerPort());
            log.info("Web Server available at http://localhost:{}/", properties.getWebServerPort());
        }
        log.info("Health checks available at http://localhost:{}{}",
                 properties.getWebServerPort(),
                 properties.getHealthCheckPath());
    }

}
