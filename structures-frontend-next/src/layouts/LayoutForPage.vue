<template>
  <div class="h-screen w-screen bg-surface-0 dark:bg-surface-950">
    <div class="fixed top-0 left-0 right-0 h-[64px] bg-white border-b border-gray-200 z-50">
      <Header />
    </div>
    <div class="fixed top-[64px] left-0 w-[256px] h-[calc(100vh-64px)] bg-white border-r border-gray-200 z-40">
      <SideBar />
    </div>
    <div class="pl-[256px] pt-[64px] h-full">
      <div class="h-[calc(100vh-64px)] overflow-y-auto px-[33px] py-6">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import SideBar from '@/components/SideBar.vue';
import Header from './Header.vue';
</script>
