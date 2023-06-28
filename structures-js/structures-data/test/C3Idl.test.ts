import {describe, it} from 'vitest'
import {ClassDeclaration, Project, SourceFile, SyntaxKind, TypeAliasDeclaration} from 'ts-morph'
import {Helper} from './util/Helper'

describe('C3-IDL', () => {


    it('should work',
        () => {

            // Create a new TypeScript project
            const project = new Project({
                tsConfigFilePath: "tsconfig.json"
            })
            project.enableLogging(true)
            project.addSourceFilesAtPaths('test/**/*.ts');

            console.log("Directories:")
            const directories = project.getDirectories()
            for (const dir of directories) {
                console.log('----')
                console.log(` Path : ${dir.getPath()} `)
                console.log(` BaseName : ${dir.getBaseName()} `)
            }

            console.log("Source Files:")
            const sourceFiles = project.getSourceFiles('test/**/*.ts');
            for (const sourceFile of sourceFiles) {
                console.log('----')
                console.log(` Path : ${sourceFile.getFilePath()} `)
                console.log(` BaseName : ${sourceFile.getBaseName()} `)

                const exportedDeclarations = sourceFile.getExportedDeclarations()
                exportedDeclarations.forEach((exportedDeclarationEntries, name) => {
                    console.log(`map entry name: ${name}`)
                    exportedDeclarationEntries.forEach((exportedDeclaration) => {
                        if (TypeAliasDeclaration.isTypeAliasDeclaration(exportedDeclaration)) {
                            // This is a type alias
                            console.log(`Type alias: ${exportedDeclaration.getName()}`)
                            const type = exportedDeclaration.getType()
                            console.log(`Type: ${type.getText()}`)
                        } else if (ClassDeclaration.isClassDeclaration(exportedDeclaration)) {
                            // This is a class
                            console.log(`Class: ${exportedDeclaration.getName()}`)
                            Helper.printProperties(exportedDeclaration)
                        } else {
                            // This is some other kind of declaration (e.g. an interface)
                            console.log(`Other declaration: ${exportedDeclaration.getText()}`)
                        }
                    })
                })
            }
        })

})
