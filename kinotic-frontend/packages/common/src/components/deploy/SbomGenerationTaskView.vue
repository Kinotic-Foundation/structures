<template>
  <div v-if="data.generated !== null" class="mb-2 text-xs text-muted-color">
    {{ data.generated ? 'Generated the SBOM' : 'The dependencies are unchanged' }}
  </div>
  <WorkloadLogTaskView v-if="data.generated !== false" :data="{ workloadId: data.workloadId }" :node="node" :run="run" />
</template>

<script setup lang="ts">
import type { JobRun, SbomGeneration } from '@kinotic-ai/management-api'
import type { JobTaskNode } from '../grind/JobTaskNode'
import WorkloadLogTaskView from '../WorkloadLogTaskView.vue'

/**
 * The TaskViews.SBOM_GENERATION view: the log of the workload generating the SBOM, and once the
 * step completes, whether the run generated it or kept one whose dependencies were the same.
 */
defineProps<{
  data: SbomGeneration
  node: JobTaskNode
  run: JobRun
}>()
</script>
