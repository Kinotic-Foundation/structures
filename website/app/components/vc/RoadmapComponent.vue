<script setup lang="ts">
type MilestoneStatus = 'shipped' | 'current' | 'upcoming'
type FeatureStatus = 'shipped' | 'in-progress' | 'planned'

interface Milestone {
  label: string
  status: MilestoneStatus
  title: string
  summary: string
}

interface Feature {
  status: FeatureStatus
  title: string
  summary: string
  when: string
}

const FEATURE_STATUS_LABELS: Record<FeatureStatus, string> = {
  'shipped': 'SHIPPED',
  'in-progress': 'IN PROGRESS',
  'planned': 'PLANNED',
}

const milestones: Milestone[] = [
  {
    label: 'Q3 2026 · SHIPPED',
    status: 'shipped',
    title: 'Core platform & MCP',
    summary: 'Data, services and frontends from one description, built and deployed by Claude over MCP.',
  },
  {
    label: 'Q4 2026 · WE ARE HERE',
    status: 'current',
    title: 'Enterprise identity',
    summary: 'Enterprise SSO and relationship-based access control on every service and agent tool.',
  },
  {
    label: 'H1 2027 · NEXT',
    status: 'upcoming',
    title: 'Governed delivery',
    summary: 'Gated dev, stage and prod, a complete audit trail, SCIM and deployment into your cloud.',
  },
  {
    label: 'H2 2027 · THEN',
    status: 'upcoming',
    title: 'AI & data governance',
    summary: 'LLM cost controls, data residency, PII controls and SOC 2 Type II.',
  },
  {
    label: '2028 · LATER',
    status: 'upcoming',
    title: 'Ecosystem',
    summary: 'A marketplace for Kinotic apps, plus sovereign and air-gapped deployments.',
  },
]

const features: Feature[] = [
  {
    status: 'shipped',
    title: 'Core platform',
    summary: 'Applications, typed services, tenant-isolated persistence and hosted frontends, deployed from GitHub on every push.',
    when: 'Q3 2026',
  },
  {
    status: 'shipped',
    title: 'MCP server & Claude plugin',
    summary: 'Claude creates, builds and deploys applications through OAuth-secured MCP tools.',
    when: 'Q3 2026',
  },
  {
    status: 'shipped',
    title: 'Isolation, observability & SBOMs',
    summary: 'Every build and service in its own microVM, OpenTelemetry on every call, an SBOM for every project.',
    when: 'Q3 2026',
  },
  {
    status: 'in-progress',
    title: 'Relationship-based access control',
    summary: 'Roles, groups and per-tenant permissions, enforced at the gateway on every service call and MCP tool.',
    when: 'Q4 2026',
  },
  {
    status: 'in-progress',
    title: 'Enterprise SSO',
    summary: 'Okta, Microsoft Entra ID and Google sign-in for organizations and for the apps they build.',
    when: 'Q4 2026',
  },
  {
    status: 'planned',
    title: 'Dev, stage & prod environments',
    summary: 'A live preview for every branch, vulnerability and migration checks before stage, and human sign-off for prod.',
    when: 'H1 2027',
  },
  {
    status: 'planned',
    title: 'Audit trail',
    summary: 'Every sign-in, access change, deploy and agent tool call recorded, retained and streamed to your SIEM.',
    when: 'H1 2027',
  },
  {
    status: 'planned',
    title: 'Agent-operated apps',
    summary: 'Your app\'s own services become MCP tools, scoped by the same permissions, with approval on destructive calls.',
    when: 'H1 2027',
  },
  {
    status: 'planned',
    title: 'Directory sync, SAML & MFA',
    summary: 'SCIM provisioning, deprovisioning that revokes every session and agent grant, SAML 2.0, enforced SSO and passkeys.',
    when: 'H1 2027',
  },
  {
    status: 'planned',
    title: 'Forward deployment',
    summary: 'Kinotic OS on your own Kubernetes cluster, in your network, with your own key vault.',
    when: 'H1 2027',
  },
  {
    status: 'planned',
    title: 'Backups & uptime SLA',
    summary: 'Automated backups and point-in-time restore for application data, and a published uptime SLA.',
    when: 'H1 2027',
  },
  {
    status: 'planned',
    title: 'Usage metering & billing',
    summary: 'Plans, invoicing and spend broken down by application and environment.',
    when: 'H1 2027',
  },
  {
    status: 'planned',
    title: 'LLM gateway & AI cost controls',
    summary: 'Approved models, your own provider keys, token budgets per app, prompt traces and cost attribution.',
    when: 'H2 2027',
  },
  {
    status: 'planned',
    title: 'Agent identity federation',
    summary: 'Agents authorized by your identity provider through Okta Cross App Access and Microsoft Entra Agent ID, with an inventory and revoke.',
    when: 'H2 2027',
  },
  {
    status: 'planned',
    title: 'Data residency & privacy controls',
    summary: 'EU hosting, customer-managed keys, PII tagging and masking, and retention policies per application.',
    when: 'H2 2027',
  },
  {
    status: 'planned',
    title: 'SOC 2 Type II & ISO 27001',
    summary: 'Independent attestation of Kinotic OS Cloud, with a trust center for security reviews.',
    when: 'H2 2027',
  },
  {
    status: 'planned',
    title: 'Marketplace',
    summary: 'Publish and sell apps, services and templates, with Kinotic as merchant of record.',
    when: '2028',
  },
  {
    status: 'planned',
    title: 'Sovereign & air-gapped deployments',
    summary: 'Disconnected installs and sovereign-cloud regions for regulated and public-sector buyers.',
    when: '2028',
  },
]

// The progress bar ends halfway through the current milestone's column.
const progress = computed(() => {
  const current = milestones.findIndex(milestone => milestone.status === 'current')
  return `${((current + 0.5) / milestones.length) * 100}%`
})
</script>

