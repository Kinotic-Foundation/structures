<script lang="ts">
import {
  Vue,
  Prop,
  Emit,
  toNative,
  Component,
  Watch
} from 'vue-facing-decorator'

import DataTable, { type DataTablePageEvent } from 'primevue/datatable'
import Column from 'primevue/column'
import Button from 'primevue/button'
import Toolbar from 'primevue/toolbar'
import InputText from 'primevue/inputtext'
import ConfirmDialog from 'primevue/confirmdialog'
import Card from 'primevue/card'
import Paginator, { type PageState } from 'primevue/paginator'
import SelectButton from 'primevue/selectbutton'

import {
  type IDataSource,
  type Identifiable,
  Order,
  type Page,
  Pageable,
  Direction,
  DataSourceUtils,
} from '@kinotic/continuum-client'

import type { CrudHeader } from '@/types/CrudHeader'
import type { DescriptiveIdentifiable } from '@/types/DescriptiveIdentifiable'

@Component({
  components: {
    DataTable,
    Column,
    Button,
    Toolbar,
    InputText,
    ConfirmDialog,
    Card,
    Paginator,
    SelectButton
  }
})
class CrudTable extends Vue {
  @Prop({ required: true }) dataSource!: IDataSource<DescriptiveIdentifiable>
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
  @Prop({ default: 'No items yet' }) emptyStateText!: string

  items: DescriptiveIdentifiable[] = []
  totalItems = 0
  loading = false
  finishedInitialLoad = false
  searchDebounceTimer: ReturnType<typeof setTimeout> | null = null
  activeView: 'burger' | 'column' = 'burger'

  searchText: string | null = ''
  options = {
    page: 0,
    rows: 9,
    first: 0,
    sortField: '',
    sortOrder: 1 as 1 | -1
  }

  viewOptions = [
    { icon: 'pi pi-bars', value: 'burger' },
    { icon: 'pi pi-th-large', value: 'column' }
  ]

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
      this.find()
    }
  }

  // Split handlers:
  onDataTablePage(event: DataTablePageEvent) {
    this.options.page = event.page
    this.options.rows = event.rows
    this.options.first = event.first
    this.find()
  }

  onPaginatorPage(event: PageState) {
    this.options.page = event.page
    this.options.rows = event.rows
    this.options.first = event.first
    this.find()
  }

  beforeUnmount() {
    if (this.searchDebounceTimer) clearTimeout(this.searchDebounceTimer)
  }

  onSearchChange() {
    if (this.searchDebounceTimer) clearTimeout(this.searchDebounceTimer)
    this.searchDebounceTimer = setTimeout(() => {
      this.options.page = 0
      this.options.first = 0
      this.find()
    }, 400)
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

  handleCardClick(item: Identifiable<string>, index: number) {
    this.onRowClick({ data: item, index })
  }

  find() {
    if (!this.loading && this.dataSource) {
      this.loading = true
      const orders: Order[] = []

      if (this.options.sortField) {
        orders.push(new Order(this.options.sortField, this.options.sortOrder === -1 ? Direction.DESC : Direction.ASC))
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
        })
        .catch((error: unknown) => {
          this.loading = false
          console.error('[CRUD Table Alert]:', error)
        })
    }
  }
}

export default toNative(CrudTable)
</script>

<template>
  <div :style="{ '--row-hover-color': rowHoverColor }">
    <Toolbar class="!border-none !px-0 !mb-6 !py-0">
      <template #start>
        <IconField class="w-full max-w-sm">
          <InputIcon class="pi pi-search" />
          <InputText v-model="searchText" placeholder="Search" @input="onSearchChange" @keyup.enter="find" />
        </IconField>
      </template>

      <template #end>
        <div class="flex items-center gap-2 h-[33px]">
          <SelectButton v-if="enableViewSwitcher" v-model="activeView" :options="viewOptions"
            optionValue="value" dataKey="value" class="h-[33px]">
            <template #option="slotProps">
              <i :class="slotProps.option.icon"></i>
            </template>
          </SelectButton>
          <Button v-if="!disableModifications && isShowAddNew" @click="addItem" :label="createNewButtonText"
            icon="pi pi-plus" class="h-[33px]" />
        </div>
      </template>
    </Toolbar>

    <div class="mb-6">
      <div v-if="isColumnView">
        <div v-if="items.length > 0" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
          <Card v-for="(item, index) in items" :key="item.id || index"
            class="cursor-pointer hover:shadow-md transition-shadow h-[152px] flex flex-col justify-between"
            @click="handleCardClick(item, index)">
            <template #content>
              <div>
                <h3 class="font-semibold text-[#101010] mb-1">{{ item?.id }}</h3>
                <p class="text-sm text-gray-500 truncate">{{ item?.description }}</p>
              </div>
              <div class="flex gap-2 mt-3">
                <Button severity="secondary" text class="p-2"
                  @click.stop="$router.push({ path: '/graphql', query: { namespace: item.id } })">
                  <img src="@/assets/graphql.svg" alt="GraphQL" class="w-5 h-5" />
                </Button>
                <Button severity="secondary" text class="p-2"
                  @click.stop="$router.push('/scalar-ui.html?namespace=' + item.id)">
                  <img src="@/assets/scalar.svg" alt="OpenAPI" class="w-5 h-5" />
                </Button>
              </div>
            </template>
          </Card>
        </div>
        <div v-else class="flex flex-col items-center justify-center text-gray-500 py-20 h-[calc(100vh-300px)]">
          <p class="text-sm">{{ emptyStateText }}</p>
        </div>

        <Paginator :rows="options.rows" :totalRecords="totalItems" @page="onPaginatorPage" class="mt-4" v-if="items.length !== 0" />
      </div>

      <div v-if="isBurgerView" class="p-4 border text-[color:var(--surface-200)] rounded-xl">
        <DataTable :value="items" :rows="options.rows" :totalRecords="totalItems" :lazy="true" :loading="loading"
          :paginator="items.length > 0" :first="options.first" :rowsPerPageOptions="[5, 10, 20]" dataKey="id"
          @page="onDataTablePage" @row-click="onRowClick" sortMode="multiple" table>
          <Column v-for="col in computedHeaders" :key="col.field" :field="col.field" :header="col.header"
            :sortable="col.sortable !== false">
            <template #body="slotProps">
              <slot :name="`item.${col.field}`" :item="slotProps.data">
                {{ slotProps.data[col.field] }}
              </slot>
            </template>
          </Column>

          <Column v-if="editable" header="">
            <template #body="slotProps">
              <div class="flex justify-center">
                <slot name="additional-actions" :item="slotProps.data" />
              </div>
            </template>
          </Column>

          <template #empty>
            <div class="flex justify-center items-center text-gray-500 py-8 h-[calc(100vh-450px)] w-full">
              {{ emptyStateText }}
            </div>
          </template>
        </DataTable>
      </div>
    </div>

    <ConfirmDialog />
  </div>
</template>

<style>
.p-datatable-paginator-bottom {
  border: none !important;
  box-shadow: none !important;
}
</style>
