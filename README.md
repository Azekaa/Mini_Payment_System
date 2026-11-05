# Mini Payment System

A backend payment processing application built with Java and Spring Boot. The project focuses on backend development, database management, application security, caching, and asynchronous message processing.

## Overview

Mini Payment System is a backend project designed to implement payment-related functionality using modern Java technologies.

The application uses a layered Spring Boot architecture and integrates several infrastructure components to support data persistence, security, caching, and event-driven communication.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Main programming language |
| Spring Boot 3.5.3 | Backend framework |
| Spring Web | REST API development |
| Spring Data JPA | Database access and ORM |
| PostgreSQL 16 | Relational database |
| Spring Security | Application security |
| Redis 8.4 | Caching and fast data access |
| Apache Kafka | Asynchronous messaging |
| Liquibase | Database schema migrations |
| MapStruct | DTO and entity mapping |
| Lombok | Boilerplate code reduction |
| Swagger / OpenAPI | API documentation |
| Maven | Dependency management and build automation |
| Docker Compose | Infrastructure management |

## Architecture

The project follows a layered architecture to separate responsibilities and make the application easier to maintain.

Typical application layers:

- **Controller** — handles incoming HTTP requests and returns responses.
- **Service** — contains business logic.
- **Repository** — manages database operations.
- **Entity** — represents database entities.
- **DTO** — transfers data between application layers and API clients.
- **Mapper** — converts entities into DTOs and vice versa.
- **Security** — handles authentication and authorization.
- **Audit** — contains audit-related functionality.

## Key Technologies and Concepts

### Database Management
- PostgreSQL for persistent data storage.
- Spring Data JPA and Hibernate for database interaction.
- Liquibase for version-controlled database migrations.

### Security
- Spring Security integration.
- Authentication and authorization configuration.
- Protection of application endpoints.

### Caching
- Redis integration for caching.
- Reducing unnecessary database queries.
- Improving response times for frequently accessed data.

### Asynchronous Messaging
- Apache Kafka integration.
- Message-based communication between application components.
- Support for asynchronous processing.

### API Documentation
- Swagger UI and OpenAPI integration.
- API exploration and testing through an interactive interface.

## Getting Started

### Prerequisites

Make sure you have installed:

- Java 17 or later
- Docker Desktop
- Git

### 1. Clone the repository

```bash
git clone https://github.com/Azekaa/Mini_Payment_System.git
```

```bash
cd Mini_Payment_System
```

### 2. Start infrastructure services

The project includes a Docker Compose configuration for PostgreSQL, Redis, and Kafka.

```bash
docker compose -f Docker-compose.yml up -d
```

To check running containers:

```bash
docker compose -f Docker-compose.yml ps
```

### 3. Configure the application

Before starting the application, check the configuration in:

`src/main/resources/application.yml`

Make sure the database, Redis, and Kafka connection settings match your local environment.

The default PostgreSQL configuration in Docker Compose is:

| Parameter | Value |
|---|---|
| Database | payment_system |
| Username | postgres |
| Password | postgres |
| Port | 5432 |

### 4. Run the application

Using Maven Wrapper:

**Linux / macOS**

```bash
./mvnw spring-boot:run
```

**Windows**

```bash
mvnw.cmd spring-boot:run
```

Alternatively, build the application:

```bash
./mvnw clean package
```

## API Documentation

After starting the application, Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI specification:

```text
http://localhost:8080/v3/api-docs
```

The availability of these endpoints depends on the application's security configuration.

## Running Tests

Execute the test suite using:

```bash
./mvnw test
```

On Windows:

```bash
mvnw.cmd test
```

## Project Structure

```text
Mini_Payment_System/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   │
│   └── test/
│
├── audit/
│
├── .mvn/
│   └── wrapper/
│
├── Docker-compose.yml
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## Future Improvements

- Expand automated unit and integration test coverage.
- Improve transaction processing and error handling.
- Add monitoring and application metrics.
- Improve API documentation with request and response examples.
- Strengthen security and configuration management.
- Add CI/CD automation.

## Author

**Azekaa**

GitHub: [@Azekaa](https://github.com/Azekaa)

---

*This project is developed for educational purposes and practical experience with Java backend development and modern Spring technologies.*
