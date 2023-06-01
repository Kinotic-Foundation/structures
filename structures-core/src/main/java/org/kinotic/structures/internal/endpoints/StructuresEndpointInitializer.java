package org.kinotic.structures.internal.endpoints;

import io.vertx.core.Vertx;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * This class is responsible for initializing the structures endpoints.
 * Created by Navíd Mitchell 🤪 on 5/30/23.
 */
@Component
public class StructuresEndpointInitializer {

    private final Vertx vertx;

    private final OpenApiVerticle openApiVerticle;

    public StructuresEndpointInitializer(Vertx vertx, OpenApiVerticle openApiVerticle) {
        this.vertx = vertx;
        this.openApiVerticle = openApiVerticle;
    }

    @PostConstruct
    public void init(){
        vertx.deployVerticle(openApiVerticle);
    }
}
