<template>
      <Layout>
    <!-- <template #aside-outline-before>
      My custom sidebar top content
    </template> -->
    <HeroSection />
  </Layout>
  <HeroSection />

  </template>
  
  <script setup>
  import DefaultTheme from 'vitepress/theme'
  import HeroSection from '../../components/home/HeroSection.vue'
//   import FeaturesGrid from '../../components/home/FeaturesGrid.vue'
  const { Layout } = DefaultTheme
  </script>
  
  <style scoped>
  .home-layout {
    padding: 2rem;
  }
  </style>
  