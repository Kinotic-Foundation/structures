package org.kinotic.structures.internal.endpoints;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import io.vertx.core.Vertx;
import io.vertx.ext.healthchecks.HealthChecks;
import io.vertx.ext.healthchecks.Status;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.api.config.StructuresProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * This class is responsible for initializing the structures endpoints.
 * Created by Navíd Mitchell 🤪 on 5/30/23.
 */
@Component
@RequiredArgsConstructor
public class StructuresEndpointInitializer {

    private static final Logger log = LoggerFactory.getLogger(StructuresEndpointInitializer.class);

    private final StructuresProperties properties;
    private final Vertx vertx;
    private final OpenApiVerticle openApiVerticle;
    private final GraphQLVerticle graphQLVerticle;
    private final WebServerVerticle webServerVerticle;
    private final HealthChecks healthChecks;
    private final ElasticsearchAsyncClient esAsyncClient;

    private boolean lastEsStatus = true;
    private Throwable lastEsError = null;


    @PostConstruct
    public void init(){
        vertx.deployVerticle(openApiVerticle);
        vertx.deployVerticle(graphQLVerticle);
        vertx.deployVerticle(webServerVerticle);

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
}
