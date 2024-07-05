package org.kinotic.structures.internal.endpoints;

import io.vertx.ext.healthchecks.HealthChecks;
import org.kinotic.continuum.api.security.SecurityService;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.internal.endpoints.graphql.GqlExecutionService;
import org.kinotic.structures.internal.endpoints.graphql.GqlVerticle;
import org.kinotic.structures.internal.endpoints.openapi.OpenApiVerticle;
import org.kinotic.structures.internal.endpoints.openapi.OpenApiVertxRouterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Creates all needed verticles at runtime so multiple instances can be used
 * Created By navidmitchell 🤯on 3/6/24
 */
@Component
public class StructuresVerticleFactory {

    // Common Deps
    private final StructuresProperties properties;
    private final SecurityService securityService;

    // Open Api Deps
    private final OpenApiVertxRouterFactory openApiVertxRouterFactory;

    // Gql Deps
    private final GqlExecutionService gqlExecutionService;

    // Web Server Deps
    private final HealthChecks healthChecks;


    public StructuresVerticleFactory(OpenApiVertxRouterFactory openApiVertxRouterFactory,
                                     StructuresProperties properties,
                                     GqlExecutionService gqlExecutionService,
                                     HealthChecks healthChecks,
                                     @Autowired(required = false) SecurityService securityService) {
        this.openApiVertxRouterFactory = openApiVertxRouterFactory;
        this.properties = properties;
        this.securityService = securityService;
        this.gqlExecutionService = gqlExecutionService;
        this.healthChecks = healthChecks;
    }

    public GqlVerticle createGqlVerticle(){
        return new GqlVerticle(gqlExecutionService, properties, securityService);
    }

    public OpenApiVerticle createOpenApiVerticle(){
        return new OpenApiVerticle(properties, openApiVertxRouterFactory.createRouter());
    }

    public WebServerVerticle createWebServerVerticle(){
        return new WebServerVerticle(healthChecks, properties);
    }

}
