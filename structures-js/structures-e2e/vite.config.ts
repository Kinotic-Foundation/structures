import { defineConfig } from 'vitest/config'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig(
    {
      plugins: [vue()],
      test: {
        globalSetup: './test/setup.ts',
        setupFiles: ["allure-vitest/setup"],
        reporters: [
          "verbose",
          [
            "allure-vitest/reporter",
            {
              resultsDir: "allure-results",
            },
          ],
        ],
      }
    }
)
