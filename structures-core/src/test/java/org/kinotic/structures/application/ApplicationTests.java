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

package org.kinotic.structures.application;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.kinotic.structures.ElasticsearchTestBase;
import org.kinotic.structures.api.domain.Application;
import org.kinotic.structures.api.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ApplicationTests extends ElasticsearchTestBase {

	@Autowired
	private ApplicationService applicationService;

	@Test
	public void createAndDeleteApplication() {
		Application test = new Application();
		test.setId("Test");
		test.setDescription("Testing This Application");

		CompletableFuture<Application> future = applicationService.save(test);

		StepVerifier.create(Mono.fromFuture(future))
					.expectNextMatches(application -> application.getId().equals("Test") && application.getUpdated() != null)
					.expectComplete()
					.verify();

		StepVerifier.create(Mono.fromFuture(applicationService.deleteById(test.getId())))
					.expectComplete()
					.verify();

		StepVerifier.create(Mono.fromFuture(applicationService.findById(test.getId())))
					.expectComplete()
					.verify();
	}

}
