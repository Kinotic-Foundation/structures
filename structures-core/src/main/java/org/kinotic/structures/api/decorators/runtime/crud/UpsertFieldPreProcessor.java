package org.kinotic.structures.api.decorators.runtime.crud;

import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;
import org.kinotic.structures.api.decorators.runtime.C3DecoratorInstance;
import org.kinotic.structures.api.domain.EntityContext;
import org.kinotic.structures.api.domain.Structure;

/**
 * {@link UpsertFieldPreProcessor} is used to modify the value of a field before it is upserted into the database.
 *
 * @param <D> the {@link C3Decorator} class that this instance implements
 * @param <R> the type of the value that this processor will return
 * @param <T> the type of the value that this processor will process
 *            <p>
 *            Created by Navíd Mitchell 🤪 on 5/10/23.
 */
public interface UpsertFieldPreProcessor<D extends C3Decorator, R, T> extends C3DecoratorInstance<D> {

    /**
     * The type of the field that this processor can process.
     * This is needed for the JSON processing logic.
     *
     * @return the class of the field that this processor can process
     */
    Class<T> supportsFieldType();

    /**
     * Process the value of a field before it is upserted into the database.
     *
     * @param structure the {@link Structure} that is being processed
     * @param fieldName the name of the field that is being processed
     * @param decorator the {@link C3Decorator} that is being processed
     * @param value     the value of the field that is being processed
     * @param context   the context for this operation
     * @return the value that should be upserted into the database
     */
    R process(Structure structure,
              String fieldName,
              D decorator,
              T value,
              EntityContext context);

}
