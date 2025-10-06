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
const definedElements = new Set<string>()

const showAllWidgetContent = () => {
  const allWidgets = document.querySelectorAll('[data-widget-id]')
  allWidgets.forEach((widgetElement) => {
    const previewContent = widgetElement.querySelector('.widget-preview-content') as HTMLElement
    const loadingOverlay = widgetElement.querySelector('.widget-loading-overlay') as HTMLElement
    
    if (previewContent) {
      previewContent.style.opacity = '1'
      previewContent.style.transition = 'opacity 0.3s ease'
    }
    
    if (loadingOverlay) {
      loadingOverlay.style.opacity = '0'
      loadingOverlay.style.transition = 'opacity 0.3s ease'
      setTimeout(() => {
        loadingOverlay.style.display = 'none'
      }, 300)
    }
  })
}

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
    
    setTimeout(() => {
      savedWidgets.value.forEach((widget) => {
        executeWidgetHTML(widget)
      })
    }, 500)
    
    setTimeout(() => {
      savedWidgets.value.forEach((widget) => {
        const previewContainer = document.querySelector(`[data-widget-id="${widget.id}"] .widget-preview-content`)
        if (previewContainer && !previewContainer.querySelector('canvas, svg, [class*="chart"], [class*="apex"]')) {
          executeWidgetHTML(widget)
        }
      })
    }, 2000)
    
    setTimeout(() => {
      showAllWidgetContent()
    }, 5000)
    
  } catch (error) {
    console.error('Error loading widgets:', error)
  }
}

const executeWidgetHTML = (widget: DataInsightsWidget, targetContainer?: HTMLElement) => {
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
          createWidgetElement(widget, elementName, targetContainer)
        }
      }, 100)
      return
    }
    if (customElements.get(elementName) || definedElements.has(elementName)) {
      if (widget.id) {
        executedScripts.add(widget.id)
      }
      setTimeout(() => {
        createWidgetElement(widget, elementName, targetContainer)
      }, 100)
      return
    }
    eval(htmlContent)
    definedElements.add(elementName)
    if (widget.id) {
      executedScripts.add(widget.id)
    }
    setTimeout(() => {
      if (elementName && customElements.get(elementName)) {
        createWidgetElement(widget, elementName, targetContainer)
      }
    }, 500)
  } catch (error) {
    console.error('Error executing widget HTML:', error)
  }
}

