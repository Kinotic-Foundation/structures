<template>
  <JobRunProgress :key="jobRunId" :job-run-id="jobRunId" :failure-of="ProjectDeployResultNames.failureOf"
                  :next-step="nextStep" @loaded="onLoaded" />
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import type { JobRun } from '@kinotic-ai/management-api'
import { JobRunProgress, ProjectDeployResultNames } from '@kinotic-ai/frontend-common'

/**
 * One job run's progress, step by step, with each step's detail; a failed platform run says how
 * to run it again. Without the organization the run belongs to, it emits that organization once
 * the run is loaded.
 */
const props = defineProps<{
  jobRunId: string
  organizationId?: string
}>()

const emit = defineEmits<{
  (e: 'organization', organizationId: string | null): void
}>()

const SYSTEM_JOB_RETRY_HINT = 'To try again, start the job again with Run system job on the platform\'s Jobs page.'

const nextStep = ref<string | undefined>()

// another run's organization is cleared at once, so a run that fails to load never shows the last one's
watch(() => props.jobRunId, () => {
  nextStep.value = undefined
  if (!props.organizationId) {
    emit('organization', null)
  }
})

function onLoaded(run: JobRun): void {
  // a run owned by no organization is one of the platform's system jobs
  nextStep.value = run.organizationId ? undefined : SYSTEM_JOB_RETRY_HINT
  if (!props.organizationId) {
    emit('organization', run.organizationId)
  }
}
</script>
