package org.kinotic.structures.internal.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.services.EntitiesService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Navíd Mitchell 🤪 on 6/1/23.
 */
@Component
public class DataInitializer {

    private final TestDataService testDataService;
    private final ObjectMapper objectMapper;
    private final EntitiesService entitiesService;
    private final StructuresProperties properties;

    public DataInitializer(TestDataService testDataService,
                           ObjectMapper objectMapper,
                           EntitiesService entitiesService,
                           StructuresProperties properties) {
        this.testDataService = testDataService;
        this.objectMapper = objectMapper;
        this.entitiesService = entitiesService;
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        if (properties.isInitializeWithSampleData()) {
            testDataService.createPersonStructureIfNotExists()
                           .thenCompose(structureBooleanPair -> {
                               if(structureBooleanPair.getRight()) {
                                   return testDataService.createTestPeople(500)
                                           .thenCompose(people -> {
                                               List<CompletableFuture<RawJson>> completableFutures = new ArrayList<>();
                                               for(Person person : people){
                                                   byte[] jsonData;
                                                   try {
                                                       jsonData = objectMapper.writeValueAsBytes(person);
                                                   } catch (JsonProcessingException e) {
                                                       return CompletableFuture.failedFuture(e);
                                                   }
                                                   completableFutures.add(entitiesService.save(structureBooleanPair.getLeft()
                                                                                                                   .getId(),
                                                                                               RawJson.from(jsonData),
                                                                                               new DummyEntityContext("kinotic",
                                                                                                                      "structures")));
                                               }
                                               return CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));
                                           });
                               } else {
                                   return null;
                               }
                           }).thenApply(aVoid -> null);
        }
    }
}
