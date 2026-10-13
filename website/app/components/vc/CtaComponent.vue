<template>
  <section id="cta" class="cta">
    <div class="cta__grid">
      <div class="cta__copy">
        <div class="k-eyebrow cta__eyebrow" data-reveal>
          <span>FASTEST PATH FROM IMAGINATION TO PRODUCTION</span>
        </div>
        <h2 class="k-heading cta__title" data-reveal>Let's build the next decade of software together</h2>
        <div data-reveal>
          <a href="mailto:founders@kinotic.com" class="k-btn k-btn--red cta__button">Talk to the founders ↗</a>
        </div>
      </div>
      <FloatingCubes class="cta__visual" />
    </div>
  </section>
</template>

<style scoped>
.cta {
  border-top: 1px solid var(--color-k-border);
  border-bottom: 1px solid var(--color-k-border);
  scroll-margin-top: 76px;
}

.cta__grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  height: 415px;
}

.cta__copy {
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 0 40px 0 max(40px, calc((100vw - 1140px) / 2));
  border-right: 1px solid var(--color-k-border);
}

.cta__eyebrow {
  margin-bottom: 26px;
}

.cta__eyebrow > span {
  font-size: 13px;
  letter-spacing: 0.14em;
}

.cta__title {
  max-width: 480px;
  margin-bottom: 36px;
  font-size: clamp(30px, 3.6vw, 50px);
  line-height: 1.08;
  letter-spacing: -0.02em;
}

.cta__button {
  font-size: 14px;
  padding: 17px 34px;
}

@media (max-width: 930px) {
  .cta__grid {
    grid-template-columns: 1fr;
    height: auto;
  }

  .cta__copy {
    padding: 64px 30px;
    border-right: none;
    border-bottom: 1px solid var(--color-k-border);
  }

  .cta__visual {
    min-height: 330px;
  }
}

@media (max-width: 600px) {
  .cta__copy {
    padding: 52px 22px;
  }
}
</style>
