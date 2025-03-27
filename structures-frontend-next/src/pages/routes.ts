import { type RouteRecordRaw } from 'vue-router'

const pageRoutes: RouteRecordRaw[] = [
    {
        path: '/applications', component: () => import('@/layouts/MainLayout.vue'),
        meta: {
            authenticationRequired: false,
            showInMainNav: true,
            icon: 'fa-sitemap',
            label: 'Applications'
        },
        children: [
            {
                path: 'test',
                meta:{
                    showInMainNav: true,
                    icon: 'fa-sitemap',
                    label: 'Test'
                },
                component: () => import('@/pages/Test.vue'),
            },
            {
                path: 'test3',
                meta:{
                    showInMainNav: true,
                    icon: 'fa-sitemap',
                    label: 'Test Two'
                },
                component: () => import('@/pages/Test2.vue'),
            },
        ]
    },
    // {
    //     path: '/structures', component: () => import('@/frontends/continuum/layouts/NestedLayout.vue'),
    //     meta: {
    //         authenticationRequired: true,
    //         icon: 'fa-toolbox',
    //         title: 'Structures'
    //     },
    //     children: [
    //         {
    //             path: '',
    //             component: () => import('@/frontends/structures-admin/pages/structures/structures/StructuresList.vue')
    //         },
    //         {
    //             path: 'edit/:id', component: () => import('@/frontends/structures-admin/pages/structures/structures/StructureAddEdit.vue'), props: true
    //         },
    //         {
    //             path: 'add', component: () => import('@/frontends/structures-admin/pages/structures/structures/StructureAddEdit.vue')
    //         },
    //         {
    //             path: '/entity/:structureId', component: () => import('@/frontends/structures-admin/pages/structures/entity/EntityList.vue'), props: true,
    //             meta: {
    //                 noShow: true,
    //                 title: 'Entities'
    //             }
    //         }
    //     ]
    // }
]

export default pageRoutes
