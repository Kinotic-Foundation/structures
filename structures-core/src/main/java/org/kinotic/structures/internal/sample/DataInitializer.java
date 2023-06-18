package org.kinotic.structures.internal.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kinotic.structures.api.config.StructuresProperties;
import org.kinotic.structures.api.domain.DefaultEntityContext;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.domain.Structure;
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
                                   return CompletableFuture.allOf(createPeople(structureBooleanPair.getLeft(),
                                                                               300,
                                                                               "kinotic",
                                                                               "structures"),
                                                                  createPeople(structureBooleanPair.getLeft(),
                                                                               250,
                                                                               "kinotic2",
                                                                               "structures2"));
                               } else {
                                   return null;
                               }
                           }).thenApply(aVoid -> null);
        }
    }

    private CompletableFuture<Void> createPeople(Structure structure,
                                                 int numberOfPeopleToCreate,
                                                 String tenantId,
                                                 String participantId){
        return testDataService.createRandomTestPeople(numberOfPeopleToCreate)
                              .thenCompose(people -> {
                                  List<CompletableFuture<RawJson>> completableFutures = new ArrayList<>();
                                  for(Person person : people){
                                      byte[] jsonData;
                                      try {
                                          jsonData = objectMapper.writeValueAsBytes(person);
                                      } catch (JsonProcessingException e) {
                                          return CompletableFuture.failedFuture(e);
                                      }
                                      completableFutures.add(entitiesService.save(structure.getId(),
                                                                                  RawJson.from(jsonData),
                                                                                  new DefaultEntityContext(new DummyParticipant(tenantId,
                                                                                                           participantId))));
                                  }
                                  return CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));
                              });
    }


}
