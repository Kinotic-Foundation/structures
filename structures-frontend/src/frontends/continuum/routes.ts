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
    },
    {
        path: '/sessionUpgrade/:id',
        meta:{
            authenticationRequired: true
        },
        component: () => import(/* webpackChunkName: "main" */'@/frontends/continuum/pages/CliSessionUpgrade.vue'),
        props: true
    }
]

if(process.env.VUE_APP_KEYCLOAK_SUPPORT === "true") {
    continuumRoutes.unshift({
        path: '/login',
        name: 'login',
        meta:{
            authenticationRequired: false
        },
        component: () => import(/* webpackChunkName: "main" */'@/frontends/continuum/pages/LoginKeycloak.vue'),
        props: true
    })
}else{
    continuumRoutes.unshift({
        path: '/login',
        name: 'login',
        meta:{
            authenticationRequired: false
        },
        component: () => import(/* webpackChunkName: "main" */'@/frontends/continuum/pages/Login.vue'),
        props: true
    })
}

export default continuumRoutes
