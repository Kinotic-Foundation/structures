export class DataMapperConversionState {

    public sourceName: string

    public targetName: string

    public indent: number = 4

    constructor(targetName: string, sourceName: string) {
        this.targetName = targetName
        this.sourceName = sourceName
    }

    public indentMore(): DataMapperConversionState {
        this.indent += 2
        return this
    }

    public indentLess(): DataMapperConversionState {
        this.indent -= 2
        return this
    }
}
