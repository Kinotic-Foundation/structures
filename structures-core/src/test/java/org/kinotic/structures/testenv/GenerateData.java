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
import org.kinotic.structures.ElasticsearchTestBase;
import org.kinotic.structures.util.StructureTestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GenerateData extends ElasticsearchTestBase {

    @Autowired
    private StructureTestHelper structureTestHelper;
    
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

}
