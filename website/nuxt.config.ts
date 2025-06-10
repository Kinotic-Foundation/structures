import tailwindcss from "@tailwindcss/vite";

// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  ssr: true,
  compatibilityDate: '2025-05-15',
  devtools: { enabled: true },
  modules: ['@nuxtjs/color-mode'],
  css: ['~/assets/css/main.css'],
  colorMode: {
    classSuffix: '',         // must be '' for Tailwind dark: to work
    preference: 'dark',    // or 'dark' or 'light'
    fallback: 'light',       // used if no system preference or SSR
    storageKey: 'nuxt-color-mode',
  },
  vite: {
    plugins: [
      tailwindcss()
    ],
  },
  app: {
    baseURL: '/website/'
  }
})
