import { ITypeConverter } from './ITypeConverter'


/**
 * The {@link IConverterStrategy} is used to determine how to convert a Continuum IDL to a specific language type.
 * The {@link IConverterStrategy} should be reusable and thread safe.
 *
 * @param <T> The type to convert to
 * @param <S> The state type
 *
 * Created by Navíd Mitchell 🤪 on 4/26/23.
 */
export interface IConverterStrategy<T, S> {

  /**
   * Searches for a {@link ITypeConverter} that can convert the given value
   * @param value to find a converter for
   * @return the {@link ITypeConverter} that can convert the given value
   */
  converterFor(value: T): ITypeConverter<T, S>

  /**
   * The object that will be available via the {@link IConversionContext#state()}.
   * This can be a simple {@link java.util.Map} or something with better type safety.
   * This should return a new instance each time it is called.
   * This will be called each time a new {@link IConversionContext} is created.
   * @return the conversion context state.
   */
  initialState(): S

  /**
   * Determines if caching is turned on for this strategy.
   * If shouldCache is true and the {@link ITypeConverter} extends {@link ICacheable} then the results of the conversion will be cached and reused
   * @return true if the results of the converts should be cached
   */
  shouldCache(): boolean

}
