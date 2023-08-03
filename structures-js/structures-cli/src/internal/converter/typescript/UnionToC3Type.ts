import {BooleanC3Type, C3Type, EnumC3Type, ObjectC3Type, StringC3Type, UnionC3Type} from '@kinotic/continuum-idl'
import {Type} from 'ts-morph'
import {TypescriptConversionState} from './TypescriptConversionState.js'
import {ITypeConverter} from '../ITypeConverter.js'
import {IConversionContext} from '../IConversionContext.js'
import {pascal, trim} from 'radash'

/**
 * Converts a typescript union type to a C3Type
 * TODO: Refactor this, it is starting to seem kinda brittle
 */
export class UnionToC3Type implements ITypeConverter<Type, Type, TypescriptConversionState> {

    convert(value: Type, conversionContext: IConversionContext<Type, TypescriptConversionState>): C3Type {
        let ret: C3Type

        const converted: C3Type[] = []
        let primitiveCount = 0
        let arrayCount = 0
        let enumCount = 0
        let booleanLiteral: boolean = false
        const stringLiterals: string[] = []

        // Unions are interesting because there are unions that are not invalid but not a union when it comes to C3
        // For example: string | undefined | null
        // This is not a union in C3 because it is not a union of objects

        // Another example: let var?: string
        // Which provides the union types of string | undefined

        // Another example: let var?: MyEnum
        // Which provides the union types of undefined | MyEnum.FIRST | MyEnum.SECOND ect..

        // Another example: let var?: boolean
        // Which provides the union types of undefined | true | false

        value.getUnionTypes().forEach((unionType: Type) => {

            if (!unionType.isUndefined() && !unionType.isNull()) {

                // Array has to be checked before object because arrays are objects
                if (unionType.isArray()) {

                    converted.push(conversionContext.convert(unionType))
                    arrayCount++

                } else if (unionType.isStringLiteral()) { // This must be before is primitive because string literals are primitives

                    stringLiterals.push(unionType.getText())

                } else if (unionType.isBooleanLiteral()) { // This must be before is primitive because boolean literals are primitives

                    booleanLiteral = true

                } else if (this.isPrimitive(unionType)) {

                    if(unionType.isLiteral()){
                        converted.push(conversionContext.convert(unionType.getApparentType()))
                    }else{
                        converted.push(conversionContext.convert(unionType))
                    }
                    primitiveCount++

                } else if (unionType.isObject()) { // This must be after is primitive because dates are objects

                    converted.push(conversionContext.convert(unionType))

                } else if (unionType.isEnumLiteral()) {

                    // Since the enums come back as a bunch of enum literals we only convert the first one we encounter
                    if (enumCount === 0) {
                        const toConvert = unionType.getSymbol()?.getValueDeclaration()?.getParent()?.getType()
                        if (toConvert) {
                            converted.push(conversionContext.convert(toConvert))
                        } else {
                            throw new Error("Could not find the parent type of the enum literal: " + unionType.getText())
                        }
                    }
                    enumCount++

                } else {
                    throw new Error("Union type contains a type that is not supported: " + unionType.getText())
                }
            }
        })

        if (primitiveCount > 1) {
            throw new Error("Union type contains more than one primitive type: " + value.getText())
        }

        if (arrayCount > 1) {
            throw new Error("Union type contains more than one array type: " + value.getText())
        }

        // Now we check that a union is only objets or is single non object type
        // Since structures does not support unions of primitives, arrays, or enums
        if (primitiveCount === 1) {

            if (converted.length === 1) {
                ret = converted[0]
            } else {
                throw new Error("You cannot create a Union with a primitive type and other types: " + value.getText())
            }

        } else if (arrayCount === 1) {

            if (converted.length === 1) {
                ret = converted[0]
            } else {
                throw new Error("You cannot create a Union with an array type and other types: " + value.getText())
            }

        } else if (enumCount >= 1) {

            if (converted.length === 1) {
                ret = converted[0]
                if ((ret as EnumC3Type).values.length !== enumCount) {
                    throw new Error("There were more enums found in the union than were converted: " + value.getText() + "\n(Sorry if this error is kind of confusing, it is a bit of a edge case)")
                }
            } else {
                throw new Error("You cannot create a Union with an enum type and other types, or more than one enum type: " + value.getText())
            }

        } else if (booleanLiteral) {

            if(primitiveCount > 0 || arrayCount > 0 || enumCount > 0 || converted.length > 0){
                throw new Error("You cannot create a Union with boolean and other types: " + value.getText())
            }
            ret = new BooleanC3Type()

        } else if(stringLiterals.length > 0) {
            // This handles the case of a union of string literals
            // eg: "first" | "second" | "third"
            // This is a special case because we want to convert it to an enum
            // but for now we will just return a string type
            if (primitiveCount > 0 || arrayCount > 0 || enumCount > 0 || converted.length > 0) {
                throw new Error("You cannot create a Union with string literals and other types: " + value.getText())
            }
            // const enumType = new EnumC3Type()
            // enumType.namespace = conversionContext.state().namespace
            // enumType.name = this.getUnionPropertyName(conversionContext)
            // for (const stringLiteral of stringLiterals) {
            //     enumType.addValue(trim(stringLiteral, '"'))
            // }
            ret = new StringC3Type()

        } else if (converted.length === 1) { // In this case it was a single optional object, let myVar?: MyObject

            ret = converted[0]

        } else {

            const unionType = new UnionC3Type()
            unionType.namespace = conversionContext.state().namespace
            unionType.name = this.getUnionPropertyName(conversionContext)
            unionType.types = converted as ObjectC3Type[]
            ret = unionType

        }

        return ret
    }

    supports(value: Type): boolean {
        return value.isUnion();
    }

    private isPrimitive(type: Type): boolean {
        const typeText = type.getText().toLowerCase()
        return typeText === 'string'
            || typeText === 'number'
            || typeText === 'boolean'
            || typeText === 'date'
            || (type.isLiteral() && this.isPrimitive(type.getApparentType()))
    }

    private getUnionPropertyName(conversionContext: IConversionContext<Type, TypescriptConversionState>): string {
        if (conversionContext.state().unionPropertyNameStack.length > 0) {
            return this.capitalizeFirstLetter(conversionContext.state().unionPropertyNameStack[conversionContext.state().unionPropertyNameStack.length - 1])
        } else {
            throw new Error("The current property name is not set in the conversion state")
        }
    }

    private capitalizeFirstLetter(s: string): string {
        return s.charAt(0).toUpperCase() + s.slice(1);
    }

}
