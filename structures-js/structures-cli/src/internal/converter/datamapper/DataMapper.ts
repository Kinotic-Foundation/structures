import {DataMapperConversionState} from './DataMapperConversionState.js'

export interface DataMapper {
    toStatementString(): string
}

export class MultiDataMapper implements DataMapper{

    private readonly _state: DataMapperConversionState
    private readonly _mappers: Array<DataMapper>

    constructor(state: DataMapperConversionState, mappers?: Array<DataMapper>) {
        this._state = state
        this._mappers = mappers || []
    }

    add(mapper: DataMapper): MultiDataMapper {
        this._mappers.push(new IndentingDataMapper(this._state.indent, mapper))
        return this
    }

    addLiteral(stringLiteral: string): MultiDataMapper {
        this.add(new StringLiteralDataMapper(stringLiteral))
        return this
    }

    toStatementString(): string {
        return this._mappers
                   .map(mapper => mapper.toStatementString())
                   .join('\n')
    }

}

export class IndentingDataMapper implements DataMapper {
    private readonly _indent: number
    private readonly _mapper: DataMapper

    constructor(indent: number, mapper: DataMapper) {
        this._indent = indent
        this._mapper = mapper
    }

    toStatementString(): string {
        return this.createIndent() + this._mapper.toStatementString()
    }

    private createIndent(): string {
        let ret = ''
        for (let i= 0; i < this._indent; i++) {
            ret += ' '
        }
        return ret
    }
}

export class AssignmentDataMapper implements DataMapper {

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

export class StringLiteralDataMapper implements DataMapper {

    private readonly _value: string

    constructor(value: string) {
        this._value = value
    }

    toStatementString(): string {
        return this._value
    }
}

