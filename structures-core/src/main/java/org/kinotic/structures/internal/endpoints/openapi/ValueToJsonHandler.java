package org.kinotic.structures.internal.endpoints.openapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.kinotic.structures.internal.utils.VertxWebUtil;

import java.util.function.BiFunction;

/**
 * Handles any object that can be serialized directly to JSON
 * Created by Navíd Mitchell 🤪 on 6/1/23.
 */
@RequiredArgsConstructor
class ValueToJsonHandler<T> implements BiFunction<T, Throwable, Void> {

    private final RoutingContext context;
    private final ObjectMapper objectMapper;

    @Override
    public Void apply(T value, Throwable throwable) {
        if (throwable == null) {
            Validate.notNull(context, "context must not be null");
            try {
                context.response().putHeader("Content-Type", "application/json");
                context.response().setStatusCode(200);
                byte[] data = objectMapper.writeValueAsBytes(value);
                context.response().end(Buffer.buffer(data));
            } catch (JsonProcessingException e) {
                VertxWebUtil.writeException(context, e);
            }
        } else {
            VertxWebUtil.writeException(context, throwable);
        }
        return null;
    }
}
