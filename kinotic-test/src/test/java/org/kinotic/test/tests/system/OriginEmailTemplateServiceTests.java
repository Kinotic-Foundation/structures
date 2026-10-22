package org.kinotic.test.tests.system;

import org.junit.jupiter.api.Test;
import org.kinotic.domain.api.model.EmailTemplateKind;
import org.kinotic.domain.api.model.OriginEmailTemplate;
import org.kinotic.system.api.services.OriginEmailTemplateService;
import org.kinotic.test.support.kinotic.KinoticTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Verifies what a UI's email template must be to be saved: an origin with no path, sources that render with the
 * kind's variables, and no other template for the same origin and kind.
 */
@SpringBootTest
public class OriginEmailTemplateServiceTests extends KinoticTestBase {

    @Autowired
    private OriginEmailTemplateService templates;

    @Test
    public void aTemplateIsSavedForItsOriginInLowerCaseAndDeleted() throws Exception {
        String origin = "https://Builder-" + suffix() + ".example.com";

        OriginEmailTemplate saved = await(templates.save(invitation(origin)));

        assertEquals(origin.toLowerCase(), saved.getOrigin());
        assertEquals(saved.getOrigin(), await(templates.findById(saved.getId())).getOrigin());
        await(templates.deleteById(saved.getId()));
        assertNull(await(templates.findById(saved.getId())));
    }

    @Test
    public void anOriginHoldsOneTemplateOfEachKind() throws Exception {
        String origin = "https://builder-" + suffix() + ".example.com";
        OriginEmailTemplate first = await(templates.save(invitation(origin)));

        assertRefused(invitation(origin));

        // the other kind, and an edit of the first, are both saved
        await(templates.save(new OriginEmailTemplate().setOrigin(origin)
                                                      .setKind(EmailTemplateKind.VERIFICATION)
                                                      .setSubject("Verify your email")
                                                      .setHtmlBody("<a href=\"{{{verificationUrl}}}\">Verify</a>")
                                                      .setTextBody("Verify: {{verificationUrl}}")));
        assertEquals("Changed", await(templates.save(first.setSubject("Changed"))).getSubject());
    }

    @Test
    public void anOriginWithAPathIsRefused() {
        assertRefused(invitation("https://builder.example.com/invite"));
    }

    @Test
    public void aSourceThatDoesNotRenderIsRefused() {
        assertRefused(invitation("https://builder-" + suffix() + ".example.com").setHtmlBody("{{#if acceptUrl}}unclosed"));
    }

    private static OriginEmailTemplate invitation(String origin) {
        return new OriginEmailTemplate().setOrigin(origin)
                                        .setKind(EmailTemplateKind.INVITATION)
                                        .setSubject("Join {{organizationName}}")
                                        .setHtmlBody("<a href=\"{{{acceptUrl}}}\">Accept</a>")
                                        .setTextBody("Accept: {{acceptUrl}}");
    }

    private void assertRefused(OriginEmailTemplate template) {
        Throwable thrown = assertThrows(Throwable.class, () -> await(templates.save(template)));
        Throwable cause = thrown instanceof ExecutionException ? thrown.getCause() : thrown;
        assertInstanceOf(IllegalArgumentException.class, cause);
    }
}
