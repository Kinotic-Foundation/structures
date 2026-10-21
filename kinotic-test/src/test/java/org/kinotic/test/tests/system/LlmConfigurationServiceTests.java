package org.kinotic.test.tests.system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kinotic.core.api.exceptions.AuthorizationException;
import org.kinotic.domain.api.model.security.participant.DefaultSystemParticipant;
import org.kinotic.system.api.model.llm.LlmConfiguration;
import org.kinotic.system.api.model.llm.LlmProviderType;
import org.kinotic.system.api.services.LlmConfigurationService;
import org.kinotic.test.support.kinotic.KinoticTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Verifies who may read a configuration's API key: a cluster member calling as itself reads it, while a call made for
 * an organization's user or for a platform operator is refused.
 */
@SpringBootTest
public class LlmConfigurationServiceTests extends KinoticTestBase {

    @Autowired
    private LlmConfigurationService llmConfigurations;

    private LlmConfiguration configuration;
    private String apiKey;

    @BeforeEach
    public void saveConfiguration() throws Exception {
        apiKey = "sk-test-" + suffix();
        configuration = await(llmConfigurations.save(new LlmConfiguration().setName("llm-" + suffix())
                                                                            .setProvider(LlmProviderType.ANTHROPIC)
                                                                            .setModel("claude-test"),
                                                     apiKey));
    }

    @Test
    public void aClusterMemberCallingAsItselfReadsTheKey() throws Exception {
        String read = securityContext.callWithoutParticipant(() -> llmConfigurations.findApiKey(configuration.getId())
                                                                                    .toCompletionStage())
                                     .get(30, TimeUnit.SECONDS);
        assertEquals(apiKey, read);
    }

    @Test
    public void aCallForAnOrganizationsUserIsRefused() {
        ExecutionException thrown = assertThrows(ExecutionException.class,
                                                 () -> await(runAsOrganization(() -> llmConfigurations.findApiKey(configuration.getId()))));
        assertInstanceOf(AuthorizationException.class, thrown.getCause());
    }

    @Test
    public void aCallForAPlatformOperatorIsRefused() {
        DefaultSystemParticipant operator = new DefaultSystemParticipant("operator-" + suffix(), Map.of(), List.of());
        ExecutionException thrown = assertThrows(ExecutionException.class,
                                                 () -> securityContext.callAs(operator,
                                                                              () -> llmConfigurations.findApiKey(configuration.getId())
                                                                                                     .toCompletionStage())
                                                                      .get(30, TimeUnit.SECONDS));
        assertInstanceOf(AuthorizationException.class, thrown.getCause());
    }
}
