package org.kinotic.test.tests.core.security;

import org.junit.jupiter.api.Test;
import org.kinotic.core.api.exceptions.AuthorizationException;
import org.kinotic.domain.api.model.security.participant.DefaultSystemParticipant;
import org.kinotic.management.api.services.github.ProjectRepoCredentialService;
import org.kinotic.test.support.kinotic.KinoticTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Verifies that repository write tokens are issued only to callers acting for an organization's user.
 */
@SpringBootTest
public class ProjectRepoCredentialServiceTests extends KinoticTestBase {

    @Autowired
    private ProjectRepoCredentialService repoCredentials;

    @Test
    public void aCallForASystemUserIsRefused() {
        DefaultSystemParticipant systemUser = new DefaultSystemParticipant("system-user-" + suffix(), Map.of(), List.of());
        ExecutionException thrown = assertThrows(ExecutionException.class,
                                                 () -> securityContext.callAs(systemUser,
                                                                              () -> repoCredentials.issueWriteToken("project-" + suffix())
                                                                                                   .toCompletionStage())
                                                                      .get(30, TimeUnit.SECONDS));
        assertInstanceOf(AuthorizationException.class, thrown.getCause());
    }
}
