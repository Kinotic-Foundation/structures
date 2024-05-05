package org.kinotic.structures.internal.endpoints;

import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import org.kinotic.structures.internal.utils.VertxWebUtil;

import java.util.function.BiFunction;

/**
 * Created by Navíd Mitchell 🤪 on 5/2/24.
 */
@RequiredArgsConstructor
public class CountHandler implements BiFunction<Long, Throwable, Void> {

    private final RoutingContext context;

    @Override
    public Void apply(Long value, Throwable throwable) {
        if (throwable == null) {
            context.response().putHeader("Content-Type", "application/json");
            context.response().setStatusCode(200);
            context.response().end(Buffer.buffer("{ \"count\": " + value.toString() + '}'));
        } else {
            VertxWebUtil.writeException(context.response(), throwable);
        }
        return null;
    }
}
