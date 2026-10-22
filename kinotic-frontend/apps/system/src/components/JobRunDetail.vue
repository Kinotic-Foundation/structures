<template>
  <JobRunProgress :key="jobRunId" :job-run-id="jobRunId" :failure-of="ProjectDeployResultNames.failureOf"
                  @loaded="emitOwningOrganization" />
</template>

<script setup lang="ts">
import { watch } from 'vue'
import type { JobRun } from '@kinotic-ai/management-api'
import { JobRunProgress, ProjectDeployResultNames } from '@kinotic-ai/frontend-common'

/**
 * One job run's progress, step by step, with each step's detail. Without the organization the run
 * belongs to, it emits that organization once the run is loaded.
 */
const props = defineProps<{
  jobRunId: string
  organizationId?: string
}>()

const emit = defineEmits<{
  (e: 'organization', organizationId: string | null): void
}>()

// another run's organization is cleared at once, so a run that fails to load never shows the last one's
watch(() => props.jobRunId, () => {
  if (!props.organizationId) {
    emit('organization', null)
  }
})

function emitOwningOrganization(run: JobRun): void {
  if (!props.organizationId) {
    emit('organization', run.organizationId)
  }
}
</script>
