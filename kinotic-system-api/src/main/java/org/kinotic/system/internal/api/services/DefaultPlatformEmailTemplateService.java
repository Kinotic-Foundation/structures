package org.kinotic.system.internal.api.services;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.domain.api.model.EmailTemplateKind;
import org.kinotic.domain.api.model.PlatformEmailTemplate;
import org.kinotic.domain.internal.api.repositories.PlatformEmailTemplateRepository;
import org.kinotic.domain.internal.api.services.EmailService;
import org.kinotic.system.api.services.PlatformEmailTemplateService;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class DefaultPlatformEmailTemplateService implements PlatformEmailTemplateService {

    private static final Pattern ID = Pattern.compile("[a-z0-9]+(-[a-z0-9]+)*");
    // links append a path to it, so it is an absolute http or https URL with no query or fragment
    private static final Pattern UI_BASE_URL = Pattern.compile("https?://[^/?#@\\s]+(/[^?#\\s]*)?");

    private final PlatformEmailTemplateRepository repository;
    private final EmailService emailService;

    @Override
    public Future<Page<PlatformEmailTemplate>> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Future<PlatformEmailTemplate> findById(String id) {
        Validate.notBlank(id, "id is required");
        return repository.findById(id);
    }

    @Override
    public Future<PlatformEmailTemplate> save(PlatformEmailTemplate template) {
        Validate.notNull(template, "template is required");
        Validate.notBlank(template.getId(), "id is required");
        Validate.isTrue(ID.matcher(template.getId()).matches(),
                        "id must be lowercase letters, digits and interior dashes, such as builder-invitation");
        Validate.notNull(template.getKind(), "kind is required");
        Validate.isTrue(template.getUiBaseUrl() == null || UI_BASE_URL.matcher(template.getUiBaseUrl()).matches(),
                        "uiBaseUrl must be an http or https URL with no query, such as https://builder.example.com");
        Validate.notBlank(template.getSubject(), "subject is required");
        Validate.notBlank(template.getHtmlBody(), "htmlBody is required");
        Validate.notBlank(template.getTextBody(), "textBody is required");
        EmailTemplateKind defaultOf = defaultKind(template.getId());
        Validate.isTrue(defaultOf == null || defaultOf == template.getKind(),
                        "%s is the default %s email template, so it must stay one", template.getId(), defaultOf);
        emailService.validateTemplate(template, template.getKind());
        template.setUiBaseUrl(StringUtils.removeEnd(template.getUiBaseUrl(), "/"));

        Date now = new Date();
        template.setUpdated(now);
        return repository.findById(template.getId()).compose(existing -> {
            template.setCreated(existing != null ? existing.getCreated() : now);
            // refreshed before returning, since the console's table reads the templates right after a save
            return repository.saveSync(template);
        });
    }

    @Override
    public Future<Void> deleteById(String id) {
        Validate.notBlank(id, "id is required");
        Validate.isTrue(defaultKind(id) == null, "%s is a default email template, sent by every call that names none", id);
        return repository.deleteByIdSync(id);
    }

    // the kind whose default template has this id, or null when it is no kind's default
    private static EmailTemplateKind defaultKind(String id) {
        return Arrays.stream(EmailTemplateKind.values())
                     .filter(kind -> kind.getDefaultTemplateId().equals(id))
                     .findFirst()
                     .orElse(null);
    }
}
