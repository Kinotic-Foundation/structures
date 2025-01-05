import { reactive, markRaw } from 'vue'
import type {
    Router,
    RouteRecordRaw,
    NavigationGuardNext,
    RouteLocationNormalizedLoaded,
    RouteLocationNormalized
} from 'vue-router'


export interface IFrontendState {

    /**
     * Frontends are Routes defined in the route.js with a meta property if isFrontend: true
     */
    frontends: RouteRecordRaw[]

    /**
     * The selected "Frontend"
     */
    selectedFrontend: RouteRecordRaw

    /**
     * Must be called with a configured VueRouter
     * @param router to initialize ths with
     */
    initialize(router: Router): void
}


/**
 * Base functionality for FrontendLayout and navigation functionality
 */
class FrontendState implements IFrontendState {

    public frontends: RouteRecordRaw[] = markRaw([])

    public selectedFrontend: RouteRecordRaw = {path: '', children: []}

    public initialize(router: Router): void {
        // initialize the store with the router configuration
        // Get all routes defined as "Frontends"
        if (router.options.routes !== undefined) {
            // this logic will only work if all frontends have an unique root path with no parameters or regex
            // for now that is what we will require
            const activeFrontend: RouteRecordRaw | null = this.resolveFrontendRecord(router.currentRoute.value)

            router.options.routes.forEach((route: RouteRecordRaw) => {
                if (this.isFrontend(route)) {

                    this.frontends.push(route)

                    if (activeFrontend != null && activeFrontend.path === route.path) {
                        this.selectedFrontend = route
                    }
                }
            })
        }

        router.beforeResolve((to: RouteLocationNormalized, _: RouteLocationNormalized, next: NavigationGuardNext) => {
            const frontend: RouteRecordRaw | null = this.resolveFrontendConfig(to)
            if (frontend !== null) {
                this.selectedFrontend = frontend
            }
            next()
        })
    }

    private resolveFrontendConfig(route: RouteLocationNormalized): RouteRecordRaw | null {
        let ret: RouteRecordRaw | null = null
        const frontendRecord: RouteRecordRaw | null = this.resolveFrontendRecord(route)
        if (frontendRecord != null) {
            for (const routeConfig of this.frontends) {
                if (routeConfig.path === frontendRecord.path) {
                    ret = routeConfig
                    break
                }
            }
        }
        return ret
    }

    private resolveFrontendRecord(route: RouteLocationNormalizedLoaded): RouteRecordRaw | null {
        let ret: RouteRecordRaw | null = null
        if (route.matched.length > 0 && this.isFrontend(route.matched[0])) {
            ret = route.matched[0]
        }
        return ret
    }

    private isFrontend(routeConfig: RouteRecordRaw): boolean {
        return typeof (routeConfig.meta) !== 'undefined'
                    && typeof (routeConfig.meta.isFrontend) !== 'undefined'
                    && routeConfig.meta.isFrontend as boolean
    }

}

export const FRONTEND_STATE: IFrontendState = reactive(new FrontendState())

