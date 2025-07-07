<template>
  <div
    class="px-6 py-4 bg-[#101010] flex justify-between items-center sticky top-0 left-0 z-50"
  >
    <div class="flex items-center gap-2 text-white relative">

      <!-- LOGO: Full or Compact depending on page -->
      <RouterLink to="/applications" class="flex items-center gap-2">
        <img
          v-if="!isApplicationDetailsPage && !isProjectStructuresPage"
          src="@/assets/logo.svg"
          class="h-8"
        />
        <img
          v-else
          src="@/assets/sidebar-logo.svg"
          class="h-8 w-8"
        />
      </RouterLink>

      <!-- Dropdowns (only on allowed pages) -->
      <template v-if="isApplicationDetailsPage || isProjectStructuresPage">
        <span class="text-[#AFB0B8] text-lg">/</span>

        <!-- Application Dropdown -->
        <div class="relative inline-block">
          <button
            @click="toggleAppDropdown"
            class="flex items-center gap-1 text-[#AFB0B8] font-medium text-sm hover:opacity-80"
          >
            {{ currentAppName }}
            <svg class="w-4 h-4" fill="none" stroke="currentColor" stroke-width="2"
              viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" d="M19 9l-7 7-7-7"/>
            </svg>
          </button>

          <div
            v-if="appDropdownOpen"
            class="absolute top-full left-0 mt-1 bg-white rounded shadow-lg w-48 z-50 overflow-hidden"
          >
            <div
              v-for="app in allApplications"
              :key="app.id"
              @click="selectApp(app)"
              class="px-4 py-2 hover:bg-gray-100 cursor-pointer text-[#101010] text-sm"
            >
              {{ app.id }}
            </div>
          </div>
        </div>

        <!-- Project Dropdown (only on Project Structures Page) -->
        <template v-if="isProjectStructuresPage && currentApp">
          <span class="text-[#AFB0B8] text-lg">/</span>
          <div class="relative inline-block">
            <button
              @click="toggleProjectDropdown"
              class="flex items-center gap-1 text-[#AFB0B8] font-medium text-sm hover:opacity-80"
            >
              {{ currentProjectName }}
              <svg class="w-4 h-4" fill="none" stroke="currentColor" stroke-width="2"
                viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" d="M19 9l-7 7-7-7"/>
              </svg>
            </button>

            <div
              v-if="projectDropdownOpen"
              class="absolute top-full left-0 mt-1 bg-white rounded shadow-lg w-48 z-50 overflow-hidden"
            >
              <div
                v-for="proj in projectsForCurrentApp"
                :key="proj.id"
                @click="selectProject(proj)"
                class="px-4 py-2 hover:bg-gray-100 cursor-pointer text-[#101010] text-sm"
              >
                {{ proj.name }}
              </div>
            </div>
          </div>
        </template>
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
import type { Application, Project } from '@kinotic/structures-api'
import { Structures } from '@kinotic/structures-api'

@Component
export default class Header extends Vue {
    appDropdownOpen = false
    projectDropdownOpen = false

    isApplicationDetailsPage = false
    isProjectStructuresPage = false

    projectsForCurrentApp: Project[] = []
    currentApp: Application | null = null
    currentProject: Project | null = null

    get allApplications() {
        return APPLICATION_STATE.allApplications
    }

    get currentAppName() {
        return this.currentApp?.id || 'Select Application'
    }

    get currentProjectName() {
        return this.currentProject?.name || 'Select Project'
    }

    mounted() {
        this.updateRouteState()
        this.loadApplicationsIfNeeded()
        this.tryAutoSelectAppAndProject()
    }

    @Watch('$route.fullPath', { immediate: true })
    onRouteChange() {
        this.updateRouteState()
        this.tryAutoSelectAppAndProject()
    }

    updateRouteState() {
        const path = this.$route.path
        this.isApplicationDetailsPage = /^\/application\/[^/]+$/.test(path)
        this.isProjectStructuresPage = /^\/application\/[^/]+\/project\/[^/]+\/structures$/.test(path)
    }

    loadApplicationsIfNeeded() {
        if (APPLICATION_STATE.allApplications.length === 0) {
            APPLICATION_STATE.loadAllApplications()
        }
    }

    toggleAppDropdown() {
        this.appDropdownOpen = !this.appDropdownOpen
        this.projectDropdownOpen = false
    }

    toggleProjectDropdown() {
        if (!this.currentApp) return
        this.projectDropdownOpen = !this.projectDropdownOpen
        this.appDropdownOpen = false

        if (this.projectsForCurrentApp.length === 0) {
            this.loadProjectsForCurrentApp()
        }
    }

    async loadProjectsForCurrentApp() {
        if (!this.currentApp) return
        try {
            const pageable = { pageNumber: 0, pageSize: 100 } as any
            const result = await Structures.getProjectService().findAllForApplication(this.currentApp.id, pageable)
            this.projectsForCurrentApp = result.content ?? []
            // this.trySetCurrentProjectFromRoute()
        } catch (e) {
            console.error('[Header] Failed to load projects:', e)
        }
    }

    selectApp(app: Application) {
        this.currentApp = app
        APPLICATION_STATE.currentApplication = app
        this.appDropdownOpen = false
        this.projectsForCurrentApp = []
        this.currentProject = null

        if (this.isApplicationDetailsPage) {
            this.$router.push(`/application/${encodeURIComponent(app.id)}`)
        }
        if (this.isProjectStructuresPage) {
            this.loadProjectsForCurrentApp()
        }
    }

    async setActiveAppById(applicationId: string) {
        const app = this.allApplications.find(a => a.id === applicationId);
        if (app) {
            this.currentApp = app;
            APPLICATION_STATE.currentApplication = app;
        } else {
            console.warn('[Header] Application not found:', applicationId, 'Retrying in 500ms');
            setTimeout(() => this.setActiveAppById(applicationId), 500);
        }
    }

    selectProject(proj: Project) {
        this.currentProject = proj
        const projectId = proj.id ?? ''
        const applicationId = this.currentApp?.id ?? ''
        this.$router.push(`/application/${encodeURIComponent(applicationId)}/project/${encodeURIComponent(projectId)}/structures`)
        this.projectDropdownOpen = false
    }

    async setActiveProjectById(applicationId: string, projectId: string) {
        const app = this.allApplications.find(a => a.id === applicationId)
        if (app) {
            this.currentApp = app
            APPLICATION_STATE.currentApplication = app

            const pageable = { pageNumber: 0, pageSize: 100 } as any
            const result = await Structures.getProjectService().findAllForApplication(applicationId, pageable)
            this.projectsForCurrentApp = result.content ?? []

            const proj = this.projectsForCurrentApp.find(p => p.id === projectId)
            if (proj) {
                this.currentProject = proj
            }
        }
    }

    async tryAutoSelectAppAndProject() {
        if (APPLICATION_STATE.allApplications.length === 0) {
            await APPLICATION_STATE.loadAllApplications();
        }

        if (this.isProjectStructuresPage) {
            const applicationId = this.$route.params.applicationId as string;
            const projectId = this.$route.params.projectId as string;
            await this.setActiveProjectById(applicationId, projectId);
        } else if (this.isApplicationDetailsPage) {
            const applicationId = this.$route.params.applicationId as string;
            await this.setActiveAppById(applicationId);
        }
    }
}
</script>
