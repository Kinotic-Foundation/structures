import fs from 'fs'
import path from 'path'
import {FunctionDeclaration, Project} from 'ts-morph'
import {pathToTsGlobPath} from './Utils.js'

export class TransformerFunctionLocator {

    private project: Project
    private functionCache = new Map<string, FunctionDeclaration>()

    constructor(transformerFunctionsPaths: string[], verbose: boolean) {
        const tsConfigFilePath = path.resolve('tsconfig.json')
        if(!fs.existsSync(tsConfigFilePath)){
            throw new Error(`No tsconfig.json found in working directory: ${process.cwd()}`)
        }
        this.project = new Project({
            tsConfigFilePath: tsConfigFilePath
        })

        if(verbose) {
            this.project.enableLogging(true)
        }

        for(const transformerFunctionsPath of transformerFunctionsPaths){
            this.project.addSourceFilesAtPaths(pathToTsGlobPath(transformerFunctionsPath))
        }
    }

    public getTransformerFunction(name: string): FunctionDeclaration | null {
        let ret: FunctionDeclaration | null = null
        if(this.functionCache.has(name)){
            ret = this.functionCache.get(name) as FunctionDeclaration
        }else {
            for(const sourceFile of this.project.getSourceFiles()){
                const transformerFunction = sourceFile.getFunction(name)
                if(transformerFunction){
                    ret = transformerFunction
                    this.functionCache.set(name, transformerFunction)
                    break
                }
            }
        }
        return ret
    }
}
