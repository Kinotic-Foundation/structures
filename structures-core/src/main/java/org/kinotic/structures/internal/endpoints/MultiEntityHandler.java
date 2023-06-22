package org.kinotic.structures.internal.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.internal.utils.VertxWebUtil;
import org.springframework.data.domain.Page;

import java.util.function.BiFunction;

/**
 * Created by Navíd Mitchell 🤪 on 6/1/23.
 */
class MultiEntityHandler implements BiFunction<Page<RawJson>, Throwable, Void> {

    private final RoutingContext context;
    private final ObjectMapper objectMapper;

    public MultiEntityHandler(RoutingContext context,
                              ObjectMapper objectMapper) {
        this.context = context;
        this.objectMapper = objectMapper;
    }

    @Override
    public Void apply(Page<RawJson> rawJsonPage, Throwable throwable) {
        if (throwable == null) {
            try {
                context.response().putHeader("Content-Type", "application/json");
                context.response().setStatusCode(200);
                byte[] data = objectMapper.writeValueAsBytes(rawJsonPage);
                context.response().end(Buffer.buffer(data));
            } catch (JsonProcessingException e) {
                VertxWebUtil.writeException(context.response(), e);
            }
        } else {
            VertxWebUtil.writeException(context.response(), throwable);
        }
        return null;
    }
}
