import fs from 'fs'
import path from 'path'
import {FunctionDeclaration, Project} from 'ts-morph'
import {pathToTsGlobPath} from './Utils.js'

export class UtilFunctionLocator {

    private project: Project
    private functionCache = new Map<string, FunctionDeclaration>()

    constructor(utilFunctionsPaths: string[], verbose: boolean) {
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

        for(const utilFunctionsPath of utilFunctionsPaths){
            this.project.addSourceFilesAtPaths(pathToTsGlobPath(utilFunctionsPath))
        }
    }

    public getTransformerFunction(name: string): FunctionDeclaration | null {
        let ret: FunctionDeclaration | null = null
        if(this.functionCache.has(name)){
            ret = this.functionCache.get(name) as FunctionDeclaration
        }else {
            for(const sourceFile of this.project.getSourceFiles()){
                const utilFunction = sourceFile.getFunction(name)
                if(utilFunction){
                    ret = utilFunction
                    this.functionCache.set(name, utilFunction)
                    break
                }
            }
        }
        return ret
    }
}
