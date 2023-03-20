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

package org.kinotic.structures.internal.trait.lifecycle;

import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.domain.TypeCheckMap;
import org.kinotic.structures.api.domain.traitlifecycle.HasOnBeforeModify;
import org.kinotic.structures.api.services.ItemService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class UpdatedTime implements HasOnBeforeModify {

    private final ItemService itemService;

    public UpdatedTime(@Lazy ItemService itemService){
        this.itemService = itemService;
    }


    @Override
    public TypeCheckMap beforeModify(TypeCheckMap obj, Structure structure, String fieldName) throws Exception {
        //we should be able to turn on internal versioning, and we don't have to do this logic at all.
        // if we support partial updates,
        //    might not have an updateTime, if we are performing partial updates.
        //    if updatedTime exists we check it against the value stored, they must be equal to persist
        // not sure if this even makes sense, but not sure how to best support concurrency here.
//        if (obj.has("updatedTime")) {
//            // we want to make sure we already have the item in storage - otherwise you must create the item first
//            Optional<TypeCheckMap> stored = itemService.getById(structure, obj.getString("id"));
//            if (stored.isEmpty()) {
//                throw new IllegalArgumentException("Item does not exist in storage, you must first create the item before trying to modify it.");
//            }
//            long updatedTime = obj.getLong("updatedTime");
//            if (updatedTime != stored.get().getLong("updatedTime")) {
//                throw new OptimisticLockingFailureException("Item has been modified since you received it, please reload and try modifications again.");
//            }
//        }

        obj.amend("updatedTime", System.currentTimeMillis());
        return obj;
    }
}
