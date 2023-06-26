import { RouteConfig } from 'vue-router'

let continuumRoutes: RouteConfig[] = [
    {
        path: '/404',
        meta:{
            authenticationRequired: false
        },
        component: () => import(/* webpackChunkName: "main" */'@/frontends/continuum/pages/FourOFour.vue')
    },
    {
        path: '/access-denied',
        meta:{
            authenticationRequired: false
        },
        component: () => import(/* webpackChunkName: "main" */'@/frontends/continuum/pages/AccessDenied.vue')
    }
]

if(process.env.VUE_APP_KEYCLOAK_SUPPORT === "true") {
    continuumRoutes.unshift({
        path: '/login',
        meta:{
            authenticationRequired: false
        },
        component: () => import(/* webpackChunkName: "main" */'@/frontends/continuum/pages/LoginKeycloak.vue')
    })
}else{
    continuumRoutes.unshift({
        path: '/login',
        meta:{
            authenticationRequired: false
        },
        component: () => import(/* webpackChunkName: "main" */'@/frontends/continuum/pages/Login.vue')
    })
}

export default continuumRoutes
