import {createStructuresUI} from '@/plugins/StructuresUI.js'
import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import PrimeVue from 'primevue/config'
import StyleClass from 'primevue/styleclass'
import Aura from '@primevue/themes/aura'
import router from '@/router'

const app = createApp(App);
app.use(PrimeVue, {
    theme: {
        preset: Aura,
        options: {
            darkModeSelector: '.structures-admin-dark',
        }
    }
})
app.directive('styleclass', StyleClass)

app.use(createStructuresUI(), {router})

app.use(router)

app.mount('#app')

