import {FRONTEND_STATE, type IFrontendState} from '@/states/IFrontendState'
import {USER_STATE, type IUserState} from '@/states/IUserState'

export namespace StructuresStates {

    export function getFrontendState(): IFrontendState {
        return FRONTEND_STATE
    }

    export function getUserState(): IUserState {
        return USER_STATE
    }

}
