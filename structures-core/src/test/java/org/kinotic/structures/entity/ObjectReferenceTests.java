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

import org.kinotic.structures.ElasticsearchTestBase;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest
public class ObjectReferenceTests extends ElasticsearchTestBase {

//    @Autowired
//    private ItemService itemService;
//    @Autowired
//    private TraitService traitService;
//    @Autowired
//    private StructureService structureService;
//    @Autowired
//    private StructureTestHelper structureTestHelper;
//
//    @Test
//    public void createItemThatAlsoHasObjectReference_GetAllShouldNotLazyLoad_ThenDeleteAll() throws Exception {
//
//        Structure computerStructure = getComputerStructure();
//        Structure deviceStructure = getDeviceStructure();
//        Structure officeStructure = getOfficeStructure(deviceStructure,computerStructure);
//
//        TypeCheckMap computer = new TypeCheckMap();
//        computer.put("ip", "192.0.2.101");
//        computer.put("mac", "111111111111");
//
//        TypeCheckMap device1 = new TypeCheckMap();
//        device1.put("ip", "192.0.2.211");
//        device1.put("mac", "111111111111");
//        device1.put("generation", "B+v1.2");
//        device1.put("type", "SensorDevice");
//
//        TypeCheckMap device2 = new TypeCheckMap();
//        device2.put("ip", "192.0.2.212");
//        device2.put("mac", "111111111112");
//        device2.put("generation", "Bv1.1");
//        device2.put("type", "LightDevice");
//
//
//        TypeCheckMap savedComputer = itemService.createItem(computerStructure.getId(), computer);
//        TypeCheckMap savedDevice1 = itemService.createItem(deviceStructure.getId(), device1);
//        TypeCheckMap savedDevice2 = itemService.createItem(deviceStructure.getId(), device2);
//
//        TypeCheckMap office = new TypeCheckMap();
//        office.put("partNumber", "123456789");
//        office.put("computer", savedComputer);
//        office.put("device1", savedDevice1);
//        office.put("device2", savedDevice2);
//
//        TypeCheckMap savedOffice = itemService.createItem(officeStructure.getId(), office);
//
//        try {
//
//            Thread.sleep(1000);// give time for ES to flush the new item
//
//            SearchHits hits = itemService.getAll(officeStructure.getId(), 100, 0);
//
//            SearchHit hit = hits.getHits()[0];
//            TypeCheckMap obj = new TypeCheckMap(hit.getSourceAsMap());
//            final TypeCheckMap computerRef = obj.getTypeCheckMap("computer");
//            if (computerRef.length() != 2) {
//                throw new IllegalStateException("We saved a Office which has ObjectReferences, getAll() should never resolve deps, expected 2 fields - structureId, and id - got " + computerRef.length());
//            }
//
//
//        } finally {
//            itemService.delete(officeStructure.getId(), savedOffice.getString("id"));
//            itemService.delete(computerStructure.getId(), savedComputer.getString("id"));
//            itemService.delete(deviceStructure.getId(), savedDevice1.getString("id"));
//            itemService.delete(deviceStructure.getId(), savedDevice2.getString("id"));
//            Thread.sleep(1000);
//            structureService.delete(computerStructure.getId());
//            structureService.delete(deviceStructure.getId());
//            structureService.delete(officeStructure.getId());
//        }
//
//    }
//
//    @Test
//    public void createItemThatAlsoHasObjectReference_GetOwnerByIdShouldLazyLoad_ThenDeleteAll() throws Exception {
//
//        Structure computerStructure = getComputerStructure();
//        Structure deviceStructure = getDeviceStructure();
//        Structure officeStructure = getOfficeStructure(deviceStructure,computerStructure);
//
//        TypeCheckMap computer = new TypeCheckMap();
//        computer.put("ip", "192.0.2.101");
//        computer.put("mac", "111111111111");
//
//        TypeCheckMap device1 = new TypeCheckMap();
//        device1.put("ip", "192.0.2.211");
//        device1.put("mac", "111111111111");
//        device1.put("generation", "B+v1.2");
//        device1.put("type", "SensorDevice");
//
//        TypeCheckMap device2 = new TypeCheckMap();
//        device2.put("ip", "192.0.2.212");
//        device2.put("mac", "111111111112");
//        device2.put("generation", "Bv1.1");
//        device2.put("type", "LightDevice");
//
//
//        TypeCheckMap savedComputer = itemService.createItem(computerStructure.getId(), computer);
//        TypeCheckMap savedDevice1 = itemService.createItem(deviceStructure.getId(), device1);
//        TypeCheckMap savedDevice2 = itemService.createItem(deviceStructure.getId(), device2);
//
//        TypeCheckMap office = new TypeCheckMap();
//        office.put("partNumber", "123456789");
//        office.put("computer", savedComputer);
//        office.put("device1", savedDevice1);
//        office.put("device2", savedDevice2);
//
//        TypeCheckMap savedOffice = itemService.createItem(officeStructure.getId(), office);
//
//        try {
//
//            Thread.sleep(1000);// give time for ES to flush the new item
//
//            Optional<TypeCheckMap> resolvedOffice = itemService.getById(officeStructure, office.getString("id"));
//
//            final TypeCheckMap computerRef = resolvedOffice.get().getTypeCheckMap("computer");
//            if (computerRef.length() != 9 || !computerRef.getString("ip").equals("192.0.2.101")) {
//                throw new IllegalStateException("We saved a Office which has ObjectReferences, called getById() and it should resolve all deps, Computer (computer) expected 9 fields - got " + computerRef.length());
//            }
//
//            final TypeCheckMap device1Ref = resolvedOffice.get().getTypeCheckMap("device1");
//            if (device1Ref.length() != 11 || !device1Ref.getString("type").equals("SensorDevice")) {
//                throw new IllegalStateException("We saved a Office which has ObjectReferences, called getById() and it should resolve all deps, Device (device1) expected 11 fields - got " + device1Ref.length());
//            }
//
//            final TypeCheckMap device2Ref = resolvedOffice.get().getTypeCheckMap("device2");
//            if (device2Ref.length() != 11 || !device2Ref.getString("type").equals("LightDevice")) {
//                throw new IllegalStateException("We saved a Office which has ObjectReferences, called getById() and it should resolve all deps, Device (device2) expected 11 fields - got " + device2Ref.length());
//            }
//
//
//        } finally {
//            itemService.delete(officeStructure.getId(), savedOffice.getString("id"));
//            itemService.delete(computerStructure.getId(), savedComputer.getString("id"));
//            itemService.delete(deviceStructure.getId(), savedDevice1.getString("id"));
//            itemService.delete(deviceStructure.getId(), savedDevice2.getString("id"));
//            Thread.sleep(1000);
//            structureService.delete(computerStructure.getId());
//            structureService.delete(deviceStructure.getId());
//            structureService.delete(officeStructure.getId());
//        }
//
//    }
//
//    @Test
//    public void createItemThatAlsoHasObjectReferences_ThenAttemptToDeleteReference_CleanUp() throws Exception {
//        Assertions.assertThrows(IsReferencedException.class, () -> {
//            Structure computerStructure = getComputerStructure();
//            Structure deviceStructure = getDeviceStructure();
//            Structure officeStructure = getOfficeStructure(deviceStructure, computerStructure);
//
//            TypeCheckMap computer = new TypeCheckMap();
//            computer.put("ip", "192.0.2.101");
//            computer.put("mac", "111111111111");
//
//            TypeCheckMap device1 = new TypeCheckMap();
//            device1.put("ip", "192.0.2.211");
//            device1.put("mac", "111111111111");
//            device1.put("generation", "B+v1.2");
//            device1.put("type", "SensorDevice");
//
//            TypeCheckMap device2 = new TypeCheckMap();
//            device2.put("ip", "192.0.2.212");
//            device2.put("mac", "111111111112");
//            device2.put("generation", "Bv1.1");
//            device2.put("type", "LightDevice");
//
//
//            TypeCheckMap savedComputer = itemService.createItem(computerStructure.getId(), computer);
//            TypeCheckMap savedDevice1 = itemService.createItem(deviceStructure.getId(), device1);
//            TypeCheckMap savedDevice2 = itemService.createItem(deviceStructure.getId(), device2);
//
//            TypeCheckMap office = new TypeCheckMap();
//            office.put("partNumber", "123456789");
//            office.put("computer", savedComputer);
//            office.put("device1", savedDevice1);
//            office.put("device2", savedDevice2);
//
//            TypeCheckMap savedOffice = itemService.createItem(officeStructure.getId(), office);
//
//            try {
//
//                Thread.sleep(1000);// give time for ES to flush the new item
//
//                // will throw since it has a reference for it somewhere.
//                itemService.delete(computerStructure.getId(), savedComputer.getString("id"));
//
//            } catch (Exception e) {
//                throw e;
//            } finally {
//                itemService.delete(officeStructure.getId(), savedOffice.getString("id"));
//                itemService.delete(computerStructure.getId(), savedComputer.getString("id"));
//                itemService.delete(deviceStructure.getId(), savedDevice1.getString("id"));
//                itemService.delete(deviceStructure.getId(), savedDevice2.getString("id"));
//                Thread.sleep(1000);
//                structureService.delete(computerStructure.getId());
//                structureService.delete(deviceStructure.getId());
//                structureService.delete(officeStructure.getId());
//            }
//        });
//    }
//
//    @Test
//    public void createItemThatAlsoHasObjectReferences_TryUseSameTypeInReference_CleanUp() throws Exception {
//        Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            Structure computerStructure = getComputerStructure();
//            Structure deviceStructure = getDeviceStructure();
//            Structure officeStructure = getOfficeStructure(deviceStructure, computerStructure);
//
//            TypeCheckMap computer = new TypeCheckMap();
//            computer.put("ip", "192.0.2.101");
//            computer.put("mac", "111111111111");
//
//            TypeCheckMap device1 = new TypeCheckMap();
//            device1.put("ip", "192.0.2.211");
//            device1.put("mac", "111111111111");
//            device1.put("generation", "B+v1.2");
//            device1.put("type", "SensorDevice");
//
//            TypeCheckMap device2 = new TypeCheckMap();
//            device2.put("ip", "192.0.2.212");
//            device2.put("mac", "111111111112");
//            device2.put("generation", "Bv1.1");
//            device2.put("type", "LightDevice");
//
//            TypeCheckMap savedComputer = itemService.createItem(computerStructure.getId(), computer);
//            TypeCheckMap savedDevice1 = itemService.createItem(deviceStructure.getId(), device1);
//            TypeCheckMap savedDevice2 = itemService.createItem(deviceStructure.getId(), device2);
//
//            TypeCheckMap office = new TypeCheckMap();
//            office.put("partNumber", "123456789");
//            office.put("computer", savedComputer); // put a Device in a Computer position
//            office.put("device1", savedDevice1); // put a Computer in a Device position
//            office.put("device2", savedDevice2);
//
//            // should throw up b/c of the type mismatch
//            TypeCheckMap savedOffice = itemService.createItem(officeStructure.getId(), office);
//
//            try {
//
//                Thread.sleep(1000);// give time for ES to flush the new item
//
//                TypeCheckMap office2 = new TypeCheckMap();
//                office2.put("partNumber", "123456789");
//                office2.put("computer", device1); // put a Device in a Computer position
//                office2.put("device1", savedComputer); // put a Computer in a Device position
//                office2.put("device2", device2);
//
//                // should throw up b/c of the type mismatch
//                TypeCheckMap throwUp = itemService.createItem(officeStructure.getId(), office2);
//
//            } catch (Exception e) {
//                throw e;
//            } finally {
//                itemService.delete(officeStructure.getId(), savedOffice.getString("id"));
//                Thread.sleep(1000);
//                itemService.delete(computerStructure.getId(), savedComputer.getString("id"));
//                itemService.delete(deviceStructure.getId(), savedDevice1.getString("id"));
//                itemService.delete(deviceStructure.getId(), savedDevice2.getString("id"));
//                Thread.sleep(1000);
//                structureService.delete(computerStructure.getId());
//                structureService.delete(deviceStructure.getId());
//                structureService.delete(officeStructure.getId());
//            }
//        });
//
//    }
//
//    @Test
//    public void createItemThatAlsoHasObjectReferences_UseInvalidReferenceTypesForField_CleanUp() throws Exception {
//        Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            Structure computerStructure = getComputerStructure();
//            Structure deviceStructure = getDeviceStructure();
//            Structure officeStructure = getOfficeStructure(deviceStructure, computerStructure);
//
//            TypeCheckMap computer = new TypeCheckMap();
//            computer.put("ip", "192.0.2.101");
//            computer.put("mac", "111111111111");
//
//            TypeCheckMap device1 = new TypeCheckMap();
//            device1.put("ip", "192.0.2.211");
//            device1.put("mac", "111111111111");
//            device1.put("generation", "B+v1.2");
//            device1.put("type", "SensorDevice");
//
//            TypeCheckMap device2 = new TypeCheckMap();
//            device2.put("ip", "192.0.2.212");
//            device2.put("mac", "111111111112");
//            device2.put("generation", "Bv1.1");
//            device2.put("type", "LightDevice");
//
//
//            TypeCheckMap savedComputer = itemService.createItem(computerStructure.getId(), computer);
//            TypeCheckMap savedDevice1 = itemService.createItem(deviceStructure.getId(), device1);
//            TypeCheckMap savedDevice2 = itemService.createItem(deviceStructure.getId(), device2);
//
//            TypeCheckMap office = new TypeCheckMap();
//            office.put("partNumber", "123456789");
//            office.put("computer", savedDevice1); // put a Device in a Computer position
//            office.put("device1", savedComputer); // put a Computer in a Device position
//            office.put("device2", savedDevice2);
//
//
//            try {
//
//                Thread.sleep(1000);// give time for ES to flush the new item
//
//                // should throw up b/c of the type mismatch
//                TypeCheckMap savedOffice = itemService.createItem(officeStructure.getId(), office);
//
//            } catch (Exception e) {
//                throw e;
//            } finally {
//                itemService.delete(computerStructure.getId(), savedComputer.getString("id"));
//                itemService.delete(deviceStructure.getId(), savedDevice1.getString("id"));
//                itemService.delete(deviceStructure.getId(), savedDevice2.getString("id"));
//                Thread.sleep(1000);
//                structureService.delete(computerStructure.getId());
//                structureService.delete(deviceStructure.getId());
//                structureService.delete(officeStructure.getId());
//            }
//        });
//    }
//

}
