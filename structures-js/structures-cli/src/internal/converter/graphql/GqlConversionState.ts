import {BaseConversionState} from '../common/BaseConversionState.js'

export class GqlConversionState extends BaseConversionState{

    constructor(namespace: string) {
        super(namespace, null)
    }
}
