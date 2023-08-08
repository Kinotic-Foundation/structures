export class StatementMapperConversionState {

    public sourceName: string

    public targetName: string

    public indent: number = 4

    constructor(targetName: string, sourceName: string) {
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
