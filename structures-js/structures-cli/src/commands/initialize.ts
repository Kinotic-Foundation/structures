import {Command, Flags} from '@oclif/core'
import fs from 'fs'
import path from 'path'
import chalk from 'chalk'
import {
    isStructuresProject,
    StructuresProject,
    NamespaceConfiguration,
    saveStructuresProject
} from '../internal/state/StructuresProject.js'

export class Initialize extends Command {
    static aliases = ['init']

    static description = 'This will initialize a new Structures Project for use with the Structures CLI.'

    static examples = [
        '$ structures initialize --namespace my.namespace --entities path/to/entities --generated path/to/services',
        '$ structures init --namespace my.namespace --entities path/to/entities --generated path/to/services',
        '$ structures init -n my.namespace -e path/to/entities -g path/to/services',
    ]

    static flags = {
        namespace:  Flags.string({char: 'n', description: 'The name of the namespace you want to use', required: true}),
        entities:   Flags.string({char: 'e', description: 'Path to the directory containing the Entity definitions', required: true}),
        generated:  Flags.string({char: 'g', description: 'Path to the directory to write generated Services', required: true}),
    }

    public async run(): Promise<void> {
        const {flags} = await this.parse(Initialize)

        if(await isStructuresProject()){
            this.log(chalk.red('Error: ') + ' The working directory is already a Structures Project')
            return
        }

        const entitiesPath = path.resolve(flags.entities)
        const generatedPath = path.resolve(flags.generated)

        if(!fs.existsSync(entitiesPath)){
            this.error(`Entities path does not exist: ${entitiesPath}`)
        }
        if(!fs.existsSync(generatedPath)){
            this.error(`Generated path does not exist: ${generatedPath}`)
        }

        const namespace = flags.namespace
        let structuresProject = new StructuresProject()
        structuresProject.defaultNamespaceName = namespace
        let namespaceConfig =  new NamespaceConfiguration()
        namespaceConfig.namespaceName = namespace
        namespaceConfig.entitiesPaths = [flags.entities]
        namespaceConfig.generatedPath = flags.generated
        namespaceConfig.validate = false
        structuresProject.namespaces.push(namespaceConfig)

        await saveStructuresProject(structuresProject)

        this.log(chalk.green('Success:') + ' Initialized Project')
    }
}
