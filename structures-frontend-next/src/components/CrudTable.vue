<script lang="ts">
import {
  Vue,
  Prop,
  Emit,
  toNative,
  Component,
  Watch
} from 'vue-facing-decorator'

import DataTable, { type DataTablePageEvent, type DataTableSortEvent } from 'primevue/datatable'
import Column from 'primevue/column'
import Button from 'primevue/button'
import Toolbar from 'primevue/toolbar'
import InputText from 'primevue/inputtext'
import ConfirmDialog from 'primevue/confirmdialog'
import InputGroup from 'primevue/inputgroup'
import InputGroupAddon from 'primevue/inputgroupaddon'

import {
  type IDataSource,
  type Identifiable,
  Order,
  type Page,
  Pageable,
  Direction,
  DataSourceUtils,
  type IEditableDataSource,
} from '@kinotic/continuum-client'

interface CrudHeader {
  field: string
  header: string
  sortable?: boolean
}

@Component
class CrudTable extends Vue {
  static components = {
    DataTable,
    Column,
    Button,
    Toolbar,
    InputText,
    ConfirmDialog,
    InputGroup,
    InputGroupAddon
  }

  @Prop({ required: true }) dataSource!: IDataSource<Identifiable<string>>
  @Prop({ required: true }) headers!: CrudHeader[]
  @Prop({ default: false }) multiSort!: boolean
  @Prop({ default: true }) mustSort!: boolean
  @Prop({ default: false }) singleExpand!: boolean
  @Prop({ default: false }) disableModifications!: boolean
  @Prop({ default: true }) isShowAddNew!: boolean
  @Prop({ default: true }) isShowDelete!: boolean
  @Prop({ default: '' }) initialSearch!: string
  @Prop({ default: '#f5f5f5' }) rowHoverColor!: string
  @Prop({ default: 'Add new' }) createNewButtonText!: string
  @Prop({ default: false }) enableViewSwitcher!: boolean

  items: Identifiable<string>[] = []
  totalItems = 0
  loading = false
  finishedInitialLoad = false
  searchDebounceTimer: ReturnType<typeof setTimeout> | null = null
  activeView: 'burger' | 'column' = 'burger'

  searchText: string | null = ''
  options = {
    page: 0,
    rows: 10,
    first: 0,
    sortField: '',
    sortOrder: 1 as 1 | -1
  }

  get editable(): boolean {
    return this.dataSource && DataSourceUtils.instanceOfEditableDataSource(this.dataSource)
  }

  get computedHeaders(): CrudHeader[] {
    return this.headers
  }
  get isBurgerView(): boolean {
    return this.enableViewSwitcher ? this.activeView === 'burger' : true
  }

  get isColumnView(): boolean {
    return this.enableViewSwitcher && this.activeView === 'column'
  }
  mounted() {
    this.searchText = (this.$route.query.search as string) || ''
    this.find()
  }

  @Watch('searchText')
  onSearchTextChanged(newVal: string) {
    const query = { ...this.$route.query, search: newVal || undefined }
    this.$router.replace({ query })
  }

  @Watch('dataSource', { immediate: true })
  onDataSourceChanged(newVal: IDataSource<Identifiable<string>>) {
    if (newVal) {
      this.searchText = (this.$route.query.search as string) || ''
      this.find()
    }
  }

  onPage(event: DataTablePageEvent) {
    this.options.page = event.page
    this.options.rows = event.rows
    this.options.first = event.first
    this.find()
  }

  onSort(event: DataTableSortEvent) {
    this.options.sortField = typeof event.sortField === 'string' ? event.sortField : ''
    this.options.sortOrder = event.sortOrder === 1 || event.sortOrder === -1 ? event.sortOrder : 1
    this.find()
  }

  beforeUnmount() {
    if (this.searchDebounceTimer) {
      clearTimeout(this.searchDebounceTimer)
    }
  }

  onSearchChange() {
    if (this.searchDebounceTimer) {
      clearTimeout(this.searchDebounceTimer)
    }
    this.searchDebounceTimer = setTimeout(() => {
      this.options.page = 0
      this.options.first = 0
      this.find()
    }, 500)
  }

  @Emit()
  addItem(): void { }

  @Emit()
  editItem(item: Identifiable<string>): Identifiable<string> {
    return { ...item }
  }

  @Emit()
  onRowClick(event: { data: Identifiable<string>; index: number }): Identifiable<string> {
    return { ...event.data }
  }

  async deleteItem(item: Identifiable<string>) {
    if (!item.id || !this.editable) return

    const confirmRef = this.$refs.confirm as any
    const confirmed = await confirmRef.open(
      'Delete Item',
      'Are you sure you want to delete this item?',
      { width: 400, style: { maxWidth: '400px' } }
    )

    if (!confirmed) return

    try {
      await (this.dataSource as IEditableDataSource<Identifiable<string>>).deleteById(item.id)

      this.items = this.items.filter(i => i.id !== item.id)
      this.totalItems--

      const totalPages = Math.ceil(this.totalItems / this.options.rows)
      if (this.options.page >= totalPages && this.options.page > 0) {
        this.options.page--
      }

      setTimeout(() => this.find(), 300)
    } catch (error: unknown) {
      this.displayAlert((error as Error).message || 'Failed to delete item.')
    }
  }

