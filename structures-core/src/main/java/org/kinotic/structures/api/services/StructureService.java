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
import org.kinotic.structures.api.domain.*;

import java.io.IOException;

@Publish
public interface StructureService {

    StructureHolder save(StructureHolder structureHolder) throws AlreadyExistsException, IOException;

    Structures getAll(int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException;

    Structures getAllIdLike(String idLike, int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException;

    StructureHolder getStructureById(String id) throws IOException;

    Structures getAllPublishedAndIdLike(String idLike, int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException;

    Structures getAllPublished(int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException;

    Structures getAllPublishedForNamespace(String namespace, int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException;

    Structures getAllNamespaceEquals(String namespace, int numberPerPage, int page, String columnToSortBy, boolean descending) throws IOException;

    void delete(String structureId) throws IOException, PermenentTraitException;

    void publish(String structureId) throws IOException;

    void addTraitToStructure(String structureId, String fieldName, Trait newTrait) throws IOException;

    void insertTraitBeforeAnotherForStructure(String structureId, String movingTraitName, String insertBeforeTraitName) throws IOException;

    void insertTraitAfterAnotherForStructure(String structureId, String movingTraitName, String insertAfterTraitName) throws IOException;

    String getJsonSchema(String structureId) throws IOException;

    String getElasticSearchBaseMapping(String structureId) throws IOException;

}
