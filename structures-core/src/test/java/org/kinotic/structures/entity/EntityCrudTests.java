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
import org.kinotic.continuum.core.api.crud.CursorPage;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.continuum.core.api.crud.Sort;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
    public void testCreateAndDeleteByQuery() throws InterruptedException {
        EntityContext context = new DefaultEntityContext(new DummyParticipant("tenant", "user"));

        StructureAndPersonHolder holder = createAndVerify(20, false, context, "-testFindByIds");

        Assertions.assertNotNull(holder);

        StepVerifier.create(Mono.fromFuture(entitiesService.count(holder.getStructure().getId(), context)))
                .expectNext(20L)
                .as("Verifying Tenant 1 has 20 entities")
                .verifyComplete();

        StepVerifier.create(Mono.fromFuture(entitiesService.deleteByQuery(holder.getStructure().getId(),
                        "lastName: A*",
                        context)))
                .verifyComplete();

        Thread.sleep(5000);

        StepVerifier.create(Mono.fromFuture(entitiesService.count(holder.getStructure().getId(), context)))
                .expectNext(18L)
                .as("Verifying Tenant 1 has 18 entities after delete by query")
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
    public void testFindByIds(){
        EntityContext context = new DefaultEntityContext(new DummyParticipant("tenant", "user"));

        StructureAndPersonHolder holder = createAndVerify(10, true, context, "-testFindByIds");

        Assertions.assertNotNull(holder);

        HashMap<String, Person> personMap = new HashMap<>();
        ArrayList<String> ids = new ArrayList<>();
        for(int i = 0; i < holder.getPersons().size(); i++){
            Person p = holder.getPersons().get(i);
            personMap.put(p.getId(), p);
            if(i % 2 == 0){
                ids.add(p.getId());
            }
        }

        StepVerifier.create(Mono.fromFuture(entitiesService.findByIds(holder.getStructure().getId(),
                        ids,
                        RawJson.class,
                        context)))
                .expectNextMatches(responseList -> {
                    boolean ret = false;
                    try {
                        Assertions.assertEquals(ids.size(), responseList.size(), "id list size does not match response list");
                        for(RawJson json : responseList){
                            Person savedPerson = objectMapper.readValue(json.data(),
                                    Person.class);
                            Assertions.assertNotNull(savedPerson);
                            ret = savedPerson.equals(personMap.get(savedPerson.getId()));
                            if(!ret){
                                break;
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return ret;
                })
                .verifyComplete();

    }

    @Test
    public void testFindByIdsNoneFound(){
        EntityContext context = new DefaultEntityContext(new DummyParticipant("tenant", "user"));

        StructureAndPersonHolder holder = createAndVerify(10, true, context, "-testFindByIdsNoneFound");

        Assertions.assertNotNull(holder);

        ArrayList<String> ids = new ArrayList<>();
        for(int i = 0; i < holder.getPersons().size(); i++){
            Person p = holder.getPersons().get(i);
            if(i % 2 == 0){
                ids.add("aaaaa"+p.getId()+"aaaaa");
            }
        }

        StepVerifier.create(Mono.fromFuture(entitiesService.findByIds(holder.getStructure().getId(),
                        ids,
                        RawJson.class,
                        context)))
                .expectNextMatches(List::isEmpty)
                .as("Verifying Tenant ids query to be empty as none of ids matches")
                .verifyComplete();

    }

    @Test
    public void testCount(){
        EntityContext context1 = new DefaultEntityContext(new DummyParticipant("tenant1", "user1"));
        EntityContext context2 = new DefaultEntityContext(new DummyParticipant("tenant2", "user2"));

        StructureAndPersonHolder holder1 = createAndVerify(10, true, context1, "-testCount");

        Assertions.assertNotNull(holder1);

        StructureAndPersonHolder holder2 = createAndVerify(20, true, context2, "-testCount");

        Assertions.assertNotNull(holder2);

        StepVerifier.create(Mono.fromFuture(entitiesService.count(holder1.getStructure().getId(), context1)))
                    .expectNext(10L)
                    .as("Verifying Tenant 1 has 10 entities")
                    .verifyComplete();

        StepVerifier.create(Mono.fromFuture(entitiesService.count(holder2.getStructure().getId(), context2)))
                    .expectNext(20L)
                    .as("Verifying Tenant 2 has 20 entities")
                    .verifyComplete();
    }


    @Test
    public void testCountByQuery(){
        EntityContext context1 = new DefaultEntityContext(new DummyParticipant("tenant1", "user1"));
        EntityContext context2 = new DefaultEntityContext(new DummyParticipant("tenant2", "user2"));

        StructureAndPersonHolder holder1 = createAndVerify(10, false, context1, "-testCountByQuery");

        Assertions.assertNotNull(holder1);

        StructureAndPersonHolder holder2 = createAndVerify(20, false, context2, "-testCountByQuery");

        Assertions.assertNotNull(holder2);

        StepVerifier.create(Mono.fromFuture(entitiesService.countByQuery(holder1.getStructure().getId(), "lastName: Z*", context1)))
                .expectNext(2L)
                .as("Verifying Tenant 1 has 2 entities by search")
                .verifyComplete();

        StepVerifier.create(Mono.fromFuture(entitiesService.countByQuery(holder2.getStructure().getId(), "lastName: A*", context2)))
                .expectNext(2L)
                .as("Verifying Tenant 2 has 2 entities by search")
                .verifyComplete();

        StepVerifier.create(Mono.fromFuture(entitiesService.countByQuery(holder2.getStructure().getId(), "lastName: a*", context2)))
                .expectNext(0L)
                .as("Verifying Tenant 0 has 2 entities by search")
                .verifyComplete();

    }


    @Test
    public void testFindAll(){

        EntityContext context1 = new DefaultEntityContext(new DummyParticipant("tenant1", "user1"));
        EntityContext context2 = new DefaultEntityContext(new DummyParticipant("tenant2", "user2"));

        StructureAndPersonHolder holder1 = createAndVerify(10, true, context1, "-testAll");

        Assertions.assertNotNull(holder1);

        StructureAndPersonHolder holder2 = createAndVerify(20, true, context2, "-testAll");

        Assertions.assertNotNull(holder2);

        // TODO: verify all data items as well, not just sizes
        StepVerifier.create(Mono.fromFuture(entitiesService.findAll(holder1.getStructure().getId(),
                                                                    Pageable.ofSize(20), // make sure page size is larger than number of entities
                                                                    RawJson.class,
                                                                    context1)))
                    .expectNextMatches(rawJsons -> rawJsons.getTotalElements() == 10
                            && rawJsons.getContent().size() == 10)
                    .as("Verifying Tenant 1 has 10 entities")
                    .verifyComplete();

        StepVerifier.create(Mono.fromFuture(entitiesService.findAll(holder2.getStructure().getId(),
                                                                    Pageable.ofSize(20), // make sure page size is larger than number of entities
                                                                    RawJson.class,
                                                                    context2)))
                    .expectNextMatches(rawJsons -> rawJsons.getTotalElements() == 20
                            && rawJsons.getContent().size() == 20)
                    .as("Verifying Tenant 2 has 20 entities")
                    .verifyComplete();
    }

    @Test
    public void testFindAllWithCursor(){

        EntityContext context1 = new DefaultEntityContext(new DummyParticipant("tenant1", "user1"));
        EntityContext context2 = new DefaultEntityContext(new DummyParticipant("tenant2", "user2"));

        StructureAndPersonHolder holder1 = createAndVerify(40, true, context1, "-testAllWCursor");

        Assertions.assertNotNull(holder1);

        StructureAndPersonHolder holder2 = createAndVerify(30, true, context2, "-testAllWCursor");

        Assertions.assertNotNull(holder2);

        // Make sure all data is indexed
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // TODO: verify all data items as well, not just sizes
        Sort sort = Sort.by("firstName");
        AtomicReference<String> cursorRef = new AtomicReference<>();

        StepVerifier.create(Mono.fromFuture(entitiesService.findAll(holder1.getStructure().getId(),
                                                                    Pageable.create("",
                                                                                    20,
                                                                                    Sort.by("firstName")),
                                                                    RawJson.class,
                                                                    context1)))
                    .expectNextMatches(page -> {
                        String cursor = null;
                        if(page instanceof CursorPage){
                            cursor = ((CursorPage<?>)page).getCursor();
                            cursorRef.set(cursor);
                        }
                        return cursor != null
                                && page.getTotalElements() == null
                                && page.getContent().size() == 20;
                    })
                    .as("Verifying First page for Tenant 1")
                    .verifyComplete();

        Assertions.assertNotNull(cursorRef.get(), "Cursor is null");

        StepVerifier.create(Mono.fromFuture(entitiesService.findAll(holder1.getStructure().getId(),
                                                                    Pageable.create(cursorRef.get(), 20, sort),
                                                                    RawJson.class,
                                                                    context1)))
                    .expectNextMatches(page -> {
                        String cursor = null;
                        cursorRef.set(null);
                        if(page instanceof CursorPage){
                            cursor = ((CursorPage<?>)page).getCursor();
                            cursorRef.set(cursor);
                        }
                        return cursor != null
                                && page.getTotalElements() ==  null
                                && page.getContent().size() == 20;
                    })
                    .as("Verifying Second page for Tenant 1")
                    .verifyComplete();

        Assertions.assertNotNull(cursorRef.get(), "Cursor is null");

        StepVerifier.create(Mono.fromFuture(entitiesService.findAll(holder1.getStructure().getId(),
                                                                    Pageable.create(cursorRef.get(), 20, sort),
                                                                    RawJson.class,
                                                                    context1)))
                    .expectNextMatches(page -> {
                        String cursor = null;
                        cursorRef.set(null);
                        if(page instanceof CursorPage){
                            cursor = ((CursorPage<?>)page).getCursor();
                            cursorRef.set(cursor);
                        }
                        return cursor == null
                                && page.getTotalElements() == null
                                && page.getContent().isEmpty();
                    })
                    .as("Verifying Third page is empty for Tenant 1")
                    .verifyComplete();

        // Second tenant

        Assertions.assertNull(cursorRef.get(), "Cursor is not null");

        StepVerifier.create(Mono.fromFuture(entitiesService.findAll(holder2.getStructure().getId(),
                                                                    Pageable.create("", 10, sort),
                                                                    RawJson.class,
                                                                    context2)))
                    .expectNextMatches(page -> {
                        String cursor = null;
                        if(page instanceof CursorPage){
                            cursor = ((CursorPage<?>)page).getCursor();
                            cursorRef.set(cursor);
                        }
                        return cursor != null
                                && page.getTotalElements() == null
                                && page.getContent().size() == 10;
                    })
                    .as("Verifying First page for Tenant 2")
                    .verifyComplete();

        Assertions.assertNotNull(cursorRef.get(), "Cursor is null");

        StepVerifier.create(Mono.fromFuture(entitiesService.findAll(holder2.getStructure().getId(),
                                                                    Pageable.create(cursorRef.get(), 10, sort),
                                                                    RawJson.class,
                                                                    context2)))
                    .expectNextMatches(page -> {
                        String cursor = null;
                        cursorRef.set(null);
                        if(page instanceof CursorPage){
                            cursor = ((CursorPage<?>)page).getCursor();
                            cursorRef.set(cursor);
                        }
                        return cursor != null
                                && page.getTotalElements() == null
                                && page.getContent().size() == 10;
                    })
                    .as("Verifying Second page for Tenant 2")
                    .verifyComplete();

        Assertions.assertNotNull(cursorRef.get(), "Cursor is null");

        StepVerifier.create(Mono.fromFuture(entitiesService.findAll(holder2.getStructure().getId(),
                                                                    Pageable.create(cursorRef.get(), 10, sort),
                                                                    RawJson.class,
                                                                    context2)))
                    .expectNextMatches(page -> {
                        String cursor = null;
                        cursorRef.set(null);
                        if(page instanceof CursorPage){
                            cursor = ((CursorPage<?>)page).getCursor();
                            cursorRef.set(cursor);
                        }
                        return cursor != null
                                && page.getTotalElements() == null
                                && page.getContent().size() == 10;
                    })
                    .as("Verifying Third page for Tenant 2")
                    .verifyComplete();

        Assertions.assertNotNull(cursorRef.get(), "Cursor is null");


        StepVerifier.create(Mono.fromFuture(entitiesService.findAll(holder2.getStructure().getId(),
                                                                    Pageable.create(cursorRef.get(), 10, sort),
                                                                    RawJson.class,
                                                                    context2)))
                    .expectNextMatches(page -> {
                        String cursor = null;
                        cursorRef.set(null);
                        if(page instanceof CursorPage){
                            cursor = ((CursorPage<?>)page).getCursor();
                            cursorRef.set(cursor);
                        }
                        return cursor == null
                                && page.getTotalElements() == null
                                && page.getContent().isEmpty();
                    })
                    .as("Verifying Fourth page is empty for Tenant 2")
                    .verifyComplete();
    }

    @Test
    public void testSearch(){
        EntityContext context1 = new DefaultEntityContext(new DummyParticipant("tenant1", "user1"));
        EntityContext context2 = new DefaultEntityContext(new DummyParticipant("tenant2", "user2"));

        StructureAndPersonHolder holder1 = createAndVerify(10, false, context1, "-testSearch");

        Assertions.assertNotNull(holder1);

        StructureAndPersonHolder holder2 = createAndVerify(20, false, context2, "-testSearch");

        Assertions.assertNotNull(holder2);

        // TODO: verify all data items as well, not just sizes
        StepVerifier.create(Mono.fromFuture(entitiesService.search(holder1.getStructure().getId(),
                                                                   "lastName: Z*",
                                                                   Pageable.ofSize(20), // make sure page size is larger than number of entities
                                                                   RawJson.class,
                                                                   context1)))
                    .expectNextMatches(rawJsons -> {
                        boolean b = rawJsons.getTotalElements() == 2
                                && rawJsons.getContent().size() == 2;
                        if(!b){
                            log.error("Wrong Data, Wat! Raw:\n"+rawJsons.getContent());
                        }
                        return b;
                    })
                    .as("Verifying search for Tenant 1 has 2 entities")
                    .verifyComplete();

        StepVerifier.create(Mono.fromFuture(entitiesService.search(holder2.getStructure().getId(),
                                                                   "lastName: Z*",
                                                                   Pageable.ofSize(20), // make sure page size is larger than number of entities
                                                                   RawJson.class,
                                                                   context2)))
                    .expectNextMatches(rawJsons -> {
                        boolean b = rawJsons.getTotalElements() == 2
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
    public void testMultiTenantSearch(){

        HashMap<DefaultEntityContext, StructureAndPersonHolder> contextMap = new HashMap<>();
        for(int i = 0; i < 10; i++){
            int numberOfPeople = (int)(Math.random()*100);
            DefaultEntityContext context = new DefaultEntityContext(new DummyParticipant("tenant"+i, "user"+i));
            StructureAndPersonHolder holder = createAndVerify(numberOfPeople, true, context, "-testMultiTenantSearch");
            Assertions.assertNotNull(holder);
            contextMap.put(context,  holder);
        }

        contextMap.forEach((context, holder) -> {
            StepVerifier.create(Mono.fromFuture(entitiesService.search(holder.getStructure().getId(),
                            "lastName: *",
                            Pageable.ofSize(20), // make sure page size is larger than number of entities
                            RawJson.class,
                            context)))
                    .expectNextMatches(rawJsons -> {
                        long expectedCount = holder.getPersons().size();
                        long queriedCount = rawJsons.getTotalElements();
                        if(expectedCount != queriedCount){
                            log.error("Wrong Data, Wat! Expected: "+expectedCount+" and Queried: "+queriedCount);
                        }
                        return expectedCount == queriedCount;
                    })
                    .as("Verifying search for Multi Tenant, count queried does not match how many we stored")
                    .verifyComplete();
        });

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
    public void testPartialUpdate() throws Exception {
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

        Car result2 = testHelper.updateCarAsRawJson(car, structure, entityContext).join();

        Assertions.assertEquals(car.getId(), result2.getId(), "Car id does not match after partial update");

        Page<RawJson> page2 = entitiesService.findAll(structure.getId(), Pageable.ofSize(10), RawJson.class, entityContext).join();

        Assertions.assertEquals(1, page2.getTotalElements(), "Wrong number of entities after partial update");

        Car updatedCar = objectMapper.readValue(page2.getContent().get(0).data(), Car.class);

        Assertions.assertNotNull(updatedCar.getMake(), "Expected make to be null after partial update");
        Assertions.assertNotNull(updatedCar.getModel(), "Expected model to be null after partial update");
        Assertions.assertNotNull(updatedCar.getYear(), "Expected year to be null after partial update");
        Assertions.assertNotNull(updatedCar.getOwner(), "Expected owner to be set after partial update");
    }

}
