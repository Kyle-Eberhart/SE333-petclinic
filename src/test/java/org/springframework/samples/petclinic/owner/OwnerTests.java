/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package org.springframework.samples.petclinic.owner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Minimal JUnit tests for {@link Owner}.
 */
class OwnerTests {

	private Owner owner;

	@BeforeEach
	void setUp() {
		owner = new Owner();
		owner.setFirstName("Jane");
		owner.setLastName("Doe");
		owner.setAddress("123 Main St");
		owner.setCity("Chicago");
		owner.setTelephone("1234567890");
	}

	@Test
	void gettersAndSettersWork() {
		assertThat(owner.getFirstName()).isEqualTo("Jane");
		assertThat(owner.getLastName()).isEqualTo("Doe");
		assertThat(owner.getAddress()).isEqualTo("123 Main St");
		assertThat(owner.getCity()).isEqualTo("Chicago");
		assertThat(owner.getTelephone()).isEqualTo("1234567890");
	}

	@Test
	void getPetsReturnsEmptyListInitially() {
		assertThat(owner.getPets()).isEmpty();
	}

	@Test
	void addPetAddsNewPetOnly() {
		Pet newPet = new Pet();
		newPet.setName("Max");
		owner.addPet(newPet);
		assertThat(owner.getPets()).hasSize(1).contains(newPet);
	}

	@Test
	void addPetDoesNotAddExistingPet() {
		Pet existingPet = new Pet();
		existingPet.setId(1);
		existingPet.setName("Max");
		owner.addPet(existingPet);
		assertThat(owner.getPets()).isEmpty();
	}

	@Test
	void getPetByNameReturnsMatchingPet() {
		Pet pet = new Pet();
		pet.setName("Max");
		owner.addPet(pet);
		assertThat(owner.getPet("Max")).isSameAs(pet);
		assertThat(owner.getPet("max")).isSameAs(pet);
		assertThat(owner.getPet("Other")).isNull();
	}

	@Test
	void getPetByIdReturnsMatchingPet() {
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName("Max");
		owner.getPets().add(pet);
		assertThat(owner.getPet(1)).isSameAs(pet);
		assertThat(owner.getPet(2)).isNull();
	}

	@Test
	void getPetByNameWithIgnoreNewExcludesNewPetsWhenTrue() {
		Pet newPet = new Pet();
		newPet.setName("Max");
		owner.addPet(newPet);
		assertThat(owner.getPet("Max", true)).isNull();
		assertThat(owner.getPet("Max", false)).isSameAs(newPet);
	}

	@Test
	void addVisitAddsVisitToPet() {
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName("Max");
		owner.getPets().add(pet);
		Visit visit = new Visit();
		visit.setDescription("Checkup");
		owner.addVisit(1, visit);
		assertThat(pet.getVisits()).contains(visit);
	}

	@Test
	void addVisitThrowsWhenPetIdNull() {
		Visit visit = new Visit();
		assertThatIllegalArgumentException().isThrownBy(() -> owner.addVisit(null, visit))
			.withMessageContaining("Pet identifier must not be null");
	}

	@Test
	void addVisitThrowsWhenVisitNull() {
		Pet pet = new Pet();
		pet.setId(1);
		owner.getPets().add(pet);
		assertThatIllegalArgumentException().isThrownBy(() -> owner.addVisit(1, null))
			.withMessageContaining("Visit must not be null");
	}

	@Test
	void addVisitThrowsWhenPetNotFound() {
		Visit visit = new Visit();
		assertThatIllegalArgumentException().isThrownBy(() -> owner.addVisit(99, visit))
			.withMessageContaining("Invalid Pet identifier");
	}

	@Test
	void toStringContainsExpectedFields() {
		owner.setId(1);
		String s = owner.toString();
		assertThat(s).contains("1", "Doe", "Jane", "123 Main St", "Chicago", "1234567890");
	}

	@Test
	void getPetByIdReturnsNullWhenOnlyNewPetsInList() {
		Pet newPet = new Pet();
		newPet.setName("Max");
		owner.addPet(newPet);
		assertThat(owner.getPet(1)).isNull();
	}

}
