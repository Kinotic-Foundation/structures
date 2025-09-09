

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
