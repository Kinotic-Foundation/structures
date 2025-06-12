import { CodeGenerationService } from '@kinotic/structures-cli/dist/internal/CodeGenerationService.js'
import { ConsoleLogger } from '@kinotic/structures-cli/dist/internal/Logger.js'
import { NamespaceConfiguration } from '@kinotic/structures-cli/dist/internal/state/StructuresProject.js'
import path from 'path'
import fs from 'fs/promises'

// Hardcode the output directory since this is only used during build
const outputDir = path.resolve(process.cwd(), 'dist/entity-definitions')

async function buildEntityDefinitions() {
    const logger = new ConsoleLogger()
    const namespace = 'ecommerce'
    const codeGenerationService = new CodeGenerationService(namespace, '.js', logger)

    const namespaceConfig: NamespaceConfiguration = new NamespaceConfiguration()
    namespaceConfig.namespaceName = namespace
    namespaceConfig.validate = false
    namespaceConfig.entitiesPaths = ['../entity/domain/ecommerce']
    namespaceConfig.generatedPath = '../services/ecommerce'

    // Ensure output directory exists
    await fs.mkdir(outputDir, { recursive: true })

    await codeGenerationService.generateAllEntities(
        namespaceConfig,
        false,
        async (entityInfo) => {
            const outputPath = path.join(outputDir, `${entityInfo.entity.name.toLowerCase()}.json`)
            await fs.writeFile(outputPath, JSON.stringify(entityInfo.entity, null, 2))
            logger.log(`Generated entity definition for ${entityInfo.entity.name} at ${outputPath}`)
        }
    )
}

// Run the script
console.log('Running buildEntityDefinitions...')
try {
    await buildEntityDefinitions()
    console.log('Build completed successfully')
    process.exit(0)
} catch (error) {
    console.error('Build failed:', error)
    process.exit(1)
} 