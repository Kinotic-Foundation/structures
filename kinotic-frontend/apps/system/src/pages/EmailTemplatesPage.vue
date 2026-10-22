<template>
  <div class="flex flex-col">
    <PageHeader title="Email templates"
                description="The emails a UI built on Kinotic sends in its own name, in place of Kinotic's built-in ones." />

    <CrudTable
      ref="crudTable"
      :headers="headers"
      :data-source="dataSource"
      :search="tableSearch"
      create-new-button-text="Add template"
      empty-state-text="No email templates; every UI sends Kinotic's built-in emails"
      :row-actions="rowActions"
      @update:search="tableSearch = $event"
      @add-item="openDrawer(null)"
    >
      <template #item.origin="{ item }">
        <span class="font-mono text-sm">{{ item.origin }}</span>
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
import type { OriginEmailTemplate } from '@kinotic-ai/system-api'
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
  template: OriginEmailTemplate
  origin: string
  kind: string
  subject: string
  updated: string | null
}

const headers: CrudHeader[] = [
  { field: 'origin', header: 'UI origin', sortable: false, width: '30%' },
  { field: 'kind', header: 'Email', sortable: false, width: '18%' },
  { field: 'subject', header: 'Subject', sortable: false, optional: true, width: '32%' },
  { field: 'updated', header: 'Updated', sortable: false, optional: true, width: '20%' }
]

const confirm = useConfirm()
const { tableSearch, dataSource, refreshTable, run, removeRow } = useCrudTablePage(
  filteredPageLoader((pageable: Pageable) => Kinotic.originEmailTemplates.findAll(pageable),
                     toRow,
                     row => [row.origin, row.kind, row.subject])
)

const drawerVisible = ref(false)
const editing = ref<OriginEmailTemplate | null>(null)

function toRow(template: OriginEmailTemplate): TemplateRow {
  return {
    id: template.id ?? '',
    template,
    origin: template.origin,
    kind: EMAIL_TEMPLATE_KINDS[template.kind]?.label ?? template.kind,
    subject: template.subject,
    updated: template.updated
  }
}

function openDrawer(template: OriginEmailTemplate | null): void {
  editing.value = template
  drawerVisible.value = true
}

function rowActions(row: TemplateRow): MenuItem[] {
  return [
    { label: 'Edit', icon: 'pi pi-pencil', command: () => openDrawer(row.template) },
    { label: 'Delete', icon: 'pi pi-trash', command: () => confirmDelete(row) }
  ]
}

function confirmDelete(row: TemplateRow): void {
  confirm.require({
    header: 'Delete template',
    message: `Delete the ${row.kind.toLowerCase()} email of ${row.origin}? That UI sends Kinotic's built-in email again.`,
    icon: 'pi pi-exclamation-triangle',
    acceptProps: { label: 'Delete', severity: 'danger' },
    rejectProps: { label: 'Cancel', severity: 'secondary', outlined: true },
    accept: () => run(async () => {
      await Kinotic.originEmailTemplates.deleteById(row.id)
      removeRow(row.id)
    }, 'Template deleted', 'Failed to delete the template')
  })
}
</script>
