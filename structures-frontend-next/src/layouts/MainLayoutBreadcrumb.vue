<script setup lang="ts">
import { computed } from 'vue';
import { useRoute } from 'vue-router';
import Breadcrumb from 'primevue/breadcrumb';
import type { MenuItem } from 'primevue/menuitem';

defineProps<{
  separatorIcon?: string;
}>();

const route = useRoute();

const breadcrumbs = computed<MenuItem[]>(() => {
  return route.matched
    .filter((r) => r.meta.label)
    .map((r, index, array) => ({
      label: r.meta.label as string,
      to: index < array.length - 1 ? r.path : undefined,
      icon: r.meta.icon as string | undefined,
    }));
});
</script>

<template>
    <div class="w-full pt-6 px-[33px] flex justify-between items-center">
      <Breadcrumb
        :model="breadcrumbs"
        aria-label="Breadcrumb"
        class="bg-surface-0 rounded-md shadow-sm"
      >
        <template #item="{ item }">
          <router-link
            v-if="item.to"
            :to="item.to"
            class="flex justify-center gap-2 items-center"
          >
            <span v-if="item.icon" :class="item.icon" class="text-[#64748B] text-[14px] "></span>
            <span class="text-[#64748B] text-[14px]">{{ item.label }}</span>
          </router-link>
          <div class="flex justify-center gap-2 items-center" v-else >
              <span class="text-[#64748B] text-[14px] flex justify-start items-center">
                <span v-if="item.icon" :class="item.icon" class="mr-2"></span>
                {{ item.label }}
              </span>
          </div>
        </template>
        <template #separator>
          <span
            :class="separatorIcon || 'pi pi-angle-right'"
            class="mx-2 text-[#64748B] text-lg"
          ></span>
        </template>
      </Breadcrumb>
      <div class="flex justify-between items-center gap-16">
        <div class="text-[#64748B] text-[14px] flex gap-4">
            <router-link to="#">FeedBack</router-link>
            <router-link to="#">Changelog</router-link>
            <router-link to="#">Help</router-link>
            <router-link to="#">Docs</router-link>
        </div>
        <div class="w-8 h-8 rounded-full border border-text-[#64748B] flex justify-center items-center cursor-pointer">
            <i class="pi pi-bell"></i>
        </div>
      </div>
    </div>
  </template>
