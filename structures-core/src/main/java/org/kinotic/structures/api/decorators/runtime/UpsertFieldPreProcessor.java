package org.kinotic.structures.api.decorators.runtime;

import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;

/**
 * {@link UpsertFieldPreProcessor} is used to modify the value of a field before it is upserted into the database.
 *
 * @param <D> the {@link C3Decorator} class that this instance implements
 * @param <R> the type of the value that this processor will return
 * @param <T> the type of the value that this processor will process
 *
 * Created by Navíd Mitchell 🤪 on 5/10/23.
 */
public interface UpsertFieldPreProcessor<D extends C3Decorator, R, T> extends DecoratorProcessor<D, R, T>{

    /**
     * The type of the field that this processor will process.
     * This is needed for the JSON processing logic.
     * @return the class of the field that this processor will process
     */
    Class<T> getFieldType();

}
