<script setup lang="ts">
const FOUNDERS_VIDEO_EMBED = 'https://www.youtube-nocookie.com/embed/K3du2BlRYPI?autoplay=1&rel=0&playsinline=1'
</script>

<template>
  <section id="about" class="about">
    <div class="k-wrap about__inner">
      <div class="k-eyebrow about__eyebrow" data-reveal>
        <span>ABOUT US</span>
      </div>
      <h1 class="k-heading about__title" data-reveal>Our Story</h1>
      <p class="about__lede" data-reveal>
        Kinotic did not begin with the AI boom. It is the convergence of more than two decades of
        building software, infrastructure and products together.
      </p>

      <div class="about__video" data-reveal>
        <div class="about__glow about__glow--top" />
        <div class="about__glow about__glow--left" />
        <div class="about__glow about__glow--right" />
        <div class="about__glow about__glow--bottom" />
        <VcVideoFacade
          class="about__player"
          poster="/images/vc/founders-video-poster.webp"
          label="Play the founders video"
        >
          <iframe
            :src="FOUNDERS_VIDEO_EMBED"
            title="Kinotic founders video"
            referrerpolicy="strict-origin-when-cross-origin"
            allow="autoplay; encrypted-media; fullscreen; picture-in-picture"
            allowfullscreen
          />
        </VcVideoFacade>
        <div class="about__rule" />
      </div>
    </div>
  </section>
</template>

<style scoped>
.about {
  position: relative;
  padding: 180px 0 90px;
  overflow: hidden;
  scroll-margin-top: 76px;
}

.about__inner {
  position: relative;
  z-index: 2;
}

.about__eyebrow {
  justify-content: center;
  margin-bottom: 22px;
}

.about__title {
  font-size: clamp(48px, 6.2vw, 86px);
  font-weight: 500;
  line-height: 1.04;
  text-align: center;
}

.about__lede {
  max-width: 820px;
  margin: 34px auto 0;
  color: var(--color-k-muted);
  font-size: 21px;
  line-height: 1.55;
  text-align: center;
}

.about__video {
  position: relative;
  max-width: 900px;
  margin: 64px auto 0;
}

.about__glow {
  position: absolute;
  pointer-events: none;
  filter: blur(24px);
  background: radial-gradient(ellipse 50% 50% at 50% 50%, rgba(236, 31, 82, 0.2), transparent 70%);
}

.about__glow--top {
  left: 6%;
  top: -56px;
  width: 88%;
  height: 200px;
  background: radial-gradient(ellipse 50% 50% at 50% 50%, rgba(236, 31, 82, 0.32), transparent 70%);
}

.about__glow--left {
  left: -70px;
  top: 22%;
  width: 220px;
  height: 400px;
}

.about__glow--right {
  right: -70px;
  top: 30%;
  width: 220px;
  height: 380px;
}

.about__glow--bottom {
  left: 14%;
  bottom: -52px;
  width: 72%;
  height: 170px;
  background: radial-gradient(ellipse 50% 50% at 50% 50%, rgba(236, 31, 82, 0.26), transparent 70%);
}

.about__player {
  aspect-ratio: 16 / 9;
  border: 1px solid rgba(255, 255, 255, 0.09);
  border-radius: 18px;
}

.about__rule {
  height: 1px;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.18), transparent);
}

@media (max-width: 768px) {
  .about {
    padding: 148px 0 64px;
  }

  .about__lede {
    font-size: 18px;
  }

  .about__player {
    border-radius: 12px;
  }
}
</style>
