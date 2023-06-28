import { resolve } from 'path'
import { defineConfig } from 'vitest/config'
import dts from 'vite-plugin-dts'
import { externalizeDeps } from 'vite-plugin-externalize-deps'

// https://vitejs.dev/guide/build.html#library-mode
export default defineConfig({
    build: {
        lib: {
            entry: resolve(__dirname, 'src/index.ts'),
            name: 'structures-data',
            fileName: 'structures-data',
            formats: ["es", "cjs"],
        },
        sourcemap: true,
    },
    resolve:{
        alias:{
            '@' : resolve(__dirname, 'src')
        },
    },
    plugins: [externalizeDeps(), dts()],
    test: {
        // vitest options here
    },
})
