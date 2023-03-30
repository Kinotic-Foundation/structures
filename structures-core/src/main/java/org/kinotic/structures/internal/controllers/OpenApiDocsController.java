package org.kinotic.structures.internal.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kinotic.structures.internal.api.services.OpenApiService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;

/**
 * Created by Navíd Mitchell 🤪 on 3/18/23.
 */
@RestController
public class OpenApiDocsController {

    private final OpenApiService openApiService;

    public OpenApiDocsController(OpenApiService openApiService) {
        this.openApiService = openApiService;
    }


    @GetMapping(value = "/api-docs/{namespace}/openapi.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> getOpenApiDocs(@PathVariable String namespace) {
        return Mono.defer(() -> {
               try {
                   //This wacky stuff is needed since we do not want nulls in our output
                   ObjectMapper mapper = new ObjectMapper();
                   mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                   String json = mapper.writeValueAsString(openApiService.getOpenApiSpec(namespace));
                   return Mono.just(json);
               } catch (IOException e) {
                   return Mono.error(e);
               }
        })
        .subscribeOn(Schedulers.boundedElastic());
    }

}
