<template>
  <div>
    <v-data-table
        v-resize.quiet="onResize"
        :height="computedHeight"
        :headers="headers"
        :items="items"
        :server-items-length="totalItems"
        :options.sync="options"
        :loading="loading"
        item-key="id"
        loading-text="Loading... Please wait"
        class="elevation-1"
        @page-count="pageCount = $event"
        fixed-header
        :footer-props="{
                              showFirstLastPage: true,
                              firstIcon: icons.firstIcon,
                              lastIcon: icons.lastIcon,
                              prevIcon: icons.prevIcon,
                              nextIcon: icons.nextIcon,
                              'items-per-page-options': [5,10,25,50,75,100,-1]
                            }" >

      <template v-slot:body="{ items }">
        <tbody>
        <tr v-if="items.length > 0" v-for="item in items" :key="item.id">
          <td v-for="(key, index) in keys">
            {{ structureProperties[key].type === "date" ? DatetimeUtil.formatDate(item[key]) : item[key] }}
          </td>
        </tr>
        <tr v-if="items.length === 0">
          <td class="py-12" style="margin: 0 auto; text-align: center" :colspan="headers.length">
            <v-btn color="primary" @click="find" v-show="!loading">
              No Data - Push To Search Again
            </v-btn>
          </td>
        </tr>
        </tbody>
      </template>

      <template v-slot:top>
        <v-toolbar flat >
          <v-text-field clearable
                        hide-details
                        single-line
                        hint="Press Enter to Search"
                        v-model="searchText"
                        :append-icon="icons.searchIcon"
                        label="Search"
                        @click:clear="clearSearch"
                        @focus="$event.target.select()"
                        @keyup.enter.capture="search($event)">
          </v-text-field>
          <v-spacer></v-spacer>
        </v-toolbar>
      </template>

    </v-data-table>
  </div>
</template>

<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator'
import {DataOptions, DataTableHeader} from 'vuetify'
import {Identifiable, Order, Direction, Pageable, Page, Continuum} from '@kinotic/continuum'
import CrudTable from '@/frontends/continuum/components/CrudTable.vue'
import {
  mdiPlus,
  mdiPencil,
  mdiDelete,
  mdiClose,
  mdiDatabase,
  mdiMinusCircle,
  mdiPlusCircle,
  mdiArrowCollapseLeft,
  mdiArrowCollapseRight,
  mdiUmbraco, mdiMagnify, mdiChevronLeft, mdiChevronRight
} from '@mdi/js'
import {
  IJsonEntitiesService,
  IStructureService,
  JsonEntitiesService
} from "@/frontends/structures-admin/services";
import DatetimeUtil from "@/frontends/structures-admin/pages/structures/util/DatetimeUtil";
import {Structure} from "@/frontends/structures-admin/pages/structures/structures/Structure";

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
export default class EntityList extends Vue {


  @Prop({type: String, required: true})
  public structureId!: string


  /**
   * Icons
   */
  private icons = {
    searchIcon: mdiMagnify,
    addIcon: mdiPlus,
    editIcon: mdiPencil,
    deleteIcon: mdiDelete,
    firstIcon: mdiArrowCollapseLeft,
    lastIcon: mdiArrowCollapseRight,
    prevIcon: mdiChevronLeft,
    nextIcon: mdiChevronRight,
  }


  /**
   * Internal state management vars
   */
  private computedHeight: number = (window.innerHeight - 210)
  private loading: boolean = false
  private finishedInitialLoad: boolean = false
  private items: Array<Identifiable<string>> = new Array<Identifiable<string>>()
  private totalItems: number = 0
  private searchText: string | null = null
  private keys: string[] = []
  private headers: DataTableHeader[] = []
  private structureProperties: any = {}

  private structure!: Structure

  /**
   * Services
   */
  private jsonEntitiesService: IJsonEntitiesService = new JsonEntitiesService()
  private structureService!: IStructureService

  private options: DataOptions = {
    page: 1,
    itemsPerPage: 10,
    sortBy: [],
    sortDesc: [],
    groupBy: [],
    groupDesc: [],
    multiSort: true,
    mustSort: true
  }

  constructor() {
    super()
  }

  // Lifecycle hooks
  public created() {
    this.structureService = Continuum.crudServiceProxy('org.kinotic.structures.api.services.StructureService') as IStructureService
  }

  public mounted() {
    this.structureService.findById(this.structureId)
        .then((structure) => {
          this.structure = structure
          this.structureProperties = this.structure.entityDefinition.properties
          let keys: string[] = Object.keys(this.structureProperties)
          console.log(`keys ${keys}`)
          for (let key of keys) {
            if (this.structureProperties.hasOwnProperty(key)) {
              let definition: any = this.structureProperties[key]
              let fieldName = key.charAt(0).toUpperCase() + key.slice(1)
              let sortable: boolean = true
              // FIXME: how to ensure we don't try and sort on full text search fields?
              if (definition.type === "ref" || definition.type === "array" || definition.type === "object") {
                sortable = false
              }
              this.headers.push({text: fieldName, value: key, sortable: sortable})
              this.keys.push(key)
            }
          }
          this.find()
      })
      .catch((error) => {
        console.error(`Error occurred during retrieval of structure properties : ${error.message}`)
        this.displayAlert(error.message)
      })
  }

  public onResize() {
    this.computedHeight = (window.innerHeight - 210)
  }

  public clearSearch(): void {
    this.searchText = null
    this.options.page = 1
    this.find()
  }

  public search(event: Event): void {
    this.options.page = 1
    this.find()
    let input: HTMLInputElement = event.target as HTMLInputElement
    input.select()
  }

  private hideAlert() {
    (this.$notify as any as { close: (value: string) => void }).close('crudTableAlert')
  }

  public displayAlert(text: string) {
    this.$notify({ group: 'alert', type: 'crudTableAlert', text})
  }


  public find(): void {
    if(!this.loading) {
      this.loading = true
      this.hideAlert()

      const orders: Order[] = []
      for (const [index, value] of this.options.sortBy.entries()) {
        let direction: Direction = Direction.ASC
        if (this.options.sortDesc[index]) {
          direction = Direction.DESC
        }
        orders.push(new Order(value, direction))
      }

      const pageable: Pageable = {
        pageNumber: this.options.page - 1,
        pageSize: this.options.itemsPerPage,
        sort: {orders}
      }

      let queryPromise!: Promise<Page<any>>

      if (this.searchText !== null && this.searchText.length > 0) {
        queryPromise = this.jsonEntitiesService.search(this.structureId, this.searchText, pageable)
      } else {
        queryPromise = this.jsonEntitiesService.findAll(this.structureId, pageable)
      }

      queryPromise.then((page: Page<any>) => {
        this.loading = false
        this.totalItems = page.totalElements
        this.items = page.content

        if (!this.finishedInitialLoad) {
          setTimeout(() => {
            this.finishedInitialLoad = true
          }, 500)
        }
      }).catch((error: any) => {
        this.loading = false
        this.displayAlert(error.message)

        if (!this.finishedInitialLoad) {
          setTimeout(() => {
            this.finishedInitialLoad = true
          }, 500)
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
