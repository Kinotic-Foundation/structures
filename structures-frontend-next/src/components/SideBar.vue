<script lang="ts">
import { Component, Vue, Watch } from 'vue-facing-decorator'
import type { RouteLocationNormalizedLoaded } from 'vue-router'
import SidebarItem from './SidebarItem.vue'
import strCollapse from '@/assets/str-collapse.svg'
import strExpand from '@/assets/str-expand.svg'

const COLLAPSE_KEY = 'sidebar-collapsed'

@Component({
  components: {
    SidebarItem
  }
})
export default class Sidebar extends Vue {
  collapsed = false
  sidebarItems: Array<{ icon: string; label: string; path: string }> = []

  strCollapse = strCollapse
  strExpand = strExpand

  mounted() {
    const stored = localStorage.getItem(COLLAPSE_KEY)
    this.collapsed = stored === 'true'

    this.generateSidebarItems()
  }

  @Watch('$route')
  onRouteChange() {
    this.generateSidebarItems()
  }

  get router() {
    return this.$router
  }

  get route(): RouteLocationNormalizedLoaded {
    return this.$route
  }

  toggleSidebar() {
    this.collapsed = !this.collapsed
    localStorage.setItem(COLLAPSE_KEY, String(this.collapsed))
  }

  navigateTo(path: string) {
    if (this.route.path !== path) {
      this.router.push(path)
    }
  }

  generateSidebarItems() {
    const matchedWithSidebar = this.route.matched.find(r => r.meta?.sidebarItems)

    if (matchedWithSidebar) {
      const sidebarItemsMeta = matchedWithSidebar.meta.sidebarItems
      this.sidebarItems =
        typeof sidebarItemsMeta === 'function'
          ? sidebarItemsMeta(this.route)
          : sidebarItemsMeta || []
    } else {
      const allRoutes = this.router.getRoutes()
      this.sidebarItems = allRoutes
        .filter(r => r.meta?.showInMainNav)
        .map(r => ({
          icon: r.meta.icon,
          label: r.meta.label,
          path: r.path
        })) as Array<{ icon: string; label: string; path: string }>
    }
  }
}
</script>

<template>
  <div
    :class="[
      'bg-surface-50 fixed top-[64px] left-0 z-40 flex flex-col justify-between px-4 py-4 h-[calc(100vh-64px)]',
      'transition-[width] duration-300 ease-in-out',
      collapsed ? 'w-[64px]' : 'w-[256px]'
    ]"
  >
    <div class="flex flex-col gap-2 w-full">
      <SidebarItem
        v-for="item in sidebarItems"
        :key="item.path"
        :icon="item.icon"
        :label="item.label"
        :collapsed="collapsed"
        @click="navigateTo(item.path)"
      />
    </div>

    <div
      @click="toggleSidebar"
      class="cursor-pointer p-2 hover:bg-gray-200 rounded-lg transition max-w-max"
    >
      <img
        :src="collapsed ? strExpand : strCollapse"
        alt="Toggle Sidebar"
        class="w-5 h-5 transition-transform duration-300"
        :class="collapsed ? 'rotate-180' : ''"
      />
    </div>
  </div>
</template>
