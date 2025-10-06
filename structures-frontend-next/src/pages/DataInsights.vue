<script lang="ts">
import { Component, Vue, Watch } from 'vue-facing-decorator'
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'
import Card from 'primevue/card'
import ScrollPanel from 'primevue/scrollpanel'
import Divider from 'primevue/divider'
import Calendar from 'primevue/calendar'
import { Structures, ProgressType } from '@kinotic/structures-api'
import type { InsightRequest, DataInsightsComponent, InsightProgress } from '@kinotic/structures-api'
import { APPLICATION_STATE } from '@/states/IApplicationState'
import { INSIGHTS_STATE, type InsightData } from '@/states/IInsightsState'
import { DataInsightsWidgetEntityService } from '@/services/DataInsightsWidgetEntityService'
import { DataInsightsWidget } from '@/domain/DataInsightsWidget'

interface ChatMessage {
  id: string
  type: 'user' | 'assistant'
  content: string
  timestamp: Date
  loading?: boolean
  tasks?: string[]
  isExpanded?: boolean
}

interface VisualizationComponent {
  id: string
  htmlContent: string
  createdAt: Date
  status: string
  supportsDateRangeFiltering?: boolean
}

interface DateRange {
  startDate: Date | null
  endDate: Date | null
}

class DateRangeObservable {
  private subscribers: Set<(dateRange: DateRange) => void> = new Set()
  private currentDateRange: DateRange = {
    startDate: null,
    endDate: null
  }

  subscribe(callback: (dateRange: DateRange) => void): () => void {
    this.subscribers.add(callback)
    callback(this.currentDateRange)
    return () => {
      this.subscribers.delete(callback)
    }
  }

  updateDateRange(dateRange: DateRange) {
    this.currentDateRange = dateRange
    this.subscribers.forEach(callback => callback(dateRange))
  }

  getCurrentDateRange(): DateRange {
    return { ...this.currentDateRange }
  }
}
declare global {
  interface Window {
    globalDateRangeObservable: DateRangeObservable
  }
}

@Component({
  components: {
    InputText,
    Button,
    Card,
    ScrollPanel,
    Divider,
    Calendar
  }
})
export default class DataInsights extends Vue {
  chatMessages: ChatMessage[] = []
  userInput: string = ''
  isLoading: boolean = false
  visualizations: VisualizationComponent[] = []
  currentApplicationId: string = ''
  dateRange: DateRange = {
    startDate: null,
    endDate: null
  }
  showDateRangePicker: boolean = false
  widgetService: DataInsightsWidgetEntityService = new DataInsightsWidgetEntityService()

  get currentApplicationName(): string {
    const appId = APPLICATION_STATE.currentApplication?.id
    console.log('DataInsights: Getting currentApplicationName:', appId)
    console.log('DataInsights: Full APPLICATION_STATE:', APPLICATION_STATE)
    console.log('DataInsights: APPLICATION_STATE.currentApplication:', APPLICATION_STATE.currentApplication)
    return appId || 'Unknown Application'
  }

  mounted() {
    if (!window.globalDateRangeObservable) {
      window.globalDateRangeObservable = new DateRangeObservable()
    }
    
    this.currentApplicationId = APPLICATION_STATE.currentApplication?.id || this.$route.params.applicationId as string || 'default'
    console.log('DataInsights: Mounted with application ID:', this.currentApplicationId)
    console.log('DataInsights: APPLICATION_STATE.currentApplication:', APPLICATION_STATE.currentApplication)
    
    const routeAppId = this.$route.params.applicationId as string
    console.log('DataInsights: Trying to set application from route:', routeAppId)
    console.log('DataInsights: All applications available:', APPLICATION_STATE.allApplications?.map(a => a.id))
    
    if (routeAppId && !APPLICATION_STATE.currentApplication) {
      const app = APPLICATION_STATE.allApplications?.find(a => a.id === routeAppId)
      if (app) {
        console.log('DataInsights: Found application in list, setting as current:', app.id)
        APPLICATION_STATE.currentApplication = app
      } else {
        console.log('DataInsights: Application not found in list, using route ID:', routeAppId)
        this.currentApplicationId = routeAppId
      }
    }
    
    (window as any).setCurrentApp = this.setCurrentApplication
    
    this.restoreStateFromStoredInsights()
  }

