import path from 'path'

// In vite-node, __dirname is available
const baseDir = path.resolve(__dirname, '..')

export class Constants {
    static readonly ECOMMERCE_ENTITIES_PATH = path.resolve(baseDir, 'entity/domain/ecommerce')
    static readonly ECOMMERCE_GENERATED_PATH = path.resolve(baseDir, 'services/ecommerce/generated')
    static readonly ECOMMERCE_DEFINITIONS_PATH = path.resolve(baseDir, '../../dist/entity-definitions')

    static {
        console.log('Constants paths:')
        console.log('Base directory:', baseDir)
        console.log('Entities:', this.ECOMMERCE_ENTITIES_PATH)
        console.log('Generated:', this.ECOMMERCE_GENERATED_PATH)
        console.log('Definitions:', this.ECOMMERCE_DEFINITIONS_PATH)
    }
} 