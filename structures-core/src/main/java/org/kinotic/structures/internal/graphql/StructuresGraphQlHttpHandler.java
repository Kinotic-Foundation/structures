package org.kinotic.structures.internal.graphql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.graphql.server.WebGraphQlHandler;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Navíd Mitchell 🤪 on 4/17/23.
 */
public class StructuresGraphQlHttpHandler {

    private static final Logger log = LoggerFactory.getLogger(StructuresGraphQlHttpHandler.class);

    private static final ParameterizedTypeReference<Map<String, Object>> MAP_PARAMETERIZED_TYPE_REF =
            new ParameterizedTypeReference<>() {};

    private static final List<MediaType> SUPPORTED_MEDIA_TYPES =
            Arrays.asList(MediaType.APPLICATION_GRAPHQL, MediaType.APPLICATION_JSON);

    private final WebGraphQlHandler graphQlHandler;

    /**
     * Create a new instance.
     * @param graphQlHandler common handler for GraphQL over HTTP requests
     */
    public StructuresGraphQlHttpHandler(WebGraphQlHandler graphQlHandler) {
        Assert.notNull(graphQlHandler, "WebGraphQlHandler is required");
        this.graphQlHandler = graphQlHandler;
    }

    /**
     * Handle GraphQL requests over HTTP.
     * @param serverRequest the incoming HTTP request
     * @return the HTTP response
     */
    public Mono<ServerResponse> handleRequest(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(MAP_PARAMETERIZED_TYPE_REF)
                            .flatMap(body -> {
                                if(!body.containsKey("extensions")){
                                    body.put("extensions", new HashMap<>());
                                }
                                WebGraphQlRequest graphQlRequest = new WebGraphQlRequest(
                                        serverRequest.uri(), serverRequest.headers().asHttpHeaders(), body,
                                        serverRequest.exchange().getRequest().getId(),
                                        serverRequest.exchange().getLocaleContext().getLocale());

                                graphQlRequest.getExtensions().put("__structuresNamespace", serverRequest.pathVariable("namespace"));

                                if (log.isTraceEnabled()) {
                                    log.trace("Executing: " + graphQlRequest);
                                }

                                return this.graphQlHandler.handleRequest(graphQlRequest);

                            })
                            .flatMap(response -> {
                                if (log.isTraceEnabled()) {
                                    log.trace("Execution complete");
                                }
                                ServerResponse.BodyBuilder builder = ServerResponse.ok();
                                builder.headers(headers -> headers.putAll(response.getResponseHeaders()));
                                builder.contentType(selectResponseMediaType(serverRequest));
                                return builder.bodyValue(response.toMap());
                            });
    }

    private static MediaType selectResponseMediaType(ServerRequest serverRequest) {
        for (MediaType accepted : serverRequest.headers().accept()) {
            if (SUPPORTED_MEDIA_TYPES.contains(accepted)) {
                return accepted;
            }
        }
        return MediaType.APPLICATION_JSON;
    }



}
