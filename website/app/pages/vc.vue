<script setup lang="ts">
// An unlinked page for investors, shared by URL. nuxt.config.ts lists /vc under
// nitro.prerender.routes so `nuxt generate` emits it without a link to crawl.
definePageMeta({ layout: 'home', header: false, footer: false })

useHead({ titleTemplate: '%s' })
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
  <!-- The roadmap stays unpublished until its content is revised; render <VcRoadmapComponent /> here to show it. -->
  <VcCtaComponent />
</template>
