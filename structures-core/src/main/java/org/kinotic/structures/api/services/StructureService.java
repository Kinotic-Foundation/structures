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

import org.kinotic.continuum.api.annotations.Publish;
import org.kinotic.continuum.core.api.crud.IdentifiableCrudService;
import org.kinotic.continuum.core.api.crud.Page;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.structures.api.domain.Structure;

import java.util.concurrent.CompletableFuture;

@Publish
public interface StructureService extends IdentifiableCrudService<Structure, String> {

    /**
     * Finds all published structures for the given namespace.
     * @param namespace the namespace to find structures for
     * @param pageable the page to return
     * @return a future that will complete with a page of structures
     */
    CompletableFuture<Page<Structure>> findAllPublishedForNamespace(String namespace, Pageable pageable);

    /**
     * Counts all structures for the given namespace.
     * @param namespace the namespace to find structures for
     * @return a future that will complete with a page of structures
     */
    CompletableFuture<Long> countForNamespace(String namespace);

    /**
     * Publishes thed structure with the given id.
     * This will make the structure available for use to read and write items for.
     * @param structureId the id of the structure to publish
     * @return a future that will complete when the structure has been published
     */
    CompletableFuture<Void> publish(String structureId);

    /**
     * Un-publish the structure with the given id.
     * @param structureId the id of the structure to un-publish
     * @return a future that will complete when the structure has been unpublished
     */
    CompletableFuture<Void> unPublish(String structureId);

}
