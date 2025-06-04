<template>
  <header class="bg-white text-black dark:bg-[rgba(0,0,0,0.8)] dark:text-white shadow-md sticky top-0 left-0 z-50">
    <BaseContainer>
      <nav class="mx-auto py-3 flex justify-between items-center px-[88px]">
        <NuxtLink to="/" class="text-lg font-bold text-blue-700 dark:text-orange-700">
          <img src="/icons/logo.svg" class="invert-100 dark:invert-0" alt="logo" />
        </NuxtLink>
        <div class="space-x-4 flex items-center">
          <NuxtLink to="https://structuresframework.org/webdocs/guide/overview.html" class="text-gray-900 hover:text-gray-700 dark:text-gray-300">Guide</NuxtLink>
          <NuxtLink to="https://structuresframework.org/webdocs/reference/structures-config.html" class="text-gray-900 hover:text-gray-700 dark:text-gray-300">Reference</NuxtLink>
          <NuxtLink to="https://structuresframework.org/allure/index.html" class="text-gray-900 hover:text-gray-700 dark:text-gray-300">Test Status</NuxtLink>
          <NuxtLink to="https://github.com/Kinotic-Foundation/structures" class="text-gray-900 hover:text-gray-700 dark:text-gray-300 flex items-center gap-1">
            <img src="/icons/git.svg" alt="GitHub" class="w-5 h-5" />
          </NuxtLink>

          <label class="relative inline-flex items-center cursor-pointer">
            <input
              type="checkbox"
              class="sr-only peer"
              :checked="$colorMode.preference === 'dark'"
              @change="$colorMode.preference = $colorMode.preference === 'dark' ? 'light' : 'dark'"
            />
            <div
              class="w-11 h-6 bg-gray-300 peer-focus:outline-none peer-focus:ring-2 peer-focus:ring-blue-500 rounded-full peer dark:bg-gray-600 peer-checked:bg-blue-600 transition-all"
            ></div>
            <div
              class="absolute left-0.5 top-0.5 w-5 h-5 bg-white rounded-full transition-transform peer-checked:translate-x-full"
            ></div>
          </label>
        </div>
      </nav>
    </BaseContainer>
  </header>
</template>
