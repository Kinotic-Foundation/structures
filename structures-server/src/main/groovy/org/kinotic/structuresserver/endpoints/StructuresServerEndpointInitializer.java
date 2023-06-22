package org.kinotic.structuresserver.endpoints;

import io.vertx.core.Vertx;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Navíd Mitchell 🤪on 6/8/23.
 */
@Component
public class StructuresServerEndpointInitializer {

    private final Vertx vertx;
    private final UiServerVerticle uiServerVerticle;

    public StructuresServerEndpointInitializer(Vertx vertx,
                                               UiServerVerticle uiServerVerticle) {
        this.vertx = vertx;
        this.uiServerVerticle = uiServerVerticle;
    }

    @PostConstruct
    public void init(){
        vertx.deployVerticle(uiServerVerticle);
    }

}
