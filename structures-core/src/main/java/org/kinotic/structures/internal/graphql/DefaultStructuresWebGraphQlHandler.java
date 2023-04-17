package org.kinotic.structures.internal.graphql;

import org.kinotic.structures.internal.api.services.ExecutionGraphQlServiceProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.graphql.execution.ThreadLocalAccessor;
import org.springframework.graphql.server.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

/**
 * Created by Navíd Mitchell 🤪 on 4/16/23.
 */
@Component
public class DefaultStructuresWebGraphQlHandler implements WebGraphQlHandler {

    private final ExecutionGraphQlServiceProvider executionGraphQlServiceProvider;
    private final WebGraphQlInterceptor.Chain executionChain;
    private WebSocketGraphQlInterceptor webSocketInterceptor;

    public DefaultStructuresWebGraphQlHandler(ExecutionGraphQlServiceProvider executionGraphQlServiceProvider,
                                              ObjectProvider<WebGraphQlInterceptor> interceptorsProvider) {

        this.executionGraphQlServiceProvider = executionGraphQlServiceProvider;

        WebGraphQlInterceptor.Chain endOfChain = request -> {
            String namespace = (String) request.getExtensions().get("__structuresNamespace");
            return this.executionGraphQlServiceProvider.getService(namespace)
                                                       .flatMap(service -> service.execute(request))
                                                       .map(WebGraphQlResponse::new);
        };

        executionChain = interceptorsProvider.orderedStream()
                                             .peek(interceptor -> {
                                                 if (interceptor instanceof WebSocketGraphQlInterceptor) {
                                                     Assert.isNull(this.webSocketInterceptor, "There can be at most 1 WebSocketInterceptor");
                                                     this.webSocketInterceptor = (WebSocketGraphQlInterceptor) interceptor;
                                                 }
                                             })
                                             .reduce(WebGraphQlInterceptor::andThen)
                                             .map(interceptor -> interceptor.apply(endOfChain))
                                             .orElse(endOfChain);
    }

    @Override
    public WebSocketGraphQlInterceptor getWebSocketInterceptor() {
        return (webSocketInterceptor != null ?
                webSocketInterceptor : new WebSocketGraphQlInterceptor() {});
    }

    @Nullable
    @Override
    public ThreadLocalAccessor getThreadLocalAccessor() {
        return null;
    }

    @Override
    public Mono<WebGraphQlResponse> handleRequest(WebGraphQlRequest request) {
        return executionChain.next(request);
    }
}