  setCurrentApplication(appId: string): void {
    console.log('DataInsights: Manually setting current application to:', appId)
    const app = APPLICATION_STATE.allApplications?.find(a => a.id === appId)
    if (app) {
      APPLICATION_STATE.currentApplication = app
      console.log('DataInsights: Successfully set current application:', app.id)
      this.currentApplicationId = app.id
      this.chatMessages = []
      this.visualizations = []
      const dashboardContainer = document.getElementById('dashboard-container')
      if (dashboardContainer) {
        dashboardContainer.innerHTML = ''
      }
      this.addWelcomeMessage()
    } else {
      console.log('DataInsights: Application not found:', appId)
    }
  }

  restoreStateFromStoredInsights(): void {
    console.log('DataInsights: Restoring state from stored insights')
    
    this.visualizations = []
    this.chatMessages = []
    
    const storedInsights = INSIGHTS_STATE.getInsightsByApplication(this.currentApplicationId)
    console.log('DataInsights: Found stored insights:', storedInsights.length)
    
    if (storedInsights.length === 0) {
      this.addWelcomeMessage()
      return
    }
    this.visualizations = storedInsights
      .filter(insight => insight.htmlContent)
      .map(insight => ({
        id: insight.id,
        htmlContent: insight.htmlContent!,
        createdAt: insight.createdAt,
        status: 'success',
        supportsDateRangeFiltering: false
      }))
    this.addWelcomeMessage()
    this.chatMessages.push({
      id: 'restored-summary',
      type: 'assistant',
      content: `Restored ${storedInsights.length} previous analysis${storedInsights.length > 1 ? 'es' : ''}. Your visualizations are available in the dashboard.`,
      timestamp: new Date(),
      loading: false,
      tasks: [],
      isExpanded: false
    })
    setTimeout(() => {
      this.visualizations.forEach(viz => {
        this.executeVisualization(viz.htmlContent)
      })
    }, 1000)

    console.log('DataInsights: State restored successfully')
  }

  addWelcomeMessage() {
    this.chatMessages.push({
      id: 'welcome',
      type: 'assistant',
      content: `Hello! I'm your data insights assistant. 

Ask me questions about your data in application "${this.currentApplicationName}" and I'll create visualizations for you. Try asking things like:

• Show me a summary of my data
• Create a chart showing trends over time
• Display the most important metrics

Components that support date filtering will automatically respond to the global date range picker.`,
      timestamp: new Date()
    })
  }

  @Watch('APPLICATION_STATE.currentApplication', { immediate: true, deep: true })
  onApplicationChange(newApp: any, oldApp: any) {
    console.log('DataInsights: Application change detected', { newApp, oldApp })
    console.log('DataInsights: Current APPLICATION_STATE:', APPLICATION_STATE.currentApplication)
    console.log('DataInsights: New app ID:', newApp?.id)
    console.log('DataInsights: Old app ID:', oldApp?.id)
    
    if (APPLICATION_STATE.currentApplication) {
      console.log('DataInsights: Updating to new application:', APPLICATION_STATE.currentApplication.id)
      this.currentApplicationId = APPLICATION_STATE.currentApplication.id
      this.chatMessages = []
      this.visualizations = []
      const dashboardContainer = document.getElementById('dashboard-container')
      if (dashboardContainer) {
        dashboardContainer.innerHTML = ''
      }
      this.restoreStateFromStoredInsights()
    } else {
      console.log('DataInsights: No current application set')
    }
  }
  @Watch('currentApplicationName', { immediate: true })
  onApplicationNameChange(newName: string, oldName: string) {
    console.log('DataInsights: Application name changed from', oldName, 'to', newName)
    if (newName !== 'Unknown Application' && newName !== oldName) {
      console.log('DataInsights: Application name changed, updating component')
      this.currentApplicationId = APPLICATION_STATE.currentApplication?.id || ''
      this.chatMessages = []
      this.visualizations = []
      const dashboardContainer = document.getElementById('dashboard-container')
      if (dashboardContainer) {
        dashboardContainer.innerHTML = ''
      }
      
      this.addWelcomeMessage()
    }
  }

