<script setup lang="ts">
// An unlinked page for investors, shared by URL. nuxt.config.ts lists /vc under
// nitro.prerender.routes so `nuxt generate` emits it without a link to crawl.
definePageMeta({
  layout: 'home',
  header: false,
  footer: false,
  // Add { label: 'Roadmap', to: '#roadmap', external: true } once VcRoadmapComponent is rendered.
  headerNav: {
    links: [
      { label: 'About us', to: '#about', external: true },
      { label: 'Product demo', to: '#demo', external: true },
      { label: 'Investor deck', to: '#deck', external: true },
    ],
    cta: { label: 'Contact us', to: '#cta', external: true },
  },
})

useHead({ titleTemplate: '%s', htmlAttrs: { class: 'k-smooth-scroll' } })
useSeoMeta({
  title: 'Kinotic — Our story',
  description: 'Kinotic is the convergence of more than two decades of building software, '
    + 'infrastructure and products together.',
  robots: 'noindex, nofollow',
})

useMarketingMotion()

// The YouTube video open in the modal, or null while it is closed.
const video = ref<string | null>(null)
</script>

<template>
  <VcAboutComponent @play="video = $event" />
  <VcFoundersComponent />
  <VcStoryComponent />
  <VcDemoComponent @play="video = $event" />
  <VcDeckComponent />
  <!-- The roadmap stays unpublished until its content is revised; render <VcRoadmapComponent /> here and add its header link to show it. -->
  <VcCtaComponent />
  <VcVideoModal :video-id="video" @close="video = null" />
</template>
