import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import PrimeVue from 'primevue/config'
import StyleClass from 'primevue/styleclass'
import Aura from '@primevue/themes/aura'

const app = createApp(App);
app.use(PrimeVue, {
    theme: {
        preset: Aura
    }
})
app.directive('styleclass', StyleClass)
app.mount('#app')

