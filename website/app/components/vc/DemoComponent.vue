<template>
  <section id="demo" class="k-section demo">
    <div class="k-wrap">
      <div class="k-eyebrow demo__eyebrow" data-reveal>
        <span>GETTING STARTED</span>
      </div>
      <h2 class="k-heading demo__title" data-reveal>See the product in motion</h2>
      <p class="demo__lede" data-reveal>A walkthrough from a blank workspace to a running application.</p>

      <div class="demo__card" data-reveal>
        <VcVideoFacade
          class="demo__player"
          poster="/images/vc/product-demo-poster.webp"
          label="Play the getting started demo"
        >
          <video src="/product_demo.mp4" title="Getting started demo" controls autoplay playsinline />
        </VcVideoFacade>
        <div class="demo__body">
          <div class="demo__kicker">PRODUCT</div>
          <h3 class="demo__name">Getting started demo</h3>
          <p class="demo__blurb">
            Describe an idea, scaffold the application, and ship it to a live environment — end to end
            in one sitting.
          </p>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.demo {
  border-top: 1px solid var(--color-k-border);
  scroll-margin-top: 76px;
}

.demo__eyebrow {
  margin-bottom: 18px;
}

.demo__title {
  margin-bottom: 16px;
  font-size: clamp(29px, 3.4vw, 44px);
}

.demo__lede {
  max-width: 560px;
  margin: 0 0 52px;
  color: var(--color-k-muted);
  font-size: 16px;
  line-height: 1.6;
}

.demo__card {
  border: 1px solid var(--color-k-border);
  background: var(--color-k-bg-card);
}

.demo__player {
  aspect-ratio: 16 / 9;
  border-bottom: 1px solid var(--color-k-border);
}

.demo__body {
  padding: 30px 32px 34px;
}

.demo__kicker {
  margin-bottom: 12px;
  color: var(--color-k-mint);
  font-family: var(--font-k-mono);
  font-size: 11px;
  letter-spacing: 0.14em;
}

.demo__name {
  margin: 0 0 10px;
  color: var(--color-k-text);
  font-family: var(--font-k-mono);
  font-size: 19px;
  font-weight: 400;
}

.demo__blurb {
  margin: 0;
  color: var(--color-k-muted);
  font-size: 13.5px;
  line-height: 1.65;
}

@media (max-width: 768px) {
  .demo {
    --k-section-padding: 64px 0;
  }
}
</style>
