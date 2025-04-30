import { type RouteMeta, type RouteRecordRaw } from 'vue-router'

const pageRoutes: RouteRecordRaw[] = [
    {
      path: '/dashboard',
      component: () => import('@/layouts/MainLayout.vue'),
      name: "Dashboard",
      meta: {
        authenticationRequired: true,
        showInMainNav: true,
        icon: 'dashboard.svg',
        label: 'Dashboard',
      } as RouteMeta,
      children: [
        {
          path: '',
          component: () => import('@/pages/Test.vue'),
          meta: { authenticationRequired: false }
        },
      ]
    },
    {
      path: '/applications',
      component: () => import('@/layouts/MainLayout.vue'),
      name: "Applications",
      meta: {
        authenticationRequired: true,
        showInMainNav: true,
        icon: 'microchip.svg',
        label: 'Applications',
      } as RouteMeta,
      children: [
        {
          path: '',
          component: () => import('@/pages/NamespaceList.vue'),
          meta: { authenticationRequired: false }
        },
        {
          path: 'list',
          name: "List",
          component: () => import('@/pages/Test.vue'),
          meta: {
            authenticationRequired: true,
            showInMainNav: true,
            icon: 'pi pi-list',
            label: 'List',
          } as RouteMeta,
        },
        {
          path: 'test3',
          component: () => import('@/pages/Test2.vue'),
          meta: {
            authenticationRequired: true,
            showInMainNav: true,
            icon: 'fa-sitemap',
            label: 'Test Two',
          } as RouteMeta,
        },
      ]
    },
    {
      path: '/projects',
      component: () => import('@/layouts/MainLayout.vue'),
      meta: {
        authenticationRequired: true,
        showInMainNav: true,
        icon: 'folder.svg',
        label: 'Projects',
      } as RouteMeta,
    },
    {
      path: '/namespaces',
      component: () => import('@/layouts/MainLayout.vue'),
      meta: {
        authenticationRequired: true,
        showInMainNav: true,
        icon: 'objects-column.svg',
        label: 'Namespaces',
      } as RouteMeta,
    },
    {
      path: '/application-add',
      component: () => import('@/pages/NamespaceAddEdit.vue'),
      meta: {
        authenticationRequired: true,
        showInMainNav: false,
        icon: 'settings.svg',
        label: 'Add Application',
      } as RouteMeta,
    },
    {
      path: '/users',
      component: () => import('@/layouts/MainLayout.vue'),
      meta: {
        authenticationRequired: true,
        showInMainNav: true,
        icon: 'icon-man.svg',
        label: 'Users',
      } as RouteMeta,
      children: [
        {
          path: '',
          component: () => import('@/pages/Users.vue'),
          meta: { authenticationRequired: false }
        },
        
      ]
    },
    {
      path: '/settings',
      component: () => import('@/layouts/MainLayout.vue'),
      meta: {
        authenticationRequired: true,
        showInMainNav: true,
        icon: 'settings.svg',
        label: 'Settings',
      } as RouteMeta,
    },
    {
      path: '/login',
      component: () => import('@/pages/Login.vue'),
      meta: {
        authenticationRequired: false,
        showInMainNav: false,
      } as RouteMeta,
    },
];

export default pageRoutes