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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kinotic.continuum.idl.api.ArrayC3Type;
import org.kinotic.continuum.idl.api.ObjectC3Type;
import org.kinotic.continuum.idl.api.StringC3Type;
import org.kinotic.structures.ElasticsearchTestBase;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.StructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.CompletableFuture;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StructureCrudTests extends ElasticsearchTestBase {

	//    @Autowired
//    private TraitService traitService;
	@Autowired
	private StructureService structureService;
//    @Autowired
//    private ItemServiceInternal itemService;
//	@Autowired
//	private StructureTestHelper structureTestHelper;
//
//

	private ObjectC3Type buildTestItemDefinition(){
		return new ObjectC3Type()
				.addProperty("name", new StringC3Type())
				.addProperty("description", new StringC3Type())
				.addProperty("addresses", new ArrayC3Type(
						new ObjectC3Type()
								.addProperty("street", new StringC3Type())
								.addProperty("city", new StringC3Type())
								.addProperty("state", new StringC3Type())
								.addProperty("zip", new StringC3Type())
				));
	}


	@Test
	public void createAndDeleteStructure() {

		Structure structure = new Structure();
		structure.setName("Person")
				 .setNamespace("kinotic_")
				 .setDescription("Defines a Person")
				 .setItemDefinition(buildTestItemDefinition());

		CompletableFuture<Structure> future = structureService.save(structure);

		StepVerifier.create(Mono.fromFuture(future))
					.expectNextMatches(savedStructure -> {
						Assertions.assertNotNull(savedStructure.getId());
						Assertions.assertTrue(savedStructure.getCreated() > 0);
						Assertions.assertTrue(savedStructure.getUpdated() > 0);
						Assertions.assertEquals(structure.getName(), savedStructure.getName());
						Assertions.assertEquals(structure.getDescription(), savedStructure.getDescription());
						Assertions.assertEquals(structure.getItemDefinition(), savedStructure.getItemDefinition());
						return true;
					})
					.expectComplete()
					.verify();


	}
//
//	@Test
//	public void tryCreateDuplicateStructure(){
//		Assertions.assertThrows(AlreadyExistsException.class, () -> {
//			Structure structure = new Structure();
//			structure.setName("Computer2-" + System.currentTimeMillis());
//			structure.setNamespace("some_other_org_");
//			structure.setDescription("Defines the Computer Device properties");
//
//
//			Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
//			Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
//			Optional<Trait> macOptional = traitService.getTraitByName("Mac");
//
//			structure.getTraits().put("vpnIp", vpnIpOptional.get());
//			structure.getTraits().put("ip", ipOptional.get());
//			structure.getTraits().put("mac", macOptional.get());
//			// should also get createdTime, updateTime, and deleted by default
//
//			structure = structureService.save(structure);
//			String structureId = structure.getId(); // save for later deletion
//			try {
//				structure.setId("");
//				structure.setCreated(0);
//				structure.setUpdated(0L);
//				structureService.save(structure);
//			} catch (AlreadyExistsException aee) {
//				throw aee;
//			} finally {
//				structureService.delete(structureId);
//			}
//		});
//	}
//
//
//	@Test
//	public void tryCreateUpdateStructure_WithoutGettingFreshFromDb(){
//		Assertions.assertThrows(OptimisticLockingFailureException.class, () -> {
//			Structure structure = new Structure();
//			structure.setName("Computer42-" + System.currentTimeMillis());
//			structure.setNamespace("some_other_org_");
//			structure.setDescription("Defines the Computer Device properties");
//
//
//			Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
//			Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
//			Optional<Trait> macOptional = traitService.getTraitByName("Mac");
//
//			structure.getTraits().put("vpnIp", vpnIpOptional.get());
//			structure.getTraits().put("ip", ipOptional.get());
//			structure.getTraits().put("mac", macOptional.get());
//			// should also get createdTime, updateTime, and deleted by default
//
//			structure = structureService.save(structure);
//
//			Structure clone = new Structure();// break pass by reference chain
//			clone.setId(structure.getId());
//			clone.setDescription(structure.getDescription());
//			clone.setCreated(structure.getCreated());
//			clone.setPublished(structure.isPublished());
//			clone.setPublishedTimestamp(structure.getPublishedTimestamp());
//			clone.setName(structure.getName());
//			clone.setNamespace(structure.getNamespace());
//			clone.setTraits(structure.getTraits());
//			clone.setMetadata(structure.getMetadata());
//			clone.setUpdated(structure.getUpdated());
//
//			structure.setDescription("Some New Description");
//			structure = structureService.save(structure);// make sure our updated field has new value
//
//			Assertions.assertTrue(structure.getUpdated() > clone.getUpdated(), "Updated Structure did not get new updated time value");
//
//			try {
//				clone.setDescription("Should Fail because this object is out of sync with db");
//				structureService.save(clone);
//			} catch (OptimisticLockingFailureException olfe) {
//				throw olfe;
//			} finally {
//				structureService.delete(structure.getId());
//			}
//		});
//	}
//
//	@Test
//	public void addToTraitMapNotPublishedAndValidate() throws AlreadyExistsException, IOException, PermenentTraitException {
//		Structure structure = new Structure();
//		structure.setName("Computer3-" + System.currentTimeMillis());
//		structure.setNamespace("some_other_org_");
//		structure.setDescription("Defines the Computer Device properties");
//
//
//		Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
//		Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
//		Optional<Trait> macOptional = traitService.getTraitByName("Mac");
//
//		structure.getTraits().put("vpnIp", vpnIpOptional.get());
//		structure.getTraits().put("ip", ipOptional.get());
//		// should also get createdTime, updateTime, and deleted by default
//
//		structure = structureService.save(structure);
//
//		try {
//
//			structureService.addTraitToStructure(structure.getId(), "mac", macOptional.get());
//
//			Optional<Structure> optional = structureService.getById(structure.getId());
//			Structure saved = optional.get();
//			int index = 0;
//			for (Map.Entry<String, Trait> traitEntry : saved.getTraits().entrySet()) {
//				if (index == 0 && !traitEntry.getKey().equals("vpnIp")) {
//					throw new IllegalStateException("Order of Trait Map not what it should be, index 0 should be \'vpnIp\' but got \'" + traitEntry.getKey());
//				} else if (index == 1 && !traitEntry.getKey().equals("ip")) {
//					throw new IllegalStateException("Order of Trait Map not what it should be, index 1 should be \'ip\' but got \'" + traitEntry.getKey());
//				} else if (index == 2 && !traitEntry.getKey().equals("id")) {
//					throw new IllegalStateException("Order of Trait Map not what it should be, index 2 should be \'id\' but got \'" + traitEntry.getKey());
//				} else if (index == 3 && !traitEntry.getKey().equals("deleted")) {
//					throw new IllegalStateException("Order of Trait Map not what it should be, index 3 should be \'deleted\' but got \'" + traitEntry.getKey());
//				} else if (index == 4 && !traitEntry.getKey().equals("deletedTime")) {
//					throw new IllegalStateException("Order of Trait Map not what it should be, index 4 should be \'deletedTime\' but got \'" + traitEntry.getKey());
//				} else if (index == 5 && !traitEntry.getKey().equals("updatedTime")) {
//					throw new IllegalStateException("Order of Trait Map not what it should be, index 5 should be \'updatedTime\' but got \'" + traitEntry.getKey());
//				} else if (index == 6 && !traitEntry.getKey().equals("structureId")) {
//					throw new IllegalStateException("Order of Trait Map not what it should be, index 6 should be \'structureId\' but got \'" + traitEntry.getKey());
//				} else if (index == 7 && !traitEntry.getKey().equals("mac")) {
//					throw new IllegalStateException("Order of Trait Map not what it should be, index 7 should be \'mac\' but got \'" + traitEntry.getKey());
//				}
//
//				index++;
//			}
//
//
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			structureService.delete(structure.getId());
//		}
//
//	}
//
//	@Test
//	public void addToTraitMapAlreadyPublishedAndValidate() throws AlreadyExistsException, IOException, PermenentTraitException {
//		Structure structure = new Structure();
//		structure.setName("Computer4-" + System.currentTimeMillis());
//		structure.setNamespace("some_other_org_");
//		structure.setDescription("Defines the Computer Device properties");
//
//
//		Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
//		Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
//		Optional<Trait> macOptional = traitService.getTraitByName("Mac");
//
//		structure.getTraits().put("vpnIp", vpnIpOptional.get());
//		structure.getTraits().put("ip", ipOptional.get());
//		// should also get createdTime, updateTime, and deleted by default
//
//		structure = structureService.save(structure);
//
//		try {
//
//			structureService.publish(structure.getId());
//
//			structureService.addTraitToStructure(structure.getId(), "mac", macOptional.get());
//
//			Optional<Structure> optional = structureService.getById(structure.getId());
//			Structure saved = optional.get();
//			int index = 0;
//			for (Map.Entry<String, Trait> traitEntry : saved.getTraits().entrySet()) {
//				if (index == 0 && !traitEntry.getKey().equals("vpnIp")) {
//					throw new IllegalStateException("Order of Trait Map not what it should be, index 0 should be \'vpnIp\' but got \'" + traitEntry.getKey());
//				} else if (index == 1 && !traitEntry.getKey().equals("ip")) {
//					throw new IllegalStateException("Order of Trait Map not what it should be, index 1 should be \'ip\' but got \'" + traitEntry.getKey());
//				} else if (index == 2 && !traitEntry.getKey().equals("id")) {
//					throw new IllegalStateException("Order of Trait Map not what it should be, index 2 should be \'id\' but got \'" + traitEntry.getKey());
//				} else if (index == 3 && !traitEntry.getKey().equals("deleted")) {
//					throw new IllegalStateException("Order of Trait Map not what it should be, index 3 should be \'deleted\' but got \'" + traitEntry.getKey());
//				} else if (index == 4 && !traitEntry.getKey().equals("deletedTime")) {
//					throw new IllegalStateException("Order of Trait Map not what it should be, index 4 should be \'deletedTime\' but got \'" + traitEntry.getKey());
//				} else if (index == 5 && !traitEntry.getKey().equals("updatedTime")) {
//					throw new IllegalStateException("Order of Trait Map not what it should be, index 5 should be \'updatedTime\' but got \'" + traitEntry.getKey());
//				} else if (index == 6 && !traitEntry.getKey().equals("structureId")) {
//					throw new IllegalStateException("Order of Trait Map not what it should be, index 6 should be \'structureId\' but got \'" + traitEntry.getKey());
//				} else if (index == 7 && !traitEntry.getKey().equals("mac")) {
//					throw new IllegalStateException("Order of Trait Map not what it should be, index 7 should be \'mac\' but got \'" + traitEntry.getKey());
//				}
//
//				index++;
//			}
//
//
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			structureService.delete(structure.getId());
//		}
//
//	}
//
//	@Test
//	public void publishAndDeleteAStructure() throws AlreadyExistsException, IOException, PermenentTraitException {
//		Structure structure = new Structure();
//		structure.setName("Computer9-" + System.currentTimeMillis());
//		structure.setNamespace("some_other_org_");
//		structure.setDescription("Defines the Computer Device properties");
//
//
//		Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
//		Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
//		Optional<Trait> macOptional = traitService.getTraitByName("Mac");
//
//		structure.getTraits().put("vpnIp", vpnIpOptional.get());
//		structure.getTraits().put("ip", ipOptional.get());
//		structure.getTraits().put("mac", macOptional.get());
//		// should also get createdTime, updateTime, and deleted by default
//
//		structure = structureService.save(structure);
//
//		structureService.publish(structure.getId());
//
//		structureService.delete(structure.getId());
//	}
//
//	@Test
//	public void publishAndDeleteAStructureWithAnItem() {
//		Assertions.assertThrows(IllegalStateException.class, () -> {
//			Structure structure = new Structure();
//			structure.setName("Computer10-" + System.currentTimeMillis());
//			structure.setNamespace("some_other_org_");
//			structure.setDescription("Defines the Computer Device properties");
//
//
//			Optional<Trait> vpnIpOptional = traitService.getTraitByName("VpnIp");
//			Optional<Trait> ipOptional = traitService.getTraitByName("Ip");
//			Optional<Trait> macOptional = traitService.getTraitByName("Mac");
//
//			structure.getTraits().put("vpnIp", vpnIpOptional.get());
//			structure.getTraits().put("ip", ipOptional.get());
//			structure.getTraits().put("mac", macOptional.get());
//			// should also get createdTime, updateTime, and deleted by default
//
//			structure = structureService.save(structure);
//			structureService.publish(structure.getId());
//
//			// now we can create an item with the above fields
//			TypeCheckMap obj = new TypeCheckMap();
//			obj.put("ip", "192.0.2.11");
//			obj.put("mac", "000000000001");
//			TypeCheckMap saved = null;
//
//			try {
//				saved = itemService.upsertItem(structure.getId(), obj, null);
//
//				Thread.sleep(1000);// give time for ES to flush the new item
//
//				// should throw an exception if there is items created for the structure
//				// cannot delete something that has data out there.
//				structureService.delete(structure.getId());
//			} catch (IllegalStateException ise) {
//				throw ise;
//			} finally {
//				// now delete the item so we can delete the structure.
//				if (saved != null) {
//					itemService.delete(structure.getId(), saved.getString("id"), null);
//				}
//
//				Thread.sleep(1000);// give time for ES to flush the new item
//				structureService.delete(structure.getId());
//			}
//		});
//
//	}
//
//	@Test
//	public void publishManyNamespacesAndQueryForOneNamespace() throws IOException, AlreadyExistsException, PermenentTraitException {
//		Structure office1 = structureTestHelper.getOfficeStructure("org.kinotic.");
//		Structure computer = structureTestHelper.getComputerStructure("org.kinotic.");
//		Structure office2 = structureTestHelper.getOfficeStructure("some_name_space_");
//		Structure computer1 = structureTestHelper.getComputerStructure("some_name_space_");
//		Structure computer2 = structureTestHelper.getComputerStructure("some_name_space_");
//		Structure computer3 = structureTestHelper.getComputerStructure("some_name_space_");
//
//		try {
//
//			Structures query1 = structureService.getAllPublishedForNamespace("some_name_space_", 100, 0, "name", true);
//
//			Assertions.assertEquals(4, query1.getTotalElements(), "Structure Namespace query did not return expected results");
//
//			Structures query2 = structureService.getAllPublishedForNamespace("org.kinotic.", 100, 0, "name", true);
//
//			Assertions.assertEquals(2, query2.getTotalElements(), "Second Structure Namespace query did not return expected results");
//
//		} catch (IOException ioe) {
//			throw ioe;
//		} finally {
//			structureService.delete(office1.getId());
//			structureService.delete(computer.getId());
//			structureService.delete(office2.getId());
//			structureService.delete(computer1.getId());
//			structureService.delete(computer2.getId());
//			structureService.delete(computer3.getId());
//		}
//	}
//
//
//	@Test
//	public void publishAndUnpublishThenDeleteAStructure() throws Exception {
//		Structure structure = structureTestHelper.getSimpleItemStructure();
//
//
//		// now we can create an item with the above fields
//		TypeCheckMap obj = new TypeCheckMap();
//		obj.put("ip", "192.0.2.11");
//		obj.put("mac", "000000000001");
//
//		TypeCheckMap saved = itemService.upsertItem(structure.getId(), obj, null);
//		Assertions.assertNotNull(saved);
//
//		// unpublish - should remove everything and unpublish the structure
//		StructureHolder holder = structureService.unPublish(structure.getId());
//		Assertions.assertFalse(holder.getStructure().isPublished());
//
//		structureService.delete(structure.getId());
//	}
}
