import { Progress } from './Progress'

export enum StepState {
    PENDING,
    ACTIVE,
    FINISHED,
    FAILED
}

/**
 * Represents a step in a "Grind" job
 * With additional information for the purpose of rendering
 */
export class Step {

    public sequence!: number
    public description: string = ""
    public steps: Step[] | undefined = []

    // These are not in the data sent by the server but are used by the UI. Should we create a separate class for this.. ?
    public state: StepState | undefined = StepState.PENDING
    public progress: Progress | undefined = undefined
    public selected: boolean = false

    public hasSteps(): boolean{
        return this.steps !== undefined && this.steps.length > 0
    }

}
