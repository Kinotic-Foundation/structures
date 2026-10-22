<template>
  <FormDrawer :visible="visible" :title="isNew ? 'Add email template' : 'Edit email template'"
              description="An email a UI sends in its own name, which the calls that send it name by id." @close="emit('update:visible', false)">
    <form id="email-template-form" class="flex flex-col gap-5" @submit.prevent="submit">
      <div>
        <label for="email-template-id" class="mb-2 block text-sm font-medium">Id</label>
        <InputText id="email-template-id" v-model="id" :disabled="!isNew" placeholder="builder-invitation" autocomplete="off" class="w-full font-mono" />
        <p class="mt-1.5 text-[0.8125rem] text-muted-color">
          The name a UI sends this email by: lowercase letters, digits and dashes.
        </p>
      </div>

      <div>
        <label for="email-template-ui" class="mb-2 block text-sm font-medium">UI base URL</label>
        <InputText id="email-template-ui" v-model="uiBaseUrl" placeholder="https://builder.example.com" autocomplete="off" class="w-full font-mono" />
        <p class="mt-1.5 text-[0.8125rem] text-muted-color">
          Where the email's link opens the UI's invitation or verification page. Leave it empty for the portal.
        </p>
      </div>

      <div>
        <label for="email-template-subject" class="mb-2 block text-sm font-medium">Subject</label>
        <InputText id="email-template-subject" v-model="subject" autocomplete="off" class="w-full" />
      </div>

      <div>
        <div class="mb-2 flex items-center justify-between gap-3">
          <span class="text-sm font-medium">Body</span>
          <SelectButton v-model="bodyFormat" :options="BODY_FORMATS" option-label="label" option-value="value" :allow-empty="false" size="small" />
        </div>
        <TemplateCodeEditor v-if="bodyFormat === 'html'" id="email-template-html" v-model="htmlBody" html
                            file-name="body.html" aria-label="HTML body" />
        <TemplateCodeEditor v-else id="email-template-text" v-model="textBody"
                            file-name="body.txt" aria-label="Plain-text body" />
        <p class="mt-1.5 text-[0.8125rem] text-muted-color">
          Both are required: most mail clients show the HTML, and the rest the plain text. Put a URL variable inside a
          link with triple braces, such as <code v-pre class="font-mono">{{{acceptUrl}}}</code>, so it isn't HTML-escaped.
        </p>
      </div>

      <div v-for="email in EMAILS" :key="email.label">
        <span class="mb-2 block text-sm font-medium">{{ email.label }} variables</span>
        <div class="flex flex-wrap gap-2">
          <code v-for="variable in email.variables" :key="variable"
                class="rounded-md bg-indigo-50 px-2 py-1 font-mono text-[13px] text-indigo-700 dark:bg-indigo-500/15 dark:text-indigo-300">{{ placeholderOf(variable) }}</code>
        </div>
      </div>
    </form>

    <template #footer>
      <Button type="button" label="Cancel" severity="secondary" outlined @click="emit('update:visible', false)" />
      <Button type="submit" form="email-template-form" :label="isNew ? 'Add template' : 'Save'" :loading="saving" :disabled="!complete" />
    </template>
  </FormDrawer>
</template>

<script setup lang="ts">
import { computed, ref, watch } from 'vue'
import Button from 'primevue/button'
import InputText from 'primevue/inputtext'
import SelectButton from 'primevue/selectbutton'
import { useToast } from 'primevue/usetoast'
import { Kinotic } from '@kinotic-ai/core'
import { SystemEmailTemplate } from '@kinotic-ai/system-api'
import { FormDrawer, showErrorToast, TemplateCodeEditor } from '@kinotic-ai/frontend-common'

/**
 * Adds an email template, or edits the one given.
 */
const props = defineProps<{
  visible: boolean
  template: SystemEmailTemplate | null
}>()

const emit = defineEmits<{
  (e: 'update:visible', visible: boolean): void
  (e: 'saved', template: SystemEmailTemplate): void
}>()

// the variables each email a template may be sent as renders with
const EMAILS = [
  { label: 'Invitation', variables: ['inviterName', 'organizationName', 'applicationName', 'acceptUrl', 'expiresInDays'] },
  { label: 'Sign-up verification', variables: ['displayName', 'verificationUrl'] }
]

const BODY_FORMATS = [
  { label: 'HTML', value: 'html' },
  { label: 'Plain text', value: 'text' }
]

const toast = useToast()

const id = ref('')
const uiBaseUrl = ref('')
const subject = ref('')
const htmlBody = ref('')
const textBody = ref('')
const bodyFormat = ref<'html' | 'text'>('html')
const saving = ref(false)

const isNew = computed(() => props.template === null)
const complete = computed(() => [id, subject, htmlBody, textBody].every(field => field.value.trim() !== ''))

// the fields start from the template being edited each time the drawer opens
watch(() => props.visible, visible => {
  if (visible) {
    const template = props.template
    id.value = template?.id ?? ''
    uiBaseUrl.value = template?.uiBaseUrl ?? ''
    subject.value = template?.subject ?? ''
    htmlBody.value = template?.htmlBody ?? ''
    textBody.value = template?.textBody ?? ''
    bodyFormat.value = 'html'
  }
}, { immediate: true })

function placeholderOf(variable: string): string {
  return `{{${variable}}}`
}

async function submit(): Promise<void> {
  if (!complete.value) {
    return
  }
  const template = Object.assign(new SystemEmailTemplate(id.value.trim(), subject.value, htmlBody.value, textBody.value), {
    uiBaseUrl: uiBaseUrl.value.trim() || null,
    created: props.template?.created ?? null
  })
  saving.value = true
  try {
    const saved = await Kinotic.systemEmailTemplates.save(template)
    toast.add({ severity: 'success', summary: isNew.value ? 'Template added' : 'Template saved', life: 4000 })
    emit('saved', saved)
    emit('update:visible', false)
  } catch (err) {
    showErrorToast(toast, 'Failed to save the template', err, { life: 8000 })
  } finally {
    saving.value = false
  }
}
</script>
