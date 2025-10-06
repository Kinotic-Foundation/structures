<script lang="ts" setup>
import { computed, ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import { APPLICATION_STATE } from '@/states/IApplicationState'
import { DashboardEntityService } from '@/services/DashboardEntityService'
import { Dashboard } from '@/domain/Dashboard'
import CrudTable from '@/components/CrudTable.vue'
import Button from 'primevue/button'
import { useToast } from 'primevue/usetoast'
import { type IDataSource, type IterablePage, Pageable } from '@kinotic/continuum-client'

const router = useRouter()
const toast = useToast()

const props = defineProps<{ applicationId?: string }>()

const currentApplicationId = computed(() => {
  return props.applicationId || APPLICATION_STATE.currentApplication?.id || ''
})

const title = computed(() => `Dashboards${currentApplicationId.value ? ` – ${currentApplicationId.value}` : ''}`)

const dashboardService = new DashboardEntityService()
const crudTableRef = ref<any>(null)

const dashboardDataSource: IDataSource<Dashboard> = {
  async findAll(pageable: Pageable): Promise<IterablePage<Dashboard>> {
    
    if (!currentApplicationId.value) {
      const emptyResult = await dashboardService.findAll(pageable)
      return {
        ...emptyResult,
        content: [],
        totalElements: 0
      }
    }
    
    try {
      const allPageable = Pageable.create(0, 10000)
      const result = await dashboardService.findAll(allPageable)
      const filteredDashboards = (result.content || []).filter(dashboard => {
        const matches = dashboard.applicationId === currentApplicationId.value
        return matches
      })
      return {
        ...result,
        content: filteredDashboards,
        totalElements: filteredDashboards.length
      }
    } catch (error) {
      const emptyResult = await dashboardService.findAll(pageable)
      return {
        ...emptyResult,
        content: [],
        totalElements: 0
      }
    }
  },
  
  async search(searchText: string, pageable: Pageable): Promise<IterablePage<Dashboard>> {
    
    if (!currentApplicationId.value) {
      const emptyResult = await dashboardService.findAll(pageable)
      return {
        ...emptyResult,
        content: [],
        totalElements: 0
      }
    }
    
    try {
      const allPageable = Pageable.create(0, 10000)
      const result = await dashboardService.findAll(allPageable)
      
      const filteredDashboards = (result.content || []).filter(dashboard => {
        const matchesApp = dashboard.applicationId === currentApplicationId.value
        const matchesSearch = dashboard.name?.toLowerCase().includes(searchText.toLowerCase()) ||
                             dashboard.description?.toLowerCase().includes(searchText.toLowerCase())
        return matchesApp && matchesSearch
      })
      
      
      return {
        ...result,
        content: filteredDashboards,
        totalElements: filteredDashboards.length
      }
    } catch (error) {
      const emptyResult = await dashboardService.findAll(pageable)
      return {
        ...emptyResult,
        content: [],
        totalElements: 0
      }
    }
  }
}


const tableHeaders = [
  { field: 'name', header: 'Name', sortable: true },
  { field: 'description', header: 'Description', sortable: true },
  { field: 'created', header: 'Created', sortable: true },
  { field: 'updated', header: 'Updated', sortable: true },
  { field: 'actions', header: 'Actions', sortable: false }
]

const deleteDashboard = async (dashboard: Dashboard) => {
  if (!confirm(`Are you sure you want to delete the dashboard "${dashboard.name}"?`)) {
    return
  }

  try {
    await dashboardService.deleteById(dashboard.id!)
    toast.add({ severity: 'success', summary: 'Success', detail: 'Dashboard deleted successfully' })
    
    if (crudTableRef.value) {
      crudTableRef.value.refresh()
    }
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to delete dashboard' })
  }
}

const openDashboard = (dashboard: Dashboard) => {
  if (!dashboard.id) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Dashboard ID is missing' })
    return
  }
  
  if (!currentApplicationId.value) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Application ID is missing' })
    return
  }
  
  const route = `/application/${currentApplicationId.value}/dashboards/${dashboard.id}`
  router.push(route)
}

const createNewDashboard = () => {
  router.push(`/application/${currentApplicationId.value}/dashboards/new`)
}

const editDashboard = (dashboard: Dashboard) => {
  router.push(`/application/${currentApplicationId.value}/dashboards/${dashboard.id}/edit`)
}

watch(() => router.currentRoute.value.query.refresh, () => {
  if (crudTableRef.value) {
    crudTableRef.value.refresh()
  } else {
    console.log('CrudTable ref not available yet')
  }
})

</script>

<template>
  <div class="p-6 h-full">
    <div class="flex justify-between items-center mb-6">
      <h1 class="text-2xl font-semibold text-surface-950">{{ title }}</h1>
      <Button
        @click="createNewDashboard"
        icon="pi pi-plus"
        label="Create Dashboard"
        class="p-button-primary"
      />
    </div>
    
    <div class="h-[calc(100vh-140px)]">
      <CrudTable
        ref="crudTableRef"
        :dataSource="dashboardDataSource"
        :headers="tableHeaders"
        :defaultPageSize="50"
        @edit="editDashboard"
        @delete="deleteDashboard"
        @onRowClick="openDashboard"
        row-clickable
      >
        <template #item.created="{ item }">
          {{ item.created ? new Date(item.created).toLocaleDateString() : '-' }}
        </template>
        <template #item.updated="{ item }">
          {{ item.updated ? new Date(item.updated).toLocaleDateString() : '-' }}
        </template>
        <template #item.actions="{ item }">
          <div class="flex items-center gap-2">
            <Button
              @click.stop="editDashboard(item)"
              icon="pi pi-pencil"
              class="p-button-text p-button-sm"
              v-tooltip.top="'Edit Dashboard'"
            />
            <Button
              @click.stop="deleteDashboard(item)"
              icon="pi pi-trash"
              class="p-button-text p-button-sm p-button-danger"
              v-tooltip.top="'Delete Dashboard'"
            />
          </div>
        </template>
      </CrudTable>
    </div>

  </div>
</template>


