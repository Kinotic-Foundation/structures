package org.kinotic.structures.internal.config;

import org.kinotic.structures.internal.graphql.StructuresGraphQlHttpHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.ReactiveSecurityDataFetcherExceptionResolver;
import org.springframework.graphql.server.WebGraphQlHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

/**
 * Created by Navíd Mitchell 🤪 on 4/17/23.
 */
@Configuration
public class GraphQlConfig {

    private static final RequestPredicate SUPPORTS_MEDIATYPES = accept(MediaType.APPLICATION_GRAPHQL,
                                                                       MediaType.APPLICATION_JSON)
            .and(contentType(MediaType.APPLICATION_GRAPHQL, MediaType.APPLICATION_JSON));

    @Bean
    public StructuresGraphQlHttpHandler graphQlHttpHandler(WebGraphQlHandler graphQlHandler) {
        return new StructuresGraphQlHttpHandler(graphQlHandler);
    }

    @Bean
    public RouterFunction<ServerResponse> graphQlRouterFunction(StructuresGraphQlHttpHandler handler) {
        RouterFunctions.Builder builder = RouterFunctions.route();
        builder = builder.GET("/graphql/{namespace}", this::onlyAllowPost);
        builder = builder.POST("/graphql/{namespace}", SUPPORTS_MEDIATYPES, handler::handleRequest);
        return builder.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public ReactiveSecurityDataFetcherExceptionResolver reactiveSecurityDataFetcherExceptionResolver() {
        return new ReactiveSecurityDataFetcherExceptionResolver();
    }

    private Mono<ServerResponse> onlyAllowPost(ServerRequest request) {
        return ServerResponse.status(HttpStatus.METHOD_NOT_ALLOWED).headers(this::onlyAllowPost).build();
    }

    private void onlyAllowPost(HttpHeaders headers) {
        headers.setAllow(Collections.singleton(HttpMethod.POST));
    }

}
