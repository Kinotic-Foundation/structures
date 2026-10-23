<template>
  <JobRunProgress :key="jobRunId" :job-run-id="jobRunId"
                  :failure-of="platformRun ? undefined : ProjectDeployResultNames.failureOf"
                  :next-step="platformRun ? SYSTEM_JOB_RETRY_HINT : undefined" @loaded="onLoaded" />
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { JobOwnerType, type JobRun } from '@kinotic-ai/management-api'
import { JobRunProgress, ownerTypeOf, ProjectDeployResultNames } from '@kinotic-ai/frontend-common'

/**
 * One job run's progress, step by step, with each step's detail. A failed deployment explains its
 * failure as a deployment, and a failed platform run says how to run it again. Without the
 * organization the run belongs to, it emits that organization once the run is loaded.
 */
const props = defineProps<{
  jobRunId: string
  organizationId?: string
}>()

const emit = defineEmits<{
  (e: 'organization', organizationId: string | null): void
}>()

const SYSTEM_JOB_RETRY_HINT = 'To try again, start the job again with Run system job on the platform\'s Jobs page.'

// true once the loaded run is the platform's own, one of its system jobs
const platformRun = ref(false)

// another run's organization is cleared at once, so a run that fails to load never shows the last one's
watch(() => props.jobRunId, () => {
  platformRun.value = false
  if (!props.organizationId) {
    emit('organization', null)
  }
})

function onLoaded(run: JobRun): void {
  platformRun.value = ownerTypeOf(run) === JobOwnerType.SYSTEM
  if (!props.organizationId) {
    emit('organization', run.organizationId)
  }
}
</script>
