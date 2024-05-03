package org.kinotic.structures.internal.endpoints;

import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.internal.utils.VertxWebUtil;

import java.util.function.BiFunction;

/**
 * Created by Navíd Mitchell 🤪on 6/22/23.
 */
@RequiredArgsConstructor
class NoValueHandler implements BiFunction<Void, Throwable, Void>{

    private final RoutingContext context;

    @Override
    public Void apply(Void aVoid, Throwable throwable) {
        if(throwable != null){
            VertxWebUtil.writeException(context.response(), throwable);
        }else {
            context.response().setStatusCode(200);
            context.response().end();
        }
        return null;
    }
}
