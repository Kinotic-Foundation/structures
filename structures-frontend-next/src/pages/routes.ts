import { type RouteMeta, type RouteRecordRaw } from 'vue-router'

const pageRoutes: RouteRecordRaw[] = [
  {
    path: '/dashboard',
    component: () => import('@/layouts/MainLayout.vue'),
    meta: {
      showInMainNav: true,
      icon: 'dashboard.svg',
      label: 'Dashboard',
    } as RouteMeta,
    children: [
      {
        name: "dashboard",
        path: '',
        component: () => import('@/pages/Test.vue'),
      },
    ]
  },
  {
    path: '/applications',
    component: () => import('@/layouts/MainLayout.vue'),
    meta: {
      showInMainNav: true,
      icon: 'microchip.svg',
      label: 'Applications',
    } as RouteMeta,
    children: [
      {
        name: "applications",
        path: '',
        component: () => import('@/pages/ApplicationList.vue'),
      },
    ]
  },

  {
    path: '/application/:applicationId',
    component: () => import('@/layouts/LayoutForPage.vue'),
    meta: {
      showInMainNav: false,
      label: 'Application Details',
      icon: 'microchip.svg'
    } as RouteMeta,
    children: [
      {
        name: 'application-details',
        path: '',
        component: () => import('@/pages/ApplicationDetails.vue'),
        props: (route) => ({ applicationId: route.params.applicationId })
      }
    ]
  },
  {
    path: '/projects',
    component: () => import('@/layouts/MainLayout.vue'),
    meta: {
      showInMainNav: true,
      icon: 'folder.svg',
      label: 'Projects',
    } as RouteMeta,
  },
  {
    path: '/application/:applicationId/project/:projectId/structures',
    name: 'project-structures-wrapper',
    component: () => import('@/layouts/LayoutForPage.vue'),
    meta: {
      showInMainNav: false,
      icon: 'objects-column.svg',
      label: 'Project Structures',
    } as RouteMeta,
    children: [
      {
        name: 'project-structures',
        path: '',
        component: () => import('@/pages/ProjectStructuresPage.vue'),
        props: (route) => ({
          applicationId: route.params.applicationId,
          projectId: route.params.projectId,
        }),
      },
    ],
  },
  {
    path: '/structures',
    component: () => import('@/layouts/MainLayout.vue'),
    meta: {
      showInMainNav: true,
      icon: 'objects-column.svg',
      label: 'Structures',
    } as RouteMeta,
    children: [
      {
        name: "structures",
        path: '',
        component: () => import('@/pages/structures/StructuresList.vue'),
      }
    ]
  },
  {
    path: '/entity/:id',
    component: () => import('@/layouts/MainLayout.vue'),
    meta: {
      showInMainNav: false,
      icon: 'objects-column.svg',
      label: 'Structure Details',
    } as RouteMeta,
    children: [
      {
        path: '',
        component: () => import('@/pages/structures/entity/EntityList.vue'),
      }
    ]
  },
  {
    path: '/application-add',
    component: () => import('@/pages/ApplicationAddEdit.vue'),
    meta: {
      showInMainNav: false,
      icon: 'settings.svg',
      label: 'Add Application',
    } as RouteMeta,
  },
  {
    path: '/users',
    component: () => import('@/layouts/MainLayout.vue'),
    meta: {
      showInMainNav: true,
      icon: 'icon-man.svg',
      label: 'Users',
    } as RouteMeta,
    children: [
      {
        path: '',
        component: () => import('@/pages/Users.vue'),
      },

    ]
  },
  {
    path: '/settings',
    component: () => import('@/layouts/MainLayout.vue'),
    meta: {
      showInMainNav: true,
      icon: 'settings.svg',
      label: 'Settings',
    } as RouteMeta,
  },
  {
    path: '/login',
    component: () => import('@/pages/Login.vue'),
    meta: {
      showInMainNav: false,
      authenticationRequired: false
    } as RouteMeta,
  },
  {
    path: '/graphql',
    component: () => import('@/layouts/MainLayout.vue'),
    meta: {
      showInMainNav: false,
      icon: 'objects-column.svg',
      label: 'GraphQLPlayground',
    } as RouteMeta,
    children: [
      {
        name: "GraphQLPlayground",
        path: '',
        component: () => import('@/pages/GraphQLPlayground.vue'),
      }
    ]
  },
];

export default pageRoutes