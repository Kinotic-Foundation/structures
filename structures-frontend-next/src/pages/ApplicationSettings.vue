<template>
  <div class="application-settings p-4">
    <h1 class="text-2xl font-bold mb-4">Settings for Application {{ applicationId }}</h1>
    <p class="mb-4 text-gray-700">This is where you can manage settings for your application.</p>

    <form @submit.prevent="saveSettings" class="space-y-4">
      <div class="field">
        <label for="appName" class="block text-sm font-medium text-gray-700">Application Name</label>
        <InputText 
          v-model="appName" 
          id="appName" 
          placeholder="Enter name" 
          required
          class="p-inputtext w-full" 
        />
      </div>

      <Button 
        label="Save Settings" 
        icon="pi pi-save" 
        class="p-button-primary w-full" 
      />
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref, defineProps } from 'vue'
import { InputText, Button } from 'primevue'

defineProps({
  applicationId: {
    type: String,
    required: true
  }
})

const appName = ref('')

const saveSettings = () => {
  alert(`Settings saved for ${appName.value}`)
}
</script>

<style scoped>
.application-settings {
  max-width: 600px;
  margin: 0 auto;
}
</style>
