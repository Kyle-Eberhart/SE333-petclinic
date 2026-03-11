/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package org.springframework.samples.petclinic.owner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Minimal JUnit tests for {@link Pet}.
 */
class PetTests {

	private Pet pet;

	@BeforeEach
	void setUp() {
		pet = new Pet();
		pet.setName("Max");
		pet.setBirthDate(LocalDate.of(2020, 3, 15));
	}

	@Test
	void getNameReturnsSetName() {
		assertThat(pet.getName()).isEqualTo("Max");
	}

	@Test
	void getBirthDateReturnsSetDate() {
		assertThat(pet.getBirthDate()).isEqualTo(LocalDate.of(2020, 3, 15));
	}

	@Test
	void getTypeReturnsSetType() {
		PetType type = new PetType();
		type.setName("Dog");
		pet.setType(type);
		assertThat(pet.getType()).isSameAs(type);
	}

	@Test
	void getVisitsReturnsEmptyInitially() {
		assertThat(pet.getVisits()).isEmpty();
	}

	@Test
	void addVisitAddsVisitToCollection() {
		Visit visit = new Visit();
		visit.setDescription("Annual checkup");
		pet.addVisit(visit);
		assertThat(pet.getVisits()).hasSize(1).contains(visit);
	}

	@Test
	void addVisitMaintainsOrder() {
		Visit v1 = new Visit();
		v1.setDescription("First");
		Visit v2 = new Visit();
		v2.setDescription("Second");
		pet.addVisit(v1);
		pet.addVisit(v2);
		assertThat(pet.getVisits()).containsExactly(v1, v2);
	}

}
