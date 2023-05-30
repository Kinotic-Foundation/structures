<template>
    <div>
        <v-data-table
            v-resize.quiet="onResize"
            :height="computedHeight"
            :headers="computedHeaders"
            :items="items"
            :server-items-length="totalItems"
            :options.sync="options"
            :loading="loading"
            item-key="identity"
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
                    <v-btn v-if="editable"
                           color="primary"
                           fab
                           small
                           @click="addItem">
                        <v-icon>{{icons.addIcon}}</v-icon>
                    </v-btn>
                </v-toolbar>
            </template>

            <!--  Include slots when defined by parent component  -->
            <!-- Solution found here https://gist.github.com/loilo/73c55ed04917ecf5d682ec70a2a1b8e2 -->
            <slot v-for="(_, name) in $slots" :name="name" :slot="name" />
            <template v-for="(_, name) in $scopedSlots" :slot="name" slot-scope="slotData"><slot :name="name" v-bind="slotData" /></template>

            <!--  Add Action columns  -->
            <template v-if="editable" v-slot:item.action="{ item }" >
                <v-icon title="Edit"
                        small
                        class="mr-2"
                        @click="editItem(item)">
                    {{icons.editIcon}}
                </v-icon>
                <v-icon title="Delete"
                        small
                        @click="deleteItem(item)">
                    {{icons.deleteIcon}}
                </v-icon>
            </template>

        </v-data-table>

        <confirm ref="confirm"></confirm>
    </div>
</template>

<script lang="ts">
import { PropType } from 'vue'
import { Component, Emit, Prop, Vue, Watch } from 'vue-property-decorator'
import { DataOptions, DataTableHeader } from 'vuetify'
import {
    mdiMagnify,
    mdiPlus,
    mdiPencil,
    mdiArrowCollapseLeft,
    mdiArrowCollapseRight,
    mdiChevronLeft,
    mdiChevronRight,
    mdiDelete
} from '@mdi/js'
import {
  Direction,
  Identifiable,
  Order,
  Page,
  Pageable,
  IDataSource,
  IEditableDataSource,
  DataSourceUtils
} from '@kinotic/continuum'
import Confirm from './Confirm.vue'

// noinspection TypeScriptValidateTypes
@Component({
               components: { Confirm }
           })
export default class CrudTable extends Vue {

    /**
     * The {@link IDataSource} to use to fetch data for this table
     */
    @Prop({ type: Object as PropType<IDataSource<any>> , required: true })
    public dataSource!: IDataSource<any>

    /**
     * The {@link DataTableHeader}'s for all columns that should be rendered
     */
    @Prop({ type: Array as PropType<DataTableHeader[]> , required: true })
    public headers!: DataTableHeader[]

    @Prop({ type: Boolean, required: false, default: false })
    public multiSort!: boolean

    @Prop({ type: Boolean, required: false, default: true })
    public mustSort!: boolean

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

    private searchText: string | null = ''

    private options: DataOptions = {
        page: 1,
        itemsPerPage: 10,
        sortBy: [],
        sortDesc: [],
        groupBy: [],
        groupDesc: [],
        multiSort: this.multiSort,
        mustSort: this.mustSort
    }

    constructor() {
        super()
    }

    // Lifecycle hooks
    public mounted() {
        this.find()
    }

    // computed

    // We compute the headers so we can add headers to the end for our actions
    get computedHeaders(): DataTableHeader[] {
        let ret: DataTableHeader[] = []
        if(DataSourceUtils.instanceOfEditableDataSource(this.dataSource)){
            ret = ret.concat(this.headers)
            ret.push({ text: 'Actions', value: 'action', sortable: false, align: 'end' })
        }else{
            ret = this.headers
        }
        return ret
    }

    get editable(): boolean {
      return DataSourceUtils.instanceOfEditableDataSource(this.dataSource)
    }

    // watched
    @Watch('options')
    public watchPagination(value: any, oldValue: any) {
        if (this.finishedInitialLoad) {
            this.find()
        }
    }

    // methods
    public onResize() {
        this.computedHeight = (window.innerHeight - 210)
    }

    @Emit()
    public addItem(): void {
        this.hideAlert()
        // we emit nothing just the event
    }

    @Emit()
    public editItem(item: Identifiable<string>): Identifiable<string> {
        this.hideAlert()
        return Object.assign({}, item) as Identifiable<string>
    }

    public async deleteItem(item: Identifiable<string>) {
        this.hideAlert()
        const index = this.items.indexOf(item)

        if (await (this.$refs.confirm as Confirm).open('Delete Item', 'Are you sure you want to do this?', { color: 'error' })) {
          (this.dataSource as IEditableDataSource<any>).deleteById(item.id).then(() => {
                this.items.splice(index, 1)
                this.totalItems--
                if ((this.totalItems / this.options.itemsPerPage) < this.options.page && this.options.page > 1) {
                    this.options.page--
                    this.find()
                }
            }).catch((error: any) => {
                this.displayAlert(error.message)
            })
        }
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

            if (this.searchText !== null && this.searchText.length > 0) {
                queryPromise = this.dataSource.search(this.searchText, pageable)
            } else {
                queryPromise = this.dataSource.findAll(pageable)
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

    private hideAlert() {
        (this.$notify as any as { close: (value: string) => void }).close('crudTableAlert')
    }

    private displayAlert(text: string) {
        this.$notify({ group: 'alert', type: 'crudTableAlert', text})
    }

}
</script>

<style scoped>

</style>
