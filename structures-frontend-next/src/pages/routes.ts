import { type RouteMeta, type RouteRecordRaw } from 'vue-router'

const pageRoutes: RouteRecordRaw[] = [
    {
        path: '/applications',
        component: () => import('@/layouts/MainLayout.vue'),
        name:"Applications",
        meta: {
          authenticationRequired: false,
          showInMainNav: true,
          icon: 'pi pi-sitemap',
          label: 'Applications',
        } as RouteMeta,
        children: [
            {
                path: '',
                component: () => import('@/pages/NamespaceList.vue'),
                meta: {
                } as RouteMeta,
            },
            {
                path: 'list',
                name:"List",
                meta: {
                  showInMainNav: true,
                  icon: 'pi pi-list',
                  label: 'List',
                } as RouteMeta,
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
    {
        path: '/application-add',
        component: () => import('@/pages/NamespaceAddEdit.vue'),
        // name:"Add",
        meta: {
          authenticationRequired: false,
          showInMainNav: true,
          icon: 'pi pi-sitemap',
          label: 'Create Application',
        } as RouteMeta,
    }
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