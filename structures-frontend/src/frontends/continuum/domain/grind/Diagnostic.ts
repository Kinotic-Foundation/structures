/**
 * The "diagnostic" level for a grind {@link Diagnostic} message
 */
export enum DiagnosticLevel {
    NONE = "NONE",
    TRACE = "TRACE",
    DEBUG = "DEBUG",
    INFO = "INFO",
    WARN = "WARN",
    ERROR = "ERROR"
}


/**
 * A Diagnostic message sent from a grind task
 */
export class Diagnostic {

    public diagnosticLevel: DiagnosticLevel
    public message: string


    constructor(diagnosticLevel: DiagnosticLevel, message: string) {
        this.diagnosticLevel = diagnosticLevel
        this.message = message
    }
}
