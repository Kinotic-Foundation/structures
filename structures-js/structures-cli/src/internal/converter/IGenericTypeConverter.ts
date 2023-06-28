import { C3Type } from '@kinotic/continuum-idl'
import { ITypeConverter } from './ITypeConverter'

/**
 * {@link IGenericTypeConverter} are more general and can convert any value where supports returns true.
 *
 * @param <T> The type to convert to
 * @param <S> The state type
 *
 * Created by Navíd Mitchell 🤪 on 4/26/23.
 */
export interface IGenericTypeConverter<T, S> extends ITypeConverter<T, S> {

    /**
     * Checks if the given value is supported by this converter
     *
     * @param value to check if supported
     * @return true if this converter can convert the type false if not
     */
    supports(value: T): boolean

}
