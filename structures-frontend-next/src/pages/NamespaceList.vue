<template>
  <div class="pt-4">
    <CrudTable rowHoverColor="" title="Applications" subtitle="List of all applications in the system"
      :data-source="dataSource" :headers="headers" :singleExpand="false" @add-item="onAddItem" @edit-item="onEditItem"
      ref="crudTable">
      <template #item.id="{ item }">
        <span>{{ item.id }}</span>
      </template>
      <template #item.description="{ item }">
        {{ item.description }}
      </template>

      <template #additional-actions="{ item }">
        <Button text class="!text-[#334155] !bg-white" :title="'OpenAPI'">
          <RouterLink target="_blank" :to="'/scalar-ui.html?namespace=' + item.id">
            <svg width="20" height="20" viewBox="0 0 24 24">
              <path :d="icons.api" fill="currentColor" />
            </svg>
          </RouterLink>
        </Button>
        <Button text class="!text-[#334155] !bg-white" :title="'GraphQL'">
          <RouterLink :to="{ path: '/graphql', query: { namespace: item.id } }">
            <svg width="20" height="20" viewBox="0 0 24 24">
              <path :d="icons.graph" fill="currentColor" />
            </svg>
          </RouterLink>
          <!-- <div target="_blank" @click="() => toGraphql(item.id)">
            <svg width="20" height="20" viewBox="0 0 24 24">
              <path :d="icons.graph" fill="currentColor" />
            </svg>
          </div> -->
          <!-- <RouterLink target="_blank" to="/gql-ui">
            <svg width="20" height="20" viewBox="0 0 24 24">
              <path :d="icons.graph" fill="currentColor" />
            </svg>
          </RouterLink> -->
        </Button>
      </template>
    </CrudTable>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-facing-decorator'
import CrudTable from '@/components/CrudTable.vue'
import { type Identifiable } from '@kinotic/continuum-client'
import { Structures, type INamespaceService } from '@kinotic/structures-api'
import { mdiGraphql, mdiApi } from '@mdi/js'
import { USER_STATE } from '@/states/IUserState'
import { graphqlURI } from '@/util/helpers'

interface CrudHeader {
  field: string
  header: string
  sortable?: boolean
}

@Component({
  components: { CrudTable }
})
export default class NamespaceList extends Vue {
  [x: string]: any
  headers: CrudHeader[] = [
    { field: 'id', header: 'Id', sortable: false },
    { field: 'description', header: 'Description', sortable: false },
    { field: 'created', header: 'Created', sortable: false },
    { field: 'updated', header: 'Updated', sortable: false },
  ]

  dataSource: INamespaceService = Structures.getNamespaceService()
  token = '';
  icons = {
    graph: mdiGraphql,
    api: mdiApi
  }

  toGraphql(id: string) {
    const URI = graphqlURI(id)
    window.open(URI)
  }

  async mounted(): Promise<void> {
    try {
      if (!USER_STATE.isAuthenticated()) {
        await USER_STATE.authenticate('admin', 'structures')
      }

      this.refreshTable()
      this.token = USER_STATE.btoaToken;
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
    this.$router.push(`/application-add`)
  }

  onEditItem(item: Identifiable<string>): void {
    this.$router.push(`${this.$route.path}/edit/${item.id}`)
  }
}
</script>
