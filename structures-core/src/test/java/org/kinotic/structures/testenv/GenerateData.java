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

package org.kinotic.structures.testenv;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kinotic.structures.api.domain.AlreadyExistsException;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.Trait;
import org.kinotic.structures.api.services.ItemService;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.api.services.TraitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GenerateData {

    @Autowired
    private ItemService itemService;
    @Autowired
    private TraitService traitService;
    @Autowired
    private StructureService structureService;
    
    @Test
    public void createData() {

//		Structure computerStructure = getComputerStructure()
//		Structure deviceStructure = getDeviceStructure()
//		Structure officeStructure = getOfficeStructure()
//
//		TypeCheckMap computer = new TypeCheckMap()
//		computer.put("ip","192.0.2.101")
//		computer.put("mac","111111111111")
//
//		TypeCheckMap device1 = new TypeCheckMap()
//		device1.put("ip","192.0.2.211")
//		device1.put("mac","111111111111")
//		device1.put("generation","B+v1.2")
//		device1.put("type","SensorDevice")
//
//		TypeCheckMap device2 = new TypeCheckMap()
//		device2.put("ip","192.0.2.212")
//		device2.put("mac","111111111112")
//		device2.put("generation","Bv1.1")
//		device1.put("type","LightDevice")
//
//
//		TypeCheckMap savedComputer = itemService.createItem(computerStructure.id, computer)
//		TypeCheckMap savedDevice1 = itemService.createItem(deviceStructure.id, device1)
//		TypeCheckMap savedDevice2 = itemService.createItem(deviceStructure.id, device2)
//
//		TypeCheckMap office = new TypeCheckMap()
//		office.put("partNumber","123456789")
//		office.put("computer",savedComputer)
//		office.put("device1",savedDevice1)
//		office.put("device2",savedDevice2)
//
//		TypeCheckMap savedOffice = itemService.createItem(officeStructure.id, office)

    }

    public Structure getComputerStructure() throws AlreadyExistsException, IOException {
        Structure structure = new Structure();
        structure.setId("Computer-" + System.currentTimeMillis());
        structure.setDescription("Defines an Computer");
        structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));

        Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
        Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
        Optional<Trait> macOptional = traitService.getTraitByName("Mac");

        structure.getTraits().put("vpnIp", vpnIpOptional.get());
        structure.getTraits().put("ip", ipOptional.get());
        structure.getTraits().put("mac", macOptional.get());

        structureService.save(structure);
        structureService.publish(structure.getId());
        return structure;
    }

    public Structure getDeviceStructure() throws AlreadyExistsException, IOException {
        Structure structure = new Structure();
        structure.setId("Device-" + System.currentTimeMillis());
        structure.setDescription("Defines an Device");
        structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));

        Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
        Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
        Optional<Trait> macOptional = traitService.getTraitByName("Mac");
        Optional<Trait> textOptional = traitService.getTraitByName("KeywordString");

        structure.getTraits().put("vpnIp", vpnIpOptional.get());
        structure.getTraits().put("ip", ipOptional.get());
        structure.getTraits().put("mac", macOptional.get());
        structure.getTraits().put("generation", textOptional.get());
        structure.getTraits().put("type", textOptional.get());

        structureService.save(structure);
        structureService.publish(structure.getId());
        return structure;
    }

    public Structure getOfficeStructure() throws AlreadyExistsException, IOException {
        Structure structure = new Structure();
        structure.setId("Office-" + System.currentTimeMillis());
        structure.setDescription("Defines a Office");
        structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));

        Optional<Trait> textOptional = traitService.getTraitByName("KeywordString");
        Optional<Trait> objRefOptional = traitService.getTraitByName("ObjectReference");

        structure.getTraits().put("partNumber", textOptional.get());
        structure.getTraits().put("computer", objRefOptional.get());
        structure.getTraits().put("device1", objRefOptional.get());
        structure.getTraits().put("device2", objRefOptional.get());

        structureService.save(structure);
        structureService.publish(structure.getId());
        return structure;
    }
}
