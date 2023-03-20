/*
 *
 * Copyright 2008-2021 Kinotic and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kinotic.structures.api.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * Created by Navid Mitchell on 2/11/21.
 */
public interface ItemDao<T, ID> {

    /**
     * Creates a given item. Use the returned instance for further operations as the save operation might have changed the
     * item instance completely.
     *
     * @param item must not be {@literal null}.
     * @return {@link Mono} emitting the saved item.
     * @throws IllegalArgumentException in case the given {@literal item} is {@literal null}.
     */
    <S extends T> Mono<S> create(S item);


    /**
     * Saves a given item or updates it if it already exists. Use the returned instance for further operations as the save operation might have changed the
     * item instance completely.
     *
     * @param item must not be {@literal null}.
     * @return {@link Mono} emitting the saved item.
     * @throws IllegalArgumentException in case the given {@literal item} is {@literal null}.
     */
    <S extends T> Mono<S> saveOrUpdate(S item);

    /**
     * Retrieves an item by its id.
     *
     * @param id must not be {@literal null}.
     * @return {@link Mono} emitting the item with the given id or {@link Mono#empty()} if none found.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}.
     */
    Mono<T> findById(ID id);

    /**
     * Returns all instances of the type.
     *
     * @return {@link Flux} emitting all entities.
     */
    Flux<T> findAll();

    /**
     * Returns the number of entities available.
     *
     * @return {@link Mono} emitting the number of entities.
     */
    Mono<Long> count();

    /**
     * Deletes the item with the given id.
     *
     * @param id must not be {@literal null}.
     * @return {@link Mono} signaling when operation has completed.
     * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}.
     */
    Mono<Void> deleteById(ID id);

    /**
     * Deletes a given item.
     *
     * @param item must not be {@literal null}.
     * @return {@link Mono} signaling when operation has completed.
     * @throws IllegalArgumentException in case the given item is {@literal null}.
     */
    Mono<Void> delete(T item);

}
