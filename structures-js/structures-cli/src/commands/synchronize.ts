import {Args, Command, Flags} from '@oclif/core'
import path from 'node:path'
import {Project} from 'ts-morph'

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

    this.log(`Synchronizing Entities in namespace ${args.namespace} from ${flags.entities}!`)
  }

}
