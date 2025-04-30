<template>
  <div class="pt-4">

    <CrudTable :data-source="dataSource" :headers="headers" :singleExpand="false" @add-item="onAddItem"
      @edit-item="onEditItem" ref="crudTable">
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
          <RouterLink target="_blank" :to="'/gql-ui.html?namespace=' + item.id">
            <svg width="20" height="20" viewBox="0 0 24 24">
              <path :d="icons.graph" fill="currentColor" />
            </svg>
          </RouterLink>

        </Button>
      </template>
    </CrudTable>
  </div>
</template>

<script lang="ts">

import { Component, Vue } from 'vue-facing-decorator'
import CrudTable from '@/components/CrudTable.vue'
import { type Identifiable } from '@kinotic/continuum-client'
import { Structures, INamespaceService } from '@kinotic/structures-api'
import { mdiGraphql, mdiApi } from '@mdi/js'
import { USER_STATE } from '@/states/IUserState'
@Component({
  components: { CrudTable }
})
class NamespaceList extends Vue {
  headers = [
    { field: 'id', header: 'Id', sortable: false },
    { field: 'description', header: 'Description', sortable: false },
    { field: 'created', header: 'Created', sortable: false },
    { field: 'updated', header: 'Updated', sortable: false },
  ]

  dataSource: INamespaceService = Structures.getNamespaceService()

  icons = {
    graph: mdiGraphql,
    api: mdiApi
  }
  async mounted() {
    console.log(this.icons.api, "asdsadsa")
    try {
      if (!USER_STATE.isAuthenticated()) {
        await USER_STATE.authenticate('admin', 'structures') // Replace with real credentials
      }
      this.refreshTable()
      if (this.$route.query.created === 'true') {
        this.$router.replace({ query: {} })
      }

    } catch (error) {
      console.error('[NamespaceList] Auth or connection failed:', error)
    }
  }


  private refreshTable(): void {
    (this.$refs.crudTable as any).find()
  }
  onAddItem() {
    this.$router.push(`/application-add`)
  }

  onEditItem(item: Identifiable<string>) {
    this.$router.push(`${this.$route.path}/edit/${item.id}`)
  }
}

export default NamespaceList
</script>