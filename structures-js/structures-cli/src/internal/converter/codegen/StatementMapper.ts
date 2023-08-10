import {StatementMapperConversionState} from './StatementMapperConversionState.js'

export interface NeededImport {
    importName: string
    importPath: string
    defaultExport?: boolean
}

export interface StatementMapper {

    getNeededImports(): NeededImport[]

    toStatementString(): string | null
}

export class MultiStatementMapper implements StatementMapper{

    private readonly _state: StatementMapperConversionState
    private readonly _mappers: Array<StatementMapper>

    constructor(state: StatementMapperConversionState, mappers?: Array<StatementMapper>) {
        this._state = state
        this._mappers = mappers || []
    }

    add(mapper: StatementMapper): MultiStatementMapper {
        if(mapper instanceof MultiStatementMapper){
            this._mappers.push(mapper)
        }else{
            this._mappers.push(new IndentingStatementMapper(this._state.indent, mapper))
        }
        return this
    }

    addLiteral(stringLiteral: string): MultiStatementMapper {
        this.add(new LiteralStatementMapper(stringLiteral))
        return this
    }

    getNeededImports(): NeededImport[] {
        return this._mappers.map(mapper => mapper.getNeededImports()).flat()
    }

    toStatementString(): string | null{
        let ret = null
        for(const mapper of this._mappers){
            const statementString = mapper.toStatementString()
            if(statementString != null){
                if(ret == null){
                    ret = ''
                }
                ret += statementString + (statementString.endsWith('\n') ? '' : '\n')
            }
        }
        return ret
    }
}

export class IndentingStatementMapper implements StatementMapper {
    private readonly _indent: number
    private readonly _mapper: StatementMapper

    constructor(indent: number, mapper: StatementMapper) {
        this._indent = indent
        this._mapper = mapper
    }

    getNeededImports(): NeededImport[] {
        return this._mapper.getNeededImports()
    }

    toStatementString(): string | null {
        const out = this._mapper.toStatementString()
        return ' '.repeat(this._indent) + out
    }

}

export class LiteralStatementMapper implements StatementMapper {

    public statementString: string | null = null
    public neededImports: NeededImport[] = []

    constructor(statementString?: string | null, neededImports?: NeededImport[]) {
        if(statementString){
            this.statementString = statementString
        }
        if(neededImports){
            this.neededImports = neededImports
        }
    }

    getNeededImports(): NeededImport[] {
        return this.neededImports
    }

    toStatementString(): string | null {
        return this.statementString
    }
}

export function createImportString(statementMapper: StatementMapper): string | null{
    const importsWithoutDuplicates = removeDuplicateImports(statementMapper.getNeededImports())
    // Now make sure all imports for the same paths are grouped together in the import string
    const importMap: { [key: string]: NeededImport[] } = {}
    for (const imp of importsWithoutDuplicates) {
        if (importMap[imp.importPath]) {
            importMap[imp.importPath].push(imp)
        } else {
            importMap[imp.importPath] = [imp]
        }
    }
    let importString = ''
    for (const importPath of Object.keys(importMap)) {
        const imports = importMap[importPath]
        const defaultImports = imports.filter(imp => imp.defaultExport)
        const namedImports = imports.filter(imp => !imp.defaultExport)
        if (defaultImports.length > 0) {
            if(defaultImports.length > 1){
                throw new Error(`Multiple default exports for import path ${importPath}`)
            }
            importString += `import ${defaultImports[0].importName} from '${importPath}'\n`
        }
        if (namedImports.length > 0) {
            importString += `import {${namedImports.map(imp => imp.importName).join(',\n')}} from '${importPath}'\n`
        }
    }
    return importString.length > 0 ? importString : null
}

function removeDuplicateImports(imports: NeededImport[]): NeededImport[] {
    const ret: NeededImport[] = []
    const importMap: { [key: string]: NeededImport } = {}
    for (const imp of imports) {
        if (importMap[imp.importName]) {
            const existing = importMap[imp.importName]
            if (existing.importPath !== imp.importPath) {
                throw new Error(`Import name ${imp.importName} is used for multiple imports: ${existing.importPath} and ${imp.importPath}`)
            }
            if (imp.defaultExport) {
                importMap[imp.importName].defaultExport = true
            }
        } else {
            importMap[imp.importName] = imp
            ret.push(imp)
        }
    }
    return ret
}
