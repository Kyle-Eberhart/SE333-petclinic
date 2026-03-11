# Coverage Iteration Log

Target classes (scope; ClinicServiceImpl does not exist in this repository):

- Owner
- Pet
- Visit
- OwnerController

JaCoCo report is generated on `mvn test` at `target/site/jacoco/index.html` and `target/site/jacoco/jacoco.xml`.

---

## Iteration 1 (Baseline)

Initial JUnit tests added for the four target classes.

**Owner** (`OwnerTests`): getters/setters, getPets, addPet (new vs existing), getPet by name/id and getPet(name, ignoreNew), addVisit and its validation (null petId/visit, invalid pet), toString.

**Pet** (`PetTests`): name, birthDate, type, getVisits, addVisit and order.

**Visit** (`VisitTests`): default date, get/set date and description, isNew.

**OwnerController** (`OwnerControllerTests`): initCreationForm, initFindForm, showOwner (found), initUpdateOwnerForm, processCreationForm (valid), processFindForm (single/multiple results), processUpdateOwnerForm (valid, id matches).

**Baseline coverage (from JaCoCo):**

- Owner, Pet, Visit: full line coverage; Owner had a few uncovered branches (getPet by id when only new pets, getPet(name, ignoreNew) variants).
- OwnerController: main flows covered; uncovered: processCreationForm/processUpdateOwnerForm when `result.hasErrors()`, processFindForm when no results, processUpdateOwnerForm when id mismatch.

---

## Iteration 2 (Improvements)

Additional tests to cover previously uncovered branches and edge cases.

**Owner:**

- `getPetByIdReturnsNullWhenOnlyNewPetsInList`: getPet(id) when list contains only new pets (no id set) returns null.

**OwnerController:**

- `processCreationFormWithValidationErrorsReturnsForm`: POST /owners/new with empty/invalid fields returns 200 and create form view (hasErrors path).
- `processFindFormWithNoResultsReturnsFindOwners`: GET /owners with lastName that yields empty page returns findOwners view (rejectValue path).
- `processUpdateOwnerFormWithValidationErrorsReturnsForm`: POST /owners/1/edit with validation errors returns form view (hasErrors path).
- `processUpdateOwnerFormWithIdMismatchRedirectsToEditWithError`: POST with form id != path ownerId redirects to edit and sets flash error (id mismatch path).

**Result:** Improved branch coverage for Owner and OwnerController; validation and error paths for creation, find (no results), and update (errors, id mismatch) are now exercised.

---

## Summary

Two iterations of test generation: first established baseline coverage for Owner, Pet, Visit, and OwnerController; second added minimal tests for uncovered methods, branches, and edge cases (validation errors, empty find results, id mismatch). Coverage for the four target classes improved without over-engineering.