  updateDateRange() {
    console.log('Date range updated:', this.dateRange)
    window.globalDateRangeObservable.updateDateRange(this.dateRange)
  }

  toggleDateRangePicker() {
    this.showDateRangePicker = !this.showDateRangePicker
  }

  async sendMessage() {
    if (!this.userInput.trim() || this.isLoading) return

    const userMessage: ChatMessage = {
      id: Date.now().toString(),
      type: 'user',
      content: this.userInput,
      timestamp: new Date()
    }

    this.chatMessages.push(userMessage)
    const userQuery = this.userInput
    this.userInput = ''
    this.isLoading = true

    const loadingMessage: ChatMessage = {
      id: (Date.now() + 1).toString(),
      type: 'assistant',
      content: 'Starting analysis...',
      timestamp: new Date(),
      loading: true,
      tasks: []
    }
    this.chatMessages.push(loadingMessage)

    try {
      const insightsService = Structures.getDataInsightsService()
      
      const request: InsightRequest = {
        query: userQuery,
        applicationId: this.currentApplicationId,
        focusStructureId: undefined,
        preferredVisualization: undefined,
        additionalContext: undefined
      }

      const progressObservable = insightsService.processRequest(request)
      
      progressObservable.subscribe({
        next: (progress: InsightProgress) => {
          const loadingMessage = this.chatMessages.find(msg => msg.loading)
          if (loadingMessage) {
            if (progress.message && !loadingMessage.tasks?.includes(progress.message)) {
              if (!loadingMessage.tasks) {
                loadingMessage.tasks = []
              }
              loadingMessage.tasks.push(progress.message)
            }
            loadingMessage.content = progress.message
          }
          
          if (progress.type === ProgressType.COMPONENTS_READY && progress.components && progress.components.length > 0) {
            progress.components.forEach(async (component: DataInsightsComponent) => {
              this.visualizations.push({
                id: component.id,
                htmlContent: component.rawHtml,
                createdAt: new Date(component.modifiedAt),
                status: 'success',
                supportsDateRangeFiltering: component.supportsDateRangeFiltering || false
              })

              const insightData: InsightData = {
                id: component.id,
                title: this.generateInsightTitle(userQuery),
                description: `AI-generated insight for: "${userQuery}"`,
                query: userQuery,
                applicationId: this.currentApplicationId,
                createdAt: new Date(component.modifiedAt),
                data: component,
                visualizationType: this.detectVisualizationType(component.rawHtml),
                htmlContent: component.rawHtml,
                metadata: {
                  tasks: loadingMessage?.tasks,
                  status: 'success'
                }
              }
              INSIGHTS_STATE.addInsight(insightData)

              // Save widget as entity for dashboard sidebar
              await this.saveWidgetAsEntity(component, userQuery)

              this.executeVisualization(component.rawHtml)
            })
          } else if (progress.type === ProgressType.COMPONENTS_READY) {
            console.warn('COMPONENTS_READY event received but no components found')
          }
          
          if (progress.type === ProgressType.COMPLETED) {
            const loadingMessage = this.chatMessages.find(msg => msg.loading)
            if (loadingMessage) {
              loadingMessage.loading = false
              loadingMessage.content = 'Analysis completed! Your visualizations have been added to the dashboard.'
              loadingMessage.isExpanded = false 
            }
          }
          
          if (progress.type === ProgressType.ERROR) {
            const loadingMessage = this.chatMessages.find(msg => msg.loading)
            if (loadingMessage) {
              loadingMessage.loading = false
              loadingMessage.content = `Error: ${progress.errorMessage || 'An error occurred during analysis'}`
              loadingMessage.isExpanded = false
            }
          }
        },
        error: (error) => {
          console.error('Error in progress stream:', error)
          const loadingMessage = this.chatMessages.find(msg => msg.loading)
          if (loadingMessage) {
            loadingMessage.loading = false
            loadingMessage.content = `Error: ${error.message || 'An error occurred during analysis'}`
            loadingMessage.isExpanded = false
          }
        },
        complete: () => {
          this.isLoading = false
        }
      })
    } catch (error) {
      const loadingMessage = this.chatMessages.find(msg => msg.loading)
      if (loadingMessage) {
        loadingMessage.loading = false
        loadingMessage.content = `Sorry, I couldn't process your request. Please try again or rephrase your question.`
        loadingMessage.isExpanded = false
      }
      
      console.error('Data insights error:', error)
      this.isLoading = false
    }
  }

