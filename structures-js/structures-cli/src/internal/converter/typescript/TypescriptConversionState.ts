import {UtilFunctionLocator} from '../../UtilFunctionLocator.js'
import {BaseConversionState} from '../common/BaseConversionState.js'

/**
 * The state of the Typescript to C3Type conversion process.
 */
export class TypescriptConversionState extends BaseConversionState{

    private readonly _namespace: string

    public unionPropertyNameStack: string[] = []
    public shouldAddSourcePathToMetadata: boolean = true

    constructor(namespace: string,
                utilFunctionLocator: UtilFunctionLocator | null) {
        super(utilFunctionLocator)
        this._namespace = namespace
    }

    get namespace(): string {
        return this._namespace
    }

}
