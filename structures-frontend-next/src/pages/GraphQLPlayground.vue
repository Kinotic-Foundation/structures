
<template>
  <iframe :src="'public/graphiql.html' + params" width="100%" height="600" frameborder="0"></iframe>
</template>

<script setup>
import { onMounted, ref } from 'vue'
const params = ref('');

function getQueryParam(name) {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.get(name);
}

onMounted(() => {
  const namespace = getQueryParam('namespace') || 'default'
  const token = getQueryParam('token') || ''
  params.value = `?namespace=${namespace}&token=${token}`
})
</script>