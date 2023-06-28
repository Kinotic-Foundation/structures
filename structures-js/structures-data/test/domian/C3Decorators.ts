import "reflect-metadata"

export class C3Decorators {

    static notNull(_: any, context) {
        if (context.kind === "field") {

        }
    }

    static id(_: any, context) {
        if (context.kind === "field") {
            // return function (initialValue: any) {
            //     return initialValue
            // }
        }
    }

    static query(value: string, context) {
        if (context.kind === "function") {
            return function (initialValue: any) {
                return initialValue
            }
        }
    }

    static deprecatedProperty(_: any, context) {
        if (context.kind === "field") {
            console.warn(`${context.name} is deprecated and will be removed in a future version.`)
        }
    }

}