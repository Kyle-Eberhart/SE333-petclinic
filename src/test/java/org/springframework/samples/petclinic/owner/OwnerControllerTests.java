/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package org.springframework.samples.petclinic.owner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Minimal JUnit tests for {@link OwnerController}.
 */
@WebMvcTest(OwnerController.class)
class OwnerControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OwnerRepository owners;

	@Test
	void initCreationFormReturnsCreateFormView() throws Exception {
		mockMvc.perform(get("/owners/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void initFindFormReturnsFindView() throws Exception {
		mockMvc.perform(get("/owners/find")).andExpect(status().isOk()).andExpect(view().name("owners/findOwners"));
	}

	@Test
	void showOwnerReturnsOwnerDetailsWhenFound() throws Exception {
		Owner owner = new Owner();
		owner.setId(1);
		owner.setFirstName("Jane");
		owner.setLastName("Doe");
		owner.setAddress("123 Main St");
		owner.setCity("Chicago");
		owner.setTelephone("1234567890");
		given(owners.findById(1)).willReturn(Optional.of(owner));

		mockMvc.perform(get("/owners/1")).andExpect(status().isOk()).andExpect(view().name("owners/ownerDetails"));
	}

	@Test
	void initUpdateOwnerFormReturnsCreateFormView() throws Exception {
		Owner owner = new Owner();
		owner.setId(1);
		given(owners.findById(1)).willReturn(Optional.of(owner));

		mockMvc.perform(get("/owners/1/edit"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void processCreationFormRedirectsWhenValid() throws Exception {
		doAnswer(invocation -> {
			Owner o = invocation.getArgument(0);
			o.setId(1);
			return o;
		}).when(owners).save(any(Owner.class));

		mockMvc
			.perform(post("/owners/new").param("firstName", "Jane")
				.param("lastName", "Doe")
				.param("address", "123 Main St")
				.param("city", "Chicago")
				.param("telephone", "1234567890"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/owners/1"))
			.andExpect(flash().attribute("message", "New Owner Created"));
	}

	@Test
	void processFindFormWithSingleResultRedirectsToOwner() throws Exception {
		Owner owner = new Owner();
		owner.setId(1);
		owner.setLastName("Doe");
		Page<Owner> page = new PageImpl<>(java.util.List.of(owner));
		given(owners.findByLastNameStartingWith(eq("Doe"), any(Pageable.class))).willReturn(page);

		mockMvc.perform(get("/owners").param("lastName", "Doe"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/owners/1"));
	}

	@Test
	void processFindFormWithMultipleResultsReturnsOwnersList() throws Exception {
		Owner o1 = new Owner();
		o1.setId(1);
		Owner o2 = new Owner();
		o2.setId(2);
		Page<Owner> page = new PageImpl<>(java.util.List.of(o1, o2),
				org.springframework.data.domain.PageRequest.of(0, 5), 2);
		given(owners.findByLastNameStartingWith(eq("Doe"), any(Pageable.class))).willReturn(page);

		mockMvc.perform(get("/owners").param("lastName", "Doe"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/ownersList"));
	}

	@Test
	void processUpdateOwnerFormRedirectsWhenValidAndIdMatches() throws Exception {
		Owner owner = new Owner();
		owner.setId(1);
		owner.setFirstName("Jane");
		owner.setLastName("Doe");
		owner.setAddress("123 Main St");
		owner.setCity("Chicago");
		owner.setTelephone("1234567890");
		given(owners.findById(1)).willReturn(Optional.of(owner));
		given(owners.save(any(Owner.class))).willReturn(owner);

		mockMvc
			.perform(post("/owners/1/edit").param("id", "1")
				.param("firstName", "Jane")
				.param("lastName", "Doe")
				.param("address", "123 Main St")
				.param("city", "Chicago")
				.param("telephone", "1234567890"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/owners/1"))
			.andExpect(flash().attribute("message", "Owner Values Updated"));
	}

	@Test
	void processCreationFormWithValidationErrorsReturnsForm() throws Exception {
		mockMvc
			.perform(post("/owners/new").param("firstName", "")
				.param("lastName", "")
				.param("address", "")
				.param("city", "")
				.param("telephone", ""))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void processFindFormWithNoResultsReturnsFindOwners() throws Exception {
		Page<Owner> emptyPage = new PageImpl<>(java.util.List.of(),
				org.springframework.data.domain.PageRequest.of(0, 5), 0);
		given(owners.findByLastNameStartingWith(eq("Nonexistent"), any(Pageable.class))).willReturn(emptyPage);

		mockMvc.perform(get("/owners").param("lastName", "Nonexistent"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/findOwners"));
	}

	@Test
	void processFindFormWithoutLastNameUsesBroadSearchAndReturnsList() throws Exception {
		Owner o1 = new Owner();
		o1.setId(1);
		Owner o2 = new Owner();
		o2.setId(2);
		Page<Owner> page = new PageImpl<>(java.util.List.of(o1, o2),
				org.springframework.data.domain.PageRequest.of(0, 5), 2);
		given(owners.findByLastNameStartingWith(eq(""), any(Pageable.class))).willReturn(page);

		mockMvc.perform(get("/owners")).andExpect(status().isOk()).andExpect(view().name("owners/ownersList"));
	}

	@Test
	void processUpdateOwnerFormWithValidationErrorsReturnsForm() throws Exception {
		Owner owner = new Owner();
		owner.setId(1);
		given(owners.findById(1)).willReturn(Optional.of(owner));

		mockMvc
			.perform(post("/owners/1/edit").param("id", "1")
				.param("firstName", "")
				.param("lastName", "")
				.param("address", "")
				.param("city", "")
				.param("telephone", ""))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void processUpdateOwnerFormWithIdMismatchRedirectsToEditWithError() throws Exception {
		Owner owner = new Owner();
		owner.setId(2);
		owner.setFirstName("Jane");
		owner.setLastName("Doe");
		owner.setAddress("123 Main St");
		owner.setCity("Chicago");
		owner.setTelephone("1234567890");
		given(owners.findById(1)).willReturn(Optional.of(owner));

		mockMvc
			.perform(post("/owners/1/edit").param("id", "2")
				.param("firstName", "Jane")
				.param("lastName", "Doe")
				.param("address", "123 Main St")
				.param("city", "Chicago")
				.param("telephone", "1234567890"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/owners/1/edit"))
			.andExpect(flash().attribute("error", "Owner ID mismatch. Please try again."));
	}

}
