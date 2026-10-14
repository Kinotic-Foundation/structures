<script setup lang="ts">
type MilestoneStatus = 'shipped' | 'current' | 'upcoming'
type FeatureStatus = 'shipped' | 'in-progress'

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
}

const milestones: Milestone[] = [
  {
    label: 'Q3 2026 · SHIPPED',
    status: 'shipped',
    title: 'Core platform',
    summary: 'Data, services and frontends from one description, built and deployed by Claude over MCP.',
  },
  {
    label: 'Q4 2026 · NOW',
    status: 'current',
    title: 'Production ready',
    summary: 'Access control, enterprise SSO and gated releases ready for real workloads, alongside our seed round.',
  },
  {
    label: 'Q1 2027',
    status: 'upcoming',
    title: 'Launch',
    summary: 'Kinotic OS Cloud opens to everyone, design partners start building, and the engineering team grows.',
  },
  {
    label: 'Q2 2027',
    status: 'upcoming',
    title: 'First customers',
    summary: 'Design partners convert to paid plans, and the first enterprise runs Kinotic OS in its own cloud.',
  },
  {
    label: 'Q3 2027',
    status: 'upcoming',
    title: 'Repeatable sales',
    summary: 'Proven pricing, SOC 2 in hand, and partner channels through the MCP ecosystem and cloud marketplaces.',
  },
  {
    label: 'Q4 2027',
    status: 'upcoming',
    title: 'Series A ready',
    summary: 'Revenue, retention and pipeline that support a Series A, with customers setting the roadmap.',
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
    summary: 'Who can act: roles, groups and per-tenant permissions, enforced on every service call and MCP tool.',
    when: 'Q4 2026',
  },
  {
    status: 'in-progress',
    title: 'Gated dev, stage & prod',
    summary: 'What ships: a live preview for every branch, vulnerability and migration checks before stage, human sign-off for prod.',
    when: 'Q4 2026',
  },
  {
    status: 'in-progress',
    title: 'Enterprise SSO',
    summary: 'Okta, Microsoft Entra ID and Google sign-in for organizations and for the apps they build.',
    when: 'Q4 2026',
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
        <span>ROADMAP</span>
      </div>
      <h2 class="k-heading roadmap__title" data-reveal>Where we're headed</h2>
      <p class="roadmap__lede" data-reveal>
        This quarter Kinotic becomes production ready. After that, the customers we land decide what we
        build next, so 2027 is planned in business milestones rather than features.
      </p>

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
  margin-bottom: 16px;
  font-size: clamp(29px, 3.4vw, 44px);
}

.roadmap__lede {
  max-width: 560px;
  margin: 0 0 64px;
  color: var(--color-k-muted);
  font-size: 16px;
  line-height: 1.6;
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
  gap: 16px;
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

@media (max-width: 1080px) {
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
