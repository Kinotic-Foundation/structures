<template>
  <div class="pt-4">
    <CrudTable title="Structures" :initial-search="searchParam" subtitle="" :data-source="dataSource" :headers="headers" :singleExpand="false"
      ref="crudTable" :isShowAddNew="false" :isShowDelete="true" @onRowClick="openModal">
      <template #item.id="{ item }">
        <span>{{ item.id }}</span>
      </template>
      <template #item.description="{ item }">
        {{ item.description }}
      </template>
      <template #item.created="{ item }">
        <span>{{ formatDate(item.created) }}</span>
        
      </template>

      <template #item.updated="{ item }">
        <span>{{ formatDate(item.updated) }}</span>
      </template>

      <template #item.publishedTimestamp="{ item }">
        <span>{{ formatDate(item.publishedTimestamp) }}</span>
      </template>
      <template #additional-actions="{ item }">
        <Button v-show="!item.published" :loading="item['publishing']" class="mr-2" @click.stop="publish(item)"
          title="Publish" text severity="secondary">
          <template #icon>
            <svg :width="18" :height="18" viewBox="0 0 24 24" fill="currentColor" class="text-gray-600">
              <path :d="icons.graph" />
            </svg>
          </template>
        </Button>

        <Button v-show="item.published" class="mr-2" @click.stop="toStructureItemsPage(item)" title="Manage" text
          severity="secondary">
          <template #icon>
            <svg :width="18" :height="18" viewBox="0 0 24 24" fill="currentColor" class="text-gray-600">
              <path :d="icons.database" />
            </svg>
          </template>
        </Button>

        <Button v-show="item.published" class="mr-2 text-red-600" :loading="item['publishing']" @click.stop="unPublish(item)"
          title="Un-Publish" text severity="danger">
          <template #icon>
            <svg :width="18" :height="18" viewBox="0 0 24 24" fill="currentColor" class="text-red-600">
              <path :d="icons.unpublish" />
            </svg>
          </template>
        </Button>
      </template>
    </CrudTable>
    <StructureItemModal v-if="isModalOpen" :item="selectedStructure" @close="closeModal" />
    <Dialog v-model:visible="confirmDialogVisible" modal header="Confirm Action" :closable="false">
      <div class="p-3 text-gray-800 whitespace-pre-line">{{ confirmDialogMessage }}</div>
      <template #footer>
        <Button label="Cancel" @click="confirmDialogVisible = false" severity="secondary" text />
        <Button label="Confirm" @click="onDialogConfirm" autofocus severity="danger" />
      </template>
    </Dialog>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-facing-decorator'
import CrudTable from '@/components/CrudTable.vue'
import { Structures, type IStructureService } from '@kinotic/structures-api'
import { mdiDatabase, mdiGraphql, mdiUmbraco } from '@mdi/js'
import { STRUCTURES_STATE } from '@/states/IStructuresState'
import StructureItemModal from '@/components/modals/StructureItemModal.vue'
import Dialog from 'primevue/dialog';
@Component({
  components: { CrudTable, StructureItemModal }
})
export default class StructuresList extends Vue {
  confirmDialogVisible = false
  confirmDialogMessage = ''
  confirmDialogAction: (() => Promise<void>) | null = null
  headers = [
    { field: 'id', header: 'Id', sortable: false },
    { field: 'namespace', header: 'Namespace', sortable: false },
    { field: 'name', header: 'Name', sortable: false },
    { field: 'description', header: 'Description', sortable: false },
    { field: 'created', header: 'Created', sortable: false },
    { field: 'updated', header: 'Last Updated', sortable: false },
    { field: 'published', header: 'Published', sortable: false },
    { field: 'publishedTimestamp', header: 'Published On', sortable: false }
  ]
  get isModalOpen() {
    return STRUCTURES_STATE.isModalOpen.value
  }
  get selectedStructure() {
    return STRUCTURES_STATE.selectedStructure.value
  }
  public icons = {
    database: mdiDatabase,
    unpublish: mdiUmbraco,
    graph: mdiGraphql
  }
  get searchParam() {
    return this.$route.query.search as string || ''
  }
  private dataSource: IStructureService = Structures.getStructureService()
  openModal(item: any) {
    STRUCTURES_STATE.openModal(item)
  }
  closeModal() {
    STRUCTURES_STATE.closeModal()
  }
  formatDate(dateStr: string): string {
  const date = new Date(dateStr)
  return new Intl.DateTimeFormat('en-US', {
    dateStyle: 'short',
    timeStyle: 'medium'
  }).format(date).slice(0, -2);
}

  showConfirmDialog(message: string, action: () => Promise<void>) {
    this.confirmDialogMessage = message
    this.confirmDialogAction = action
    this.confirmDialogVisible = true
  }
  public async publish(item: any) {
    item['publishing'] = true
    this.showConfirmDialog('Are you sure you want to Publish this Structure?', async () => {
      const table: any = this.$refs.crudTable
      try {
        await this.dataSource.publish(item.id)
        table?.find()
      } catch (error: any) {
        console.error(error)
        table?.displayAlert?.(error.message)
      } finally {
        delete item['publishing']
      }
    })
  }

  public async unPublish(item: any) {
    item['publishing'] = true
    const warning =
      'Are you sure you want to Remove Published Status for this Structure?\nAll data saved under this Structure will be permanently deleted.'
    this.showConfirmDialog(warning, async () => {
      const table: any = this.$refs.crudTable
      try {
        await this.dataSource.unPublish(item.id)
        table?.find()
      } catch (error: any) {
        console.error(error)
        table?.displayAlert?.(error.message)
      } finally {
        delete item['publishing']
      }
    })
  }
  onDialogConfirm() {
    this.confirmDialogVisible = false
    if (this.confirmDialogAction) {
      this.confirmDialogAction()
      this.confirmDialogAction = null
    }
  }
  public toStructureItemsPage(structure: any) {
  this.$router.push({ path: `/entity/${structure.id}` })
}

}
</script>