  search() {
    this.options.page = 0
    this.options.first = 0
    this.find()
  }
  handleCardClick(item: Identifiable<string>, index: number) {
    this.onRowClick({ data: item, index })
  }
  find() {
    if (!this.loading && this.dataSource) {
      this.loading = true
      const orders: Order[] = []

      if (this.options.sortField) {
        orders.push(
          new Order(
            this.options.sortField,
            this.options.sortOrder === -1 ? Direction.DESC : Direction.ASC
          )
        )
      }

      const pageable = Pageable.create(this.options.page, this.options.rows, { orders })

      const queryPromise: Promise<Page<Identifiable<string>>> = this.searchText
        ? this.dataSource.search(this.searchText, pageable)
        : this.dataSource.findAll(pageable)

      queryPromise
        .then((page: Page<Identifiable<string>>) => {
          this.loading = false
          this.totalItems = page.totalElements ?? 0
          this.items = page.content ?? []
          if (!this.finishedInitialLoad) {
            setTimeout(() => {
              this.finishedInitialLoad = true
            }, 500)
          }
        })
        .catch((error: unknown) => {
          this.loading = false
          this.displayAlert((error as Error).message)
        })
    }
  }

  displayAlert(text: string) {
    console.warn('[CRUD Table Alert]:', text)
  }
}

export default toNative(CrudTable)
</script>

<template>
  <div :style="{ '--row-hover-color': rowHoverColor }">
    <Toolbar class="!border-none !px-0 !mb-6 !py-0">
      <template #start>
        <div class="relative w-full max-w-sm">
          <i class="pi pi-search absolute left-4 top-1/2 -translate-y-1/2 text-gray-400" />
          <InputText v-model="searchText" placeholder="Search" @input="onSearchChange" @keyup.enter="search"
            class="!pl-10 !pr-4 !py-2 !w-full !rounded-xl !border !border-[#D2D3D9] !text-[#797B80]" />
        </div>
      </template>

      <template #end>
        <div class="flex">
          <div v-if="enableViewSwitcher" class="flex mr-3 p-1 bg-[#F0F1F5] gap-2 rounded-xl">
            <div class="p-2 rounded-xl flex justify-center items-center cursor-pointer transition"
              :class="activeView === 'burger' ? 'bg-white' : ''" @click="activeView = 'burger'">
              <img src="@/assets/burger.svg" />
            </div>
            <div class="p-2 rounded-xl flex justify-center items-center cursor-pointer transition"
              :class="activeView === 'column' ? 'bg-white' : ''" @click="activeView = 'column'">
              <img src="@/assets/column.svg" />
            </div>
          </div>
          <Button v-if="!disableModifications && isShowAddNew" @click="addItem" severity="secondary"
            class="!bg-[#3651ED] !text-white hover:!bg-[#274bcc]">
            <img src="@/assets/plus.svg" />
            <span>{{ createNewButtonText }}</span>
          </Button>
        </div>
      </template>
    </Toolbar>

    <div class="mb-9">
      <div v-if="isColumnView" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-5">
      <div
        v-for="(item, index) in items"
        :key="item.id"
        class="bg-white rounded-xl p-4 shadow-sm h-[152px] border border-gray-200 flex flex-col justify-between cursor-pointer hover:shadow-md transition"
        @click="handleCardClick(item, index)"
      >
          <div>
            <h3 class="font-semiboldtext-base text-[#101010] mb-2">{{ item?.id }}</h3>
            <p class="text-sm text-gray-500 mb-3">
              {{ item?.description }}
            </p>
          </div>
          <div class="flex items-center gap-3">
            <Button text class="!text-[#334155] !bg-white !pl-0" :title="'GraphQL'">
              <RouterLink :to="{ path: '/graphql', query: { namespace: item.id } }">
                <img src="@/assets/graphql.svg" />
              </RouterLink>
            </Button>
            <Button text class="!text-[#334155] !bg-white" :title="'OpenAPI'">
              <RouterLink target="_blank" :to="'/scalar-ui.html?namespace=' + item.id">
                <img src="@/assets/scalar.svg" />
              </RouterLink>
            </Button>
          </div>
        </div>
      </div>
      <DataTable v-if="isBurgerView" :value="items" :rowsPerPageOptions="[5, 10, 20, 50]" :paginator="true"
        :rows="options.rows" :totalRecords="totalItems" :lazy="true" :loading="loading" :first="options.first"
        :sortField="options.sortField" :sortOrder="options.sortOrder" :scrollable="true" :removableSort="true"
        scrollHeight="flex" dataKey="id" @page="onPage" @sort="onSort" @row-click="onRowClick" sortMode="multiple">
        <Column v-for="col in computedHeaders" :key="col.field" :field="col.field" :header="col.header"
          :sortable="col.sortable !== false">
          <template #body="slotProps">
            <slot :name="`item.${col.field}`" :item="slotProps.data">
              {{ slotProps.data[col.field] }}
            </slot>
          </template>
        </Column>

        <Column v-if="editable" header="" style="text-align: right">
          <template #body="slotProps">
            <div class="flex justify-center items-center">
              <slot name="additional-actions" :item="slotProps.data" />
            </div>
          </template>
        </Column>
      </DataTable>
    </div>

    <Confirm ref="confirm" />
    <ConfirmDialog />
  </div>
</template>

<style scoped>
::v-deep(.p-datatable-table-container) {
  overflow: auto !important;
  border: 1px solid #E2E8F0 !important;
  border-radius: 26px !important;
  padding: 20px !important;
}

::v-deep(.p-datatable-paginator-bottom) {
  border: none !important;
}

::v-deep(.p-datatable .p-datatable-tbody > tr:hover) {
  background-color: var(--row-hover-color) !important;
}

::v-deep(.no-left-border.p-inputtext) {
  border-left: none !important;
  border-top-left-radius: 0 !important;
  border-bottom-left-radius: 0 !important;
}
</style>
