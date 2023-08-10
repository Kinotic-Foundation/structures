import {StatementMapperConversionState} from './StatementMapperConversionState.js'

export interface StatementMapper {

    toImportString(): string | null

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

    toImportString(): string | null {
        let ret = null
        for(const mapper of this._mappers){
            const importString = mapper.toImportString()
            if(importString != null){
                if(ret == null){
                    ret = ''
                }
                ret += importString + '\n'
            }
        }
        return ret
    }

    toStatementString(): string | null{
        let ret = null
        for(const mapper of this._mappers){
            const importString = mapper.toStatementString()
            if(importString != null){
                if(ret == null){
                    ret = ''
                }
                ret += importString + '\n'
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

    toImportString(): string | null {
        return this._mapper.toImportString()
    }

    toStatementString(): string | null {
        const out = this._mapper.toStatementString()
        return ' '.repeat(this._indent) + out
    }

}

export class LiteralStatementMapper implements StatementMapper {

    public statementString: string | null = null
    public importString: string | null = null

    constructor(statementString?: string | null, importString?: string | null) {
        if(statementString){
            this.statementString = statementString
        }
        if(importString){
            this.importString = importString
        }
    }

    toImportString(): string | null{
        return this.importString
    }

    toStatementString(): string | null {
        return this.statementString
    }
}

