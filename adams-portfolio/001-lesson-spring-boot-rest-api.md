# Lesson 001 – Spring Boot REST API Structure for a Portfolio

In this project, our Spring Boot backend will expose REST APIs that your Angular frontend can call.

## Key building blocks

1. **Entity (model)**

   - Java class mapped to a database table with JPA annotations.
   - Example: `Profile`, `Experience`, `Project`.

2. **Repository**

   - Interface that extends `JpaRepository` or similar.
   - Spring Data implements it at runtime, giving you CRUD methods.

3. **Service (optional but recommended)**

   - Class that holds business logic.
   - Not strictly required for very small apps, but a good habit.

4. **Controller**

   - Class annotated with `@RestController`.
   - Defines HTTP endpoints like `GET /api/profile`, `GET /api/projects`.
   - Uses repositories (or services) to fetch/save data.

5. **Configuration**
   - `application.properties` connects to MySQL and configures JPA.

## How it flows (for a GET request)

`Angular` → HTTP GET `http://localhost:8080/api/profile` → `ProfileController` → `ProfileRepository` → MySQL → returns JSON → `Angular` displays it.

We will implement this flow step by step for your portfolio:

- First, a `Profile` entity + repository + controller.
- Then, `Experience` and `Project`.
- Finally, Angular will call these endpoints.
