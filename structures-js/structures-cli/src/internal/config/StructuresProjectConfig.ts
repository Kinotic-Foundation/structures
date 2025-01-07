/**
 * The project configuration for a structures project.
 * This is the base configuration. Language specific configurations extend this configuration.
 */
export abstract class StructuresProjectConfig {
    /**
     * The name of the project or undefined if a language specific project name is used.
     * i.e. if the project is typescript the package.json name will be used.
     */
    public name?: string

    /**
     * The version of the project or undefined if a language specific project version is used.
     * i.e. if the project is typescript the package.json version will be used.
     */
    public version?: string

    /**
     * The Structures Application that this project belongs to.
     */
    public application!: string

    /**
     * This specifies the Model Definition Language (MDL).
     * This is the language that is used to define the structures models.
     * The following values are supported:
     * - ts: TypeScript
     * - ts-external: TypeScript with external models (defined by an external package)
     * - gql: GraphQL
     */
    public mdl!: 'ts' | 'ts-external' | 'gql'

}

/**
 * The project configuration for a TypeScript structures project.
 */
export class TypescriptProjectConfig extends StructuresProjectConfig {

    public readonly mdl = 'ts'

    /**
     * The paths to search for classes decorated with @Entity that Structures will be created for.
     */
    entitiesPaths!: string[]

    /**
     * The path to where generated files will be placed.
     */
    generatedPath!: string

    /**
     * The file extension to use for imports in generated files.
     */
    fileExtensionForImports: string = '.js'

}
