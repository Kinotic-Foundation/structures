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

package org.kinotic.structures.structure;

import org.kinotic.structures.ElasticsearchTestBase;
import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.StructureService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kinotic.structures.internal.config.StructuresProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NameTests extends ElasticsearchTestBase {

    @Autowired
    private StructureService structureService;
    @Autowired
    private StructuresProperties structuresProperties;

    /**
     * Structure Id Tests to ensure we do not allow unsupported ElasticSearch index Ids.
     */
    // TODO: figure out how to change the index-prefix on a per file basis so we can
    //  write tests against this config.
    @Test
    public void tryCreateStructureWithIdStartsWith_(){
        if(structuresProperties.getIndexPrefix().isBlank()){
            Assertions.assertThrows(IllegalStateException.class, () -> {
                Structure structure = new Structure();
                structure.setName("_TEST");
                structure.setNamespace("_org-kinotic");
                structure.setDescription("Test Structure Id that starts with underscore (_)");
                structureService.save(structure);
            });
        }
    }

    @Test
    public void tryCreateStructureWithIdStartsWithADash(){
        if(structuresProperties.getIndexPrefix().isBlank()) {
            Assertions.assertThrows(IllegalStateException.class, () -> {
                Structure structure = new Structure();
                structure.setName("-TEST");
                structure.setNamespace("-org-kinotic");
                structure.setDescription("Test Structure Id that starts with a dash (-)");
                structureService.save(structure);
            });
        }
    }

    @Test
    public void tryCreateStructureWithIdStartsWithAPlus(){
        if(structuresProperties.getIndexPrefix().isBlank()) {
            Assertions.assertThrows(IllegalStateException.class, () -> {
                Structure structure = new Structure();
                structure.setName("+TEST");
                structure.setNamespace("+org-kinotic");
                structure.setDescription("Test Structure Id that starts with a plus (+)");
                structureService.save(structure);
            });
        }
    }

    @Test
    public void tryCreateStructureWithIdStartsWithPeriod(){
        if(structuresProperties.getIndexPrefix().isBlank()) {
            Assertions.assertThrows(IllegalStateException.class, () -> {
                Structure structure = new Structure();
                structure.setName(".TEST");
                structure.setNamespace(".org.kinotic");
                structure.setDescription("Test Structure Id that contains a period (.)");
                structureService.save(structure);
            });
        }
    }

    @Test
    public void tryCreateStructureWithIdStartsWithDotDot(){
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setName("..TEST");
            structure.setNamespace("..org.kinotic");
            structure.setDescription("Test Structure Id that contains a dotdot (..)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsBackslash() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setName("TEST\\ING");
            structure.setNamespace("org.kinotic");
            structure.setDescription("Test Structure Id that contains a backslash (\\)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsForwardSlash() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setName("TES/T");
            structure.setNamespace("org.kinotic");
            structure.setDescription("Test Structure Id that contains a forward slash (/)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsAsterisk() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setName("TE*ST");
            structure.setNamespace("org.kinotic");
            structure.setDescription("Test Structure Id that contains an asterisk (*)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsQuestionMark(){
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setName("TEST?");
            structure.setNamespace("org.kinotic");
            structure.setDescription("Test Structure Id that contains a question mark (?)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsQuotationMarks(){
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setName("T\"EST");
            structure.setNamespace("org.kinotic");
            structure.setDescription("Test Structure Id that contains quotation marks (\")");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsLessThan() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setName("TE<ST");
            structure.setNamespace("org.kinotic");
            structure.setDescription("Test Structure Id that contains a less than symbol (<)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsGreaterThan() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setName("TE>ST");
            structure.setNamespace("org.kinotic");
            structure.setDescription("Test Structure Id that contains a greater than symbol (>)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsPipe(){
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setName("TE|ST");
            structure.setNamespace("org.kinotic");
            structure.setDescription("Test Structure Id that contains a pipe operator (|)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsSpace() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setName("TE ST");
            structure.setNamespace("org.kinotic");
            structure.setDescription("Test Structure Id that contains a space ( )");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsComma() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setName("TES,T");
            structure.setNamespace("org.kinotic");
            structure.setDescription("Test Structure Id that contains a comma (,)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsHash() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setName("TEST#");
            structure.setNamespace("org.kinotic");
            structure.setDescription("Test Structure Id that contains a hash (#)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsDoubleDot() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setName(".TEST");
            structure.setNamespace("org.kinotic.");
            structure.setDescription("Test Structure Id that contains a double dot (..)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsColon() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setName("TES:T");
            structure.setNamespace("org.kinotic");
            structure.setDescription("Test Structure Id that contains a colon (:)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsSemiColon() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setName("TES;T");
            structure.setNamespace("org.kinotic");
            structure.setDescription("Test Structure Id that contains a semi colon (;)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdLongerThan255(){
        Assertions.assertThrows(IllegalStateException.class, () -> {
            String tooLong = "";
            for (int i = 0; i < 246; i++) { // leaving some room for the namespace to go over
                tooLong += "a";
            }
            Structure structure = new Structure();
            structure.setName(tooLong);
            structure.setNamespace("org.kinotic");
            structure.setDescription("Test Structure Namespace+Id that is longer than 255 chars");
            structureService.save(structure);
        });
    }
}
