<template>
  <v-container class="no-gutters" fill-height fluid>
    <v-row class="no-gutters fill-height">
      <v-col>
        <CrudTable :data-source="dataSource"
                   :headers="headers"
                   :singleExpand="true"
                   @add-item="onAddItem"
                   @edit-item="onEditItem"
                   ref="crudTable">

          <template v-slot:item.created="{ item }">
            {{ DatetimeUtil.formatDate(item.created) }}
          </template>
          <template v-slot:item.updated="{ item }">
            {{ DatetimeUtil.formatDate(item.updated) }}
          </template>
          <template v-slot:item.publishedTimestamp="{ item }">
            {{ DatetimeUtil.formatDate(item.publishedTimestamp) }}
          </template>

          <template v-slot:item.published="{ item }">
            <v-icon medium
                    v-show="item.published"
                    title="Structure Published">
              fab fa-product-hunt
            </v-icon>
          </template>

          <template v-slot:expanded-item="{ item }"  >
            <td :colspan="headers.length" class="pa-6" :key="item.id">
              <pre>{{item?.entityDefinition}}</pre>
            </td>
          </template>

          <template #additional-actions="{ item }" >
            <v-btn small
                   icon
                   class="mr-2"
                   v-show="!item.item.published"
                   :key="'publish-'+item.item.id"
                   :loading="item['publishing']"
                   @click="publish(item)"
                   title="Publish">
              <v-icon small>
                fab fa-product-hunt
              </v-icon>
            </v-btn>
            <v-btn small
                   icon
                   class="mr-2"
                   v-show="item.item.published"
                   @click="toStructureItemsPage(item.item)"
                   title="Manage">
              <v-icon small>
                {{icons.database}}
              </v-icon>
            </v-btn>
            <v-btn small
                   icon
                   class="mr-2 red--text"
                   v-show="item.item.published"
                   :loading="item['publishing']"
                   :key="'unpublish-'+item.item.id"
                   @click="unPublish(item)"
                   title="Un-Publish">
              <v-icon small>
                {{ icons.unpublish }}
              </v-icon>
            </v-btn>
          </template>
        </CrudTable>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { DataTableHeader } from 'vuetify'
import {Identifiable} from '@kinotic/continuum'
import CrudTable from '@/frontends/continuum/components/CrudTable.vue'
import {
  mdiDatabase,
  mdiUmbraco
} from '@mdi/js'
import {IStructureService} from '@/frontends/structures-admin/services/IStructureService'
import DatetimeUtil from '@/frontends/structures-admin/pages/structures/util/DatetimeUtil'
import {Structures} from "@/frontends/structures-admin/services";

/**
 * Provides a List page that can be used with the {@link CrudLayout}
 * to display a {@link CrudTable} when you do not need to override any columns
 */

@Component({
             computed: {
               DatetimeUtil() {
                 return DatetimeUtil
               }
             },
             components: { CrudTable }
           })
export default class StructuresList extends Vue {

  private headers: DataTableHeader[] = [
    { text: '', value: 'data-table-expand', sortable: false },
    { text: 'Id', value: 'id', sortable: true },
    { text: 'Namespace', value: 'namespace', sortable: true },
    { text: 'Name', value: 'name', sortable: true },
    { text: 'Description', value: 'description', sortable: false },
    { text: 'Created', value: 'created', sortable: true },
    { text: 'Last Updated', value: 'updated', sortable: true },
    { text: 'Published', value: 'published', sortable: true },
    { text: 'Published On', value: 'publishedTimestamp', sortable: true }
  ]

  /**
   * Services
   */
  private dataSource: IStructureService = Structures.getStructureService()
  private loading: {[key: string]: boolean} = {}

  private icons = {
    database: mdiDatabase,
    unpublish: mdiUmbraco
  }

  constructor() {
    super()
  }


  public onAddItem() {
    this.$router.push(`${this.$route.path }/add`)
  }

  public onEditItem(identifiable: Identifiable<string>) {
    this.$router.push(`${this.$route.path}/edit/${identifiable.id}`)
  }

  public async publish(item: any){
    item['publishing'] = true
    if(confirm('Are you sure you want to Publish this Structure?')) {
      let table: any = this.$refs?.crudTable
      try {
        await this.dataSource.publish(item.item.id)
        table?.find() // refresh table
        delete item['publishing']
      }catch (error: any){
        delete item['publishing']
        console.log(error.stack)
        table?.displayAlert(error.message)
      }
    }
  }

  public async unPublish(item: any){
    item['publishing'] = true
    if(confirm('Are you sure you want to Remove Published Status for this Structure? \nAll data saved under this Structure will be permanently deleted, proceed with caution.')) {
      let table: any = this.$refs?.crudTable
      try {
        await this.dataSource.unPublish(item.item.id)
        table?.find() // refresh table
        delete item['publishing']
      }catch (error: any){
        delete item['publishing']
        console.log(error.stack)
        table?.displayAlert(error.message)
      }
    }
  }

  public toStructureItemsPage(structure: any){
    this.$router.push({ path: `/entity/${structure.id}` })
  }
}
</script>

<style scoped>

</style>
