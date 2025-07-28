<script lang="ts">
import { Component, Vue, Prop, Ref, Watch } from 'vue-facing-decorator'
import CrudTable from '@/components/CrudTable.vue'
import StructureItemModal from '@/components/modals/StructureItemModal.vue'
import type { Identifiable, IterablePage, Pageable } from '@kinotic/continuum-client'
import { APPLICATION_STATE } from '@/states/IApplicationState'
import { Structure, Structures } from '@kinotic/structures-api'
import type { CrudHeader } from '@/types/CrudHeader'
import DatetimeUtil from "@/util/DatetimeUtil"

@Component({
  components: { CrudTable, StructureItemModal }
})
export default class StructuresList extends Vue {
  @Prop({ required: true }) applicationId!: string
  @Prop({ required: false, default: undefined }) projectId?: string
  @Prop({ required: false, default: '' }) initialSearch!: string

  @Ref('crudTable') crudTable!: InstanceType<typeof CrudTable>

  structureTableHeaders: CrudHeader[] = [
    { field: 'name', header: 'Structure name', sortable: true },
    { field: 'projectId', header: 'Project', sortable: true },
    { field: 'description', header: 'Description', sortable: false },
    { field: 'created', header: 'Created', sortable: false },
    { field: 'updated', header: 'Updated', sortable: false },
    { field: 'published', header: 'Status', sortable: false }
  ]

  showModal = false
  selectedStructure: Structure | null = null

  searchText = ''
  isInitialized = false

  mounted(): void {
    this.searchText = this.initialSearch || ''
    this.isInitialized = true
  }

  @Watch('initialSearch')
  onInitialSearchChanged(newVal: string) {
    if (this.isInitialized) {
      this.searchText = newVal || ''
      this.refreshTable()
    }
  }

  @Watch('applicationId')
  onApplicationIdChanged() {
    this.refreshTable()
  }

  @Watch('projectId')
  onProjectIdChanged() {
    this.refreshTable()
  }

  get dataSource() {
    return {
      findAll: async (pageable: Pageable): Promise<IterablePage<Structure>> => {
        const service = Structures.getStructureService()
        const result = this.projectId
          ? await service.findAllForProject(this.projectId, pageable)
          : await service.findAllForApplication(this.applicationId, pageable)

        APPLICATION_STATE.structuresCount = result.totalElements ?? 0
        return result
      },
      search: async (_searchText: string, pageable: Pageable): Promise<IterablePage<Structure>> => {
        const filter = this.projectId ? `projectId:${this.projectId}` : `applicationId:${this.applicationId}`
        const query = `${filter} && ${this.searchText}`
        return Structures.getStructureService().search(query, pageable)
      }
    }
  }

  get structuresCount() {
    return APPLICATION_STATE.structuresCount
  }
  public DatetimeUtil = DatetimeUtil
  refreshTable(): void {
    if (this.crudTable?.find) {
      this.crudTable.find()
    }
  }

  updateRouteQuery(newSearch: string): void {
    this.searchText = newSearch
    const query = { ...this.$route.query }

    if (newSearch) {
      query['search-structure'] = newSearch
    } else {
      delete query['search-structure']
    }

    this.$router.replace({ query }).catch(() => { })
    this.refreshTable()
  }

  openModal(item: Structure) {
    this.selectedStructure = item
    this.showModal = true
  }

  closeModal() {
    this.showModal = false
    this.selectedStructure = null
  }

  handleRowClick(item: Structure): void {
    this.openModal(item)
  }

  onEditItem(item: Identifiable<string>): void {
    this.$router.push(`${this.$route.path}/edit/${item.id}`)
  }
}
</script>

<template>
  <div>
    <CrudTable ref="crudTable" rowHoverColor="" :data-source="dataSource" :headers="structureTableHeaders"
      :singleExpand="false" :search="searchText" @update:search="updateRouteQuery" @edit-item="onEditItem"
      @onRowClick="handleRowClick" createNewButtonText="New Structure" emptyStateText="No structures yet">
      <template #item.id="{ item }">
        <span>{{ item.id }}</span>
      </template>
            <template #item.created="{ item }">
        <span>
          {{ DatetimeUtil.formatMonthDayYear(item.created) }}
        </span>
      </template>
      <template #item.updated="{ item }">
        <span>
          {{ DatetimeUtil.formatRelativeDate(item.updated) }}
        </span>
      </template>
    </CrudTable>

    <StructureItemModal v-if="showModal && selectedStructure" :item="selectedStructure" @close="closeModal" />
  </div>
</template>
