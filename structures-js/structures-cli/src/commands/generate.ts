import {CodeGenerationService} from '../internal/CodeGenerationService.js'
import {Args, Command, Flags} from '@oclif/core'
import {
    isStructuresProject,
    loadStructuresProject
} from '../internal/state/StructuresProject.js'

export class Generate extends Command {
    static aliases = ['gen']

    static description = 'This will generate all Entity Service classes.'

    static examples = [
        '$ structures generate',
        '$ structures gen',
        '$ structures gen my.namespace -v',
    ]

    static flags = {
        verbose:    Flags.boolean({char: 'v', description: 'Enable verbose logging'}),
    }

    static args = {
        namespace: Args.string({description: 'The namespace that you want to generate service classes for', required: false})
    }

    public async run(): Promise<void> {
        const {args, flags} = await this.parse(Generate)

        if(!(await isStructuresProject())){
            this.error('The working directory is not a Structures Project')
        }

        const structuresProject= await loadStructuresProject()

        let namespaceConfig = structuresProject.findNamespaceConfigOrDefault(args.namespace)

        const codeGenerationService = new CodeGenerationService(namespaceConfig.namespaceName,
                                                                structuresProject.fileExtensionForImports,
                                                                this)

        await codeGenerationService.generateAllEntities(namespaceConfig, flags.verbose)

        this.log(`Code Generation Complete For namespace: ${namespaceConfig.namespaceName}`, flags.verbose)
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
