<template>
  <div class="relative rounded-xl bg-lime-400/10 text-xs min-w-[200px] border border-lime-400 p-6">
    <div class="absolute top-[-12px] left-3 text-xs font-semibold px-2 py-1 bg-lime-500 text-white rounded-sm">
      {{ data.label }}
    </div>
    <div class="flex flex-row overflow-auto gap-4">
      <div
        v-for="(variant, index) in data.variants"
        :key="index"
        class="flex flex-col bg-white rounded shadow min-w-[160px]"
      >
        <div class="font-semibold text-surface-900 text-xs p-2 bg-lime-200 w-full">
          {{ variant.name || `Variant ${index + 1}` }}
        </div>
        <div v-for="(field, fIndex) in variant.fields" :key="fIndex" class="border-t border-surface-100 text-xs p-2 text-gray-700">
         {{ field }}
        </div>
        <Handle
          type="source"
          :position="Position.Right"
          :id="`out-${index}`"
          class="w-2 h-2 bg-green-500 absolute right-[-6px] top-1/2 transform -translate-y-1/2"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { Handle, Position } from '@vue-flow/core'

defineProps<{
  data: {
    label: string
    variants: { name?: string; fields: string[] }[]
    color: string
  }
}>()
</script>
