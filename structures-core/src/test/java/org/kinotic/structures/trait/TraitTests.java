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

package org.kinotic.structures.trait;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kinotic.structures.ElasticsearchTestBase;
import org.kinotic.structures.api.domain.AlreadyExistsException;
import org.kinotic.structures.api.domain.PermenentTraitException;
import org.kinotic.structures.api.domain.Trait;
import org.kinotic.structures.api.services.TraitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TraitTests extends ElasticsearchTestBase {

	@Autowired
	TraitService traitService;

	@Test
	public void createAndDeleteTrait() throws Exception {
		Trait test = new Trait();
		test.setName("Test");
		test.setDescribeTrait("Testing");
		test.setSchema("{ \"type\": \"string\" }");
		test.setEsSchema("{ \"type\": \"keyword\" }");
		test.setRequired(false);

		test = traitService.save(test);

		traitService.delete(test.getId());

		Optional<Trait> isGone = traitService.getTraitByName(test.getId());
		if(!isGone.isEmpty()){
			throw new IllegalStateException("We should not have a value after deletion, but we still do.");
		}
	}

	@Test
	public void createAndTryDuplicateTrait() {
		Assertions.assertThrows(AlreadyExistsException.class, () -> {
			Trait test = new Trait();
			test.setName("TryClone");
			test.setDescribeTrait("TryClone");
			test.setSchema("{ \"type\": \"string\" }");
			test.setEsSchema("{ \"type\": \"text\" }");
			test.setRequired(false);
			traitService.save(test);

			try {
				Trait clone = new Trait();
				clone.setName("TryClone");
				clone.setDescribeTrait("TryClone");
				clone.setSchema("{ \"type\": \"string\" }");
				clone.setEsSchema("{ \"type\": \"text\" }");
				clone.setRequired(false);
				traitService.save(clone);
			} catch (AlreadyExistsException | PermenentTraitException aee) {
				traitService.delete(test.getId());
				throw aee;
			}
		});
	}

}
