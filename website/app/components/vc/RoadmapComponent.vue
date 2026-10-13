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
    title: 'Core platform',
    summary: 'Frontends, microservices, persistence and CI/CD from a single description.',
  },
  {
    label: 'Q4 2026 · WE ARE HERE',
    status: 'current',
    title: 'MCP & agents',
    summary: 'Build with Claude, ChatGPT or Cursor over MCP; every service exposed as a tool.',
  },
  {
    label: 'H1 2027 · NEXT',
    status: 'upcoming',
    title: 'Enterprise controls',
    summary: 'RBAC, audit, private deployments and multi-region.',
  },
  {
    label: 'H2 2027 · LATER',
    status: 'upcoming',
    title: 'Marketplace',
    summary: 'Shared structures, services and templates across the Kinotic network.',
  },
]

const features: Feature[] = [
  {
    status: 'shipped',
    title: 'Prototype-to-production continuity',
    summary: 'The same artifact moves from dev to stage to prod with no rewrite.',
    when: 'Q3 2026',
  },
  {
    status: 'shipped',
    title: 'Identity & access management',
    summary: 'OIDC sign-in and Cedar policy enforcement built into every service.',
    when: 'Q3 2026',
  },
  {
    status: 'in-progress',
    title: 'MCP tool generation',
    summary: 'Every structure and service published as an MCP tool for any agent.',
    when: 'Q4 2026',
  },
  {
    status: 'in-progress',
    title: 'Live usage & cost metering',
    summary: 'Per-application observability with spend attribution.',
    when: 'Q4 2026',
  },
  {
    status: 'planned',
    title: 'RBAC & audit trail',
    summary: 'Role-based access across workspaces with immutable change history.',
    when: 'H1 2027',
  },
  {
    status: 'planned',
    title: 'Private & multi-region deployments',
    summary: 'Run the factory inside a customer\'s cloud account, in any region.',
    when: 'H1 2027',
  },
  {
    status: 'planned',
    title: 'Structures marketplace',
    summary: 'Publish and reuse data models, services and app templates.',
    when: 'H2 2027',
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
  grid-template-columns: repeat(4, 1fr);
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
    grid-template-columns: 1fr;
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
