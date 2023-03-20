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

package org.kinotic.structures.internal.api.services;

import org.kinotic.structures.api.services.ItemDao;
import org.kinotic.structures.api.services.ItemService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * Created by Navid Mitchell on 2/11/21.
 */
public class DefaultItemDao<T, ID> implements ItemDao<T, ID> {


    private ItemService itemService;



    @Override
    public <S extends T> Mono<S> create(S item) {
        return null;
    }

    @Override
    public <S extends T> Mono<S> saveOrUpdate(S item) {
        return null;
    }

    @Override
    public Mono<T> findById(ID id) {
        return null;
    }

    @Override
    public Flux<T> findAll() {
        return null;
    }

    @Override
    public Mono<Long> count() {
        return null;
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        return null;
    }

    @Override
    public Mono<Void> delete(T item) {
        return null;
    }

}
