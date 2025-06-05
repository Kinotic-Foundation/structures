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
  @Prop({ default: '' }) title!: string
  @Prop({ default: '' }) subtitle!: string
  @Prop({ default: true }) isShowAddNew!: boolean
  @Prop({ default: true }) isShowDelete!: boolean
  @Prop({ default: '' }) initialSearch!: string
  @Prop({ default: '#f5f5f5' }) rowHoverColor!: string

  items: Identifiable<string>[] = []
  totalItems = 0
  loading = false
  finishedInitialLoad = false
  searchDebounceTimer: ReturnType<typeof setTimeout> | null = null

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
  addItem(): void {}

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
    <div class="flex flex-col mb-[37px] overflow-x-auto w-full">
      <span class="text-3xl font-semibold mb-4">
        {{ title }}
      </span>
      <span class="font-semibold text-[13px] text-[#9FA9B7]">
        {{ subtitle }}
      </span>
    </div>

    <Toolbar class="mb-4 !border-none">
      <template #start>
        <InputGroup>
          <InputGroupAddon>
            <i class="pi pi-search" />
          </InputGroupAddon>
          <InputText v-model="searchText" placeholder="Search" @keyup.enter="search" @input="onSearchChange" />
        </InputGroup>
        <Button icon="pi pi-filter" rounded outlined aria-label="Filter"
                class="!border-gray-300 !ml-4 !min-h-[33px] !w-min-[35px] !rounded-[8px] !text-gray-600 hover:!bg-gray-100" />
      </template>

      <template #end>
        <Button
          v-if="editable && !disableModifications && isShowAddNew"
          label="Add New"
          @click="addItem"
          severity="secondary"
          class="!bg-[#3651ED] !text-white hover:!bg-[#274bcc]" />
      </template>
    </Toolbar>

    <div class="mb-9">
      <DataTable
        :value="items"
        :rowsPerPageOptions="[5, 10, 20, 50]"
        :paginator="true"
        :rows="options.rows"
        :totalRecords="totalItems"
        :lazy="true"
        :loading="loading"
        :first="options.first"
        :sortField="options.sortField"
        :sortOrder="options.sortOrder"
        :scrollable="true"
        :removableSort="true"
        scrollHeight="flex"
        dataKey="id"
        @page="onPage"
        @sort="onSort"
        @row-click="onRowClick"
        sortMode="multiple"
      >
        <Column
          v-for="col in computedHeaders"
          :key="col.field"
          :field="col.field"
          :header="col.header"
          :sortable="col.sortable !== false"
        >
          <template #body="slotProps">
            <slot :name="`item.${col.field}`" :item="slotProps.data">
              {{ slotProps.data[col.field] }}
            </slot>
          </template>
        </Column>

        <Column v-if="editable" header="Actions" style="text-align: right">
          <template #body="slotProps">
            <div class="flex justify-center items-center">
              <slot name="additional-actions" :item="slotProps.data" />
              <span v-if="isShowDelete" class="text-[#D0D5DD] mx-5">|</span>
              <Button
                v-if="isShowDelete"
                icon="pi pi-trash"
                class="p-button-text p-button-sm !text-[#334155] !bg-white"
                severity="danger"
                @click.stop="deleteItem(slotProps.data)"
              />
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
::v-deep(.p-paginator) {
  justify-content: flex-end !important;
}
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
</style>
