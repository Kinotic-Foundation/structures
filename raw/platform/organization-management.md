# Organization Management

> How organizations are created, how their users authenticate, and how OIDC providers are wired up.

## Overview

A Kinotic deployment hosts many customer organizations. Each org has its own users, applications, and (optionally) its own enterprise SSO configuration. This page describes how an org is created, who can log in to it, and how the OIDC plumbing is shared across orgs without leaking access between them.

System-level platform operators sign in to the system server, separately from everything described here; see [System Authentication](#system-authentication).

## Mental Model

Eight persistent entities carry the auth state, and one short-lived entity bridges each signup:

<table>
<thead>
  <tr>
    <th>
      Entity
    </th>
    
    <th>
      Purpose
    </th>
    
    <th>
      Lifecycle
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      <code>
        OrgSignupOidcConfiguration
      </code>
    </td>
    
    <td>
      A registration the platform holds with a provider (GitHub) shown as a login/signup button to everyone. Belongs to no organization
    </td>
    
    <td>
      Seeded by SQL migration
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        ApplicationSocialOidcConfiguration
      </code>
    </td>
    
    <td>
      A registration the platform holds with a social provider (Google, Microsoft) that any organization may offer at one of its applications' logins. Belongs to no organization
    </td>
    
    <td>
      Seeded by SQL migration
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        Organization
      </code>
    </td>
    
    <td>
      A customer org
    </td>
    
    <td>
      Created at the end of signup
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        OrganizationOidcConfiguration
      </code>
    </td>
    
    <td>
      A provider record (provider type, account at the provider, clientId) owned by one organization for its own login; its client secret is held in the organization's scope of the secret storage, never on the record
    </td>
    
    <td>
      Created by org admins
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        ApplicationOidcConfiguration
      </code>
    </td>
    
    <td>
      The same record made for the login of one of the organization's applications (<code>
        applicationId
      </code>
      
      )
    </td>
    
    <td>
      Created by org admins
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        Tenant
      </code>
    </td>
    
    <td>
      A tenant of an application whose tenants its administrators manage: a customer of the application, whose users carry its id and whose sites, <code>
        <tenantId>--<applicationId>--<uiName>
      </code>
      
      , sign them in
    </td>
    
    <td>
      Created by org admins, <code>
        TenantService
      </code>
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        TenantOidcConfiguration
      </code>
    </td>
    
    <td>
      The same record made for the login of one tenant of an application (<code>
        applicationId
      </code>
      
      , <code>
        tenantId
      </code>
      
      )
    </td>
    
    <td>
      Created by org admins
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        UserParticipantIdentity
      </code>
    </td>
    
    <td>
      A person. An <strong>
        account
      </strong>
      
       carries neither <code>
        organizationId
      </code>
      
       nor <code>
        applicationId
      </code>
      
       and is unique by email; an application user carries both and belongs to that application alone
    </td>
    
    <td>
      An account is created at signup, by an administrator adding a member, or by invitation; application users by an admin, invitation, or <code>
        createUserOnSignIn
      </code>
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        Membership
      </code>
    </td>
    
    <td>
      An account's membership of one organization (<code>
        <identityId>:<organizationId>
      </code>
      
      ), or of the platform for an operator (<code>
        <identityId>:system
      </code>
      
      ), with its own <code>
        enabled
      </code>
      
       flag
    </td>
    
    <td>
      Created at signup, by an administrator adding a member, or by invitation
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        OidcBinding
      </code>
    </td>
    
    <td>
      A provider identity bound to a user: <code>
        (oidcConfigId, subject)
      </code>
      
       → identity id, once among accounts and once per application among the application's users. With the optional password these are the user's sign-in methods
    </td>
    
    <td>
      Created at signup, by invitation, by an organization's provider claiming the member an administrator added, by <code>
        createUserOnSignIn
      </code>
      
       for an application's user, or by the user from the Profile page
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        PendingSignUp
      </code>
    </td>
    
    <td>
      Holds a signup in flight — the email-verification state for a local signup, or the verified identity for a social one, discriminated by <code>
        authType
      </code>
    </td>
    
    <td>
      Short-lived; deleted once <code>
        /api/auth/org/signup/complete
      </code>
      
       or <code>
        /api/auth/org/signup/social/complete
      </code>
      
       succeeds
    </td>
  </tr>
</tbody>
</table>

A configuration is offered at the one scope its kind names. When the same Okta tenant serves both the org's own login and one of its applications', the org copies the configuration into the application's scope with `OwnedOidcConfigurationService.copy`; the copy is registered with the provider under its own callback URL and keeps its own copy of the client secret. An application offers the platform's Google and Microsoft registrations by naming their provider kinds in its `socialProviders`.

### Two distinct OIDC roles

<table>
<thead>
  <tr>
    <th>
      Role
    </th>
    
    <th>
      Entity
    </th>
    
    <th>
      Offered by
    </th>
    
    <th>
      What the user sees
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      <strong>
        Social sign-in
      </strong>
      
       (org sign-up)
    </td>
    
    <td>
      <code>
        OrgSignupOidcConfiguration
      </code>
    </td>
    
    <td>
      Nothing — every enabled row is offered
    </td>
    
    <td>
      A "Continue with GitHub" button on the login <em>
        and
      </em>
      
       signup pages
    </td>
  </tr>
  
  <tr>
    <td>
      <strong>
        Per-org SSO
      </strong>
      
       (enterprise)
    </td>
    
    <td>
      <code>
        OrganizationOidcConfiguration
      </code>
    </td>
    
    <td>
      The org, at its own login, by creating the configuration
    </td>
    
    <td>
      A "Sign in with <provider>
        " button on the organization's own login page, <code>
          /login/<organizationId>
        </code>
      </provider>
    </td>
  </tr>
  
  <tr>
    <td>
      <strong>
        Application SSO
      </strong>
    </td>
    
    <td>
      <code>
        ApplicationOidcConfiguration
      </code>
    </td>
    
    <td>
      The org, at one of its applications' logins, by creating the configuration for that application
    </td>
    
    <td>
      A "Continue with <name>
        " button on that application's own login page
      </name>
    </td>
  </tr>
  
  <tr>
    <td>
      <strong>
        Application social sign-in
      </strong>
    </td>
    
    <td>
      <code>
        ApplicationSocialOidcConfiguration
      </code>
    </td>
    
    <td>
      The application, by naming the provider type in its <code>
        socialProviders
      </code>
    </td>
    
    <td>
      A "Continue with Google/Microsoft" button on that application's own login page
    </td>
  </tr>
  
  <tr>
    <td>
      <strong>
        Tenant SSO
      </strong>
    </td>
    
    <td>
      <code>
        TenantOidcConfiguration
      </code>
    </td>
    
    <td>
      The org, at the login of one tenant of one of its applications, by creating the configuration for that tenant
    </td>
    
    <td>
      A "Continue with <name>
        " button on the tenant's own sites, before the application's buttons
      </name>
    </td>
  </tr>
