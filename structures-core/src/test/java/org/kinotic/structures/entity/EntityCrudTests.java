/*
 *
 * Copyright 2008-2021 Kinotic and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kinotic.structures.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kinotic.structures.ElasticsearchTestBase;
import org.kinotic.structures.api.domain.DefaultEntityContext;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.internal.sample.Car;
import org.kinotic.structures.internal.sample.DummyParticipant;
import org.kinotic.structures.internal.sample.Person;
import org.kinotic.structures.internal.sample.TestDataService;
import org.kinotic.structures.support.StructureAndPersonHolder;
import org.kinotic.structures.support.TestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EntityCrudTests extends ElasticsearchTestBase {

    private static final Logger log = LoggerFactory.getLogger(EntityCrudTests.class);

    @Autowired
    private EntitiesService entitiesService;
    @Autowired
    private TestHelper testHelper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestDataService testDataService;


    private StructureAndPersonHolder createAndVerify(){
        return createAndVerify(1,
                               true,
                               new DefaultEntityContext(new DummyParticipant()),
                               "-" + System.currentTimeMillis());
    }

    private StructureAndPersonHolder createAndVerify(int numberOfPeopleToCreate,
                                                     boolean randomPeople,
                                                     EntityContext entityContext,
                                                     String structureSuffix){
        StructureAndPersonHolder ret = new StructureAndPersonHolder();

        StepVerifier.create(testHelper.createPersonStructureAndEntities(numberOfPeopleToCreate,
                                                                        randomPeople,
                                                                        entityContext,
                                                                        structureSuffix))
                    .expectNextMatches(structureAndPersonHolder -> {
                        boolean matches = structureAndPersonHolder.getStructure() != null &&
                                structureAndPersonHolder.getStructure().getId() != null &&
                                structureAndPersonHolder.getPersons().size() == numberOfPeopleToCreate;
                        if(matches){
                            ret.setStructure(structureAndPersonHolder.getStructure());
                            ret.setPersons(structureAndPersonHolder.getPersons());
                        }
                        return matches;
                    })
                    .verifyComplete();
        return ret;
    }

    @Test
    public void testCreateAndDeleteItem() {
        StructureAndPersonHolder holder = createAndVerify();

        Assertions.assertNotNull(holder);

        StepVerifier.create(Mono.fromFuture(entitiesService.deleteById(holder.getStructure().getId(),
                                                                       holder.getFirstPerson().getId(),
                                                                       new DefaultEntityContext(new DummyParticipant()))))
                    .verifyComplete();
    }

    @Test
    public void testFindById(){
        StructureAndPersonHolder holder = createAndVerify();

        Assertions.assertNotNull(holder);

        StepVerifier.create(Mono.fromFuture(entitiesService.findById(holder.getStructure().getId(),
                                                                     holder.getFirstPerson().getId(),
                                                                     RawJson.class,
                                                                     new DefaultEntityContext(new DummyParticipant()))))
                    .expectNextMatches(found -> {
                        boolean ret;
                        try {
                            Person savedPerson = objectMapper.readValue(found.data(),
                                                                        Person.class);
                            Assertions.assertNotNull(savedPerson);

                            ret = savedPerson.equals(holder.getFirstPerson());

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return ret;
                    })
                    .verifyComplete();

    }

    @Test
    public void testCount(){
        EntityContext context1 = new DefaultEntityContext(new DummyParticipant("tenant1", "user1"));
        EntityContext context2 = new DefaultEntityContext(new DummyParticipant("tenant2", "user2"));

        StructureAndPersonHolder holder1 = createAndVerify(10, true, context1, "-testCount");

        Assertions.assertNotNull(holder1);

        StructureAndPersonHolder holder2 = createAndVerify(10, true, context2, "-testCount");

        Assertions.assertNotNull(holder2);

        StepVerifier.create(Mono.fromFuture(entitiesService.count(holder1.getStructure().getId(), context1)))
                    .expectNext(10L)
                    .as("Verifying Tenant 1 has 10 entities")
                    .verifyComplete();

        StepVerifier.create(Mono.fromFuture(entitiesService.count(holder2.getStructure().getId(), context2)))
                    .expectNext(10L)
                    .as("Verifying Tenant 2 has 10 entities")
                    .verifyComplete();
    }

    @Test
    public void testFindAll(){

        EntityContext context1 = new DefaultEntityContext(new DummyParticipant("tenant1", "user1"));
        EntityContext context2 = new DefaultEntityContext(new DummyParticipant("tenant2", "user2"));

        StructureAndPersonHolder holder1 = createAndVerify(10, true, context1, "-testAll");

        Assertions.assertNotNull(holder1);

        StructureAndPersonHolder holder2 = createAndVerify(10, true, context2, "-testAll");

        Assertions.assertNotNull(holder2);

        // TODO: verify all data items as well, not just sizes
        StepVerifier.create(Mono.fromFuture(entitiesService.findAll(holder1.getStructure().getId(),
                                                                    Pageable.ofSize(20), // make sure page size is larger than number of entities
                                                                    RawJson.class,
                                                                    context1)))
                    .expectNextMatches(rawJsons -> rawJsons.getTotalElements() == 10
                            && rawJsons.getTotalPages() == 1
                            && rawJsons.getContent().size() == 10)
                    .as("Verifying Tenant 1 has 10 entities")
                    .verifyComplete();

        StepVerifier.create(Mono.fromFuture(entitiesService.findAll(holder1.getStructure().getId(),
                                                                    Pageable.ofSize(20), // make sure page size is larger than number of entities
                                                                    RawJson.class,
                                                                    context2)))
                    .expectNextMatches(rawJsons -> rawJsons.getTotalElements() == 10
                            && rawJsons.getTotalPages() == 1
                            && rawJsons.getContent().size() == 10)
                    .as("Verifying Tenant 2 has 10 entities")
                    .verifyComplete();
    }

    @Test
    public void testSearch(){
        EntityContext context1 = new DefaultEntityContext(new DummyParticipant("tenant1", "user1"));
        EntityContext context2 = new DefaultEntityContext(new DummyParticipant("tenant2", "user2"));

        StructureAndPersonHolder holder1 = createAndVerify(10, false, context1, "-testSearch");

        Assertions.assertNotNull(holder1);

        StructureAndPersonHolder holder2 = createAndVerify(10, false, context2, "-testSearch");

        Assertions.assertNotNull(holder2);

        // TODO: verify all data items as well, not just sizes
        StepVerifier.create(Mono.fromFuture(entitiesService.search(holder1.getStructure().getId(),
                                                                   "lastName: Z*",
                                                                   Pageable.ofSize(20), // make sure page size is larger than number of entities
                                                                   RawJson.class,
                                                                   context1)))
                    .expectNextMatches(rawJsons -> {
                        boolean b = rawJsons.getTotalElements() == 2
                                && rawJsons.getTotalPages() == 1
                                && rawJsons.getContent().size() == 2;
                        if(!b){
                            log.error("Wrong Data, Wat! Raw:\n"+rawJsons.getContent());
                        }
                        return b;
                    })
                    .as("Verifying search for Tenant 1 has 2 entities")
                    .verifyComplete();

        StepVerifier.create(Mono.fromFuture(entitiesService.search(holder1.getStructure().getId(),
                                                                   "lastName: Z*",
                                                                   Pageable.ofSize(20), // make sure page size is larger than number of entities
                                                                   RawJson.class,
                                                                   context2)))
                    .expectNextMatches(rawJsons -> {
                        boolean b = rawJsons.getTotalElements() == 2
                                && rawJsons.getTotalPages() == 1
                                && rawJsons.getContent().size() == 2;
                        if(!b){
                            log.error("Wrong Data, Wat! Raw:\n"+rawJsons.getContent());
                        }
                        return b;
                    })
                    .as("Verifying search for Tenant 2 has 2 entities")
                    .verifyComplete();
    }

    @Test
    public void findMissingEntity(){
        StructureAndPersonHolder holder = createAndVerify();

        Assertions.assertNotNull(holder);

        StepVerifier.create(Mono.fromFuture(entitiesService.findById(holder.getStructure().getId(),
                                                                     "missing",
                                                                     RawJson.class,
                                                                     new DefaultEntityContext(new DummyParticipant()))))
                    .verifyComplete();
    }

    @Test
    public void testPartialUpdate(){
        EntityContext entityContext = new DefaultEntityContext(new DummyParticipant());
        CompletableFuture<Pair<Structure, Boolean>> createStructure = testDataService.createCarStructureIfNotExists("-partialUpdate");

        StepVerifier.create(Mono.fromFuture(createStructure))
                    .expectNextMatches(pair -> {
                        boolean ret = pair.getLeft() != null && pair.getRight();
                        if(!ret){
                            log.error("Failed to create structure: "+pair.getLeft());
                        }
                        return ret;
                    })
                    .verifyComplete();

        Structure structure = createStructure.join().getLeft();

        Car car = new Car();
        car.setId(UUID.randomUUID().toString());
        car.setMake("Honda");
        car.setModel("Civic");
        car.setYear("2019");

        Car result = testHelper.saveCarAsRawJson(car, structure, entityContext).join();

        Assertions.assertEquals(car.getId(), result.getId(), "Car id does not match");

        Page<RawJson> page = entitiesService.findAll(structure.getId(), Pageable.ofSize(10), RawJson.class, entityContext).join();

        Assertions.assertEquals(1, page.getTotalElements(), "Wrong number of entities");

        List<Person> personList = testDataService.createRandomTestPeopleWithId(1).join();

        Assertions.assertEquals(1, personList.size(), "Failed to create test person");

        Person person = personList.get(0);

        // now do partial update
        car.setMake(null)
           .setModel(null)
           .setYear(null)
           .setOwner(person);

        Car result2 = testHelper.saveCarAsRawJson(car, structure, entityContext).join();

        Assertions.assertEquals(car.getId(), result2.getId(), "Car id does not match after partial update");

        Page<RawJson> page2 = entitiesService.findAll(structure.getId(), Pageable.ofSize(10), RawJson.class, entityContext).join();

        Assertions.assertEquals(1, page2.getTotalElements(), "Wrong number of entities after partial update");
    }
}
