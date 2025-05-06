<template>
    <div class="rounded border shadow bg-white text-xs min-w-[200px]">
      <div class="font-bold border-b px-2 py-1 bg-gray-100">{{ data.label }}</div>
      <div v-for="(field, index) in data.fields" :key="index" class="relative px-2 py-1 border-t flex justify-between items-center">
        <Handle
          type="target"
          :position="'left'"
          :id="`in-${index}`"
          class="w-2 h-2 bg-blue-500 absolute left-[-6px] top-1/2 transform -translate-y-1/2"
        />
        <span>{{ field }}</span>
        <Handle
          type="source"
          :position="'right'"
          :id="`out-${index}`"
          class="w-2 h-2 bg-green-500 absolute right-[-6px] top-1/2 transform -translate-y-1/2"
        />
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { Handle } from '@vue-flow/core'
  defineProps<{ data: { label: string; fields: string[] } }>()
  </script>