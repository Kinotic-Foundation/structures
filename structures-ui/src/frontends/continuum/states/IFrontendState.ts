import { container, injectable } from 'inversify-props'
import { reactive, markRaw } from '@vue/composition-api'
import VueRouter, { RouteConfig, Route, RouteRecord, NavigationGuardNext} from 'vue-router'

export interface IFrontendState {

    /**
     * If the side drawer is open or not
     */
    drawerOpen: boolean

    /**
     * Frontends are Routes defined in the route.js with a meta property if isFrontend: true
     */
    frontends: RouteConfig[]

    /**
     * The selected "Frontend"
     */
    selectedFrontend: RouteConfig

    /**
     * Must be called with a configured VueRouter
     * @param router to initialize ths with
     */
    initialize(router: VueRouter): void


}


/**
 * Base functionality for FrontendLayout and navigation functionality
 */
@injectable()
class FrontendState implements IFrontendState {

    public drawerOpen: boolean = true

    public frontends: RouteConfig[] = markRaw([])

    public selectedFrontend: RouteConfig = {path: '', children: []}

    constructor() {
    }

    public initialize(router: VueRouter): void {
        // initialize the store with the router configuration
        // Get all routes defined as "Frontends"
        if (router.options.routes !== undefined) {
            // this logic will only work if all frontends have an unique root path with no parameters or regex
            // for now that is what we will require
            const activeFrontend: RouteRecord | null = this.resolveFrontendRecord(router.currentRoute)

            router.options.routes.forEach((route: RouteConfig) => {
                if (this.isFrontend(route)) {

                    this.frontends.push(route)

                    if (activeFrontend != null && activeFrontend.path === route.path) {
                        this.selectedFrontend = route
                    }
                }
            })
        }

        router.beforeResolve((to: Route, from: Route, next: NavigationGuardNext) => {
            const frontend: RouteConfig | null = this.resolveFrontendConfig(to)
            if (frontend !== null) {
                this.selectedFrontend = frontend
            }
            next()
        })
    }

    private resolveFrontendConfig(route: Route): RouteConfig | null {
        let ret: RouteConfig | null = null
        const frontendRecord: RouteRecord | null = this.resolveFrontendRecord(route)
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

    private resolveFrontendRecord(route: Route): RouteRecord | null {
        let ret: RouteRecord | null = null
        if (route.matched.length > 0 && this.isFrontend(route.matched[0])) {
            ret = route.matched[0]
        }
        return ret
    }

    private isFrontend(routeConfig: RouteConfig | Route | RouteRecord): boolean {
        return typeof (routeConfig.meta) !== 'undefined'
                    && typeof (routeConfig.meta.isFrontend) !== 'undefined'
                    && routeConfig.meta.isFrontend
    }

}


container.addSingleton<IFrontendState>(FrontendState).onActivation((context, frontendState) => {
    // This line is what does the magic and makes the class a "state" object that vue can react too
    return reactive<IFrontendState>(frontendState) as IFrontendState
})

