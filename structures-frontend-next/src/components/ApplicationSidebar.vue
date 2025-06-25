<!-- <template>
  <transition name="slide">
    <div
      v-if="visible"
      class="fixed inset-y-0 right-0 w-[400px] bg-white shadow-xl z-50 p-4 overflow-y-auto"
    >
    <div class="flex justify-between items-center mb-4 border-b border-b-[#E6E7EB] pb-4">
        <div class="flex items-center gap-3">
            <img src="@/assets/action-plus-icon.svg" />
            <h2 class="text-lg font-semibold">New Application</h2>
        </div>
        <button @click="handleClose" class="w-[11px] h-[11px] cursor-pointer">
            <img src="@/assets/close-icon.svg" />
        </button>
      </div>

      <form @submit.prevent="handleSubmit" class="flex flex-col gap-4 p-4">
        <div class="mb-5">
          <label class="block text-sm text-[#101010] mb-3 font-semibold">Name</label>
          <input
            v-model="form.name"
            type="text"
            class="w-full p-2 border border-[#D2D3D9] rounded-lg"
            required
          />
        </div>
        <div class="mb-5">
          <label class="block text-sm text-[#101010] mb-3 font-semibold">Description</label>
          <textarea
            v-model="form.description"
            class="w-full p-2 border border-[#D2D3D9] rounded-lg"
          />
        </div>

        <label class="block text-sm text-[#101010] font-semibold">API configuration</label>
        <div class="border border-[#E6E7EB] rounded-2xl p-4 w-full">
          <div class="flex items-center gap-2 border-b border-b-[#E6E7EB] pb-4">
            <img src="@/assets/graphql.svg" />
            <span class="text-[#3F424D] text-sm font-normal">
                GraphQL
            </span>
          </div>
          <div class="flex items-center gap-2 pt-4">
            <img src="@/assets/scalar.svg" />
            <span class="text-[#3F424D] text-sm font-normal">
                OpenAPI
            </span>
          </div>
        </div>

        <div class="flex justify-end gap-2 mt-6">
          <button type="button" @click="handleClose">Cancel</button>
          <button type="submit">Create Application</button>
        </div>
      </form>
    </div>
  </transition>
</template>

<script lang="ts">
import { Component, Vue, Prop, Emit } from 'vue-facing-decorator'

interface ApplicationForm {
  name: string
  description: string
  graphql: boolean
  openapi: boolean
}

@Component
export default class ApplicationSidebar extends Vue {
  @Prop({ required: true }) readonly visible!: boolean

  form: ApplicationForm = {
    name: '',
    description: '',
    graphql: true,
    openapi: false
  }

  @Emit('close')
  handleClose(): void {
    this.resetForm()
  }

  @Emit('submit')
  handleSubmit(): ApplicationForm {
    const data = { ...this.form }
    this.resetForm()
    return data
  }

  private resetForm(): void {
    this.form = {
      name: '',
      description: '',
      graphql: true,
      openapi: false
    }
  }
}
</script>

<style scoped>
/* Slide-in from right animation */
.slide-enter-active,
.slide-leave-active {
  transition: transform 0.3s ease;
}
.slide-enter-from {
  transform: translateX(100%);
}
.slide-enter-to {
  transform: translateX(0%);
}
.slide-leave-from {
  transform: translateX(0%);
}
.slide-leave-to {
  transform: translateX(100%);
}
</style> -->

