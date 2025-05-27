import {APPLICATION_STATE, type IApplicationState} from './IApplicationState.js'
import {USER_STATE, type IUserState} from './IUserState'

export namespace StructuresStates {

    export function getApplicationState(): IApplicationState {
        return APPLICATION_STATE
    }

    export function getUserState(): IUserState {
        return USER_STATE
    }

}
