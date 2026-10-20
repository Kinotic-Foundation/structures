import {createRouter, createWebHistory, type RouteRecordRaw} from 'vue-router'
import { Kinotic } from '@kinotic-ai/core'
import pagesRoutes from '@/pages/routes'

/**
 * The page a signed-in member lands on: the quickstart until the organization has its first
 * project, then its applications.
 */
async function landingPath(): Promise<string> {
    let ret: string
    try {
        ret = await Kinotic.projects.count() > 0 ? '/applications' : '/quickstart'
    } catch {
        // a rejected guard aborts the navigation and leaves a blank page
        ret = '/applications'
    }
    return ret
}

const routes: RouteRecordRaw[] = [
    {
        // the name makes this component-less record matchable; beforeEnter runs after the
        // global auth guard, so landingPath only queries for a signed-in member
        name: 'landing',
        path: '/',
        children: [],
        beforeEnter: landingPath
    },
    {
        path: '/404',
        meta:{
            authenticationRequired: false
        },
        component: () => import('@/pages/FourOFour.vue')
    },

    ...pagesRoutes,

    {
        path: '/:catchAll(.*)',
        redirect: '/404'
    }
]


const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
