import {StatementMapperConversionState} from './StatementMapperConversionState'

export interface StatementMapper {
    toStatementString(): string
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
        this.add(new StringLiteralStatementMapper(stringLiteral))
        return this
    }

    toStatementString(): string {
        return this._mappers
                   .map(mapper => mapper.toStatementString())
                   .join('\n')
    }

}

export class IndentingStatementMapper implements StatementMapper {
    private readonly _indent: number
    private readonly _mapper: StatementMapper

    constructor(indent: number, mapper: StatementMapper) {
        this._indent = indent
        this._mapper = mapper
    }

    toStatementString(): string {
        const out = this._mapper.toStatementString()
        return ' '.repeat(this._indent) + out
    }

}

export class AssignmentStatementMapper implements StatementMapper {

    private readonly _target: string
    private readonly _value: string

    constructor(target: string, value: string) {
        this._target = target
        this._value = value
    }

    toStatementString(): string {
        return `${this._target} = ${this._value}`
    }
}

export class StringLiteralStatementMapper implements StatementMapper {

    private readonly _value: string

    constructor(value: string) {
        this._value = value
    }

    toStatementString(): string {
        return this._value
    }
}

