<template>
  <div>
    <div v-if="!isMounted"
      class="fixed inset-0 flex items-center justify-center bg-black text-white z-50 transition-opacity duration-300">
      <svg class="animate-spin h-10 w-10 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
        <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4" />
        <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z" />
      </svg>
    </div>
    <div v-if="isMounted"
      class="w-full bg-cover bg-center bg-no-repeat py-1 transition-opacity duration-500 ease-in" :style="{
        backgroundImage: `url(/images/${colorMode.value === 'dark' ? 'mask.svg' : 'mask-light.svg'})`,
        backgroundColor: colorMode.value === 'dark' ? '#000000' : '#FFFFFF'
      }">
      <BaseContainer>
        <div class="w-full flex justify-between items-center xl:px-[88px] px-5 xl:flex-row flex-col-reverse">
          <div class="max-w-full xl:max-w-[550px] mt-0 xl:mt-16 mb-[54px] xl:mb-0 text-white">
            <p class="flex flex-col xl:mb-10 mb-3">
              <span class="xl:text-[68px] text-4xl text-center xl:text-left font-[BauhausNanoDisplayRegular] leading-[110%] dark:text-white text-black">
                Data Evolution Made Simple
              </span>
            </p>
            <p class="xl:text-[22px] text-[17px] text-center xl:text-left dark:text-white text-black font-[InterRegular]">
              Streamline your management of complex data structures with our powerful
              open-source framework for schema evolution and data manipulation.
            </p>
            <div class="xl:mt-10 mt-7 flex justify-center xl:justify-start items-center gap-6">
              <button class="px-4 py-2 xl:text-lg text-[17px] rounded-lg bg-[#3651ED] text-white font-medium">
                Sign up for Beta
              </button>
              <button class="px-4 py-2 xl:text-lg rounded-lg bg-[#3651ED]/10 text-[#3651ED] dark:bg-[#D8DEFF]/10 dark:text-[#EDEEF2] font-medium">
                View on Github
              </button>
            </div>
          </div>
          <div class="mb-11 xl:mb-0">
            <img src="/public/images/hero-section-image.png" alt="Hero Section Image" class="max-w-[700px] w-full" />
          </div>
        </div>

      </BaseContainer>
    </div>
  </div>
</template>

<script setup>
const colorMode = useColorMode()
const isMounted = ref(false)

onMounted(() => {
  isMounted.value = true
})
</script>
