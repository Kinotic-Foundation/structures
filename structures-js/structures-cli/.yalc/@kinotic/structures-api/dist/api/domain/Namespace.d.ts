import { Identifiable } from '@kinotic/continuum-client';

export declare class Namespace implements Identifiable<string> {
    id: string;
    description: string;
    updated: number;
    constructor(id: string, description: string, updated: number);
}
