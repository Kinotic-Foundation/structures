-- Summit 42's invitation and verification emails, in its own style, which Summit 42 names by id in the calls
-- that send them. Their links open http://localhost:5175, where a development stack runs Summit 42 at, its vite dev server.

INSERT INTO kinotic_system_email_template (id, type, uiBaseUrl, subject, htmlBody, textBody, created, updated) VALUES ('summit-42-invitation', 'INVITATION', 'http://localhost:5175', '{{#if applicationName}}{{inviterName}} invited you to {{applicationName}}{{else}}{{inviterName}} invited you to Summit 42{{/if}}', '<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>You&#39;re invited</title>
    <style>
        @media (max-width: 600px) {
            .container { width: 100% !important; }
            .px-gutter { padding-left: 20px !important; padding-right: 20px !important; }
            .cta-link { display: block !important; }
        }
    </style>
</head>
<body style="margin:0; padding:0; background-color:#0a1420;">

<div style="display:none; max-height:0; overflow:hidden; mso-hide:all;">
    {{inviterName}} invited you to {{#if applicationName}}{{applicationName}}{{else}}{{organizationName}}{{/if}} on Summit 42.
</div>

<table role="presentation" width="100%" cellpadding="0" cellspacing="0" border="0" bgcolor="#0a1420" style="background-color:#0a1420;">
    <tr>
        <td align="center" style="padding:32px 12px;">
            <table role="presentation" class="container" width="560" cellpadding="0" cellspacing="0" border="0" style="max-width:560px; width:100%;">

                <tr>
                    <td class="px-gutter" style="padding:8px 32px 24px;">
                        <table role="presentation" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                                <td width="28" height="28" bgcolor="#e8875a" align="center" valign="middle"
                                    style="width:28px; height:28px; border-radius:7px; background-color:#e8875a; color:#0a1420; font-family:Arial,sans-serif; font-size:14px; line-height:28px;">&#9650;</td>
                                <td style="padding-left:10px; font-family:&#39;Inter&#39;,&#39;Helvetica Neue&#39;,Arial,sans-serif; font-size:17px; font-weight:600; color:#edf1f5;">Summit 42</td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <tr>
                    <td class="px-gutter" bgcolor="#0e1a28" style="padding:36px 32px; background-color:#0e1a28; border:1px solid #1b2c3f; border-radius:16px;">
                        <h1 style="margin:0 0 14px; font-family:&#39;Inter&#39;,&#39;Helvetica Neue&#39;,Arial,sans-serif; font-size:24px; line-height:125%; font-weight:600; letter-spacing:-0.02em; color:#edf1f5;">
                            You&#39;re invited
                        </h1>
                        <p style="margin:0 0 28px; font-family:&#39;Inter&#39;,&#39;Helvetica Neue&#39;,Arial,sans-serif; font-size:16px; line-height:150%; color:#9db0c3;">
                            {{#if applicationName}}<strong style="color:#edf1f5;">{{inviterName}}</strong> invited you to use <strong style="color:#edf1f5;">{{applicationName}}</strong>, an app built on Summit 42.{{else}}<strong style="color:#edf1f5;">{{inviterName}}</strong> invited you to build apps with them in <strong style="color:#edf1f5;">{{organizationName}}</strong> on Summit 42.{{/if}}
                        </p>

                        <table role="presentation" border="0" cellspacing="0" cellpadding="0" style="border-collapse:separate;">
                            <tr>
                                <td align="center" bgcolor="#e8875a" style="border-radius:10px; background-color:#e8875a;">
                                    <a class="cta-link" href="{{{acceptUrl}}}"
                                       style="display:inline-block; padding:12px 26px; font-family:&#39;Inter&#39;,&#39;Helvetica Neue&#39;,Arial,sans-serif; font-size:15px; font-weight:600; color:#0a1420; text-decoration:none; border-radius:10px;">
                                        Accept invitation
                                    </a>
                                </td>
                            </tr>
                        </table>

                        <p style="margin:28px 0 6px; font-family:&#39;Inter&#39;,&#39;Helvetica Neue&#39;,Arial,sans-serif; font-size:13px; line-height:150%; color:#7f93a8;">
                            Button not working? Paste this link into your browser:
                        </p>
                        <p style="margin:0; font-family:&#39;Inter&#39;,Arial,sans-serif; font-size:13px; line-height:150%; word-break:break-all;">
                            <a href="{{{acceptUrl}}}" style="color:#f4a77f; text-decoration:underline;">{{{acceptUrl}}}</a>
                        </p>
                    </td>
                </tr>

                <tr>
                    <td class="px-gutter" style="padding:20px 32px 0;">
                        <p style="margin:0; font-family:&#39;Inter&#39;,Arial,sans-serif; font-size:12px; line-height:150%; color:#7f93a8;">
                            This invitation expires in {{expiresInDays}} days. If you weren&#39;t expecting it, you can ignore this email.
                        </p>
                    </td>
                </tr>

            </table>
        </td>
    </tr>
</table>

</body>
</html>', 'Hi,

{{#if applicationName}}
{{inviterName}} invited you to use {{applicationName}}, an app built on Summit 42.
{{else}}
{{inviterName}} invited you to build apps with them in {{organizationName}} on Summit 42.
{{/if}}

Accept the invitation:

{{acceptUrl}}

This invitation expires in {{expiresInDays}} days. If you weren’t expecting it,
you can ignore this email.

Summit 42', '2026-10-22T00:00:00Z', '2026-10-22T00:00:00Z') WITH REFRESH;
INSERT INTO kinotic_system_email_template (id, type, uiBaseUrl, subject, htmlBody, textBody, created, updated) VALUES ('summit-42-verification', 'VERIFICATION', 'http://localhost:5175', 'Verify your email for Summit 42', '<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Verify your email</title>
    <style>
        @media (max-width: 600px) {
            .container { width: 100% !important; }
            .px-gutter { padding-left: 20px !important; padding-right: 20px !important; }
            .cta-link { display: block !important; }
        }
    </style>
</head>
<body style="margin:0; padding:0; background-color:#0a1420;">

<div style="display:none; max-height:0; overflow:hidden; mso-hide:all;">
    Verify your email to finish creating your Summit 42 account.
</div>

<table role="presentation" width="100%" cellpadding="0" cellspacing="0" border="0" bgcolor="#0a1420" style="background-color:#0a1420;">
    <tr>
        <td align="center" style="padding:32px 12px;">
            <table role="presentation" class="container" width="560" cellpadding="0" cellspacing="0" border="0" style="max-width:560px; width:100%;">

                <tr>
                    <td class="px-gutter" style="padding:8px 32px 24px;">
                        <table role="presentation" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                                <td width="28" height="28" bgcolor="#e8875a" align="center" valign="middle"
                                    style="width:28px; height:28px; border-radius:7px; background-color:#e8875a; color:#0a1420; font-family:Arial,sans-serif; font-size:14px; line-height:28px;">&#9650;</td>
                                <td style="padding-left:10px; font-family:&#39;Inter&#39;,&#39;Helvetica Neue&#39;,Arial,sans-serif; font-size:17px; font-weight:600; color:#edf1f5;">Summit 42</td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <tr>
                    <td class="px-gutter" bgcolor="#0e1a28" style="padding:36px 32px; background-color:#0e1a28; border:1px solid #1b2c3f; border-radius:16px;">
                        <h1 style="margin:0 0 14px; font-family:&#39;Inter&#39;,&#39;Helvetica Neue&#39;,Arial,sans-serif; font-size:24px; line-height:125%; font-weight:600; letter-spacing:-0.02em; color:#edf1f5;">
                            Welcome to Summit 42{{#if displayName}}, {{displayName}}{{/if}}
                        </h1>
                        <p style="margin:0 0 28px; font-family:&#39;Inter&#39;,&#39;Helvetica Neue&#39;,Arial,sans-serif; font-size:16px; line-height:150%; color:#9db0c3;">
                            Verify your email address to finish creating your account, then tell Summit 42 what to build.
                        </p>

                        <table role="presentation" border="0" cellspacing="0" cellpadding="0" style="border-collapse:separate;">
                            <tr>
                                <td align="center" bgcolor="#e8875a" style="border-radius:10px; background-color:#e8875a;">
                                    <a class="cta-link" href="{{{verificationUrl}}}"
                                       style="display:inline-block; padding:12px 26px; font-family:&#39;Inter&#39;,&#39;Helvetica Neue&#39;,Arial,sans-serif; font-size:15px; font-weight:600; color:#0a1420; text-decoration:none; border-radius:10px;">
                                        Verify email
                                    </a>
                                </td>
                            </tr>
                        </table>

                        <p style="margin:28px 0 6px; font-family:&#39;Inter&#39;,&#39;Helvetica Neue&#39;,Arial,sans-serif; font-size:13px; line-height:150%; color:#7f93a8;">
                            Button not working? Paste this link into your browser:
                        </p>
                        <p style="margin:0; font-family:&#39;Inter&#39;,Arial,sans-serif; font-size:13px; line-height:150%; word-break:break-all;">
                            <a href="{{{verificationUrl}}}" style="color:#f4a77f; text-decoration:underline;">{{{verificationUrl}}}</a>
                        </p>
                    </td>
                </tr>

                <tr>
                    <td class="px-gutter" style="padding:20px 32px 0;">
                        <p style="margin:0; font-family:&#39;Inter&#39;,Arial,sans-serif; font-size:12px; line-height:150%; color:#7f93a8;">
                            This link expires in 24 hours. If you didn&#39;t sign up for Summit 42, you can ignore this email.
                        </p>
                    </td>
                </tr>

            </table>
        </td>
    </tr>
</table>

</body>
</html>', 'Hi{{#if displayName}} {{displayName}}{{/if}},

Welcome to Summit 42. Verify your email address to finish creating your account:

{{verificationUrl}}

This link expires in 24 hours. If you didn’t sign up for Summit 42,
you can ignore this email.

Summit 42', '2026-10-22T00:00:00Z', '2026-10-22T00:00:00Z') WITH REFRESH;