const createWidgetElement = (widget: DataInsightsWidget, elementName: string, targetContainer?: HTMLElement) => {
  const previewContainer = targetContainer || document.querySelector(`[data-widget-id="${widget.id}"] .widget-preview-content`)
  
  if (previewContainer) {
    const element = document.createElement(elementName)
    previewContainer.innerHTML = ''
    previewContainer.appendChild(element)
    
    setTimeout(() => {
      if (element.shadowRoot) {
        const h3Element = element.shadowRoot.querySelector('h3')
        const pElement = element.shadowRoot.querySelector('p')
        
        let extractedTitle = ''
        let extractedSubtitle = ''
        
        if (h3Element) {
          extractedTitle = h3Element.textContent?.trim() || ''
        }
        if (pElement) {
          extractedSubtitle = pElement.textContent?.trim() || ''
        }
        
        if (extractedTitle || extractedSubtitle) {
          try {
            updateWidgetConfig(widget, { 
              aiTitle: extractedTitle, 
              aiSubtitle: extractedSubtitle 
            })
          } catch (error) {
            console.error('Error updating widget config:', error)
          }
        }
        
        const style = document.createElement('style')
        style.textContent = `
          h1, h2, h3, h4, h5, h6, p, span, label, title, desc, .title, .description { display: none !important; } 
          :host { 
            padding: 0 !important; 
            margin: 0 !important; 
            border: none !important; 
            box-shadow: none !important; 
            background: transparent !important;
            width: 100% !important;
            height: 100% !important;
            display: flex !important;
            align-items: center !important;
            justify-content: center !important;
          }
          canvas, svg {
            max-width: 100% !important;
            max-height: 100% !important;
            width: auto !important;
            height: auto !important;
            display: block !important;
            margin: auto !important;
          }
          .apexcharts-canvas, .apexcharts-svg, [class*="apex"] {
            margin: auto !important;
            display: block !important;
          }
          /* Show chart elements and their containers */
          canvas, svg, [class*="chart"], [class*="apex"], [class*="visualization"], div[class*="chart"], div[class*="apex"], .chart-container {
            display: block !important;
          }
        `
        element.shadowRoot.appendChild(style)
        
        const chartElements = element.shadowRoot.querySelectorAll('canvas, svg, [class*="chart"], [class*="apex"]')
        
        if (chartElements.length === 0) {
          const placeholder = previewContainer.querySelector('.chart-placeholder') as HTMLElement
          if (placeholder) {
            placeholder.style.display = 'block'
          }
        }
      } else {
        console.warn('No shadow root for widget')
      }
    }, 500)
  } else {
    console.warn('Preview container not found for widget')
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
            setTimeout(() => {
              addResizeIcons()
            }, 2000)
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

const addResizeIcons = () => {
  const resizeHandles = document.querySelectorAll('.grid-stack-item .ui-resizable-se')
  
  if (resizeHandles.length === 0) {
  }
  
  resizeHandles.forEach((handle) => {
    
    handle.innerHTML = ''
    
    const icon = document.createElement('i')
    icon.className = 'pi pi-arrow-up-right-and-arrow-down-left-from-center'
    icon.style.fontSize = '12px'
    icon.style.color = '#6b7280'
    
    handle.appendChild(icon)
    
  })
}

const setupResizeIconObserver = () => {
  const observer = new MutationObserver((mutations) => {
    mutations.forEach((mutation) => {
      if (mutation.type === 'childList') {
        mutation.addedNodes.forEach((node) => {
          if (node.nodeType === Node.ELEMENT_NODE) {
            const element = node as Element
            if (element.classList.contains('grid-stack-item') || element.querySelector('.grid-stack-item')) {
              setTimeout(() => {
                addResizeIcons()
              }, 100)
            }
          }
        })
      }
    })
  })
  
  const gridContainer = document.querySelector('.grid-stack')
  if (gridContainer) {
    observer.observe(gridContainer, { childList: true, subtree: true })
  }
}

const initGrid = () => {
  gridStack.value = GridStack.init({
    cellHeight: 80,
    column: 12,
    margin: 5,
    float: true,
    acceptWidgets: true,
    resizable: { handles: 'se' },
    draggable: { handle: '.grid-stack-item-content' }
  })
  
  setTimeout(() => {
    addResizeIcons()
  }, 500)
  
  setupResizeIconObserver()
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
        
        const dragImage = card.cloneNode(true) as HTMLElement
        dragImage.style.position = 'absolute'
        dragImage.style.top = '-1000px'
        dragImage.style.left = '-1000px'
        dragImage.style.width = '300px'
        dragImage.style.height = '200px'
        dragImage.style.transform = 'rotate(5deg)'
        dragImage.style.opacity = '0.8'
        dragImage.style.border = '2px solid #3b82f6'
        dragImage.style.borderRadius = '8px'
        dragImage.style.boxShadow = '0 10px 25px rgba(0, 0, 0, 0.2)'
        dragImage.style.zIndex = '1000'
        
        document.body.appendChild(dragImage)
        
        dragEvent.dataTransfer.setDragImage(dragImage, 150, 100)
        
        setTimeout(() => {
          if (document.body.contains(dragImage)) {
            document.body.removeChild(dragImage)
          }
        }, 100)
      }
    })
  })

  const gridEl = document.querySelector('.grid-stack')
  if (!gridEl) return

  gridEl.addEventListener('dragover', (e: Event) => {
    e.preventDefault()
    gridEl.classList.add('drag-over')
  })

  gridEl.addEventListener('dragleave', () => {
    gridEl.classList.remove('drag-over')
  })

  gridEl.addEventListener('drop', (e: Event) => {
    e.preventDefault()
    gridEl.classList.remove('drag-over')
    
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
      const col = Math.max(0, Math.min(Math.floor(x / cellWidth), 11))
      const row = Math.max(0, Math.floor(y / cellHeight))
      
      
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
    <div class="grid-stack-item-content bg-white rounded-lg border border-surface-200 h-full flex flex-col p-4">
      <!-- Title and Subtitle (Top) -->
      
      <!-- Chart/Diagram Area (Bottom) -->
      <div class="widget-chart-area bg-white p-2 flex-1">
        <div class="widget-body h-full" data-instance-id="${instanceId}"></div>
      </div>
      
      <!-- Remove Button -->
      <button class="remove-btn absolute top-2 right-2 text-gray-400 hover:text-red-500 text-sm">
        <i class="pi pi-trash"></i>
      </button>
    </div>
  `

  const removeBtn = el.querySelector('.remove-btn')
  if (removeBtn) {
    removeBtn.addEventListener('click', async () => {
      gridStack.value?.removeWidget(el)
      addedWidgetIds.delete(widgetInstanceId)
    })
  }
  const getWidgetSize = (widget: DataInsightsWidget) => {
    const config = JSON.parse(widget.config || '{}')
    const query = config.query || ''
    
    if (query.toLowerCase().includes('pie') || query.toLowerCase().includes('donut')) {
      return { w: 3, h: 3 }
    } else if (query.toLowerCase().includes('bar') || query.toLowerCase().includes('column')) {
      return { w: 4, h: 3 }
    } else if (query.toLowerCase().includes('line') || query.toLowerCase().includes('trend')) {
      return { w: 5, h: 3 }
    } else if (query.toLowerCase().includes('scatter') || query.toLowerCase().includes('heatmap')) {
      return { w: 4, h: 4 }
    } else {
      return { w: 4, h: 3 }
    }
  }
  
  const defaultSize = getWidgetSize(widget)
  const options: any = { 
    w: w || defaultSize.w, 
    h: h || defaultSize.h,
    minW: 1,
    maxW: 12,
    minH: 1,
    maxH: 12
  }
  if (x !== undefined) options.x = Math.max(0, Math.min(x, 11))
  if (y !== undefined) options.y = Math.max(0, y)
  
  gridStack.value.makeWidget(el, options)
  addedWidgetIds.add(widgetInstanceId)
  
  const resizeObserver = new ResizeObserver((entries) => {
    entries.forEach((entry) => {
      const widgetBody = entry.target.querySelector('.widget-body')
      if (widgetBody) {
        setTimeout(() => {
          const widgetId = entry.target.getAttribute('data-widget-id')
          if (widgetId) {
            const widget = savedWidgets.value.find(w => w.id === widgetId)
            if (widget) {
              
              const config = JSON.parse(widget.config || '{}')
              const htmlContent = config.originalRawHtml || widget.src
              
              if (htmlContent && htmlContent.includes('customElements.define')) {
                const elementNameMatch = htmlContent.match(/customElements\.define\(['"`]([^'"`]+)['"`]/)
                const elementName = elementNameMatch ? elementNameMatch[1] : null
                
                if (elementName && customElements.get(elementName)) {
                  widgetBody.innerHTML = ''
                  const element = document.createElement(elementName)
                  widgetBody.appendChild(element)
                  
                  setTimeout(() => {
                    if (element.shadowRoot) {
                      const style = document.createElement('style')
                      style.textContent = 'h1, h2, h3, h4, h5, h6, p, span, label, title, desc, .title, .description :host { padding: 0 !important; margin: 0 !important; border: none !important; box-shadow: none !important; } canvas, svg, [class*="chart"], [class*="apex"], [class*="visualization"], div[class*="chart"], div[class*="apex"], .chart-container { display: block !important; }'
                      element.shadowRoot.appendChild(style)
                      setTimeout(() => {
                        const chartElement = element.shadowRoot.querySelector('canvas, svg, [class*="chart"], [class*="apex"]') as any
                        if (chartElement) {
                          if (chartElement.__apexcharts) {
                            chartElement.__apexcharts.resize()
                          }
                          if (typeof chartElement.resize === 'function') {
                            chartElement.resize()
                          }
                        }
                      }, 100)
                      
                    }
                  }, 100)
                }
              }
            }
          }
        }, 100)
      }
    })
  })
  
  resizeObserver.observe(el)
  
  setTimeout(() => {
    addResizeIcons()
  }, 300)
  
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
              const h3Element = element.shadowRoot.querySelector('h3')
              const pElement = element.shadowRoot.querySelector('p')
              
              let extractedTitle = ''
              let extractedSubtitle = ''
              
              if (h3Element) {
                extractedTitle = h3Element.textContent?.trim() || ''
              }
              if (pElement) {
                extractedSubtitle = pElement.textContent?.trim() || ''
              }
              
                if (extractedTitle || extractedSubtitle) {
                  try {
                    updateWidgetConfig(widget, { 
                      aiTitle: extractedTitle, 
                      aiSubtitle: extractedSubtitle 
                    })
                } catch (error) {
                  console.error('Grid: Error updating widget config:', error)
                }
              }
              
              const style = document.createElement('style')
              style.textContent = 'h1, h2, h3, h4, h5, h6, p, span, label, title, desc, .title, .description :host { padding: 0 !important; margin: 0 !important; border: none !important; box-shadow: none !important; } canvas, svg, [class*="chart"], [class*="apex"], [class*="visualization"], div[class*="chart"], div[class*="apex"], .chart-container { display: block !important; }'
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

