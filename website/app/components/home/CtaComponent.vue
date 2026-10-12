<template>
  <section id="cloud-waitlist" class="cta" aria-labelledby="cloud-waitlist-title">
    <div id="cta" class="k-wrap cta__grid">
      <div class="cta__copy">
        <div class="k-eyebrow cta__eyebrow">
          <span>KINOTIC OS CLOUD · EARLY ACCESS</span>
        </div>
        <h2 id="cloud-waitlist-title" class="k-heading cta__title">Be first to build on Kinotic OS Cloud</h2>
        <p class="cta__description">Get early access to the cloud release.</p>

        <HomeWaitlistForm class="cta__signup" submit-label="Join cloud waitlist ↗" />
      </div>

      <div class="cta__visual" data-reveal>
        <div class="cta__glow cta__glow--red" />
        <div class="cta__glow cta__glow--mint" />
        <svg class="cta__cubes" viewBox="0 0 560 470" fill="none" preserveAspectRatio="xMidYMid meet" aria-hidden="true">
          <g class="cube cube--fast">
            <path d="M 96 92 L 141 71 L 186 92 L 141 113 Z" fill="#B0324C" />
            <path d="M 96 92 L 96 142 L 141 163 L 141 113 Z" fill="#7A1D33" />
            <path d="M 186 92 L 186 142 L 141 163 L 141 113 Z" fill="#96263F" />
          </g>
          <g class="cube cube--fast" style="animation-delay: -3s;">
            <path d="M 428 122 L 466 104 L 504 122 L 466 140 Z" fill="#4BF2B0" />
            <path d="M 428 122 L 428 164 L 466 182 L 466 140 Z" fill="#0E9E68" />
            <path d="M 504 122 L 504 164 L 466 182 L 466 140 Z" fill="#17B87C" />
          </g>
          <g class="cube">
            <path d="M 168 236 L 258 194 L 348 236 L 258 278 Z" fill="#C6455C" />
            <path d="M 168 236 L 168 336 L 258 378 L 258 278 Z" fill="#8E2038" />
            <path d="M 348 236 L 348 336 L 258 378 L 258 278 Z" fill="#A62B44" />
          </g>
          <g class="cube" style="animation-delay: -2s;">
            <path d="M 448 344 L 480 329 L 512 344 L 480 359 Z" fill="#8E3247" />
            <path d="M 448 344 L 448 378 L 480 393 L 480 359 Z" fill="#5E1A29" />
            <path d="M 512 344 L 512 378 L 480 393 L 480 359 Z" fill="#752338" />
          </g>
        </svg>
      </div>
    </div>
  </section>
</template>

<style scoped>
.cta {
  padding: 76px 0;
  border-top: 1px solid var(--color-k-border);
  border-bottom: 1px solid var(--color-k-border);
  background: radial-gradient(ellipse at 5% 90%, rgba(236, 31, 82, 0.08), transparent 65%), var(--color-k-bg-panel);
  scroll-margin-top: 150px;
}

.cta__grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  align-items: center;
  gap: 64px;
}

.cta__copy {
  min-width: 0;
}

.cta__eyebrow {
  margin-bottom: 20px;
}

.cta__eyebrow > span {
  font-size: 12px;
  letter-spacing: 0.12em;
}

.cta__title {
  font-size: clamp(30px, 3.6vw, 48px);
  line-height: 1.1;
  letter-spacing: -0.02em;
  max-width: 480px;
}

.cta__description {
  margin: 18px 0 0;
  color: var(--color-k-body);
  font-size: 16px;
}

.cta__signup {
  margin-top: 32px;
}

.cta__visual {
  position: relative;
  height: 415px;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
}

.cta__glow {
  position: absolute;
  filter: blur(6px);
}

.cta__glow--red {
  left: 12%;
  top: 18%;
  width: 520px;
  height: 420px;
  background: radial-gradient(ellipse 50% 50% at 50% 50%, rgba(236, 31, 82, 0.22), transparent 70%);
}

.cta__glow--mint {
  right: -6%;
  bottom: 2%;
  width: 460px;
  height: 400px;
  background: radial-gradient(ellipse 50% 50% at 50% 50%, rgba(40, 254, 180, 0.12), transparent 70%);
}

.cta__cubes {
  position: relative;
  width: 100%;
  height: 100%;
}

.cube {
  transform-box: fill-box;
  transform-origin: center;
  animation: cta-float 7s ease-in-out infinite;
}

.cube--fast {
  animation: cta-tumble 9s ease-in-out infinite;
}

@keyframes cta-float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-12px); }
}

@keyframes cta-tumble {
  0%, 100% { transform: translateY(0) rotate(-2deg); }
  50% { transform: translateY(-22px) rotate(2deg); }
}

@media (max-width: 930px) {
  .cta__grid {
    grid-template-columns: 1fr;
    gap: 32px;
  }

  .cta__visual {
    height: 330px;
  }
}

@media (max-width: 600px) {
  .cta {
    padding: 52px 0;
  }
}
</style>
