/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package org.springframework.samples.petclinic.owner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Minimal JUnit tests for {@link Visit}.
 */
class VisitTests {

	@Test
	void defaultConstructorSetsDateToToday() {
		Visit visit = new Visit();
		assertThat(visit.getDate()).isEqualTo(LocalDate.now());
	}

	@Test
	void getDateReturnsSetDate() {
		Visit visit = new Visit();
		LocalDate date = LocalDate.of(2024, 6, 10);
		visit.setDate(date);
		assertThat(visit.getDate()).isEqualTo(date);
	}

	@Test
	void getDescriptionReturnsSetDescription() {
		Visit visit = new Visit();
		visit.setDescription("Vaccination");
		assertThat(visit.getDescription()).isEqualTo("Vaccination");
	}

	@Test
	void isNewWhenIdIsNull() {
		Visit visit = new Visit();
		assertThat(visit.isNew()).isTrue();
	}

	@Test
	void isNotNewWhenIdIsSet() {
		Visit visit = new Visit();
		visit.setId(1);
		assertThat(visit.isNew()).isFalse();
	}

}
