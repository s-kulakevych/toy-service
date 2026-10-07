# toy-service

Spring Boot 4 + Kotlin + Gradle toy REST service for a `Product` entity, persisted in H2.

| Stack | Version |
|---|---|
| Spring Boot | 4.1.1 |
| Kotlin | 2.2.21 |
| Gradle (wrapper) | 9.1.0 |
| Java toolchain | 21 |
| springdoc-openapi (Swagger UI) | 3.1.0 |

## Run

```bash
./gradlew bootRun        # Windows: gradlew.bat bootRun
./gradlew test
```

## Endpoints (basic auth, credentials in `application.properties`: `admin` / `admin123`)

| Method | Path | Result |
|---|---|---|
| POST | `/product` | 201 + `Location` header, 400 on invalid body |
| GET | `/product/{id}` | 200, 404 if missing |
| DELETE | `/product/{id}` | 204, 404 if missing |

```bash
curl -u admin:admin123 -H 'Content-Type: application/json' \
     -d '{"name":"Wooden train","description":"3 wagons","price":19.99}' localhost:8080/product
curl -u admin:admin123 localhost:8080/product/1
curl -u admin:admin123 -X DELETE localhost:8080/product/1
```

## API docs

- Swagger UI: http://localhost:8080/swagger-ui.html (public; use **Authorize** to call endpoints)
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Notes on the Spring Boot 4 upgrade

- `springfox-boot-starter:3.0.0` is abandoned (last release 2020) and does not work with Spring Boot 3/4
  (it targets `javax.*`, Boot 4 is `jakarta.*` / Spring Framework 7). It is replaced by its de-facto successor
  **springdoc-openapi** (`springdoc-openapi-starter-webmvc-ui`), which provides the OpenAPI doc and Swagger UI.
- `spring-boot-starter-web` is renamed `spring-boot-starter-webmvc` in Boot 4 (the old name is a deprecated alias).
- Jackson 3: the Kotlin module is `tools.jackson.module:jackson-module-kotlin`.
- Test starters are modular in Boot 4 (`spring-boot-starter-webmvc-test`, `-security-test`).

## Layout

- `ProductController` – thin HTTP layer (validation, status codes, OpenAPI annotations)
- `ProductService` – all business logic and transactions
- `ProductRepository` – Spring Data JPA repository
- `Product` – JPA entity; `ProductDtos` – request/response models
- `SecurityConfig` – basic auth, Swagger paths public; `OpenApiConfig` – API info + basic auth scheme
