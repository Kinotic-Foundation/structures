<script lang="ts" setup>
import { useRoute } from 'vue-router'
import { computed, watch } from 'vue'
import ProjectStructuresTable from '@/components/ProjectStructuresTable.vue'
import { APPLICATION_STATE } from '@/states/IApplicationState'

const route = useRoute()
const projectId = computed(() => route.params.projectId as string)
const applicationId = computed(() => APPLICATION_STATE.currentApplication?.id || '')

watch(() => APPLICATION_STATE.currentApplication, (newApp) => {
  console.log('ProjectStructuresPage: APPLICATION_STATE.currentApplication changed to:', newApp?.id)
}, { deep: true })

watch(applicationId, (newId) => {
  console.log('ProjectStructuresPage: applicationId computed changed to:', newId)
})
</script>

<template>
  <div class="p-6">
    <h1 class="text-2xl font-semibold mb-4 text-surface-950">
      {{ projectId }}
    </h1>
    <ProjectStructuresTable :key="`${applicationId}-${projectId}`" :applicationId="applicationId" />
  </div>
</template>
