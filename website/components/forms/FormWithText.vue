<script setup>
import { useModal } from '~/composables/useModal'
import Modal from '~/components/modals/Modal.vue'

const { modalState, openModal, closeModal } = useModal()
const employees = ['1-10', '11-50', '51-200', '201+']
const colorMode = useColorMode()
const isMounted = ref(false)
const windowWidth = ref(0)

onMounted(() => {
  isMounted.value = true
  windowWidth.value = window.innerWidth
  window.addEventListener('resize', () => {
    windowWidth.value = window.innerWidth
  })
})

const gradientStyle = computed(() => {
  const isDark = colorMode.value === 'dark'
  const isSmallScreen = windowWidth.value < 1280 // xl

  if (isSmallScreen) {
    return isDark
      ? 'linear-gradient(117deg, rgba(16, 16, 16, 1) 0%, rgba(54, 81, 237, 0.28) 100%)'
      : 'linear-gradient(117deg, rgba(255, 255, 255, 1) 0%, rgba(128, 147, 255, 0.28) 100%)'
  } else {
    return isDark
      ? 'linear-gradient(171deg, rgba(16, 16, 16, 1) 0%, rgba(54, 81, 237, 0.28) 100%)'
      : 'linear-gradient(171deg, rgba(255, 255, 255, 1) 0%, rgba(128, 147, 255, 0.28) 100%)'
  }
})
</script>

<template>
  <section v-if="isMounted" :style="{ background: gradientStyle }">
    <BaseContainer>
      <div class="mx-auto rounded-xl xl:py-20 py-12 pb-5 xl:px-[88px] px-5 md:flex md:justify-between gap-10">
        <div class="md:w-1/2 mb-10 md:mb-0">
          <h2
            class="text-[#101010] dark:text-white text-[30px] xl:text-4xl font-[BauhausNanoDisplayRegular] leading-tight text-center xl:text-left">
            Sign up to Get<br />
            Early Access to<br />
            Structures Cloud
          </h2>
        </div>

        <form class="md:w-1/2 grid grid-cols-1 sm:grid-cols-2 gap-6">
          <div>
            <label class="block text-sm text-[#101010] dark:text-white mb-2">Name</label>
            <input type="text" placeholder="Enter name"
              class="border border-[#CBD5E1] dark:border-none rounded-lg w-full h-[48px] px-4 bg-white dark:bg-[#FFFFFF]/10 text-black dark:text-white outline-none focus:ring-2 focus:ring-blue-500" />
          </div>
          <div>
            <label class="block text-sm text-[#101010] dark:text-white mb-2">Email</label>
            <input type="email" placeholder="Enter email"
              class="border border-[#CBD5E1] dark:border-none rounded-lg w-full h-[48px] px-4 bg-white dark:bg-[#FFFFFF]/10 text-black dark:text-white outline-none focus:ring-2 focus:ring-blue-500" />
          </div>
          <div>
            <label class="block text-sm text-[#101010] dark:text-white mb-2">Company name</label>
            <input type="text" placeholder="Company"
              class="border border-[#CBD5E1] dark:border-none rounded-lg w-full h-[48px] px-4 bg-white dark:bg-[#FFFFFF]/10 text-black dark:text-white outline-none focus:ring-2 focus:ring-blue-500" />
          </div>
          <div>
            <label class="block text-sm text-[#101010] dark:text-white mb-2">Number of employees</label>
            <select
              class="border border-[#CBD5E1] dark:border-none rounded-lg w-full h-[48px] px-4 bg-white dark:bg-[#FFFFFF]/10 text-black dark:text-white outline-none focus:ring-2 focus:ring-blue-500">
              <option disabled selected>Select</option>
              <option class="text-black dark:text-white" v-for="(e, index) in employees" :key="index">{{ e }}</option>
            </select>
          </div>

          <div class="col-span-1 sm:col-span-2">
            <button
              @click="openModal({ title: 'Welcome', content: 'This is a custom modal!' })" 
              type="button"
              class="xl:w-1/4 w-full h-[48px] bg-[#3651ED] text-white rounded-md font-medium hover:bg-blue-600 transition">
              Sign up for Beta
            </button>
          </div>
        </form>
        <Modal>
        <template #header>
          <h2>{{ modalState.title }}</h2>
        </template>
        <template #body>
          <p>{{ modalState.content }}</p>
        </template>
        <template #footer>
          <button @click="closeModal">Close</button>
        </template>
      </Modal>
      </div>
    </BaseContainer>
  </section>
</template>
