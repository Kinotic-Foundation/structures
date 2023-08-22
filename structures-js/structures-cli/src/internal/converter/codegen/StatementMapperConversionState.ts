import {UtilFunctionLocator} from '../../UtilFunctionLocator.js'
import {BaseConversionState} from '../common/BaseConversionState.js'

export class StatementMapperConversionState extends BaseConversionState{

    public generatedServicePath: string

    public sourceName: string = 'entity'

    public targetName: string = 'ret'

    public indent: number = 4

    constructor(generatedServicePath: string,
                utilFunctionLocator: UtilFunctionLocator) {
        super(utilFunctionLocator, true)
        this.generatedServicePath = generatedServicePath
    }

    public indentMore(): StatementMapperConversionState {
        this.indent += 2
        return this
    }

    public indentLess(): StatementMapperConversionState {
        this.indent -= 2
        return this
    }
}
