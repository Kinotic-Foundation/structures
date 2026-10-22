<template>
  <DashboardSection :icon="SearchCheck" :tint="tint" title="Check access"
                    description="Ask whether a system user or a machine holds one of the system's permissions, and which grants give it.">
    <div class="flex flex-col gap-4 p-5">
      <div class="grid gap-3 md:grid-cols-[1fr_1fr_auto]">
        <Select v-model="subjectId" :options="subjectOptions" option-label="label" option-value="subject.id" filter
                :filter-fields="['label', 'detail']" option-group-label="label" option-group-children="items"
                placeholder="System user or machine" class="w-full">
          <template #option="{ option }">
            <div class="flex flex-col">
              <span class="text-sm">{{ option.label }}</span>
              <span v-if="option.detail" class="text-xs text-muted-color">{{ option.detail }}</span>
            </div>
          </template>
        </Select>
        <Select v-model="permission" :options="permissionOptions" option-label="label" option-value="name"
                placeholder="Permission" class="w-full" />
        <Button type="button" label="Check" icon="pi pi-search" :loading="checking" :disabled="!subjectId || !permission" @click="check" />
      </div>

      <div v-if="explanation" :class="['flex items-start gap-3 rounded-xl border p-4', explanation.allowed
          ? 'border-green-200 bg-green-50 dark:border-green-500/30 dark:bg-green-500/10'
          : 'border-red-200 bg-red-50 dark:border-red-500/30 dark:bg-red-500/10']">
        <component :is="explanation.allowed ? CircleCheck : CircleX" :size="20" :stroke-width="1.75"
                   :class="['mt-0.5 shrink-0', explanation.allowed ? 'text-green-600 dark:text-green-300' : 'text-red-600 dark:text-red-300']" aria-hidden="true" />
        <div class="min-w-0 flex-1 text-sm">
          <p class="font-medium text-surface-950 dark:text-surface-0">
            {{ checkedLabel }} {{ explanation.allowed ? 'holds' : 'does not hold' }} {{ checkedPermission }} on the system
          </p>
          <p v-if="explanation.allowed && explanation.through.length === 0" class="mt-1 text-muted-color">
            Held through nothing a grant explains.
          </p>
          <ul v-else-if="explanation.through.length > 0" class="mt-2 flex flex-col gap-1.5">
            <li v-for="grant in explanation.through" :key="grant.id" class="flex flex-wrap items-center gap-2 text-surface-800 dark:text-surface-100">
              <span>{{ roleName(grant.roleId) }}</span>
              <span class="text-muted-color">granted to {{ labelOf(grant.subject) }} on the system</span>
            </li>
          </ul>
          <p v-else class="mt-1 text-muted-color">No grant on the system gives the permission.</p>
        </div>
      </div>
    </div>
  </DashboardSection>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import Button from 'primevue/button'
import Select from 'primevue/select'
import { useToast } from 'primevue/usetoast'
import { CircleCheck, CircleX, SearchCheck } from '@lucide/vue'
import { Kinotic } from '@kinotic-ai/core'
import { type AccessExplanation, type RoleDefinition, type Subject, SubjectType } from '@kinotic-ai/management-api'
import { DashboardSection, permissionLabel, showErrorToast, splitPermission } from '@kinotic-ai/frontend-common'
import type { SubjectOption } from './useSubjects'

/**
 * Explains a system user's or a machine's access to the system: whether it holds one of the system's own
 * permissions, and the grants on the system it holds the permission through.
 */
const props = defineProps<{
  tint: string
  systemUsers: SubjectOption[]
  machines: SubjectOption[]
  roles: RoleDefinition[]
  /** The model names of the system's own permissions. */
  catalog: string[]
  labelOf: (subject: Subject) => string
  /** The subject to start with picked, such as the one the page was opened for. */
  initialSubjectId?: string
}>()

const toast = useToast()
const subjectId = ref<string | null>(props.initialSubjectId ?? null)
const permission = ref<string | null>(null)
const checking = ref(false)
const explanation = ref<AccessExplanation | null>(null)
const checkedLabel = ref('')
const checkedPermission = ref('')

const subjectOptions = computed(() => [
  { label: 'System users', items: props.systemUsers },
  { label: 'Machines', items: props.machines }
])

const permissionOptions = computed(() => props.catalog.map(name => ({ name, label: permissionLabel(splitPermission(name).permission) })))

function roleName(roleId: string): string {
  return props.roles.find(role => role.id === roleId)?.name ?? roleId
}

async function check(): Promise<void> {
  const picked = [...props.systemUsers, ...props.machines].find(option => option.subject.id === subjectId.value)
  if (!picked || !permission.value) {
    return
  }
  const split = splitPermission(permission.value)
  checking.value = true
  try {
    explanation.value = await Kinotic.systemAccess.explain({ type: SubjectType.USER, id: picked.subject.id }, split.permission)
    checkedLabel.value = picked.label
    checkedPermission.value = permissionLabel(split.permission).toLowerCase()
  } catch (err) {
    showErrorToast(toast, 'Failed to check access', err, { life: 8000 })
  } finally {
    checking.value = false
  }
}
</script>
