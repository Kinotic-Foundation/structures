import { createStructuresUI } from '@/plugins/StructuresUI.js'
import './style.css'
import './theme.css'
import PrimeVue from 'primevue/config'
import StyleClass from 'primevue/styleclass'
import StructuresTheme from '@/theme'
import router from '@/router'
import ToastService from 'primevue/toastservice'
import { CONTINUUM_UI } from '@/IContinuumUI'
import 'primeicons/primeicons.css'
import { createApp } from 'vue'
import App from './App.vue'

const app = createApp(App)

app.use(PrimeVue, {
    theme: {
        preset: StructuresTheme,
        options: {
            darkModeSelector: '.structures-admin-dark',
            cssLayer: false,
            prefix: 'p',
        }
    }
})
CONTINUUM_UI.initialize({
    routes: router.options.routes
});
app.directive('styleclass', StyleClass)
app.use(ToastService)
app.use(createStructuresUI(), { router })


app.use(router)


app.mount('#app')