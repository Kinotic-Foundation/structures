import VueRouter, {NavigationGuardNext, Route, RouterOptions} from 'vue-router'
import {container, inject, injectable} from 'inversify-props'
import {IFrontendState, IUserState} from "./states";

export interface IContinuumUI {

    initialize(routerOptions: RouterOptions): VueRouter

    navigate(path: string): Promise<Route>

}

@injectable()
class ContinuumUI implements IContinuumUI{

    @inject()
    private frontendState!: IFrontendState

    @inject()
    private userState!: IUserState

    private router!: VueRouter

    constructor() {
    }

    initialize(routerOptions: RouterOptions): VueRouter {
        this.router = new VueRouter(routerOptions)

        this.router.beforeEach((to: Route, from: Route, next: NavigationGuardNext<Vue>) => {
            // @ts-ignore
            let { authenticationRequired } = to.meta

            if ((authenticationRequired === undefined || authenticationRequired)
                    && !this.userState.isAuthenticated()){

                next({ path: '/login' })
            } else {
                next()
            }
        })

        this.frontendState.initialize(this.router)
        return this.router
    }

    navigate(path: string): Promise<Route> {
        return this.router.push(path)
    }

}

container.addSingleton<IContinuumUI>(ContinuumUI)