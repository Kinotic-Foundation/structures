import VueRouter, {NavigationGuardNext, Route, RouterOptions} from 'vue-router'
import {StructuresStates} from '@/frontends/states';
import {reactive} from 'vue';


export interface IContinuumUI {

    initialize(routerOptions: RouterOptions): VueRouter

    navigate(path: string): Promise<Route>

}

class ContinuumUI implements IContinuumUI {

    private router!: VueRouter

    constructor() {
    }

    public initialize(routerOptions: RouterOptions): VueRouter {
        this.router = new VueRouter(routerOptions)

        this.router.beforeEach((to: Route, from: Route, next: NavigationGuardNext<Vue>) => {
            // @ts-ignore
            const { authenticationRequired } = to.meta

            if ((authenticationRequired === undefined || authenticationRequired)
                    && !StructuresStates.getUserState().isAuthenticated()) {

                next({ name: 'login' , params:{referer: to.path}})
            } else {
                next()
            }
        })

        StructuresStates.getFrontendState().initialize(this.router)
        return this.router
    }

    public navigate(path: string): Promise<Route> {
        return this.router.push(path)
    }

}

export const CONTINUUM_UI: IContinuumUI = reactive(new ContinuumUI())
