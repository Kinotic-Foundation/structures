/**
 * Represents a Grind JobDefinition
 */
import { Step } from './Step'

export class JobDefinition{
    public description: string
    public steps: Step[] = []

    constructor(description: string) {
        this.description = description
    }
}
