import { C3Type } from '@kinotic/continuum-idl'
import { IConversionContext } from './IConversionContext'

/**
 * {@link ITypeConverter} are the base interface for converting a specific type to a {@link C3Type}.
 * All {@link ITypeConverter}'s should be stateless. Any state that needs to be retained should be stored in the {@link IConversionContext}.
 *
 * @param <T> the type to convert to
 * @param <S> the state type
 *
 * Created by Navíd Mitchell 🤪 on 4/26/23.
 */
export interface ITypeConverter<T, S> {

  /**
   * Converts the given value to a {@link C3Type}.
   * @param value to convert
   * @param conversionContext the context to use for conversion
   * @return the converted type
   */
  convert(value: T,
          conversionContext: IConversionContext<T, S>): C3Type

}
