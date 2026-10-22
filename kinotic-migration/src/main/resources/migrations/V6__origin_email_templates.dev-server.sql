-- The email templates of the UIs the development server serves: the portal and Summit 42.
-- A UI with no row here sends the built-in templates.

-- The portal's, in the Kinotic style of the built-in templates
INSERT INTO kinotic_origin_email_template (id, origin, kind, subject, htmlBody, textBody, created, updated) VALUES ('00000000-0000-0000-0000-0000000000f1', 'https://dev-portal.kinotic.ai', 'INVITATION', '{{#if applicationName}}You’re invited to join {{applicationName}}{{else}}You’re invited to join {{organizationName}} on Kinotic{{/if}}', '<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>You&#39;re invited</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        @media (max-width: 600px) {
            .container { width: 100% !important; }
            .px-gutter { padding-left: 20px !important; padding-right: 20px !important; }
            .h1-responsive { font-size: 26px !important; }
            .cta-link {
                display: block !important;
                height: 48px !important;
                line-height: 48px !important;
                font-size: 15px !important;
            }
        }
    </style>
</head>
<body style="margin:0; padding:0; background-color:#171717;">

<!-- Preheader (hidden) -->
<div style="display:none; max-height:0; overflow:hidden; mso-hide:all;">
    {{inviterName}} has invited you to {{#if applicationName}}{{applicationName}}{{else}}{{organizationName}}{{/if}}.
</div>

<table role="presentation" width="100%" cellpadding="0" cellspacing="0" border="0"
       bgcolor="#171717" style="background-color:#171717;">
    <tr>
        <td align="center">
            <table role="presentation" class="container" width="600" cellpadding="0" cellspacing="0" border="0"
                   style="max-width:600px; width:100%;">

                <!-- Header / logo -->
                <tr>
                    <td class="px-gutter" style="padding:40px 32px 24px;">
                        <img src="https://kinotic.ai/images/kinotic-wordmark.png" alt="Kinotic" width="158" height="28"
                             style="display:block; border:0; outline:none; text-decoration:none;">
                    </td>
                </tr>

                <!-- Accent bar -->
                <tr>
                    <td class="px-gutter" style="padding:0 32px;">
                        <div style="background-color:#28FEB4; height:2px; line-height:2px; font-size:0;">&nbsp;</div>
                    </td>
                </tr>

                <!-- Body -->
                <tr>
                    <td class="px-gutter" style="padding:40px 32px 32px;">
                        <h1 class="h1-responsive"
                            style="margin:0 0 16px; font-family:&#39;Inter&#39;,&#39;Helvetica Neue&#39;,Arial,sans-serif; font-size:28px; line-height:120%; letter-spacing:-0.02em; font-weight:700; color:#FFFFFF;">
                            You&#39;re invited
                        </h1>

                        <p style="margin:0 0 32px; font-family:&#39;Inter&#39;,&#39;Helvetica Neue&#39;,Arial,sans-serif; font-size:17px; line-height:140%; color:#AFAFB4;">
                            {{#if applicationName}}
                            <strong style="color:#FFFFFF;">{{inviterName}}</strong> has invited you to join
                            <strong style="color:#FFFFFF;">{{applicationName}}</strong>, provided by {{organizationName}}.
                            {{else}}
                            <strong style="color:#FFFFFF;">{{inviterName}}</strong> has invited you to join
                            <strong style="color:#FFFFFF;">{{organizationName}}</strong> on Kinotic.
                            {{/if}}
                            Accept the invitation to set up how you sign in.
                        </p>

                        <!-- CTA (bulletproof) -->
                        <!--[if mso]>
                        <v:roundrect xmlns:v="urn:schemas-microsoft-com:vml"
                                     xmlns:w="urn:schemas-microsoft-com:office:word"
                                     href="{{{acceptUrl}}}"
                                     style="height:44px; v-text-anchor:middle; width:220px;"
                                     arcsize="100%" stroke="f" fillcolor="#FF2D7A">
                            <w:anchorlock/>
                            <center style="color:#FFFFFF; font-family:&#39;Inter&#39;,Arial,sans-serif; font-size:14px; font-weight:500;">
                                Accept invitation
                            </center>
                        </v:roundrect>
                        <![endif]-->
                        <!--[if !mso]><!-- -->
                        <table role="presentation" border="0" cellspacing="0" cellpadding="0"
                               style="border-collapse:separate;">
                            <tr>
                                <td align="center"
                                    bgcolor="#FF2D7A"
                                    style="border-radius:9999px; background-color:#FF2D7A; background-image:linear-gradient(135deg,#FF2D7A 0%,#FF2A55 100%); box-shadow:0 20px 50px rgba(255,45,122,0.28);">
                                    <a class="cta-link"
                                       href="{{{acceptUrl}}}"
                                       style="display:inline-block; padding:0 24px; height:44px; line-height:44px; font-family:&#39;Inter&#39;,&#39;Helvetica Neue&#39;,Arial,sans-serif; font-size:14px; font-weight:500; color:#FFFFFF; text-decoration:none; border-radius:9999px;">
                                        Accept invitation
                                    </a>
                                </td>
                            </tr>
                        </table>
                        <!--<![endif]-->

                        <!-- Fallback URL -->
                        <p style="margin:32px 0 8px; font-family:&#39;Inter&#39;,&#39;Helvetica Neue&#39;,Arial,sans-serif; font-size:13px; line-height:150%; color:#AFAFB4;">
                            Button not working? Paste this URL into your browser:
                        </p>
                        <p style="margin:0; font-family:&#39;Inter&#39;,Arial,sans-serif; font-size:13px; line-height:150%; word-break:break-all;">
                            <a href="{{{acceptUrl}}}"
                               style="color:#28FEB4; text-decoration:underline;">{{{acceptUrl}}}</a>
                        </p>
                    </td>
                </tr>

                <!-- Divider -->
                <tr>
                    <td class="px-gutter" style="padding:0 32px;">
                        <div style="border-top:1px solid #2A2A2A; line-height:1px; font-size:0;">&nbsp;</div>
                    </td>
                </tr>

                <!-- Fine print -->
                <tr>
                    <td class="px-gutter" style="padding:16px 32px 0;">
                        <p style="margin:0; font-family:&#39;Inter&#39;,Arial,sans-serif; font-size:12px; line-height:150%; color:#6b7280;">
                            This invitation expires in {{expiresInDays}} days. If you weren&#39;t expecting it, you can safely ignore this email.
                        </p>
                    </td>
                </tr>

                <!-- Footer -->
                <tr>
                    <td class="px-gutter" style="padding:32px 32px 40px;">
                        <p style="margin:0; font-family:&#39;Inter&#39;,Arial,sans-serif; font-size:12px; color:#6b7280;">
                            — The Kinotic Team
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
{{inviterName}} has invited you to join {{applicationName}}, provided by {{organizationName}}.
{{else}}
{{inviterName}} has invited you to join {{organizationName}} on Kinotic.
{{/if}}

Accept the invitation to set up your account:

{{acceptUrl}}

This invitation expires in {{expiresInDays}} days. If you weren’t expecting it,
you can safely ignore this email.

— The Kinotic Team', '2026-10-22T00:00:00Z', '2026-10-22T00:00:00Z') WITH REFRESH;
INSERT INTO kinotic_origin_email_template (id, origin, kind, subject, htmlBody, textBody, created, updated) VALUES ('00000000-0000-0000-0000-0000000000f2', 'https://dev-portal.kinotic.ai', 'VERIFICATION', 'Verify your Kinotic email', '<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Verify your Kinotic email</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        @media (max-width: 600px) {
            .container { width: 100% !important; }
            .px-gutter { padding-left: 20px !important; padding-right: 20px !important; }
            .h1-responsive { font-size: 26px !important; }
            .cta-link {
                display: block !important;
                height: 48px !important;
                line-height: 48px !important;
                font-size: 15px !important;
            }
        }
    </style>
</head>
<body style="margin:0; padding:0; background-color:#171717;">

<!-- Preheader (hidden) -->
<div style="display:none; max-height:0; overflow:hidden; mso-hide:all;">
    Confirm your email to finish signing up for Kinotic.
</div>

<table role="presentation" width="100%" cellpadding="0" cellspacing="0" border="0"
       bgcolor="#171717" style="background-color:#171717;">
    <tr>
        <td align="center">
            <table role="presentation" class="container" width="600" cellpadding="0" cellspacing="0" border="0"
                   style="max-width:600px; width:100%;">

                <!-- Header / logo -->
                <tr>
                    <td class="px-gutter" style="padding:40px 32px 24px;">
                        <img src="https://kinotic.ai/images/kinotic-wordmark.png" alt="Kinotic" width="158" height="28"
                             style="display:block; border:0; outline:none; text-decoration:none;">
                    </td>
                </tr>

                <!-- Accent bar -->
                <tr>
                    <td class="px-gutter" style="padding:0 32px;">
                        <div style="background-color:#28FEB4; height:2px; line-height:2px; font-size:0;">&nbsp;</div>
                    </td>
                </tr>

                <!-- Body -->
                <tr>
                    <td class="px-gutter" style="padding:40px 32px 32px;">
                        <h1 class="h1-responsive"
                            style="margin:0 0 16px; font-family:&#39;Inter&#39;,&#39;Helvetica Neue&#39;,Arial,sans-serif; font-size:28px; line-height:120%; letter-spacing:-0.02em; font-weight:700; color:#FFFFFF;">
                            Verify your email
                        </h1>

                        <p style="margin:0 0 32px; font-family:&#39;Inter&#39;,&#39;Helvetica Neue&#39;,Arial,sans-serif; font-size:17px; line-height:140%; color:#AFAFB4;">
                            Hi {{displayName}}, welcome to Kinotic.
                            Confirm your email address to finish signing up.
                        </p>

                        <!-- CTA (bulletproof) -->
                        <!--[if mso]>
                        <v:roundrect xmlns:v="urn:schemas-microsoft-com:vml"
                                     xmlns:w="urn:schemas-microsoft-com:office:word"
                                     href="{{{verificationUrl}}}"
                                     style="height:44px; v-text-anchor:middle; width:220px;"
                                     arcsize="100%" stroke="f" fillcolor="#FF2D7A">
                            <w:anchorlock/>
                            <center style="color:#FFFFFF; font-family:&#39;Inter&#39;,Arial,sans-serif; font-size:14px; font-weight:500;">
                                Verify email
                            </center>
                        </v:roundrect>
                        <![endif]-->
                        <!--[if !mso]><!-- -->
                        <table role="presentation" border="0" cellspacing="0" cellpadding="0"
                               style="border-collapse:separate;">
                            <tr>
                                <td align="center"
                                    bgcolor="#FF2D7A"
                                    style="border-radius:9999px; background-color:#FF2D7A; background-image:linear-gradient(135deg,#FF2D7A 0%,#FF2A55 100%); box-shadow:0 20px 50px rgba(255,45,122,0.28);">
                                    <a class="cta-link"
                                       href="{{{verificationUrl}}}"
                                       style="display:inline-block; padding:0 24px; height:44px; line-height:44px; font-family:&#39;Inter&#39;,&#39;Helvetica Neue&#39;,Arial,sans-serif; font-size:14px; font-weight:500; color:#FFFFFF; text-decoration:none; border-radius:9999px;">
                                        Verify email
                                    </a>
                                </td>
                            </tr>
                        </table>
                        <!--<![endif]-->

                        <!-- Fallback URL -->
                        <p style="margin:32px 0 8px; font-family:&#39;Inter&#39;,&#39;Helvetica Neue&#39;,Arial,sans-serif; font-size:13px; line-height:150%; color:#AFAFB4;">
                            Button not working? Paste this URL into your browser:
                        </p>
                        <p style="margin:0; font-family:&#39;Inter&#39;,Arial,sans-serif; font-size:13px; line-height:150%; word-break:break-all;">
                            <a href="{{{verificationUrl}}}"
                               style="color:#28FEB4; text-decoration:underline;">{{{verificationUrl}}}</a>
                        </p>
                    </td>
                </tr>

                <!-- Divider -->
                <tr>
                    <td class="px-gutter" style="padding:0 32px;">
                        <div style="border-top:1px solid #2A2A2A; line-height:1px; font-size:0;">&nbsp;</div>
                    </td>
                </tr>

                <!-- Fine print -->
                <tr>
                    <td class="px-gutter" style="padding:16px 32px 0;">
                        <p style="margin:0; font-family:&#39;Inter&#39;,Arial,sans-serif; font-size:12px; line-height:150%; color:#6b7280;">
                            This link expires in 24 hours. If you didn&#39;t sign up for Kinotic, you can safely ignore this email.
                        </p>
                    </td>
                </tr>

                <!-- Footer -->
                <tr>
                    <td class="px-gutter" style="padding:32px 32px 40px;">
                        <p style="margin:0; font-family:&#39;Inter&#39;,Arial,sans-serif; font-size:12px; color:#6b7280;">
                            — The Kinotic Team
                        </p>
                    </td>
                </tr>

            </table>
        </td>
    </tr>
</table>

</body>
</html>', 'Hi {{displayName}},

Welcome to Kinotic. Verify your email address to activate your account:

{{verificationUrl}}

This link expires in 24 hours. If you didn’t sign up for Kinotic,
you can safely ignore this email.

— The Kinotic Team', '2026-10-22T00:00:00Z', '2026-10-22T00:00:00Z') WITH REFRESH;

-- Summit 42's, in its own style
INSERT INTO kinotic_origin_email_template (id, origin, kind, subject, htmlBody, textBody, created, updated) VALUES ('00000000-0000-0000-0000-0000000000f3', 'https://dev-summit42.kinotic.ai', 'INVITATION', '{{#if applicationName}}{{inviterName}} invited you to {{applicationName}}{{else}}{{inviterName}} invited you to Summit 42{{/if}}', '<!DOCTYPE html>
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
INSERT INTO kinotic_origin_email_template (id, origin, kind, subject, htmlBody, textBody, created, updated) VALUES ('00000000-0000-0000-0000-0000000000f4', 'https://dev-summit42.kinotic.ai', 'VERIFICATION', 'Verify your email for Summit 42', '<!DOCTYPE html>
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
