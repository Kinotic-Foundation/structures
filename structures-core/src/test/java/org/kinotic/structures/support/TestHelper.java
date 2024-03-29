package org.kinotic.structures.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.sample.Car;
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
    private TestDataService testDataService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntitiesService entitiesService;

    public CompletableFuture<Void> bulkUpdateCarsAsRawJson(List<Car> cars, Structure structure, EntityContext entityContext){
        byte[] jsonData;
        try {
            jsonData = objectMapper.writeValueAsBytes(cars);
        } catch (JsonProcessingException e) {
            return CompletableFuture.failedFuture(new IllegalStateException(e));
        }
        return entitiesService.bulkUpdate(structure.getId(), RawJson.from(jsonData), entityContext);
    }

    public CompletableFuture<Void> bulkSaveCarsAsRawJson(List<Car> cars, Structure structure, EntityContext entityContext){
        byte[] jsonData;
        try {
            jsonData = objectMapper.writeValueAsBytes(cars);
        } catch (JsonProcessingException e) {
            return CompletableFuture.failedFuture(new IllegalStateException(e));
        }
        return entitiesService.bulkSave(structure.getId(), RawJson.from(jsonData), entityContext);
    }

    public CompletableFuture<Car> saveCarAsRawJson(Car car, Structure structure, EntityContext entityContext){
        byte[] jsonData;
        try {
            jsonData = objectMapper.writeValueAsBytes(car);
        } catch (JsonProcessingException e) {
            return CompletableFuture.failedFuture(new IllegalStateException(e));
        }
        return entitiesService.save(structure.getId(), RawJson.from(jsonData), entityContext)
                              .thenApply(entity -> {
                                  try {
                                      return objectMapper.readValue(entity.data(), Car.class);
                                  } catch (IOException e) {
                                      throw new IllegalStateException(e);
                                  }
                              });
    }


    public CompletableFuture<Car> updateCarAsRawJson(Car car, Structure structure, EntityContext entityContext){
        byte[] jsonData;
        try {
            jsonData = objectMapper.writeValueAsBytes(car);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(new IllegalStateException(e));
        }

        return entitiesService.update(structure.getId(), RawJson.from(jsonData), entityContext)
                              .thenApply(entity -> {
                                  try {
                                      return objectMapper.readValue(jsonData, Car.class);
                                  } catch (IOException e) {
                                      throw new IllegalStateException(e);
                                  }
                              });
    }

    /**
     * Creates a {@link org.kinotic.structures.api.domain.Structure} if it does not exist with the given name suffix and then creates the given number of people
     * @param numberOfPeopleToCreate the number of people to create
     * @param randomPeople if true random people will be created, if false a static list of people will be created
     * @param entityContext the {@link EntityContext} to use when creating the entities
     * @param structureNameSuffix the suffix to append to the structure name or null to not append anything
     * @return a {@link Mono} that will emit a {@link StructureAndPersonHolder} when complete
     */
    public Mono<StructureAndPersonHolder> createPersonStructureAndEntities(int numberOfPeopleToCreate,
                                                                           boolean randomPeople,
                                                                           EntityContext entityContext,
                                                                           String structureNameSuffix){
        return Mono.fromFuture(() -> testDataService
                .createPersonStructureIfNotExists(structureNameSuffix)
                .thenCompose(pair -> createTestPeopleWithCorrectMethod(numberOfPeopleToCreate, randomPeople)
                                             .thenCompose(people -> {
                                                 Structure structure = pair.getLeft();
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
                                                                                                 entityContext)
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

    public Mono<StructureAndPersonHolder> createPersonStructureAndEntitiesBulk(int numberOfPeopleToCreate,
                                                                               boolean randomPeople,
                                                                               EntityContext entityContext,
                                                                               String structureNameSuffix){
        return Mono.fromFuture(() -> testDataService
                .createPersonStructureIfNotExists(structureNameSuffix)
                .thenCompose(pair -> createTestPeopleWithCorrectMethod(numberOfPeopleToCreate, randomPeople)
                                             .thenCompose(people -> {
                                                 Structure structure = pair.getLeft();
                                                 byte[] jsonData;
                                                 try {
                                                     jsonData = objectMapper.writeValueAsBytes(people);
                                                 } catch (JsonProcessingException e) {
                                                     return CompletableFuture.failedFuture(e);
                                                 }
                                                 return entitiesService.bulkSave(structure.getId(),
                                                                                 RawJson.from(jsonData),
                                                                                 entityContext)
                                                         .thenCompose(unused -> CompletableFuture
                                                                 .completedFuture(new StructureAndPersonHolder(
                                                                         structure,
                                                                         people)));
                                             })));
    }


    private CompletableFuture<List<Person>> createTestPeopleWithCorrectMethod(int numberOfPeopleToCreate,
                                                                              boolean randomPeople){
        if(randomPeople){
            return testDataService.createRandomTestPeople(numberOfPeopleToCreate);
        }else {
            return testDataService.createTestPeople(numberOfPeopleToCreate);
        }
    }


}
