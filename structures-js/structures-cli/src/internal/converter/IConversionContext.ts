import { C3Type } from '@kinotic/continuum-idl'

/**
 * {@link IConversionContext} allows for conversion of a specific language type to Continuum IDL types.
 * The {@link IConversionContext} contains state and can be reused but will retain state between requests.
 * If state needs to be reset a new {@link IConversionContext} should be created.
 *
 * @param <T> The type to convert from
 * @param <S> The state type
 *
 * Created by Navíd Mitchell 🤪 on 4/26/23.
 */
export interface IConversionContext<T, S> {

  /**
   * Converts the given type to a {@link C3Type} by resolving the proper {@link ITypeConverter}.
   *
   * @param value to convert
   * @return the converted value
   */
  convert(value: T): C3Type

  /**
   * @return the state of this {@link IConversionContext}
   */
  state(): S

}
