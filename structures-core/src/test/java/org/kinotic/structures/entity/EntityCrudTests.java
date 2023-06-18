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

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.util.BinaryData;
import co.elastic.clients.util.ContentType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kinotic.structures.ElasticsearchTestBase;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.RawJson;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.api.domain.DefaultEntityContext;
import org.kinotic.structures.internal.sample.DummyParticipant;
import org.kinotic.structures.internal.sample.Person;
import org.kinotic.structures.support.StructureAndPersonHolder;
import org.kinotic.structures.support.TestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EntityCrudTests extends ElasticsearchTestBase {

    @Autowired
    private EntitiesService entitiesService;
    @Autowired
    private TestHelper testHelper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ElasticsearchAsyncClient esAsyncClient;

    private StructureAndPersonHolder createAndVerify(){
        return createAndVerify(1,
                               new DefaultEntityContext(new DummyParticipant()),
                               "-" + System.currentTimeMillis());
    }

    private StructureAndPersonHolder createAndVerify(int numberOfPeopleToCreate,
                                                     EntityContext entityContext,
                                                     String structureSuffix){
        StructureAndPersonHolder ret = new StructureAndPersonHolder();

        StepVerifier.create(testHelper.createPersonStructureAndEntities(numberOfPeopleToCreate,
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

        StructureAndPersonHolder holder1 = createAndVerify(10, context1, "-testCount");

        Assertions.assertNotNull(holder1);

        StructureAndPersonHolder holder2 = createAndVerify(10, context2, "-testCount");

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

        StructureAndPersonHolder holder1 = createAndVerify(10, context1, "-testAll");

        Assertions.assertNotNull(holder1);

        StructureAndPersonHolder holder2 = createAndVerify(10, context2, "-testAll");

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
    public void findMissingEntity(){
        StructureAndPersonHolder holder = createAndVerify();

        Assertions.assertNotNull(holder);

        StepVerifier.create(Mono.fromFuture(entitiesService.findById(holder.getStructure().getId(),
                                                                     "missing",
                                                                     RawJson.class,
                                                                     new DefaultEntityContext(new DummyParticipant()))))
                    .verifyComplete();
    }

    //@Test
    public void testBinaryDataIngestion() throws Exception {
        String index = "binary-ingestion-test";
        String id = "foo-bar";

        BinaryData data = BinaryData.of("{\"id\":\"27082ce1-d9ab-404e-a810-f2894640edf4\",\"firstName\":\"Jesse\",\"lastName\":\"Pinkman\",\"addresses\":[{\"street\":\"1001 Central Ave NE\",\"city\":\"Albuquerque\",\"state\":\"NM\",\"zip\":\"87106\"}]}".getBytes(), ContentType.APPLICATION_JSON);

        esAsyncClient.index(i -> i
                .index(index)
                .id(id)
                .document(data)
                .refresh(Refresh.True)
        ).get();

        GetResponse<BinaryData> getResponse = esAsyncClient.get(g -> g
                                                                 .index(index)
                                                                 .id(id)
                , BinaryData.class
        ).get();

        Assertions.assertEquals(id, getResponse.id());
        Assertions.assertEquals(
                "{\"id\":\"27082ce1-d9ab-404e-a810-f2894640edf4\",\"firstName\":\"Jesse\",\"lastName\":\"Pinkman\",\"addresses\":[{\"street\":\"1001 Central Ave NE\",\"city\":\"Albuquerque\",\"state\":\"NM\",\"zip\":\"87106\"}]}",
                new String(getResponse.source().asByteBuffer().array(), StandardCharsets.UTF_8)
        );
    }



//
//    @Test
//    public void createAndDeleteItem_checkingCountBeforeDelete() throws Exception {
//
//        Structure structure = structureTestHelper.getSimpleItemStructure();
//
//        // now we can create an item with the above fields
//        TypeCheckMap obj = new TypeCheckMap();
//        obj.put("ip", "192.0.2.11");
//        obj.put("mac", "000000000001");
//
//        TypeCheckMap saved = itemService.upsertItem(structure.getId(), obj, null);
//
//        Thread.sleep(1000);// give time for ES to flush the new item
//
//        long count = itemService.count(structure.getId(), null);
//        Assertions.assertEquals(1, count);
//
//        SearchHits hits = itemService.getAll(structure.getId(), 100, 0, null);
//        Assertions.assertEquals(1, hits.getHits().length);
//        Assertions.assertEquals(1, hits.getTotalHits().value);
//
//        Thread.sleep(1000);
//
//        itemService.delete(structure.getId(), saved.getString("id"), null);
//
//        Thread.sleep(1000);
//
//        structureService.delete(structure.getId());
//
//    }
//
//    @Test
//    public void createAndDeleteItem_canWeGetById() throws Exception {
//
//        Structure structure = structureTestHelper.getSimpleItemStructure();
//
//        // now we can create an item with the above fields
//        TypeCheckMap obj = new TypeCheckMap();
//        obj.put("ip", "192.0.2.11");
//        obj.put("mac", "000000000001");
//
//        TypeCheckMap saved = itemService.upsertItem(structure.getId(), obj, null);
//
//        Thread.sleep(1000);
//
//        itemService.delete(structure.getId(), saved.getString("id"), null);
//
//        Thread.sleep(1000);
//
//        Optional<TypeCheckMap> deleted = itemService.getById(structure, saved.getString("id"), null);
//
//        // we should still be able to get this item if it was deleted, they still exist just filtered out by default
//        // TODO: we might want to make the deletion route configurable - fully delete item or mark it as deleted and filter
//        Assertions.assertTrue(deleted.isPresent());
//
//        structureService.delete(structure.getId());
//
//    }
//
//    @Test
//    public void createAndDeleteItem_checkingCountAfterDelete() throws Exception {
//
//        Structure structure = structureTestHelper.getSimpleItemStructure();
//
//        // now we can create an item with the above fields
//        TypeCheckMap obj = new TypeCheckMap();
//        obj.put("ip", "192.0.2.11");
//        obj.put("mac", "000000000001");
//
//        TypeCheckMap saved = itemService.upsertItem(structure.getId(), obj, null);
//
//        Thread.sleep(1000);
//
//        itemService.delete(structure.getId(), saved.getString("id"), null);
//
//        Thread.sleep(1000);
//
//        long count = itemService.count(structure.getId(), null);
//        Assertions.assertEquals(0, count);
//
//        SearchHits hits = itemService.getAll(structure.getId(), 100, 0, null);
//        Assertions.assertEquals(0, hits.getHits().length);
//        Assertions.assertEquals(0, hits.getTotalHits().value);
//
//        Thread.sleep(1000);
//
//        structureService.delete(structure.getId());
//
//    }
//
//
//    @Test
//    public void createAndupsertItem() throws Exception {
//
//        Structure structure = structureTestHelper.getSimpleItemStructure();
//
//        // now we can create an item with the above fields
//        TypeCheckMap obj = new TypeCheckMap();
//        obj.put("ip", "192.0.2.11");
//        obj.put("mac", "000000000001");
//
//        TypeCheckMap saved = itemService.upsertItem(structure.getId(), obj, null);
//
//        try {
//            Thread.sleep(1000);// give time for ES to flush the new item
//
//            saved = itemService.getItemById(structure.getId(), saved.getString("id"), null).get();
//
//            if (!saved.getString("mac").equals("000000000001")) {
//                throw new IllegalStateException("Data provided to Item apon saving and getting");
//            }
//
//
//            saved.put("mac", "aaaaddddrrrr");
//
//            itemService.upsertItem(structure.getId(), saved, null);
//
//            saved = itemService.getItemById(structure.getId(), saved.getString("id"), null).get();
//
//            if (!saved.getString("mac").equals("aaaaddddrrrr")) {
//                throw new IllegalStateException("Data provided to Item apon saving and getting");
//            }
//
//
//        } catch (AlreadyExistsException e) {
//            throw e;
//        } finally {
//            itemService.delete(structure.getId(), saved.getString("id"), null);
//
//            Thread.sleep(1000);
//
//            structureService.delete(structure.getId());
//        }
//    }
//
//    @Test
//    public void validatePrimaryKeyWithTwoFields() throws Exception {
//
//        Structure structure = new Structure();
//        structure.setName("Item3-" + System.currentTimeMillis());
//        structure.setNamespace("my.funky-Namespace_");
//        structure.setDescription("Defines an Person");
//
//        Optional<Trait> stateOptional = traitService.getTraitByName("KeywordString");
//        Optional<Trait> cityOptional = traitService.getTraitByName("KeywordString");
//        Optional<Trait> addressOptional = traitService.getTraitByName("KeywordString");
//        Optional<Trait> firstNameOptional = traitService.getTraitByName("KeywordString");
//        Optional<Trait> lastNameOptional = traitService.getTraitByName("KeywordString");
//
//        structure.getTraits().put("state", stateOptional.get());
//        structure.getTraits().put("city", cityOptional.get());
//        structure.getTraits().put("address", addressOptional.get());
//        structure.getTraits().put("firstName", firstNameOptional.get());
//        structure.getTraits().put("lastName", lastNameOptional.get());
//
//        // should also get createdTime, updateTime, and deleted by default
//
//        // now we can create an item with the above fields
//        TypeCheckMap obj = new TypeCheckMap();
//        obj.put("state", "Nevada");
//        obj.put("city", "Las Vegas");
//        obj.put("address", "111 Las Vegas Blvd");
//        obj.put("firstName", "Marco");
//        obj.put("lastName", "Polo");
//        obj.put("id", "nevada-las_vegas-111_las_vegas_blvd");
//
//        structure = structureService.save(structure);
//        structureService.publish(structure.getId());
//        TypeCheckMap saved = itemService.upsertItem(structure.getId(), obj, null);
//
//        try {
//            Thread.sleep(1000);// give time for ES to flush the new item
//
//            Optional<TypeCheckMap> freshOpt = itemService.getItemById(structure.getId(), "nevada-las_vegas-111_las_vegas_blvd", null);
//
//            if(freshOpt.isEmpty()){
//                throw new IllegalStateException("Composite Primary Key was not saved as expected");
//            }
//
//            TypeCheckMap fresh = freshOpt.get();
//
//            if (!fresh.getString("firstName").equals("Marco")) {
//                throw new IllegalStateException("Data provided to upsert was not saved properly");
//            }
//
//            fresh.put("firstName", "The");
//            fresh.put("lastName", "Dude");
//
//            TypeCheckMap updated = itemService.upsertItem(structure.getId(), fresh, null);
//
//            if (!updated.getString("firstName").equals("The") || !updated.getString("lastName").equals("Dude")) {
//                throw new IllegalStateException("Data provided to upsert was not saved properly");
//            }
//
//            TypeCheckMap secondGet = itemService.getItemById(structure.getId(), "nevada-las_vegas-111_las_vegas_blvd", null).get();
//
//            if (!secondGet.getString("firstName").equals("The") || !secondGet.getString("lastName").equals("Dude")) {
//                throw new IllegalStateException("Data provided to upsert was not saved properly");
//            }
//
//        } catch (AlreadyExistsException e) {
//            throw e;
//        } finally {
//            itemService.delete(structure.getId(), saved.getString("id"), null);
//
//            Thread.sleep(1000);
//
//            structureService.delete(structure.getId());
//        }
//    }
//
//    @Test
//    public void upsertItemThenAddFieldAndupsertItem() throws Exception {
//
//        Structure structure = structureTestHelper.getSimpleItemStructure();
//
//        // now we can create an item with the above fields
//        TypeCheckMap obj = new TypeCheckMap();
//        obj.put("ip", "192.0.2.101");
//        obj.put("vpnIp", "10.0.2.101");
//        obj.put("mac", "000000000001");
//
//        TypeCheckMap saved = itemService.upsertItem(structure.getId(), obj, null);
//
//        try {
//            Thread.sleep(1000);// give time for ES to flush the new item
//
//            saved = itemService.getItemById(structure.getId(), saved.getString("id"), null).get();
//
//            if (!saved.getString("ip").equals("192.0.2.101")) {
//                throw new IllegalStateException("ip provided to Item apon saving and getting are not what was expected.");
//            }
//
//            Optional<Trait> wifiMacOptional = traitService.getTraitByName("Mac");
//            structureService.addTraitToStructure(structure.getId(), "wifiMac", wifiMacOptional.get());
//
//            saved.put("wifiMac", "aaaaddddrrrr");
//
//            itemService.upsertItem(structure.getId(), saved, null);
//
//            saved = itemService.getItemById(structure.getId(), saved.getString("id"), null).get();
//
//            if (!saved.getString("wifiMac").equals("aaaaddddrrrr")) {
//                throw new IllegalStateException("Data provided to Item apon saving and getting");
//            }
//
//
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            itemService.delete(structure.getId(), saved.getString("id"), null);
//            Thread.sleep(1000);
//            structureService.delete(structure.getId());
//        }
//
//
//    }
//
//    @Test
//    public void upsertItemThenPerformPartialUpdate() throws Exception {
//
//        Structure structure = structureTestHelper.getSimpleItemStructure();
//
//        // now we can create an item with the above fields
//        TypeCheckMap obj = new TypeCheckMap();
//        obj.put("ip", "192.0.2.101");
//        obj.put("mac", "111111111111");
//
//        TypeCheckMap saved = itemService.upsertItem(structure.getId(), obj, null);
//
//        try {
//            Thread.sleep(1000);// give time for ES to flush the new item
//
//            saved = itemService.getItemById(structure.getId(), saved.getString("id"), null).get();
//
//            if (!saved.getString("mac").equals("111111111111")) {
//                throw new IllegalStateException("mac provided to Item apon saving and getting are not what was expected.");
//            }
//
//
//            TypeCheckMap partial = new TypeCheckMap();
//            partial.put("id", saved.getString("id"));// required to update
//            partial.put("mac", "aaaaddddrrrr");
//            partial.put("ip", "192.0.2.101");
//
//            itemService.upsertItem(structure.getId(), partial, null);
//
//            TypeCheckMap updated = itemService.getItemById(structure.getId(), saved.getString("id"), null).get();
//
//            if (!updated.getString("mac").equals("aaaaddddrrrr")) {
//                throw new IllegalStateException("mac provided to Item apon saving and getting are not what we expected from the updated.");
//            }
//
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            itemService.delete(structure.getId(), saved.getString("id"), null);
//            Thread.sleep(1000);
//            structureService.delete(structure.getId());
//        }
//
//    }
}
