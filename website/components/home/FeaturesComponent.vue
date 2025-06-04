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
                    <img :src="'/icons/' + f.icon" class="w-10 h-10" alt="icon" />
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

  const features = [
    {
      title: "Schema Flexibility",
      details:
        "Structures supports schema evolution, allowing developers to modify data schemas over time without requiring significant changes to their application code. This feature simplifies the process of managing complex data structures and enables developers to evolve their data models to meet changing business requirements.",
      icon: "folder.svg",
    },
    {
      title: "Data Management",
      details:
        "Structures provides developers with a comprehensive set of tools for managing data, including the ability to create, read, update, and delete data. This feature allows developers to easily manage complex data sets, and provides a flexible interface for data manipulation.",
      icon: "database.svg",
    },
    {
      title: "Intuitive GUI",
      details:
        "Structures provides a user-friendly graphical user interface (GUI) for data management built with Continuum, as well as an OpenAPI, and GraphQL interfaces for accessing data stored in the framework. This feature enables developers to easily interact with the data stored in Structures, and allows them to integrate Structures into their existing applications and workflows.",
      icon: "share.svg",
    },
  ];
  </script>
  