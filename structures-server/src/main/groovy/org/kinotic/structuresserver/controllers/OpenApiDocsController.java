package org.kinotic.structuresserver.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import org.kinotic.structuresserver.openapi.OpenApiService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Created by Navíd Mitchell 🤪 on 3/18/23.
 */
@RestController
public class OpenApiDocsController {

    private final OpenApiService openApiService;

    public OpenApiDocsController(OpenApiService openApiService) {
        this.openApiService = openApiService;
    }




    // TODO: eventually this will be namespaced
    @GetMapping(value = "/api-docs/openapi.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> getOpenApiDocs() {
        return Mono.defer(() -> {
                       try {
                           //This wacky stuff is needed since we do not want nulls in our output
                           ObjectMapper mapper = new ObjectMapper();
                           mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                           String json = mapper.writeValueAsString(openApiService.getOpenApiSpec());
                           return Mono.just(json);
                       } catch (JsonProcessingException e) {
                           return Mono.error(e);
                       }
                   })
                   .subscribeOn(Schedulers.boundedElastic());
    }

    public class Test {
        String name;
        String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }


}
