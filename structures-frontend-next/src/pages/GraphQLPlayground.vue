<template>
  <div id="graphql-playground" class="flex items-center justify-center w-screen h-screen bg-[#172a3a] text-white">
    <div class="text-center w-screen">
      <img src="//cdn.jsdelivr.net/npm/graphql-playground-react/build/logo.png" alt="Logo" class="mx-auto mb-4 w-[78px] h-[78px]" />
      <div class="loading text-2xl font-light text-white/60">
        Loading <span class="title font-normal">GraphQL Playground</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'

onMounted(() => {
  const script = document.createElement('script')
  script.src = '//cdn.jsdelivr.net/npm/graphql-playground-react/build/static/js/middleware.js'
  script.onload = () => {
    const getQueryParam = (name) => new URLSearchParams(window.location.search).get(name)
    const getBaseUrl = () => {
      
      const isHttps = window.location.protocol.startsWith('https')
      const port = isHttps ? '443' : '4000'
      return `${window.location.protocol}//${window.location.hostname}:${port}`
    }
    const namespace = getQueryParam('namespace') || 'default'
window.GraphQLPlayground.init(document.getElementById('graphql-playground'), {
  endpoint: `${getBaseUrl()}/graphql/${namespace}`,
  headers: {
    Authorization: `Basic ${btoa("admin:structures")}`
  }
})

  }
  document.body.appendChild(script)
})
</script>

<style >
#graphql-playground > div:first-child {
  width: 100vw;
}
</style>