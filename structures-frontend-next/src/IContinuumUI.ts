import { type Router } from 'vue-router';
import { reactive } from 'vue';
import { StructuresStates } from './states';

// Interface for ContinuumUI
export interface IContinuumUI {
    initialize(router: Router): void;
    navigate(path: string): Promise<any>;
}

// Class for ContinuumUI
class ContinuumUI implements IContinuumUI {

    private router!: Router;

    constructor() {}

    public initialize(router: Router): void {
        this.router = router;
        
        // Add navigation guard to the existing router
        this.router.beforeEach((to, _from, next) => {
            const { authenticationRequired } = to.meta as { authenticationRequired?: boolean };
        
            if ((authenticationRequired === undefined || authenticationRequired)
                && !StructuresStates.getUserState().isAuthenticated()) {
                
                next({ path: '/login', query: { referer: to.path } });
        
            } else {
                next();
            }
        });
    }

    public navigate(path: string): Promise<any> {
        console.log('CONTINUUM_UI.navigate called with path:', path);
        return this.router.push(path);
    }
}

// Export a reactive singleton
export const CONTINUUM_UI: IContinuumUI = reactive(new ContinuumUI());