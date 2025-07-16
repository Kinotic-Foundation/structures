<template>
  <div ref="headerRef" class="px-6 py-4 bg-surface-950 flex justify-between items-center sticky top-0 left-0 z-50">
    <div class="flex items-center gap-2 text-white relative">
      <RouterLink to="/applications" class="flex items-center gap-2">
        <img v-if="!isApplicationDetailsPage && !isProjectStructuresPage" src="@/assets/logo.svg" class="h-8" />
        <img v-else src="@/assets/sidebar-logo.svg" class="h-8 w-8" />
      </RouterLink>

      <template v-if="isApplicationDetailsPage || isProjectStructuresPage">
        <span class="text-surface-400 text-lg">/</span>

        <div ref="appDropdownRef" class="relative inline-block w-48">
          <button @click="toggleAppDropdown"
            class="flex items-center gap-1 text-surface-400 font-medium text-sm hover:opacity-80 w-full justify-between">
            {{ currentAppName }}
            <svg class="w-4 h-4" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" d="M19 9l-7 7-7-7" />
            </svg>
          </button>
          <div v-if="appDropdownOpen"
            class="absolute top-full left-0 mt-1 bg-white rounded shadow-lg w-64 max-h-80 overflow-y-auto z-50 p-2">
            <div class="w-full mb-2">
              <IconField class="w-full">
                <InputIcon class="pi pi-search" />
                <InputText v-model="searchTextApp" placeholder="Search applications" class="w-full" />
              </IconField>
            </div>
            <div v-for="app in filteredApplications" :key="app.id" @click="selectApp(app)"
              class="px-4 py-2 hover:bg-gray-100 cursor-pointer text-surface-950 text-sm rounded">
              {{ app.id }}
            </div>
          </div>
        </div>

        <template v-if="isProjectStructuresPage && currentApp">
          <span class="text-surface-400 text-lg">/</span>
          <div ref="projectDropdownRef" class="relative inline-block w-48">
            <button @click="toggleProjectDropdown"
              class="flex items-center gap-1 text-surface-400 font-medium text-sm hover:opacity-80 w-full justify-between">
              {{ currentProjectName }}
              <svg class="w-4 h-4" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" d="M19 9l-7 7-7-7" />
              </svg>
            </button>

            <div v-if="projectDropdownOpen"
              class="absolute top-full left-0 mt-1 bg-white rounded shadow-lg w-64 max-h-80 overflow-y-auto z-50 p-2">
              <div class="w-full mb-2">
                <IconField class="w-full">
                  <InputIcon class="pi pi-search" />
                  <InputText v-model="searchTextProject" placeholder="Search projects" class="w-full" />
                </IconField>
              </div>
              <div v-for="proj in filteredProjects" :key="proj.id ?? ''" @click="selectProject(proj)"
                class="px-4 py-2 hover:bg-gray-100 cursor-pointer text-surface-950 text-sm rounded">
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
import { Component, Vue, Watch } from 'vue-facing-decorator';
import { APPLICATION_STATE } from '@/states/IApplicationState';
import type { Application, Project } from '@kinotic/structures-api';
import { Structures } from '@kinotic/structures-api';
import InputText from 'primevue/inputtext';
import IconField from 'primevue/iconfield';
import InputIcon from 'primevue/inputicon';

@Component({
  components: {
    InputText,
    IconField,
    InputIcon
  }
})
export default class Header extends Vue {
  appDropdownOpen = false;
  projectDropdownOpen = false;

  searchTextApp = '';
  searchTextProject = '';

  isApplicationDetailsPage = false;
  isProjectStructuresPage = false;

  projectsForCurrentApp: Project[] = [];
  currentApp: Application | null = null;
  currentProject: Project | null = null;

  mounted() {
    this.updateRouteState();
    this.loadApplicationsIfNeeded();
    this.tryAutoSelectAppAndProject();
    document.addEventListener('click', this.handleClickOutside);
  }

  beforeUnmount() {
    document.removeEventListener('click', this.handleClickOutside);
  }

  get allApplications() {
    return APPLICATION_STATE.allApplications;
  }

  get filteredApplications() {
    return this.allApplications.filter(app =>
      app.id.toLowerCase().includes(this.searchTextApp.toLowerCase())
    );
  }

