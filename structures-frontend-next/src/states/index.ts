import {FRONTEND_STATE, type IFrontendState} from './IFrontendState'
import {USER_STATE, type IUserState} from './IUserState'

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
