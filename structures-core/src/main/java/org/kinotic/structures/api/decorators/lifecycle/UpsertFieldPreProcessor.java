package org.kinotic.structures.api.decorators.lifecycle;

import org.kinotic.continuum.idl.api.schema.decorators.C3Decorator;

/**
 * {@link UpsertFieldPreProcessor} is used to modify the value of a field before it is upserted into the database.
 * Created by Navíd Mitchell 🤪 on 5/10/23.
 */
public interface UpsertFieldPreProcessor<D extends C3Decorator, T, R> extends DecoratorProcessor<D, T, R>{

}
