package org.kinotic.structures.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.sample.DummyEntityContext;
import org.kinotic.structures.internal.sample.Person;
import org.kinotic.structures.internal.sample.TestDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class TestHelper {

    @Autowired
    TestDataService testDataService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntitiesService entitiesService;


    public Mono<StructureAndPersonHolder> createPersonStructureAndEntities(int numberOfPeopleToCreate){
        return Mono.fromFuture(() -> testDataService.createPersonStructure("-" + System.currentTimeMillis())
                .thenCompose(structure ->
                     testDataService.createTestPeople(numberOfPeopleToCreate)
                                    .thenCompose(people -> {
                                        List<CompletableFuture<Person>> completableFutures = new ArrayList<>();
                                        for(Person person : people){
                                            byte[] jsonData;
                                            try {
                                                jsonData = objectMapper.writeValueAsBytes(person);
                                            } catch (JsonProcessingException e) {
                                                return CompletableFuture.failedFuture(e);
                                            }
                                            completableFutures.add(entitiesService.save(structure.getId(),
                                                                                        RawJson.from(jsonData),
                                                                                        new DummyEntityContext())
                                                                                  .thenCompose(saved -> {
                                                                                      try {
                                                                                          Person savedPerson = objectMapper.readValue(saved.data(),
                                                                                                                                      Person.class);
                                                                                          return CompletableFuture.completedFuture(savedPerson);
                                                                                      } catch (IOException e) {
                                                                                          return CompletableFuture.failedFuture(e);
                                                                                      }
                                                                                  }));
                                        }
                                        return CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]))
                                                                .thenApply(v -> {
                                                                    StructureAndPersonHolder holder = new StructureAndPersonHolder();
                                                                    holder.setStructure(structure);
                                                                    for(CompletableFuture<Person> future : completableFutures){
                                                                        holder.addPerson(future.join());
                                                                    }
                                                                    return holder;
                                                                });
                                    })));
    }

}