</tbody>
</table>

The roles are separate *subtypes* of `OidcConfiguration`, stored in one index under a `type` discriminator the way the kinds of `ParticipantIdentity` are, rather than one type with a flag, because the difference between them is ownership. Every lookup filters on the discriminator and every write is held to its kind's owner, so an org admin configuring SSO can never make it appear as a global button or name a platform secret, and a seeded platform registration can never affect an org's SSO settings.

## Org Creation

There are two entry points, both producing an `Organization` and an admin `ParticipantIdentity` scoped to it. The SPA's `/signup` page currently offers **GitHub only** (a hardcoded button — every Kinotic project is backed by a GitHub repository, so the founding user signs up with GitHub and installs the GitHub App right after). The email/password signup entry point remains fully mounted on the backend; its previous UI (`Signup.vue`) is kept in the tree unrouted until other methods return. Login is not restricted: invitees must be able to sign back in however they joined, so an organization's login page keeps every provider the organization offers plus a password form.

### Email/password signup (backend flow — not currently reachable from the SPA)

```text
1. User loads /signup, enters email + displayName
2. POST /api/auth/org/signup
3. SignUpService.initiateLocalSignUp:
   - rejects if a sign-up is already pending for this email, or if a ParticipantIdentity
     already exists for this email: a person founds one organization at most, and joins
     others by invitation
   - creates a PendingSignUp with a 24h verification token
   - EmailService sends the verification link (logs it instead when email is disabled)
4. User clicks /signup/verify?token=<verificationToken> in their inbox
5. /signup/verify form (VerifyEmail.vue) prompts for orgName + password + confirm
6. POST /api/auth/org/signup/complete  { token, orgName, orgDescription?, password }
7. SignUpService.completeLocalSignUp:
   - validates token, rejects if expired
   - creates Organization (auto-derived id from name)
   - creates the founder's account (UserParticipantIdentity with no organizationId or
     applicationId, enabled=true) with an IdentityCredential (bcrypt hash, separate index
     keyed by the account id)
   - creates the founder's Membership of the organization
   - links Organization.createdBy = account.id
   - deletes the PendingSignUp
8. The gateway establishes the browser session (204 + Set-Cookie); the frontend then calls
   userState.login() to open the realtime connection, authenticated by that session cookie.
```

Email verification is the security gate — no `Organization`, account or membership exists until the link is clicked. With `KINOTIC_EMAIL_ENABLED=false` (the local default) the verification URL is logged to the management server's console instead of sent; copy it into the browser to finish the flow.

### Social-IdP signup

```text
1. User loads /signup, clicks "Sign up with GitHub" (the page's only button today)
2. POST /api/auth/org/signup/social/start/github
3. OrganizationSignupHandler.handleSocialStart:
   - picks the OrgSignupOidcConfiguration whose provider key matches
     (oidcConfigurationService.findEnabledOrgSignupByProvider)
   - generates state/nonce/PKCE, stashes them on the session cookie
   - 302 to <authority>/authorize?...
4. User authenticates at the IdP
5. IdP returns to GET /api/auth/org/login/social/callback/<configId>, the one callback the
   registration is registered with; the flow's purpose, recorded at its start, routes it
6. OrganizationOidcCallbackHandler → OrganizationSignupHandler.complete:
   - validates state/nonce/PKCE, exchanges code for id_token + access_token
   - rejects if email_verified=false in the id_token
   - rejects with account_exists if an account is already bound to (configId, sub) or holds
     the verified email: a person founds one organization at most, and joins others by
     invitation; SignUpService checks again at completion, so two sign-ups of one identity in
     flight at once cannot both found an organization, and the organization is never created
     for an account that cannot be
   - creates a PendingSignUp with the verified subject, configId, email, displayName
   - 302 to /register?token=<verificationToken>
7. /register prompts for orgName (CompleteOrg.vue)
8. POST /api/auth/org/signup/social/complete  { token, orgName, orgDescription? }
9. SignUpService.completeOidcWithNewOrg:
   - validates the pending token
   - creates Organization
   - creates the founder's account (no organizationId or applicationId, enabled=true) with an
     OidcBinding of (configId, sub) to it
   - creates the founder's Membership of the organization
   - links Organization.createdBy
   - deletes the PendingSignUp
10. The gateway establishes the browser session (204 + Set-Cookie). CompleteOrg.vue then
    calls userState.login() to open the realtime connection, authenticated by that session
    cookie. No token travels in the URL.
11. CompleteOrg.vue shows a "Connect GitHub" step explaining that the next GitHub
    round-trip authorizes repository access (distinct from the sign-in, which only
    proved identity). Clicking "Continue to GitHub" calls
    githubAppInstallations.startInstall("/applications") and redirects the whole tab to
    the returned GitHub install URL. The App requests user authorization (OAuth) during
    installation, so GitHub returns to /github/install/callback — the App's first
    registered callback URL — with code, installation_id, and the state minted by
    startInstall. The callback runs completeInstall, which consumes the state, exchanges
    the code with the github-platform credential for the authorizing user's access
    token, and binds the installation only when GitHub's /user/installations reports
    that user can access it (see [Defense in Depth](/platform/defense-in-depth)). It
    then lands on /applications — project creation requires the install, so the new org
    arrives ready. If the install can't start, the page falls back to /applications
    directly.
```

The `PendingSignUp` is consumed once. The `?token=` in the redirect to `/register` is the short-lived `PendingSignUp` verification token, not an auth credential — the actual login is established by the session cookie set when the org-naming POST succeeds.

GitHub follows the same flow with one difference in step 6: GitHub is OAuth 2.0 only and issues no id_token, so its configuration row sets the endpoints explicitly instead of relying on discovery — `authorizationUri`/`tokenUri` drive the code flow, `userInfoUri` supplies `sub` and the display name, and `userEmailsUri` supplies the email plus its `verified` flag (primary address preferred). Any curated row that sets these fields takes this path; nothing about it is GitHub-specific in code, and only a curated row can set them. Issuer, audience, and nonce validation don't apply (there is no token to carry them); the state match and the direct TLS code exchange are the security boundary.

