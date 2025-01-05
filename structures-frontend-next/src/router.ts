import { createRouter, createWebHistory } from 'vue-router'
import pagesRoutes from '@/pages/routes'

const routes =  [
    { // This is where we have to configure the default route
        path: '/',
        redirect: '/applications'
    },
    {
        path: '/404',
        meta:{
            authenticationRequired: false
        },
        component: () => import(/* webpackChunkName: "main" */'@/pages/FourOFour.vue')
    },

    ...pagesRoutes,

    { // Not found must be at end
        path: '/:catchAll(.*)',
        redirect: '/404'
    }
]


const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
