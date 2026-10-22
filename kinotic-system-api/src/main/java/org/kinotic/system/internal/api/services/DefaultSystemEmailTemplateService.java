package org.kinotic.system.internal.api.services;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.domain.api.model.SystemEmailTemplate;
import org.kinotic.domain.internal.api.repositories.SystemEmailTemplateRepository;
import org.kinotic.domain.internal.api.services.EmailService;
import org.kinotic.system.api.services.SystemEmailTemplateService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class DefaultSystemEmailTemplateService implements SystemEmailTemplateService {

    private static final Pattern ID = Pattern.compile("[a-z0-9]+(-[a-z0-9]+)*");
    // links append a path to it, so it is an absolute http or https URL with no query or fragment
    private static final Pattern UI_BASE_URL = Pattern.compile("https?://[^/?#@\\s]+(/[^?#\\s]*)?");
    private static final Set<String> DEFAULT_IDS = Set.of(SystemEmailTemplate.DEFAULT_INVITATION_ID,
                                                          SystemEmailTemplate.DEFAULT_VERIFICATION_ID);

    private final SystemEmailTemplateRepository repository;
    private final EmailService emailService;

    @Override
    public Future<Page<SystemEmailTemplate>> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Future<SystemEmailTemplate> findById(String id) {
        Validate.notBlank(id, "id is required");
        return repository.findById(id);
    }

    @Override
    public Future<SystemEmailTemplate> save(SystemEmailTemplate template) {
        Validate.notNull(template, "template is required");
        Validate.notBlank(template.getId(), "id is required");
        Validate.isTrue(ID.matcher(template.getId()).matches(),
                        "id must be lowercase letters, digits and interior dashes, such as builder-invitation");
        Validate.isTrue(template.getUiBaseUrl() == null || UI_BASE_URL.matcher(template.getUiBaseUrl()).matches(),
                        "uiBaseUrl must be an http or https URL with no query, such as https://builder.example.com");
        Validate.notBlank(template.getSubject(), "subject is required");
        Validate.notBlank(template.getHtmlBody(), "htmlBody is required");
        Validate.notBlank(template.getTextBody(), "textBody is required");
        emailService.validateTemplate(template);
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
        Validate.isTrue(!DEFAULT_IDS.contains(id), "%s is a default email template, sent by every call that names none", id);
        return repository.deleteByIdSync(id);
    }
}
