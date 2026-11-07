# Banking Management System

A modular Banking Management Backend developed using **Java 17 and Spring Boot**, focusing on secure authentication, customer onboarding, database management, asynchronous messaging, and clean software architecture.

## Tech Stack

| Category | Technologies |
|---|---|
| Language | Java 17 |
| Backend Framework | Spring Boot 3 |
| Security | Spring Security, JWT |
| Database | PostgreSQL |
| ORM | Spring Data JPA, Hibernate |
| Caching | Redis |
| Messaging | Apache Kafka |
| Containerization | Docker, Docker Compose |
| API Documentation | Swagger / OpenAPI |
| Object Mapping | MapStruct |
| Testing | JUnit 5, Mockito |
| Build Tool | Maven |

## Architecture

The application follows a modular layered architecture with separation of concerns.

- **Controller Layer:** Handles HTTP requests and API responses.
- **Service Layer:** Contains business logic and application workflows.
- **Repository Layer:** Handles database operations using Spring Data JPA.
- **Entity Layer:** Defines database entities and relationships.
- **DTO Layer:** Separates API request and response models from persistence entities.
- **Mapper Layer:** Handles entity-to-DTO mapping using MapStruct.
- **Security Layer:** Manages JWT authentication and authorization.
- **Messaging Layer:** Organizes Kafka producer and consumer components.
- **Configuration Layer:** Contains application and infrastructure configurations.

## Implemented Features

### 1. Authentication & Security
- JWT-based authentication.
- Spring Security integration.
- Role-based access control.
- Separate employee and customer authentication flows.
- Password hashing using PasswordEncoder.
- JWT expiration and unique token identifiers (JTI).

### 2. Employee Management
- Employee authentication and authorization.
- Employee roles and permissions.
- UUID-based employee identification.
- Development employee bootstrap configuration.

### 3. Customer Management
- Employee-controlled customer creation.
- Customer registration and verification workflows.
- Email and National ID validation.
- Customer-to-user account association.
- Transactional customer registration.

### 4. Secure Card Management
- Automatic card number generation.
- HMAC-SHA256 card number hashing.
- Storage of card hash and last four digits instead of the full card number.
- Secure one-time card number delivery during customer creation.

### 5. OTP Verification & Redis
- OTP-based customer registration.
- Temporary registration data stored in Redis.
- OTP expiration and verification attempts.
- Temporary password hash storage.
- Customer verification before user account creation.
- Transactional customer-user linking.

### 6. Database & Persistence
- PostgreSQL integration.
- Spring Data JPA and Hibernate.
- Entity relationships and database constraints.
- UUID-based identifiers.
- Repository pattern.
- Transaction management.

## Apache Kafka & Docker

Implemented asynchronous messaging workflows using Apache Kafka.

- Configured Kafka using Docker and Docker Compose.
- Worked with Kafka Producer and Consumer.
- Created and configured Kafka topics.
- Configured topic partitions.
- Implemented consumer group configuration.
- Configured message serialization and deserialization.
- Processed JSON messages.
- Tested message publishing and consumption using Kafka UI.

## API Documentation

Integrated Swagger / OpenAPI to:
- Document REST API endpoints.
- Inspect request and response models.
- Test endpoints directly through Swagger UI.
- Authorize protected endpoints using JWT Bearer tokens.

## Testing

Implemented automated testing using:
- JUnit 5.
- Mockito.
- JWT service unit tests.
- Spring Boot application context tests.

## Running the Project

### Prerequisites
- Java 17 or later.
- Maven.
- PostgreSQL.
- Redis.
- Docker and Docker Compose (for Kafka environment).

### Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/Mohamed-elhoussein/Banking-System.git
   ```

2. Navigate to the project directory:

   ```bash
   cd Banking-System
   ```

3. Configure your local database, Redis, mail, JWT, card hashing, and bootstrap employee settings in `application.properties`.

4. Make sure PostgreSQL and Redis are running.

5. Start the Kafka environment using the project's Docker configuration.

6. Run the application:

   ```bash
   ./mvnw spring-boot:run
   ```

   On Windows:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

### API Documentation

After starting the application, access Swagger UI:

```text
http://localhost:8082/swagger-ui/index.html
```

### Run Tests

```bash
./mvnw test
```

On Windows:

```powershell
.\mvnw.cmd test
```

## Project Goals

This project demonstrates practical backend development skills, including modular architecture, secure authentication, relational database design, caching, asynchronous messaging, API documentation, and automated testing.

---

**Author:** Mohamed Elhoussein  
**Repository:** https://github.com/Mohamed-elhoussein/Banking-System