<script lang="ts">
import { Component, Vue } from 'vue-facing-decorator'
import SidebarItem from './SidebarItem.vue'

// ✅ Standard ES imports (works with Vite and Webpack 5+)
import overviewIcon from '@/assets/overview-icon.svg'
import authIcon from '@/assets/union.svg'
import settingsIcon from '@/assets/settings-gray.svg'
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

    // ✅ Class properties used in template
    overviewIcon = overviewIcon
    authIcon = authIcon
    settingsIcon = settingsIcon
    strCollapse = strCollapse
    strExpand = strExpand

    mounted() {
        const stored = localStorage.getItem(COLLAPSE_KEY)
        this.collapsed = stored === 'true'
    }

    toggleSidebar() {
        this.collapsed = !this.collapsed
        localStorage.setItem(COLLAPSE_KEY, String(this.collapsed))
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
                :icon="overviewIcon"
                label="Overview"
                :collapsed="collapsed"
            />
            <SidebarItem
                :icon="authIcon"
                label="Authentication"
                :collapsed="collapsed"
                textColor="text-[#797B80]"
            />
            <SidebarItem
                :icon="settingsIcon"
                label="Application settings"
                :collapsed="collapsed"
                textColor="text-[#797B80]"
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