<template>
  <section id="roadmap" class="k-section roadmap">
    <div class="k-wrap">
      <div class="k-eyebrow roadmap__eyebrow" data-reveal>
        <span>PRODUCT ROADMAP</span>
      </div>
      <h2 class="k-heading roadmap__title" data-reveal>Where we're headed</h2>

      <div class="timeline" data-reveal>
        <div class="timeline__track" />
        <div class="timeline__progress" :style="{ width: progress }" />
        <ol class="timeline__items">
          <li v-for="milestone in milestones" :key="milestone.title" class="milestone" :class="`milestone--${milestone.status}`">
            <span class="milestone__dot" />
            <div class="milestone__label">{{ milestone.label }}</div>
            <div class="milestone__title">{{ milestone.title }}</div>
            <p class="milestone__summary">{{ milestone.summary }}</p>
          </li>
        </ol>
      </div>

      <ul class="features" data-reveal>
        <li v-for="feature in features" :key="feature.title" class="feature">
          <span class="feature__status" :class="`feature__status--${feature.status}`">
            {{ FEATURE_STATUS_LABELS[feature.status] }}
          </span>
          <span class="feature__copy">
            <span class="feature__title">{{ feature.title }}</span>
            <span class="feature__summary">{{ feature.summary }}</span>
          </span>
          <span class="feature__when">{{ feature.when }}</span>
        </li>
      </ul>
    </div>
  </section>
</template>

<style scoped>
.roadmap {
  --k-section-padding: 110px 0 120px;
  border-top: 1px solid var(--color-k-border);
  scroll-margin-top: 76px;
}

.roadmap__eyebrow {
  margin-bottom: 18px;
}

.roadmap__title {
  margin-bottom: 64px;
  font-size: clamp(29px, 3.4vw, 44px);
}

.timeline {
  position: relative;
  margin-bottom: 72px;
}

.timeline__track {
  position: absolute;
  left: 0;
  right: 0;
  top: 7px;
  height: 1px;
  background: var(--color-k-border);
}

.timeline__progress {
  position: absolute;
  left: 0;
  top: 6px;
  height: 3px;
  background: var(--color-k-mint);
}

.timeline__items {
  position: relative;
  display: grid;
  grid-auto-columns: 1fr;
  grid-auto-flow: column;
  gap: 24px;
  margin: 0;
  padding: 0;
  list-style: none;
}

.milestone {
  position: relative;
  padding-top: 34px;
}

.milestone__dot {
  position: absolute;
  left: 0;
  top: 0;
  width: 15px;
  height: 15px;
  border: 2px solid #4A4A52;
  border-radius: 50%;
  background: var(--color-k-bg);
}

.milestone--shipped .milestone__dot {
  border-color: var(--color-k-mint);
  background: var(--color-k-mint);
}

.milestone--current .milestone__dot {
  top: -1px;
  width: 17px;
  height: 17px;
  border-color: var(--color-k-red);
  background: var(--color-k-red);
  box-shadow: 0 0 0 6px rgba(236, 31, 82, 0.22);
  animation: k-pulse 2.2s ease-in-out infinite;
}

.milestone__label {
  margin-bottom: 10px;
  color: var(--color-k-muted);
  font-family: var(--font-k-mono);
  font-size: 11px;
  letter-spacing: 0.14em;
}

.milestone--shipped .milestone__label {
  color: var(--color-k-mint);
}

.milestone--current .milestone__label {
  color: var(--color-k-red);
}

.milestone__title {
  margin-bottom: 8px;
  color: var(--color-k-text);
  font-family: var(--font-k-mono);
  font-size: 17px;
}

.milestone__summary {
  margin: 0;
  color: var(--color-k-muted);
  font-size: 13px;
  line-height: 1.6;
}

.features {
  margin: 0;
  padding: 0;
  list-style: none;
  border: 1px solid var(--color-k-border);
  background: var(--color-k-bg-card);
}

.feature {
  display: flex;
  align-items: center;
  gap: 28px;
  padding: 24px 34px;
  border-bottom: 1px solid var(--color-k-border);
}

.feature:last-child {
  border-bottom: none;
}

.feature__status {
  flex: none;
  min-width: 112px;
  padding: 6px 10px;
  border-radius: 3px;
  background: #17181C;
  color: var(--color-k-muted);
  font-family: var(--font-k-mono);
  font-size: 11px;
  letter-spacing: 0.12em;
  text-align: center;
}

.feature__status--shipped {
  background: #101E19;
  color: var(--color-k-mint);
}

.feature__status--in-progress {
  background: #2A1017;
  color: #FF5C84;
}

.feature__copy {
  flex: 1;
}

.feature__title {
  display: block;
  margin-bottom: 4px;
  color: var(--color-k-text);
  font-family: var(--font-k-mono);
  font-size: 15px;
}

.feature__summary {
  display: block;
  color: var(--color-k-muted);
  font-size: 13px;
}

.feature__when {
  flex: none;
  color: var(--color-k-fainter);
  font-family: var(--font-k-mono);
  font-size: 12px;
}

@media (max-width: 930px) {
  .timeline__track,
  .timeline__progress {
    display: none;
  }

  .timeline__items {
    grid-auto-flow: row;
    gap: 0;
  }

  .milestone {
    padding: 0 0 28px 28px;
    border-left: 1px solid var(--color-k-border);
  }

  .milestone__dot,
  .milestone--current .milestone__dot {
    left: -8px;
    top: 4px;
  }
}

@media (max-width: 768px) {
  .roadmap {
    --k-section-padding: 64px 0;
  }
}

@media (max-width: 600px) {
  .feature {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
    padding: 22px 20px;
  }
}
</style>