## Members

An organization's administrators add its members on the Members page, one action for every case: **Add member**, with the person's email, an optional display name and a switch, on by default, that sends them an invitation email (`MemberService.addMember(email, displayName, sendInvitation)`, `Kinotic.members.addMember`).

- **An address with no account yet** gets one, with no sign-in method, and its membership at once, so the person appears among the members as *Pending*. With the switch on, the invitation they receive claims the account: they set a password, or sign in through one of the organization's providers under the same address. With the switch off, nothing is sent and the person signs in through one of the organization's own providers (`OrganizationOidcConfiguration`), whose first sign-in under the invited address claims the account — so the switch can only be off when the organization has one, and the server refuses the request otherwise, revealing nothing about the address.
- **An address that already has an account** is emailed an invitation whatever the switch says, since joining someone's account to an organization needs their acceptance; they appear once they accept, and the administrator is told an invitation was sent. Accepting with the account's password, or through one of the organization's providers whose verified email is the invited one, joins the account.
- **Pending members** are listed with the invitations alike, each with *Send invitation* (`MemberService.sendInvitation(email)`, which sends the one they have again or a new one for a member added without) and *Remove*. Removing a pending member deletes the account nobody signed in to and the invitation it never accepted; removing a member who has signed in leaves their account and other organizations as they are.
- **An application's users** are still invited (`MemberService.inviteMember(email, displayName, applicationId)`), since their rows belong to the application and are created when the invitation is accepted.

An organization's own provider therefore signs in only the members its administrators added: a person the provider verifies whose address no pending member holds gets `no_account`, and `createUserOnSignIn` is refused on an `OrganizationOidcConfiguration`. Nothing an organization's provider asserts can reach an account the organization did not create, and an account someone already signs in to joins only by its holder's acceptance.

## User Login

A login is addressed to an organization. An account may be a member of any number of organizations, each a `Membership` of its own, so nothing about the person alone says where they are signing in; the organization's id, the slug of its hostnames (see [Applications and Projects](/apps/application-structure/applications-and-projects#applications)), is what every login route and page is addressed by. The portal has two login pages:

- `/login`, the landing page, for someone whose organization is not yet known: the platform's social buttons, which sign in without naming an organization when the identity belongs to exactly one and otherwise offer the ones it belongs to; an organization field that leads to that organization's page (prefilled with the organization this browser last signed in to); and a "Don't know your organization?" link that emails the person the organizations their address belongs to.
- `/login/<organizationId>`, the organization's page (`OrganizationLoginPage.vue`), which offers exactly what that organization's login supports: one button per configuration `GET /api/auth/org/login/<organizationId>/providers` lists — the platform's org sign-up registrations (GitHub) followed by the organization's own `OrganizationOidcConfiguration`s — and an email + password form. The organization's own host, `<organizationId>.<sitesDomain>`, redirects here.

No route asks about an email first: the page offers every method up front, and the password login answers a generic `401`, so nothing tells whether an email has an account anywhere. What the page does disclose, to anyone who knows an organization's id, is that the organization exists and the names and provider kinds of the configurations its login offers, as any SSO login page does; the id is already public as the organization's hostname, and the configurations are the buttons the page shows. A signed-in member of several organizations switches between them without signing in again: the account menu's **Organizations** list posts `POST /api/auth/org/switch { organizationId }`, which re-establishes the session for the chosen membership and reloads the portal.

### Password

The form posts email + password to the organization's login route, which verifies the credential and **establishes a browser session** — there is no client-held token. The `fetch` uses `credentials: 'include'` so the gateway's `Set-Cookie` is stored even cross-origin:

```text
1. POST /api/auth/org/login/<organizationId> { email, password }   (credentials: 'include')
2. OrganizationLoginHandler.handleLogin → AuthEndpointSupport.handlePasswordLogin:
   - LocalAuthenticationService.authenticateLocal(email, password, null, null)
       finds the account with that email (an application user with the same email is
       another row and never signs in here), requires it enabled, loads its
       IdentityCredential and verifies the bcrypt hash; an account with no password
       fails here
   - ParticipantIdentityService.resolveParticipant(account.id, organizationId, null)
       requires the account's enabled Membership of that organization and makes the
       OrganizationParticipant from the two
   - on success: establishSession(ctx, participant) puts it on the Vert.x session
                 (regenerating the session id), then 204 + Set-Cookie
   - on any failure: 401 "Invalid credentials"
                     (deliberately generic — covers unknown email, wrong password,
                      no password, disabled account or membership, not a member)
3. OrganizationLoginPage.vue calls userState.login() → Kinotic.connect().
   The default resolution supplies no auth headers in a browser, so the
   WebSocket upgrade is authenticated by the session cookie set in step 2.
```

The SPA never exchanges the password for a token and never sends raw passwords over the WebSocket. Non-UI clients (CLI, automation) do not use this path — they authenticate directly at the upgrade instead; the table in "The WebSocket upgrade" below covers what each client type presents.

### A provider the organization offers

Each button on the organization's page is a form posting to the organization's start route for that configuration. The route also answers `GET`, so a link — from a bookmark, or from the organization's own tile in its provider's application launcher — starts the same flow:

```text
1. POST|GET /api/auth/org/login/<organizationId>/start/<configId>?referer=<spa-path>
   - the router guard bounced an unauthenticated navigation to /login?referer=<spa-path>,
     and the login pages forward it here; a provider sign-in leaves the SPA, so the path
     cannot be kept client side the way the password path keeps it
2. OrganizationLoginHandler.handleStart:
   - an enabled OrgSignupOidcConfiguration under that id, which every organization's
     login offers; otherwise the organization's own configuration under it
     (oidcConfigurationService.findOffered(configId, organizationId, null)); 400 for
     anything else
   - stashes the organization on the flow session, with the referer when it names a
     path within the SPA, then state/nonce/PKCE as for signup, and 302 to the IdP
   - the completed login is bound to the page that started it, or to the portal when a
     GET link, which names no page, started it
3. IdP returns to the callback registered for the configuration:
     GET /api/auth/org/login/social/callback/<configId>   an org sign-up registration
     GET /api/auth/org/login/sso/callback/<configId>      the organization's own configuration
4. OrganizationLoginHandler → AuthEndpointSupport.completeOidcLogin:
   - validates state/nonce, exchanges code, validates id_token (sub + email_verified)
   - looks up the account bound to (configId, sub) (ParticipantIdentityService.
     findByOidcIdentity) and its Membership of the stashed organization
   - no account bound to the identity: through the organization's own configuration, the
                 member an administrator added under the identity's verified email that
                 nobody has signed in as yet is claimed — the identity is bound to that
                 account as its first sign-in method and it signs in; otherwise 302
                 /login?error=no_account
   - no membership: 302 /login?error=no_account
   - a disabled account or membership: 302 /login?error=account_disabled
   - otherwise: establishSession(ctx, participant), then redirectSuccess → 302 to the
       stored referer, or the SPA root when the flow stored none, with Set-Cookie.
       No token travels in the URL.
5. The SPA loads with the session cookie set; userState.login() opens the realtime
   connection, authenticated by that cookie.
```

