<<<<<<< HEAD
# Employee Management System

A production-style Spring Boot REST API for managing employees. It demonstrates CRUD design, JPA/Hibernate, PostgreSQL, validation, JWT authentication, role-based access control, pagination, structured error responses, logging, OpenAPI documentation, Docker, and unit tests.

## Stack

- Java 11, Spring Boot 2.7, Spring Security, Spring Data JPA
- PostgreSQL, Hibernate, Bean Validation
- JJWT, springdoc OpenAPI / Swagger UI
- Docker Compose, JUnit 5 and Mockito

## Run locally

Create a PostgreSQL database and set connection details (the defaults are listed in `application.yml`), then run:

```powershell
mvn clean test
mvn spring-boot:run
```

Alternatively, package the application and start the API with PostgreSQL:

```powershell
docker compose up --build
```

Swagger UI is available at `http://localhost:8080/swagger-ui.html`. The generated OpenAPI JSON is at `/v3/api-docs`.

## First login

On startup, the application creates the configured administrator if it does not exist:

```text
email: admin@ems.local
password: ChangeMe123!
```

Override `ADMIN_EMAIL`, `ADMIN_PASSWORD`, and `JWT_SECRET` outside local development. Never use the sample credentials or secret in a deployed environment.

## API flow

1. `POST /api/v1/auth/login` with the administrator credentials.
2. Copy `accessToken` from the response.
3. In Swagger, select **Authorize** and enter `Bearer <accessToken>`.
4. Create, update, or delete employees as an `ADMIN`; both `ADMIN` and `USER` may read employees.

Registering through `POST /api/v1/auth/register` always creates a `USER`, preventing privilege escalation.

## Main endpoints

| Method | Endpoint | Access | Purpose |
| --- | --- | --- | --- |
| POST | `/api/v1/auth/register` | Public | Create a user account |
| POST | `/api/v1/auth/login` | Public | Obtain a JWT |
| GET | `/api/v1/employees?page=0&size=10&sortBy=lastName&direction=asc` | USER / ADMIN | List employees |
| GET | `/api/v1/employees/{id}` | USER / ADMIN | Get one employee |
| POST | `/api/v1/employees` | ADMIN | Add an employee |
| PUT | `/api/v1/employees/{id}` | ADMIN | Update an employee |
| DELETE | `/api/v1/employees/{id}` | ADMIN | Delete an employee |

## Interview talking points

- Passwords are BCrypt-hashed; only a signed, expiring JWT is accepted after login.
- Authorization is enforced at the service boundary using method security, not only in the controller.
- The list API uses `Pageable` and validates sort fields to avoid invalid/dangerous queries.
- `@RestControllerAdvice` turns validation, business, and authentication failures into predictable JSON responses.
- Docker Compose starts the API only after PostgreSQL has passed its health check.
=======

A RESTful Employee Management System built with Spring Boot, Spring Data JPA, and PostgreSQL. Implements CRUD operations, layered architecture, request validation, exception handling, and database integration for efficient employee record management.
>>>>>>> bccccf4ac0d388fbe5279bc286b03fb8fd7635ba
