import {UtilFunctionLocator} from '../../UtilFunctionLocator.js'
import {BaseConversionState} from '../common/BaseConversionState.js'

/**
 * The state of the Typescript to C3Type conversion process.
 */
export class TypescriptConversionState extends BaseConversionState{

    private readonly _namespace: string

    /**
     * Contains a stack of object names for objects being processed.
     */
    public objectNameStack: string[] = []
    /**
     * Contains a stack of property names for any properties processed that are a union type.
     */
    public unionPropertyNameStack: string[] = []
    /**
     * Boolean to indicate if the source path should be added to the metadata for the {@link C3Type}s created.
     */
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
