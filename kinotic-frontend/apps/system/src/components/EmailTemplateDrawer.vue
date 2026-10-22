<template>
  <FormDrawer :visible="visible" :title="isNew ? 'Add email template' : 'Edit email template'"
              description="An email a UI sends in its own name, for the flows started from its pages." @close="emit('update:visible', false)">
    <form id="email-template-form" class="flex flex-col gap-5" @submit.prevent="submit">
      <div>
        <label for="email-template-origin" class="mb-2 block text-sm font-medium">UI origin</label>
        <InputText id="email-template-origin" v-model="origin" placeholder="https://builder.example.com" autocomplete="off" class="w-full font-mono" />
        <p class="mt-1.5 text-[0.8125rem] text-muted-color">
          The scheme and host of the UI, with no path. It is used only when the management server's CORS allowlist trusts this origin.
        </p>
      </div>

      <div>
        <label for="email-template-kind" class="mb-2 block text-sm font-medium">Email</label>
        <Select input-id="email-template-kind" v-model="kind" :options="KINDS" option-label="label" option-value="value" class="w-full" />
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

      <div>
        <span class="mb-2 block text-sm font-medium">Variables</span>
        <div class="flex flex-wrap gap-2">
          <code v-for="variable in EMAIL_TEMPLATE_KINDS[kind].variables" :key="variable"
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
import Select from 'primevue/select'
import SelectButton from 'primevue/selectbutton'
import { useToast } from 'primevue/usetoast'
import { Kinotic } from '@kinotic-ai/core'
import { EmailTemplateKind, OriginEmailTemplate } from '@kinotic-ai/system-api'
import { FormDrawer, showErrorToast, TemplateCodeEditor } from '@kinotic-ai/frontend-common'
import { EMAIL_TEMPLATE_KINDS } from '@/util/EmailTemplateKinds'

/**
 * Adds a UI's email template, or edits the one given.
 */
const props = defineProps<{
  visible: boolean
  template: OriginEmailTemplate | null
}>()

const emit = defineEmits<{
  (e: 'update:visible', visible: boolean): void
  (e: 'saved', template: OriginEmailTemplate): void
}>()

const KINDS = Object.values(EmailTemplateKind).map(value => ({ label: EMAIL_TEMPLATE_KINDS[value].label, value }))

const BODY_FORMATS = [
  { label: 'HTML', value: 'html' },
  { label: 'Plain text', value: 'text' }
]

const toast = useToast()

const origin = ref('')
const kind = ref<EmailTemplateKind>(EmailTemplateKind.INVITATION)
const subject = ref('')
const htmlBody = ref('')
const textBody = ref('')
const bodyFormat = ref<'html' | 'text'>('html')
const saving = ref(false)

const isNew = computed(() => !props.template?.id)
const complete = computed(() => [origin, subject, htmlBody, textBody].every(field => field.value.trim() !== ''))

// the fields start from the template being edited each time the drawer opens
watch(() => props.visible, visible => {
  if (visible) {
    const template = props.template
    origin.value = template?.origin ?? ''
    kind.value = template?.kind ?? EmailTemplateKind.INVITATION
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
  const template = Object.assign(new OriginEmailTemplate(origin.value.trim(), kind.value, subject.value, htmlBody.value, textBody.value), {
    id: props.template?.id ?? null,
    created: props.template?.created ?? null
  })
  saving.value = true
  try {
    const saved = await Kinotic.originEmailTemplates.save(template)
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
