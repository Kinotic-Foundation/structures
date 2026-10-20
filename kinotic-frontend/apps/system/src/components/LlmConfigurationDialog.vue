<template>
  <FormDialog :visible="visible" :icon="BrainCircuit" :title="isNew ? 'Add LLM configuration' : 'Edit LLM configuration'"
              description="A model the platform's services call, selected by its name."
              @update:visible="emit('update:visible', $event)" @submit="submit">
    <div class="flex flex-col gap-5">
      <div>
        <label for="llm-name" class="mb-2 block text-sm font-medium">Name</label>
        <InputText id="llm-name" v-model="name" placeholder="app-builder" autocomplete="off" class="w-full font-mono" />
        <p class="mt-1.5 text-[0.8125rem] text-muted-color">The name a service selects this configuration by, unique on the platform.</p>
      </div>

      <div>
        <label for="llm-provider" class="mb-2 block text-sm font-medium">Provider</label>
        <Select input-id="llm-provider" v-model="provider" :options="PROVIDERS" option-label="label" option-value="value" class="w-full" />
      </div>

      <div>
        <label for="llm-model" class="mb-2 block text-sm font-medium">Model</label>
        <InputText id="llm-model" v-model="model" :placeholder="MODEL_PLACEHOLDERS[provider]" autocomplete="off" class="w-full font-mono" />
      </div>

      <div>
        <label for="llm-api-key" class="mb-2 block text-sm font-medium">API key</label>
        <Password input-id="llm-api-key" v-model="apiKey" :feedback="false" toggle-mask
                  :placeholder="configuration?.apiKeyHeld ? 'A key is held' : undefined"
                  :input-props="{ autocomplete: 'new-password' }" class="w-full" input-class="w-full" />
        <p v-if="configuration?.apiKeyHeld" class="mt-1.5 text-[0.8125rem] text-muted-color">Leave it empty to keep the key held for this configuration.</p>
      </div>

      <div>
        <label for="llm-endpoint" class="mb-2 flex items-center justify-between text-sm font-medium">
          Endpoint
          <span class="text-xs font-normal text-muted-color">Optional</span>
        </label>
        <InputText id="llm-endpoint" v-model="endpoint" placeholder="https://api.anthropic.com" autocomplete="off" class="w-full font-mono" />
        <p class="mt-1.5 text-[0.8125rem] text-muted-color">The base URL of the provider's API, when it is not the provider's public API.</p>
      </div>

      <div class="grid grid-cols-2 gap-4">
        <div>
          <label for="llm-max-tokens" class="mb-2 flex items-center justify-between text-sm font-medium">
            Max tokens
            <span class="text-xs font-normal text-muted-color">Optional</span>
          </label>
          <InputNumber input-id="llm-max-tokens" v-model="maxTokens" :min="1" :use-grouping="false" class="w-full" input-class="w-full" />
        </div>
        <div>
          <label for="llm-temperature" class="mb-2 flex items-center justify-between text-sm font-medium">
            Temperature
            <span class="text-xs font-normal text-muted-color">Optional</span>
          </label>
          <InputNumber input-id="llm-temperature" v-model="temperature" :min="0" :max="2" :min-fraction-digits="0" :max-fraction-digits="2"
                       class="w-full" input-class="w-full" />
        </div>
      </div>
    </div>

    <template #footer>
      <Button type="button" label="Cancel" severity="secondary" outlined @click="emit('update:visible', false)" />
      <Button type="submit" :label="isNew ? 'Add configuration' : 'Save'" :loading="saving" :disabled="!complete" />
    </template>
  </FormDialog>
</template>

<script setup lang="ts">
import { computed, ref, watch } from 'vue'
import Button from 'primevue/button'
import InputNumber from 'primevue/inputnumber'
import InputText from 'primevue/inputtext'
import Password from 'primevue/password'
import Select from 'primevue/select'
import { useToast } from 'primevue/usetoast'
import { BrainCircuit } from '@lucide/vue'
import { Kinotic } from '@kinotic-ai/core'
import { LlmConfiguration, LlmProviderType } from '@kinotic-ai/system-api'
import { FormDialog, showErrorToast } from '@kinotic-ai/frontend-common'

/**
 * Adds an LLM configuration, or edits the one given, and saves it with the API key typed, if any.
 */
const props = defineProps<{
  visible: boolean
  configuration: LlmConfiguration | null
}>()

const emit = defineEmits<{
  (e: 'update:visible', visible: boolean): void
  (e: 'saved', configuration: LlmConfiguration): void
}>()

const PROVIDERS = [
  { label: 'Anthropic', value: LlmProviderType.ANTHROPIC },
  { label: 'OpenAI', value: LlmProviderType.OPENAI }
]

const MODEL_PLACEHOLDERS: Record<LlmProviderType, string> = {
  [LlmProviderType.ANTHROPIC]: 'claude-opus-5-5',
  [LlmProviderType.OPENAI]: 'gpt-5'
}

const toast = useToast()

const name = ref('')
const provider = ref<LlmProviderType>(LlmProviderType.ANTHROPIC)
const model = ref('')
const apiKey = ref('')
const endpoint = ref('')
const maxTokens = ref<number | null>(null)
const temperature = ref<number | null>(null)
const saving = ref(false)

const isNew = computed(() => !props.configuration?.id)
const complete = computed(() => name.value.trim() !== '' && model.value.trim() !== '')

// the fields start from the configuration being edited each time the dialog opens
watch(() => props.visible, visible => {
  if (visible) {
    const configuration = props.configuration
    name.value = configuration?.name ?? ''
    provider.value = configuration?.provider ?? LlmProviderType.ANTHROPIC
    model.value = configuration?.model ?? ''
    apiKey.value = ''
    endpoint.value = configuration?.endpoint ?? ''
    maxTokens.value = configuration?.maxTokens ?? null
    temperature.value = configuration?.temperature ?? null
  }
}, { immediate: true })

async function submit(): Promise<void> {
  if (!complete.value) {
    return
  }
  const configuration = Object.assign(new LlmConfiguration(name.value.trim(), provider.value, model.value.trim()), {
    id: props.configuration?.id ?? null,
    created: props.configuration?.created ?? null,
    endpoint: endpoint.value.trim() || null,
    maxTokens: maxTokens.value,
    temperature: temperature.value
  })
  saving.value = true
  try {
    const saved = await Kinotic.llmConfigurations.save(configuration, apiKey.value || null)
    toast.add({ severity: 'success', summary: isNew.value ? 'Configuration added' : 'Configuration saved', life: 4000 })
    emit('saved', saved)
    emit('update:visible', false)
  } catch (err) {
    showErrorToast(toast, 'Failed to save the configuration', err, { life: 8000 })
  } finally {
    saving.value = false
  }
}
</script>
