<script lang="ts">
import { Component, Vue } from 'vue-facing-decorator'
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'
import Card from 'primevue/card'
import ScrollPanel from 'primevue/scrollpanel'
import Divider from 'primevue/divider'
import { Structures, ProgressType } from '@kinotic/structures-api'
import type { InsightRequest, DataInsightsComponent, InsightProgress } from '@kinotic/structures-api'

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
}

@Component({
  components: {
    InputText,
    Button,
    Card,
    ScrollPanel,
    Divider
  }
})
export default class DataInsights extends Vue {
  chatMessages: ChatMessage[] = []
  userInput: string = ''
  isLoading: boolean = false
  visualizations: VisualizationComponent[] = []
  currentApplicationId: string = ''

  created() {
    // Get application ID from route params or default
    this.currentApplicationId = this.$route.params.applicationId as string || 'default'
    
    // Add welcome message
    this.chatMessages.push({
      id: 'welcome',
      type: 'assistant',
      content: `Hello! I'm your data insights assistant. Ask me questions about your data in application "${this.currentApplicationId}" and I'll create visualizations for you. Try asking things like:

• "Show me a summary of my data"
• "Create a chart showing trends over time"
• "Display the most important metrics"`,
      timestamp: new Date()
    })
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

    // Add loading message with task list
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
      // Call the DataInsightsService via RPC with progress updates
      const insightsService = Structures.getDataInsightsService()
      
      const request: InsightRequest = {
        query: userQuery,
        applicationId: this.currentApplicationId,
        focusStructureId: undefined, // Let AI discover relevant structures
        preferredVisualization: undefined,
        additionalContext: undefined
      }

      // Subscribe to progress updates
      const progressObservable = insightsService.processRequest(request)
      
      progressObservable.subscribe({
        next: (progress: InsightProgress) => {
          // Update loading message with progress
          const loadingMessage = this.chatMessages.find(msg => msg.loading)
          if (loadingMessage) {
            // Add task to the list if it's not already there
            if (progress.message && !loadingMessage.tasks?.includes(progress.message)) {
              if (!loadingMessage.tasks) {
                loadingMessage.tasks = []
              }
              loadingMessage.tasks.push(progress.message)
            }
            // Update the message content to show the current active task
            loadingMessage.content = progress.message
          }
          
          // Handle components ready events
          if (progress.type === ProgressType.COMPONENTS_READY && progress.components) {
            // Add each component to dashboard
            progress.components.forEach((component: DataInsightsComponent) => {
              this.visualizations.push({
                id: component.id,
                htmlContent: component.rawHtml,
                createdAt: new Date(component.modifiedAt),
                status: 'success'
              })

              // Execute the web component code
              this.executeVisualization(component.rawHtml)
            })
          }
          
          // Handle completion
          if (progress.type === ProgressType.COMPLETED) {
            const loadingMessage = this.chatMessages.find(msg => msg.loading)
            if (loadingMessage) {
              // Convert loading message to completed message with task history
              loadingMessage.loading = false
              loadingMessage.content = 'Analysis completed! Your visualizations have been added to the dashboard.'
              loadingMessage.isExpanded = false // Start collapsed
            }
          }
          
          // Handle errors
          if (progress.type === ProgressType.ERROR) {
            const loadingMessage = this.chatMessages.find(msg => msg.loading)
            if (loadingMessage) {
              // Convert loading message to error message with task history
              loadingMessage.loading = false
              loadingMessage.content = `Error: ${progress.errorMessage || 'An error occurred during analysis'}`
              loadingMessage.isExpanded = false // Start collapsed
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
      // Update loading message to error
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
      setTimeout(() => {
        try {
          // Create an instance of the defined web component
          const element = document.createElement(elementName)
          
          // Add to dashboard container
          const dashboardContainer = document.getElementById('dashboard-container')
          if (dashboardContainer) {
            dashboardContainer.appendChild(element)
            console.log('Added element to dashboard:', elementName)
          } else {
            console.error('Dashboard container not found')
          }
        } catch (error) {
          console.error('Error creating element:', error)
        }
      }, 100)
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


}
</script>

<template>
  <div class="flex h-full">
    <!-- Dashboard Panel (Left) -->
    <div class="w-2/3 flex flex-col">
      <!-- Header -->
      <div class="p-4 border-b border-surface-200 bg-surface-50 rounded-t-lg">
        <h2 class="text-xl font-semibold text-surface-900">Visualization Dashboard</h2>
        <p class="text-sm text-surface-600 mt-1">
          {{ visualizations.length }} visualization{{ visualizations.length !== 1 ? 's' : '' }} created
        </p>
      </div>

      <!-- Dashboard Content -->
      <div class="flex-1 p-4 overflow-auto rounded-b-lg">
        <div v-if="visualizations.length === 0" class="flex items-center justify-center h-full">
          <div class="text-center text-surface-500">
            <i class="pi pi-chart-line text-4xl mb-4"></i>
            <p class="text-lg font-medium">No visualizations yet</p>
            <p class="text-sm">Start a conversation to create your first visualization</p>
          </div>
        </div>

        <div v-else id="dashboard-container" class="space-y-6">
          <!-- Visualizations will be dynamically inserted here -->
        </div>
      </div>
    </div>

    <!-- Chat Panel (Right) -->
    <div class="w-1/3 border-l border-surface-200 flex flex-col">
      <!-- Header -->
      <div class="p-4 border-b border-surface-200 bg-surface-50 flex-shrink-0 rounded-t-lg">
        <h1 class="text-xl font-semibold text-surface-900">Data Insights Chat</h1>
        <p class="text-sm text-surface-600 mt-1">Ask questions about your data</p>
      </div>

      <!-- Messages -->
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
                  
                  <!-- Task list for loading messages -->
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
                  
                  <!-- Expandable task history for completed messages -->
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

      <!-- Input -->
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