import {PrettyPrintableError} from '@oclif/core/lib/errors'

export type Logger = {
    log: (message?: string, ...args: any[]) => void
    warn(input: string | Error): string | Error
    error(input: string | Error, options: {
                                              code?: string;
                                              exit: false;
                                          } & PrettyPrintableError): void
    error(input: string | Error, options?: {
                                               code?: string;
                                               exit?: number;
                                           } & PrettyPrintableError): never

    logVerbose(message: string | ( () => string ), verbose: boolean): void

}
