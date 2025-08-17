<script lang="ts" setup>
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import ProjectStructuresTable from '@/components/ProjectStructuresTable.vue'

const route = useRoute()
const projectId = computed(() => route.params.projectId as string)
</script>

<template>
  <div class="p-6">
    <h1 class="text-2xl font-semibold mb-4 text-surface-950">
      {{ projectId }}
    </h1>
    <ProjectStructuresTable :projectId="projectId" />
  </div>
</template>
