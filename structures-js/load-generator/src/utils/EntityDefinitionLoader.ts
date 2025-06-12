import fs from 'fs/promises'
import path from 'path'
import { ObjectC3Type } from '@kinotic/continuum-idl'
import { ConsoleLogger } from '@kinotic/structures-cli/dist/internal/Logger.js'
import { EntityDefinitionGenerator } from './EntityDefinitionGenerator'

export class EntityDefinitionLoader {
    private readonly logger: ConsoleLogger
    private readonly generator: EntityDefinitionGenerator
    private readonly jsonDir: string

    constructor(
        namespace: string,
        entitiesPath: string,
        generatedPath: string,
        jsonDir: string
    ) {
        this.logger = new ConsoleLogger()
        this.generator = new EntityDefinitionGenerator(namespace, entitiesPath, generatedPath)
        this.jsonDir = jsonDir
    }

    async loadDefinitions(): Promise<Map<string, ObjectC3Type>> {
        if (process.env.NODE_ENV === 'production') {
            return this.loadFromJson()
        } else {
            this.logger.log('Development environment - generating entity definitions from source')
            return this.generator.generateDefinitions()
        }
    }

    private async loadFromJson(): Promise<Map<string, ObjectC3Type>> {
        const definitions = new Map<string, ObjectC3Type>()

        try {
            // Read all JSON files in the directory
            const files = await fs.readdir(this.jsonDir)
            const jsonFiles = files.filter(file => file.endsWith('.json'))

            if (jsonFiles.length === 0) {
                throw new Error('No entity definition JSON files found')
            }

            for (const file of jsonFiles) {
                const filePath = path.join(this.jsonDir, file)
                const content = await fs.readFile(filePath, 'utf-8')
                const definition = JSON.parse(content) as ObjectC3Type
                definitions.set(definition.name.toLowerCase(), definition)
            }

            this.logger.log(`Loaded ${definitions.size} entity definitions from JSON files`)
        } catch (error) {
            this.logger.log(`Error loading JSON files: ${error}`)
            throw new Error('Failed to load entity definitions from JSON files in production environment')
        }

        return definitions
    }
} 