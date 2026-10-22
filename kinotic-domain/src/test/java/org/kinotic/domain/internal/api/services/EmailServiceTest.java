package org.kinotic.domain.internal.api.services;

import org.junit.jupiter.api.Test;
import org.kinotic.domain.api.model.EmailTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Covers {@link EmailService#validateTemplate}: which templates a caller may pass.
 */
class EmailServiceTest {

    private final EmailService emailService = new EmailService(null, null, null);

    @Test
    void aCompleteTemplateIsAccepted() {
        assertDoesNotThrow(() -> emailService.validateTemplate(template("https://builder.example.com")));
        assertDoesNotThrow(() -> emailService.validateTemplate(template("HTTPS://builder.example.com/app")));
        assertDoesNotThrow(() -> emailService.validateTemplate(null));
    }

    @Test
    void aUiBaseUrlLinksCannotBeAppendedToIsRejected() {
        for (String url : new String[]{null, "", "https://builder.example.com/", "https://builder.example.com?x=1",
                                       "https://builder.example.com#top", "builder.example.com", "ftp://builder.example.com",
                                       " https://builder.example.com"}) {
            assertThrows(IllegalArgumentException.class, () -> emailService.validateTemplate(template(url)), String.valueOf(url));
        }
    }

    @Test
    void aBlankOrBrokenSourceIsRejected() {
        assertThrows(IllegalArgumentException.class,
                     () -> emailService.validateTemplate(template("https://builder.example.com").setSubject(" ")));
        assertThrows(IllegalArgumentException.class,
                     () -> emailService.validateTemplate(template("https://builder.example.com").setTextBody(null)));
        assertThrows(IllegalArgumentException.class,
                     () -> emailService.validateTemplate(template("https://builder.example.com").setHtmlBody("{{#if acceptUrl}}")));
        assertThrows(IllegalArgumentException.class,
                     () -> emailService.validateTemplate(template("https://builder.example.com").setHtmlBody("{{> header}}")));
    }

    private static EmailTemplate template(String uiBaseUrl) {
        return new EmailTemplate().setUiBaseUrl(uiBaseUrl)
                                  .setSubject("Join {{organizationName}}")
                                  .setHtmlBody("<a href=\"{{{acceptUrl}}}\">Accept</a>")
                                  .setTextBody("Accept: {{acceptUrl}}");
    }
}
