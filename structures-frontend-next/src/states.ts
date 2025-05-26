import {USER_STATE, type IUserState} from '@/states/IUserState'

export namespace StructuresStates {

    export function getUserState(): IUserState {
        return USER_STATE
    }

}
