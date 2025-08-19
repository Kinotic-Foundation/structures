import {CodeGenerationService} from '../internal/CodeGenerationService.js'
import {Args, Command, Flags} from '@oclif/core'
import {
    isStructuresProject,
    loadStructuresProjectConfig
} from '../internal/state/StructuresProject.js'
import { TypescriptExternalProjectConfig, TypescriptProjectConfig } from '@kinotic/structures-api'

export class Generate extends Command {
    static aliases = ['gen']

    static description = 'This will generate all Entity Service classes.'

    static examples = [
        '$ structures generate',
        '$ structures gen',
        '$ structures gen my.application -v',
    ]

    static flags = {
        verbose:    Flags.boolean({char: 'v', description: 'Enable verbose logging'}),
    }

    static args = {
        application: Args.string({description: 'The application that you want to generate service classes for', required: false})
    }

    public async run(): Promise<void> {
        const {args, flags} = await this.parse(Generate)

        if(!(await isStructuresProject())){
            this.error('The working directory is not a Structures Project')
        }

        const structuresProjectConfig = await loadStructuresProjectConfig()

        const codeGenerationService = new CodeGenerationService(structuresProjectConfig.application,
                                                                structuresProjectConfig.fileExtensionForImports,
                                                                this)

            await codeGenerationService.generateAllEntities(structuresProjectConfig as TypescriptProjectConfig | TypescriptExternalProjectConfig, flags.verbose)

        this.log(`Code Generation Complete For applicartion: ${structuresProjectConfig.application}`, flags.verbose)
    }

    public logVerbose(message: string | ( () => string ), verbose: boolean): void {
        if (verbose) {
            if (typeof message === 'function') {
                this.log(message())
            }else{
                this.log(message)
            }
        }
    }
}
