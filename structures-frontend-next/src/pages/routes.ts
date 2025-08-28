
import { type RouteMeta, type RouteRecordRaw } from 'vue-router'

const pageRoutes: RouteRecordRaw[] = [
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
      icon: 'microchip.svg',
      sidebarItems: (route: { params: { applicationId: any } }) => [
        {
          label: 'Overview',
          icon: 'pi-objects-column',
          path: `/application/${route.params.applicationId}`
        },
        {
          label: 'Data Insights',
          icon: 'pi pi-chart-line',
          path: `/application/${route.params.applicationId}/data-insights`
        },
        {
          label: 'Authentication',
          icon: 'pi pi-key',
          path: `/application/${route.params.applicationId}/auth`
        },
        {
          label: 'Application settings',
          icon: 'pi pi-cog',
          path: `/application/${route.params.applicationId}/settings`
        }
      ]
    }  as RouteMeta,
    children: [
      {
        name: 'application-details',
        path: '',
        component: () => import('@/pages/ApplicationDetails.vue'),
        props: (route) => ({ applicationId: route.params.applicationId })
      },
      {
        name: 'data-insights',
        path: 'data-insights',
        component: () => import('@/pages/DataInsights.vue'),
        props: (route) => ({ applicationId: route.params.applicationId })
      },
      {
        name: 'application-settings',
        path: 'settings',
        component: () => import('@/pages/ApplicationSettings.vue'),
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
      sidebarItems: (route: { params: { applicationId: any; projectId: any; }; }) => [
        {
          label: 'Structures',
          icon: 'pi pi-table',
          path: `/application/${route.params.applicationId}/project/${route.params.projectId}/structures`
        },
        {
          label: 'Lambdas',
          icon: 'pi pi-key',
          path: `/application/${route.params.applicationId}/project/${route.params.projectId}/structures/sections`
        },
        {
          label: 'Project settings',
          icon: 'pi pi-cog',
          path: `/application/${route.params.applicationId}/project/${route.params.projectId}/structures/settings`
        }
      ]
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
    path: '/application-add',
    component: () => import('@/pages/ApplicationAddEdit.vue'),
    meta: {
      showInMainNav: false,
      icon: 'settings.svg',
      label: 'Add Application',
    } as RouteMeta,
  },
  {
    path: '/new-structure',
    component: () => import('@/pages/NewStructure.vue'),
    meta: {
      showInMainNav: false,
      label: 'New Structure',
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
    component: () => import('@/pages/login/Login.vue'),
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
