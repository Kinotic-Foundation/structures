import {C3Type} from '@kinotic/continuum-idl'
import {IConversionContext} from './IConversionContext.js'
import {IConverterStrategy, Logger} from './IConverterStrategy.js'
import {ITypeConverter} from './ITypeConverter.js'

/**
 * Created by Navíd Mitchell 🤪 on 4/26/23.
 */
export class DefaultConversionContext<BASE_TYPE, S> implements IConversionContext<BASE_TYPE, S> {

    private readonly strategy: IConverterStrategy<BASE_TYPE, S>
    private readonly conversionDepthStack: Array<BASE_TYPE> = []
    private readonly errorStack: Array<BASE_TYPE> = []
    private readonly _state: S
    private readonly logger: Logger
    private _propertyStack: string[] = []
    public currentJsonPath: string = ''

    constructor(strategy: IConverterStrategy<BASE_TYPE, S>) {
        this.strategy = strategy
        let state = strategy.initialState()
        if(state instanceof Function){
            this._state = state()
        }else{
            this._state = state
        }
        let logger = strategy.logger()
        if(logger instanceof Function){
            this.logger = logger()
        }else{
            this.logger = logger
        }
    }

    public beginProcessingProperty(name: string): void {
        this.currentJsonPath = (this._propertyStack.length > 0  ? this._propertyStack[this._propertyStack.length - 1] + '.' : '') + name
        this._propertyStack.push(this.currentJsonPath)
    }

    public endProcessingProperty(): void {
        this._propertyStack.pop()
        this.currentJsonPath = this._propertyStack.length > 0 ? this._propertyStack[this._propertyStack.length - 1] : ''
    }

    public convert(value: BASE_TYPE): C3Type {
        try {

            this.conversionDepthStack.unshift(value)

            let converter = this.selectConverter(value)
            if (converter != null) {

                return converter.convert(value, this)

            }else{
                // this causes the stack to unwind, so this is intentional
                // noinspection ExceptionCaughtLocallyJS
                throw new Error('No ITypeConverter can be found for ' + this.strategy.valueToString(value) + '\nWhen using strategy ' + this.strategy.constructor.name)
            }
        } catch (e: any) {
            this.logException(e)
            throw e
        } finally {
            this.conversionDepthStack.shift()
        }
    }

    public state(): S {
        return this._state
    }

    private selectConverter(value: BASE_TYPE): ITypeConverter<BASE_TYPE, any, S> | null{
        let ret: ITypeConverter<BASE_TYPE, any, S> | null = null
        for (let converter of this.strategy.typeConverters()) {
            if(converter.supports(value, this.state())){
                ret = converter
                break
            }
        }
        return ret
    }

    /**
     * Log an exception when appropriate dealing with only logging once even when recursion has occurred
     * @param e to log
     */
    private logException(e: Error) {
        // This indicates this is the first time logException has been called for this context.
        // This would occur at the furthest call depth so at this point the conversionDepthStack has the complete stack
        if(this.errorStack.length === 0){
            // We loop vs add all to keep stack intact
            for(let value of this.conversionDepthStack){
                this.errorStack.unshift(value)
            }
        }
        if(this.conversionDepthStack.length === 1) { // we are at the top of the stack during recursion
            let sb: string = `\nError occurred during conversion.\nException: ${e.message}\nJsonPath: ${this.currentJsonPath}\nConversion Stack:\n`
            let objectCount = 1
            for (let value of this.errorStack) {
                const spacer = '  '.repeat(objectCount)
                sb += spacer
                sb += '- '
                sb += this.strategy.valueToString(value)
                          .replaceAll('\n', '\n  ' + spacer)
                sb += '\n'
                objectCount++
            }
            this.logger.log(sb)
            this.errorStack.length = 0 // we have printed reset
        }
    }

}
