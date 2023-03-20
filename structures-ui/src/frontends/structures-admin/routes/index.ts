import { RouteConfig } from 'vue-router'

const adminRoutes: RouteConfig[] = [
    {
        path: '/structures-admin',
        component: () => import(/* webpackChunkName: "frontendLayout" */'@/frontends/continuum/layouts/FrontendLayout.vue'),
        meta: {
            authenticationRequired: true,
            isFrontend: true,
            icon: 'fa-cubes',
            title: 'Admin'
        },
        children: [
            {
                path: '/structures-admin', component: () => import('@/frontends/structures-admin/pages/structures/structures/Structures.vue'),
                meta: {
                    authenticationRequired: true,
                    icon: 'fa-toolbox',
                    title: 'Structures'
                }
            },
            {
                path: '/structures-admin-traits', component: () => import('@/frontends/structures-admin/pages/structures/traits/Traits.vue'),
                meta: {
                    authenticationRequired: true,
                    icon: 'fa-tools',
                    title: 'Traits'
                }
            },
            {
                path: '/structure-items/:structureId', component: () => import('@/frontends/structures-admin/pages/structures/items/GenericItem.vue'), props: true,
                meta: {
                    authenticationRequired: true,
                    noShow: true,
                    title: 'Items'
                }
            }
        ]
    }
]

export default adminRoutes
