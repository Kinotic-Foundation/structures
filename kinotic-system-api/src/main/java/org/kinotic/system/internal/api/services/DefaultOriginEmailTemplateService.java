package org.kinotic.system.internal.api.services;

import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.kinotic.core.api.crud.Page;
import org.kinotic.core.api.crud.Pageable;
import org.kinotic.domain.api.model.OriginEmailTemplate;
import org.kinotic.domain.internal.api.repositories.OriginEmailTemplateRepository;
import org.kinotic.domain.internal.api.services.EmailService;
import org.kinotic.system.api.services.OriginEmailTemplateService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class DefaultOriginEmailTemplateService implements OriginEmailTemplateService {

    // what a browser sends as a page's Origin: a scheme and authority, with no user info, path, query or fragment
    private static final Pattern ORIGIN = Pattern.compile("https?://[^/?#@\\s]+");

    private final OriginEmailTemplateRepository repository;
    private final EmailService emailService;

    @Override
    public Future<Page<OriginEmailTemplate>> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Future<OriginEmailTemplate> findById(String id) {
        Validate.notBlank(id, "id is required");
        return repository.findById(id);
    }

    @Override
    public Future<OriginEmailTemplate> save(OriginEmailTemplate template) {
        Validate.notNull(template, "template is required");
        Validate.notBlank(template.getOrigin(), "origin is required");
        Validate.notNull(template.getKind(), "kind is required");
        Validate.notBlank(template.getSubject(), "subject is required");
        Validate.notBlank(template.getHtmlBody(), "htmlBody is required");
        Validate.notBlank(template.getTextBody(), "textBody is required");
        Validate.isTrue(template.getId() == null || StringUtils.isNotBlank(template.getId()), "id must not be blank");
        String origin = template.getOrigin().trim().toLowerCase(Locale.ROOT);
        Validate.isTrue(ORIGIN.matcher(origin).matches(),
                        "origin must be a scheme and host with no path, such as https://builder.example.com");
        emailService.validateTemplate(template, template.getKind());
        template.setOrigin(origin);

        Date now = new Date();
        Future<Void> stored;
        if (template.getId() == null) {
            template.setId(UUID.randomUUID().toString())
                    .setCreated(now);
            stored = Future.succeededFuture();
        } else {
            // an update keeps the stored creation time
            stored = repository.findById(template.getId()).map(existing -> {
                Validate.isTrue(existing != null, "No email template has id '%s'", template.getId());
                template.setCreated(existing.getCreated());
                return null;
            });
        }
        template.setUpdated(now);
        return stored.compose(v -> repository.findByOriginAndKind(origin, template.getKind()))
                     .compose(same -> {
                         Validate.isTrue(same == null || same.getId().equals(template.getId()),
                                         "%s already has a %s email template", origin, template.getKind());
                         // refreshed before returning, since the console's table reads the templates right after a save
                         return repository.saveSync(template);
                     });
    }

    @Override
    public Future<Void> deleteById(String id) {
        Validate.notBlank(id, "id is required");
        return repository.deleteByIdSync(id);
    }
}
