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

package org.kinotic.structures.structure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kinotic.structures.api.domain.AlreadyExistsException;
import org.kinotic.structures.api.domain.PermenentTraitException;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.Trait;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.api.services.TraitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TraitReorderTests {

    @Autowired
    private TraitService traitService;
    @Autowired
    private StructureService structureService;

    @BeforeEach
    public void init() throws IOException, PermenentTraitException, AlreadyExistsException {
        Optional<Trait> ipOptional = traitService.getTraitByName("VpnIp");
        if(ipOptional.isEmpty()){
            Trait temp = new Trait();
            temp.setName("VpnIp");
            temp.setDescribeTrait("VpnIp address that the devices should be provided on the VLAN.");
            temp.setSchema("{ \"type\": \"string\", \"format\": \"ipv4\" }");
            temp.setEsSchema("{ \"type\": \"ip\" }");
            temp.setRequired(true);
            traitService.save(temp);
        }
    }

    @Test
    public void addToTraitAndMoveBeforeFirst() throws AlreadyExistsException, IOException, PermenentTraitException {
        Structure structure = new Structure();
        structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));
        structure.setId("Computer5-" + System.currentTimeMillis());
        structure.setDescription("Defines the Computer Device properties");


        Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
        Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
        Optional<Trait> macOptional = traitService.getTraitByName("Mac");

        structure.getTraits().put("vpnIp", vpnIpOptional.get());
        structure.getTraits().put("ip", ipOptional.get());
        // should also get createdTime, updateTime, and deleted by default

        structure = structureService.save(structure);

        try {

            structureService.publish(structure.getId());

            structureService.addTraitToStructure(structure.getId(), "mac", macOptional.get());

            Optional<Structure> optional = structureService.getStructureById(structure.getId());
            Structure saved = optional.get();
            int index = 0;
            String insertFirstId = "";
            String movingTraitId = "";
            for (Map.Entry<String, Trait> traitEntry : saved.getTraits().entrySet()) {
                if (index == 0) {
                    insertFirstId = traitEntry.getKey();
                    if (!traitEntry.getKey().equals("vpnIp")) {
                        throw new IllegalStateException("Order of Trait Map not what it should be, index 0 should be \'vpnIp\' but got \'" + traitEntry.getKey());
                    }

                } else if (index == 1 && !traitEntry.getKey().equals("ip")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 1 should be \'ip\' but got \'" + traitEntry.getKey());
                } else if (index == 2 && !traitEntry.getKey().equals("id")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 2 should be \'id\' but got \'" + traitEntry.getKey());
                } else if (index == 3 && !traitEntry.getKey().equals("deleted")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 3 should be \'deleted\' but got \'" + traitEntry.getKey());
                } else if (index == 4 && !traitEntry.getKey().equals("deletedTime")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 4 should be \'deletedTime\' but got \'" + traitEntry.getKey());
                } else if (index == 5 && !traitEntry.getKey().equals("createdTime")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 5 should be \'createdTime\' but got \'" + traitEntry.getKey());
                } else if (index == 6 && !traitEntry.getKey().equals("updatedTime")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 6 should be \'updatedTime\' but got \'" + traitEntry.getKey());
                } else if (index == 7 && !traitEntry.getKey().equals("structureId")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 7 should be \'structureId\' but got \'" + traitEntry.getKey());
                } else if (index == 8) {
                    movingTraitId = traitEntry.getKey();
                    if (!traitEntry.getKey().equals("mac")) {
                        throw new IllegalStateException("Order of Trait Map not what it should be, index 8 should be \'mac\' but got \'" + traitEntry.getKey());
                    }

                }

                index++;
            }


            structureService.insertTraitBeforeAnotherForStructure(structure.getId(), movingTraitId, insertFirstId);

            saved = structureService.getStructureById(structure.getId()).get();
            index = 0;
            for (Map.Entry<String, Trait> traitEntry : saved.getTraits().entrySet()) {
                if (index == 0 && !traitEntry.getKey().equals("mac")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 0 should be \'mac\' but got \'" + traitEntry.getKey());
                } else if (index == 1 && !traitEntry.getKey().equals("vpnIp")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 1 should be \'vpnIp\' but got \'" + traitEntry.getKey());
                } else if (index == 2 && !traitEntry.getKey().equals("ip")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 2 should be \'ip\' but got \'" + traitEntry.getKey());
                } else if (index == 3 && !traitEntry.getKey().equals("id")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 3 should be \'id\' but got \'" + traitEntry.getKey());
                } else if (index == 4 && !traitEntry.getKey().equals("deleted")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 4 should be \'deleted\' but got \'" + traitEntry.getKey());
                } else if (index == 5 && !traitEntry.getKey().equals("deletedTime")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 5 should be \'deletedTime\' but got \'" + traitEntry.getKey());
                } else if (index == 6 && !traitEntry.getKey().equals("createdTime")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 6 should be \'createdTime\' but got \'" + traitEntry.getKey());
                } else if (index == 7 && !traitEntry.getKey().equals("updatedTime")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 7 should be \'updatedTime\' but got \'" + traitEntry.getKey());
                } else if (index == 8 && !traitEntry.getKey().equals("structureId")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 8 should be \'structureId\' but got \'" + traitEntry.getKey());
                }

                index++;
            }

        } catch (Exception e) {
            throw e;
        } finally {
            structureService.delete(structure.getId());
        }

    }

    @Test
    public void addToTraitAndMoveAfterLast() throws AlreadyExistsException, IOException, PermenentTraitException {
        Structure structure = new Structure();
        structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));
        structure.setId("Computer6-" + System.currentTimeMillis());
        structure.setDescription("Defines the Computer Device properties");


        Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
        Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
        Optional<Trait> macOptional = traitService.getTraitByName("Mac");

        structure.getTraits().put("vpnIp", vpnIpOptional.get());
        structure.getTraits().put("ip", ipOptional.get());
        // should also get createdTime, updateTime, and deleted by default

        structureService.save(structure);

        try {

            structureService.publish(structure.getId());

            structureService.addTraitToStructure(structure.getId(), "mac", macOptional.get());

            Optional<Structure> optional = structureService.getStructureById(structure.getId());
            Structure saved = optional.get();
            int index = 0;
            String insertLastId = "";
            String movingTraitId = "";
            for (Map.Entry<String, Trait> traitEntry : saved.getTraits().entrySet()) {
                if (index == 0) {
                    movingTraitId = traitEntry.getKey();
                    if (!traitEntry.getKey().equals("vpnIp")) {
                        throw new IllegalStateException("Order of Trait Map not what it should be, index 0 should be \'vpnIp\' but got \'" + traitEntry.getKey());
                    }

                } else if (index == 1 && !traitEntry.getKey().equals("ip")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 1 should be \'ip\' but got \'" + traitEntry.getKey());
                } else if (index == 2 && !traitEntry.getKey().equals("id")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 2 should be \'id\' but got \'" + traitEntry.getKey());
                } else if (index == 3 && !traitEntry.getKey().equals("deleted")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 3 should be \'deleted\' but got \'" + traitEntry.getKey());
                } else if (index == 4 && !traitEntry.getKey().equals("deletedTime")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 4 should be \'deletedTime\' but got \'" + traitEntry.getKey());
                } else if (index == 5 && !traitEntry.getKey().equals("createdTime")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 5 should be \'createdTime\' but got \'" + traitEntry.getKey());
                } else if (index == 6 && !traitEntry.getKey().equals("updatedTime")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 6 should be \'updatedTime\' but got \'" + traitEntry.getKey());
                } else if (index == 7 && !traitEntry.getKey().equals("structureId")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 7 should be \'structureId\' but got \'" + traitEntry.getKey());
                } else if (index == 8) {
                    insertLastId = traitEntry.getKey();
                    if (!traitEntry.getKey().equals("mac")) {
                        throw new IllegalStateException("Order of Trait Map not what it should be, index 8 should be \'mac\' but got \'" + traitEntry.getKey());
                    }

                }

                index++;
            }


            structureService.insertTraitAfterAnotherForStructure(structure.getId(), movingTraitId, insertLastId);

            saved = structureService.getStructureById(structure.getId()).get();
            index = 0;
            for (Map.Entry<String, Trait> traitEntry : saved.getTraits().entrySet()) {
                if (index == 0 && !traitEntry.getKey().equals("ip")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 0 should be \'ip\' but got \'" + traitEntry.getKey());
                } else if (index == 1 && !traitEntry.getKey().equals("id")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 1 should be \'id\' but got \'" + traitEntry.getKey());
                } else if (index == 2 && !traitEntry.getKey().equals("deleted")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 2 should be \'deleted\' but got \'" + traitEntry.getKey());
                } else if (index == 3 && !traitEntry.getKey().equals("deletedTime")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 3 should be \'deletedTime\' but got \'" + traitEntry.getKey());
                } else if (index == 4 && !traitEntry.getKey().equals("createdTime")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 4 should be \'createdTime\' but got \'" + traitEntry.getKey());
                } else if (index == 5 && !traitEntry.getKey().equals("updatedTime")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 5 should be \'updatedTime\' but got \'" + traitEntry.getKey());
                } else if (index == 6 && !traitEntry.getKey().equals("structureId")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 6 should be \'structureId\' but got \'" + traitEntry.getKey());
                } else if (index == 7 && !traitEntry.getKey().equals("mac")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 7 should be \'mac\' but got \'" + traitEntry.getKey());
                } else if (index == 8 && !traitEntry.getKey().equals("vpnIp")) {
                    throw new IllegalStateException("Order of Trait Map not what it should be, index 8 should be \'vpnIp\' but got \'" + traitEntry.getKey());
                }

                index++;
            }

        } catch (Exception e) {
            throw e;
        } finally {
            structureService.delete(structure.getId());
        }

    }
}
