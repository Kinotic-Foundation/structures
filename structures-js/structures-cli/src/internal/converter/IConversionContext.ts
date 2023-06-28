import { C3Type } from '@kinotic/continuum-idl'
import {IConverterStrategy} from './IConverterStrategy';
import {DefaultConversionContext} from './DefaultConversionContext';

/**
 * {@link IConversionContext} allows for conversion of a specific language type to Continuum IDL types.
 * The {@link IConversionContext} contains state and can be reused but will retain state between requests.
 * If state needs to be reset a new {@link IConversionContext} should be created.
 *
 * @param <BASE_TYPE> The base type that will be converted
 * @param <S> The state type
 *s
 * Created by Navíd Mitchell 🤪 on 4/26/23.
 */
export interface IConversionContext<BASE_TYPE, S> {

  /**
   * Converts the given type to a {@link C3Type} by resolving the proper {@link ITypeConverter}.
   *
   * @param value to convert
   * @return the converted value
   */
  convert(value: BASE_TYPE): C3Type

  /**
   * @return the state of this {@link IConversionContext}
   */
  state(): S

}

/**
 * Creates {@link IConversionContext} instances based on a {@link IConverterStrategy}
 * The {@link IConversionContext} allows for conversion of a specific language type to Continuum IDL types.
 * The {@link IConversionContext} contains state and can be reused but will retain state between requests.
 * If state needs to be reset a new {@link IConversionContext} should be created.
 * @param strategy
 */
export function createConversionContext<BASE_TYPE, T extends BASE_TYPE, S>(strategy: IConverterStrategy<BASE_TYPE, T, S>): IConversionContext<BASE_TYPE, S>{
  return new DefaultConversionContext(strategy)
}
