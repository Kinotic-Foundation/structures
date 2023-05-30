import { RouteConfig } from 'vue-router'
import {ServiceIdentifierConstants} from "@/frontends/iam/Constants";

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
                    icon: 'fa-sitemap',
                    title: 'Namespaces'
                },
                children: [
                    {
                        path: '',
                        component: () => import('@/frontends/continuum/pages/BasicCrudList.vue'),
                        props: {
                            crudServiceIdentifier: 'org.kinotic.structures.api.services.NamespaceService',
                            headers: [
                                { text: 'Name', value: 'id', width: 300, sortable: true },
                                { text: 'Description', value: 'description', sortable: false }
                            ]
                        }
                    },
                    {
                        path: 'edit/:identity', component: () => import('@/frontends/structures-admin/pages/structures/namespaces/NamespaceAddEdit.vue'), props: true
                    },
                    {
                        path: 'add', component: () => import('@/frontends/structures-admin/pages/structures/namespaces/NamespaceAddEdit.vue')
                    }
                ]
            },
            // {
            //     path: '/structures-admin', component: () => import('@/frontends/structures-admin/pages/structures/structures/Structures.vue'),
            //     meta: {
            //         authenticationRequired: true,
            //         icon: 'fa-toolbox',
            //         title: 'Structures'
            //     }
            // },
            // {
            //     path: '/structure-items/:structureId', component: () => import('@/frontends/structures-admin/pages/structures/items/GenericItem.vue'), props: true,
            //     meta: {
            //         authenticationRequired: true,
            //         noShow: true,
            //         title: 'Items'
            //     }
            // }
        ]
    }
]

export default adminRoutes