const updateWidgetConfig = (widget: DataInsightsWidget, updates: { aiTitle?: string, aiSubtitle?: string }) => {
  try {
    const config = JSON.parse(widget.config || '{}')
    let configUpdated = false
    
    if (updates.aiTitle && !config.aiTitle) {
      config.aiTitle = updates.aiTitle
      configUpdated = true
    }
    if (updates.aiSubtitle && !config.aiSubtitle) {
      config.aiSubtitle = updates.aiSubtitle
      configUpdated = true
    }
    
    if (configUpdated) {
      widget.config = JSON.stringify(config)
      return true
    }
    return false
  } catch (error) {
    return false
  }
}

const getWidgetTitle = (widget: DataInsightsWidget): string => {
  try {
    const config = JSON.parse(widget.config || '{}')
    const aiTitle = config.aiTitle
    if (aiTitle && aiTitle !== 'Test' && !aiTitle.includes('AI-generated') && aiTitle.length > 2) {
      return aiTitle
    }
    const widgetName = widget.name || ''
    if (widgetName && !widgetName.includes('AI-generated')) {
      return widgetName
    }
    
    return 'Data Insight'
  } catch {
    return 'Data Insight'
  }
}

const getWidgetSubtitle = (widget: DataInsightsWidget): string => {
  try {
    const config = JSON.parse(widget.config || '{}')
    const aiSubtitle = config.aiSubtitle
    if (aiSubtitle && !aiSubtitle.includes('AI-generated') && !aiSubtitle.includes('widget for:') && aiSubtitle.length > 5) {
      return aiSubtitle
    }
    const widgetDesc = widget.description || ''
    if (widgetDesc && !widgetDesc.includes('AI-generated')) {
      return widgetDesc
    }
    
    return 'Data visualization'
  } catch {
    return 'Data visualization'
  }
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
        <div v-else class="grid-stack flex-1 overflow-y-auto"></div>
      </div>
    </div>

    <div class="w-80 border-l border-surface-200 flex flex-col h-full overflow-y-auto">
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
              class="widget-card bg-white rounded-lg cursor-move border border-surface-200 overflow-hidden"
            >
              <div class="widget-chart-area bg-gray-50 p-2 relative">
                <div class="widget-loading-overlay absolute inset-0 bg-white bg-opacity-90 flex items-center justify-center z-10">
                  <div class="text-center">
                    <i class="pi pi-spin pi-spinner text-blue-500 text-lg mb-1"></i>
                    <div class="text-xs text-gray-600">Loading...</div>
                  </div>
                </div>
                
                <div class="widget-preview-content" :data-widget-id="widget.id">
                  <div class="chart-placeholder" style="display: none; color: #999; font-size: 12px; text-align: center;">
                    Loading chart...
                  </div>
                </div>
              </div>
              
              <div class="p-3">
                <div class="font-semibold text-sm text-gray-900 mb-1">{{ getWidgetTitle(widget) }}</div>
                <div class="text-xs text-gray-500 truncate">{{ getWidgetSubtitle(widget) }}</div>
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
  min-height: calc(100vh - 200px);
  transition: background-color 0.2s ease;
  position: relative;
}

