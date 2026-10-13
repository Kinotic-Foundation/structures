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
      { label: 'Demo', to: '#demo', external: true },
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
</script>

<template>
  <VcAboutComponent />
  <VcFoundersComponent />
  <VcStoryComponent />
  <VcDemoComponent />
  <!-- The roadmap stays unpublished until its content is revised; render <VcRoadmapComponent /> here and add its header link to show it. -->
  <VcCtaComponent />
</template>
