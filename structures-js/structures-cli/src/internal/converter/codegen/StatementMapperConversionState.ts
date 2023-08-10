import {UtilFunctionLocator} from '../../UtilFunctionLocator.js'
import {BaseConversionState} from '../common/BaseConversionState.js'

export class StatementMapperConversionState extends BaseConversionState{

    public generatedServicePath: string

    public sourceName: string

    public targetName: string

    public indent: number = 4

    constructor(generatedServicePath: string,
                targetName: string,
                sourceName: string,
                utilFunctionLocator: UtilFunctionLocator) {
        super(utilFunctionLocator, true)
        this.generatedServicePath = generatedServicePath
        this.targetName = targetName
        this.sourceName = sourceName
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
