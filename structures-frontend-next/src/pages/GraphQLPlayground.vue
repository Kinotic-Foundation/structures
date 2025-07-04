<template>
  <iframe
    ref="iframeRef"
    src="/graphiql.html"
    width="100%"
    height="100%"
    frameborder="0"
  ></iframe>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import Cookies from 'js-cookie'

const iframeRef = ref(null)

function getQueryParam(name) {
  const urlParams = new URLSearchParams(window.location.search)
  return urlParams.get(name)
}

onMounted(() => {
  const namespace = getQueryParam('namespace') || 'default'
  const token = Cookies.get('token')

  if (!token) {
    console.warn('No token found in cookie')
    return
  }

  iframeRef.value?.addEventListener('load', () => {
    iframeRef.value.contentWindow.postMessage({ namespace, token }, '*')
  })
})
</script>
