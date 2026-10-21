import {AnyC3Type, C3Type, DateC3Type, MapC3Type, StringC3Type} from '@kinotic-ai/idl'
import {Type} from 'ts-morph'
import {IConversionContext} from '@/internal/converter/IConversionContext'
import {ITypeConverter} from '@/internal/converter/ITypeConverter'
import {Logger} from '@/internal/Logger'
import {TypescriptConversionState} from './TypescriptConversionState'

// Types JSON.stringify writes as {} or cannot write at all, by the name of their symbol
const NOT_JSON = new Map<string, string>([
    ['Map', 'a Map is written as {}; declare a Record or an array'],
    ['WeakMap', 'a WeakMap is written as {}; declare a Record or an array'],
    ['Set', 'a Set is written as {}; declare an array'],
    ['WeakSet', 'a WeakSet is written as {}; declare an array'],
    ['ArrayBuffer', 'binary data is written as an object of its bytes; declare a base64 string'],
    ['Uint8Array', 'binary data is written as an object of its bytes; declare a base64 string'],
    ['Buffer', 'binary data is written as an object of its bytes; declare a base64 string'],
])

/**
 * Converts the types a service's parameters and return values carry as JSON travels them, ahead of the converters
 * an entity's properties use: anything unconstrained to any, a type parameter to its constraint, a string-keyed
 * record to a map, and a Date to a date, logging and adding to the conversion's warnings that it arrives as the
 * ISO-8601 string a date travels as. A type JSON cannot carry is refused.
 */
export class ServiceValueToC3Type implements ITypeConverter<Type, C3Type, TypescriptConversionState> {

    private readonly logger: Logger

    constructor(logger: Logger) {
        this.logger = logger
    }

    convert(value: Type, conversionContext: IConversionContext<Type, C3Type, TypescriptConversionState>): C3Type {
        let ret: C3Type
        if (value.isAny() || value.isUnknown()) {
            ret = new AnyC3Type()
        } else if (value.isTypeParameter()) {
            const constraint = value.getConstraint()
            ret = constraint ? conversionContext.convert(constraint) : new AnyC3Type()
        } else if (this.isDate(value)) {
            // the conversion names the service first and the function or parameter last
            const state = conversionContext.state()
            const path = [state.objectNameStack[0], state.unionPropertyNameStack[0], conversionContext.currentJsonPath]
            const warning = `A Date in ${path.filter(part => part).join('.')} arrives as a string: JSON carries a date`
                            + ' as an ISO-8601 string, so the service receives one and a TypeScript caller gets one back, never a Date'
            this.logger.warn(warning)
            state.warnings.push(warning)
            ret = new DateC3Type()
        } else if (this.isRecord(value)) {
            const map = new MapC3Type()
            map.key = new StringC3Type()
            map.value = conversionContext.convert(value.getStringIndexType() as Type)
            ret = map
        } else {
            throw new Error(`${value.getText()} cannot travel as JSON: ${this.whyNotJson(value)}`)
        }
        return ret
    }

    supports(value: Type): boolean {
        return value.isAny()
            || value.isUnknown()
            || value.isTypeParameter()
            || this.isDate(value)
            || this.isRecord(value)
            || this.whyNotJson(value) !== null
    }

    private isDate(value: Type): boolean {
        return value.getSymbol()?.getName() === 'Date' && value.getProperties().some(p => p.getName() === 'toISOString')
    }

    // An object whose only members are a string index signature, as Record<string, V> and {[key: string]: V} are
    private isRecord(value: Type): boolean {
        return value.isObject() && !value.isArray() && value.getStringIndexType() !== undefined && value.getProperties().length === 0
    }

    private whyNotJson(value: Type): string | null {
        let ret: string | null = null
        const symbolName = value.getSymbol()?.getName()
        if (value.isBigInt() || value.isBigIntLiteral()) {
            ret = 'JSON.stringify refuses a bigint; declare a number or a string'
        } else if (value.isTuple()) {
            ret = 'a tuple has no C3 type; declare an array or an object'
        } else if (value.getCallSignatures().length > 0 && value.getProperties().length === 0) {
            ret = 'a function is left out of JSON; a caller cannot send or receive one'
        } else if (symbolName && NOT_JSON.has(symbolName)) {
            ret = NOT_JSON.get(symbolName) as string
        }
        return ret
    }
}