.grid-stack:not(.drag-over) {
  min-height: calc(100vh - 200px);
}

.grid-stack.drag-over {
  background-color: rgba(59, 130, 246, 0.05);
  border: 2px dashed rgba(59, 130, 246, 0.3);
  border-radius: 8px;
  transition: all 0.2s ease;
}

.grid-stack.drag-over::before {
  content: 'Drop widget here';
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #6b7280;
  font-size: 14px;
  font-weight: 500;
  pointer-events: none;
  z-index: 1;
}

:deep(.grid-stack-item-content) {
  cursor: move;
}

:deep(.grid-stack-item .ui-resizable-handle) {
  display: none;
}

:deep(.grid-stack-item .ui-resizable-se) {
  display: block !important;
  position: absolute;
  bottom: 0;
  right: 0;
  width: 20px;
  height: 20px;
  background: #e5e7eb;
  border: 1px solid #9ca3af;
  cursor: se-resize;
  z-index: 10;
}

:deep(.grid-stack-item .ui-resizable-se) {
  display: block !important;
  position: absolute;
  bottom: 6px;
  right: 22px;
  width: 16px;
  height: 16px;
  background: transparent;
  transform: rotate(90deg);
  border: none;
  cursor: se-resize;
  z-index: 10;
  display: flex !important;
  align-items: center !important;
  justify-content: center !important;
}

