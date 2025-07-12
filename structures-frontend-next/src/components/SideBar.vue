<script setup lang="ts">
import { ref, defineExpose } from 'vue'
import SidebarItem from './SidebarItem.vue'
import overviewIcon from '@/assets/overview-icon.svg'
import authIcon from '@/assets/union.svg'
import settingsIcon from '@/assets/settings-gray.svg'
import strCollapse from '@/assets/str-collapse.svg'
import strExpand from '@/assets/str-expand.svg'

const collapsed = ref(false)

function toggleSidebar() {
    collapsed.value = !collapsed.value
}

defineExpose({
    collapsed,
    toggleSidebar
})
</script>

<template>
    <div
        :class="[
            'bg-[#F5F7FA] fixed top-[64px] left-0 z-40 flex flex-col justify-between px-4 py-4 h-[calc(100vh-64px)]',
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
