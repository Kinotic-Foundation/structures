import { createStateManager } from './IStateManager.js'

const ENVIRONMENT_KEY = 'structures-environment'

export class Environment {

  public servers!: {
    [key: string]: string
  }

  public defaultServer!: string

}

export async function loadEnvironment(dataDir: string): Promise<Environment>{
  const stateManager = createStateManager(dataDir)
  if(await stateManager.containsState(ENVIRONMENT_KEY)){
    return stateManager.load<Environment>(ENVIRONMENT_KEY)
  }else {
    return new Environment()
  }
}

export async function saveEnvironment(dataDir: string, environment: Environment): Promise<void> {
  const stateManager = createStateManager(dataDir)
  await stateManager.save(ENVIRONMENT_KEY, environment)
}
