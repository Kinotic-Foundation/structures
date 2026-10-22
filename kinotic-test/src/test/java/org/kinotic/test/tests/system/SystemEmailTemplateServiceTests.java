package org.kinotic.test.tests.system;

import io.vertx.core.Future;
import org.junit.jupiter.api.Test;
import org.kinotic.domain.api.model.SystemEmailTemplate;
import org.kinotic.system.api.services.SystemEmailTemplateService;
import org.kinotic.test.support.kinotic.KinoticTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Verifies what a system email template must be to be saved: a readable id, an http or https UI base URL, and
 * sources that render with the email variables; and that the defaults every unnamed send uses stay in place.
 */
@SpringBootTest
public class SystemEmailTemplateServiceTests extends KinoticTestBase {

    @Autowired
    private SystemEmailTemplateService templates;

    @Test
    public void aTemplateIsSavedByItsIdReplacedAndDeleted() throws Exception {
        String id = "builder-invitation-" + suffix();

        SystemEmailTemplate saved = await(templates.save(invitation(id).setUiBaseUrl("https://builder.example.com/")));
        assertEquals("https://builder.example.com", saved.getUiBaseUrl());

        // a save with the same id replaces it and keeps its creation time
        SystemEmailTemplate replaced = await(templates.save(invitation(id).setSubject("Changed")));
        assertEquals("Changed", await(templates.findById(id)).getSubject());
        assertEquals(saved.getCreated(), replaced.getCreated());

        await(templates.deleteById(id));
        assertNull(await(templates.findById(id)));
    }

    @Test
    public void theDefaultsAreSeededAndStay() throws Exception {
        for (String id : List.of(SystemEmailTemplate.DEFAULT_INVITATION_ID, SystemEmailTemplate.DEFAULT_VERIFICATION_ID)) {
            assertNotNull(await(templates.findById(id)));
            assertRefused(() -> templates.deleteById(id));
        }
    }

    @Test
    public void anIdThatIsNotLowercaseWordsIsRefused() {
        assertRefused(() -> templates.save(invitation("Builder Invitation")));
    }

    @Test
    public void aUiBaseUrlWithAQueryIsRefused() {
        assertRefused(() -> templates.save(invitation("builder-" + suffix()).setUiBaseUrl("https://builder.example.com/?a=b")));
    }

    @Test
    public void aSourceThatDoesNotRenderIsRefused() {
        assertRefused(() -> templates.save(invitation("builder-" + suffix()).setHtmlBody("{{#if acceptUrl}}unclosed")));
    }

    private static SystemEmailTemplate invitation(String id) {
        return new SystemEmailTemplate().setId(id)
                                          .setSubject("Join {{organizationName}}")
                                          .setHtmlBody("<a href=\"{{{acceptUrl}}}\">Accept</a>")
                                          .setTextBody("Accept: {{acceptUrl}}");
    }

    private void assertRefused(Supplier<Future<?>> call) {
        Throwable thrown = assertThrows(Throwable.class, () -> await(call.get()));
        Throwable cause = thrown instanceof ExecutionException ? thrown.getCause() : thrown;
        assertInstanceOf(IllegalArgumentException.class, cause);
    }
}
