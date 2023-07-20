import 'reflect-metadata'
import Vue from 'vue'
import vuetify from './plugins/vuetify'
import {router} from '@/frontends/router'
import './plugins/vue-highlight'
import './plugins/vue-editor-ace'
import './plugins/vue-notification'
import './plugins/prism-editor'
import './registerServiceWorker'
import './frontends/continuum'

// Make sure services get autowired early during App entry!
import '@/frontends/services'

// now load app specific entry points
import '@/frontends/states'
import Main from '@/Main.vue'
import Keycloak, {KeycloakOnLoad} from 'keycloak-js'

// Vue.config.productionTip = false


if(process.env.VUE_APP_KEYCLOAK_SUPPORT === 'true') {

    let initOptions = {
        url: process.env.VUE_APP_KEYCLOAK_URL,
        realm: process.env.VUE_APP_KEYCLOAK_REALM,
        clientId: process.env.VUE_APP_KEYCLOAK_CLIENT_ID,
        onLoad: 'login-required' as KeycloakOnLoad
    }
    let keycloak: Keycloak = new Keycloak(initOptions)

    keycloak.init({ onLoad: initOptions.onLoad }).then((auth) => {
        if (!auth) {
            keycloak.redirectUri = window.location.origin
        } else {

            new Vue({
                router,
                vuetify,
                render: (h) => h(Main, { props: { keycloak: keycloak } })
            }).$mount('#main')

        }
    }).catch((error) => {
        console.error("Authenticated Failed", error)
    })

}else{

    new Vue({
        router,
        vuetify,
        render: (h) => h(Main)
    }).$mount('#main')

}