  get filteredProjects() {
    return this.projectsForCurrentApp.filter(proj =>
      proj.name.toLowerCase().includes(this.searchTextProject.toLowerCase())
    );
  }

  get currentAppName() {
    return this.currentApp?.id || 'Select Application';
  }

  get currentProjectName() {
    return this.currentProject?.name || 'Select Project';
  }

  @Watch('$route.fullPath', { immediate: true })
  onRouteChange() {
    this.updateRouteState();
    this.tryAutoSelectAppAndProject();
  }

  updateRouteState() {
    const path = this.$route.path;
    this.isApplicationDetailsPage = /^\/application\/[^/]+$/.test(path);
    this.isProjectStructuresPage = /^\/application\/[^/]+\/project\/[^/]+\/structures$/.test(path);
  }

  loadApplicationsIfNeeded() {
    if (APPLICATION_STATE.allApplications.length === 0) {
      APPLICATION_STATE.loadAllApplications();
    }
  }

  toggleAppDropdown() {
    this.appDropdownOpen = !this.appDropdownOpen;
    if (this.appDropdownOpen) this.projectDropdownOpen = false;
  }

  toggleProjectDropdown() {
    if (!this.currentApp) return;
    this.projectDropdownOpen = !this.projectDropdownOpen;
    if (this.projectDropdownOpen) this.appDropdownOpen = false;

    if (this.projectsForCurrentApp.length === 0) {
      this.loadProjectsForCurrentApp();
    }
  }

  async loadProjectsForCurrentApp() {
    if (!this.currentApp) return;
    try {
      const pageable = { pageNumber: 0, pageSize: 100 } as any;
      const result = await Structures.getProjectService().findAllForApplication(this.currentApp.id, pageable);
      this.projectsForCurrentApp = result.content ?? [];
    } catch (e) {
      console.error('[Header] Failed to load projects:', e);
    }
  }

  selectApp(app: Application) {
    const appId = encodeURIComponent(app.id);

    this.currentApp = app;
    APPLICATION_STATE.currentApplication = app;
    this.appDropdownOpen = false;
    this.projectsForCurrentApp = [];
    this.currentProject = null;
    this.searchTextApp = '';

    this.$router.push(`/application/${appId}`);
  }

  selectProject(proj: Project) {
    this.currentProject = proj;
    const projectId = proj.id ?? '';
    const applicationId = this.currentApp?.id ?? '';
    this.$router.push(`/application/${encodeURIComponent(applicationId)}/project/${encodeURIComponent(projectId)}/structures`);
    this.projectDropdownOpen = false;
    this.searchTextProject = '';
  }

  async setActiveAppById(applicationId: string) {
    const app = this.allApplications.find(a => a.id === applicationId);
    if (app) {
      this.currentApp = app;
      APPLICATION_STATE.currentApplication = app;
    } else {
      setTimeout(() => this.setActiveAppById(applicationId), 500);
    }
  }

  async setActiveProjectById(applicationId: string, projectId: string) {
    const app = this.allApplications.find(a => a.id === applicationId);
    if (app) {
      this.currentApp = app;
      APPLICATION_STATE.currentApplication = app;

      const pageable = { pageNumber: 0, pageSize: 100 } as any;
      const result = await Structures.getProjectService().findAllForApplication(applicationId, pageable);
      this.projectsForCurrentApp = result.content ?? [];

      const proj = this.projectsForCurrentApp.find(p => p.id === projectId);
      if (proj) {
        this.currentProject = proj;
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

  handleClickOutside(event: MouseEvent) {
    const appDropdownEl = this.$refs.appDropdownRef as HTMLElement;
    const projectDropdownEl = this.$refs.projectDropdownRef as HTMLElement;

    const clickedOutsideApp = appDropdownEl && !appDropdownEl.contains(event.target as Node);
    const clickedOutsideProject = projectDropdownEl && !projectDropdownEl.contains(event.target as Node);

    if (this.appDropdownOpen && clickedOutsideApp) {
      this.appDropdownOpen = false;
    }
    if (this.projectDropdownOpen && clickedOutsideProject) {
      this.projectDropdownOpen = false;
    }
  }
}
</script>
