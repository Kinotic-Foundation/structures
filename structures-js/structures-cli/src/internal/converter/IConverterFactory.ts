import { IConverterStrategy } from './IConverterStrategy'
import {IConversionContext} from './IConversionContext'

/**
 * Creates {@link IConversionContext} instances based on a {@link IConverterStrategy}
 * Created by Navíd Mitchell 🤪 on 4/26/23.
 */
export interface IConverterFactory {

  /**
   * Creates a new {@link IConversionContext} based on the given {@link IConverterStrategy}
   * @param strategy to use for conversion
   * @return a new {@link IConversionContext} instance
   */
  createConverter<T, S>(strategy: IConverterStrategy<T, S>): IConversionContext<T, S>

}
