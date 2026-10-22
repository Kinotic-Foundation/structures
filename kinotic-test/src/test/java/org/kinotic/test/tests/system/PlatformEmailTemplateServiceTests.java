package org.kinotic.test.tests.system;

import io.vertx.core.Future;
import org.junit.jupiter.api.Test;
import org.kinotic.domain.api.model.EmailTemplateType;
import org.kinotic.domain.api.model.PlatformEmailTemplate;
import org.kinotic.system.api.services.PlatformEmailTemplateService;
import org.kinotic.test.support.kinotic.KinoticTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Verifies what a platform email template must be to be saved: a readable id, an http or https UI base URL, and
 * sources that render with the type's variables; and that the defaults every unnamed send uses stay in place.
 */
@SpringBootTest
public class PlatformEmailTemplateServiceTests extends KinoticTestBase {

    @Autowired
    private PlatformEmailTemplateService templates;

    @Test
    public void aTemplateIsSavedByItsIdReplacedAndDeleted() throws Exception {
        String id = "builder-invitation-" + suffix();

        PlatformEmailTemplate saved = await(templates.save(invitation(id).setUiBaseUrl("https://builder.example.com/")));
        assertEquals("https://builder.example.com", saved.getUiBaseUrl());

        // a save with the same id replaces it and keeps its creation time
        PlatformEmailTemplate replaced = await(templates.save(invitation(id).setSubject("Changed")));
        assertEquals("Changed", await(templates.findById(id)).getSubject());
        assertEquals(saved.getCreated(), replaced.getCreated());

        await(templates.deleteById(id));
        assertNull(await(templates.findById(id)));
    }

    @Test
    public void theDefaultsAreSeededAndStay() throws Exception {
        for (EmailTemplateType type : EmailTemplateType.values()) {
            PlatformEmailTemplate seeded = await(templates.findById(type.getDefaultTemplateId()));
            assertNotNull(seeded);
            assertEquals(type, seeded.getType());
            assertRefused(() -> templates.deleteById(type.getDefaultTemplateId()));
        }
        assertRefused(() -> templates.save(invitation(EmailTemplateType.VERIFICATION.getDefaultTemplateId())));
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

    private static PlatformEmailTemplate invitation(String id) {
        return new PlatformEmailTemplate().setId(id)
                                          .setType(EmailTemplateType.INVITATION)
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
