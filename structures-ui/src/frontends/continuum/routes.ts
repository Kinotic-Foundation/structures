import { RouteConfig } from 'vue-router'

const continuumRoutes: RouteConfig[] = [
    {
        path: '/login',
        meta:{
            authenticationRequired: false
        },
        component: () => import(/* webpackChunkName: "main" */'@/frontends/continuum/pages/Login.vue')
    },
    {
        path: '/404',
        meta:{
            authenticationRequired: false
        },
        component: () => import(/* webpackChunkName: "main" */'@/frontends/continuum/pages/FourOFour.vue')
    }
]

export default continuumRoutes
