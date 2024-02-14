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
                path: '/namespaces', component: () => import('@/frontends/continuum/layouts/NestedLayout.vue'),
                meta: {
                    authenticationRequired: true,
                    icon: 'fa-sitemap',
                    title: 'Namespaces'
                },
                children: [
                    {
                        path: '',
                        component: () => import('@/frontends/structures-admin/pages/structures/namespaces/NamespaceList.vue')
                    },
                    {
                        path: 'edit/:id', component: () => import('@/frontends/structures-admin/pages/structures/namespaces/NamespaceAddEdit.vue'), props: true
                    },
                    {
                        path: 'add', component: () => import('@/frontends/structures-admin/pages/structures/namespaces/NamespaceAddEdit.vue')
                    }
                ]
            },
            {
                path: '/structures', component: () => import('@/frontends/continuum/layouts/NestedLayout.vue'),
                meta: {
                    authenticationRequired: true,
                    icon: 'fa-toolbox',
                    title: 'Structures'
                },
                children: [
                    {
                        path: '',
                        component: () => import('@/frontends/structures-admin/pages/structures/structures/StructuresList.vue')
                    },
                    {
                        path: 'edit/:id', component: () => import('@/frontends/structures-admin/pages/structures/structures/StructureAddEdit.vue'), props: true
                    },
                    {
                        path: 'add', component: () => import('@/frontends/structures-admin/pages/structures/structures/StructureAddEdit.vue')
                    },
                    {
                        path: '/entity/:structureId', component: () => import('@/frontends/structures-admin/pages/structures/entity/EntityList.vue'), props: true,
                        meta: {
                            noShow: true,
                            title: 'Entities'
                        }
                    }
                ]
            }
        ]
    }
]

export default adminRoutes
