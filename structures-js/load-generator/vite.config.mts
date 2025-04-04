import { defineConfig } from 'vite'
import {externals} from './plugins/externals'
import {shims} from './plugins/shims'
import {config} from './plugins/config'
import tsconfigPaths from 'vite-tsconfig-paths'

export default defineConfig({
  // basically what is explained here https://dev.to/rxliuli/developing-and-building-nodejs-applications-with-vite-311n
  plugins: [shims(), externals(), config(), tsconfigPaths()],
})
