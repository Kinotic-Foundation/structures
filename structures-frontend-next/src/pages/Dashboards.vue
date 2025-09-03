<script lang="ts" setup>
import { computed, watch, onMounted, nextTick } from 'vue'
import { APPLICATION_STATE } from '@/states/IApplicationState'
import { INSIGHTS_STATE } from '@/states/IInsightsState'

const props = defineProps<{ applicationId?: string }>()

const currentApplicationId = computed(() => {
  return props.applicationId || APPLICATION_STATE.currentApplication?.id || ''
})

const title = computed(() => `Dashboards${currentApplicationId.value ? ` – ${currentApplicationId.value}` : ''}`)

const insights = computed(() => {
  return INSIGHTS_STATE.getInsightsByApplication(currentApplicationId.value)
})

watch(() => currentApplicationId.value, (newAppId) => {
  console.log('Dashboard: Application changed to', newAppId)
}, { immediate: true })


function renderInsightContent(insight: any) {
  const containerId = `insight-content-${insight.id}`
  const container = document.getElementById(containerId)
  
  if (!container || !insight.htmlContent) {
    return
  }

  container.innerHTML = ''

  try {
    const script = document.createElement('script')
    script.textContent = insight.htmlContent
    document.head.appendChild(script)
    
    setTimeout(() => {
      const elementNameMatch = insight.htmlContent.match(/customElements\.define\(['"`]([^'"`]+)['"`]/)
      const elementName = elementNameMatch ? elementNameMatch[1] : 'ai-insight-component'
      
      if (customElements.get(elementName)) {
        const element = document.createElement(elementName)
        container.appendChild(element)
      } else {
        container.innerHTML = insight.htmlContent
      }
    }, 1000)
  } catch (error) {
    console.error('Error rendering insight content:', error)
    container.innerHTML = '<div class="text-red-500 text-sm">Error rendering content</div>'
  }
}

async function renderAllInsights() {
  await nextTick()
  insights.value.forEach(insight => {
    renderInsightContent(insight)
  })
}

watch(insights, () => {
  renderAllInsights()
}, { deep: true })

onMounted(() => {
  renderAllInsights()
})
</script>

<template>
  <div class="p-6 h-full">
    <div class="flex justify-between items-center mb-6">
      <h1 class="text-2xl font-semibold text-surface-950">{{ title }}</h1>
      <div class="flex items-center gap-2 text-sm text-surface-600">
        <i class="pi pi-info-circle"></i>
        <span>{{ insights.length }} AI-generated insight{{ insights.length !== 1 ? 's' : '' }}</span>
      </div>
    </div>
    
    <div class="h-[calc(100vh-140px)]">
      <!-- Insights Grid -->
      <div v-if="insights.length > 0" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 h-full overflow-auto">
        <div
          v-for="insight in insights"
          :key="insight.id"
          class="bg-white rounded-xl border border-[#E6E7EB] p-6 hover:shadow-lg transition-shadow duration-200"
        >
          <!-- AI Generated Content Only -->
          <div 
            :id="`insight-content-${insight.id}`" 
            class="insight-content w-full"
          >
            <!-- The actual AI-generated visualization will be rendered here -->
          </div>
        </div>
      </div>

      <!-- Empty State -->
      <div v-else class="h-full flex items-center justify-center">
        <div class="text-center text-surface-500">
          <i class="pi pi-chart-line text-6xl mb-4"></i>
          <h3 class="text-lg font-medium mb-2">No AI Insights Yet</h3>
          <p class="text-sm mb-4">Generate your first data insight using the Data Insights page</p>
          <div class="text-xs text-surface-400">
            Go to <strong>Data Insights</strong> and ask questions about your data to create visualizations
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


