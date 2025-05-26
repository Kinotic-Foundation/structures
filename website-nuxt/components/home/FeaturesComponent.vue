<template>
      <BaseContainer>
        <section class="pt-16 px-[88px]">
              <div class="flex flex-wrap border-b gap-[43px] border-white/20 justify-between pb-[70px] mx-auto">
                <div
                  v-for="(f, i) in features"
                  :key="i"
                  class="flex-1 min-w-[250px] border border-black dark:border-white/20 bg-transparent p-8 rounded-xl"
                >
                  <div class="flex items-center gap-5 mb-6">
                    <img :src="getIconPath(f.icon)" class="w-10 h-10" alt="icon" />
                    <h3 class="text-xl font-semibold dark:text-white text-[#101010]">
                      {{ f.title }}
                    </h3>
                  </div>
                  <p class="dark:text-white text-[#101010]">
                    {{ f.details }}
                  </p>
                </div>
              </div>
        </section>
      </BaseContainer>
  </template>
  
  <script setup>
import BaseContainer from '~/components/BaseContainer.vue'

  const getIconPath = (filename) => `/icons/${filename}`;
  const features = [
    {
      title: "Schema Flexibility",
      details:
        "Structures makes schema evolution easy—letting developers adjust data schemas over time without major changes to application code. This streamlines the management of complex data and helps teams adapt their data models as business needs evolve.",
      icon: "share.svg",
    },
    {
      title: "Data Management",
      details:
        "Structures equips developers with powerful tools to create, read, update, and delete data—making it easy to manage complex datasets and offering a flexible interface for data manipulation.",
      icon: "database.svg",
    },
    {
      title: "Intuitive GUI and OpenAPI",
      details:
        "Structures offers an intuitive GUI built with Continuum and an OpenAPI interface, making it easy for developers to manage data and integrate Structures into existing applications and workflows.",
      icon: "folder.svg",
    },
  ];
  </script>
  