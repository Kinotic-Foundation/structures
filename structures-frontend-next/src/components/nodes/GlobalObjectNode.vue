<template>
  <div class="rounded-lg overflow-hidden shadow bg-white text-xs min-w-[200px] border border-gray-300">
    <div>
      <div class="p-3 bg-surface-950 font-semibold text-white flex items-center gap-2">
        <i class="pi pi-table text-[#C8FB39]" />
        {{ data.label }}
      </div>
    </div>

    <div
      v-for="(field, index) in data.fields"
      :key="index"
      class="relative px-2 py-1 border-t border-gray-300 flex justify-between items-center"
    >
      <span class="font-normal text-surface-900">{{ field.name }}</span>
      <span class="text-gray-500 text-xs bg-gray-200 ml-2 px-2 py-1"> {{ field.type }}</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { Handle, Position } from "@vue-flow/core";

defineProps<{
  data: {
    label: string;
    fields: { name: string; type: string }[];
    color: any;
  };
}>();
</script>
