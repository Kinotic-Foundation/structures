<script lang="ts" setup>
import { ref, onMounted, computed, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { GridStack } from 'gridstack'
import 'gridstack/dist/gridstack.min.css'
import { DashboardEntityService } from '@/services/DashboardEntityService'
import { Dashboard } from '@/domain/Dashboard'
import { DataInsightsWidgetEntityService } from '@/services/DataInsightsWidgetEntityService'
import { DataInsightsWidget } from '@/domain/DataInsightsWidget'
import Button from 'primevue/button'
import InputText from 'primevue/inputtext'
import { useToast } from 'primevue/usetoast'

const router = useRouter()
const toast = useToast()

const props = defineProps<{ 
  applicationId: string
  dashboardId: string 
}>()

const dashboardService = new DashboardEntityService()
const widgetService = new DataInsightsWidgetEntityService()

const dashboard = ref<Dashboard | null>(null)
const dashboardTitle = ref('')
const loading = ref(true)
const gridStack = ref<GridStack | null>(null)
const savedWidgets = ref<DataInsightsWidget[]>([])
const widgetSearchText = ref('')
const addedWidgetIds = new Set<string>()
const executedScripts = new Set<string>()

const filteredWidgets = computed(() => {
  if (!widgetSearchText.value) return savedWidgets.value
  return savedWidgets.value.filter(w =>
    w.name.toLowerCase().includes(widgetSearchText.value.toLowerCase())
  )
})

const isNewDashboard = computed(() => {
  return props.dashboardId === 'new' || !dashboard.value?.id
})

const saveButtonLabel = computed(() => {
  return isNewDashboard.value ? 'Create' : 'Save'
})

const loadWidgets = async () => {
  try {
    savedWidgets.value = await widgetService.findByApplicationId(props.applicationId)
    
    await nextTick()
    
    // Execute widgets after DOM is ready
    setTimeout(() => {
      savedWidgets.value.forEach((widget) => {
        executeWidgetHTML(widget)
      })
    }, 500)
  } catch (error) {
    console.error('Error loading widgets:', error)
  }
}

const executeWidgetHTML = (widget: DataInsightsWidget) => {
  const config = JSON.parse(widget.config || '{}')
  const htmlContent = config.originalRawHtml || widget.src
  
  if (!htmlContent) return
  
  try {
    const elementNameMatch = htmlContent.match(/customElements\.define\(['"`]([^'"`]+)['"`]/)
    const elementName = elementNameMatch ? elementNameMatch[1] : null
    
    if (!elementName) return
    
    if (widget.id && executedScripts.has(widget.id)) {
      setTimeout(() => {
        if (elementName && customElements.get(elementName)) {
          createWidgetElement(widget, elementName)
        }
      }, 100)
      return
    }
    
    eval(htmlContent)
    if (widget.id) {
      executedScripts.add(widget.id)
    }
    setTimeout(() => {
      if (elementName && !customElements.get(elementName)) {
        return
      }
      
      if (elementName) {
        createWidgetElement(widget, elementName)
      }
    }, 500)
  } catch (error) {
    console.error('Error executing widget HTML:', error)
  }
}

const createWidgetElement = (widget: DataInsightsWidget, elementName: string) => {
  const previewContainer = document.querySelector(`[data-widget-id="${widget.id}"] .widget-preview-content`)
  
  if (previewContainer) {
    const element = document.createElement(elementName)
    previewContainer.innerHTML = ''
    previewContainer.appendChild(element)
    setTimeout(() => {
      if (element.shadowRoot) {
        const style = document.createElement('style')
        style.textContent = 'h3, p { display: none !important; } :host { padding: 0 !important; margin: 0 !important; border: none !important; box-shadow: none !important; background: transparent !important; }'
        element.shadowRoot.appendChild(style)
      }
    }, 500)
  } else {
    console.warn('Preview container not found for widget:', widget.id)
  }
}

const loadDashboard = async () => {
  try {
    loading.value = true
    if (props.dashboardId === 'new') {
      dashboard.value = new Dashboard()
      dashboard.value.applicationId = props.applicationId
      dashboard.value.name = ''
      dashboard.value.description = 'New dashboard'
      dashboard.value.layout = ''
      dashboardTitle.value = ''
      loading.value = false
      return
    }
    
    dashboard.value = await dashboardService.findById(props.dashboardId)
    dashboardTitle.value = dashboard.value?.name || 'Dashboard'
    
    if (dashboard.value?.layout) {
      try {
        const layoutData = JSON.parse(dashboard.value.layout)
        
        if (layoutData.widgets && layoutData.widgets.length > 0) {
          setTimeout(() => {
            
            layoutData.widgets.forEach((widgetData: any) => {
              const widget = savedWidgets.value.find(w => w.id === widgetData.widgetId)
              
              if (widget) {
                addWidgetToGrid(widget, widgetData.x, widgetData.y, widgetData.w, widgetData.h, widgetData.instanceId)
              } else {
              }
            })
          }, 1500)
        } else {
        }
      } catch (error) {
      }
    } else {
    }
  } catch (error) {
  } finally {
    loading.value = false
  }
}

const initGrid = () => {
  gridStack.value = GridStack.init({
    cellHeight: 80,
    column: 12,
    margin: 5,
    float: true,
    resizable: { handles: 'all' },
    draggable: { handle: '.grid-stack-item-content' }
  })
}

const setupDragDrop = () => {
  const widgets = document.querySelectorAll('.widget-card')
  
  widgets.forEach(card => {
    (card as HTMLElement).draggable = true
    
    card.addEventListener('dragstart', (e: Event) => {
      const dragEvent = e as DragEvent
      const widgetId = card.getAttribute('data-widget-id')
      if (dragEvent.dataTransfer && widgetId) {
        dragEvent.dataTransfer.setData('text/plain', widgetId)
      }
    })
  })

  const gridEl = document.querySelector('.grid-stack')
  if (!gridEl) return

  gridEl.addEventListener('dragover', (e: Event) => {
    e.preventDefault()
  })

  gridEl.addEventListener('drop', (e: Event) => {
    e.preventDefault()
    const dragEvent = e as DragEvent
    const widgetId = dragEvent.dataTransfer?.getData('text/plain')
    if (!widgetId) return

    const widget = savedWidgets.value.find(w => w.id === widgetId)
    if (widget) {
      const gridRect = gridEl.getBoundingClientRect()
      const x = dragEvent.clientX - gridRect.left
      const y = dragEvent.clientY - gridRect.top
      
      const cellHeight = 80 + 5
      const cellWidth = gridRect.width / 12
      const col = Math.floor(x / cellWidth)
      const row = Math.floor(y / cellHeight)
      
      addWidgetToGrid(widget, col, row)
    }
  })
}

const addWidgetToGrid = (widget: DataInsightsWidget, x?: number, y?: number, w?: number, h?: number, instanceId?: string) => {
  if (!gridStack.value || !widget.id) return
  const widgetInstanceId = instanceId || `${widget.id}-${Date.now()}`

  const config = JSON.parse(widget.config || '{}')
  const htmlContent = config.originalRawHtml || widget.src

  if (htmlContent && htmlContent.includes('customElements.define')) {
    const elementNameMatch = htmlContent.match(/customElements\.define\(['"`]([^'"`]+)['"`]/)
    const elementName = elementNameMatch ? elementNameMatch[1] : null
    
    if (elementName && !customElements.get(elementName) && !executedScripts.has(widget.id)) {
      try {
        eval(htmlContent)
        executedScripts.add(widget.id)
      } catch (error) {
      }
    }
  }

  const el = document.createElement('div')
  el.className = 'grid-stack-item'
  el.setAttribute('data-widget-id', widget.id)
  el.setAttribute('data-instance-id', widgetInstanceId)
  el.innerHTML = `
    <div class="grid-stack-item-content bg-white rounded-lg p-4 border border-surface-200 h-full flex flex-col">
      <div class="flex justify-between mb-2">
        <h3 class="font-semibold">${widget.name}</h3>
        <button class="remove-btn text-gray-400 hover:text-red-500">×</button>
      </div>
      <div class="widget-body flex-1" data-instance-id="${instanceId}"></div>
    </div>
  `

  const removeBtn = el.querySelector('.remove-btn')
  if (removeBtn) {
    removeBtn.addEventListener('click', async () => {
      gridStack.value?.removeWidget(el)
      addedWidgetIds.delete(widgetInstanceId)
    })
  }
  const options: any = { 
    w: w || 4, 
    h: h || 3 
  }
  if (x !== undefined) options.x = Math.max(0, Math.min(x, 8))
  if (y !== undefined) options.y = Math.max(0, y)
  
  gridStack.value.makeWidget(el, options)
  addedWidgetIds.add(widgetInstanceId)
  
  setTimeout(() => {
    const elementNameMatch = htmlContent?.match(/customElements\.define\(['"`]([^'"`]+)['"`]/)
    if (elementNameMatch) {
      const elementName = elementNameMatch[1]
      
      if (customElements.get(elementName)) {
        const widgetBody = el.querySelector('.widget-body')
        if (widgetBody) {
          const element = document.createElement(elementName)
          widgetBody.innerHTML = ''
          widgetBody.appendChild(element)
          
          setTimeout(() => {
            if (element.shadowRoot) {
              const style = document.createElement('style')
              style.textContent = 'h3, p { display: none !important; } :host { padding: 0 !important; margin: 0 !important; border: none !important; box-shadow: none !important; }'
              element.shadowRoot.appendChild(style)
            }
          }, 100)
        }
      }
    }
  }, 1000)
}

const createDashboard = async () => {
  if (!dashboardTitle.value || dashboardTitle.value.trim() === '') {
    toast.add({ severity: 'warn', summary: 'Warning', detail: 'Please enter a dashboard title' })
    return
  }
  
  try {
    const newDashboard: any = {
      id: null,
      name: dashboardTitle.value.trim(),
      description: 'Dashboard',
      applicationId: props.applicationId,
      layout: JSON.stringify({ widgets: [] }),
      created: new Date(),
      updated: new Date()
    }
    
    
    const savedDashboard = await dashboardService.save(newDashboard)
    
    toast.add({ severity: 'success', summary: 'Created', detail: `Dashboard "${savedDashboard.name}" created successfully` })
    
    setTimeout(() => {
      router.push({
        path: `/application/${props.applicationId}/dashboards`,
        query: { refresh: Date.now().toString() }
      })
    }, 500)
  } catch (error: any) {
    toast.add({ 
      severity: 'error', 
      summary: 'Error', 
      detail: error?.message || 'Failed to create dashboard' 
    })
  }
}

const updateDashboard = async () => {
  if (!dashboard.value || !gridStack.value) return
  
  try {
    const gridItems = document.querySelectorAll('.grid-stack .grid-stack-item')
    const widgetInstances: any[] = []
    gridItems.forEach(item => {
      const el = item as HTMLElement
      const instanceId = el.getAttribute('data-instance-id')
      const widgetId = el.getAttribute('data-widget-id')
      const x = parseInt(el.getAttribute('gs-x') || '0')
      const y = parseInt(el.getAttribute('gs-y') || '0')
      const w = parseInt(el.getAttribute('gs-w') || '4')
      const h = parseInt(el.getAttribute('gs-h') || '3')
      
      if (widgetId && instanceId) {
        widgetInstances.push({ instanceId, widgetId, x, y, w, h })
      }
    })
    
    dashboard.value.name = dashboardTitle.value
    dashboard.value.layout = JSON.stringify({ widgets: widgetInstances })
    dashboard.value.updated = new Date()
    
    const savedDashboard = await dashboardService.save(dashboard.value)
    console.log('✅ Dashboard updated successfully', savedDashboard)
    
    toast.add({ severity: 'success', summary: 'Updated', detail: 'Dashboard updated successfully' })
  } catch (error) {
    toast.add({ severity: 'error', summary: 'Error', detail: 'Failed to update dashboard' })
  }
}

const saveDashboard = async () => {
  if (isNewDashboard.value) {
    await createDashboard()
  } else {
    await updateDashboard()
  }
}

const goBack = () => {
  router.push(`/application/${props.applicationId}/dashboards`)
}

onMounted(async () => {
  await Promise.all([loadDashboard(), loadWidgets()])
  setTimeout(() => {
    initGrid()
    setTimeout(() => setupDragDrop(), 100)
  }, 200)
})
</script>

<template>
  <div class="h-full flex">
    <div class="flex-1 flex flex-col">
      <div class="flex justify-between items-center p-4 bg-white">
        <div class="flex items-center gap-4">
          <Button @click="goBack" icon="pi pi-arrow-left" class="p-button-text p-button-sm" />
          <InputText v-model="dashboardTitle" placeholder="Dashboard Title" class="text-xl font-semibold" />
        </div>
        <div class="flex gap-2">
          <Button @click="goBack" label="Cancel" class="p-button-outlined" />
          <Button @click="saveDashboard" :label="saveButtonLabel" class="p-button-primary" />
        </div>
      </div>

      <div class="flex-1 p-4 flex flex-col">
        <div v-if="loading" class="flex items-center justify-center flex-1">
          <i class="pi pi-spin pi-spinner text-3xl"></i>
        </div>
        <div v-else class="grid-stack flex-1"></div>
      </div>
    </div>

    <div class="w-80 border-l border-surface-200 flex flex-col h-full">
      <div class="p-4 bg-white flex-shrink-0">
        <h3 class="text-lg font-semibold mb-3">Widgets</h3>
        <InputText v-model="widgetSearchText" placeholder="Search..." class="w-full mb-3" />
      </div>
      <div class="flex-1 p-4 overflow-y-auto">
        <div class="space-y-2">
            <div
              v-for="widget in filteredWidgets"
              :key="widget.id || ''"
              :data-widget-id="widget.id || ''"
              class="widget-card bg-white p-3 rounded-lg cursor-move border border-surface-200"
            >
              <div class="font-medium text-sm mb-2">{{ widget.name }}</div>
              <div class="widget-preview rounded p-2 bg-gray-50 overflow-hidden">
                <div class="widget-preview-content" :data-widget-id="widget.id">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
</template>

<style scoped>
.grid-stack {
  min-height: 400px;
  height: 100%;
}

:deep(.grid-stack-item-content) {
  cursor: move;
}

:deep(.widget-body) {
  overflow: auto;
}

.widget-card {
  user-select: none;
}

.widget-preview {
  max-height: 120px;
  overflow: hidden;
  position: relative;
  min-height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.widget-preview-content {
  transform: scale(0.6);
  transform-origin: top left;
  width: 166%;
  height: 166%;
  min-height: 100px;
}

.widget-preview :deep(canvas),
.widget-preview :deep(svg) {
  max-width: 100% !important;
  height: auto !important;
}

.widget-preview :deep(*) {
  pointer-events: none;
}

:deep(.widget-html-content h3),
:deep(.widget-html-content p),
:deep(.widget-body h3),
:deep(.widget-body p) {
  display: none !important;
}

:deep(.widget-html-content .chart-container),
:deep(.widget-body .chart-container) {
  margin: 0 !important;
  padding: 0 !important;
}
</style>

