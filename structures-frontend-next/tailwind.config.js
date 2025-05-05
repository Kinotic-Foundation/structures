/** @type {import('tailwindcss').Config} */
export default {
  content: [
    './index.html',
    './src/**/*.{vue,js,ts}',
    './node_modules/primevue/**/*.{js,ts,vue}',
  ],
  darkMode: "selector",
  theme: {
    extend: {},
  },
  plugins: [require('tailwindcss-primeui')]
}

