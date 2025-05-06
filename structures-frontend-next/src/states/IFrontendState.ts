import { reactive, markRaw } from 'vue';
import type { Router, RouteLocationNormalized, RouteRecordNormalized, NavigationGuardNext } from 'vue-router';

export interface IFrontendState {
  drawerOpen: boolean;
  frontends: RouteRecordNormalized[];
  selectedFrontend: RouteRecordNormalized | null;
  initialize(router: Router): void;
}

export class FrontendState implements IFrontendState {

  public drawerOpen = true;

  public frontends: RouteRecordNormalized[] = markRaw([]);

  public selectedFrontend: RouteRecordNormalized | null = null;

  constructor() {}

  public initialize(router: Router): void {
    if (router.options.routes !== undefined) {
      const currentRoute = router.currentRoute?.value;
      
      let activeFrontend: RouteRecordNormalized | null = null;
      if (currentRoute && currentRoute.matched && currentRoute.matched.length > 0) {
        activeFrontend = this.resolveFrontendRecord(currentRoute);
      }
  
      router.options.routes.forEach((route: any) => {
        if (this.isFrontend(route)) {
          this.frontends.push(route);
  
          if (activeFrontend && activeFrontend.path === route.path) {
            this.selectedFrontend = route;
          }
        }
      });
    }
  
    router.beforeResolve((to: RouteLocationNormalized, from: RouteLocationNormalized, next: NavigationGuardNext) => {
      const frontend = this.resolveFrontendRecord(to);
      if (frontend !== null) {
        this.selectedFrontend = frontend;
      }
      next();
    });
  }
  

  private resolveFrontendRecord(route: RouteLocationNormalized): RouteRecordNormalized | null {
    let ret: RouteRecordNormalized | null = null;
    if (route.matched.length > 0 && this.isFrontend(route.matched[0])) {
      ret = route.matched[0];
    }
    return ret;
  }

  private isFrontend(route: Partial<RouteRecordNormalized>): boolean {
    return !!(route.meta && route.meta.isFrontend);
  }
}

export const FRONTEND_STATE: IFrontendState = reactive(new FrontendState());
