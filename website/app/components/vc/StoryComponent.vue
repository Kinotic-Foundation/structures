<template>
  <section class="story">
    <div class="k-wrap">
      <div class="story__banner" data-reveal>
        <div class="story__glow" />
        <div class="story__grid">
          <div>
            <div class="k-eyebrow story__eyebrow">
              <span>THE FOUNDER STORY</span>
            </div>
            <h2 class="k-heading story__title">Built by people who've shipped the hard way</h2>
          </div>
          <div class="story__copy">
            <p>
              We've spent our careers building software the traditional way — cloud platforms, embedded
              systems, data infrastructure, and the design of products used by millions. We've stood up
              the pipelines, carried the pagers, and watched great ideas die in the gap between a
              prototype and a production system.
            </p>
            <p>
              Kinotic is our answer to that gap. A software factory where the entire operational stack —
              data, services, pipelines, security, observability — is the platform's job, so the only
              thing left between you and a running business is the idea itself.
            </p>
            <p>
              We believe the next great software companies won't be built by the biggest teams. They'll
              be built by the clearest thinkers.
            </p>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.story {
  padding: 40px 0 120px;
}

.story__banner {
  position: relative;
  overflow: hidden;
  padding: 64px 72px;
  border: 1px solid var(--color-k-border);
  background: var(--color-k-bg-card);
}

.story__glow {
  position: absolute;
  right: -120px;
  top: -120px;
  width: 420px;
  height: 420px;
  background: radial-gradient(ellipse 50% 50% at 50% 50%, rgba(236, 31, 82, 0.18), transparent 70%);
  filter: blur(20px);
  pointer-events: none;
}

.story__grid {
  position: relative;
  display: grid;
  grid-template-columns: 5fr 7fr;
  gap: 64px;
  align-items: start;
}

.story__eyebrow {
  margin-bottom: 22px;
}

.story__title {
  max-width: 420px;
  font-size: clamp(29px, 3.4vw, 44px);
  line-height: 1.15;
}

.story__copy {
  display: flex;
  flex-direction: column;
  gap: 22px;
}

.story__copy p {
  margin: 0;
  color: var(--color-k-body);
  font-size: 14.5px;
  line-height: 1.7;
}

@media (max-width: 930px) {
  .story__grid {
    grid-template-columns: 1fr;
    gap: 26px;
  }
}

@media (max-width: 768px) {
  .story {
    padding: 40px 0 64px;
  }

  .story__banner {
    padding: 40px 24px;
  }
}
</style>
