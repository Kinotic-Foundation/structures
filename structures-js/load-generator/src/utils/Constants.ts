import path from 'path'

// All paths are relative to the project root
const rootDir = path.resolve(__dirname, '../../')

export class Constants {
    static readonly ECOMMERCE_ENTITIES_PATH = path.resolve(rootDir, 'src/entity/domain/ecommerce')
    static readonly ECOMMERCE_GENERATED_PATH = path.resolve(rootDir, 'src/services/ecommerce/generated')
    static readonly ECOMMERCE_DEFINITIONS_PATH = path.resolve(rootDir, 'dist/entity-definitions')

    static {
        console.log('Constants paths:')
        console.log('Root directory:', rootDir)
        console.log('Entities:', this.ECOMMERCE_ENTITIES_PATH)
        console.log('Generated:', this.ECOMMERCE_GENERATED_PATH)
        console.log('Definitions:', this.ECOMMERCE_DEFINITIONS_PATH)
    }
} 