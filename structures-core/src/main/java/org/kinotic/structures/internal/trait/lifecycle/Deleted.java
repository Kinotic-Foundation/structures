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
import org.kinotic.structures.api.domain.traitlifecycle.HasOnBeforeDelete;
import org.kinotic.structures.api.domain.traitlifecycle.HasOnBeforeModify;
import org.kinotic.structures.internal.repositories.ReferenceLogElasticRepository;
import org.springframework.stereotype.Component;

@Component
public class Deleted implements HasOnBeforeDelete, HasOnBeforeModify {

    private final ReferenceLogElasticRepository referenceLogElasticRepository;

    public Deleted(ReferenceLogElasticRepository referenceLogElasticRepository){
        this.referenceLogElasticRepository = referenceLogElasticRepository;
    }

    @Override
    public TypeCheckMap beforeModify(TypeCheckMap obj, Structure structure, String fieldName) throws Exception {
        if(!obj.has("deleted")){
            obj.amend("deleted", false);
        }
        return obj;
    }

    @Override
    public TypeCheckMap beforeDelete(TypeCheckMap obj, final Structure structure, String fieldName) throws Exception {
//        Iterable<ReferenceLog> references = referenceLogElasticRepository.findByReferencesContains(structure.getId().toLowerCase() + "_" + obj.getString("id"));
//        StringBuilder referenceHolderIds = new StringBuilder();
//        for (ReferenceLog log : references) {
//            if (referenceHolderIds.length() > 0) {
//                referenceHolderIds.append(",");
//            }
//
//            referenceHolderIds.append(log.getOwnerStructureId() + ":" + log.getOwnerId());
//        }
//
//        if (referenceHolderIds.length() > 0) {
//            throw new IsReferencedException("Item in " + structure.getId() + " store that you are trying to delete has references and cannot be deleted until all references are removed.\n " + referenceHolderIds);
//        }
//
//        Optional<ReferenceLog> opt = referenceLogElasticRepository.findByOwnerIdAndOwnerStructureId(obj.getString("id"), structure.getId().toLowerCase());
//        if (opt.isPresent()) {
//            referenceLogElasticRepository.deleteById(opt.get().getId());
//        }

        obj.amend("deleted", true);
        return obj;
    }

}
