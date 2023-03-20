import { RouteConfig } from 'vue-router'
import { ServiceIdentifierConstants } from '@/frontends/iam/Constants'

const iamRoutes: RouteConfig[] = [
    {
        path: '/iam',
        component: () => import(/* webpackChunkName: "frontendLayout" */'@/frontends/continuum/layouts/FrontendLayout.vue'),
        meta: {
            isFrontend: true,
            icon: 'fa-user-shield',
            title: 'IAM'
        },
        children: [
            {
                path: '/iam-users', component: () => import('@/frontends/continuum/layouts/NestedLayout.vue'),
                meta: {
                    icon: 'fa-users',
                    title: 'Users'
                },
                children: [ // needed since we are using the NestedLayout above
                    {
                        // This is the default path that will be loaded first
                        path: '',
                        component: () => import('@/frontends/continuum/pages/BasicCrudList.vue'),
                        props: {
                            crudServiceIdentifier: ServiceIdentifierConstants.USER_SERVICE,
                            headers: [
                                { text: 'Email', value: 'identity', width: 300, sortable: true },
                                { text: 'First Name', value: 'metadata.firstName', width: 200, sortable: false },
                                { text: 'Last Name', value: 'metadata.lastName', sortable: false }
                            ]
                        }
                    },
                    {
                        path: 'edit/:identity', component: () => import('@/frontends/iam/pages/UserAddEdit.vue'), props: true
                    },
                    {
                        path: 'add', component: () => import('@/frontends/iam/pages/UserAddEdit.vue')
                    }
                ]
            },
            {
                path: '/iam-devices', component: () => import('@/frontends/continuum/layouts/NestedLayout.vue'),
                meta: {
                    icon: 'fa-microchip',
                    title: 'Devices'
                },
                children: [
                    {
                        path: '',
                        component: () => import('@/frontends/continuum/pages/BasicCrudList.vue'),
                        props: {
                            crudServiceIdentifier: ServiceIdentifierConstants.DEVICE_SERVICE,
                            headers: [
                                { text: 'MAC', value: 'identity', width: 300, sortable: true },
                                { text: 'Description', value: 'metadata.description', sortable: false }
                            ]
                        }
                    },
                    {
                        path: 'edit/:identity', component: () => import('@/frontends/iam/pages/DeviceAddEdit.vue'), props: true
                    },
                    {
                        path: 'add', component: () => import('@/frontends/iam/pages/DeviceAddEdit.vue')
                    }
                ]
            },
            {
                path: '/iam-roles', component: () => import('@/frontends/continuum/layouts/NestedLayout.vue'),
                meta: {
                    icon: 'fa-shield-alt',
                    title: 'Roles'
                },
                children: [
                    {
                        path: '',
                        component: () => import('@/frontends/continuum/pages/BasicCrudList.vue'),
                        props: {
                            crudServiceIdentifier: ServiceIdentifierConstants.ROLE_SERVICE,
                            headers: [
                                { text: 'Name', value: 'identity', width: 300, sortable: true },
                                { text: 'Description', value: 'description', sortable: false }
                            ]
                       }
                    },
                    {
                        path: 'edit/:identity', component: () => import('@/frontends/iam/pages/RoleAddEdit.vue'), props: true
                    },
                    {
                        path: 'add', component: () => import('@/frontends/iam/pages/RoleAddEdit.vue')
                    }
                ]
            },
            {
                path: '/iam-policies', component: () => import('@/frontends/continuum/layouts/NestedLayout.vue'),
                meta: {
                    icon: 'fa-file-signature',
                    title: 'Access Policies'
                },
                children: [
                    {
                        path: '',
                        component: () => import('@/frontends/continuum/pages/BasicCrudList.vue'),
                        props: {
                            crudServiceIdentifier: ServiceIdentifierConstants.ACCESS_POLICY_SERVICE,
                            headers: [
                                { text: 'Name', value: 'identity', width: 300, sortable: true },
                                { text: 'Description', value: 'description', sortable: false }
                            ]
                        }
                    },
                    {
                        path: 'edit/:identity', component: () => import('@/frontends/iam/pages/AccessPolicyAddEdit.vue'), props: true
                    },
                    {
                        path: 'add', component: () => import('@/frontends/iam/pages/AccessPolicyAddEdit.vue')
                    }
                ]
            }
        ]
    }
]

export default iamRoutes
