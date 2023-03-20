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

import org.kinotic.structures.api.domain.Structure;
import org.kinotic.structures.api.services.StructureService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NameTests {

    @Autowired
    private StructureService structureService;

    /**
     * Structure Id Tests to ensure we do not allow unsupported ElasticSearch index Ids.
     */
    @Test
    public void tryCreateStructureWithIdStartsWith_(){
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setId("_TEST");
            structure.setDescription("Test Structure Id that starts with underscore (_)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdStartsWithADash(){
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setId("-TEST");
            structure.setDescription("Test Structure Id that starts with a dash (-)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdStartsWithAPlus(){
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setId("+TEST");
            structure.setDescription("Test Structure Id that starts with a plus (+)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdStartsWithPeriod(){
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setId(".TEST");
            structure.setDescription("Test Structure Id that contains a period (.)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdStartsWithDotDot(){
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setId("..TEST");
            structure.setDescription("Test Structure Id that contains a dotdot (..)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsBackslash() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setId("TEST\\ING");
            structure.setDescription("Test Structure Id that contains a backslash (\\)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsForwardSlash() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setId("TES/T");
            structure.setDescription("Test Structure Id that contains a forward slash (/)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsAsterisk() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setId("TE*ST");
            structure.setDescription("Test Structure Id that contains an asterisk (*)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsQuestionMark(){
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setId("TEST?");
            structure.setDescription("Test Structure Id that contains a question mark (?)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsQuotationMarks(){
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setId("T\"EST");
            structure.setDescription("Test Structure Id that contains quotation marks (\")");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsLessThan() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setId("TE<ST");
            structure.setDescription("Test Structure Id that contains a less than symbol (<)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsGreaterThan() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setId("TE>ST");
            structure.setDescription("Test Structure Id that contains a greater than symbol (>)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsPipe(){
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setId("TE|ST");
            structure.setDescription("Test Structure Id that contains a pipe operator (|)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsSpace() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setId("TE ST");
            structure.setDescription("Test Structure Id that contains a space ( )");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsComma() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setId("TES,T");
            structure.setDescription("Test Structure Id that contains a comma (,)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsHash() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setId("TEST#");
            structure.setDescription("Test Structure Id that contains a hash (#)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsColon() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setId("TES:T");
            structure.setDescription("Test Structure Id that contains a colon (:)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdContainsSemiColon() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Structure structure = new Structure();
            structure.setId("TES;T");
            structure.setDescription("Test Structure Id that contains a semi colon (;)");
            structureService.save(structure);
        });
    }

    @Test
    public void tryCreateStructureWithIdLongerThan255(){
        Assertions.assertThrows(IllegalStateException.class, () -> {
            String tooLong = "";
            for (int i = 0; i < 257; i++) {
                tooLong += "a";
            }
            Structure structure = new Structure();
            structure.setId(tooLong);
            structure.setDescription("Test Structure Id that is longer than 255 chars");
            structureService.save(structure);
        });
    }
}
