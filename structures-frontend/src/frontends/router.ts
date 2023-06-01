import Vue from 'vue'
import Router, {RouterOptions} from 'vue-router'
import {CONTINUUM_UI, IContinuumUI} from './continuum'
import continuumRoutes from './continuum/routes'
// import developRoutes from './develop/routes'
// import iamRoutes from './iam/routes'

import structuresAdminRoutes from './structures-admin/routes'

Vue.use(Router)

// FIXME: externalize this so that we can build against a specific frontend or many.
export const ConfiguredRouterOptions: RouterOptions = {
  routes: [
      { // This is where we have to configure the default route
            path: '/',
            redirect: '/namespaces'
      },
      ...continuumRoutes, // must be provided for 404 ect..

      ...structuresAdminRoutes,
      // ...developRoutes,
      // ...iamRoutes,

      { // Not found must be at end
          path: '*',
          redirect: '/404'
      }
  ]
}


export const router = CONTINUUM_UI.initialize(ConfiguredRouterOptions)
