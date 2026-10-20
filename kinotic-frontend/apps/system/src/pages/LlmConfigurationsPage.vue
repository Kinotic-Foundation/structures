<template>
  <div class="flex flex-col">
    <PageHeader title="LLM configurations"
                description="The large language models the platform's services call, each selected by its name." />

    <CrudTable
      ref="crudTable"
      :headers="headers"
      :data-source="dataSource"
      :search="tableSearch"
      create-new-button-text="Add configuration"
      empty-state-text="No LLM configurations"
      :row-actions="rowActions"
      @update:search="tableSearch = $event"
      @add-item="openDialog(null)"
    >
      <template #item.name="{ item }">
        <span class="font-mono text-sm">{{ item.name }}</span>
      </template>

      <template #item.model="{ item }">
        <span class="font-mono text-sm">{{ item.model }}</span>
      </template>

      <template #item.apiKey="{ item }">
        <Tag :value="item.apiKey" :severity="item.configuration.apiKeyHeld ? 'info' : 'warn'" />
      </template>

      <template #item.updated="{ item }">
        <TimePill :date="item.updated" />
      </template>
    </CrudTable>

    <LlmConfigurationDialog v-model:visible="dialogVisible" :configuration="editing" @saved="refreshTable()" />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import Tag from 'primevue/tag'
import type { MenuItem } from 'primevue/menuitem'
import { useConfirm } from 'primevue/useconfirm'
import { FunctionalIterablePage, Kinotic, type IterablePage, type Page, type Pageable } from '@kinotic-ai/core'
import { LlmProviderType, type LlmConfiguration } from '@kinotic-ai/system-api'
import {
  CrudTable,
  PageHeader,
  TimePill,
  useCrudTablePage,
  type CrudHeader,
  type DescriptiveIdentifiable
} from '@kinotic-ai/frontend-common'

import LlmConfigurationDialog from '@/components/LlmConfigurationDialog.vue'

/** One row: a configuration and the columns it is listed by. */
interface ConfigurationRow extends DescriptiveIdentifiable {
  id: string
  configuration: LlmConfiguration
  name: string
  provider: string
  model: string
  apiKey: string
  updated: string | null
}

const PROVIDER_NAMES: Record<LlmProviderType, string> = {
  [LlmProviderType.ANTHROPIC]: 'Anthropic',
  [LlmProviderType.OPENAI]: 'OpenAI'
}

const headers: CrudHeader[] = [
  { field: 'name', header: 'Name', sortable: false, width: '24%' },
  { field: 'provider', header: 'Provider', sortable: false, width: '16%' },
  { field: 'model', header: 'Model', sortable: false, width: '24%' },
  { field: 'apiKey', header: 'API key', sortable: false, optional: true, width: '16%' },
  { field: 'updated', header: 'Updated', sortable: false, optional: true, width: '20%' }
]

const confirm = useConfirm()
const { tableSearch, dataSource, refreshTable, run, removeRow } = useCrudTablePage(load)

const dialogVisible = ref(false)
const editing = ref<LlmConfiguration | null>(null)

function toRow(configuration: LlmConfiguration): ConfigurationRow {
  return {
    id: configuration.id ?? '',
    configuration,
    name: configuration.name,
    provider: PROVIDER_NAMES[configuration.provider] ?? configuration.provider,
    model: configuration.model,
    apiKey: configuration.apiKeyHeld ? 'Key held' : 'No key',
    updated: configuration.updated
  }
}

// the platform holds few configurations, so they are read in one page and searched here
async function load(pageable: Pageable, searchText: string | null): Promise<IterablePage<DescriptiveIdentifiable>> {
  const all = await Kinotic.llmConfigurations.findAll(pageable)
  let shown = all.content ?? []
  if (searchText) {
    const needle = searchText.trim().toLowerCase()
    shown = shown.filter(c => [c.name, c.model, c.provider].some(field => (field ?? '').toLowerCase().includes(needle)))
  }
  const page: Page<DescriptiveIdentifiable> = { content: shown.map(toRow), totalElements: all.totalElements, cursor: undefined }
  return new FunctionalIterablePage(pageable, page, (next: Pageable) => load(next, searchText))
}

function openDialog(configuration: LlmConfiguration | null): void {
  editing.value = configuration
  dialogVisible.value = true
}

function rowActions(row: ConfigurationRow): MenuItem[] {
  return [
    { label: 'Edit', icon: 'pi pi-pencil', command: () => openDialog(row.configuration) },
    { label: 'Delete', icon: 'pi pi-trash', command: () => confirmDelete(row.configuration) }
  ]
}

function confirmDelete(configuration: LlmConfiguration): void {
  confirm.require({
    header: 'Delete configuration',
    message: `Delete ${configuration.name}? Services that select it can no longer call the model, and its API key is deleted.`,
    icon: 'pi pi-exclamation-triangle',
    acceptProps: { label: 'Delete', severity: 'danger' },
    rejectProps: { label: 'Cancel', severity: 'secondary', outlined: true },
    accept: () => run(async () => {
      await Kinotic.llmConfigurations.deleteById(configuration.id!)
      removeRow(configuration.id!)
    }, 'Configuration deleted', 'Failed to delete the configuration')
  })
}
</script>
