import type { NavigationGuardNext, RouteLocationNormalized, Router } from 'vue-router'
import type { App, Plugin } from 'vue'
import {StructuresStates} from '@/states'

export function createStructuresUI(): Plugin {
    return {
        install(_: App, options: {router: Router}) {
            options.router.beforeEach((to: RouteLocationNormalized, _: RouteLocationNormalized, next: NavigationGuardNext) => {

                const { authenticationRequired } = to.meta

                if ((authenticationRequired === undefined || authenticationRequired)
                    && !StructuresStates.getUserState().isAuthenticated()){
                    next({ path: '/login' })
                } else {
                    next()
                }
            })

            StructuresStates.getFrontendState().initialize(options.router)
        }
    }
}
