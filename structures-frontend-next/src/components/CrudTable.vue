<script lang="ts">
import { Vue, Prop, Emit, toNative, Component, Watch } from 'vue-facing-decorator'
import DataTable from 'primevue/datatable'
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

  @Prop({ required: true }) dataSource!: IDataSource<any>
  @Prop({ required: true }) headers!: any[]
  @Prop({ default: false }) multiSort!: boolean
  @Prop({ default: true }) mustSort!: boolean
  @Prop({ default: false }) singleExpand!: boolean
  @Prop({ default: false }) disableModifications!: boolean
  @Prop({ default: '' }) title!: string
  @Prop({ default: '' }) subtitle!: string
  @Prop({ default: true }) isShowAddNew!: boolean
  @Prop({ default: true }) isShowDelete!: boolean

  items: Identifiable<string>[] = []
  totalItems = 0
  loading = false
  finishedInitialLoad = false

  searchText: string | null = ''
  options = {
    page: 0,
    rows: 10,
    first: 0,
    sortField: '',
    sortOrder: 1
  }

  get editable(): boolean {
    return this.dataSource && DataSourceUtils.instanceOfEditableDataSource(this.dataSource)
  }

  get computedHeaders() {
    return this.headers
  }

  mounted() {
    this.find()
  }

  @Watch('dataSource', { immediate: true })
  onDataSourceChanged(newVal: IDataSource<any>) {
    if (newVal) {
      this.find()
    }
  }

  onPage(event: any) {
    this.options.page = event.page
    this.options.rows = event.rows
    this.options.first = event.first
    this.find()
  }

  onSort(event: any) {
    this.options.sortField = event.sortField
    this.options.sortOrder = event.sortOrder
    this.find()
  }

  onSearchChange() {
    this.options.page = 0
    this.options.first = 0
    this.find()
  }

  @Emit()
  addItem() { }

  @Emit()
  editItem(item: Identifiable<string>) {
    return { ...item }
  }

  async deleteItem(item: Identifiable<string>) {
  if (!item.id || !this.editable) return

  const confirmed = await (this.$refs.confirm as any).open(
    'Delete Item',
    'Are you sure you want to delete this item?',
    { width: 400, style: { maxWidth: '400px' } }
  )

  if (!confirmed) return

  try {
    const index = this.items.findIndex(i => i.id === item.id)
    await (this.dataSource as IEditableDataSource<any>).deleteById(item.id)

    this.items.splice(index, 1)
    this.totalItems--

    const totalPages = Math.ceil(this.totalItems / this.options.rows)
    if (this.options.page >= totalPages && this.options.page > 0) {
      this.options.page--
    }

    this.find()
  } catch (error: any) {
    this.displayAlert(error.message || 'Failed to delete item.')
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
        orders.push(new Order(
          this.options.sortField,
          this.options.sortOrder === -1 ? Direction.DESC : Direction.ASC
        ))
      }

      const pageable = Pageable.create(this.options.page, this.options.rows, { orders })

      let queryPromise: Promise<Page<any>>
      if (this.searchText) {
        queryPromise = this.dataSource.search(this.searchText, pageable)
      } else {
        queryPromise = this.dataSource.findAll(pageable)
      }

      queryPromise.then((page: Page<any>) => {
        this.loading = false
        this.totalItems = page.totalElements ?? 0
        this.items = page.content ?? []
        if (!this.finishedInitialLoad) {
          setTimeout(() => {
            this.finishedInitialLoad = true
          }, 500)
        }
      }).catch((error: any) => {
        this.loading = false
        this.displayAlert(error.message)
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
  <div>
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
        <Button v-if="editable && !disableModifications && isShowAddNew" label="Add New" @click="addItem" severity="secondary"
          class="!bg-[#3651ED] !text-white hover:!bg-[#274bcc]" />
      </template>
    </Toolbar>
    <div class="mb-9">

      <DataTable :rowsPerPageOptions="[5, 10, 20, 50]" :value="items" :paginator="true" :rows="options.rows" :totalRecords="totalItems" :lazy="true"
        :loading="loading" :first="options.first" :sortField="options.sortField" :sortOrder="options.sortOrder"
        :scrollable="true" scrollHeight="flex" @page="onPage" @sort="onSort" dataKey="id">
        <Column v-for="col in computedHeaders" :key="col.field" :field="col.field" :header="col.header"
          :sortable="col.sortable !== false" :style="col.style || ''" />

        <Column v-if="editable" header="Actions" style="text-align: right">
          <template #body="slotProps">
            <div class="flex justify-center items-center">
              <slot name="additional-actions" :item="slotProps.data" />
              <!-- <Button icon="pi pi-pencil" class="p-button-text p-button-sm mr-2" @click="editItem(slotProps.data)"
            v-if="!disableModifications" /> -->
              <span v-if="isShowDelete" class="text-[#D0D5DD] mx-5">|</span>
              <!-- <Button v-if="isShowDelete" icon="pi pi-trash" class="p-button-text p-button-sm !text-[#334155] !bg-white" severity="danger"
                @click="deleteItem(slotProps.data)" /> -->
                <Button
  v-if="isShowDelete"
  icon="pi pi-trash"
  class="p-button-text p-button-sm !text-[#334155] !bg-white"
  severity="danger"
  @click="deleteItem(slotProps.data)"
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
<style>
.p-paginator {
  justify-content: flex-end !important
}
.p-datatable-table-container {
      overflow: auto !important;
    border: 1px solid #E2E8F0 !important;
    border-radius: 26px !important;
    padding: 20px !important;
}
.p-datatable-paginator-bottom {
  border: none !important
}
</style>
