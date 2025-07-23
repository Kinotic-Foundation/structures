<!-- SidebarItem.vue -->
<script setup lang="ts">
import { computed } from 'vue'

const props = defineProps<{
  icon: string,
  label: string,
  collapsed: boolean,
  textColor?: string
}>()

const textClass = computed(() => props.textColor ?? 'text-black')
</script>

<template>
  <div
    class="flex items-center px-[10px] py-[9px] mb-[7px] cursor-pointer w-full rounded-md hover:bg-gray-200 transition-colors duration-200"
  >
    <div class="min-w-[20px] flex justify-center">
      <i :class="['pi', icon, 'text-base']" />
    </div>
    <div class="w-[8px]"></div>
    <div class="overflow-hidden">
      <span
        :class="[
          'inline-block whitespace-nowrap text-sm font-normal transform transition-all duration-300 origin-left',
          textClass,
          collapsed ? 'opacity-0 scale-x-0' : 'opacity-100 scale-x-100'
        ]"
      >
        {{ label }}
      </span>
    </div>
  </div>
</template>
