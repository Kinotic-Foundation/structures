<template>
  <div class="application-settings p-4">
    <h1 class="text-2xl font-semibold mb-4">Application Settings</h1>

    <Tabs :value="activeTab" @update:value="(value: string | number) => activeTab = Number(value)">
      <TabList>
        <Tab :value="0">General</Tab>
        <Tab :value="1">Widgets</Tab>
      </TabList>
      <TabPanels>
        <TabPanel :value="0">
          <div v-show="activeTab === 0" class="pt-4">
            <div class="max-w-[400px] mx-auto">
              <form @submit.prevent="saveSettings" class="flex flex-col gap-4">
                <div>
                  <div class="mb-5">
                    <label class="block text-sm text-[#101010] mb-3 font-semibold">Name</label>
                    <InputText 
                      v-model="appName" 
                      type="text" 
                      class="w-full" 
                      disabled
                    />
                  </div>
                  <div class="mb-5">
                    <label class="block text-sm text-[#101010] mb-3 font-semibold">Description</label>
                    <Textarea 
                      v-model="appDescription" 
                      class="w-full h-[100px]"
                      rows="3" 
                    />
                  </div>
                  <div>
                    <label class="block text-sm text-[#101010] mb-3 font-semibold">API configuration</label>
                    <div class="border border-[#E6E7EB] rounded-2xl w-full divide-y divide-[#E6E7EB]">
                      <div class="flex items-center justify-between p-4">
                        <div class="flex items-center gap-2">
                          <img src="@/assets/graphql.svg" />
                          <span class="text-[#3F424D] text-sm font-normal">GraphQL</span>
                        </div>
                        <ToggleButton v-model="enableGraphQL" onLabel="On" offLabel="Off" />
                      </div>
                      <div class="flex items-center justify-between p-4">
                        <div class="flex items-center gap-2">
                          <img src="@/assets/scalar.svg" />
                          <span class="text-[#3F424D] text-sm font-normal">OpenAPI</span>
                        </div>
                        <ToggleButton v-model="enableOpenAPI" onLabel="On" offLabel="Off" />
                      </div>
                      <div class="flex items-center justify-between p-4">
                        <div class="flex items-center gap-2">
                          <img src="@/assets/mcp.svg" />
                          <span class="text-[#3F424D] text-sm font-normal">MCP (Model Context Protocol)</span>
                        </div>
                        <ToggleButton v-model="enableMCP" onLabel="On" offLabel="Off" />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="flex justify-end gap-2 mt-6">
                  <Button 
                    type="submit" 
                    :disabled="loading" 
                    severity="primary" 
                    label="Save Settings" 
                  />
                </div>
              </form>
            </div>
          </div>
        </TabPanel>
        <TabPanel :value="1">
          <div v-show="activeTab === 1" class="pt-4">
            <div class="text-center py-8 text-gray-500">
              <h3 class="text-lg font-medium mb-2">Widgets Configuration</h3>
              <p>Widget settings will be available here in the future.</p>
            </div>
          </div>
        </TabPanel>
      </TabPanels>
    </Tabs>
  </div>
</template>

<script setup lang="ts">
import { ref, defineProps, onMounted, watch } from 'vue'
import { InputText, Textarea, Button, ToggleButton, Tabs, TabList, Tab, TabPanels, TabPanel } from 'primevue'
import { APPLICATION_STATE } from '@/states/IApplicationState'
import { Structures } from '@kinotic/structures-api'
import { useToast } from 'primevue/usetoast'

defineProps({
  applicationId: {
    type: String,
    required: true
  }
})

const toast = useToast()
const appName = ref('')
const appDescription = ref('')
const enableGraphQL = ref(false)
const enableOpenAPI = ref(false)
const enableMCP = ref(false)
const loading = ref(false)
const activeTab = ref(0)

watch(() => APPLICATION_STATE.currentApplication, (newApp) => {
  if (newApp) {
    appName.value = newApp.id || ''
    appDescription.value = newApp.description || ''
    enableGraphQL.value = newApp.enableGraphQL || false
    enableOpenAPI.value = newApp.enableOpenAPI || false
    enableMCP.value = (newApp as any).enableMCP || false
  }
}, { immediate: true })

onMounted(() => {
  if (APPLICATION_STATE.currentApplication) {
    const app = APPLICATION_STATE.currentApplication
    appName.value = app.id || ''
    appDescription.value = app.description || ''
    enableGraphQL.value = app.enableGraphQL || false
    enableOpenAPI.value = app.enableOpenAPI || false
    enableMCP.value = (app as any).enableMCP || false
  }
})

const saveSettings = async () => {
  if (!APPLICATION_STATE.currentApplication) {
    toast.add({
      severity: 'error',
      summary: 'Error',
      detail: 'No application selected',
      life: 3000
    })
    return
  }

  loading.value = true
  try {
    const updatedApplication = {
      ...APPLICATION_STATE.currentApplication,
      description: appDescription.value,
      enableGraphQL: enableGraphQL.value,
      enableOpenAPI: enableOpenAPI.value,
      enableMCP: enableMCP.value
    }

    await Structures.getApplicationService().save(updatedApplication)
    
    APPLICATION_STATE.currentApplication = updatedApplication

    toast.add({
      severity: 'success',
      summary: 'Success',
      detail: 'Application settings saved successfully',
      life: 3000
    })
  } catch (error) {
    console.error('Failed to save application settings:', error)
    toast.add({
      severity: 'error',
      summary: 'Error',
      detail: 'Failed to save application settings',
      life: 3000
    })
  } finally {
    loading.value = false
  }
}
</script>
