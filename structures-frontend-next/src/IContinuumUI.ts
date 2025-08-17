import { createRouter, createWebHistory, type RouterOptions, type Router } from 'vue-router';
import { reactive } from 'vue';
import { StructuresStates } from './states';

// Interface for ContinuumUI
export interface IContinuumUI {
    initialize(routerOptions: Omit<RouterOptions, 'history'>): Router;
    navigate(path: string): Promise<void>;
}

// Class for ContinuumUI
class ContinuumUI implements IContinuumUI {

    private router!: Router;

    constructor() {}

    public initialize(routerOptions: Omit<RouterOptions, 'history'>): Router {
        this.router = createRouter({
            history: createWebHistory(),  // You can use createWebHashHistory() if needed
            ...routerOptions
        });
        this.router.beforeEach((to, _from, next) => {
            const { authenticationRequired } = to.meta as { authenticationRequired?: boolean };
        
            if ((authenticationRequired === undefined || authenticationRequired)
                && !StructuresStates.getUserState().isAuthenticated()) {
                
                next({ name: 'login', params: { referer: to.path } });
        
            } else {
                next();
            }
        });

        return this.router;
    }

    public navigate(path: string): any {
        return this.router.push(path);
    }
}

// Export a reactive singleton
export const CONTINUUM_UI: IContinuumUI = reactive(new ContinuumUI());