import {C3Decorator} from '@kinotic/continuum-idl'

export class StructureUtil {
    public static hasDecorator(decoratorName: string, decorators: C3Decorator[] | undefined): boolean {
        let hasDecorator: boolean = false
        if(decorators !== undefined && decorators.length > 0){
            // check to see if we already have not null
            decorators.forEach((decorator) => {
                if(decorator.type === decoratorName){
                    hasDecorator = true
                }
            })
        }
        return hasDecorator
    }
}