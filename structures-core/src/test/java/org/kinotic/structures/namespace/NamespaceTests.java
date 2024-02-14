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

package org.kinotic.structures.namespace;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kinotic.structures.ElasticsearchTestBase;
import org.kinotic.structures.api.domain.Namespace;
import org.kinotic.structures.api.services.NamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.CompletableFuture;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NamespaceTests extends ElasticsearchTestBase {

	@Autowired
	private NamespaceService namespaceService;
//	@Autowired
//	private StructureService structureService;
//	@Autowired
//	private StructureTestHelper structureTestHelper;

	@Test
	public void createAndDeleteNamespace() {
		Namespace test = new Namespace();
		test.setId("Test");
		test.setDescription("Testing This Namespace");

		CompletableFuture<Namespace> future = namespaceService.save(test);

		StepVerifier.create(Mono.fromFuture(future))
					.expectNextMatches(namespace -> namespace.getId().equals("Test") && namespace.getUpdated() != null)
					.expectComplete()
					.verify();

		StepVerifier.create(Mono.fromFuture(namespaceService.deleteById(test.getId())))
					.expectComplete()
					.verify();

		StepVerifier.create(Mono.fromFuture(namespaceService.findById(test.getId())))
					.expectComplete()
					.verify();
	}

//	@Test
//	public void createAndTryDuplicateNamespace() {
//		Assertions.assertThrows(AlreadyExistsException.class, () -> {
//			Namespace test = new Namespace();
//			test.setName("TryClone");
//			test.setDescription("Testing This Namespace");
//			namespaceService.save(test);
//
//			try {
//				Namespace clone = new Namespace();
//				clone.setName("TryClone");
//				clone.setDescription("Testing This Namespace");
//				namespaceService.save(clone);
//			} catch (AlreadyExistsException aee) {
//				throw aee;
//			}finally {
//				namespaceService.delete(test.getName());
//			}
//		});
//	}
//
//
//	@Test
//	public void createAndTestNameLikeThenDelete() throws Exception {
//		Namespace test1 = new Namespace();
//		test1.setName("org.kinotic.");
//		test1.setDescription("kinotic stuff");
//		test1 = namespaceService.save(test1);
//
//		Namespace test2 = new Namespace();
//		test2.setName("some-namespace");
//		test2.setDescription("namespace stuff");
//		test2 = namespaceService.save(test2);
//
//		Namespace test3 = new Namespace();
//		test3.setName("some-kinotic-space");
//		test3.setDescription("namespace stuff");
//		test3 = namespaceService.save(test3);
//
//		SearchHits query1 = namespaceService.getAllNamespaceLike("org*", 10, 0, "name", true);
//		Assertions.assertEquals(1, query1.getTotalHits().value, "First Namespace 'name like' query did not return expected results");
//
//		SearchHits query2 = namespaceService.getAllNamespaceLike("some*", 10, 0, "name", true);
//		Assertions.assertEquals(2, query2.getTotalHits().value, "Second Namespace 'name like' query did not return expected results");
//
//		namespaceService.delete(test1.getName());
//		namespaceService.delete(test2.getName());
//		namespaceService.delete(test3.getName());
//	}
//
//	@Test
//	public void createAndTestGetAllThenDelete() throws AlreadyExistsException, IOException {
//		Namespace test1 = new Namespace();
//		test1.setName("org.kinotic.");
//		test1.setDescription("kinotic stuff");
//		test1 = namespaceService.save(test1);
//
//		Namespace test2 = new Namespace();
//		test2.setName("some-namespace");
//		test2.setDescription("namespace stuff");
//		test2 = namespaceService.save(test2);
//
//		Namespace test3 = new Namespace();
//		test3.setName("some-kinotic-space");
//		test3.setDescription("namespace stuff");
//		test3 = namespaceService.save(test3);
//
//		SearchHits query1 = namespaceService.getAll( 10, 0, "name", true);
//		Assertions.assertEquals(3, query1.getTotalHits().value, "Namespace 'getAll' query did not return expected results");
//
//		namespaceService.delete(test1.getName());
//		namespaceService.delete(test2.getName());
//		namespaceService.delete(test3.getName());
//	}
//
//	@Test
//	public void createAndTestGetNamespaceThenDelete() throws Exception {
//		Namespace test = new Namespace();
//		test.setName("additional-testing");
//		test.setDescription("kinotic stuff");
//		test = namespaceService.save(test);
//
//		Optional<Namespace> isSaved = namespaceService.getNamespace(test.getName());
//		if(isSaved.isEmpty()){
//			namespaceService.delete(test.getName());
//			throw new IllegalStateException("Searched for Namespace just added and received empty response.");
//		}else{
//			namespaceService.delete(test.getName());
//		}
//	}
//
//
//	@Test
//	public void createNamespaceAndStructureAndAttemptDeleteOfNamespace() {
//		Assertions.assertThrows(IllegalStateException.class, () -> {
//			Namespace test = new Namespace();
//			test.setName("error-testing");
//			test.setDescription("kinotic stuff");
//			Structure structure = null;
//
//			try {
//				test = namespaceService.save(test);
//				structure = structureTestHelper.getComputerStructure(test.getName());
//				namespaceService.delete(test.getName());
//			}catch (Exception e){
//				throw e;
//			}finally {
//				if(structure != null){
//					structureService.delete(structure.getId());
//				}
//				Thread.sleep(1000);
//				namespaceService.delete(test.getName());
//			}
//		});
//	}
}
