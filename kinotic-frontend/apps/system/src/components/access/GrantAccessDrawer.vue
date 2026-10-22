<template>
  <FormDrawer :visible="visible" title="Grant access"
              description="Give a system user or a machine a role on the system, which reaches every organization and worker node on it."
              @close="emit('update:visible', false)">
    <form id="system-grant-form" @submit.prevent="submit">
      <div class="flex flex-col gap-5">
        <div>
          <span class="mb-2 block text-sm font-medium">Who</span>
          <SelectButton v-model="staff" :options="STAFF" :allow-empty="false" class="mb-3" />
          <Select v-model="subjectId" :options="staff === 'System user' ? systemUsers : machines" option-label="label" option-value="subject.id"
                  filter :filter-fields="['label', 'detail']" :placeholder="staff === 'System user' ? 'Choose a system user' : 'Choose a machine'"
                  class="w-full" :empty-message="staff === 'System user' ? 'No system users' : 'No machines yet'">
            <template #option="{ option }">
              <div class="flex flex-col">
                <span class="text-sm">{{ option.label }}</span>
                <span v-if="option.detail" class="text-xs text-muted-color">{{ option.detail }}</span>
              </div>
            </template>
          </Select>
        </div>
        <div>
          <label for="system-grant-role" class="mb-2 block text-sm font-medium">Role</label>
          <Select input-id="system-grant-role" v-model="roleId" :options="roles" option-label="name" option-value="id" filter
                  placeholder="Choose a role" class="w-full">
            <template #option="{ option }">
              <div class="flex flex-col">
                <span class="flex items-center gap-2 text-sm">
                  {{ option.name }}
                  <Tag v-if="option.builtIn" value="Built-in" severity="secondary" class="!text-[10px]" />
                </span>
                <span class="text-xs text-muted-color">{{ summarizeRole(option) }}</span>
              </div>
            </template>
          </Select>
          <p class="mt-1.5 text-[0.8125rem] text-muted-color">The role's permissions reach the system and everything on it.</p>
        </div>
      </div>
    </form>

    <template #footer>
      <Button type="button" label="Cancel" severity="secondary" outlined @click="emit('update:visible', false)" />
      <Button type="submit" form="system-grant-form" label="Grant" icon="pi pi-check" :loading="granting" :disabled="!subjectId || !roleId" />
    </template>
  </FormDrawer>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import Button from 'primevue/button'
import Select from 'primevue/select'
import SelectButton from 'primevue/selectbutton'
import Tag from 'primevue/tag'
import { useToast } from 'primevue/usetoast'
import { Kinotic } from '@kinotic-ai/core'
import { type Grant, type RoleDefinition, SubjectKind } from '@kinotic-ai/management-api'
import { FormDrawer, showErrorToast, summarizeRole } from '@kinotic-ai/frontend-common'
import type { SubjectOption } from './useSubjects'

/**
 * Grants a role to a system user or a machine on the system: the subject holds every permission the role bundles
 * on the system and on everything on it.
 */
const props = defineProps<{
  visible: boolean
  roles: RoleDefinition[]
  systemUsers: SubjectOption[]
  machines: SubjectOption[]
}>()

const emit = defineEmits<{
  (e: 'update:visible', visible: boolean): void
  (e: 'granted', grant: Grant): void
}>()

const STAFF = ['System user', 'Machine']

const toast = useToast()
const staff = ref<'System user' | 'Machine'>('System user')
const subjectId = ref<string | null>(null)
const roleId = ref<string | null>(null)
const granting = ref(false)

watch(() => props.visible, visible => {
  if (visible) {
    staff.value = 'System user'
    subjectId.value = null
    roleId.value = null
  }
})

// a picked system user is not a machine, so switching the kind clears the pick
watch(staff, () => { subjectId.value = null })

async function submit(): Promise<void> {
  if (!subjectId.value || !roleId.value) {
    return
  }
  granting.value = true
  try {
    const grant = await Kinotic.systemAccess.grant({ kind: SubjectKind.USER, id: subjectId.value }, roleId.value)
    toast.add({ severity: 'success', summary: 'Access granted', life: 4000 })
    emit('granted', grant)
    emit('update:visible', false)
  } catch (err) {
    showErrorToast(toast, 'Failed to grant access', err, { life: 8000 })
  } finally {
    granting.value = false
  }
}
</script>
