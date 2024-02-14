<template>
  <div>
    <v-data-iterator
        v-resize.quiet="onResize"
        :height="computedHeight"
        :items="items"
        :server-items-length="totalItems"
        :options.sync="options"
        :loading="loading"
        :item-key="itemKey"
        loading-text="Loading... Please wait"
        class="elevation-1"
        @page-count="pageCount = $event"
        :footer-props="{
                              showFirstLastPage: true,
                              firstIcon: firstIcon,
                              lastIcon: lastIcon,
                              prevIcon: prevIcon,
                              nextIcon: nextIcon,
                              'items-per-page-options': [5,10,25,50,75,100,-1]
                            }" >

      <!--  Include slots when defined by parent component  -->
      <!-- Solution found here https://gist.github.com/loilo/73c55ed04917ecf5d682ec70a2a1b8e2 -->
      <slot v-for="(_, name) in $slots" :name="name" :slot="name" />

    </v-data-iterator>

    <confirm ref="confirm"></confirm>
  </div>
</template>

<script lang="ts">
import { PropType } from 'vue'
import { Component, Emit, Prop, Vue, Watch } from 'vue-property-decorator'
import { DataOptions, DataTableHeader } from 'vuetify'
import { IDataSource } from '@kinotic/continuum-client'
import {
  mdiMagnify,
  mdiArrowCollapseLeft,
  mdiArrowCollapseRight,
  mdiChevronLeft,
  mdiChevronRight,
} from '@mdi/js'
import {
  Direction,
  Identifiable,
  Order,
  Page,
  Pageable
} from '@kinotic/continuum-client'
import Confirm from './Confirm.vue'

// noinspection TypeScriptValidateTypes
@Component({
             components: { Confirm }
           })
export default class DataIterator extends Vue {

  /**
   * The property on each item that is used as a unique key
   */
  @Prop({ type: String, required: true })
  public itemKey!: string

  /**
   * The {@link IDataSource} to be used for data access
   */
  @Prop({ type: Object as PropType<IDataSource<any>> , required: true })
  public dataSource!: IDataSource<any>

  /**
   * Icons
   */
  private searchIcon: string = mdiMagnify
  private firstIcon: string = mdiArrowCollapseLeft
  private lastIcon: string = mdiArrowCollapseRight
  private prevIcon: string = mdiChevronLeft
  private nextIcon: string = mdiChevronRight

  /**
   * Internal state management vars
   */
  private computedHeight: number = (window.innerHeight - 210)
  private loading: boolean = false
  private finishedInitialLoad: boolean = false

  private items: Array<Identifiable<string>> = new Array<Identifiable<string>>()
  private totalItems: number = 0

  private searchText: string | null = ''

  private options: DataOptions = {
    page: 1,
    itemsPerPage: 10,
    sortBy: [],
    sortDesc: [],
    groupBy: [],
    groupDesc: [],
    multiSort: false,
    mustSort: true
  }

  constructor() {
    super()
  }

  // Lifecycle hooks
  public mounted() {
    this.find()
  }

  // computed

  // watched
  @Watch('options')
  public watchPagination(value: any, oldValue: any) {
    if (this.finishedInitialLoad) {
      this.find()
    }
  }

  // methods
  public onResize() {
    this.computedHeight = (window.innerHeight - 245)
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

      queryPromise = this.dataSource.findAll(pageable)

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

  private hideAlert() {
    (this.$notify as any as { close: (value: string) => void }).close('dataIteratorAlert')
  }

  private displayAlert(text: string) {
    this.$notify({ group: 'alert', type: 'dataIteratorAlert', text})
  }

}
</script>

<style scoped>

</style>
