import path from 'path'
import { EntityDefinitionGenerator } from './EntityDefinitionGenerator'
import { Constants } from './Constants'
import fs from 'fs/promises'

async function buildEntityDefinitions(): Promise<void> {
    const outputDir = Constants.ECOMMERCE_DEFINITIONS_PATH
    console.log('Building entity definitions...')
    console.log('Output directory:', outputDir)
    
    const generator = new EntityDefinitionGenerator(
        'ecommerce',
        Constants.ECOMMERCE_ENTITIES_PATH,
        Constants.ECOMMERCE_GENERATED_PATH
    )
    
    try {
        console.log('Generating definitions...')
        const definitions = await generator.generateDefinitions()
        console.log(`Generated ${definitions.size} definitions`)

        // Ensure output directory exists
        console.log('Creating output directory...')
        await fs.mkdir(outputDir, { recursive: true })
        
        // Save definitions to JSON files
        console.log('Writing definition files...')
        for (const [name, definition] of definitions) {
            const filePath = path.join(outputDir, `${name}.json`)
            console.log(`Writing ${filePath}`)
            await fs.writeFile(
                filePath,
                JSON.stringify(definition, null, 2)
            )
        }
        console.log('Entity definitions built successfully')
    } catch (error) {
        console.error('Failed to build entity definitions:', error)
        throw error
    }
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