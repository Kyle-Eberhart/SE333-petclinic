# Cursor Agent Instructions

Goal: Generate unit tests for the Spring Petclinic project.

Focus only on these classes:

- Owner
- Pet
- Visit
- OwnerController

Tasks:

1. Inspect logic in the target classes.
2. Generate JUnit tests covering:
   - constructors
   - getters/setters
   - validation logic
   - service interactions
3. Run tests with Maven.
4. Inspect JaCoCo coverage results.
5. Generate additional tests for uncovered logic.
6. Update docs/coverage-iteration-log.md.

Do not generate tests for the entire repository.

Keep tests simple.
