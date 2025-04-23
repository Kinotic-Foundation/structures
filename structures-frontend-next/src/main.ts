import {createStructuresUI} from '@/plugins/StructuresUI.js'
import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import PrimeVue from 'primevue/config'
import StyleClass from 'primevue/styleclass'
import Aura from '@primeuix/themes/aura'
import router from '@/router'
import ToastService from 'primevue/toastservice'
// import 'primevue/resources/themes/saga-blue/theme.css'
// import 'primevue/resources/primevue.min.css'
// import 'primeicons/primeicons.css'
// import '@primevue/themes/saga-blue/theme.css';
const app = createApp(App);
app.use(PrimeVue, {
    theme: {
        preset: Aura,
        options: {
            darkModeSelector: '.structures-admin-dark',
            cssLayer: false,
            prefix: 'p',
        }
    }
})
app.directive('styleclass', StyleClass)
app.use(ToastService)
app.use(createStructuresUI(), {router})

app.use(router)

app.mount('#app')

