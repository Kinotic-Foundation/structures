import { type RouteMeta, type RouteRecordRaw } from 'vue-router'

const pageRoutes: RouteRecordRaw[] = [
    {
      path: '/dashboard',
      component: () => import('@/layouts/MainLayout.vue'),
      name:"Dashboard",
      meta: {
        authenticationRequired: false,
        showInMainNav: true,
        icon: 'dashboard.svg',
        label: 'Dashboard',
      } as RouteMeta,
    },
    {
        path: '/applications',
        component: () => import('@/layouts/MainLayout.vue'),
        name:"Applications",
        meta: {
          authenticationRequired: false,
          showInMainNav: true,
          icon: 'microchip.svg',
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
      path: '/projects',
      component: () => import('@/layouts/MainLayout.vue'),
      meta: {
        authenticationRequired: false,
        showInMainNav: true,
        icon: 'folder.svg',
        label: 'Projects',
      } as RouteMeta,
    },
    {
      path: '/namespaces',
      component: () => import('@/layouts/MainLayout.vue'),
      meta: {
        authenticationRequired: false,
        showInMainNav: true,
        icon: 'objects-column.svg',
        label: 'Namespaces',
      } as RouteMeta,
    },
    {
      path: '/settings',
      component: () => import('@/layouts/MainLayout.vue'),
      meta: {
        authenticationRequired: false,
        showInMainNav: true,
        icon: 'settings.svg',
        label: 'Settings',
      } as RouteMeta,
    }
    // {
    //   path: '/login',
    //   component: () => import('@/pages/Login.vue'),
    //   meta: {
    //     authenticationRequired: false,
    //     showInMainNav: true,
    //     // icon: 'pi pi-sitemap',
    //     // label: 'Create Application',
    //   } as RouteMeta,
    // }
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