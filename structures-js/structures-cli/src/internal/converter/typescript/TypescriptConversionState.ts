/**
 * The state of the Typescript to C3Type conversion process.
 */
export class TypescriptConversionState {

  private readonly _namespace: string

  constructor(namespace: string) {
    this._namespace = namespace
  }

  get namespace(): string {
    return this._namespace
  }
}
