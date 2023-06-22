// import all states here

import {FRONTEND_STATE, IFrontendState, USER_STATE, IUserState} from '@/frontends/continuum'


/**
 * Export all states
 */
export namespace StructuresStates {

    export function getFrontendState(): IFrontendState {
        return FRONTEND_STATE
    }

    export function getUserState(): IUserState {
        return USER_STATE
    }

}
