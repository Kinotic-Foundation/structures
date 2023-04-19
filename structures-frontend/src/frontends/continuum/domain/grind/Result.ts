import { Step } from './Step'
import { StepInfo } from './StepInfo'

export enum ResultType{
    /**
     * The result value is the final VALUE of the task
     */
    VALUE = "VALUE",
    /**
     * The task resulted in no action being taken the value will be null
     */
    NOOP = "NOOP",
    /**
     * The result value is a Diagnostic message
     */
    DIAGNOSTIC = "DIAGNOSTIC",
    /**
     * The result value is a {@link Progress} object
     */
    PROGRESS = "PROGRESS",
    /**
     * Result contains new {@link Step}'s that have been returned by a {@link Task} execution
     * This is used to update the known {@link Step}'s when wanting to receive progress notifications
     * The result value will contain the new {@link Step}
     */
    DYNAMIC_STEPS = "DYNAMIC_STEPS",
    /**
     * The result value is a Error indicating that an error occurred at the given step
     */
    EXCEPTION = "EXCEPTION"
}

export class Result {

    /**
     * The {@link StepInfo} which represents the {@link Step}'s that are responsible for creating this {@link Result}
     * @return the {@link StepInfo} for this {@link Result}
     */
    public stepInfo!: StepInfo

    /**
     * What type of result this is.
     * The results that are produced by a "JobDefinition" Execution
     *
     * The result type will effect the meaning of the value
     * For {@link ResultType#VALUE} the value will be the "final" value produced by a {@link Task}
     * For {@link ResultType#NOOP} the value will be null
     * For {@link ResultType#DIAGNOSTIC} the value will be a simple message describing something that happened
     * For {@link ResultType#PROGRESS} the value will be a {@link Progress} object
     * For {@link ResultType#EXCEPTION} the value will be a Excpetion object
     *
     * @return the {@link ResultType} for this result
     */
    public resultType!: ResultType

    /**
     * @return the result of a successful execution for the given {@link Step}
     */
    public value: any | null = null

}
