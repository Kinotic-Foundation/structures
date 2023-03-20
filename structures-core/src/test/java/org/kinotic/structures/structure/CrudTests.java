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

import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kinotic.structures.api.domain.*;
import org.kinotic.structures.api.services.ItemService;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.api.services.TraitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CrudTests {

    @Autowired
    private TraitService traitService;
    @Autowired
    private StructureService structureService;
    @Autowired
    private ItemService itemService;

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
	public void createAndDeleteStructure() {
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			Structure structure = new Structure();
			structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));
			structure.setId("Computer1-" + System.currentTimeMillis());
			structure.setDescription("Defines the Computer Device properties");


			Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
			Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
			Optional<Trait> macOptional = traitService.getTraitByName("Mac");

			structure.getTraits().put("vpnIp", vpnIpOptional.get());
			structure.getTraits().put("ip", ipOptional.get());
			structure.getTraits().put("mac", macOptional.get());
			// should also get createdTime, updateTime, and deleted by default

			final Structure saved = structureService.save(structure);

			try {
				if (saved.getTraits().size() != 9) {
					throw new IllegalStateException("We should have 9 traits, 6 given by default. We have " + saved.getTraits()
																												   .size());
				}

			} catch (Exception e) {
				throw e;
			} finally {
				structureService.delete(structure.getId());
			}


			SearchHits all = structureService.getAll(10000, 0, "id", true);
			if (all.iterator().hasNext()) {
				throw new IllegalStateException("We should have no structures left, all deleted, however getAll() returned more than 0 structures");
			}


			Optional<Structure> optional = structureService.getStructureById(structure.getId());
			optional.get();// should throw if null
		});
	}

	@Test
	public void tryCreateDuplicateStructure(){
		Assertions.assertThrows(AlreadyExistsException.class, () -> {
			Structure structure = new Structure();
			structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));
			structure.setId("Computer2-" + System.currentTimeMillis());
			structure.setDescription("Defines the Computer Device properties");


			Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
			Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
			Optional<Trait> macOptional = traitService.getTraitByName("Mac");

			structure.getTraits().put("vpnIp", vpnIpOptional.get());
			structure.getTraits().put("ip", ipOptional.get());
			structure.getTraits().put("mac", macOptional.get());
			// should also get createdTime, updateTime, and deleted by default

			structureService.save(structure);
			try {
				structure.setCreated(0);
				structure.setUpdated(0L);
				structureService.save(structure);
			} catch (AlreadyExistsException aee) {
				throw aee;
			} finally {
				structureService.delete(structure.getId());
			}
		});
	}

	@Test
	public void addToTraitMapNotPublishedAndValidate() throws AlreadyExistsException, IOException, PermenentTraitException {
		Structure structure = new Structure();
		structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));
		structure.setId("Computer3-" + System.currentTimeMillis());
		structure.setDescription("Defines the Computer Device properties");


		Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
		Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
		Optional<Trait> macOptional = traitService.getTraitByName("Mac");

		structure.getTraits().put("vpnIp", vpnIpOptional.get());
		structure.getTraits().put("ip", ipOptional.get());
		// should also get createdTime, updateTime, and deleted by default

		structureService.save(structure);

		try {

			structureService.addTraitToStructure(structure.getId(), "mac", macOptional.get());

			Optional<Structure> optional = structureService.getStructureById(structure.getId());
			Structure saved = optional.get();
			int index = 0;
			for (Map.Entry<String, Trait> traitEntry : saved.getTraits().entrySet()) {
				if (index == 0 && !traitEntry.getKey().equals("vpnIp")) {
					throw new IllegalStateException("Order of Trait Map not what it should be, index 0 should be \'vpnIp\' but got \'" + traitEntry.getKey());
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
				} else if (index == 8 && !traitEntry.getKey().equals("mac")) {
					throw new IllegalStateException("Order of Trait Map not what it should be, index 8 should be \'mac\' but got \'" + traitEntry.getKey());
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
	public void addToTraitMapAlreadyPublishedAndValidate() throws AlreadyExistsException, IOException, PermenentTraitException {
		Structure structure = new Structure();
		structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));
		structure.setId("Computer4-" + System.currentTimeMillis());
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
			for (Map.Entry<String, Trait> traitEntry : saved.getTraits().entrySet()) {
				if (index == 0 && !traitEntry.getKey().equals("vpnIp")) {
					throw new IllegalStateException("Order of Trait Map not what it should be, index 0 should be \'vpnIp\' but got \'" + traitEntry.getKey());
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
				} else if (index == 8 && !traitEntry.getKey().equals("mac")) {
					throw new IllegalStateException("Order of Trait Map not what it should be, index 8 should be \'mac\' but got \'" + traitEntry.getKey());
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
	public void publishAndDeleteAStructure() throws AlreadyExistsException, IOException, PermenentTraitException {
		Structure structure = new Structure();
		structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));
		structure.setId("Computer9-" + System.currentTimeMillis());
		structure.setDescription("Defines the Computer Device properties");


		Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
		Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
		Optional<Trait> macOptional = traitService.getTraitByName("Mac");

		structure.getTraits().put("vpnIp", vpnIpOptional.get());
		structure.getTraits().put("ip", ipOptional.get());
		structure.getTraits().put("mac", macOptional.get());
		// should also get createdTime, updateTime, and deleted by default

		structureService.save(structure);

		structureService.publish(structure.getId());

		structureService.delete(structure.getId());
	}

	@Test
	public void publishAndDeleteAStructureWithAnItem() {
		Assertions.assertThrows(IllegalStateException.class, () -> {
			Structure structure = new Structure();
			structure.setPrimaryKey(new LinkedList<String>(Collections.singleton("id")));
			structure.setId("Computer10-" + System.currentTimeMillis());
			structure.setDescription("Defines the Computer Device properties");


			Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
			Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
			Optional<Trait> macOptional = traitService.getTraitByName("Mac");

			structure.getTraits().put("vpnIp", vpnIpOptional.get());
			structure.getTraits().put("ip", ipOptional.get());
			structure.getTraits().put("mac", macOptional.get());
			// should also get createdTime, updateTime, and deleted by default

			structureService.save(structure);
			structureService.publish(structure.getId());

			// now we can create an item with the above fields
			TypeCheckMap obj = new TypeCheckMap();
			obj.put("ip", "192.0.2.11");
			obj.put("mac", "000000000001");
			TypeCheckMap saved = null;

			try {
				saved = itemService.upsertItem(structure.getId(), obj);

				Thread.sleep(1000);// give time for ES to flush the new item

				// should throw an exception if there is items created for the structure
				// cannot delete something that has data out there.
				structureService.delete(structure.getId());
			} catch (IllegalStateException ise) {
				throw ise;
			} finally {
				// now delete the item so we can delete the structure.
				if (saved != null) {
					itemService.delete(structure.getId(), saved.getString("id"));
				}

				Thread.sleep(1000);// give time for ES to flush the new item
				structureService.delete(structure.getId());
			}
		});

	}
}
