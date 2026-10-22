<template>
  <div class="flex flex-col">
    <PageHeader title="Email templates"
                description="The emails UIs send in their own name, which a call names by id. A call that names none sends the Kinotic default." />

    <CrudTable
      ref="crudTable"
      :headers="headers"
      :data-source="dataSource"
      :search="tableSearch"
      create-new-button-text="Add template"
      empty-state-text="No email templates"
      :row-actions="rowActions"
      @update:search="tableSearch = $event"
      @add-item="openDrawer(null)"
    >
      <template #item.id="{ item }">
        <span class="font-mono text-sm">{{ item.id }}</span>
      </template>

      <template #item.ui="{ item }">
        <span v-if="item.template.uiBaseUrl" class="font-mono text-sm">{{ item.ui }}</span>
        <span v-else class="text-muted-color">{{ item.ui }}</span>
      </template>

      <template #item.updated="{ item }">
        <TimePill :date="item.updated" />
      </template>
    </CrudTable>

    <EmailTemplateDrawer v-model:visible="drawerVisible" :template="editing" @saved="refreshTable()" />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { MenuItem } from 'primevue/menuitem'
import { useConfirm } from 'primevue/useconfirm'
import { Kinotic, type Pageable } from '@kinotic-ai/core'
import { DEFAULT_EMAIL_TEMPLATE_IDS, type PlatformEmailTemplate } from '@kinotic-ai/system-api'
import {
  CrudTable,
  filteredPageLoader,
  PageHeader,
  TimePill,
  useCrudTablePage,
  type CrudHeader,
  type DescriptiveIdentifiable
} from '@kinotic-ai/frontend-common'

import EmailTemplateDrawer from '@/components/EmailTemplateDrawer.vue'
import { EMAIL_TEMPLATE_KINDS } from '@/util/EmailTemplateKinds'

/** One row: a template and the columns it is listed by. */
interface TemplateRow extends DescriptiveIdentifiable {
  id: string
  template: PlatformEmailTemplate
  ui: string
  kind: string
  subject: string
  updated: string | null
}

const headers: CrudHeader[] = [
  { field: 'id', header: 'Id', sortable: false, width: '24%' },
  { field: 'kind', header: 'Email', sortable: false, width: '16%' },
  { field: 'ui', header: 'Links open', sortable: false, optional: true, width: '28%' },
  { field: 'subject', header: 'Subject', sortable: false, optional: true, width: '20%' },
  { field: 'updated', header: 'Updated', sortable: false, optional: true, width: '12%' }
]

const confirm = useConfirm()
const { tableSearch, dataSource, refreshTable, run, removeRow } = useCrudTablePage(
  filteredPageLoader((pageable: Pageable) => Kinotic.platformEmailTemplates.findAll(pageable),
                     toRow,
                     row => [row.id, row.ui, row.kind, row.subject])
)

const drawerVisible = ref(false)
const editing = ref<PlatformEmailTemplate | null>(null)

function toRow(template: PlatformEmailTemplate): TemplateRow {
  return {
    id: template.id,
    template,
    ui: template.uiBaseUrl ?? 'The portal',
    kind: EMAIL_TEMPLATE_KINDS[template.kind]?.label ?? template.kind,
    subject: template.subject,
    updated: template.updated
  }
}

function openDrawer(template: PlatformEmailTemplate | null): void {
  editing.value = template
  drawerVisible.value = true
}

// a kind's default is sent by every call that names no template, so it can be edited but not deleted
function rowActions(row: TemplateRow): MenuItem[] {
  const actions: MenuItem[] = [{ label: 'Edit', icon: 'pi pi-pencil', command: () => openDrawer(row.template) }]
  if (!Object.values(DEFAULT_EMAIL_TEMPLATE_IDS).includes(row.id)) {
    actions.push({ label: 'Delete', icon: 'pi pi-trash', command: () => confirmDelete(row) })
  }
  return actions
}

function confirmDelete(row: TemplateRow): void {
  confirm.require({
    header: 'Delete template',
    message: `Delete the ${row.kind.toLowerCase()} email ${row.id}? Calls that name it fail until a template with that id is added again.`,
    icon: 'pi pi-exclamation-triangle',
    acceptProps: { label: 'Delete', severity: 'danger' },
    rejectProps: { label: 'Cancel', severity: 'secondary', outlined: true },
    accept: () => run(async () => {
      await Kinotic.platformEmailTemplates.deleteById(row.id)
      removeRow(row.id)
    }, 'Template deleted', 'Failed to delete the template')
  })
}
</script>
