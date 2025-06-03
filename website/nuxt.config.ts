import tailwindcss from "@tailwindcss/vite";

// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  ssr: true,
  compatibilityDate: '2025-05-15',
  devtools: { enabled: true },
  modules: ['@nuxtjs/color-mode'],
  css: ['~/assets/css/main.css'],
  colorMode: {
    classSuffix: '',
  },
  vite: {
    plugins: [
      tailwindcss()
    ],
  },
  app: {
    baseURL: '/structures/website/'
  }
})
