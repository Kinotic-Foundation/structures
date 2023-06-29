import {Args, Command, Flags} from '@oclif/core'
import path from 'node:path'
import {ClassDeclaration, Project} from 'ts-morph'
import {createConversionContext} from '../internal/converter/IConversionContext.js'
import {TypescriptConverterStrategy} from '../internal/converter/typescript/TypescriptConverterStrategy.js'
import {TypescriptConversionState} from '../internal/converter/typescript/TypescriptConversionState.js'

export default class Synchronize extends Command {
  static description = 'Synchronize the local Entity definitions with the Structures Server'

  static examples = [
    `$ structures synchronize my.namespace --entities path/to/entities`,
  ]

  static flags = {
    entities: Flags.string({char: 'e', description: 'Path to the directory containing the Entity definitions', required: true}),
  }

  static args = {
    namespace: Args.string({description: 'The namespace the Entities belong to', required: true}),
  }

  async run(): Promise<void> {
    const {args, flags} = await this.parse(Synchronize)
    const project = new Project({
      tsConfigFilePath: path.resolve('tsconfig.json')
    })

    project.enableLogging(true)
    const entitiesPath = path.resolve(flags.entities)
    project.addSourceFilesAtPaths(entitiesPath + '/*.ts')

    const sourceFiles = project.getSourceFiles(entitiesPath +'/*.ts');
    for (const sourceFile of sourceFiles) {
      this.log('----')
      this.log(` Path : ${sourceFile.getFilePath()} `)
      this.log(` BaseName : ${sourceFile.getBaseName()} `)

      const conversionContext = createConversionContext(new TypescriptConverterStrategy(new TypescriptConversionState(args.namespace, project), this))

      const exportedDeclarations = sourceFile.getExportedDeclarations()
      exportedDeclarations.forEach((exportedDeclarationEntries, name) => {
        this.log(`map entry name: ${name}`)
        exportedDeclarationEntries.forEach((exportedDeclaration) => {
          if (ClassDeclaration.isClassDeclaration(exportedDeclaration)) {

            try {
              this.log('Converting class')

              const c3Type = conversionContext.convert(exportedDeclaration)

              this.log('Printing C3Type')
              this.log(JSON.stringify(c3Type))
            } catch (e) {
              //this.log(`Error converting class: ${e}`)
            }

          } else {
            // This is some other kind of declaration (e.g. an interface)
            this.log(`Other declaration: ${exportedDeclaration.getText()}`)
          }
        })
      })
    }
  }

}