<template>
  <transition name="slide">
    <div
      v-if="visible"
      class="fixed inset-y-0 right-0 w-[400px] h-screen bg-white shadow-xl z-50 overflow-y-auto"
    >
      <div class="flex justify-between items-center mb-4 border-b border-b-[#E6E7EB] p-4">
        <div class="flex items-center gap-3">
          <img src="@/assets/action-plus-icon.svg" />
          <h2 class="text-lg font-semibold">New Application</h2>
        </div>
        <button @click="handleClose" class="w-[11px] h-[11px] cursor-pointer">
          <img src="@/assets/close-icon.svg" />
        </button>
      </div>
      <form @submit.prevent="handleSubmit" class="flex flex-col h-[calc(100vh-100px)] justify-between gap-4 p-4">
        <div>   
            <div>
              <label class="block text-sm text-[#101010] mb-3 font-semibold">Name</label>
              <input
                v-model="form.name"
                type="text"
                class="w-full p-2 border border-[#D2D3D9] rounded-lg"
                required
              />
            </div>
            <div>
              <label class="block text-sm text-[#101010] mb-3 font-semibold">Description</label>
              <textarea
                v-model="form.description"
                class="w-full p-2 border border-[#D2D3D9] rounded-lg"
              />
            </div>
            <div>
              <label class="block text-sm text-[#101010] mb-3 font-semibold">API configuration</label>
              <div class="border border-[#E6E7EB] rounded-2xl w-full divide-y divide-[#E6E7EB]">
                <!-- GraphQL -->
                <div class="flex items-center justify-between p-4">
                  <div class="flex items-center gap-2">
                    <img src="@/assets/graphql.svg" />
                    <span class="text-[#3F424D] text-sm font-normal">GraphQL</span>
                  </div>
                  <div class="w-[56px] p-1 bg-[#F4F6FA] rounded-lg flex justify-center items-center">
                      <button
                        type="button"
                        @click="form.graphql = !form.graphql"
                        :class="[
                          'px-3 py-1 text-sm font-medium rounded-lg',
                          form.graphql
                            ? '!bg-white text-[#101010]'
                            : '!bg-[#F4F6FA] text-[#999CA0]'
                        ]"
                      >
                        {{ form.graphql ? 'On' : 'Off' }}
                      </button>
                  </div>
                </div>
    
                <!-- OpenAPI -->
                <div class="flex items-center justify-between p-4">
                  <div class="flex items-center gap-2">
                    <img src="@/assets/scalar.svg" />
                    <span class="text-[#3F424D] text-sm font-normal">OpenAPI</span>
                  </div>
                  <div class="w-[56px] p-1 bg-[#F4F6FA] rounded-lg flex justify-center items-center">
                      <button
                        type="button"
                        @click="form.openapi = !form.openapi"
                        :class="[
                          'px-3 py-1 text-sm font-medium rounded-lg',
                          form.openapi
                            ? '!bg-white text-[#101010]'
                            : '!bg-[#F4F6FA] text-[#999CA0]'
                        ]"
                      >
                        {{ form.openapi ? 'On' : 'Off' }}
                      </button>
                  </div>
                </div>
              </div>
            </div>
        </div>
        <div class="flex justify-end gap-2 mt-6">
          <button
            type="button"
            @click="handleClose"
            class="px-[10px] py-[7px] rounded-lg text-sm bg-[#F0F1F5] text-[#4F5159]"
          >
            Cancel
          </button>
          <button
            type="submit"
            class="px-[10px] py-[7px] bg-[#3651ED]/70 text-white rounded-lg text-sm"
          >
            Create Application
          </button>
        </div>
      </form>
    </div>
  </transition>
</template>

<script lang="ts">
import { Component, Vue, Prop, Emit } from 'vue-facing-decorator'

interface ApplicationForm {
  name: string
  description: string
  graphql: boolean
  openapi: boolean
}

@Component
export default class ApplicationSidebar extends Vue {
  @Prop({ required: true }) readonly visible!: boolean

  form: ApplicationForm = {
    name: '',
    description: '',
    graphql: true,
    openapi: false
  }

  @Emit('close')
  handleClose(): void {
    this.resetForm()
  }

  @Emit('submit')
  handleSubmit(): ApplicationForm {
    const data = { ...this.form }
    this.resetForm()
    return data
  }

  private resetForm(): void {
    this.form = {
      name: '',
      description: '',
      graphql: true,
      openapi: false
    }
  }
}
</script>

<style scoped>
/* Slide-in from right animation */
.slide-enter-active,
.slide-leave-active {
  transition: transform 0.3s ease;
}
.slide-enter-from {
  transform: translateX(100%);
}
.slide-enter-to {
  transform: translateX(0%);
}
.slide-leave-from {
  transform: translateX(0%);
}
.slide-leave-to {
  transform: translateX(100%);
}
</style>
