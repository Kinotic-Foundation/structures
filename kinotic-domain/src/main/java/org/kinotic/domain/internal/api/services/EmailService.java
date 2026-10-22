package org.kinotic.domain.internal.api.services;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import com.azure.communication.email.models.EmailAddress;
import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.communication.email.models.EmailSendStatus;
import com.azure.core.util.polling.SyncPoller;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.EscapingStrategy;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.kinotic.domain.api.config.KinoticDomainProperties;
import org.kinotic.domain.api.model.EmailTemplate;
import org.kinotic.domain.api.model.InviteEmailTemplate;
import org.kinotic.domain.api.model.Organization;
import org.kinotic.domain.api.model.SystemEmailTemplate;
import org.kinotic.domain.api.model.security.PendingInvite;
import org.kinotic.domain.internal.api.repositories.InviteEmailTemplateRepository;
import org.kinotic.domain.internal.api.repositories.SystemEmailTemplateRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Sends transactional emails via Azure Communication Services. Bodies are rendered
 * from Handlebars templates.
 * <p>
 * When {@code kinotic.domain.email.enabled=false}, sends are skipped and the action
 * URL is logged instead — useful for local development.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailService {

    private static final String VERIFICATION_PATH = "/signup/verify?token=";
    private static final String INVITE_PATH = "/invite/accept?token=";
    private static final String ORGANIZATIONS_SUBJECT = "Your organizations on Kinotic";
    private static final String ORGANIZATION_LOGIN_PATH = "/login/";

    // Two engines differing by template suffix and escaping: HTML bodies HTML-escape
    // {{var}} by default (templates opt out with {{{var}}} for system-constructed URLs);
    // plain-text bodies must not be escaped at all.
    private static final Handlebars HTML_TEMPLATES =
            new Handlebars(new ClassPathTemplateLoader("/templates/email", ".html"));
    private static final Handlebars TEXT_TEMPLATES =
            new Handlebars(new ClassPathTemplateLoader("/templates/email", ".txt"))
                    .with(EscapingStrategy.NOOP);

    private static final Template ORGANIZATIONS_HTML = compileTemplate(HTML_TEMPLATES, "organizations-email");
    private static final Template ORGANIZATIONS_TEXT = compileTemplate(TEXT_TEMPLATES, "organizations-email");

    private final KinoticDomainProperties properties;
    private final InviteEmailTemplateRepository inviteEmailTemplateRepository;
    private final SystemEmailTemplateRepository systemEmailTemplateRepository;
    private final Vertx vertx;

    private volatile EmailClient emailClient;

    /**
     * Sends a verification email to the given address, rendered with the {@link SystemEmailTemplate}
     * {@code emailTemplateId}, whose UI's verification page the link opens.
     *
     * @param email             the recipient's email address
     * @param displayName       the recipient's display name for the greeting
     * @param verificationToken the token to include in the verification URL
     * @param emailTemplateId   the template to send, or {@code null} for
     *                          {@link SystemEmailTemplate#DEFAULT_VERIFICATION_ID}
     * @return a future that completes once the send has finished (or fails if ACS rejects it)
     * @throws IllegalArgumentException when no template has id {@code emailTemplateId}
     */
    public Future<Void> sendVerificationEmail(String email,
                                              String displayName,
                                              String verificationToken,
                                              String emailTemplateId) {
        return findSystemTemplate(emailTemplateId, SystemEmailTemplate.DEFAULT_VERIFICATION_ID).compose(template -> {
            String verificationUrl = uiBaseUrl(template) + VERIFICATION_PATH + verificationToken;
            Future<Void> ret;
            if (properties.getDomain().getEmail().isEnabled()) {
                Map<String, Object> variables = Map.of("displayName", displayName,
                                                       "verificationUrl", verificationUrl);
                ret = send(email, displayName, template, variables);
            } else {
                log.warn("Email sending is disabled; verification URL for {} <{}>: {}",
                         displayName, email, verificationUrl);
                ret = Future.succeededFuture();
            }
            return ret;
        });
    }

    /**
     * Sends an invitation email carrying the accept link for the given invite, which opens the invitation page of the
     * UI of the {@link SystemEmailTemplate} {@code emailTemplateId}. The email
     * is rendered with that template, or with the application's {@link InviteEmailTemplate} for an application invite
     * whose application has one.
     *
     * @param invite           the saved invitation (token, recipient, scope, inviter attribution)
     * @param organizationName display name of the inviting organization
     * @param emailTemplateId  the template to send, or {@code null} for
     *                         {@link SystemEmailTemplate#DEFAULT_INVITATION_ID}
     * @return a future that completes once the send has finished (or fails if ACS rejects it)
     * @throws IllegalArgumentException when no template has id {@code emailTemplateId}
     */
    public Future<Void> sendInviteEmail(PendingInvite invite, String organizationName, String emailTemplateId) {
        return findSystemTemplate(emailTemplateId, SystemEmailTemplate.DEFAULT_INVITATION_ID).compose(systemTemplate -> {
            String acceptUrl = uiBaseUrl(systemTemplate) + INVITE_PATH + invite.getVerificationToken();
            String toName = invite.getDisplayName() != null ? invite.getDisplayName() : invite.getEmail();
            Future<Void> ret;
            if (properties.getDomain().getEmail().isEnabled()) {
                long expiresInDays = Math.max(1, Math.round(
                        (invite.getExpiresAt().getTime() - System.currentTimeMillis()) / 86_400_000.0));

                // HashMap rather than Map.of: applicationName is omitted (not null) for org invites
                // so the templates' {{#if applicationName}} branch sees it as falsy.
                Map<String, Object> variables = new HashMap<>();
                variables.put("inviterName", invite.getInvitedByName());
                variables.put("organizationName", organizationName);
                if (invite.getApplicationId() != null) {
                    variables.put("applicationName", invite.getApplicationId());
                }
                variables.put("acceptUrl", acceptUrl);
                variables.put("expiresInDays", expiresInDays);

                ret = findApplicationTemplate(invite)
                        .compose(own -> send(invite.getEmail(), toName, own != null ? own : systemTemplate, variables));
            } else {
                log.warn("Email sending is disabled; invite accept URL for {} <{}>: {}",
                         toName, invite.getEmail(), acceptUrl);
                ret = Future.succeededFuture();
            }
            return ret;
        });
    }

    // the application's own template for an application invite, or null when it has none or the invite is an organization's
    private Future<InviteEmailTemplate> findApplicationTemplate(PendingInvite invite) {
        return invite.getApplicationId() != null
                ? inviteEmailTemplateRepository.findByApplication(invite.getApplicationId(), invite.getOrganizationId())
                : Future.succeededFuture();
    }

    private Future<SystemEmailTemplate> findSystemTemplate(String emailTemplateId, String defaultId) {
        String id = emailTemplateId != null ? emailTemplateId : defaultId;
        return systemEmailTemplateRepository.findById(id).map(found -> {
            if (emailTemplateId == null) {
                // the seed migration gives every environment the defaults, and the console refuses to delete them
                Validate.validState(found != null, "The default email template %s is missing", id);
            }
            Validate.isTrue(found != null, "No email template has id '%s'", id);
            return found;
        });
    }

    private String uiBaseUrl(SystemEmailTemplate template) {
        return template.getUiBaseUrl() != null ? template.getUiBaseUrl() : portalBaseUrl();
    }

    /**
     * Sends the person at {@code email} the list of organizations they are a member of, each with a link to
     * that organization's login. An empty list sends an email saying the address belongs to no
     * organization, so the request that asked for it reveals nothing about the address.
     *
     * @param email         the recipient's email address
     * @param organizations the organizations the address is a member of, possibly none
     * @return a future that completes once the send has finished (or fails if ACS rejects it)
     */
    public Future<Void> sendOrganizationsEmail(String email, List<Organization> organizations) {
        List<Map<String, Object>> rows = organizations.stream()
                .map(organization -> Map.<String, Object>of(
                        "name", organization.getName(),
                        "loginUrl", portalBaseUrl() + ORGANIZATION_LOGIN_PATH + organization.getId()))
                .toList();

        if (!properties.getDomain().getEmail().isEnabled()) {
            log.warn("Email sending is disabled; organizations of <{}>: {}", email,
                     rows.stream().map(row -> row.get("loginUrl")).toList());
            return Future.succeededFuture();
        }

        // HashMap rather than Map.of: an empty list is omitted so the templates' {{#if organizations}} sees it as falsy
        Map<String, Object> variables = new HashMap<>();
        if (!rows.isEmpty()) {
            variables.put("organizations", rows);
        }
        return send(email,
                    email,
                    ORGANIZATIONS_SUBJECT,
                    render(ORGANIZATIONS_HTML, variables),
                    render(ORGANIZATIONS_TEXT, variables));
    }

    // checked per send: a server that sends no mail leaves it unset
    private String portalBaseUrl() {
        String ret = properties.getDomain().getPortalBaseUrl();
        Validate.notBlank(ret, "kinotic.domain.portalBaseUrl must be set on a server that sends email");
        return ret;
    }

    /**
     * Parses and test-renders a template's sources with sample values of the invitation and verification variables, so
     * a broken template fails at save time rather than when the next email is sent.
     *
     * @throws IllegalArgumentException carrying the Handlebars message when a source does
     *                                  not compile or render
     */
    public void validateTemplate(EmailTemplate template) {
        Map<String, Object> sample = Map.of("inviterName", "Inviter Name",
                                            "organizationName", "Organization Name",
                                            "applicationName", "application-id",
                                            "acceptUrl", "https://example.invalid/invite/accept?token=sample",
                                            "expiresInDays", 7L,
                                            "displayName", "Display Name",
                                            "verificationUrl", "https://example.invalid/signup/verify?token=sample");
        try {
            renderInline(TEXT_TEMPLATES, template.getSubject(), sample);
            renderInline(HTML_TEMPLATES, template.getHtmlBody(), sample);
            renderInline(TEXT_TEMPLATES, template.getTextBody(), sample);
        } catch (RuntimeException e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            throw new IllegalArgumentException("Invalid email template: " + cause.getMessage(), e);
        }
    }

    /**
     * Renders a stored (user-authored) template source with the same map-only resolution
     * as the built-ins. Compiled per send — stored templates can change between sends.
     */
    private static String renderInline(Handlebars engine, String source, Map<String, Object> variables) {
        try {
            return render(engine.compileInline(source), variables);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed compiling stored email template", e);
        }
    }

    private static String render(Template template, Map<String, Object> variables) {
        // Map-only resolution: no reflective bean/method traversal of context objects,
        // which is the known handlebars.java template-injection vector.
        Context context = Context.newBuilder(variables)
                                 .resolver(MapValueResolver.INSTANCE)
                                 .build();
        try {
            return template.apply(context);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed rendering email template", e);
        } finally {
            context.destroy();
        }
    }

    private Future<Void> send(String toEmail, String toName, EmailTemplate template, Map<String, Object> variables) {
        return send(toEmail,
                    toName,
                    renderInline(TEXT_TEMPLATES, template.getSubject(), variables),
                    renderInline(HTML_TEMPLATES, template.getHtmlBody(), variables),
                    renderInline(TEXT_TEMPLATES, template.getTextBody(), variables));
    }

    private Future<Void> send(String toEmail,
                              String toName,
                              String subject,
                              String htmlBody,
                              String textBody) {
        EmailClient client = getOrBuildEmailClient();

        EmailMessage message = new EmailMessage()
                .setSenderAddress(properties.getDomain().getEmail().getSenderAddress())
                .setToRecipients(List.of(new EmailAddress(toEmail).setDisplayName(toName)))
                .setSubject(subject)
                .setBodyHtml(htmlBody)
                .setBodyPlainText(textBody);

        return Future.fromCompletionStage(CompletableFuture.supplyAsync(() -> {
            SyncPoller<EmailSendResult, EmailSendResult> poller = client.beginSend(message);
            poller.waitForCompletion(properties.getDomain().getEmail().getSendTimeout());
            EmailSendResult result = poller.getFinalResult();

            if (result.getStatus() == EmailSendStatus.SUCCEEDED) {
                log.info("Sent '{}' email to {} (messageId={})", subject, toEmail, result.getId());
                return null;
            }
            throw new IllegalStateException("Azure Communication Services rejected the send: status="
                    + result.getStatus() + " messageId=" + result.getId());
        }), vertx.getOrCreateContext());
    }

    /**
     * Lazily builds the ACS {@link EmailClient} on first successful send. Auth uses
     * {@code DefaultAzureCredential} — on AKS this resolves to a Workload-Identity
     * federated token; on a developer laptop it falls through to {@code AzureCliCredential}
     * (via {@code az login}). No connection strings or secrets are read from config.
     */
    private EmailClient getOrBuildEmailClient() {
        EmailClient client = emailClient;
        if (client == null) {
            synchronized (this) {
                client = emailClient;
                if (client == null) {
                    DefaultAzureCredentialBuilder credBuilder = new DefaultAzureCredentialBuilder();
                    if (StringUtils.hasText(properties.getDomain().getEmail().getManagedIdentityClientId())) {
                        credBuilder.managedIdentityClientId(properties.getDomain().getEmail().getManagedIdentityClientId());
                    }
                    client = new EmailClientBuilder()
                            .endpoint(properties.getDomain().getEmail().getEndpoint())
                            .credential(credBuilder.build())
                            .buildClient();
                    emailClient = client;
                }
            }
        }
        return client;
    }

    /**
     * Compiles a built-in template at class initialization, so a missing or broken
     * template fails fast at startup rather than on the first send.
     */
    private static Template compileTemplate(Handlebars handlebars, String name) {
        try {
            return handlebars.compile(name);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed compiling email template: " + name, e);
        }
    }

}
