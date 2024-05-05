package org.kinotic.structures.internal.endpoints;

import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.internal.utils.VertxWebUtil;

import java.util.function.BiFunction;

/**
 * Created by Navíd Mitchell 🤪 on 6/1/23.
 */
@RequiredArgsConstructor
class SingleEntityHandler implements BiFunction<RawJson, Throwable, Void> {

    private final RoutingContext context;

    @Override
    public Void apply(RawJson rawJson, Throwable throwable) {
        if (throwable == null) {
            context.response().putHeader("Content-Type", "application/json");
            context.response().setStatusCode(200);
            context.response().end(Buffer.buffer(rawJson.data()));
        } else {
            VertxWebUtil.writeException(context.response(), throwable);
        }
        return null;
    }
}
