import {ClassDeclaration, Decorator, PropertyDeclaration, Node, MethodDeclaration} from 'ts-morph'

export class Helper {

    public static printProperties(clazz: ClassDeclaration): void {
        const properties = clazz.getProperties()

        properties.forEach((property: PropertyDeclaration) => {
            const propertyType = property.getType()
            const annotations = property.getDecorators()
            console.log(`Property: ${property.getName()}, Type: ${propertyType.getText()}`)
            if (annotations.length > 0) {
                console.log("Annotations:")
                annotations.forEach((annotation: Decorator) => {
                    console.log(`- ${annotation.getText()}`)
                })
            }
        })

        clazz.forEachDescendant((node: Node) => {

            if (MethodDeclaration.isMethodDeclaration(node) && node.isAbstract()) {
                const methodDeclaration = node
                console.log('Abstract Function:', methodDeclaration.getName())
                const decorators = methodDeclaration.getDecorators()
                if (decorators.length > 0) {
                    decorators.forEach((decorator: Decorator) => {
                        const decoratorName = decorator.getName()
                        const decoratorArguments = decorator.getArguments()

                        console.log('Decorator:', decoratorName)
                        if (decoratorArguments.length > 0) {
                            decoratorArguments.forEach((decoratorArgument) => {
                                const decoratorArgumentValue = decoratorArgument.getText()
                                console.log('Decorator Argument Value:', decoratorArgumentValue)
                            })
                        }
                    })
                }
            }
        })
    }

}
