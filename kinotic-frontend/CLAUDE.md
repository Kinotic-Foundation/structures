# kinotic-frontend

## Workspace Layout

This is a pnpm workspace:

- `apps/portal` — the Kinotic OS dashboard (organization users). Built into the management server's
  `webroot` for docker-compose; deployed to Azure static hosting in production.
- `apps/system` — the platform-operator console (system users). Deployed to VPN-restricted
  static hosting only; never baked into the server jar.
- `packages/common` — shared UI code (`@kinotic-ai/frontend-common`): theme preset, auth page
  shell, session state, utils. Consumed as source via `workspace:*` — no build step.

Shared code moves to `packages/common` only once both apps actually consume it.

Import `@kinotic-ai/frontend-common` statically, never with `import()`: its index chunk is in every
app's startup graph, so a dynamic import loads nothing later and makes that chunk import every chunk
holding one of its exports, which can close a cycle that leaves the page blank. Both apps' production
builds fail on a dynamic import of it and on any import cycle between output chunks
(`packages/common/vite/bundleGuards.ts`).

## Add and edit forms open in a right-hand drawer

A form that adds or edits something (an application, a project, a member, a sign-in provider)
opens in `FormDrawer` from `@kinotic-ai/frontend-common`, the panel that slides in from the right,
never in a modal dialog. Put the fields in a `<form id="…" @submit.prevent="…">` in the default slot
and the buttons in the `footer` slot, with the submit button naming the form (`form="…"`);
`ApplicationSidebar.vue` and `OidcConfigurationDrawer.vue` in the portal are examples. A row's
details open in `SteppingDrawer`, also on the right. A modal is for what is not a form: a
confirmation (`useConfirm`), a value shown once (`MachineSecretDialog`), or a log viewer.

## Package Manager

**IMPORTANT:** This project uses **pnpm** for package management and script execution.

- Use `pnpm install` at the workspace root to install dependencies
- Use `pnpm add <package>` in the app/package that needs the dependency
- Use `pnpm <script>` to run scripts (root `build` builds every app; `dev` runs the portal,
  `dev:system` runs the system console)
- **DO NOT** use bun, yarn, or npm

Note: the `kinotic-js/workspace` packages use bun — that does not apply here.