:deep(.grid-stack-item .grid-stack-item-resize) {
  position: absolute;
  bottom: 2px;
  right: 8px;
  width: 16px;
  height: 16px;
  background: transparent;
  border: none;
  cursor: se-resize;
  z-index: 10;
  display: flex !important;
  align-items: center !important;
  justify-content: center !important;
}

:deep(.widget-body) {
  overflow: auto;
}

.widget-card {
  user-select: none;
  transition: transform 0.2s ease, opacity 0.2s ease;
}

.widget-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.widget-card:active {
  transform: scale(0.98);
  opacity: 0.8;
}

.widget-chart-area {
  height: 120px;
  overflow: hidden;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f8f9fa;
}

.widget-preview-content {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  min-height: 100px;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.widget-preview-content :deep(canvas),
.widget-preview-content :deep(svg) {
  max-width: 100% !important;
  max-height: 100% !important;
  width: auto !important;
  height: auto !important;
  display: block !important;
  margin: auto !important;
}

.widget-preview-content :deep(*) {
  display: block !important;
  margin: auto !important;
}

.widget-preview-content :deep(.apexcharts-canvas),
.widget-preview-content :deep(.apexcharts-svg),
.widget-preview-content :deep([class*="apex"]) {
  margin: auto !important;
  display: block !important;
}

.widget-preview-content :deep(*) {
  pointer-events: none;
}

.grid-stack-item .widget-chart-area {
  height: 100%;
  overflow: hidden;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f8f9fa;
}

.grid-stack-item .widget-body {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.grid-stack-item .widget-body :deep(canvas),
.grid-stack-item .widget-body :deep(svg) {
  max-width: 100% !important;
  max-height: 100% !important;
  width: auto !important;
  height: auto !important;
  display: block !important;
  margin: auto !important;
  transition: all 0.3s ease;
}

.grid-stack-item .widget-body :deep(.apexcharts-canvas),
.grid-stack-item .widget-body :deep(.apexcharts-svg),
.grid-stack-item .widget-body :deep([class*="apex"]) {
  margin: auto !important;
  display: block !important;
  width: 100% !important;
  height: 100% !important;
}

.grid-stack-item .widget-body :deep(.chart-container),
.grid-stack-item .widget-body :deep([class*="chart"]) {
  width: 100% !important;
  height: 100% !important;
  display: flex !important;
  align-items: center !important;
  justify-content: center !important;
}

.grid-stack-item .grid-stack-item-content {
  transition: all 0.3s ease;
}

.grid-stack-item[gs-w="2"] .grid-stack-item-content .font-semibold {
  font-size: 0.75rem;
}

.grid-stack-item[gs-w="3"] .grid-stack-item-content .font-semibold {
  font-size: 0.875rem;
}

.grid-stack-item[gs-w="4"] .grid-stack-item-content .font-semibold {
  font-size: 1rem;
}

.grid-stack-item[gs-w="5"] .grid-stack-item-content .font-semibold {
  font-size: 1.125rem;
}

.grid-stack-item[gs-w="6"] .grid-stack-item-content .font-semibold {
  font-size: 1.25rem;
}

.grid-stack-item .widget-body :deep(*) {
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

