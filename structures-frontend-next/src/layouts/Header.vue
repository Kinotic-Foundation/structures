<template>
  <div class="px-6 py-4 bg-[#101010] flex justify-between items-center sticky top-0 left-0 z-50">
    <div class="flex items-center gap-2 text-white relative">
      <RouterLink to="/applications" v-if="isApplicationDetail">
        <img src="@/assets/sidebar-logo.svg" class="h-8 w-8" />
      </RouterLink>
      <RouterLink to="/applications" v-else>
        <img src="@/assets/logo.svg" class="h-8" />
      </RouterLink>
      <template v-if="isApplicationDetail">
        <span class="text-[#AFB0B8] text-lg">/</span>
        <button @click="toggleDropdown" class="flex items-center gap-1 text-[#AFB0B8] font-medium text-sm hover:opacity-80">
          {{ currentAppName }}
          <svg class="w-4 h-4" fill="none" stroke="currentColor" stroke-width="2"
               viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" d="M19 9l-7 7-7-7"/>
          </svg>
        </button>

        <div v-if="dropdownOpen"
             class="absolute top-10 left-10 bg-white rounded shadow-lg w-48 z-50 overflow-hidden">
          <div v-for="app in allApplications" :key="app.id"
               @click="selectApp(app)"
               class="px-4 py-2 hover:bg-gray-100 cursor-pointer text-[#101010] text-sm">
            {{ app.id }}
          </div>
        </div>
      </template>
    </div>
    <RouterLink to="#">
      <img src="@/assets/avatar.png" class="h-8 w-8 rounded-full" />
    </RouterLink>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from 'vue-facing-decorator'
import { APPLICATION_STATE } from '@/states/IApplicationState'

@Component
export default class Header extends Vue {
  dropdownOpen = false
  isApplicationDetail = false

  get allApplications() {
    return APPLICATION_STATE.allApplications
  }

  get currentAppName() {
    return APPLICATION_STATE.currentApplication?.id || 'Application name'
  }

  mounted(): void {
    this.updateRouteState()
    this.loadApplicationsIfNeeded()
  }

  @Watch('$route.path', { immediate: true })
  onRouteChange(): void {
    this.updateRouteState()
    this.loadApplicationsIfNeeded()
  }

  updateRouteState() {
    this.isApplicationDetail = /^\/application\/[^/]+$/.test(this.$route.path)
  }

  loadApplicationsIfNeeded() {
    if (this.isApplicationDetail && APPLICATION_STATE.allApplications.length === 0) {
      APPLICATION_STATE.loadAllApplications()
    }
  }

  toggleDropdown() {
    this.dropdownOpen = !this.dropdownOpen
  }

  async selectApp(app: { id: string }) {
        try {
        const appId = app.id ?? ''
        APPLICATION_STATE.setCurrentApplication(app)
        this.$router.push(`/application/${encodeURIComponent(appId)}`)
        this.dropdownOpen = false
    } catch (e) {
        console.error('[NamespaceList] Failed to navigate to application:', e)
    }
  }
}
</script>
