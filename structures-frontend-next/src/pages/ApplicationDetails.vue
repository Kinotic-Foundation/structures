<script lang="ts">
import { Component, Vue } from 'vue-facing-decorator'
import CrudTable from '@/components/CrudTable.vue'
import type { Identifiable } from '@kinotic/continuum-client'
import { type INamespaceService, Structures } from '@kinotic/structures-api'
import { mdiGraphql, mdiApi } from '@mdi/js'
import NewProjectSidebar from '@/components/NewProjectSidebar.vue'
interface CrudHeader {
  field: string
  header: string
  sortable?: boolean
}

@Component({
  components: { CrudTable,NewProjectSidebar }
})
export default class ApplicationDetails extends Vue {
  activeTab = 'projects'
  showProjectSidebar = false

  headers: CrudHeader[] = [
    { field: 'id', header: 'Id', sortable: false },
    { field: 'description', header: 'Description', sortable: false },
    { field: 'created', header: 'Created', sortable: false },
    { field: 'updated', header: 'Updated', sortable: false }
  ]

  dataSource: INamespaceService = Structures.getNamespaceService()
  icons = { graph: mdiGraphql, api: mdiApi }

  showSidebar = false

  async mounted(): Promise<void> {
    try {
      this.refreshTable()
      if (this.$route.query.created === 'true') {
        this.$router.replace({ query: {} })
      }
    } catch (error: unknown) {
      const message = error instanceof Error ? error.message : 'Unknown error'
      console.error('[NamespaceList] Auth or connection failed:', message)
    }
  }

  private refreshTable(): void {
    const tableRef = this.$refs.crudTable as InstanceType<typeof CrudTable> | undefined
    tableRef?.find()
  }

  onAddProject(): void {
    this.showProjectSidebar = true
  }
  onProjectSidebarClose(): void {
    this.showProjectSidebar = false
  }

  onProjectSubmit(data: {
    name: string
    description: string
    source: string
    language: string
  }): void {
    console.log('Project created:', data)
    this.showProjectSidebar = false
    this.refreshTable()
  }

  onAddItem(): void {
    this.showSidebar = true
  }

  toApplicationPage(item: Identifiable<string>): void {
    this.$router.push(`/application/${encodeURIComponent(item.id)}`)
  }

  onSidebarClose(): void {
    this.showSidebar = false
  }

  onApplicationSubmit(data: {
    name: string
    description: string
    graphql: boolean
    openapi: boolean
  }): void {
    console.log('Submitting form:', data)
    this.refreshTable()
  }

  onEditItem(item: Identifiable<string>): void {
    this.$router.push(`${this.$route.path}/edit/${item.id}`)
  }

  switchTab(tab: string): void {
    this.activeTab = tab
  }
}
</script>

<template>
  <div class="p-10">
    <div class="flex justify-between items-center mb-6">
      <div>
        <h1 class="font-semibold text-2xl text-[#101010] mb-3">Application name</h1>
        <p class="text-[#101010] font-normal text-sm">12 projects, 24 structures</p>
      </div>
      <div class="flex gap-3">
        <div class="border border-[#E6E7EB] rounded-xl flex justify-center items-center gap-2 py-[17px] px-[61px]">
          <img src="@/assets/graphql.svg" class="w-6 h-6" />
          <span class="text-sm text-[#101010] font-semibold">GraphQL</span>
        </div>
        <div class="border border-[#E6E7EB] rounded-xl flex justify-center items-center gap-2 py-[17px] px-[61px]">
          <img src="@/assets/scalar.svg" class="w-6 h-6" />
          <span class="text-sm text-[#101010] font-semibold">OpenAPI</span>
        </div>
      </div>
    </div>
    <div class="flex gap-6 border-b border-[#E6E7EB] mb-6">
      <button
        @click="switchTab('projects')"
        :class="[
          'text-sm font-semibold pb-3 border-b-2',
          activeTab === 'projects' ? 'border-[#3D5ACC] text-[#101010]' : 'border-transparent text-[#999CA0]'
        ]"
      >
        Projects
      </button>
      <button
        @click="switchTab('structures')"
        :class="[
          'text-sm font-semibold pb-3 border-b-2',
          activeTab === 'structures' ? 'border-[#3D5ACC] text-[#101010]' : 'border-transparent text-[#999CA0]'
        ]"
      >
        Structures
      </button>
    </div>
    <CrudTable
      v-if="activeTab === 'projects'"
      rowHoverColor=""
      :data-source="dataSource"
      :headers="headers"
      :singleExpand="false"
      @add-item="onAddProject"
      @edit-item="onEditItem"
      ref="crudTable"
      @onRowClick="toApplicationPage"
      createNewButtonText="New project"
    >
      <template #item.id="{ item }">
        <span>{{ item.id }}</span>
      </template>

      <template #additional-actions="{ item }">
        <Button text class="!text-[#334155] !bg-white" :title="'GraphQL'">
          <RouterLink :to="{ path: '/graphql', query: { namespace: item.id } }">
            <img src="@/assets/graphql.svg" />
          </RouterLink>
        </Button>
        <Button text class="!text-[#334155] !bg-white" :title="'OpenAPI'">
          <RouterLink target="_blank" :to="'/scalar-ui.html?namespace=' + item.id">
            <img src="@/assets/scalar.svg" />
          </RouterLink>
        </Button>
      </template>
    </CrudTable>
    <CrudTable
    v-else-if="activeTab === 'structures'"
      rowHoverColor=""
      :data-source="dataSource"
      :headers="headers"
      :singleExpand="false"
      @add-item="onAddItem"
      @edit-item="onEditItem"
      ref="crudTable"
      @onRowClick="toApplicationPage"
      createNewButtonText="New Structure"
    >
      <template #item.id="{ item }">
        <span>{{ item.id }}</span>
      </template>

      <template #additional-actions="{ item }">
        <Button text class="!text-[#334155] !bg-white" :title="'GraphQL'">
          <RouterLink :to="{ path: '/graphql', query: { namespace: item.id } }">
            <img src="@/assets/graphql.svg" />
          </RouterLink>
        </Button>
        <Button text class="!text-[#334155] !bg-white" :title="'OpenAPI'">
          <RouterLink target="_blank" :to="'/scalar-ui.html?namespace=' + item.id">
            <img src="@/assets/scalar.svg" />
          </RouterLink>
        </Button>
      </template>
    </CrudTable>
      <NewProjectSidebar
    :visible="showProjectSidebar"
    @close="onProjectSidebarClose"
    @submit="onProjectSubmit"
    />
  </div>
</template>
