<template>
    <div class="relative flex h-screen bg-surface-0 dark:bg-surface-950">
        <div id="app-sidebar" class="h-full hidden lg:block lg:static absolute left-0 top-0 z-50">
            <div class="w-[18rem] h-full flex flex-col" style="background-color: #101010">
                <a class="inline-flex items-center gap-3 px-6 py-4 cursor-pointer">
                    <svg class="fill-surface-0" width="28" height="29" viewBox="0 0 28 29" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path
                            fill-rule="evenodd"
                            clip-rule="evenodd"
                            d="M14 28.5C21.732 28.5 28 22.232 28 14.5C28 6.76802 21.732 0.5 14 0.5C6.26801 0.5 0 6.76802 0 14.5C0 22.232 6.26801 28.5 14 28.5ZM18.3675 7.02179C18.5801 6.26664 17.8473 5.82009 17.178 6.29691L7.83519 12.9527C7.10936 13.4698 7.22353 14.5 8.00669 14.5H10.4669V14.4809H15.2618L11.3549 15.8595L9.63251 21.9782C9.41992 22.7334 10.1527 23.1799 10.822 22.7031L20.1649 16.0473C20.8907 15.5302 20.7764 14.5 19.9934 14.5H16.2625L18.3675 7.02179Z"
                        />
                    </svg>
                    <span class="font-semibold text-surface-0">Structures</span>
                </a>
                <div class="w-[calc(100%-3rem)] mx-auto h-[1px] bg-surface-700" />
                <div class="p-6 flex-1">
                    <SideNav class="flex flex-col gap-2 overflow-hidden"
                             :nav-items="applicationState.mainNavItems"
                             v-model="applicationState.selectedNavItem"
                    >
                    </SideNav>
                </div>
                <div class="w-[calc(100%-3rem)] mx-auto h-[1px] bg-surface-700 px-6" />
                <div class="p-6 flex items-center gap-3 cursor-pointer">
                    <Avatar image="https://fqjltiegiezfetthbags.supabase.co/storage/v1/render/image/public/block.images/blocks/avatars/circle/avatar-f-1.png" size="large" shape="circle" class="!w-9 !h-9" />
                    <div>
                        <div class="text-sm font-semibold text-surface-0">Amy Elsner</div>
                        <span class="text-xs text-surface-400 leading-none">Description</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="flex-1 flex flex-col gap-6">
            <MainLayoutBreadcrumb separator-icon="pi pi-chevron-right" />
            <div class="flex-1 px-[33px]">
                <router-view />
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import SideNav from '@/components/SideNav.vue'
import {StructuresStates} from '@/states/index.js'
import Avatar from 'primevue/avatar'
import MainLayoutBreadcrumb from './MainLayoutBreadcrumb.vue'

const applicationState = StructuresStates.getApplicationState()


</script>
