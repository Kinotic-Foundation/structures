package org.kinotic.structuresserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * Created by Navíd Mitchell 🤪 on 3/18/23.
 */
@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                    .allowedOrigins("*")
                    .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/public/");
    }

    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions.route(RequestPredicates.GET("/"), this::indexHandler);
    }

    private Mono<ServerResponse> indexHandler(ServerRequest request) {
        return ServerResponse.permanentRedirect(URI.create("/index.html")).build();
    }

}
