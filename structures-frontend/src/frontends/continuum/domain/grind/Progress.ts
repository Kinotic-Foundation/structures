/**
 * Represents the progress of a grind task
 */
export class Progress {
    public percentageComplete: number
    public message: string

    constructor(percentageComplete: number, message: string) {
        this.percentageComplete = percentageComplete
        this.message = message
    }
}
