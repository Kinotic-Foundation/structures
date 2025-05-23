import { createRouter, createWebHistory, type RouterOptions, type Router, type RouteLocationNormalized, type NavigationGuardNext, type NavigationFailure } from 'vue-router'
import { reactive } from 'vue'
import { StructuresStates } from './states'

export interface IContinuumUI {
  initialize(routerOptions: Omit<RouterOptions, 'history'>): Router
  navigate(path: string): Promise<void | NavigationFailure>
}

class ContinuumUI implements IContinuumUI {
  private router!: Router

  public initialize(routerOptions: Omit<RouterOptions, 'history'>): Router {
    this.router = createRouter({
      history: createWebHistory(),
      ...routerOptions
    })

    this.router.beforeEach((to: RouteLocationNormalized, from: RouteLocationNormalized, next: NavigationGuardNext) => {
      const { authenticationRequired } = to.meta as { authenticationRequired?: boolean }

      if ((authenticationRequired === undefined || authenticationRequired) &&
        !StructuresStates.getUserState().isAuthenticated()) {

        next({ name: 'login', params: { referer: to.path } })
      } else {
        next()
      }
    })

    if (StructuresStates.getFrontendState) {
      StructuresStates.getFrontendState().initialize(this.router)
    }

    return this.router
  }

  public navigate(path: string): Promise<void | NavigationFailure> {
    return this.router.push(path)
  }
}

export const CONTINUUM_UI: IContinuumUI = reactive(new ContinuumUI())
