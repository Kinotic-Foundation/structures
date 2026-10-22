<script setup lang="ts">
const DECK_URL = 'https://www.figma.com/deck/cd4CJyhT2hIRJ4mk7IOEpr/Investor-Deck---Website?node-id=4042-211'
const DECK_EMBED_URL = 'https://embed.figma.com/deck/cd4CJyhT2hIRJ4mk7IOEpr/Investor-Deck---Website'
  + '?node-id=4042-211&embed-host=kinotic&scaling=contain&theme=dark&hide-ui=1&hotspot-hints=0&footer=false'
</script>

<template>
  <section class="k-section deck">
    <div class="k-wrap">
      <div class="k-eyebrow deck__eyebrow" data-reveal>
        <span>INVESTOR DECK</span>
      </div>
      <h2 class="k-heading deck__title" data-reveal>The deck</h2>
      <p class="deck__lede" data-reveal>
        The full investor presentation — market, product, traction and the raise. Flip through it
        here or open it in a new tab.
      </p>

      <div id="deck" class="deck__card" data-reveal>
        <div class="deck__frame">
          <iframe :src="DECK_EMBED_URL" title="Kinotic investor deck" loading="lazy" allowfullscreen />
        </div>
        <div class="deck__bar">
          <span class="deck__hint">Use ← → to move between slides</span>
          <a :href="DECK_URL" target="_blank" rel="noreferrer" class="k-btn k-btn--red deck__open">
            <span>Open full deck</span>
            <svg width="12" height="12" viewBox="0 0 12 12" fill="none" aria-hidden="true">
              <path d="M2.5 9.5l7-7M4 2.5h5.5V8" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round" />
            </svg>
          </a>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.deck {
  border-top: 1px solid var(--color-k-border);
}

.deck__eyebrow {
  margin-bottom: 18px;
}

.deck__title {
  margin-bottom: 16px;
  font-size: clamp(29px, 3.4vw, 44px);
}

.deck__lede {
  max-width: 560px;
  margin: 0 0 52px;
  color: var(--color-k-muted);
  font-size: 16px;
  line-height: 1.6;
}

.deck__card {
  border: 1px solid var(--color-k-border);
  background: var(--color-k-bg-card);
  overflow: hidden;
  scroll-margin-top: 132px;
}

.deck__frame {
  position: relative;
  aspect-ratio: 16 / 9;
  background: var(--color-k-bg);
  overflow: hidden;
}

/* Oversized by a percent on each side so the embed's own edge never shows inside the card. */
.deck__frame iframe {
  position: absolute;
  left: -1%;
  top: -1%;
  width: 102%;
  height: 102%;
  border: 0;
  background: var(--color-k-bg);
}

.deck__bar {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  padding: 22px 32px;
  border-top: 1px solid var(--color-k-border);
}

.deck__hint {
  color: var(--color-k-muted);
  font-family: var(--font-k-mono);
  font-size: 12px;
  letter-spacing: 0.08em;
}

.deck__open {
  padding: 13px 22px;
  font-size: 13px;
}

@media (max-width: 768px) {
  .deck {
    --k-section-padding: 64px 0;
  }

  .deck__bar {
    padding: 18px 20px;
  }
}
</style>