### Social button on the landing page

The landing page's buttons are populated from `GET /api/auth/org/login/providers`, which lists the unique provider keys present in the org sign-up configs (the invite-hint UI on `MembersPage` reads the same endpoint). A sign-in there names no organization, so its callback finds the one organization the identity is a member of:

```text
1. POST /api/auth/org/login/social/start/github?referer=<spa-path>
2. OrganizationLoginHandler.handleSocialStart:
   - finds the OrgSignupOidcConfiguration with provider="github"
     (oidcConfigurationService.findEnabledOrgSignupByProvider)
   - same flow-session setup as above, with no organization stashed, then 302 to the IdP
3. IdP returns to GET /api/auth/org/login/social/callback/<configId>
4. OrganizationLoginHandler.handleSocialCallback → completeOidcLogin:
   - finds the account bound to (configId, sub) and its enabled Memberships
     (MembershipService.findMemberships)
   - exactly one: signs in to that organization as above
   - no account, or no organization membership: 302 /login?error=no_account
   - a disabled account, or one whose every organization membership is disabled:
     302 /login?error=account_disabled
   - several: their ids are kept in the session for the page that started the flow and
     302 /login?error=multiple_organizations&referer=<spa-path> — the account has proven who
     it is, so the landing page reads the ids from GET /api/auth/org/login/choices, answered
     for two minutes and until the page starts a login at an organization, and offers those
     organizations to choose from; the person signs in at the chosen one's own page, which
     lands on the referer. The redirect carries no ids, so the page that started the flow
     learns only the code
```

### Finding one's organizations

`POST /api/auth/org/login/find { email, turnstileToken }` emails the address every organization its account holds an enabled membership of, each with a link to that organization's login page (`OrganizationService.sendMembershipsEmail`); a disabled account is told of none, as it can sign in to none. The route always answers `204` and the email is sent whether or not the address belongs to any organization — one that belongs to none says so — so the request reveals nothing about the address. With `KINOTIC_EMAIL_ENABLED=false` the links are logged to the management server's console instead of sent.

The route is open to anyone and sends an email on every call, so it requires a person at the browser: a Cloudflare Turnstile token earned on the page, verified by the management server with the site's secret (`TurnstileVerifier`, configured under `kinotic.managementServer.turnstile.*`). `GET /api/auth/org/login/find` answers `{turnstileSiteKey}`, the site whose challenge the landing page renders before it enables the button, and a request whose token is missing, already verified or refused answers `400`. An environment with no Turnstile site, such as the e2e stack, turns the requirement off with `kinotic.managementServer.turnstile.enabled=false`, and the `GET` answers `{}`; development uses Cloudflare's always-passing test site.

### Platform operators (SYSTEM scope)

A platform operator is an account holding the platform membership (`Membership` with no `organizationId`, id `<identityId>:system`). They sign in through a separate, password-only route used by the system console (there is no signup, SSO, or social path for SYSTEM scope):

```text
1. POST /api/auth/system/login { email, password }   (credentials: 'include')
2. SystemLoginHandler.handleLogin → AuthEndpointSupport.handlePasswordLogin:
   - LocalAuthenticationService.authenticateLocal(email, password, null, null)
       verifies the account's password, then resolveParticipant(account.id, null, null)
       requires its enabled platform membership — an account without one, however
       many organizations it belongs to, can never authenticate here
   - success/failure behave exactly like the org password branch:
     204 + Set-Cookie, or a generic 401
```

The session established here is the same browser-session mechanism as every other login path; `/api/auth/me` and `/api/auth/logout` apply unchanged.

The console lists them at **Members → Users**, read-only, from `SystemMemberService.findUsers` in the system zone, which lists the accounts holding the platform membership. Operators are created outside the console — by the migration fixtures, or by the identity provider once SYSTEM scope is behind Entra — so the page reports who holds access rather than granting it.

### The WebSocket upgrade (the final step in every path)

Authentication happens at the WebSocket upgrade (handshake), not in a STOMP CONNECT frame. How the upgrade is authenticated depends on the client:

<table>
<thead>
  <tr>
    <th>
      Client
    </th>
    
    <th>
      Upgrade credentials
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      Browser SPA
    </td>
    
    <td>
      Session cookie established by the REST login; the default credential resolution supplies no headers
    </td>
  </tr>
  
  <tr>
    <td>
      Node client, credentials
    </td>
    
    <td>
      <code>
        clientId
      </code>
      
      , <code>
        clientSecret
      </code>
      
      , plus <code>
        organizationId
      </code>
      
       / <code>
        applicationId
      </code>
      
       scope headers (via <code>
        BasicCredentialsResolver
      </code>
      
       or the <code>
        KINOTIC_CLIENT_*
      </code>
      
       environment variables)
    </td>
  </tr>
  
  <tr>
    <td>
      Node client, Bearer token
    </td>
    
    <td>
      <code>
        Authorization: Bearer <jwt>
      </code>
      
       (via <code>
        BearerCredentialsResolver
      </code>
      
      ); a Kinotic JWT carries the <code>
        organizationId
      </code>
      
       / <code>
        applicationId
      </code>
      
       claims
    </td>
  </tr>
</tbody>
</table>

The browser SPA never holds a JWT — its login establishes a session cookie and that cookie authenticates the upgrade. The Bearer path is for non-browser clients: the CLI obtains an access token through the OAuth device-code grant at `POST /api/auth/oauth/token`, which mints a Kinotic JWT carrying `sub` / `email` / `organizationId` / `applicationId`. For that path the management server validates the JWT signature against its signing keys and creates the `Session`. Every path admits only the server's own kind of participant: a credential that proves an identity of another scope fails as invalid (see [System Security](/platform/system-security#authentication-methods)). The CLI persists a rotating refresh token to mint fresh access tokens before each connect.

## Provider-Specific Quirks

