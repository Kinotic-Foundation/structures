import {Project} from 'ts-morph'

/**
 * The state of the Typescript to C3Type conversion process.
 */
export class TypescriptConversionState {

  private readonly _namespace: string
  private readonly _project: Project
  public currentPropertyName: string | null = null

  constructor(namespace: string, project: Project) {
    this._namespace = namespace
    this._project = project
  }

  get namespace(): string {
    return this._namespace
  }


  get project(): Project {
    return this._project
  }
}
