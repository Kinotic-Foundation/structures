import {C3Type} from '@kinotic/continuum-idl'
import {FunctionDeclaration} from 'ts-morph'
import {EntityConfiguration, OverrideConfiguration, TransformConfiguration} from '../../state/StructuresProject.js'
import {TransformerFunctionLocator} from '../../TransformerFunctionLocator.js'

/**
 * The state of the Typescript to C3Type conversion process.
 */
export class TypescriptConversionState {

    private readonly _namespace: string
    private readonly transformerFunctionLocator: TransformerFunctionLocator
    private _entityConfiguration: EntityConfiguration | undefined = undefined
    private _exclusionMap: Map<string, void> | null = null
    private _overridesMap: Map<string, OverrideConfiguration> | null = null
    private _transformsMap: Map<string, TransformConfiguration> | null = null
    private _propertyStack: string[] = []

    public currentJsonPath: string = ''
    public nearestPropertyNameNotInUnion: string | null = null
    public convertingUnion: boolean = false

    constructor(namespace: string, transformerFunctionLocator: TransformerFunctionLocator) {
        this._namespace = namespace
        this.transformerFunctionLocator = transformerFunctionLocator
    }

    get namespace(): string {
        return this._namespace
    }

    beginProcessingProperty(name: string): void {
        this.currentJsonPath = (this._propertyStack.length > 0  ? this._propertyStack[this._propertyStack.length - 1] + '.' : '') + name
        this._propertyStack.push(this.currentJsonPath)
    }

    endProcessingProperty(): void {
        this._propertyStack.pop()
        this.currentJsonPath = this._propertyStack.length > 0 ? this._propertyStack[this._propertyStack.length - 1] : ''
    }

    get entityConfiguration(): EntityConfiguration | undefined {
        return this._entityConfiguration
    }

    set entityConfiguration(value: EntityConfiguration | undefined) {
        this._entityConfiguration = value
        if(value){
            if(value.excludeJsonPaths) {
                this._exclusionMap = new Map<string, void>()
                for (const exclusion of value.excludeJsonPaths) {
                    this._exclusionMap.set(exclusion, undefined)
                }
            }
            if(value.overrides) {
                this._overridesMap = new Map<string, OverrideConfiguration>()
                for (const override of value.overrides) {
                    this._overridesMap.set(override.jsonPath, override)
                }
            }
            if(value.transforms) {
                this._transformsMap = new Map<string, TransformConfiguration>()
                for (const transform of value.transforms) {
                    this._transformsMap.set(transform.jsonPath, transform)
                }
            }
        }else{
            this._exclusionMap = null
            this._overridesMap = null
            this._transformsMap = null
        }
    }

    shouldExclude(): boolean {
        let ret = false
        if(this._exclusionMap){
            ret = this._exclusionMap.has(this.currentJsonPath)
        }
        return ret
    }

    getOverrideType(): C3Type | null {
        let ret: C3Type | null = null
        if(this._overridesMap){
            const override = this._overridesMap.get(this.currentJsonPath)
            if(override){
                ret = override.c3Type
            }
        }
        return ret
    }

    getTransformFunction(): FunctionDeclaration | null {
        let ret: FunctionDeclaration | null = null
        if(this._transformsMap){
            const transform = this._transformsMap.get(this.currentJsonPath)
            if(transform){
                ret = this.transformerFunctionLocator.getTransformerFunction(transform.transformerFunctionName)
            }
        }
        return ret
    }
}
