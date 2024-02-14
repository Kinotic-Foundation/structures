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
import org.kinotic.structures.ElasticsearchTestBase;
import org.kinotic.structures.api.decorators.MultiTenancyType;
import org.kinotic.structures.api.domain.DefaultEntityContext;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.EntitiesService;
import org.kinotic.structures.api.services.StructureService;
import org.kinotic.structures.internal.sample.DummyParticipant;
import org.kinotic.structures.internal.sample.TestDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.CompletableFuture;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StructureCrudTests extends ElasticsearchTestBase {

	@Autowired
	private StructureService structureService;
	@Autowired
	private TestDataService testDataService;
	@Autowired
	private EntitiesService entitiesService;

	@Test
	public void createPublishAndDeleteStructure() throws Exception {

		Structure structure = new Structure();
		structure.setName("PersonWat")
				 .setNamespace("org.kinotic.sample")
				 .setDescription("Defines a Person")
				 .setEntityDefinition(testDataService.createPersonSchema(MultiTenancyType.NONE, false));

		CompletableFuture<Structure> future = structureService.create(structure);

		StepVerifier.create(Mono.fromFuture(future))
					.expectNextMatches(savedStructure -> {
						Assertions.assertNotNull(savedStructure.getId());
						Assertions.assertNotNull(savedStructure.getCreated());
						Assertions.assertNotNull(savedStructure.getUpdated());
						Assertions.assertEquals(structure.getName(), savedStructure.getName());
						Assertions.assertEquals(structure.getDescription(), savedStructure.getDescription());
						Assertions.assertEquals(structure.getEntityDefinition(), savedStructure.getEntityDefinition());
						return true;
					})
					.expectComplete()
					.verify();

		CompletableFuture<Void> pubFuture = structureService.publish(future.get().getId());

		StepVerifier.create(Mono.fromFuture(pubFuture))
					.expectComplete()
					.verify();

		CompletableFuture<Void> unPubFuture = structureService.unPublish(future.get().getId());

		StepVerifier.create(Mono.fromFuture(unPubFuture))
					.expectComplete()
					.verify();

		CompletableFuture<Void> delFuture = structureService.deleteById(future.get().getId());

		StepVerifier.create(Mono.fromFuture(delFuture))
					.expectComplete()
					.verify();
	}

	@Test
	public void tryOperationsOnPublishedStructure() throws Exception{
		Structure structure = new Structure();
		structure.setName("PersonBum")
				 .setNamespace("org.kinotic.sample")
				 .setDescription("Defines a Person")
				 .setEntityDefinition(testDataService.createPersonSchema(MultiTenancyType.NONE, false));

		CompletableFuture<Structure> future = structureService.create(structure);

		StepVerifier.create(Mono.fromFuture(future))
					.expectNextMatches(savedStructure -> {
						Assertions.assertNotNull(savedStructure.getId());
						Assertions.assertNotNull(savedStructure.getCreated());
						Assertions.assertNotNull(savedStructure.getUpdated());
						Assertions.assertEquals(structure.getName(), savedStructure.getName());
						Assertions.assertEquals(structure.getDescription(), savedStructure.getDescription());
						Assertions.assertEquals(structure.getEntityDefinition(), savedStructure.getEntityDefinition());
						return true;
					})
					.expectComplete()
					.verify();

		CompletableFuture<Void> pubFuture = structureService.publish(future.get().getId());

		StepVerifier.create(Mono.fromFuture(pubFuture))
					.expectComplete()
					.verify();

		CompletableFuture<Void> delFuture = structureService.deleteById(future.get().getId());

		StepVerifier.create(Mono.fromFuture(delFuture))
					.expectError(IllegalStateException.class)
					.verify();

		// TODO: add rename name and namespace operations
	}

	@Test
	public void createStructureInvalidField(){
		Structure structure = new Structure();
		structure.setName("PersonStupid")
				 .setNamespace("org.kinotic.sample")
				 .setDescription("Defines a Person")
				 .setEntityDefinition(testDataService.createPersonSchema(MultiTenancyType.NONE, true));

		CompletableFuture<Structure> future = structureService.create(structure);

		StepVerifier.create(Mono.fromFuture(future))
					.expectError(IllegalArgumentException.class)
					.verify();
	}

	@Test
	public void createStructureWithSameNameError() throws Exception {
		Structure structure = new Structure();
		structure.setName("PersonHomer")
				 .setNamespace("org.kinotic.sample")
				 .setDescription("Defines a Person")
				 .setEntityDefinition(testDataService.createPersonSchema(MultiTenancyType.NONE, false));

		CompletableFuture<Structure> future = structureService.create(structure);

		StepVerifier.create(Mono.fromFuture(future))
					.expectNextMatches(savedStructure -> {
						Assertions.assertNotNull(savedStructure.getId());
						Assertions.assertNotNull(savedStructure.getCreated());
						Assertions.assertNotNull(savedStructure.getUpdated());
						Assertions.assertEquals(structure.getName(), savedStructure.getName());
						Assertions.assertEquals(structure.getDescription(), savedStructure.getDescription());
						Assertions.assertEquals(structure.getEntityDefinition(), savedStructure.getEntityDefinition());
						return true;
					})
					.expectComplete()
					.verify();

		CompletableFuture<Structure> future2 = structureService.create(structure);

		StepVerifier.create(Mono.fromFuture(future2))
					.expectError(IllegalArgumentException.class)
					.verify();

		CompletableFuture<Void> delFuture = structureService.deleteById(future.get().getId());

		StepVerifier.create(Mono.fromFuture(delFuture))
					.expectComplete()
					.verify();
	}

	@Test
	public void tryOperationOnNotPublishedStructure() throws Exception {
		Structure structure = new Structure();
		structure.setName("PersonStoned")
				 .setNamespace("org.kinotic.sample")
				 .setDescription("Defines a Person")
				 .setEntityDefinition(testDataService.createPersonSchema(MultiTenancyType.NONE, false));

		CompletableFuture<Structure> future = structureService.create(structure);

		StepVerifier.create(Mono.fromFuture(future))
					.expectNextMatches(savedStructure -> {
						Assertions.assertNotNull(savedStructure.getId());
						Assertions.assertNotNull(savedStructure.getCreated());
						Assertions.assertNotNull(savedStructure.getUpdated());
						Assertions.assertEquals(structure.getName(), savedStructure.getName());
						Assertions.assertEquals(structure.getDescription(), savedStructure.getDescription());
						Assertions.assertEquals(structure.getEntityDefinition(), savedStructure.getEntityDefinition());
						return true;
					})
					.expectComplete()
					.verify();

		CompletableFuture<Long> countFuture = entitiesService.count(future.get().getId(), new DefaultEntityContext(new DummyParticipant()));

		StepVerifier.create(Mono.fromFuture(countFuture))
					.expectError(IllegalArgumentException.class)
					.verify();

		CompletableFuture<Void> delFuture = structureService.deleteById(future.get().getId());

		StepVerifier.create(Mono.fromFuture(delFuture))
					.expectComplete()
					.verify();
	}
}
