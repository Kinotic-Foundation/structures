/**
 * The {@link PrecisionType} enum is used to define the precision of a numeric field in the Continuum IDL.
 */
export declare enum PrecisionType {
    /**
     * A double-precision 64-bit IEEE 754 floating point number, restricted to finite values.
     */
    DOUBLE = 0,
    /**
     * A single-precision 32-bit IEEE 754 floating point number, restricted to finite values.
     */
    FLOAT = 1,
    /**
     * A signed 32-bit integer with a minimum value of -231 and a maximum value of 231-1.
     */
    INT = 2,
    /**
     * A signed 64-bit integer with a minimum value of -263 and a maximum value of 263-1.
     */
    LONG = 3,
    /**
     * A signed 16-bit integer with a minimum value of -32,768 and a maximum value of 32,767.
     */
    SHORT = 4
}