  executeVisualization(htmlContent: string) {
    try {
      console.log('Executing visualization with content length:', htmlContent.length)
      
      // Execute the web component code (it should define itself)
      const script = document.createElement('script')
      script.textContent = htmlContent
      document.head.appendChild(script)
      
      // The AI should generate code that defines a custom element
      // We'll try to find what element name was defined by looking for customElements.define
      const elementNameMatch = htmlContent.match(/customElements\.define\(['"`]([^'"`]+)['"`]/)
      const elementName = elementNameMatch ? elementNameMatch[1] : 'data-insights-dashboard'
      
      console.log('Extracted element name:', elementName)
      
      // Wait a bit for the script to execute and register the custom element
      // Increased timeout to allow ApexCharts to load
      setTimeout(() => {
        try {
          // Check if the custom element is defined
          if (!customElements.get(elementName)) {
            console.error('Custom element not defined:', elementName)
            return
          }
          
          // Create an instance of the defined web component
          const element = document.createElement(elementName)
          
          // Add to dashboard container
          const dashboardContainer = document.getElementById('dashboard-container')
          if (dashboardContainer) {
            dashboardContainer.appendChild(element)
            console.log('Added element to dashboard:', elementName)
            
            // Chart library availability is handled by each web component
            console.log('Web component added to dashboard:', elementName)
          } else {
            console.error('Dashboard container not found')
          }
        } catch (error) {
          console.error('Error creating element:', error)
        }
      }, 1000)
    } catch (error) {
      console.error('Error executing visualization:', error)
    }
  }

  onKeyPress(event: KeyboardEvent) {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault()
      this.sendMessage()
    }
  }

  generateInsightTitle(query: string): string {
    // Generate a meaningful title from the user query
    const words = query.toLowerCase().split(' ')
    const keyWords = words.filter(word => 
      !['show', 'me', 'create', 'display', 'generate', 'make', 'a', 'an', 'the', 'of', 'for', 'with', 'in', 'on', 'at', 'to', 'from'].includes(word)
    )
    
    if (keyWords.length > 0) {
      return keyWords.slice(0, 3).map(word => 
        word.charAt(0).toUpperCase() + word.slice(1)
      ).join(' ')
    }
    
    return 'Data Insight'
  }

  detectVisualizationType(htmlContent: string): 'chart' | 'table' | 'stat' | 'list' {
    const content = htmlContent.toLowerCase()
    
    if (content.includes('chart') || content.includes('apexchart') || content.includes('canvas')) {
      return 'chart'
    } else if (content.includes('table') || content.includes('<table>') || content.includes('thead')) {
      return 'table'
    } else if (content.includes('stat') || content.includes('metric') || content.includes('number')) {
      return 'stat'
    } else {
      return 'list'
    }
  }

  async saveWidgetAsEntity(component: DataInsightsComponent, userQuery: string): Promise<void> {
    try {
      // First, execute the JavaScript code to create the custom element
      const script = document.createElement('script')
      script.textContent = component.rawHtml
      document.head.appendChild(script)
      
      // Extract the custom element name
      const elementNameMatch = component.rawHtml.match(/customElements\.define\(['"`]([^'"`]+)['"`]/)
      const elementName = elementNameMatch ? elementNameMatch[1] : 'data-insights-dashboard'
      
      // Wait for the custom element to be defined
      await new Promise(resolve => setTimeout(resolve, 1000))
      
      // Create a temporary instance to get the rendered HTML and extract title/subtitle
      let renderedHTML = ''
      let aiTitle = ''
      let aiSubtitle = ''
      
      try {
        if (customElements.get(elementName)) {
          const tempElement = document.createElement(elementName)
          // Add to a temporary container
          const tempContainer = document.createElement('div')
          tempContainer.style.position = 'absolute'
          tempContainer.style.left = '-9999px'
          tempContainer.style.top = '-9999px'
          document.body.appendChild(tempContainer)
          tempContainer.appendChild(tempElement)
          
          // Wait for the element to render
          await new Promise(resolve => setTimeout(resolve, 500))
          
          // Extract title and subtitle from the rendered element
          const h3Element = tempElement.shadowRoot?.querySelector('h3')
          const pElement = tempElement.shadowRoot?.querySelector('p')
          
          if (h3Element) {
            aiTitle = h3Element.textContent?.trim() || ''
          }
          if (pElement) {
            aiSubtitle = pElement.textContent?.trim() || ''
          }
          
          // Get the rendered HTML (just the custom element tag)
          renderedHTML = tempElement.outerHTML
          console.log('AI Generated Title:', aiTitle)
          console.log('AI Generated Subtitle:', aiSubtitle)
          
          // Clean up
          document.body.removeChild(tempContainer)
        } else {
          console.warn('Custom element not defined, using element tag')
          renderedHTML = `<${elementName}></${elementName}>`
        }
      } catch (error) {
        console.warn('Error creating custom element, using element tag:', error)
        renderedHTML = `<${elementName}></${elementName}>`
      }
      
      // Clean up the script
      document.head.removeChild(script)
      
      const widget = new DataInsightsWidget()
      widget.applicationId = this.currentApplicationId
      widget.name = aiTitle || this.generateInsightTitle(userQuery)
      widget.description = aiSubtitle || `AI-generated widget for: "${userQuery}"`
      widget.src = renderedHTML // Use the rendered HTML instead of raw JavaScript
      widget.widgetType = this.detectVisualizationType(component.rawHtml)
      widget.config = JSON.stringify({
        query: userQuery,
        supportsDateRangeFiltering: component.supportsDateRangeFiltering || false,
        originalComponentId: component.id,
        originalRawHtml: component.rawHtml, // Keep the original JavaScript for reference
        aiTitle: aiTitle, // Store the AI-generated title
        aiSubtitle: aiSubtitle, // Store the AI-generated subtitle
        width: 4, // Default grid width
        height: 3  // Default grid height
      })
      widget.created = new Date()
      widget.updated = new Date()

      await this.widgetService.save(widget)
      console.log('Widget saved successfully:', widget.name)
    } catch (error) {
      console.error('Error saving widget:', error)
    }
  }

}
</script>

<template>
  <div class="flex h-full">
    <!-- Dashboard Panel (Left) -->
    <div class="w-2/3 flex flex-col">
      <!-- Header -->
      <div class="p-4 border-b border-surface-200 bg-surface-50 rounded-t-lg">
        <div class="flex justify-between items-center">
          <div>
            <h2 class="text-xl font-semibold text-surface-900">Visualization Dashboard</h2>
            <p class="text-sm text-surface-600 mt-1">
              {{ visualizations.length }} visualization{{ visualizations.length !== 1 ? 's' : '' }} created
            </p>
          </div>
          
          <!-- Global Date Range Picker -->
          <div class="flex items-center gap-2">image.png
            <Button
              @click="toggleDateRangePicker"
              :class="showDateRangePicker ? 'p-button-primary' : 'p-button-outlined'"
              icon="pi pi-calendar"
              size="small"
              :label="showDateRangePicker ? 'Hide Date Range' : 'Set Date Range'"
            />
            
            <div v-if="showDateRangePicker" class="flex items-center gap-2 bg-white p-2 rounded border">
              <div class="flex items-center gap-2">
                <label class="text-sm font-medium text-surface-700">From:</label>
                <Calendar
                  v-model="dateRange.startDate"
                  @date-select="updateDateRange"
                  placeholder="Start Date"
                  size="small"
                  showIcon
                />
              </div>
              
              <div class="flex items-center gap-2">
                <label class="text-sm font-medium text-surface-700">To:</label>
                <Calendar
                  v-model="dateRange.endDate"
                  @date-select="updateDateRange"
                  placeholder="End Date"
                  size="small"
                  showIcon
                />
              </div>
              
              <Button
                @click="dateRange = { startDate: null, endDate: null }; updateDateRange()"
                icon="pi pi-times"
                size="small"
                class="p-button-text"
                title="Clear date range"
              />
            </div>
          </div>
        </div>
      </div>

      <div class="flex-1 p-4 overflow-auto rounded-b-lg">
        <div v-if="visualizations.length === 0" class="flex items-center justify-center h-full">
          <div class="text-center text-surface-500">
            <i class="pi pi-chart-line text-4xl mb-4"></i>
            <p class="text-lg font-medium">No visualizations yet</p>
            <p class="text-sm">Start a conversation to create your first visualization</p>
          </div>
        </div>

        <div v-else id="dashboard-container" class="space-y-6">
        </div>
      </div>
    </div>

    <div class="w-1/3 border-l border-surface-200 flex flex-col">
      <div class="p-4 border-b border-surface-200 bg-surface-50 flex-shrink-0 rounded-t-lg">
        <h1 class="text-xl font-semibold text-surface-900">Data Insights Chat</h1>
        <p class="text-sm text-surface-600 mt-1">Ask questions about your data</p>
      </div>

      <div class="flex-1 p-4 overflow-y-auto min-h-0 rounded-b-lg">
        <div class="space-y-4">
          <div
            v-for="message in chatMessages"
            :key="message.id"
            class="flex"
            :class="message.type === 'user' ? 'justify-end' : 'justify-start'"
          >
            <Card
              class="max-w-xs"
              :class="message.type === 'user' ? 'bg-primary-50 border-primary-200' : 'bg-surface-50 border-surface-200'"
            >
              <template #content>
                <div class="text-sm">
                  <div class="whitespace-pre-wrap">{{ message.content }}</div>
                  
                  <div v-if="message.loading && message.tasks && message.tasks.length > 0" class="mt-3">
                    <div class="text-xs font-medium text-surface-700 mb-2">Progress:</div>
                    <ul class="space-y-1">
                      <li v-for="(task, index) in message.tasks" :key="task" class="text-xs text-surface-600 flex items-center">
                        <i v-if="index === message.tasks.length - 1" class="pi pi-spin pi-spinner mr-2 text-primary-500"></i>
                        <i v-else class="pi pi-check mr-2 text-green-500"></i>
                        {{ task }}
                      </li>
                    </ul>
                  </div>
                  <div v-if="!message.loading && message.tasks && message.tasks.length > 0" class="mt-3">
                    <button 
                      @click="message.isExpanded = !message.isExpanded"
                      class="text-xs text-primary-600 hover:text-primary-700 flex items-center"
                    >
                      <i :class="message.isExpanded ? 'pi pi-chevron-up' : 'pi pi-chevron-down'" class="mr-1"></i>
                      {{ message.isExpanded ? 'Hide' : 'Show' }} task history ({{ message.tasks.length }} steps)
                    </button>
                    <div v-if="message.isExpanded" class="mt-2">
                      <ul class="space-y-1">
                        <li v-for="task in message.tasks" :key="task" class="text-xs text-surface-600 flex items-center">
                          <i class="pi pi-check mr-2 text-green-500"></i>
                          {{ task }}
                        </li>
                      </ul>
                    </div>
                  </div>
                  
                  <div class="text-xs text-surface-500 mt-2">
                    {{ message.timestamp.toLocaleTimeString() }}
                  </div>
                </div>
              </template>
            </Card>
          </div>
        </div>
      </div>

      <div class="p-4 border-t border-surface-200 bg-surface-50 flex-shrink-0 rounded-b-lg">
        <div class="flex gap-2">
          <InputText
            v-model="userInput"
            placeholder="Ask about your data..."
            class="flex-1"
            :disabled="isLoading"
            @keypress="onKeyPress"
          />
          <Button
            @click="sendMessage"
            :loading="isLoading"
            :disabled="!userInput.trim() || isLoading"
            icon="pi pi-send"
            class="p-button-primary"
          />
        </div>
      </div>
    </div>
  </div>
</template> 