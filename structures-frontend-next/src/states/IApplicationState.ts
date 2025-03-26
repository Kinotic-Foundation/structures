import {NavItem} from '@/components/NavItem.js'
import {markRaw, shallowReactive} from 'vue'
import type {
    Router,
    RouteRecordRaw,
    NavigationGuardNext,
    RouteLocationNormalizedLoaded,
    RouteLocationNormalized
} from 'vue-router'


export interface IApplicationState {

    /**
     * List of Navigation Items for the main navigation
     */
    mainNavItems: NavItem[]

    /**
     * The selected Navigation Item
     */
    selectedNavItem: NavItem | null

    /**
     * Must be called with a configured VueRouter
     * @param router to initialize ths with
     */
    initialize(router: Router): void
}


/**
 * Base functionality for FrontendLayout and navigation functionality
 */
class ApplicationState implements IApplicationState {

    public mainNavItems: NavItem[] = markRaw([])

    public selectedNavItem: NavItem | null = null

    public initialize(router: Router): void {
        // initialize the store with the router configuration
        // Get all routes defined as
        if (router.options.routes !== undefined) {

            router.options.routes.forEach((route: RouteRecordRaw) => {
                if (this.showInMainNav(route)) {

                    this.mainNavItems.push(new NavItem())

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

    private createNavItem(route: RouteRecordRaw): NavItem {
        const navItem = new NavItem(route.meta?.icon as string || '',
                                    route.meta?.label as string || '',
                                    async () => {

                                    })
    }

    private showInMainNav(routeConfig: RouteRecordRaw): boolean {
        return routeConfig?.meta?.showInMainNav === true
    }

}

export const APPLICATION_STATE: IApplicationState = shallowReactive(new ApplicationState())

