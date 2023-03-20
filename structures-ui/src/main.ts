import 'reflect-metadata'
import Vue from 'vue'
import vuetify from './plugins/vuetify'
import './plugins/vue-highlight'
import './plugins/vue-editor-ace'
import './plugins/vue-notification'
import './plugins/vue-composition-api'
import './registerServiceWorker'
import './frontends/continuum'
import {router} from '@/frontends/router'

// Make sure services get autowired early during App entry!
import '@/frontends/services'

// now load app specific entry points
import '@/frontends/states'
import Main from '@/Main.vue'

// Vue.config.productionTip = false

new Vue({
  router,
  vuetify,
  render: (h) => h(Main)
}).$mount('#main')
