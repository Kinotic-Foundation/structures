<template>
  <ContainerMedium>
    <h1 class="text-2xl font-semibold mb-5">Applications</h1>

    <CrudTable
      createNewButtonText="New application" 
      rowHoverColor="" 
      :data-source="dataSource" 
      :headers="headers" 
      :singleExpand="false" 
      @add-item="onAddItem"
      @edit-item="onEditItem" 
      ref="crudTable" 
      @onRowClick="toApplicationPage"
      :enableViewSwitcher="true"
    >
      <template #item.id="{ item }">
        <span>{{ item.id }}</span>
      </template>

      <template #additional-actions="{ item }">
        <Button text class="!text-[#334155] !bg-white" :title="'GraphQL'" @click="openGraphQL">
          <img src="@/assets/graphql.svg" />
        </Button>
        <Button text class="!text-[#334155] !bg-white" :title="'OpenAPI'">
          <RouterLink target="_blank" :to="'/scalar-ui.html?namespace=' + item.id">
            <img src="@/assets/scalar.svg" />
          </RouterLink>
        </Button>
      </template>
    </CrudTable>
    <GraphQLModal :visible="showGraphQLModal" @close="closeGraphQL" />
    <ApplicationSidebar :visible="showSidebar" @close="onSidebarClose" @submit="onApplicationSubmit" />
  </ContainerMedium>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-facing-decorator'
import CrudTable from '@/components/CrudTable.vue'
import ContainerMedium from '@/components/ContainerMedium.vue'
import ApplicationSidebar from '@/components/ApplicationSidebar.vue'
import { Structures, type INamespaceService } from '@kinotic/structures-api'
import { type Identifiable } from '@kinotic/continuum-client'
import { mdiGraphql, mdiApi } from '@mdi/js'
import GraphQLModal from '@/components/modals/GraphQLModal.vue'

interface CrudHeader {
  field: string
  header: string
  sortable?: boolean
}

@Component({
  components: {
    CrudTable,
    ContainerMedium,
    ApplicationSidebar,
    GraphQLModal
  }
})
export default class NamespaceList extends Vue {
  headers: CrudHeader[] = [
    { field: 'id', header: 'Id', sortable: false },
    { field: 'description', header: 'Description', sortable: false },
    { field: 'created', header: 'Created', sortable: false },
    { field: 'updated', header: 'Updated', sortable: false }
  ]

  dataSource: INamespaceService = Structures.getNamespaceService()
  icons = { graph: mdiGraphql, api: mdiApi }
  showGraphQLModal = false
  showSidebar = false
  openGraphQL(): void {
    this.showGraphQLModal = true
  }

  closeGraphQL(): void {
    this.showGraphQLModal = false
  }

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
}
</script>
