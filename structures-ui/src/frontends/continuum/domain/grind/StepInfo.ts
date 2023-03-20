/**
 * The sequence of {@link Step}'s that have been executed to get to a specific {@link Result}
 */
export class StepInfo {

    public sequence: number;

    public ancestor: StepInfo | null = null;

    constructor(sequence: number) {
        this.sequence = sequence
    }

}