OIDC is a standard, but providers diverge on a few details. The validation helpers in `OAuth2Util` (`isIssuerValid`, `isEmailVerified`) handle these declaratively — the provider key on `OidcConfiguration` selects the right behaviour. No provider needs handler-level branching: a row either has an `authority` for OIDC discovery — derived from the organization's `providerAccount` for an organization's row, seeded for a curated one — or, for a curated row only, sets `authorizationUri`/`tokenUri`/`userInfoUri`/`userEmailsUri`/`scopes` explicitly, and every handler consumes the same claims shape either way.

<table>
<thead>
  <tr>
    <th>
      Provider key
    </th>
    
    <th>
      <code>
        iss
      </code>
      
       shape
    </th>
    
    <th>
      <code>
        email_verified
      </code>
      
       claim
    </th>
    
    <th>
      Other notes
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      <code>
        github
      </code>
    </td>
    
    <td>
      No id_token — identity is fetched from the row's <code>
        userInfoUri
      </code>
      
       with the exchanged access token, so issuer/nonce validation doesn't apply
    </td>
    
    <td>
      Synthesized from the row's <code>
        userEmailsUri
      </code>
      
       — the primary verified address is preferred, any verified address accepted
    </td>
    
    <td>
      OAuth 2.0 only, so the row sets every endpoint explicitly. <code>
        sub
      </code>
      
       is the numeric GitHub account id. Uses the kinotic-ai GitHub App's user-authorization OAuth credential — the same credential that verifies installation ownership when an org links GitHub, so the row must never point at a different OAuth client. The app needs the "Email addresses: read-only" permission, and its scope param is ignored (GitHub App permissions govern access)
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        google
      </code>
    </td>
    
    <td>
      Fixed <code>
        https://accounts.google.com
      </code>
    </td>
    
    <td>
      Emitted as boolean — required <code>
        true
      </code>
      
       to accept
    </td>
    
    <td>
      <code>
        sub
      </code>
      
       is per-OAuth-client pairwise (different Kinotic deployments see different <code>
        sub
      </code>
      
      s for the same person — fine since we key on <code>
        (sub, configId)
      </code>
      
      )
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        azure-ad
      </code>
      
       (single tenant)
    </td>
    
    <td>
      Fixed <code>
        https://login.microsoftonline.com/<tenant-id>/v2.0
      </code>
    </td>
    
    <td>
      Not emitted — email-presence is treated as verified (Entra verifies via tenant domain ownership)
    </td>
    
    <td>
      Used by per-org SSO configs that pin to a specific Entra tenant
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        azure-ad
      </code>
      
       (multi-tenant <code>
        /common
      </code>
      
       or <code>
        /organizations
      </code>
      
      )
    </td>
    
    <td>
      Per-user — substitutes user's home tenant id; we re-validate by extracting <code>
        tid
      </code>
      
       from the same signed JWT
    </td>
    
    <td>
      Same as single-tenant — not emitted, presence trusted
    </td>
    
    <td>
      Discovery doc returns a literal <code>
        {tenantid}
      </code>
      
       placeholder; we set <code>
        validateIssuer=false
      </code>
      
       and clear <code>
        jwtOptions.issuer
      </code>
      
       for this case so Vert.x's strict comparison doesn't reject
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        apple
      </code>
    </td>
    
    <td>
      Fixed <code>
        https://appleid.apple.com
      </code>
    </td>
    
    <td>
      Not emitted — presence trusted
    </td>
    
    <td>
      Email is <strong>
        only present on first sign-in
      </strong>
      
      ; later tokens omit it. Returning users are recognised by stable <code>
        sub
      </code>
      
      . May be a <code>
        …@privaterelay.appleid.com
      </code>
      
       private-relay address — still verified
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        okta
      </code>
    </td>
    
    <td>
      Fixed, <code>
        https://<okta domain>
      </code>
      
      , derived from the organization's Okta domain
    </td>
    
    <td>
      Emitted as boolean — required <code>
        true
      </code>
    </td>
    
    <td>
      Discovery + standard validation
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        keycloak
      </code>
      
      , <code>
        auth0
      </code>
      
      , <code>
        salesforce
      </code>
      
      , <code>
        amazon-cognito
      </code>
      
      , <code>
        oidc
      </code>
      
       (generic)
    </td>
    
    <td>
      Fixed (issuer URL of the realm/tenant)
    </td>
    
    <td>
      Emitted as boolean — required <code>
        true
      </code>
    </td>
    
    <td>
      Discovery + standard validation; not yet offered to organizations, since no issuer template exists for them
    </td>
  </tr>
</tbody>
</table>

`isEmailVerified` and `isIssuerValid` are the only places these differences live in code. Adding a curated provider doesn't require code changes — an OIDC-compliant provider seeds a row with an `authority`, and a plain-OAuth2 provider seeds a row with explicit endpoint fields; only providers with non-standard claim quirks (Apple's first-login-only email, Microsoft's `/common` issuer template) need to be classified explicitly in those helpers. Offering a type to organizations is one line on `OidcProviderType`: its issuer template and the pattern the organization's account must match.

## Per-Org SSO Configuration

An `OrganizationOidcConfiguration` is offered at the org's own login page, `/login/<organizationId>`, by existing. An organization admin manages the organization's configurations through the published `OwnedOidcConfigurationService` (`Kinotic.ownedOidcConfigurations` in `@kinotic-ai/management-api`, and an MCP tool), which acts for the caller's organization alone; the portal has no page for it yet.

1. Create the `OrganizationOidcConfiguration` with `OwnedOidcConfigurationService.save(configuration, clientSecret)`, naming the provider type (`azure-ad`, `okta` or `google`) and the organization's `providerAccount` at it; the authority is derived from the kind's template, so no URL is given, and the `organizationId` is the caller's whatever was sent. A null secret makes a public client (`confidentialClient` false); a secret is held in the organization's own scope of the secret storage — the row itself never names one, and nothing reads it back through the service. `createUserOnSignIn` is refused on an organization's configuration: the organization's administrators add its members (see [Members](#members)), and the provider signs in the member it verifies under a pending member's address, claiming the account on the first sign-in, or the account its identity is already bound to. A person the provider verifies whose address is no member's gets `no_account`; one whose address already has an account that someone signs in to is not claimed, and joins by accepting the invitation the administrator's Add member sent.
2. To offer the same provider at one of the org's applications, copy it there with `OwnedOidcConfigurationService.copy(configurationId, applicationId)`, which makes an `ApplicationOidcConfiguration` with a callback URL of its own, `<appServerApiBaseUrl of the application>/api/auth/app/login/oidc/callback/<copyId>`, to register with the provider as well.
3. Add the redirect URI `<kinotic.managementServer.apiBaseUrl>/api/auth/org/login/sso/callback/<configId>` to the IdP app registration. It is the only one the configuration needs: a login, an invitation accepted through the provider, and a member connecting the provider to their account from the Profile page all return to it, and the flow's purpose, recorded on the session when it starts, says which it is. For a same-origin deploy that base is the portal's own origin; for a split-origin deploy (the SPA on the sites Worker, the backend on AKS) it is the backend's hostname, so the IdP returns the browser to the management server, not the SPA.

A user who logs in via this path lands at the `/api/auth/org/login/sso/callback/:configId` handler — the IdP doesn't care that the configId is org-scoped instead of platform. The provider's application launcher can link straight to `<kinotic.managementServer.apiBaseUrl>/api/auth/org/login/<organizationId>/start/<configId>`, which starts the same flow from a plain `GET`.

## System Authentication

System-level operators are accounts holding the platform membership and no organization's, since an account's memberships are all of one kind; they sign in to the system server with an email and password, `POST /api/auth/system/login`, and the plan is to move it to Microsoft Entra, separate from the routes documented above. An organization's member holds no platform membership, so the system login refuses them as it refuses a wrong password, and an operator holds no organization's, so every organization login refuses them the same way. The curated social providers are intentionally limited to end-user self-service signup and grant org-scoped access only, so no organization route can produce a `SYSTEM`-scoped session.

## Endpoint Reference

Each handler is a component of the module that serves it, so a server mounts the routes of the modules it loads, on its own gateway port (org `58503`, system `58504`, app `58505` by default):

<table>
<thead>
  <tr>
    <th>
      Handler
    </th>
    
    <th>
      Module
    </th>
    
    <th>
      Management server
    </th>
    
    <th>
      System server
    </th>
    
    <th>
      App server
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      <code>
        OrganizationOidcCallbackHandler
      </code>
      
      , <code>
        OrganizationLoginHandler
      </code>
      
      , <code>
        OrganizationSignupHandler
      </code>
      
      , <code>
        AccountSignInMethodHandler
      </code>
      
      , <code>
        InviteHandler
      </code>
      
      , <code>
        DeviceAuthorizationHandler
      </code>
      
      , <code>
        GitHubWebhookHandler
      </code>
    </td>
    
    <td>
      <code>
        kinotic-server-management
      </code>
    </td>
    
    <td>
      ✓
    </td>
    
    <td>
      
    </td>
    
    <td>
      
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        SystemLoginHandler
      </code>
    </td>
    
    <td>
      <code>
        kinotic-server-system
      </code>
    </td>
    
    <td>
      
    </td>
    
    <td>
      ✓
    </td>
    
    <td>
      
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        ApplicationLoginHandler
      </code>
      
      , <code>
        ApplicationSocialLoginHandler
      </code>
    </td>
    
    <td>
      <code>
        kinotic-server-app
      </code>
    </td>
    
    <td>
      
    </td>
    
    <td>
      
    </td>
    
    <td>
      ✓
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        SessionEndpointHandler
      </code>
      
      , <code>
        OAuthServerHandler
      </code>
    </td>
    
    <td>
      <code>
        kinotic-domain
      </code>
    </td>
    
    <td>
      ✓
    </td>
    
    <td>
      ✓
    </td>
    
    <td>
      ✓
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        McpJsonRpcHandler
      </code>
    </td>
    
    <td>
      <code>
        kinotic-management-api
      </code>
    </td>
    
    <td>
      ✓
    </td>
    
    <td>
      ✓
    </td>
    
    <td>
      
    </td>
  </tr>
</tbody>
</table>

The app server serves every application at an API host of its own, `<organizationId>--<applicationId>` under `kinotic.appServer.apiBaseUrl` (see [Configuration](/platform/configuration#application-api-hosts)). Its login and OAuth routes act for the application whose host the request is addressed to and answer `404` on any other host, and that host is the application's OAuth issuer. The one exception is a sign-in through a platform social registration (`ApplicationSocialOidcConfiguration`): a provider returns the browser only to a redirect URI registered in advance, and the registration is shared by every application that offers it, so its `authorize` and `callback` routes are served at `kinotic.appServer.apiBaseUrl` itself, the shared host, and the verified sign-in is handed to the application's own host under a single-use code (`SocialLoginHandoffService`, an Ignite cache with a one-minute TTL) where the session is established. The code completes the sign-in only in the browser that started it: the start at the application's host put a value in that browser's session, the shared host carries it through its own session into the handoff, and the completion requires the browser to present it again, so a completion URL passed to another person signs no one in.

CORS for the server's UI origin is applied at the router root. A Vert.x `SessionHandler` covers every `/api/*` route (and the STOMP WebSocket path), so the same session cookie carries the OIDC roundtrip state and the post-login identity; the cookie is `HttpOnly`, `Secure`, `SameSite=Lax`, with a configurable timeout (`kinotic.api-gateway.session-timeout`).

Routes are namespaced under `/api/auth/...`. Organization login and signup are the SPA's paths; the application-login, OAuth, and invite routes are listed for completeness.

<table>
<thead>
  <tr>
    <th>
      Method
    </th>
    
    <th>
      Path
    </th>
    
    <th>
      Owner
    </th>
    
    <th>
      Purpose
    </th>
  </tr>
</thead>

<tbody>
  <tr>
    <td>
      <code>
        GET
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/org/login/providers
      </code>
    </td>
    
    <td>
      <code>
        OrganizationLoginHandler
      </code>
    </td>
    
    <td>
      Unique social provider keys for the landing page's button row
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        GET
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/org/login/choices
      </code>
    </td>
    
    <td>
      <code>
        OrganizationLoginHandler
      </code>
    </td>
    
    <td>
      The organization ids the last landing-page sign-in the requesting page started found the identity a member of, for two minutes and until the page starts a login at an organization; <code>
        []
      </code>
      
       otherwise
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        GET
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/org/login/find
      </code>
    </td>
    
    <td>
      <code>
        OrganizationLoginHandler
      </code>
    </td>
    
    <td>
      <code>
        {turnstileSiteKey}
      </code>
      
      , the Cloudflare Turnstile site whose token the <code>
        POST
      </code>
      
       requires; <code>
        {}
      </code>
      
       where the deployment requires none
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/org/login/find
      </code>
    </td>
    
    <td>
      <code>
        OrganizationLoginHandler
      </code>
    </td>
    
    <td>
      <code>
        {email, turnstileToken}
      </code>
      
      ; emails the address its organizations' login links; <code>
        204
      </code>
      
       once the token verifies, whether or not the address belongs to an organization; <code>
        400
      </code>
      
       for a token missing or refused
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/org/login/social/start/:provider
      </code>
    </td>
    
    <td>
      <code>
        OrganizationLoginHandler
      </code>
    </td>
    
    <td>
      Begin a social-button login from the landing page, addressed to no organization; redirects to the IdP
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        GET
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/org/login/social/callback/:configId
      </code>
    </td>
    
    <td>
      <code>
        OrganizationOidcCallbackHandler
      </code>
    </td>
    
    <td>
      The one callback a platform registration is registered with: a login signs in to the stashed organization, or the one organization the identity is a member of; a sign-up, an invitation or a connect is completed by its own handler, as the flow's recorded purpose says
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        GET
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/org/login/sso/callback/:configId
      </code>
    </td>
    
    <td>
      <code>
        OrganizationOidcCallbackHandler
      </code>
    </td>
    
    <td>
      The one callback an organization's own configuration is registered with: a login establishes the session, an invitation or a connect is completed by its own handler
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        GET
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/org/login/:orgId/providers
      </code>
    </td>
    
    <td>
      <code>
        OrganizationLoginHandler
      </code>
    </td>
    
    <td>
      Enabled configurations the organization's login offers, <code>
        [{id, name, provider}]
      </code>
      
      ; <code>
        404
      </code>
      
       for an unknown organization
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/org/login/:orgId
      </code>
    </td>
    
    <td>
      <code>
        OrganizationLoginHandler
      </code>
    </td>
    
    <td>
      Email + password, scoped to the organization; on success establishes the browser session (204 + Set-Cookie)
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
      
      , <code>
        GET
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/org/login/:orgId/start/:configId
      </code>
    </td>
    
    <td>
      <code>
        OrganizationLoginHandler
      </code>
    </td>
    
    <td>
      Begin a sign-in to the organization through a configuration its login offers; redirects to the IdP; <code>
        400
      </code>
      
       for one not offered
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/org/switch
      </code>
    </td>
    
    <td>
      <code>
        OrganizationLoginHandler
      </code>
    </td>
    
    <td>
      <code>
        {organizationId}
      </code>
      
      ; re-establishes the signed-in account's session for another of its enabled memberships (204); <code>
        401
      </code>
      
       without a login, <code>
        403
      </code>
      
       for an organization it is not a member of, <code>
        400
      </code>
      
       without an id
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/account/methods/start/:configurationId
      </code>
    </td>
    
    <td>
      <code>
        AccountSignInMethodHandler
      </code>
    </td>
    
    <td>
      Connect a configuration to the signed-in account: a platform registration, or an organization's own configuration when the account may sign in there (<code>
        403
      </code>
      
       otherwise); redirects to the IdP, whose callback binds the identity and 302s to <code>
        /account/profile?connected=<name>
      </code>
      
      , or <code>
        ?error=<code>
      </code>
      
       when it is bound to another account (<code>
        identity_in_use
      </code>
      
      ) or may not sign in (<code>
        invalid_token
      </code>
      
      , <code>
        email_not_verified
      </code>
      
      )
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/org/signup
      </code>
    </td>
    
    <td>
      <code>
        OrganizationSignupHandler
      </code>
    </td>
    
    <td>
      Submit email + displayName; sends verification email
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/org/signup/complete
      </code>
    </td>
    
    <td>
      <code>
        OrganizationSignupHandler
      </code>
    </td>
    
    <td>
      Verify token + orgName + password; creates Organization, the founder's account and membership; establishes session
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/org/signup/social/start/:provider
      </code>
    </td>
    
    <td>
      <code>
        OrganizationSignupHandler
      </code>
    </td>
    
    <td>
      Begin social-IdP signup; redirects to the IdP
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/org/signup/social/complete
      </code>
    </td>
    
    <td>
      <code>
        OrganizationSignupHandler
      </code>
    </td>
    
    <td>
      Consume PendingSignUp; create Org, the founder's account bound to the GitHub identity, and membership; establishes session
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/system/login
      </code>
    </td>
    
    <td>
      <code>
        SystemLoginHandler
      </code>
    </td>
    
    <td>
      System operator email + password; establishes session
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        GET
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/app/login/providers[?tenantId]
      </code>
    </td>
    
    <td>
      <code>
        ApplicationLoginHandler
      </code>
    </td>
    
    <td>
      Enabled configurations the application's login offers, <code>
        [{id, name, provider}]
      </code>
      
      , or the tenant's login from one of the tenant's sites or when <code>
        tenantId
      </code>
      
       names one; <code>
        404
      </code>
      
       for a tenant the application does not have
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/app/login
      </code>
    </td>
    
    <td>
      <code>
        ApplicationLoginHandler
      </code>
    </td>
    
    <td>
      <code>
        {email, password[, tenantId]}
      </code>
      
      , scoped to the application, or to the tenant from one of the tenant's sites or when <code>
        tenantId
      </code>
      
       names one; establishes session; <code>
        403
      </code>
      
       from a page that is not one of the application's UIs or is a site of another tenant
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/app/login/oidc/start/:configId[?tenantId]
      </code>
    </td>
    
    <td>
      <code>
        ApplicationLoginHandler
      </code>
    </td>
    
    <td>
      Begin a sign-in through a configuration the login offers; <code>
        {redirect}
      </code>
      
       naming the IdP's authorization URL, or the shared host's <code>
        authorize
      </code>
      
       URL for a platform social registration, carrying a <code>
        binding
      </code>
      
       the start put in the browser's session; <code>
        400
      </code>
      
       for one not offered, <code>
        403
      </code>
      
       from a page that is not one of the application's UIs
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        GET
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/app/login/oidc/callback/:configId
      </code>
    </td>
    
    <td>
      <code>
        ApplicationLoginHandler
      </code>
    </td>
    
    <td>
      App IdP returns here; establishes session for the scope the flow started for, 302 to the page that started the login; a failed callback 302s to that page's <code>
        /login
      </code>
      
       with the error, to the primary UI's when no flow session names one
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        GET
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/app/login/social/authorize
      </code>
    </td>
    
    <td>
      <code>
        ApplicationSocialLoginHandler
      </code>
    </td>
    
    <td>
      Shared host: begins the provider round-trip for <code>
        organizationId
      </code>
      
      , <code>
        applicationId
      </code>
      
      , <code>
        configurationId
      </code>
      
      , <code>
        tenantId
      </code>
      
       when the sign-in is to a tenant, the page at <code>
        origin
      </code>
      
      , which must be one of the application's UIs, and the <code>
        binding
      </code>
      
       the start put in the browser's session at the application's host; redirects to the IdP
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        GET
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/app/login/social/callback/:configId
      </code>
    </td>
    
    <td>
      <code>
        ApplicationSocialLoginHandler
      </code>
    </td>
    
    <td>
      Shared host: the IdP returns here; verifies the identity, 302 to the application's host with a single-use code; a failed callback 302s to the starting page's <code>
        /login
      </code>
      
       with the error, <code>
        400
      </code>
      
       when no flow session names one
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        GET
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/app/login/social/complete/:code
      </code>
    </td>
    
    <td>
      <code>
        ApplicationSocialLoginHandler
      </code>
    </td>
    
    <td>
      Consumes the code; establishes session, 302 to the page that started the login; <code>
        handoff_invalid
      </code>
      
       for a spent, expired or foreign code, or one presented by a browser other than the one that started the sign-in
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        GET
      </code>
    </td>
    
    <td>
      <code>
        /.well-known/oauth-authorization-server
      </code>
    </td>
    
    <td>
      <code>
        OAuthServerHandler
      </code>
    </td>
    
    <td>
      RFC 8414 authorization-server metadata
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        GET
      </code>
    </td>
    
    <td>
      <code>
        /.well-known/oauth-protected-resource[/mcp]
      </code>
    </td>
    
    <td>
      <code>
        McpJsonRpcHandler
      </code>
    </td>
    
    <td>
      RFC 9728 resource metadata for <code>
        /mcp
      </code>
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /mcp
      </code>
    </td>
    
    <td>
      <code>
        McpJsonRpcHandler
      </code>
    </td>
    
    <td>
      MCP JSON-RPC endpoint (see <a href="/platform/mcp-tools">
        MCP Tools
      </a>
      
      )
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        GET
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/oauth/authorize
      </code>
    </td>
    
    <td>
      <code>
        OAuthServerHandler
      </code>
    </td>
    
    <td>
      PKCE authorization-code flow; redirects to the <code>
        /oauth/consent
      </code>
      
       page on the server's UI, on the app server the application's primary UI (<code>
        400
      </code>
      
       until one is chosen)
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        GET
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/oauth/request/:requestId
      </code>
    </td>
    
    <td>
      <code>
        OAuthServerHandler
      </code>
    </td>
    
    <td>
      The request awaiting consent, for the consent page's signed-in user
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/oauth/approve
      </code>
    </td>
    
    <td>
      <code>
        OAuthServerHandler
      </code>
    </td>
    
    <td>
      Approves the request as the consent page's signed-in user; answers the client redirect URL carrying the code
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/oauth/deny
      </code>
    </td>
    
    <td>
      <code>
        OAuthServerHandler
      </code>
    </td>
    
    <td>
      Denies the request; answers the client redirect URL carrying <code>
        error=access_denied
      </code>
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/oauth/device_authorization
      </code>
    </td>
    
    <td>
      <code>
        DeviceAuthorizationHandler
      </code>
    </td>
    
    <td>
      RFC 8628 device grant — issue device/user codes; requires the CLI's <code>
        client_id
      </code>
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/oauth/device/approve
      </code>
    </td>
    
    <td>
      <code>
        DeviceAuthorizationHandler
      </code>
    </td>
    
    <td>
      Approves the user code the <code>
        /device
      </code>
      
       page's signed-in user confirmed
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/oauth/token
      </code>
    </td>
    
    <td>
      <code>
        OAuthServerHandler
      </code>
    </td>
    
    <td>
      Token endpoint: <code>
        authorization_code
      </code>
      
       and <code>
        refresh_token
      </code>
      
       grants, plus every extension grant a module publishes — on the management server <code>
        DeviceAuthorizationHandler
      </code>
      
      's device-code grant
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        GET
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/invite/details
      </code>
    </td>
    
    <td>
      <code>
        InviteHandler
      </code>
    </td>
    
    <td>
      Invitation details, the organization's live provider list (empty for an application invitation), <code>
        accountExists
      </code>
      
       — whether the invited email already has an account that a sign-in method signs in; a pending member's account, which none does yet, is the invitee's to claim and counts as none — and <code>
        accountHasPassword
      </code>
      
      , without which that account joins through a provider it is bound to
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/invite/accept
      </code>
    </td>
    
    <td>
      <code>
        InviteHandler
      </code>
    </td>
    
    <td>
      Accept with a password: sets it on the new account or user, or on a pending member's account as its first sign-in method, or, when the invited email already has an account someone signs in to, must be that account's password (<code>
        401
      </code>
      
       otherwise), and the account and its membership of the organization must be enabled (<code>
        403
      </code>
      
       otherwise); an organization member's session is established, while an application member signs in at the application
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/invite/oidc/start/:configId
      </code>
    </td>
    
    <td>
      <code>
        InviteHandler
      </code>
    </td>
    
    <td>
      Accept an organization invitation through a configuration the organization's login offers; redirects to the IdP, whose callback completes the acceptance: the identity's verified email must be the invited one; it joins the account it is bound to, binds a pending member's account as its first sign-in method, or creates and binds one, and establishes an organization member's session; <code>
        account_exists
      </code>
      
       when the email's account is one someone already signs in to and has no binding for the identity, so the holder accepts with the password instead; <code>
        account_disabled
      </code>
      
       when the account or its membership is disabled. An application invitation is accepted with a password
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        GET
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/me
      </code>
    </td>
    
    <td>
      <code>
        SessionEndpointHandler
      </code>
    </td>
    
    <td>
      <code>
        204
      </code>
      
       if the session cookie holds a login for the calling page, else <code>
        401
      </code>
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/auth/logout
      </code>
    </td>
    
    <td>
      <code>
        SessionEndpointHandler
      </code>
    </td>
    
    <td>
      Ends the calling page's login; the session ends once no page's login or sign-in in progress remains
    </td>
  </tr>
  
  <tr>
    <td>
      <code>
        POST
      </code>
    </td>
    
    <td>
      <code>
        /api/github/webhook
      </code>
    </td>
    
    <td>
      <code>
        GitHubWebhookHandler
      </code>
    </td>
    
    <td>
      GitHub App webhook, verified against the App's webhook secret
    </td>
  </tr>
</tbody>
</table>

For the underlying architectural rationale (scope isolation, credential separation, the two kinds of `OidcConfiguration`), see [System Security](/platform/system-security).
